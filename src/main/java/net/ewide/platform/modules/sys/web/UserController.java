/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import net.ewide.platform.common.beanvalidator.BeanValidators;
import net.ewide.platform.common.config.Global;
import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.utils.DateUtils;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.utils.excel.ExportExcel;
import net.ewide.platform.common.utils.excel.ImportExcel;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.Office;
import net.ewide.platform.modules.sys.entity.OptionData;
import net.ewide.platform.modules.sys.entity.Position;
import net.ewide.platform.modules.sys.entity.Role;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.entity.UserGroup;
import net.ewide.platform.modules.sys.entity.UserOffice;
import net.ewide.platform.modules.sys.service.PositionService;
import net.ewide.platform.modules.sys.service.SystemService;
import net.ewide.platform.modules.sys.service.UserGroupService;
import net.ewide.platform.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private UserGroupService userGroupService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		
//		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/user";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
//        model.addAttribute("page", page);
//		return "modules/sys/userList";
		model.addAttribute("userGroups",userGroupService.findList(new UserGroup()));
		model.addAttribute("positions",positionService.findList(new Position()));
		model.addAttribute("allRoles", systemService.findAllRole());
		model.addAttribute("officeId", request.getParameter("officeId"));
		model.addAttribute("officeName", request.getParameter("officeName"));
		model.addAttribute("name", request.getParameter("name"));
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "modules/sys/user";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response,""), user);
		return page;
	}
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"userByIds"}, method = RequestMethod.GET)
	public @ResponseBody String userByIds(String ids,Model model) {
		List<User> userlist=new ArrayList<User>();
		if(ids==null || ids.equals("")){
			return null;
		}
		if(ids.substring(0, 1).equals(",")){
			ids=ids.substring(1, ids.length()-1);
		}
		String []id=ids.split(",");
		for (int i = 0; i < id.length; i++) {
			User user=new User();
			user.setId(id[i]);
			userlist.add(systemService.userByIds(user));
		}
		model.addAttribute("userlist",userlist);
		Gson g=new Gson();
		return g.toJson(userlist);
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"userRole"})
	public List<Role> userRole(String id, HttpServletRequest request, HttpServletResponse response, Model model){
		return systemService.findRolesByUserId(id);
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"userOffice"})
	public String userOffice(String id, HttpServletRequest request, HttpServletResponse response, Model model){
		List<UserOffice> list = systemService.findUserOffices(id);
		Gson g = new Gson();
		return g.toJson(list);
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/sys/user/list?repage";
			return "演示模式，不允许操作！";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
//		if (!beanValidator(model, user)){
//			return form(user, model);
//		}
		if ("false".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
//			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return "保存用户'" + user.getLoginName() + "'失败，登录名已存在!";
		}
		if ("false".equals(checkEmployeeNo(user.getOldNo(), user.getNo()))){
//			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return "保存用户'" + user.getLoginName() + "'失败，工号已存在!";
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		
		systemService.saveUser(user,request.getParameter("userOffice"));
		
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
//		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
//		return "redirect:" + adminPath + "/sys/user/list?officeId="+user.getOffice().getId()+"&officeName="+user.getOffice().getName();
		return "保存用户'" + user.getLoginName() + "'成功!";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/sys/user/list?repage";
			return "演示模式，不允许操作！";
		}
		if (UserUtils.getUser().getId().equals(id.replace(",", ""))){
//			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
			return "删除用户失败, 不允许删除当前用户";
		}else if (User.isAdmin(id.replace(",", ""))){
//			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
			return "删除用户失败, 不允许删除超级管理员用户";
		}else{
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				User user = new User();
				user.setId(ids[i]);
				systemService.deleteUser(user);
			}
//			addMessage(redirectAttributes, "删除用户成功");
		}
		return "删除用户成功";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		String flg= "";
		if (loginName !=null && loginName.equals(oldLoginName)) {
			flg= "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			flg= "true";
		}else{
			flg= "false";
		}
		return flg;
	}
	
	/**
	 * 验证工号是否存在
	 * @param oldEmployeeNo
	 * @param employeeNo
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkEmployeeNo")
	public String checkEmployeeNo(String oldNo, String no) {
		String flg= "";
		if (no !=null && no.equals(oldNo)) {
			flg= "true";
		} else if (no !=null && systemService.checkEmployeeNo(no) == 0) {
			flg= "true";
		}else{
			flg= "false";
		}
		return flg;
	}
	
	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}
	/**
	 * 用户信息显示保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "infoUpdate")
	public void infoUpdate(User user , HttpServletResponse response) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
		}
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwdUpdate")
	public @ResponseBody boolean modifyPwdUpdate(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				return false;
			}else{
				return true;
			}
		}
		model.addAttribute("user", user);
		return false;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 新增修改模态框
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "modal",method=RequestMethod.POST)
	public String modal(User user, Model model) {
		model.addAttribute("positions",positionService.findList(new Position()));
		return "modules/sys/userModal";
	}
	
	/**
	 * 职位选择下拉框
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@ResponseBody
	@RequestMapping(value = "positions")
	public String positions(String companyId) {
		Position positions = new Position();
		Office office = new Office();
		office.setId(companyId);
		positions.setOffice(office);
		List<Position> positionList = positionService.findPositionById(positions);
		List<OptionData> list = Lists.newArrayList();
		for(Position position : positionList){
			OptionData data = new OptionData(position.getPositionName(), position.getId());
			list.add(data);
		}
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	
	/**
	 * 选择角色模态框
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "userRoleModal",method=RequestMethod.POST)
	public String userRoleModal(User user, Model model) {
		return "modules/sys/userRoleModal";
	}
    
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
}
