package net.ewide.platform.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 动态模态框 Controller
 * @author TianChong
 * @version 2016年4月26日
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/modal")
public class ModalController {
	
	@RequestMapping("testIndex")
	public String testIndex(){
		return "modules/test/testIndex";
	}
	
	@RequestMapping("getModalHtml")
	public String getModalHtml(String jspName){
//		int i =1/0;
		return jspName;
	}
}
