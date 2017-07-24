/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ewide.platform.common.config.Global;
import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.utils.PropertiesLoader;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.Area;
import net.ewide.platform.modules.sys.entity.Menu;
import net.ewide.platform.modules.sys.entity.Office;
import net.ewide.platform.modules.sys.entity.OptionData;
import net.ewide.platform.modules.sys.entity.Position;
import net.ewide.platform.modules.sys.entity.Subsystem;
import net.ewide.platform.modules.sys.service.SubsystemService;
import net.ewide.platform.modules.sys.service.SystemService;
import net.ewide.platform.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;

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

/**
 * 菜单Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {
	private static PropertiesLoader loader = new PropertiesLoader("ewidebs.properties");
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private SubsystemService subsystemService;
	
	@RequiresPermissions("sys:menu:view")
	@ModelAttribute("menu")
	public Menu get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getMenu(id);
		}else{
			return new Menu();
		}
	}
	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = {"list", ""})
	public String list(Menu menu,Model model) {
		Subsystem subsystem=new Subsystem();
		subsystem.setSystemCode(loader.getProperty("menu.code"));
		Integer count=subsystemService.findNameOrCode(subsystem);
		if(count>0){
			model.addAttribute("menuCode", loader.getProperty("menu.code"));
		}
		return "modules/sys/menu";
	}
	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "data", method = RequestMethod.GET)
	public @ResponseBody Page<Menu> data(Menu menu,HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if(menu != null && menu.getParentIds()!=null && !menu.getParentIds().equals("")
				&& menu.getId()!=null && !menu.getId().equals("")){
			menu.setParentIds(menu.getParentIds()+menu.getId()+",%");
		}
		Page<Menu> page = systemService.findPageMenu(new Page<Menu>(request, response,""), menu);
		return page;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,Menu menu,@RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list =null;
		if(menu!=null){
			list= systemService.findAllsysMenu(menu);
		}else{
			list = systemService.findAllMenu();
		}
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				map.put("pIds", e.getParentIds());
				map.put("subId", e.getSubsystemCode());
				mapList.add(map);
			}
		}
		return mapList;
	}
	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public @ResponseBody Menu form(Menu menu, Model model) {
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(systemService.getMenu(menu.getParent().getId()));
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())){
			List<Menu> list = Lists.newArrayList();
			List<Menu> sourcelist = systemService.findAllMenu();
			Menu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0){
				menu.setSort(list.get(list.size()-1).getSort() + 30);
			}
		}
		model.addAttribute("menu", menu);
		return menu.getParent();
	}
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody void save(Menu menu, Model model) {
		systemService.saveMenu(menu);
	}
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody void delete(String id) {
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				Menu m=new Menu();
				m.setId(ids[i]);
				systemService.deleteMenu(m);
			}
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "tree")
	public String tree() {
		return "modules/sys/menuTree";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "treeselect")
	public String treeselect(String parentId, Model model) {
		model.addAttribute("parentId", parentId);
		return "modules/sys/menuTreeselect";
	}
	
	/**
	 * 批量修改菜单排序
	 */
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/menu/";
		}
    	for (int i = 0; i < ids.length; i++) {
    		Menu menu = new Menu(ids[i]);
    		menu.setSort(sorts[i]);
    		systemService.updateMenuSort(menu);
    	}
    	addMessage(redirectAttributes, "保存菜单排序成功!");
		return "redirect:" + adminPath + "/sys/menu/";
	}
	
	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "modal", method = RequestMethod.POST)
	public String modal(Menu menu, Model model) {
		model.addAttribute("menu", menu);
		model.addAttribute("count", systemService.findParentId(menu));
		return "modules/sys/menuModal";
	}
	/**
	 * 获取子系统下拉列表
	 * @return
	 */
	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = { "subsystemList"})
	public @ResponseBody List<OptionData>  subsystemList() {
		List<Subsystem> menuList=subsystemService.findList(new Subsystem());
		List<OptionData> list = Lists.newArrayList();
		for(Subsystem subsystem : menuList){
			OptionData data = new OptionData(subsystem.getSystemName(), subsystem.getSystemCode());
			list.add(data);
		}
		return list;
	}
	
	/**
	 * 判断菜单管理name唯一性
	 * @param menu
	 * @param noName
	 * @return
	 */
	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "findName")
	public @ResponseBody String findName(Menu menu) {
		String flg= "";
		if(menu!=null && menu.getName() !=null && !menu.getName().equals("")){
			if (systemService.findName(menu)==0) {
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
	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "queryNode", method = RequestMethod.POST)
	public @ResponseBody boolean queryNode(String ids) {
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				if(systemService.quertNode(id[i])>0){
					return true;
				}
			}
			return false;
	}
}
