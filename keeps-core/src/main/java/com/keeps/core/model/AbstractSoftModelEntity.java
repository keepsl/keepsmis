package com.keeps.core.model;

import com.google.gson.Gson;
import com.keeps.core.model.utils.ModelUtil;
import com.keeps.tools.utils.PropertyUtils;

import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class AbstractSoftModelEntity<T> implements SoftEntityModel<T> {
	private String ids;
	private Integer page;
	private Integer rows;
	private String querynum;
	private String sidx;
	private String sord;
	private Boolean get = Boolean.valueOf(false);

	public Boolean isGet() {
		return this.get;
	}

	public Boolean getGet() {
		return this.get;
	}

	public void setGet(Boolean get) {
		this.get = get;
	}

	public Integer getRows() {
		return this.rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return this.page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getIds() {
		return this.ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public boolean isSave() {
		return getId() == null;
	}

	public void empty() {
		PropertyUtils.empty(this);
	}

	public void idhandle() {
		ModelUtil.randomUUID(this);
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public <T extends SoftEntityModel> T clone(String[] without) {
		try {
			SoftEntityModel t0 = (SoftEntityModel) getClass().newInstance();

			PropertyUtils.copyTo(this, t0, without);

			return (T) t0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String getQuerynum() {
		return this.querynum;
	}

	public void setQuerynum(String querynum) {
		this.querynum = querynum;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}
	
	
}
