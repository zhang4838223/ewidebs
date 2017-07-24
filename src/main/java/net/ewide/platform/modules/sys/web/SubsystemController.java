/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.Area;
import net.ewide.platform.modules.sys.entity.Subsystem;
import net.ewide.platform.modules.sys.service.SubsystemService;

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

/**
 * 子系统管理Controller
 * 
 * @author wangtao
 * @version 2016-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/subsystem")
public class SubsystemController extends BaseController {

	@Autowired
	private SubsystemService subsystemService;

	@RequiresPermissions("sys:subsystem:view")
	@ModelAttribute
	public Subsystem get(@RequestParam(required = false) String id) {
		Subsystem entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = subsystemService.get(id);
		}
		if (entity == null) {
			entity = new Subsystem();
		}
		return entity;
	}

	@RequiresPermissions("sys:subsystem:view")
	@RequestMapping(value = { "list", "" })
	public String list(Subsystem subsystem, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/sys/subsystem";
	}

	@RequiresPermissions("sys:subsystem:view")
	@RequestMapping(value = "data", method = RequestMethod.GET)
	public @ResponseBody Page data(Subsystem subsystem,
			HttpServletRequest request, HttpServletResponse response) {
		Page<Subsystem> page = subsystemService.findPage(new Page<Subsystem>(
				request, response, ""), subsystem);
		return page;
	}

	@RequiresPermissions("sys:subsystem:view")
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public @ResponseBody Subsystem form(Subsystem subsystem) {
		return subsystemService.get(subsystem);
	}

	@RequiresPermissions("sys:subsystem:edit")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody void save(Subsystem subsystem, Model model,
			RedirectAttributes redirectAttributes) {
		subsystemService.save(subsystem);
	}

	@RequiresPermissions("sys:subsystem:edit")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody void delete(String id) {
		subsystemService.delete(id);
	}

	@RequiresPermissions("sys:subsystem:view")
	@RequestMapping(value = "modal", method = RequestMethod.POST)
	public String modal(Subsystem subsystem, Model model) {
		return "modules/sys/subsystemModal";
	}
	
	@RequiresPermissions("sys:subsystem:view")
	@RequestMapping(value = "findNameOrCode")
	public @ResponseBody String findNameOrCode(Subsystem subsystem) {
		String flg= "";
		if(subsystem!=null && subsystem.getSystemName() !=null && !subsystem.getSystemName().equals("")){
			if (subsystemService.findNameOrCode(subsystem)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}else if(subsystem!=null && subsystem.getSystemCode() !=null && !subsystem.getSystemCode().equals("")){
			if (subsystemService.findNameOrCode(subsystem)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}
		return flg;
	}
}