package com.keeps.tools.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SupcanTable {
	private String head;
	private String[] assistandHead;
	private String[] titles;
	private List<Object[]> contents;
	private String remark;
	private String jsonSpan;

	public String getJsonSpan() {
		return this.jsonSpan;
	}

	public void setJsonSpan(String jsonSpan) {
		this.jsonSpan = jsonSpan;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String[] getTitles() {
		return this.titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public List<Object[]> getContents() {
		return this.contents;
	}

	public void setContents(List<Object[]> contents) {
		this.contents = contents;
	}

	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String[] getAssistandHead() {
		return this.assistandHead;
	}

	public void setAssistandHead(String[] assistandHead) {
		this.assistandHead = assistandHead;
	}

	public static SupcanTable initOnlyData(ResultSet rs) {
		SupcanTable supcan = new SupcanTable();
		try {
			ResultSetMetaData md = rs.getMetaData();

			int colcount = md.getColumnCount();

			List<Object[]> list = new ArrayList(colcount);
			while (rs.next()) {
				List<Object> contentList = new ArrayList(colcount);
				for (int i = 1; i <= colcount; i++) {
					contentList.add(rs.getObject(i));
				}
				list.add(contentList.toArray(new Object[contentList.size()]));
			}
			supcan.setContents(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supcan;
	}

	public static SupcanTable init(ResultSet rs) {
		SupcanTable supcan = new SupcanTable();
		try {
			ResultSetMetaData md = rs.getMetaData();

			int colcount = md.getColumnCount();
			List<String> titlesList = new ArrayList(colcount);
			for (int c = 1; c <= colcount; c++) {
				titlesList.add(md.getColumnName(c).toUpperCase());
			}
			supcan.setTitles((String[]) titlesList.toArray(new String[titlesList.size()]));

			List<Object[]> list = new ArrayList(colcount);
			while (rs.next()) {
				List<Object> contentList = new ArrayList(colcount);
				for (String title : titlesList) {
					contentList.add(rs.getString(title));
				}
				list.add(contentList.toArray(new Object[contentList.size()]));
			}
			supcan.setContents(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supcan;
	}

	public static SupcanTable init(List<TreeMap> list) {
		SupcanTable supcan = new SupcanTable();
		try {
			if ((list == null) || (list.isEmpty())) {
				return supcan;
			}
			Map t = (Map) list.get(0);

			Set<String> tset = t.keySet();
			supcan.setTitles((String[]) tset.toArray(new String[tset.size()]));

			List<Object[]> values = new ArrayList(list.size());
			for (Map m : list) {
				values.add(m.entrySet().toArray());
			}
			supcan.setContents(values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supcan;
	}
}
