package net.ewide.platform.modules.sys.entity;

import java.io.Serializable;

public class OptionData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String text;
	private String value;
	public String getText() {
		return text;
	}
	public OptionData(String text, String value) {
		super();
		this.text = text;
		this.value = value;
	}
	public OptionData() {
		super();
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
