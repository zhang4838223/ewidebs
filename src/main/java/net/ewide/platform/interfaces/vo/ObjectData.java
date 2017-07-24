package net.ewide.platform.interfaces.vo;

import java.util.Map;


public class ObjectData extends Data {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> objectMap;

	public Map<String, Object> getObjectMap() {
		return objectMap;
	}

	public void setObjectMap(Map<String, Object> objectMap) {
		this.objectMap = objectMap;
	}
}
