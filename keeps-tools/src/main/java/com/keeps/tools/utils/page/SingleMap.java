package com.keeps.tools.utils.page;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingleMap {
	private Map<Object, Object> map;

	public SingleMap() {
	}

	public SingleMap(Map map) {
		this.map = map;
	}

	public String toJson() {
		if (this.map == null) {
			return "";
		}
		Gson g = new Gson();
		List m = new ArrayList();
		m.add(this.map);
		return g.toJson(m);
	}

	public String toSelect(Boolean initNull) {
		if (this.map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=''></option>");
		for (Map.Entry<Object, Object> m : this.map.entrySet()) {
			sb.append("<option value='" + m.getKey() + "'>" + m.getValue() + "</option>");
		}
		return sb.toString();
	}
}
