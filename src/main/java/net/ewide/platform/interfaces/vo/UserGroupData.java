package net.ewide.platform.interfaces.vo;

import java.util.List;

import com.google.common.collect.Lists;

public class UserGroupData extends Data {
	private static final long serialVersionUID = 1L;
	private List<UserGroup> userGroup = Lists.newArrayList();
	public List<UserGroup> getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(List<UserGroup> userGroup) {
		this.userGroup = userGroup;
	}
}
