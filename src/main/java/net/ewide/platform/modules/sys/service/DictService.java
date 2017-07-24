/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.service.CrudService;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.syndata.event.DictDataEvent;
import net.ewide.platform.modules.sys.dao.DictDao;
import net.ewide.platform.common.utils.CacheUtils;
import net.ewide.platform.modules.sys.entity.Dict;
import net.ewide.platform.modules.sys.entity.Role;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.utils.UserUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据字典Service
 * @author wangtao
 * @version 2016-04-26
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {

	public static final String CACHE_DICT_MAP = "dictMap";
	
	public Dict get(String id) {
		return super.get(id);
	}
	
	public List<Dict> findList(Dict dicts) {
		return super.findList(dicts);
	}
	
	public Page<Dict> findPage(Page<Dict> page, Dict dicts) {
		return super.findPage(page, dicts);
	}
	/**
	 * 验证value或者lable的唯一性
	 * @param dict
	 * @return
	 */
	public Integer findValueOrLabel(Dict dict){
		return dao.findValueOrLabel(dict);
	}
	
	/**
	 * 判断是否有子节点
	 * @param id
	 * @return
	 */
	public Integer quertNode(String id){
		return dao.queryNode(id);
	}
	
	@Transactional(readOnly = false)
	public void save(Dict dicts) {
		super.save(dicts);
		DictDataEvent event = new DictDataEvent(dicts,RedisEnum.save);
		publisher.publishEvent(event);
		//清空缓存
		CacheUtils.remove(CACHE_DICT_MAP);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			Dict dicts = new Dict();
			dicts.setId(ids[i]);
			super.delete(dicts);
			DictDataEvent event = new DictDataEvent(dicts,RedisEnum.delete);
			publisher.publishEvent(event);
		}

		//清空缓存
		CacheUtils.remove(CACHE_DICT_MAP);
	}
	@Transactional(readOnly = false)
	public void deleteType(String id) {
		dao.deleteType(id);
		Dict dict=new Dict();
		dict.setId(id);
		DictDataEvent event = new DictDataEvent(dict,RedisEnum.delete);
		publisher.publishEvent(event);
		//清空缓存
		CacheUtils.remove(CACHE_DICT_MAP);
	}
	
	/**
	 * 获取数据范围下拉列表
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<Dict> scopeList(){
		User user = UserUtils.getUser();
		if(user.isAdmin()){
			String scope = "1";
			return dao.scopeList(scope);
		}else{
			List<String> list = new ArrayList<String>();
			for(Role role : user.getRoleList()){
				list.add(role.getDataScope());
			}
			String scope = Collections.min(list);
			return dao.scopeList(scope);
		}
	}
	
}