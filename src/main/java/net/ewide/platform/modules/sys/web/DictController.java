/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.web;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.Dict;
import net.ewide.platform.modules.sys.entity.OptionData;
import net.ewide.platform.modules.sys.entity.Subsystem;
import net.ewide.platform.modules.sys.service.DictService;
import net.ewide.platform.modules.sys.utils.DictUtils;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 数据字典Controller
 * 
 * @author wangtao
 * @version 2016-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

	@Autowired
	private DictService dictsService;

	@ModelAttribute("dict")
	public Dict get(@RequestParam(required = false) String id) {
		Dict entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = dictsService.get(id);
		}
		if (entity == null) {
			entity = new Dict();
		}
		return entity;
	}

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = { "list", "" })
	public String list() {
		return "modules/sys/dict";
	}

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "data", method = RequestMethod.GET)
	public @ResponseBody Page<Dict> data(Dict dicts,HttpServletRequest request, HttpServletResponse response) {
		if (dicts.getParentId() == null || dicts.getParentId().equals("")) {
			// //1代表根节点
			// dicts.setParentId("1");
			return null;
		}
		Page<Dict> page = dictsService.findPage(new Page<Dict>(request,response, ""), dicts);
		return page;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String dictType,@RequestParam(required = false) String label,HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Dict dicts = new Dict();
		if(dictType!=null && !dictType.equals("")){
		dicts.setDictType(dictType);
		}
		if(label!=null && !label.equals("")){
		URLDecoder.decode(label , "utf-8");
		dicts.setLabel(label);
		}
		List<Dict> list = dictsService.findList(dicts);
		for (int i = 0; i < list.size(); i++) {
			Dict e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", e.getLabel());
			map.put("value", e.getValue());
			map.put("remarks", e.getRemarks());
			map.put("p", e.getParentId());
			map.put("dictType",e.getDictType());
			mapList.add(map);
		}
		return mapList;
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody void save(Dict dicts) {
		dictsService.save(dicts);
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody void delete(String id) {
		dictsService.delete(id);
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "deleteType", method = RequestMethod.POST)
	public @ResponseBody void deleteType(String id) {
		dictsService.deleteType(id);
	}
	
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "modal", method = RequestMethod.POST)
	public String modal(Dict dict, Model model) {
		return "modules/sys/dictModal";
	}
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "modalType", method = RequestMethod.POST)
	public String modalType(Dict dict, Model model) {
		return "modules/sys/dictModalType";
	}
	
	@RequestMapping(value = { "get" }, method = RequestMethod.GET)
	@ResponseBody
	public String getDictJson(@RequestParam String type){
		String dictJson = DictUtils.getDictListJson(type);
		Gson gson = new Gson();
		Type type1 = new TypeToken<ArrayList<Dict>>() {}.getType();
		ArrayList<Dict> dictList = gson.fromJson(dictJson, type1);
		List<OptionData> list = Lists.newArrayList();
		for(Dict dict : dictList){
			OptionData data = new OptionData(dict.getLabel(), dict.getValue());
			list.add(data);
		}
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "findValueOrLabel")
	public @ResponseBody String findValueOrLabel(Dict dict) {
		String flg= "";
		if(dict!=null && dict.getValue() !=null && !dict.getValue().equals("")){
			if (dictsService.findValueOrLabel(dict)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}else if(dict!=null && dict.getLabel() !=null && !dict.getLabel().equals("")){
			if (dictsService.findValueOrLabel(dict)==0) {
				flg= "true";
			}else{
				flg= "false";
			}
		}
		return flg;
	}
	
	/**
	 * 获取数据范围下拉列表
	 * @return
	 */
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = { "scopeList"})
	public @ResponseBody List<OptionData>  scopeList() {
		List<Dict> scopeList=dictsService.scopeList();
		List<OptionData> list = Lists.newArrayList();
		for(Dict dict : scopeList){
			OptionData data = new OptionData(dict.getLabel(), dict.getValue());
			list.add(data);
		}
		return list;
	}

	
	
	/**
	 * 判断选择行下是否有子节点
	 * @param id
	 */
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "queryNode", method = RequestMethod.POST)
	public @ResponseBody boolean queryNode(String ids) {
			if(dictsService.quertNode(ids)>0){
					return true;
			}
			return false;
	}
}