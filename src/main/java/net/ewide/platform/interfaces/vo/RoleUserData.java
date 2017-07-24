package net.ewide.platform.interfaces.vo;

import java.util.List;

public class RoleUserData extends Data {
	private static final long serialVersionUID = 1L;
	
	private List<User> user_list;

	public List<User> getUser_list() {
		return user_list;
	}

	public void setUser_list(List<User> user_list) {
		this.user_list = user_list;
	}
	
}
