package redistest;

import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.redis.RedisDataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class Testjedis {
	@Autowired
	private RedisClientTemplate template;
	@Autowired
	private RedisDataSource source;
	
	@Test
	public void eventTest(){
//		System.out.println("aaaaa:"+source.getRedisClient());
		System.out.println(template.exists("bb"));

		System.out.println(template.set("bb", "123123123213123"));
	}
}
