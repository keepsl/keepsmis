package com.keeps.tools.utils.page;

import java.util.List;
import java.util.Map;

public interface Page {
	public static final Integer DEFAULT_PAGE_ROWS = Integer.valueOf(20);

	public abstract Integer getTotal();

	public abstract List getRecord();

	public abstract void setTotal(Integer paramInteger);

	public abstract void setRecord(List paramList);

	public abstract Integer getPageTotal();

	public abstract void setRows(Integer paramInteger);

	public abstract Integer getRows();

	public abstract void setPage(Integer paramInteger);

	public abstract Integer getPage();

	public abstract void setFirst(Integer paramInteger);

	public abstract Integer getFirst();

	public abstract String toJson();

	public abstract void setOtherProperty(Map paramMap);

	public abstract Map getOtherProperty();

	public abstract void setTitles(String[] paramArrayOfString);

	public abstract String[] getTitles();

	public abstract String getQuerynum();

	public abstract void setQuerynum(String paramString);

}
