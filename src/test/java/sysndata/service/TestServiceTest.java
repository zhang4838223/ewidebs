package sysndata.service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import net.ewide.platform.interfaces.service.GetDataService;
import net.ewide.platform.interfaces.vo.Menu;
import net.ewide.platform.interfaces.vo.Office;
import net.ewide.platform.interfaces.vo.Role;
import net.ewide.platform.interfaces.vo.User;
import net.ewide.platform.interfaces.vo.UserGroup;
import net.ewide.platform.modules.redis.RedisClientTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class TestServiceTest {
	@Autowired
	private RedisClientTemplate template;
	
	@Autowired
	private GetDataService getDataService;
	
	/**
	 *用户名查用户信息
	 */
	@Test
	public void eventTest(){
		//User user=getDataService.iUserInfo("wangtao");
		//System.out.println(user.getId());
	}
	/**
	 *用户名ID查角色信息
	 */
	@Test
	public void eventTest1(){
		List<Role> role=getDataService.iRoleList("82da1d3bc818453b9b982a4b84116dfe");
		for (Role role2 : role) {
			System.out.println(role2.getId()+"=="+role2.getName());
		}
	}
	/**
	 *用户名ID查菜单
	 */
	@Test
	public void eventTest2(){
		/*List<Menu> menus=getDataService.iMenuList("96f9c48bc946455797548cda00afcd90");
		for (Menu menu : menus) {
			System.out.println(menu.getId());
		}*/
	}
	/**
	 *用户名ID查部门
	 */
	@Test
	public void eventTest3(){
		List<Office> offices=getDataService.iOfficeList("2");
		for (Office office : offices) {
			System.out.println(office.getId()+"==");
		}
	}
	/**
	 *用户名ID查用户组
	 */
	@Test
	public void eventTest4(){
		List<UserGroup> userGroups=getDataService.iUserGroupList("82da1d3bc818453b9b982a4b84116dfe");
		for (UserGroup userGroup : userGroups) {
			System.out.println(userGroup.getId()+"=="+userGroup.getName());
		}
	}
	/**
	 *角色ID查用户
	 */
	@Test
	public void eventTest5(){
		List<User> user=getDataService.iRoleIdAllUser("1");
		for (User user2 : user) {
			System.out.println(user2.getId()+"=="+user2.getName());
		}
	}
}
