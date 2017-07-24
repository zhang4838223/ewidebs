package net.ewide.platform.interfaces.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.ewide.platform.common.service.BaseService;
import net.ewide.platform.interfaces.vo.Menu;
import net.ewide.platform.interfaces.vo.Office;
import net.ewide.platform.interfaces.vo.Role;
import net.ewide.platform.interfaces.vo.User;
import net.ewide.platform.interfaces.vo.UserGroup;
import net.ewide.platform.modules.redis.RedisClientTemplate;

/**
 * 用户权限接口
 * @author wangtao
 * @version 2016-4-19 09:47:03
 */
@Service
@Transactional(readOnly = true)
public class GetDataService extends BaseService {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;

	// ----------------------------登录校验------------------------------------------------------------------
	/**
	 * 根据用户名查询用户信息
	 * @param loginName
	 * @return User
	 */
	public User iUserInfo(String loginName) {
		try {
			if (template.exists("userName:" + loginName)) {
				return iUser(template.get("userName:" + loginName));
			}
		} catch (Exception e) {
			logger.error("根据用户名查询用户信息出现错误：用户名为" + loginName + "错误信息为:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 所有角色list
	 * @param userId
	 * @return List<Role>
	 */
	public List<Role> iRoleList(String userId) {
		try {
			List<String> roles = template.lrange("userRoles:" + userId, 0, -1);
			if (roles == null && roles.size() <= 0) {
				return null;
			}
			List<Role> roleList = new ArrayList<Role>();
			for (String srcRole : roles) {
				if (template.exists("roleInfo:" + srcRole)) {
					roleList.add(iRole(template.get("roleInfo:" + srcRole)));
				}
			}
			return roleList;
		} catch (Exception e) {
			logger.error("根据用户Id查询拥有角色出现错误：用户id为" + userId + "错误信息为:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取指定用户的菜单权限
	 * @param loginName
	 * @return List<Menu>
	 */
	public List<Menu> iMenuList(String userId,String systemCode) {
		try {
			List<String> roles = template.lrange("userRoles:" + userId, 0, -1);
			if (roles == null && roles.size() <= 0) {
				return null;
			}
			List<Menu> menuList = new ArrayList<Menu>();
			Set<String> menuIdList = new HashSet<String>();
			for (String srcRole : roles) {
				List<String> menus = template.lrange("roleMenus:" + srcRole, 0, -1);
				if (menus == null && menus.size() <= 0) {
					return null;
				}
				for (String srcMenu : menus) {
					if (template.exists("menuInfo:" + srcMenu)) {
						Menu  menu = iMenu(template.get("menuInfo:" + srcMenu));
						if(systemCode.equals(menu.getSubsystemCode())){
							if(!menuIdList.contains(srcMenu)){//去重设置
								menuList.add(iMenu(template.get("menuInfo:" + srcMenu)));
								menuIdList.add(srcMenu);
							}
						}
					}
				}
			}
			return menuList;
		} catch (Exception e) {
			logger.error("根据用户Id查询拥有菜单出现错误：用户id为" + userId + "错误信息为:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取指定用户兼职的部门信息
	 * @param userId
	 * @return List<Office>
	 */
	public List<Office> iOfficeList(String userId) {
		try {
			User user = iUser(template.get("userInfo:" + userId));
			List<Office> officeList = new ArrayList<Office>();
//			if (template.exists("officeInfo:" + user.getOffice().getId())) {
//				Office office = iOffice(template.get("officeInfo:" + user.getOffice().getId()));
//				officeList.add(office);
//			}
			List<String> offices = template.lrange("userOffices:" + userId, 0, -1);
			if (offices != null && offices.size() > 0) {
				for (String srcOffice : offices) {
					if (template.exists("officeInfo:" + srcOffice)) {
						officeList.add(iOffice(template.get("officeInfo:" + srcOffice)));
					}
				}
			}
			return officeList;
		} catch (Exception e) {
			logger.error("根据用户Id查询拥有部门出现错误：用户id为" + userId + "错误信息为:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取指定用户的用户组信息
	 * @param loginName
	 * @return
	 */
	public List<UserGroup> iUserGroupList(String userId) {
		try {
			List<String> userGroups = template.lrange("userUserGroups:" + userId, 0, -1);
			List<UserGroup> userGroupList = new ArrayList<UserGroup>();
			if (userGroups != null && userGroups.size() > 0) {
				for (String srcUserGroup : userGroups) {
					if (template.exists("userGroupInfo:" + srcUserGroup)) {
						userGroupList.add(iUserGroup(template.get("userGroupInfo:" + srcUserGroup)));
					}
				}
			}
			return userGroupList;
		} catch (Exception e) {
			logger.error("根据用户Id查询拥有用户组出现错误：用户id为" + userId + "错误信息为:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	// ----------------------------获取用户当前公司组织架构------------------------------------------------------------------
	public List<Office> iOfficeById(String officeId) {
		return null;
	}
	// -----------------------------关联查询，对象翻译-----------------------------------------------
	/**
	 * 根据对象类型和id查询（1:用户；2:部门/机构；3:角色；4:用户组；）
	 * @param id
	 * @param type
	 * @return 根据对象类型返回对应的对象
	 */
	public Object iObject(String id, int type) {
		try {
			if(type==1){
				User user=iUser(template.get("userInfo:"+id));
				return user;
			}else if(type==2){
				Office office=iOffice(template.get("officeInfo:"+id));
				return office;
			}else if(type==3){
				Role role=iRole(template.get("roleInfo:"+id));
				return role;
			}else if(type==4){
				UserGroup userGroup=iUserGroup(template.get("userGroupInfo:"+id));
				return userGroup;
			}else {
				return 1;
			}
		} catch (Exception e) {
			logger.error("根据对象类型和id查询（1:用户；2:部门/机构；3:角色；4:用户组；）,对象id：" + id + "错误信息为:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	// -----------------------------通过角色查询用户----------------------------------
	/**
	 * 通过角色查询所有用户
	 * @param roleId
	 * @return
	 */
	public List<User> iRoleIdAllUser(String roleId) {
		try {
			List<String> users = template.lrange("roleUsers:" + roleId, 0, -1);
			List<User> userList = new ArrayList<User>();
			if (users != null && users.size() > 0) {
				for (String srcUser : users) {
					if (template.exists("userInfo:" + srcUser)) {
						userList.add(iUser(template.get("userInfo:" + srcUser)));
					}
				}
			}
			return userList;
		} catch (Exception e) {
			logger.error("根据角色查询所有用户时出现错误,角色id：" + roleId + "错误信息为:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	// -----------------------------公用方法 将字符串转换为相应的对象----------------------------------
	/**
	 * 将User的json字符串转换User对象
	 * @param src
	 * @return User
	 */
	public User iUser(String src) {
		Gson g = new Gson();
		User user = g.fromJson(src, User.class);
		return user;
	}
	/**
	 * 将Role的json字符串转换Role对象
	 * @param src
	 * @return Role
	 */
	public Role iRole(String src) {
		Gson g = new Gson();
		Role role = g.fromJson(src, Role.class);
		return role;
	}
	/**
	 * 将Menu的json字符串转换Menu对象
	 * @param src
	 * @return Menu
	 */
	public Menu iMenu(String src) {
		Gson g = new Gson();
		Menu menu = g.fromJson(src, Menu.class);
		return menu;
	}
	/**
	 * 将Office的json字符串转换Office对象
	 * @param src
	 * @return Office
	 */
	public Office iOffice(String src) {
		Gson g = new Gson();
		Office office = g.fromJson(src, Office.class);
		return office;
	}
	/**
	 * 将UserGroup的json字符串转换UserGroup对象
	 * @param src
	 * @return UserGroup
	 */
	public UserGroup iUserGroup(String src) {
		Gson g = new Gson();
		UserGroup userGroup = g.fromJson(src, UserGroup.class);
		return userGroup;
	}
	// -----------------------------以下是查询数据库----------------------------------
	/**
	 * 用户基本信息
	 * @param name
	 * @return User
	 */
	/*
	 * public User iUserInfo(String loginName){ //根据登录名称查询用户 return userDao.getByLoginName(new
	 * User(null, loginName)); }
	 *//**
	 * 所有角色list
	 * @return Role
	 */
	/*
	 * public List<Role> iUserRole(String loginName){ User user = iUserInfo(loginName); //返回所有角色
	 * return roleDao.findAllUserRoleList(user.getId()); }
	 *//**
	 * 获取指定用户的菜单权限
	 * @param loginName
	 * @return
	 */
	/*
	 * public List<Menu> iMenuList(String loginName){ User user = iUserInfo(loginName); return
	 * menuDao.findUserMenuList(user.getId()); }
	 *//**
	 * 获取指定用户的部门信息
	 * @param loginName
	 * @return
	 */
	/*
	 * public List<Office> iOfficeList(String loginName){ User user = iUserInfo(loginName); return
	 * userOfficeDao.findUserOffices(user.getId()); }
	 *//**
	 * 获取指定用户的用户组信息
	 * @param loginName
	 * @return
	 */
	/*
	 * public List<UserGroup> iUserGroupList(String loginName){ User user = iUserInfo(loginName);
	 * return userGroupDao.findUserGroupList(user.getId()); }
	 */
}
