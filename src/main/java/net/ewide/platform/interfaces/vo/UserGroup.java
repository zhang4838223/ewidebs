package net.ewide.platform.interfaces.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import net.ewide.platform.common.persistence.DataEntity;

/**
 * 用户组Entity
 * @author TianChong
 * @version 2016年3月29日
 */
public class UserGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	private Office company;	// 归属公司
	private String name;// 组名
	private String code;//编号
	private List<User> userList = Lists.newArrayList(); // 拥有用户列表
	private List<Role> roleList = Lists.newArrayList(); // 拥有角色
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserGroup getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParent(UserGroup parent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getUserids() {
		return StringUtils.join(getUserIdList(), ",");
	}
	
	public void setUserids(String userids) {
		if(userids!=null && !userids.equals("")){
			if(userids.substring(0, 1).equals(",")){
				userids=userids.substring(1, userids.length()-1);
			}
		}
		userList = Lists.newArrayList();
		if (userids != null){
			String[] ids = StringUtils.split(userids, ",");
			setUserIdList(Lists.newArrayList(ids));
		}
	}
	public List<String> getUserIdList() {
		List<String> userIdList = Lists.newArrayList();
		for (User user : userList) {
			userIdList.add(user.getId());
		}
		return userIdList;
	}
	
	public void setUserIdList(List<String> userIdList) {
		userList = Lists.newArrayList();
		for (String roleId : userIdList) {
			User user = new User();
			user.setId(roleId);
			userList.add(user);
		}
	}
	
	public String getRoleids() {
		return StringUtils.join(getRoleIdList(), ",");
	}
	
	public void setRoleids(String roleids) {
		roleList = Lists.newArrayList();
		if (roleids != null){
			String[] ids = StringUtils.split(roleids, ",");
			setRoleIdList(Lists.newArrayList(ids));
		}
	}
	public List<String> getRoleIdList() {
		List<String> userIdList = Lists.newArrayList();
		for (Role role : roleList) {
			userIdList.add(role.getId());
		}
		return userIdList;
	}
	
	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	

	
}
