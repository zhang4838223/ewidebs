/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.ewide.platform.common.persistence.CrudDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.Dict;

/**
 * 数据字典DAO接口
 * @author wangtao
 * @version 2016-04-26
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {
	/**
	 * 验证value或者lable的唯一性
	 * @param dict
	 * @return
	 */
	public Integer findValueOrLabel(Dict dict);
	
	/**
	 * 判断是否有子节点
	 * @param id
	 * @return
	 */
	public Integer queryNode(String id);
	
	public void deleteType(String id);
	public List<Dict> findTypeList(Dict dict);
	public List<Dict> scopeList(@Param(value="scope")String scope);
	
}