package net.ewide.platform.modules.sys.entity;

import net.ewide.platform.common.persistence.DataEntity;

/**
 * 用户机构Entity
 * @author TianChong
 * @version 2016年4月11日
 */
public class UserOffice  extends DataEntity<UserOffice>{
	private static final long serialVersionUID = 1L;
	private Office company;	// 归属公司
	private Office office; // 归属机构
//	private String isMain; // 是否主部门 1是 0否
	private User user; //用户ID
	private String primaryLevelCode; //层级码  机构主键层级码+用户ID+@+用户机构ID+.psm
	
	public Office getCompany() {
		return company;
	}



	public void setCompany(Office company) {
		this.company = company;
	}



	public Office getOffice() {
		return office;
	}



	public void setOffice(Office office) {
		this.office = office;
	}


	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public String getPrimaryLevelCode() {
		return primaryLevelCode;
	}



	public void setPrimaryLevelCode(String primaryLevelCode) {
		this.primaryLevelCode = primaryLevelCode;
	}




}
