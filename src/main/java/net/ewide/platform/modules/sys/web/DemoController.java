package net.ewide.platform.modules.sys.web;

import net.ewide.platform.modules.sys.entity.Menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "${adminPath}/demo")
public class DemoController {
	
	
	@RequestMapping(value = "ui")
	public String form(Menu menu, Model model) {
		return "modules/demo/demo";
	}
	
	@RequestMapping(value = "oneColumn")
	public String oneColumn(){
		
		return "modules/demo/one-column-iframe";
	}
	
	@RequestMapping(value = "validator")
	public String validator(){
		return "modules/demo/validator";
	}
	
}
