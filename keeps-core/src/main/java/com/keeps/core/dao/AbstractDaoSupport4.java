package com.keeps.core.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.PropertyUtils;
import com.keeps.tools.utils.StringUtils;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDaoSupport4 implements SoftDao {
	public Log log = LogFactory.getLog(getClass());

	public void applyQueryParameters(Query query, Object[] values) {
		if ((values == null) || (values.length <= 0)) {
			return;
		}
		if ((values != null) && (values.length > 0)) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
	}

	public void applyQueryNamedParameters(Query query, String[] params, Object[] values) {
		if ((params == null) || (values == null)) {
			return;
		}
		Assert.isTrue(params.length == values.length,
				"传参个数不对应para[" + params.length + "],values[" + values.length + "]", true);
		for (int i = 0; i < params.length; i++) {
			String paramName = params[i];
			Object value = values[i];
			if ((value instanceof Collection)) {
				query.setParameterList(paramName, (Collection) value);
			} else if ((value instanceof Object[])) {
				query.setParameterList(paramName, (Object[]) value);
			} else {
				query.setParameter(paramName, value);
			}
		}
	}

	public List converList2Model(Class clazz, List<Map> list) {
		if ((clazz == null) || (HashMap.class.equals(clazz)) || (Map.class.equals(clazz))) {
			return list;
		}
		List<Object> relst = new ArrayList(list.size());
		if ((list == null) || (list.isEmpty())) {
			return relst;
		}
		try {
			for (Map<String, Object> map : list) {
				Object t = clazz.newInstance();
				PropertyUtils.setPropertyType(t, map);
				relst.add(t);
			}
		} catch (CapecException e) {
		} catch (Exception e) {
			e.printStackTrace();
			this.log.info(e.getMessage());
		} finally {
			return relst;
		}
	}

	public List converList2SinglMap(List<Object[]> list) {
		Map map = new LinkedHashMap(list.size());
		for (Object[] o : list) {
			map.put(o[0], o[1]);
		}
		List<Map> l = new ArrayList(1);

		l.add(map);

		return l;
	}

	public List converResult2Sql(List l) {
		if ((l == null) || (l.isEmpty())) {
			return l;
		}
		boolean group = l.get(0) instanceof Object[];
		Object[] os;
		if (group) {
			for (int i = 0; i < l.size(); ++i) {
				os = (Object[]) l.get(i);
				for (Object o : os) {
					if (o instanceof Clob)
						o = StringUtils.ClobToString((Clob) o);
				}
			}
		} else {
			for (Object o : l) {
				if ((o instanceof Clob)) {
					o = StringUtils.ClobToString((Clob) o);
				}
			}
		}
		return l;
	}
}
