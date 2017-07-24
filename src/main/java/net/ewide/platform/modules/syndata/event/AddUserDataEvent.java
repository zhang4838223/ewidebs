package net.ewide.platform.modules.syndata.event;

import org.springframework.context.ApplicationEvent;

public class AddUserDataEvent extends ApplicationEvent{

	
	protected String flag = "";
	
	public AddUserDataEvent(Object source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4388053738665372283L;
	
	
	public String getFlag(){
		return this.flag;
	}
}
