package net.ewide.platform.interfaces.vo;

/**
 * @author wanghaozhe
 *
 */
public enum ErrorCodeEnum {
	SUCCESS(0,"成功"),
	PARAM_NULL_FAIL(1,"关键参数为空"),
	SYSCODE_NOT_EXIST(2,"业务系统代码不存在"),
	USERNAME_PASSWORD_ERROR(3,"用户密码不正确");
	
	private int code;
	private String explain;
	
	private ErrorCodeEnum(int code, String explain) {
		this.code = code;
		this.explain = explain;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
}
