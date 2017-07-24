package net.ewide.platform.common.logdata.model;

import java.util.Date;

public class Log {
	/**
	 * 系统code
	 */
	private String sysCode;
	/**
	 * 菜单id
	 */
	private String menuId;
	/**
	 * 记录人
	 */
	private String taker;
	/**
	 * 记录时间
	 */
	private Date takerDate;
	/**
	 * 日志说明
	 */
	private String explain;
	/**
	 * 操作类型
	 */
	private String type;
	/**
	 * 操作前数据
	 */
	private String data;
	/**
	 * 操作后数据
	 */
	private String newData;
	/**
	 * 操作对象主键
	 */
	private String objectPk;

	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getTaker() {
		return taker;
	}
	public void setTaker(String taker) {
		this.taker = taker;
	}
	public Date getTakerDate() {
		return takerDate;
	}
	public void setTakerDate(Date takerDate) {
		this.takerDate = takerDate;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getNewData() {
		return newData;
	}
	public void setNewData(String newData) {
		this.newData = newData;
	}
	public String getObjectPk() {
		return objectPk;
	}
	public void setObjectPk(String objectPk) {
		this.objectPk = objectPk;
	}
}
