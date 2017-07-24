package redistest;

import net.ewide.platform.modules.redis.RedisClientTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class Menujedis {
	@Autowired
	private RedisClientTemplate template;
	
	@Test
	public void findMenu(){
		System.out.println(template.get("menuInfo:347ddd90623e4f4faf1b3a036b8a692f"));
		System.out.println(template.lrange("menus", 0,-1));
	}
}
