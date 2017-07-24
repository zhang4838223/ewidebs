package net.ewide.platform.modules.sys.service;

import java.util.List;

import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.sys.dao.AreaDao;
import net.ewide.platform.modules.sys.dao.MenuDao;
import net.ewide.platform.modules.sys.dao.OfficeDao;
import net.ewide.platform.modules.sys.dao.PositionDao;
import net.ewide.platform.modules.sys.dao.RoleDao;
import net.ewide.platform.modules.sys.dao.SubsystemDao;
import net.ewide.platform.modules.sys.dao.UserDao;
import net.ewide.platform.modules.sys.dao.UserGroupDao;
import net.ewide.platform.modules.sys.dao.UserOfficeDao;
import net.ewide.platform.modules.sys.entity.Area;
import net.ewide.platform.modules.sys.entity.Menu;
import net.ewide.platform.modules.sys.entity.Office;
import net.ewide.platform.modules.sys.entity.Position;
import net.ewide.platform.modules.sys.entity.Role;
import net.ewide.platform.modules.sys.entity.Subsystem;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.entity.UserGroup;
import net.ewide.platform.modules.sys.entity.UserOffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * sys模板redis初始化
 * @author TianChong
 * @version 2016年4月20日
 */
@Service("sysRedisInitService")
public class SysRedisInitService {
	@Autowired
	private RedisClientTemplate template;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private UserOfficeDao userOfficeDao;
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private UserGroupDao userGroupDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private SubsystemDao subsystemDao;
	
	public void loadRedis(){/*
		//清空所有键值
		template.flushDB();
		//redis数据初始化
		Jedis jedis=template.getJedis();
		Pipeline pipeline = jedis.pipelined();
		Gson g = new Gson();
		//业务系统代码（接口对接判断）systemCode:code
		
		//用户
		List<User> userList=userDao.findList(new User());
		for (User user : userList) {
			pipeline.set("userInfo:"+user.getId(), g.toJson(user));
			pipeline.rpush("users", user.getId());
			pipeline.set("userName:"+user.getLoginName(), g.toJson(user));
			//用户关联角色
			List<String> userRolesList=roleDao.findUserRoles(user.getId());
			for (String string2 : userRolesList) {
				pipeline.rpush("userRoles:"+user.getId(), string2);
			}
			//用户关联机构
			List<UserOffice> userOfficesList=userOfficeDao.findUserOffices(user.getId());
			for (UserOffice userOffice : userOfficesList) {
				pipeline.rpush("userOffices:"+user.getId(), userOffice.getOffice().getId());
				pipeline.set("userPrimaryLevelCode:"+user.getId(), userOffice.getPrimaryLevelCode());
			}
			
		}
		//角色
		List<Role> roleList=roleDao.findList(new Role());
		for (Role role : roleList) {
			pipeline.set("roleInfo:"+role.getId(), g.toJson(role));
			pipeline.rpush("roles", role.getId());
			//角色关联用户
			List<User> usersList=userDao.findList(new User(new Role(role.getId())));
			for (User user : usersList) {
				pipeline.rpush("roleUsers:"+role.getId(), user.getId());
			}
			//角色关联菜单
			role=roleDao.get(role.getId());
			if(role==null)continue;
			List<String> menusList=role.getMenuIdList();
			for (String string : menusList) {
				pipeline.rpush("roleMenus:"+role.getId(), string);
			}
			//角色关联机构
			List<String> officesList=role.getOfficeIdList();
			for (String string : officesList) {
				pipeline.rpush("roleOffices:"+role.getId(), string);
			}
			
		}
	
		//菜单
		List<Menu> menuList=menuDao.findList(new Menu());
		for (Menu menu : menuList) {
			pipeline.set("menuInfo:"+menu.getId(), g.toJson(menu));
			pipeline.rpush("menus", menu.getId());
		}
		//机构
		List<Office> officeList=officeDao.findList(new Office());
		for (Office office : officeList) {
			pipeline.set("officeInfo:"+office.getId(), g.toJson(office));
			pipeline.rpush("offices", office.getId());
		}
		
		//职位
		List<Position> positionList=positionDao.findList(new Position());
		for (Position position : positionList) {
			pipeline.set("positionInfo:"+position.getId(), g.toJson(position));
			pipeline.rpush("positions", position.getId());
		}
		
		//用户组
		List<UserGroup> userGroupList=userGroupDao.findList(new UserGroup());
		for (UserGroup userGroup : userGroupList) {
			pipeline.set("userGroupInfo:"+userGroup.getId(), g.toJson(userGroup));
			pipeline.rpush("userGroups", userGroup.getId());
			userGroup=userGroupDao.get(userGroup.getId());
			if(userGroup==null)continue;
			//用户组关联人员
			List<String> userIdList=userGroup.getUserIdList();
			for (String string : userIdList) {
				pipeline.rpush("userGroupUsers:"+userGroup.getId(), string);
				pipeline.rpush("userUserGroups:"+string, userGroup.getId());
			}
			//用户组关联角色
			List<String> roleIdsList=userGroup.getRoleIdList();
			for (String string : roleIdsList) {
				pipeline.rpush("userGroupRoles:"+userGroup.getId(), string);
			}
		}
		//区域
		List<Area> areaList=areaDao.findList(new Area());
		for (Area area : areaList) {
			pipeline.rpush("areas", area.getId());
			pipeline.set("areaInfo:"+area.getId(), g.toJson(area));
		}
		//子系统
		List<Subsystem> subsystemList=subsystemDao.findList(new Subsystem());
		for (Subsystem subsystem : subsystemList) {
			pipeline.rpush("subsystems", subsystem.getId());
			pipeline.set("subsystemInfo:"+subsystem.getId(), g.toJson(subsystem));
			pipeline.set("subsystemSystemCode:"+subsystem.getSystemCode(), g.toJson(subsystem));
		}
		pipeline.sync();
		template.returnResource(jedis);
		template.disconnect(jedis);
	*/}
}
