package net.ewide.platform.modules.syndata.event;

import net.ewide.platform.modules.enums.RedisEnum;

/**
 * 机构Event
 * @author TianChong
 * @version 2016年4月20日
 */
public class OfficeDataEvent extends BaseDataEvent {
	private static final long serialVersionUID = -4650119432623615887L;
	
	public OfficeDataEvent(Object source,RedisEnum redisEnum) {
		super(source);
		super.redisEnum=redisEnum;
	}
}
