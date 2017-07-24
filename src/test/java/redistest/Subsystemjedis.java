package redistest;

import net.ewide.platform.modules.redis.RedisClientTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class Subsystemjedis {
	@Autowired
	private RedisClientTemplate template;
	
	@Test
	public void findSubsystem(){
		System.out.println(template.get("subsystemInfo:8b0a345eee074e0e9cd7350a339e89b0"));
		System.out.println(template.lrange("subsystems",0,-1));
	}
}
