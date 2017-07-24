package net.ewide.platform.modules.sys.entity;

import javax.validation.constraints.NotNull;

import net.ewide.platform.common.persistence.DataEntity;
/**
 * 职位
 * @author wangtao
 * @version 2016-3-28
 */
public class Position extends DataEntity<Position> {
	private static final long serialVersionUID = 10L;
	
	private String positionName;//职位名称
	private String positionNo;//职位编号
	private Office office;	// 归属机构
	
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Position() {
		super();
	}
	
	public Position(String id) {
		super(id);
	}
	
	@NotNull(message="职位名称不能为空")
	public String getPositionName() {
		return positionName;
	}
	
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	@NotNull(message="职位编号不能为空")
	public String getPositionNo() {
		return positionNo;
	}
	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

}
