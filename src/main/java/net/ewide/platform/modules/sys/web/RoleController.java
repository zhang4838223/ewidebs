package net.ewide.platform.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ewide.platform.common.config.Global;
import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.utils.Collections3;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.Menu;
import net.ewide.platform.modules.sys.entity.Office;
import net.ewide.platform.modules.sys.entity.Role;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.service.OfficeService;
import net.ewide.platform.modules.sys.service.SystemService;
import net.ewide.platform.modules.sys.utils.UserUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;


/**
 * 角色Controller
 * @author ThinkGem
 * @version 2013-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("role")
	public Role get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getRole(id);
		}else{
			return new Role();
		}
	}
	
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = {"toList", ""})
	public String toList(Role role, Model model) {
		return "modules/sys/role";
	}
	
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = {"list"})
	public @ResponseBody Page<Role> list(Role role,HttpServletRequest request, HttpServletResponse response,  Model model) {
		Page<Role> page = systemService.findPageForRole(new Page<Role>(request, response,""), role);
		return page;
	} 
	
	/**
	 * 新增修改模态框
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "modal",method=RequestMethod.POST)
	public String modal(Role role, Model model) {
		String defaultMenuCode = Global.getConfig("menu.code");
		Menu menu = new Menu();
		menu.setSubsystemCode(defaultMenuCode);
		User user = UserUtils.getUser();
		// 超级管理员，跳过权限过滤
		if (user.isAdmin()){
			model.addAttribute("menuList", systemService.findAllsysMenu(menu));
		}else{
			model.addAttribute("menuList", systemService.findAllMenu(menu));
		}
//		User user = UserUtils.getUser();
//		menu.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
//		model.addAttribute("menuList", systemService.findAllMenu());
		model.addAttribute("menuMap",  systemService.findMapMenu(role));//按照子系统编号封装菜单
		model.addAttribute("officeList", officeService.findAll());//机构树，已经过滤的树
		role.setMenuSel(Global.getConfig("menu.code"));
		return "modules/sys/roleModal";
	}
	
	/**
	 * 分配模态框
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assignModal",method=RequestMethod.POST)
	public String assignModal(Role role, Model model) {
		return "modules/sys/roleAssignModal";
	}
	
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value ="data", method = RequestMethod.GET)
	public @ResponseBody String data(Role role, Model model) {
		List<Role> list = systemService.findAllRole();
		Gson g=new Gson();
		return g.toJson(list);
	}
	
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value ="officefindRole", method = RequestMethod.GET)
	public @ResponseBody String officefindRole(Office office,String noRole,String rolenames) {
		if(office==null || office.getId()==null || office.getId().equals("") || office.getParentIds()==null || office.getParentIds().equals("")){
			return null;
		}
		List<Role> list = systemService.officefindRole(office,noRole,rolenames);
		Gson g=new Gson();
		return g.toJson(list);
	}
	
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "getMenuIds")
	@ResponseBody
	public Role getMenuIds(Role role, Model model) {
		if (role.getOffice()==null){
			role.setOffice(UserUtils.getUser().getOffice());
		}
		return role;
	}
	
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "save",method = RequestMethod.POST)
	public 	@ResponseBody String save(Role role, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			return "演示模式，不允许操作！";
		}
		try {
			systemService.saveRole(role);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "服务器异常";
		}
		
		//addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
		return "保存角色'" + role.getName() + "'成功";
		//return "redirect:" + adminPath + "/sys/role/?repage";
	}
	
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam String id, RedirectAttributes redirectAttributes) {
//		if(!UserUtils.getUser().isAdmin()){
//			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
//			return "redirect:" + adminPath + "/sys/role/?repage";
//		}
//		if(Global.isDemoMode()){
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/sys/role/?repage";
//		}
		systemService.deleteRole(id);
		addMessage(redirectAttributes, "删除角色成功");
		return "redirect:" + adminPath + "/sys/role/?repage";
	}
	
	/**
	 * 角色分配页面
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assign")
	public @ResponseBody List<User> assign(Role role, Model model) {
		List<User> userList = systemService.findUser(new User(new Role(role.getId())));
		model.addAttribute("userList", userList);
//		Gson gson = new Gson();
		return userList;
	}
	
	
	
	/**
	 * 角色分配 -- 打开角色分配对话框
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "usertorole")
	public String selectUserToRole(Role role, Model model) {
		List<User> userList = systemService.findUser(new User(new Role(role.getId())));
		model.addAttribute("role", role);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "id", ","));
		model.addAttribute("officeList", officeService.findAll());
		return "modules/sys/selectUserToRole";
	}
	
	@RequestMapping(value = "userallot")
	public String userallot(Role role, Model model) {
		return "modules/sys/selectUserToRole";
	}
	
	/**
	 * 角色分配 -- 根据部门编号获取用户列表
	 * @param officeId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@ResponseBody
	@RequestMapping(value = "users")
	public List<Map<String, Object>> users(String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = new User();
		user.setOffice(new Office(officeId));
		Page<User> page = systemService.findUser(new Page<User>(1, -1), user);
		for (User e : page.getList()) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", 0);
			map.put("name", e.getName());
			mapList.add(map);			
		}
		return mapList;
	}
	
	/**
	 * 角色分配 -- 从角色中移除用户
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "outrole")
	@ResponseBody
	public String outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			return "演示模式，不允许操作！";
		}
		Role role = systemService.getRole(roleId);
		User user = systemService.getUser(userId);
		if (UserUtils.getUser().getId().equals(userId)) {
			return "无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！";
		} else {
			Boolean flag = systemService.outUserInRole(role, user);
			if (!flag) {
				return "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！";
			} else {
				return "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！";
			}
		}
	}
	
	/**
	 * 角色分配
	 * @param role
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assignrole")
	public String assignRole(Role role, String[] idsArr,Model model) {
		if(Global.isDemoMode()){
			addMessage(model, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
		}
		StringBuilder msg = new StringBuilder();
		int newNum = 0;
		for (int i = 0; i < idsArr.length; i++) {
			User user = systemService.assignUserToRole(role, systemService.getUser(idsArr[i]));
			if (null != user) {
				msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + role.getName() + "】！");
				newNum++;
			}
		}
		addMessage(model, "已成功分配 "+newNum+" 个用户"+msg);
		return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
	}

	/**
	 * 验证角色名是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String officeId, String oldName, String name) {
		String flg= "";
		if (name!=null && name.equals(oldName)) {
			flg= "true";
		} else if (name!=null && systemService.getRoleByName(officeId,name) == null) {
			flg= "true";
		}else{
			flg= "false";
		}
		return flg;
	}

	/**
	 * 验证角色英文名是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@ResponseBody
	@RequestMapping(value = "checkRoleCode")
	public String checkRoleCode(String oldRoleCode, String roleCode) {
		if (roleCode!=null && roleCode.equals(oldRoleCode)) {
			return "true";
		} else if (roleCode!=null && systemService.getRoleByCode(roleCode) == null) {
			return "true";
		}
		return "false";
	}
	
	
	@RequiresPermissions("sys:role:edit")
	@ResponseBody
	@RequestMapping(value = "getMenuList")
	public List<Map<String, Object>> getMenuList(Menu menu) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list =null;
		String defaultMenuCode = Global.getConfig("menu.code");
		if(StringUtils.isBlank(menu.getSubsystemCode())){
			menu.setSubsystemCode(defaultMenuCode);
		}
		User user = UserUtils.getUser();
		// 超级管理员，跳过权限过滤
		if (user.isAdmin()){
			list= systemService.findAllsysMenu(menu);
		}else{
			list =systemService.findAllMenu(menu);
		}
		for (Menu e : list){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", e.getName());
			map.put("pIds", e.getParentIds());
			map.put("subId", e.getSubsystemCode());
			mapList.add(map);
		}
		return mapList;
	}
}
