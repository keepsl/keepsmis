package com.keeps.core.hibernate;

import java.util.Map;
import java.util.TreeMap;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

public class AliasToEntityTreeMapResultTransformer extends AliasedTupleSubsetResultTransformer {
	public static final AliasToEntityTreeMapResultTransformer INSTANCE = new AliasToEntityTreeMapResultTransformer();

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map result = new TreeMap();
		for (int i = 0; i < tuple.length; i++) {
			String alias = aliases[i];
			if (alias != null) {
				result.put(alias.toUpperCase(), tuple[i]);
			}
		}
		return result;
	}

	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

	private Object readResolve() {
		return INSTANCE;
	}
}
