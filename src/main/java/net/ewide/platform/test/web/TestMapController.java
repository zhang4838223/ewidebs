package net.ewide.platform.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 百度地图Contraoller
 * @author TianChong
 * @version 2016年7月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/test/map")
public class TestMapController {
	
	@RequestMapping(value = "index")
	public String index(){
		return "jeesite/test/testMap";
	}
	
	@RequestMapping(value = "modal")
	public String modal(){
		return "jeesite/test/testMapOrderList";
	}
}
