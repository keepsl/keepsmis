package com.keeps.core.model.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.keeps.core.model.SoftEntityModel;
import com.keeps.core.model.plus.ModelPlusAdapter;
import com.keeps.core.model.plus.ModelPlusCloud;
import com.keeps.core.model.plus.ModelPlusCloudAdapter;
import com.keeps.core.model.plus.ModelPlusSchool;
import com.keeps.core.model.plus.ModelPlusSchoolAdapter;
import com.keeps.tools.utils.ArrayUtils;
import com.keeps.tools.utils.RandomUtils;

public class ModelUtil {
	protected final Log log = LogFactory.getLog(getClass());

	public static void randomUUID(SoftEntityModel model) {
		if (model.isSave()) {
			model.setId(RandomUtils.randomUUID());
		}
	}

	public static void modelPlusCreator(SoftEntityModel model, Boolean isSave) {
		ModelPlusAdapter adapter = null;
		if ((model instanceof ModelPlusSchool)) {
			adapter = ModelPlusSchoolAdapter.getInstance();
		} else if ((model instanceof ModelPlusCloud)) {
			adapter = ModelPlusCloudAdapter.getInstance();
		}
		if (adapter != null) {
			adapter.execute(model, isSave);
		}
	}

	public static String[] modelPlusFields(SoftEntityModel model, Boolean isSave, String[] fields) {
		if ((fields == null) || (fields.length <= 0)) {
			if ((model instanceof ModelPlusSchool)) {
				return ModelPlusSchoolAdapter.Fields;
			}
			if ((model instanceof ModelPlusCloud)) {
				return ModelPlusCloudAdapter.Fields;
			}
		} else {
			if ((model instanceof ModelPlusSchool)) {
				return (String[]) ArrayUtils.addObjectsToArray(fields, ModelPlusSchoolAdapter.Fields);
			}
			if ((model instanceof ModelPlusCloud)) {
				return (String[]) ArrayUtils.addObjectsToArray(fields, ModelPlusCloudAdapter.Fields);
			}
		}
		return fields;
	}
}
