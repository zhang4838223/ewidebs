package redistest;

import net.ewide.platform.modules.redis.RedisClientTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class Positionjedis {
	@Autowired
	private RedisClientTemplate template;
	
	@Test
	public void findPosition(){
		System.out.println(template.get("positionInfo:c68e986b332a4ea0ab547e888cf2ea4a"));
		System.out.println(template.lrange("positions",0,-1));
	}
}
