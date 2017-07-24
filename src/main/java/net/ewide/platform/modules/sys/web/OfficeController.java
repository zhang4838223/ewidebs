/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ewide.platform.common.config.Global;
import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.utils.IdGen;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.Office;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.service.OfficeService;
import net.ewide.platform.modules.sys.utils.DictUtils;
import net.ewide.platform.modules.sys.utils.UserUtils;

import org.apache.ibatis.annotations.Param;
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
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/office";
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model) {
//        model.addAttribute("list", officeService.findList(office));
//		return "modules/sys/officeList";
		return "modules/sys/office";
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public @ResponseBody String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "演示模式，不允许操作！";
		}
		/*if (!beanValidator(model, office)){
			return form(office, model);
		}*/
		if ("false".equals(checkOfficeName(office.getName(),office.getOldName(),office.getParentId()))){
			return "保存机构'" + office.getName() + "'失败，机构名称已存在!";
		}
		//层级码赋值
		if(StringUtils.isBlank(office.getId())){
			String id=IdGen.uuid();
			Office parentOffice=officeService.get(office.getParentId());
			if(parentOffice==null){
				parentOffice=new Office();
				parentOffice.setId("0");
			}
			office.setId(id);
			office.setPrimaryLevelCode((parentOffice.getPrimaryLevelCode()==null?"":parentOffice.getPrimaryLevelCode()+"/")+id+"."+(office.getType().equals("1")?"org":"dpt"));
			office.setIsNewRecord(true);
			
			List<Office> list = officeService.findListByParentId(parentOffice.getId());
			if(list!=null && list.size()>0){
				String levelCodes[]=new String[list.size()];
				for (int i=0;i<list.size();i++) {
					String str = ((Office)list.get(i)).getLevelCode();
					levelCodes[i]=str.substring(str.lastIndexOf("/")+1);
				}
				Arrays.sort(levelCodes);
				String maxCode=levelCodes[levelCodes.length-1];
				String num = Integer.parseInt(maxCode)+1+"";
				if(num.length()==1){
					num="00"+num;
				}else if(num.length()==2){
					num="0"+num;
				}
				office.setLevelCode((parentOffice.getLevelCode()==null?"":parentOffice.getLevelCode())+"/"+num);
			}else{
				office.setLevelCode((parentOffice.getLevelCode()==null?"":parentOffice.getLevelCode())+"/001");
			}
		}
		
		officeService.save(office);
		
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
//				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
//		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
//		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParent().getParentIds();
		return "保存机构"+office.getName()+"成功";
	}
	
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(String id, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "演示模式，不允许操作！";
		}
//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//			
//		}
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			Office office = new Office();
			office.setId(ids[i]);
			officeService.delete(office);
		}
//		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds();
		return "删除机构成功";
	}
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "getOffice")
	public @ResponseBody Office getOffice(String id) {
			return officeService.get(id);
	}
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "getParentOffice")
	public @ResponseBody Office getParentOffice(String id) {
			return officeService.getParent(id);
	}
	@RequestMapping(value = "data",method=RequestMethod.GET)
	public @ResponseBody Page<Office> data(Office office,HttpServletRequest request, HttpServletResponse response,Model model) {
		String parentIds=request.getParameter("parentIds");
		String id=request.getParameter("id");
		String primaryPersonId=request.getParameter("primaryPersonId");
		if(!StringUtils.isBlank(id)){
			office.setId(id);
		}
		if(!StringUtils.isBlank(parentIds)){
			office.setParentIds(parentIds+id+",");
		}else{
			office.setParentIds("0,");
		}
		if(!StringUtils.isBlank(primaryPersonId)){
			User primaryPerson = new User();
			primaryPerson.setId(primaryPersonId);
			office.setPrimaryPerson(primaryPerson);
		}
		Page<Office> page = officeService.findList(new Page<Office>(request, response,""), office);
		return page;
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll,@RequestParam(required=false) String parentId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = new ArrayList<Office>();
		if(StringUtils.isBlank(parentId)){
			list = officeService.findList(isAll);
		}else{
			list = officeService.findChildList(parentId);
		}
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					//&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& (Global.YES.equals(e.getUseable()) || (isAll!=null && isAll && Global.NO.equals(e.getUseable())) )){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				map.put("type", e.getType());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	/**
	 * 机构管理新增修改模态框
	 * @param office
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "modal",method=RequestMethod.POST)
	public String modal(Office office, Model model) {
		//查询根节点
		model.addAttribute("count", officeService.findParentId());
		return "modules/sys/officeModal";
	}
	
	/**
	 * 验证机构名称是否存在
	 * @param office
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "checkOfficeName")
	public String checkOfficeName(String name,String oldName,String parentId){
		String flg= "";
		if (name !=null && name.equals(oldName)) {
			flg= "true";
		} else if (name !=null && officeService.checkOfficeName(name,parentId) == 0) {
			flg= "true";
		}else{
			flg= "false";
		}
		return flg;
	}
	
	/**
	 * 查询归属公司
	 * @param office
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("sys:office:edit")
//	@RequestMapping(value = "findCompany",method=RequestMethod.POST)
//	public String findCompany(String id, Model model) {
//		return "modules/sys/officeModal";
//	}
	
}
