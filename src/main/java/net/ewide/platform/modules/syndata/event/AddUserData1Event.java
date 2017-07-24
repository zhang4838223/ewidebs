package net.ewide.platform.modules.syndata.event;

public class AddUserData1Event extends AddUserDataEvent {

	
	private String flag = "2";
	
	
	public AddUserData1Event(Object source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4650119432623615887L;


	public String getFlag() {
		return flag;
	}

}
