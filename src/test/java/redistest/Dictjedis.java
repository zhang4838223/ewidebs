package redistest;

import net.ewide.platform.modules.redis.RedisClientTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class Dictjedis {
	@Autowired
	private RedisClientTemplate template;
	
	@Test
	public void findDict(){
		System.out.println(template.get("dictInfo:163570fb81bc4c2fbba36c96644f2649"));
		System.out.println(template.lrange("dicts", 0,-1));
	}
}
