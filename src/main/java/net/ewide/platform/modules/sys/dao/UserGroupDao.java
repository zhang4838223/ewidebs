package net.ewide.platform.modules.sys.dao;

import java.util.List;

import net.ewide.platform.common.persistence.CrudDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.Role;
import net.ewide.platform.modules.sys.entity.UserGroup;

/**
 * 用户组DAO接口
 * @author TianChong
 * @version 2016年3月29日
 */
@MyBatisDao
public interface UserGroupDao  extends CrudDao<UserGroup>{
	/**
	 * 判断组名或者组编号是否唯一
	 * @param usergroup
	 * @return
	 */
	public Integer findNameOrCode(UserGroup usergroup);
	/**
	 * 根据用户id查询用户组信息
	 * @param id
	 * @return
	 */
	public List<UserGroup> findUserGroupList(String id);
	
//	public UserGroup get(UserGroup usergroup);
	
	public int insert(UserGroup userGroup);
	
	/*public List<User> getUserGroup(UserGroup userGroup);*/
	/**
	 * 维护用户组与用户关系
	 * @param role
	 * @return
	 */
	public int deleteUserGroupUser(UserGroup userGroup);

	public int insertUserGroupUser(UserGroup userGroup);
	
	/**
	 * 维护用户组与角色关系
	 * @param role
	 * @return
	 */
	public int deleteUserGroupRole(UserGroup userGroup);

	public int insertUserGroupRole(UserGroup userGroup);

}
