package net.ewide.platform.interfaces.vo;

import java.util.List;

import com.google.common.collect.Lists;

public class RoleData extends Data {
	private static final long serialVersionUID = 1L;
	private List<Role> role = Lists.newArrayList();
	public List<Role>  getRole() {
		return role;
	}
	public void setRole(List<Role>  role) {
		this.role = role;
	}

}
