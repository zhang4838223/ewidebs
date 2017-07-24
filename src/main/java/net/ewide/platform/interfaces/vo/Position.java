package net.ewide.platform.interfaces.vo;

import java.io.Serializable;

/**
 * 用户Entity
 * @author Jacky
 * @version 2016-3-28
 */
public class Position implements Serializable{
	private static final long serialVersionUID = 10L;
	
	private String positionName;//职位名称
	private String positionNo;//职位编号
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Position() {
		super();
	}
	
	public Position(String id) {
	}
	
	public String getPositionName() {
		return positionName;
	}
	
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	public String getPositionNo() {
		return positionNo;
	}
	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

}
