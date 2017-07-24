package net.ewide.platform.interfaces.dao;

import java.io.Serializable;

public class OfficeVo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String parentId;
	private String text;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
