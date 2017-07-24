package redistest;

import net.ewide.platform.modules.redis.RedisClientTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class Rolejedis {
	@Autowired
	private RedisClientTemplate template;
	
	@Test
	public void findArea(){
		System.out.println("roleInfo:==="+template.get("roleInfo:e045c1900dce42c59c6e245ee77c727e"));
		System.out.println("roleMenus:==="+template.get("roleMenus:e045c1900dce42c59c6e245ee77c727e"));
		System.out.println("roleOffices:==="+template.get("roleOffices:e045c1900dce42c59c6e245ee77c727e"));
		System.out.println("roles:==="+template.get("roles"));
	}
}
