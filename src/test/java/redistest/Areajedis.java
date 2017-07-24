package redistest;

import net.ewide.platform.modules.redis.RedisClientTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class Areajedis {
	@Autowired
	private RedisClientTemplate template;
	
	@Test
	public void findArea(){
		System.out.println(template.get("areaInfo:02d9603b39044d1cbfb7a2ff912c0678"));
		System.out.println(template.lrange("areas", 0,-1));
	}
	
	@Test
	public void findUser(){
		System.out.println(template.get("userInfo:f7d5746ceb3343528db6e7997ec61b5e"));
		System.out.println(template.lrange("users", 0,-1));
	}
}
