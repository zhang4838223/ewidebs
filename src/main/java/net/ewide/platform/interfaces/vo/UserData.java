package net.ewide.platform.interfaces.vo;

import java.util.List;

import com.google.common.collect.Lists;

public class UserData extends Data{
	private static final long serialVersionUID = 1L;
	private List<User> user = Lists.newArrayList();

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}
}
