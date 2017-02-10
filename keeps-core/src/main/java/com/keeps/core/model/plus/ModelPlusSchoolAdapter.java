package com.keeps.core.model.plus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.UserSchool;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;

public class ModelPlusSchoolAdapter implements ModelPlusAdapter {
	private static ModelPlusSchoolAdapter adapter = new ModelPlusSchoolAdapter();

	public static ModelPlusSchoolAdapter getInstance() {
		return adapter;
	}

	public static String[] Fields = { "cjr", "cjsj", "ssdw", "whsj" };
	private Log log = LogFactory.getLog(getClass());

	public Object execute(Object model, Boolean isSave) {
		ModelPlusSchool school = (ModelPlusSchool) model;
		try {
			if (isSave.booleanValue()) {
				school.setCreatetime(DateUtils.parse(DateUtils.formatNow(), "yyyy-MM-dd HH:mm:ss"));
			}
			school.setUpdatetime(DateUtils.parse(DateUtils.formatNow(), "yyyy-MM-dd HH:mm:ss"));

			UserSchool us = UserSchoolThread.get();
			if (school.getCreateperson()==null) {
				school.setCreateperson(us == null ? 0 : us.getUserid());
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.log.info("放入ModelPlusSchoolAdapter相关值时出错，请检查该对象是否应该去掉ModelPlusSchoolAdapter实现。");
		}
		return school;
	}
}
