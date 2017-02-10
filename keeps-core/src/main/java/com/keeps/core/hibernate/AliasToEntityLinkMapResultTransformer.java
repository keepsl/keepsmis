package com.keeps.core.hibernate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

public class AliasToEntityLinkMapResultTransformer extends AliasedTupleSubsetResultTransformer {
	public static final AliasToEntityLinkMapResultTransformer INSTANCE = new AliasToEntityLinkMapResultTransformer();

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map result = new LinkedHashMap(tuple.length);
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
