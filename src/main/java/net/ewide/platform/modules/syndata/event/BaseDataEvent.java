package net.ewide.platform.modules.syndata.event;

import net.ewide.platform.modules.enums.RedisEnum;

import org.springframework.context.ApplicationEvent;

public class BaseDataEvent extends ApplicationEvent{
	private static final long serialVersionUID = -4388053738665372283L;
	
	protected String flag = "1";
	
	protected RedisEnum redisEnum;
	
	public BaseDataEvent(Object source) {
		super(source);
	}

	/**
	 * 
	 */
	
	public String getFlag(){
		return this.flag;
	}
	
	public RedisEnum getRedisEnum(){
		return this.redisEnum;
	}
}
