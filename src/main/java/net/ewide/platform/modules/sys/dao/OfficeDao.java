/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.dao;

import java.util.List;

import net.ewide.platform.common.persistence.TreeDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	public List<Office> findListByParentId(String parentId);
	
	public Office get(String id);
	
	public Office getParent(String id);
	
	public Integer checkOfficeName(String officeName,String parentId);
	
	public List<Office> findChildList(Office office);

	public List<Office> findOfficeList(String id);
	
	/**
	 * 查询是否有根节点
	 * @return
	 */
	public Integer findParentId();
}
