/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.interfaces.vo;

import java.io.Serializable;
import java.util.List;



/**
 * 机构Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Office implements Serializable{

	private static final long serialVersionUID = 1L;
//	private Office parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	
	private Area area;		// 归属区域
	private String code; 	// 机构编码
	private String name; 	// 机构名称
//	private Integer sort;		// 排序
	private String type; 	// 机构类型（1：公司；2：部门；3：小组）
	private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
	private String address; // 联系地址
	private String zipCode; // 邮政编码
	private String master; 	// 负责人
	private String phone; 	// 电话
	private String fax; 	// 传真
	private String email; 	// 邮箱
	private String useable;//是否可用
	private User primaryPerson;//主负责人
	private User deputyPerson;//副负责人
	private String levelCode;//层级码
	private String primaryLevelCode;//主键层级码
	private List<String> childDeptList;//快速添加子部门
	
	public Office(){
		super();
//		this.sort = 30;
		this.type = "2";
	}

	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getChildDeptList() {
		return childDeptList;
	}

	public void setChildDeptList(List<String> childDeptList) {
		this.childDeptList = childDeptList;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public User getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(User primaryPerson) {
		this.primaryPerson = primaryPerson;
	}

	public User getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(User deputyPerson) {
		this.deputyPerson = deputyPerson;
	}


	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getPrimaryLevelCode() {
		return primaryLevelCode;
	}

	public void setPrimaryLevelCode(String primaryLevelCode) {
		this.primaryLevelCode = primaryLevelCode;
	}
}