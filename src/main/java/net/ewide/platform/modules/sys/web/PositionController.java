package net.ewide.platform.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ewide.platform.common.config.Global;
import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.Area;
import net.ewide.platform.modules.sys.entity.Position;
import net.ewide.platform.modules.sys.service.PositionService;

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
 * 职位Controller
 * 
 * @author Jacky
 * @version 2016-3-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/position")
public class PositionController extends BaseController {
	@Autowired
	private PositionService positionService;

	@ModelAttribute("position")
	public Position get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return positionService.getPosition(id);
		} else {
			return new Position();
		}
	}
	@RequiresPermissions("sys:position:view")
	@RequestMapping(value = { "list", "" })
	public String list(Position position, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sys/position";
	}
	
	@RequiresPermissions("sys:position:view")
	@RequestMapping(value = { "data" }, method = RequestMethod.GET)
	public @ResponseBody Page data(Position position, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<Position> page = positionService.findPosition(new Page<Position>(request, response, ""), position);
		model.addAttribute("page", page);
		return page;
	}
	
	@RequiresPermissions("sys:position:view")
	@RequestMapping(value = "form")
	public String form(Position position, Model model) {
		model.addAttribute("position", position);
		return "modules/sys/positionForm";
	}
	
	@RequiresPermissions("sys:position:edit")
	@RequestMapping(value = "save",method = RequestMethod.POST)
	public  @ResponseBody void save(Position position, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
			positionService.savePosition(position);
	}
	
	@RequiresPermissions("sys:position:edit")
	@RequestMapping(value = "delete",method = RequestMethod.POST)
	public  @ResponseBody void delete(String id) {
		positionService.deletePosition(id);
	}
	@RequiresPermissions("sys:position:view")
	@RequestMapping(value = "modal", method = RequestMethod.POST)
	public String modal(Position position, Model model) {
		return "modules/sys/positionModal";
	}
	
	
	@RequiresPermissions("sys:position:view")
	@RequestMapping(value = "findNameOrCode")
	public @ResponseBody String findNameOrCode(Position position) {
		String flg= "";
		if(position!=null && position.getPositionName() !=null && !position.getPositionName().equals("")){
			 if (positionService.findNameOrCode(position)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}else if(position!=null && position.getPositionNo() !=null && !position.getPositionNo().equals("")){
			if (positionService.findNameOrCode(position)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}
		return flg;
	}
}
