package com.keeps.core.hibernate;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

public class AliasToEntityHashMapResultTransformer extends AliasedTupleSubsetResultTransformer {
	public static final AliasToEntityHashMapResultTransformer INSTANCE = new AliasToEntityHashMapResultTransformer();

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map result = new HashMap(tuple.length);
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
