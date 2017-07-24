package net.ewide.platform.interfaces.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ewide.platform.interfaces.vo.ErrorCodeEnum;
import net.ewide.platform.interfaces.vo.LoginData;
import net.ewide.platform.interfaces.vo.Menu;
import net.ewide.platform.interfaces.vo.Office;
import net.ewide.platform.interfaces.vo.ResponseVo;
import net.ewide.platform.interfaces.vo.Role;
import net.ewide.platform.interfaces.vo.User;
import net.ewide.platform.interfaces.vo.UserGroup;
/**
 * @author wanghaozhe
 * 用户登录验证接口数据生成的策略实现类
 */
@Component
public class ConcreteStrategyDataAll implements BuildDataStrategy {
	@Autowired GetDataService dataservice;
	@Autowired LoginData loginData;
	
	@Override
	public ResponseVo buildData(String username,String systemCode) {
		ResponseVo responseVo = new ResponseVo();
		User user = dataservice.iUserInfo(username);
		List<Office> officeList = dataservice.iOfficeList(user.getId());
		List<Role> roleList = dataservice.iRoleList(user.getId());
		List<UserGroup> userGroupList = dataservice.iUserGroupList(user.getId());
		List<Menu> menuList = dataservice.iMenuList(user.getId(),systemCode);
		if(user !=null){
			loginData.setUserinfo(user);
		}
		if( officeList != null){
			loginData.setOffice_list(officeList);
		}
		if(roleList != null){
			loginData.setRole_list(roleList);
		}
		if(userGroupList != null){
			loginData.setUser_group_list(userGroupList);
		}
		if(menuList != null){
			loginData.setAuthorization_list(menuList);
		}
		responseVo.setErrcode(ErrorCodeEnum.SUCCESS.getCode());
		responseVo.setErrmsg(ErrorCodeEnum.SUCCESS.getExplain());
		responseVo.setData(loginData);
		return responseVo;
	}
}
