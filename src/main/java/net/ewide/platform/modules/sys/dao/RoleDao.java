/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameter;

import net.ewide.platform.common.persistence.CrudDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.Office;
import net.ewide.platform.modules.sys.entity.Role;

/**
 * 角色DAO接口
 * @author ThinkGem
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {
	
	
	public List<Role> officefindRole(Office office);

	public Role getByName(Role role);
	
	public Role getByRoleCode(Role role);

	/**
	 * 删除角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);
	/**
	 * 添加角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);
	
	public List<String> findUserRoles(String id);
	//根据用户Id返回拥有角色集合
	public List<Role> findAllUserRoleList(String id);

}
