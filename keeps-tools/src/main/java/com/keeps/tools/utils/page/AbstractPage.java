package com.keeps.tools.utils.page;

import java.util.Map;

import com.google.gson.Gson;

public abstract class AbstractPage implements Page {
	private Integer total;
	private Integer pageTotal;
	private Integer rows;
	private Integer page;
	private Map otherProperty;
	private Integer first;
	private String[] titles;
	private String querynum;

	public Integer getTotal() {
		return this.total;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getFirst() {
		if (this.first == null) {
			return Integer.valueOf((getPage().intValue() - 1) * getRows().intValue());
		}
		return this.first;
	}

	public Integer getRows() {
		if (this.rows == null) {
			this.rows = Page.DEFAULT_PAGE_ROWS;
		}
		return this.rows;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String toJson() {
		Gson g = new Gson();
		return g.toJson(this);
	}

	public Map getOtherProperty() {
		return this.otherProperty;
	}

	public void setOtherProperty(Map map) {
		this.otherProperty = map;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public Integer getPageTotal() {
		if (getTotal().intValue() % this.rows.intValue() == 0) {
			this.pageTotal = Integer.valueOf(getTotal().intValue() / this.rows.intValue());
		} else {
			this.pageTotal = Integer.valueOf(getTotal().intValue() / this.rows.intValue() + 1);
		}
		return this.pageTotal;
	}

	public void setPage(Integer pageNo) {
		this.page = pageNo;
	}

	public Integer getPage() {
		if ((this.page == null) || (this.page.intValue() < 1)) {
			this.page = Integer.valueOf(1);
		}
		return this.page;
	}

	public void setRows(Integer length) {
		this.rows = length;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public String[] getTitles() {
		return this.titles;
	}

	public String getQuerynum() {
		return this.querynum;
	}

	public void setQuerynum(String querynum) {
		this.querynum = querynum;
	}
}
