package net.ewide.platform.modules.sys.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.utils.Collections3;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.entity.UserGroup;
import net.ewide.platform.modules.sys.service.OfficeService;
import net.ewide.platform.modules.sys.service.SystemService;
import net.ewide.platform.modules.sys.service.UserGroupService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户组Controller
 * 
 * @author TianChong
 * @version 2016年3月29日
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userGroup")
public class UserGroupController extends BaseController {

	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	
	@RequiresPermissions("sys:userGroup:view")
	@ModelAttribute("userGroup")
	public UserGroup get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return userGroupService.get(id);
		} else {
			return new UserGroup();
		}
	}

	@RequiresPermissions("sys:userGroup:view")
	@RequestMapping(value = { "list" })
	public String list(UserGroup userGroup, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/sys/userGroup";
	}

	@RequiresPermissions("sys:userGroup:view")
	@RequestMapping(value = "data", method = RequestMethod.GET)
	public @ResponseBody Page<UserGroup> data(HttpServletRequest request,
			HttpServletResponse response, UserGroup userGroup) {
		Page<UserGroup> page = userGroupService.findPage(new Page<UserGroup>(
				request, response, ""), userGroup);
		return page;
	}

	@RequiresPermissions("sys:userGroup:view")
	@RequestMapping(value = "form")
	public @ResponseBody UserGroup form(Model model, HttpServletRequest request) {
		String ids = request.getParameter("ids");
		UserGroup u = new UserGroup();
		u.setId(ids);
		UserGroup userGroup = userGroupService.get(u);
		return userGroup;
	}

	@RequiresPermissions("sys:userGroup:edit")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody void save(UserGroup userGroup) {
		userGroupService.insertUserorRole(userGroup);
	}

	@RequiresPermissions("sys:userGroup:view")
	@RequestMapping(value = "usergrouptouser")
	public String userGroupToUser(User user, Model model, String ids,String officeId) {
		List<User> userList = new ArrayList<User>();
		if (ids != null && !ids.equals("")) {
			if (ids.substring(0, 1).equals(",")) {
				ids = ids.substring(1, ids.length() - 1);
			}
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				User use = new User();
				use.setId(id[i]);
				userList.add(systemService.userByIds(use));
			}
		} else {
			userList = systemService.findUserGroupUser(user);
		}

		/* model.addAttribute("role", role); */
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "id", ","));
		model.addAttribute("officeList", officeService.findOfficeList(officeId));
		return "modules/sys/selectUserGroupToRole";
	}

	@RequiresPermissions("sys:userGroup:edit")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody void delete(String ids) {
		userGroupService.deleteUserorRole(ids);
	}

	@RequiresPermissions("sys:userGroup:view")
	@RequestMapping(value = "modal", method = RequestMethod.POST)
	public String modal(UserGroup userGroup, Model model) {
		return "modules/sys/userGroupModal";
	}
	
	@RequiresPermissions("sys:userGroup:view")
	@RequestMapping(value = "modalRole", method = RequestMethod.POST)
	public String modalRole(UserGroup userGroup, Model model) {
		return "modules/sys/userGroupModalRole";
	}
	@RequiresPermissions("sys:userGroup:view")
	@RequestMapping(value = "modalUser", method = RequestMethod.POST)
	public String modalUser(UserGroup userGroup, Model model) {
		return "modules/sys/userGroupModalUser";
	}
	
	@RequiresPermissions("sys:userGroup:view")
	@RequestMapping(value = "findNameOrCode")
	public @ResponseBody String findNameOrCode(UserGroup userGroup) {
		String flg= "";
		if(userGroup!=null && userGroup.getName() !=null && !userGroup.getName().equals("")){
			if (userGroupService.findNameOrCode(userGroup)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}else if(userGroup!=null && userGroup.getCode() !=null && !userGroup.getCode().equals("")){
			if (userGroupService.findNameOrCode(userGroup)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}
		return flg;
	}
}
