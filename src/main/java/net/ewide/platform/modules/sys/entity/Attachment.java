package net.ewide.platform.modules.sys.entity;

import net.ewide.platform.common.persistence.DataEntity;

/**
 * 附件文件Entity
 * @author TianChong
 * @version 2016年5月20日
 */
public class Attachment extends DataEntity<Attachment>{
	private static final long serialVersionUID = 1L;
	
	private String name;//文件名称
	private String path;//文件路径或文件服务器唯一标示
	private String type;//文件类型
	private Integer fSize;//文件大小字节
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getfSize() {
		return fSize;
	}
	public void setfSize(Integer fSize) {
		this.fSize = fSize;
	}
	
}
