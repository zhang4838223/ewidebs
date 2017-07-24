/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import net.ewide.platform.common.persistence.DataEntity;

/**
 * 子系统管理Entity
 * @author wangtao
 * @version 2016-04-07
 */
public class Subsystem extends DataEntity<Subsystem> {
	
	private static final long serialVersionUID = 1L;
	private String systemCode;		// 子系统编号
	private String systemName;		// 子系统名称
	private String url;		// 外部链接地址
	private String createByName;//创建人名称
	private String updateByName;//修改人名称
	
	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

	public Subsystem() {
		super();
	}

	public Subsystem(String id){
		super(id);
	}

	@Length(min=0, max=50, message="子系统编号长度必须介于 0 和 50 之间")
	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	
	@Length(min=0, max=150, message="子系统名称长度必须介于 0 和 150 之间")
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	@Length(min=0, max=1000, message="外部链接地址长度必须介于 0 和 1000 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}