package net.ewide.platform.modules.sys.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import net.ewide.platform.modules.sys.entity.OptionData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "${adminPath}/test")
public class UitestController {
	@RequestMapping(value = {"test", ""})
	public String test(){
		return "modules/test/test";
	}
	@RequestMapping(value = {"province"})
	@ResponseBody
	public String province(){
		OptionData data = new OptionData("湖北", "1");
		OptionData data2 = new OptionData("湖南", "2");
		List<OptionData> list = Lists.newArrayList();
		list.add(data);
		list.add(data2);
		JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toString();
	}
	
	@RequestMapping(value = {"city"})
	@ResponseBody
	public String city(@RequestParam String provinceName){
		List<OptionData> list = Lists.newArrayList();
		if(provinceName.equals("1")){
			OptionData data = new OptionData("武汉", "1");
			OptionData data2 = new OptionData("天门", "2");
			list.add(data);
			list.add(data2);
		}
		if(provinceName.equals("2")){
			OptionData data = new OptionData("长沙", "3");
			OptionData data2 = new OptionData("湘潭", "4");
			list.add(data);
			list.add(data2);
		}
		JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toString();
	}
}
