package net.ewide.platform.interfaces.vo;

import java.io.Serializable;

import com.alibaba.druid.wall.violation.ErrorCode;


/**
 * @author wanghaozhe
 *
 */
public class ResponseVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int errcode;//错误码，0:成功，1:json数据格式不正确,2:业务系统代码不存在3:用户密码不正确
	private String errmsg;//错误信息
	private Data data;
	
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	
}
