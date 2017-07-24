/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.service.TreeService;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.syndata.event.AreaDataEvent;
import net.ewide.platform.modules.syndata.event.UserDataEvent;
import net.ewide.platform.modules.sys.dao.AreaDao;
import net.ewide.platform.modules.sys.entity.Area;
import net.ewide.platform.modules.sys.entity.Dict;
import net.ewide.platform.modules.sys.entity.Menu;
import net.ewide.platform.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}
	/**
	 * 查询所有区域
	 * @return
	 */
	public List<Area> findAllList(){
		return dao.findAllList();
	}
	
	public Integer findParentId() {
		return dao.findParentId();
	}
	
	/**
	 * 判断是否有子节点
	 * @param id
	 * @return
	 */
	public Integer quertNode(String id){
		return dao.queryNode(id);
	}
	
	public Integer findNameOrCode(Area area) {
		return dao.findNameOrCode(area);
	}
	
	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		AreaDataEvent event = new AreaDataEvent(area,RedisEnum.save);
		publisher.publishEvent(event);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		AreaDataEvent event = new AreaDataEvent(area,RedisEnum.delete);
		publisher.publishEvent(event);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
}
