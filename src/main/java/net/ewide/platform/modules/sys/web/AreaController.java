/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.Area;
import net.ewide.platform.modules.sys.entity.Subsystem;
import net.ewide.platform.modules.sys.service.AreaService;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 区域管理11Controller
 * @author 汪涛
 * @version
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/area")
public class AreaController extends BaseController {
	@Autowired
	private AreaService areaService;

	
	
	
	@ModelAttribute("area")
	public Area get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return areaService.get(id);
		} else {
			return new Area();
		}
	}
	
	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = { "list", "" })
	public String list(Area area, Model model) {
		model.addAttribute("area", area);
		return "modules/sys/area";
	}
	
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Area> list = areaService.findAllList();
		for (int i = 0; i < list.size(); i++) {
			Area e = list.get(i);
			if (StringUtils.isBlank(extId)
					|| (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = "data", method = RequestMethod.GET)
	public @ResponseBody Page<Area> data(Area area, HttpServletRequest request, HttpServletResponse response) {
		if (area != null && area.getParentIds() != null && !area.getParentIds().equals("") && area.getId() != null
				&& !area.getId().equals("")) {
			area.setParentIds(area.getParentIds() + area.getId() + ",%");
		}
		Page<Area> page = areaService.findPage(new Page<Area>(request, response, ""), area);
		return page;
	}
	
	@RequiresPermissions("sys:area:edit")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody void delete(String id) {
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			Area area = new Area();
			area.setId(ids[i]);
			areaService.delete(area);
		}
	}
	
	@RequiresPermissions("sys:area:edit")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody void save(Area area) {
		areaService.save(area);
	}
	
	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public @ResponseBody Area form(Area area) {
		if (area.getParent() == null || area.getParent().getId() == null) {
			area.setParent(UserUtils.getUser().getOffice().getArea());
		}
		area.setParent(areaService.get(area.getParent().getId()));
		return area.getParent();
	}
	
	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = "modal", method = RequestMethod.POST)
	public String modal(Area area, Model model) {
		model.addAttribute("count", areaService.findParentId());
		return "modules/sys/areaModal";
	}
	
	
	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = "findNameOrCode")
	public @ResponseBody String findNameOrCode(Area area) {
		String flg= "";
		if(area!=null && area.getName() !=null && !area.getName().equals("")){
			if (areaService.findNameOrCode(area)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}else if(area!=null && area.getCode() !=null && !area.getCode().equals("")){
			if (areaService.findNameOrCode(area)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}
		return flg;
	}
	
	/**
	 * 判断选择行下是否有子节点
	 * @param id
	 */
	@RequiresPermissions("sys:area:view")
	@RequestMapping(value = "queryNode", method = RequestMethod.POST)
	public @ResponseBody boolean queryNode(String ids) {
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				if(areaService.quertNode(id[i])>0){
					return true;
				}
			}
			return false;
	}
	
}
