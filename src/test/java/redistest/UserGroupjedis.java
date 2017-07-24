package redistest;

import net.ewide.platform.modules.redis.RedisClientTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class UserGroupjedis {
	@Autowired
	private RedisClientTemplate template;
	
	@Test
	public void findUserGroup(){
		System.out.println(template.get("userGroupInfo:66b2e5f4ad66450e9689c3c585ef5013"));
		System.out.println(template.lrange("userGroups",0,-1));
	}
	@Test
	public void findUserGroupRole(){
		System.out.println(template.lrange("userGroupRoles:66b2e5f4ad66450e9689c3c585ef5013",0,-1));
	}
	@Test
	public void findUserGroupUser(){
		System.out.println(template.lrange("userGroupUsers:66b2e5f4ad66450e9689c3c585ef5013",0,-1));
	}
}
