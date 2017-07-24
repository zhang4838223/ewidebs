/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.dao;

import java.util.List;

import net.ewide.platform.common.persistence.CrudDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {
	
	/**
	 * 判断菜单name唯一
	 * @param menu
	 * @return
	 */
	public Integer findName(Menu menu);
	/**
	 * 判断是否是该子系统的根节点
	 * @param menu
	 * @return
	 */
	public Integer findParentId(Menu menu);
	/**
	 * 判断是否有子节点
	 * @param id
	 * @return
	 */
	public Integer queryNode(String id);
	
	public List<Menu> findUserMenuList(String id);

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
}
