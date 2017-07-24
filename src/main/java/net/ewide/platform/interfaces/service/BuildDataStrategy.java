package net.ewide.platform.interfaces.service;


import net.ewide.platform.interfaces.vo.ResponseVo;

public interface BuildDataStrategy {
	ResponseVo buildData(String  username,String systemCode);
}
