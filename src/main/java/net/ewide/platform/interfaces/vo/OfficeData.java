package net.ewide.platform.interfaces.vo;

import java.util.List;

import com.google.common.collect.Lists;

public class OfficeData extends Data {
	private static final long serialVersionUID = 1L;
	private List<Office> office = Lists.newArrayList();
	public List<Office> getOffice() {
		return office;
	}
	public void setOffice(List<Office> office) {
		this.office = office;
	}
}
