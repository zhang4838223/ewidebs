package net.ewide.platform.modules.syndata.event;

import net.ewide.platform.modules.enums.RedisEnum;

/**
 * 用户Event
 * @author TianChong
 * @version 2016年4月18日
 */
public class RoleDataEvent extends BaseDataEvent {
	private static final long serialVersionUID = -4650119432623615887L;
	
	public RoleDataEvent(Object source,RedisEnum redisEnum) {
		super(source);
		super.redisEnum=redisEnum;
	}
}
