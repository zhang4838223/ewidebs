/**

 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.dao;

import java.util.List;

import net.ewide.platform.common.persistence.TreeDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	/**
	 * 判断是否有根节点
	 * @return
	 */
	public Integer findParentId();
	
	/**
	 * 判断name或者code的唯一性
	 * @param area
	 * @return
	 */
	public Integer findNameOrCode(Area area);
	/**
	 * 查询所有区域
	 */
	public List<Area> findAllList();
	
	/**
	 * 判断是否有子节点
	 * @param id
	 * @return
	 */
	public Integer queryNode(String id);
	
}
