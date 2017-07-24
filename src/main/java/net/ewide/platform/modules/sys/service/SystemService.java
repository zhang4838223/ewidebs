/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ewide.platform.common.config.Global;
import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.security.Digests;
import net.ewide.platform.common.security.shiro.session.SessionDAO;
import net.ewide.platform.common.service.BaseService;
import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.common.utils.CacheUtils;
import net.ewide.platform.common.utils.Encodes;
import net.ewide.platform.common.utils.IdGen;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.web.Servlets;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.syndata.event.MenuDataEvent;
import net.ewide.platform.modules.syndata.event.RoleDataEvent;
import net.ewide.platform.modules.syndata.event.UserDataEvent;
import net.ewide.platform.modules.sys.dao.MenuDao;
import net.ewide.platform.modules.sys.dao.OfficeDao;
import net.ewide.platform.modules.sys.dao.RoleDao;
import net.ewide.platform.modules.sys.dao.UserDao;
import net.ewide.platform.modules.sys.dao.UserOfficeDao;
import net.ewide.platform.modules.sys.entity.Menu;
import net.ewide.platform.modules.sys.entity.Office;
import net.ewide.platform.modules.sys.entity.Role;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.entity.UserOffice;
import net.ewide.platform.modules.sys.security.SystemAuthorizingRealm;
import net.ewide.platform.modules.sys.utils.LogUtils;
import net.ewide.platform.modules.sys.utils.UserUtils;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	@Autowired
	private UserOfficeDao userOfficeDao;
	@Autowired
	private OfficeDao officeDao;

	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	@Autowired
	private IdentityService identityService;
	
	/**
	 * id查询用户组的用户
	 * @param user
	 * @return
	 */
	public List<User> findUserGroupUser(User user){
		return userDao.findUserGroupUser(user);
	}
	/**
	 * id查询用户
	 * @param user
	 * @return
	 */
	public User userByIds(User user){
		return userDao.get(user);
	}

	// -- User Service --//
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return userDao.getByLoginName(new User(null, loginName));
	}
	
	/**
	 * 查询工号是否存在
	 * @param employeeNo
	 * @return
	 */
	public Integer checkEmployeeNo(String employeeNo) {
		return userDao.checkEmployeeNo(employeeNo);
	}
	
	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}
	/**
	 * 根据用户ID查询用户角色列表
	 * @param id
	 * @return
	 */
	public List<String> findUserRoles(String id){
		return roleDao.findUserRoles(id);
	}
	
	/**
	 * 根据用户ID查询用户角色
	 * @param id
	 * @return
	 */
	public List<Role> findRolesByUserId(String id){
		return userDao.findRolesByUserId(id);
	}
	
	
	/**
	 * 根据用户ID查询用户机构列表
	 * @param id
	 * @return
	 */
	public List<UserOffice> findUserOffices(String id){
		return userOfficeDao.findUserOffices(id);
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}
	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>) CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_
				+ officeId);
		if (list == null) {
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())) {
			user.preInsert();
			userDao.insert(user);
		} else {
			// 清除原用户机构用户缓存
			User oldUser = userDao.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null) {
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_
						+ oldUser.getOffice().getId());
			}
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())) {
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0) {
				userDao.insertUserRole(user);
			} else {
				//throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 将当前用户同步到Activiti
			saveActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
			// // 清除权限缓存
			// systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	@Transactional(readOnly = false)
	public void saveUser(User user,String userOffices) {
		saveUser(user);
		//维护用户机构关联数据
			UserOffice userOffice=new UserOffice();
			userOffice.setUser(user);
			userOfficeDao.delete(userOffice);
			if(StringUtils.isNotBlank(userOffices)){
				String str[];
				if(userOffices.indexOf(",")>-1){
					str=userOffices.split(",");
					
				}else{
					str=new String[]{userOffices};
				}
				List<Office> officeList = new ArrayList<Office>();
				for (int i = 0; i < str.length; i++) {
					Office office=officeDao.get(str[i]);
					String id=IdGen.uuid();
					userOffice=new UserOffice();
					userOffice.setCompany(user.getCompany());
					userOffice.setOffice(office);
					userOffice.setPrimaryLevelCode(office.getPrimaryLevelCode()+"/"+user.getId()+"@"+id+".psm");
					userOffice.setUser(user);
					userOffice.setId(id);
					userOffice.setIsNewRecord(true);
					userOfficeDao.insert(userOffice);
					officeList.add(office);
				}
				user.setOfficeList(officeList);
			}
			UserDataEvent event = new UserDataEvent(user,RedisEnum.save);
			publisher.publishEvent(event);
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		UserDataEvent event = new UserDataEvent(user,RedisEnum.update);
		publisher.publishEvent(event);
		// 清除用户缓存
		UserUtils.clearCache(user);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.delete(user);
		UserDataEvent event = new UserDataEvent(user,RedisEnum.delete);
		publisher.publishEvent(event);
		// 同步到Activiti
		deleteActivitiUser(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
		UserDataEvent event = new UserDataEvent(user,RedisEnum.update);
		publisher.publishEvent(event);
	}
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions() {
		return sessionDao.getActiveSessions(false);
	}
	// -- Role Service --//
	public Role getRole(String id) {
		return roleDao.get(id);
	}
	public Role getRoleByName(String officeId,String name) {
		Role r = new Role();
		r.setName(name);
		Office office = new Office();
		office.setId(officeId);
		r.setOffice(office);
		return roleDao.getByName(r);
	}
	public Role getRoleByCode(String roleCode) {
		Role r = new Role();
		r.setRoleCode(roleCode);
		return roleDao.getByRoleCode(r);
	}
	public List<Role> findRole(Role role) {
		return roleDao.findList(role);
	}
	public List<Role> findAllRole() {
		return UserUtils.getRoleList();
	}
	public Page<Role> findPageForRole(Page<Role> page, Role role) {
		role.setPage(page);
		User user = UserUtils.getUser();
		String dataScope = "";
		for(Role roles : user.getRoleList()){
			dataScope = roles.getDataScope();
			if("1".equals(dataScope)){
				break;
			}
		}
		if (user.isAdmin()||"1".equals(dataScope)){
			page.setList(roleDao.findAllList(role));
		}else{
			role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
			page.setList(roleDao.findList(role));
		}
		return page;
	}
	public List<Role> officefindRole(Office office,String noRole,String rolenames){
		if(noRole!=null && !noRole.equals("")){
			office.setArr(noRole.split(","));
		}
		if(rolenames!=null && !rolenames.equals("")){
			office.setName(rolenames);
		}
//		User user = UserUtils.getUser();
//		String dataScope = "";
//		for(Role roles : user.getRoleList()){
//			dataScope = roles.getDataScope();
//			if("1".equals(dataScope)){
//				break;
//			}
//		}
//		if (!(user.isAdmin()||"1".equals(dataScope))){
//			office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "a", ""));
//		}
		return roleDao.officefindRole(office);
	}
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())) {
			role.preInsert();
			roleDao.insert(role);
			// 同步到Activiti
			saveActivitiGroup(role);
		} else {
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0) {
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0) {
			roleDao.insertRoleOffice(role);
		}
		RoleDataEvent event = new RoleDataEvent(role,RedisEnum.save);
		publisher.publishEvent(event);
		// 同步到Activiti
		saveActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
	}
	@Transactional(readOnly = false)
	public void deleteRole(String id) {
		String[] ids = id.split(",");
		Role role = new Role();
		for (int i = 0; i < ids.length; i++) {
			role.setId(ids[i]);
			roleDao.delete(role);
			RoleDataEvent event = new RoleDataEvent(role,RedisEnum.delete);
			publisher.publishEvent(event);
			// 同步到Activiti
			deleteActivitiGroup(role);
			// 清除用户角色缓存
			UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
			// // 清除权限缓存
			// systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles) {
			if (e.getId().equals(role.getId())) {
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null) {
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}
	// -- Menu Service --//
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}
	/**
	 * 判断是否有子节点
	 * @param id
	 * @return
	 */
	public Integer quertNode(String id){
		return menuDao.queryNode(id);
	}
	
	/**
	 * 判断菜单管理name唯一性
	 * @param menu
	 * @return
	 */
	public Integer findName(Menu menu){
		return menuDao.findName(menu);
	}
	/**
	 * 判断是否是该子系统的根节点
	 * @param menu
	 * @return
	 */
	public Integer findParentId(Menu menu){
		return menuDao.findParentId(menu);
	}
	
	public List<Menu> findAllMenu() {
		return UserUtils.getMenuList();
	}
	public List<Menu> findAllMenu(Menu menu) {
		List<Menu> menuList = new ArrayList<Menu>();
			User user = UserUtils.getUser();
			if (user.isAdmin()){
				menuList = menuDao.findAllList(new Menu());
			}else{
				menu.setUserId(user.getId());
				menuList = menuDao.findByUserId(menu);
			}
		return menuList;
	}
	public List<Menu> findAllsysMenu(Menu menu) {
		return menuDao.findList(menu);
	}
	
	/**按照子系统编号封装菜单
	 * @return
	 */
	public String findMapMenu(Role role){
		List<Menu> menuList = role.getMenuList();
		Map<String,List<String>> map = Maps.newHashMap();
		for(Menu menu1 : menuList){
			String syscode = menu1.getSubsystemCode();
			if(map.containsKey(syscode)){
				List<String> list = map.get(syscode);
				list.add(menu1.getId());
			}else{
				List<String> list = Lists.newArrayList();
				list.add(menu1.getId());
				map.put(syscode, list);
			}
		}
		Gson gson = new Gson();
		String  json = gson.toJson(map);
		return json;
	}
	
	public Page<Menu> findPageMenu(Page<Menu> page, Menu menu) {
		menu.setPage(page);
		page.setList(menuDao.findList(menu));
		return page;
	}
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {
		if (menu.getParent().getId() == null || menu.getParent().getId().equals("") || menu.getParent().getId().equals("0")) {
			menu.setParentIds("0,");
			menu.getParent().setId("0");
			if (StringUtils.isBlank(menu.getId())) {
				menu.preInsert();
				menuDao.insert(menu);
			} else {
				menu.preUpdate();
				menuDao.update(menu);
			}
		} else {
			// 获取父节点实体
			menu.setParent(this.getMenu(menu.getParent().getId()));
			// 获取修改前的parentIds，用于更新子节点的parentIds
			String oldParentIds = menu.getParentIds();
			// 设置新的父节点串
			menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");
			// 保存或更新实体
			if (StringUtils.isBlank(menu.getId())) {
				menu.preInsert();
				menuDao.insert(menu);
			} else {
				menu.preUpdate();
				menuDao.update(menu);
			}
			MenuDataEvent event = new MenuDataEvent(menu,RedisEnum.save);
			publisher.publishEvent(event);
			// 更新子节点 parentIds
			Menu m = new Menu();
			m.setParentIds("%," + menu.getId() + ",%");
			List<Menu> list = menuDao.findByParentIdsLike(m);
			for (Menu e : list) {
				e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
				menuDao.updateParentIds(e);
				MenuDataEvent event1 = new MenuDataEvent(e,RedisEnum.save);
				publisher.publishEvent(event1);
			}
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		MenuDataEvent event = new MenuDataEvent(menu,RedisEnum.delete);
		publisher.publishEvent(event);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 " + Global.getConfig("productName") + "  - Powered By http://jeesite.com\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}

	// /////////////// Synchronized to the Activiti //////////////////
	// 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java
	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	private static boolean isSynActivitiIndetity = true;

	public void afterPropertiesSet() throws Exception {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		if (isSynActivitiIndetity) {
			isSynActivitiIndetity = false;
			// 同步角色数据
			List<Group> groupList = identityService.createGroupQuery().list();
			if (groupList.size() == 0) {
				Iterator<Role> roles = roleDao.findAllList(new Role()).iterator();
				while (roles.hasNext()) {
					Role role = roles.next();
					saveActivitiGroup(role);
				}
			}
			// 同步用户数据
			List<org.activiti.engine.identity.User> userList = identityService.createUserQuery().list();
			if (userList.size() == 0) {
				Iterator<User> users = userDao.findAllList(new User()).iterator();
				while (users.hasNext()) {
					saveActivitiUser(users.next());
				}
			}
		}
	}
	private void saveActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		String groupId = role.getRoleCode();
		//todo
//		// 如果修改了英文名，则删除原Activiti角色
//		if (StringUtils.isNotBlank(role.getOldRoleCode()) && !role.getOldRoleCode().equals(role.getRoleCode())) {
//			identityService.deleteGroup(role.getRoleCode());
//		}
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			group = identityService.newGroup(groupId);
		}
		group.setName(role.getName());
		group.setType(role.getRoleType());
		identityService.saveGroup(group);
		// 删除用户与用户组关系
		List<org.activiti.engine.identity.User> activitiUserList = identityService.createUserQuery()
				.memberOfGroup(groupId).list();
		for (org.activiti.engine.identity.User activitiUser : activitiUserList) {
			identityService.deleteMembership(activitiUser.getId(), groupId);
		}
		// 创建用户与用户组关系
		List<User> userList = findUser(new User(new Role(role.getId())));
		for (User e : userList) {
			String userId = e.getLoginName();// ObjectUtils.toString(user.getId());
			// 如果该用户不存在，则创建一个
			org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId)
					.singleResult();
			if (activitiUser == null) {
				activitiUser = identityService.newUser(userId);
				activitiUser.setFirstName(e.getName());
				activitiUser.setLastName(StringUtils.EMPTY);
				activitiUser.setEmail(e.getEmail());
				activitiUser.setPassword(StringUtils.EMPTY);
				identityService.saveUser(activitiUser);
			}
			identityService.createMembership(userId, groupId);
		}
	}
	public void deleteActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		if (role != null) {
			String groupId = role.getRoleCode();
			identityService.deleteGroup(groupId);
		}
	}
	private void saveActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		String userId = user.getLoginName();// ObjectUtils.toString(user.getId());
		org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId)
				.singleResult();
		if (activitiUser == null) {
			activitiUser = identityService.newUser(userId);
		}
		activitiUser.setFirstName(user.getName());
		activitiUser.setLastName(StringUtils.EMPTY);
		activitiUser.setEmail(user.getEmail());
		activitiUser.setPassword(StringUtils.EMPTY);
		identityService.saveUser(activitiUser);
		// 删除用户与用户组关系
		List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : activitiGroups) {
			identityService.deleteMembership(userId, group.getId());
		}
		// 创建用户与用户组关系
		for (Role role : user.getRoleList()) {
			String groupId = role.getRoleCode();
			// 如果该用户组不存在，则创建一个
			Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
			if (group == null) {
				group = identityService.newGroup(groupId);
				group.setName(role.getName());
				group.setType(role.getRoleType());
				identityService.saveGroup(group);
			}
			identityService.createMembership(userId, role.getRoleCode());
		}
	}
	private void deleteActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()) {
			return;
		}
		if (user != null) {
			String userId = user.getLoginName();// ObjectUtils.toString(user.getId());
			identityService.deleteUser(userId);
		}
	}
	// /////////////// Synchronized to the Activiti end //////////////////
	
}
