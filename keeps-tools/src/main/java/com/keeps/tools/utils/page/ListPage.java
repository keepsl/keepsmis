package com.keeps.tools.utils.page;

import java.util.List;

public class ListPage extends AbstractPage{
	private static final long serialVersionUID = -627961760017218614L;
	private List record;

	public List getRecord() {
		return this.record;
	}

	public void setRecord(List record) {
		this.record = record;
	}
}
