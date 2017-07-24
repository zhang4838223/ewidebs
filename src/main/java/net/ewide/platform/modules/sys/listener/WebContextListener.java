package net.ewide.platform.modules.sys.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.ewide.platform.modules.sys.service.SysRedisInitService;
import net.ewide.platform.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}
	
	@Override  
    public void contextInitialized(ServletContextEvent event) {  
        super.contextInitialized(event);  
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());  
        SysRedisInitService sysRedisInitService = (SysRedisInitService) applicationContext.getBean("sysRedisInitService");
        sysRedisInitService.loadRedis();
    }
}
