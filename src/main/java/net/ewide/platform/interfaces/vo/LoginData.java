package net.ewide.platform.interfaces.vo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class LoginData extends Data {
	private static final long serialVersionUID = 1L;

	private User userinfo = new User();
	private List<Office> office_list = Lists.newArrayList();
	private List<Role> role_list = Lists.newArrayList();
	private List<UserGroup> user_group_list = Lists.newArrayList();
	private List<Menu> authorization_list = Lists.newArrayList();

	public User getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(User userinfo) {
		this.userinfo = userinfo;
	}

	public List<Office> getOffice_list() {
		return office_list;
	}

	public void setOffice_list(List<Office> office_list) {
		this.office_list = office_list;
	}

	public List<Role> getRole_list() {
		return role_list;
	}

	public void setRole_list(List<Role> role_list) {
		this.role_list = role_list;
	}

	public List<UserGroup> getUser_group_list() {
		return user_group_list;
	}

	public void setUser_group_list(List<UserGroup> user_group_list) {
		this.user_group_list = user_group_list;
	}

	public List<Menu> getAuthorization_list() {
		return authorization_list;
	}

	public void setAuthorization_list(List<Menu> authorization_list) {
		this.authorization_list = authorization_list;
	}
}
