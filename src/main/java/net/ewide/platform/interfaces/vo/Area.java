/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.interfaces.vo;


import java.io.Serializable;

import net.ewide.platform.common.persistence.TreeEntity;

/**
 * 区域Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Area implements Serializable{

	private static final long serialVersionUID = 1L;
//	private Area parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	private String code; 	// 区域编码
//	private String name; 	// 区域名称
//	private Integer sort;		// 排序
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
	private String typeName;
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}