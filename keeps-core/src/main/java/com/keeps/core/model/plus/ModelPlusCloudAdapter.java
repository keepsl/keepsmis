package com.keeps.core.model.plus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.UserContext;
import com.keeps.tools.utils.threadlocal.UserContextThread;

public class ModelPlusCloudAdapter implements ModelPlusAdapter {

	private static ModelPlusCloudAdapter adapter = new ModelPlusCloudAdapter();

	public static ModelPlusCloudAdapter getInstance() {
		return adapter;
	}

	public static String[] Fields = { "createdate", "modifydate", "creator", "siteid", "endpoint", "maps" };
	private Log log = LogFactory.getLog(getClass());

	public Object execute(Object model, Boolean isSave) {
		ModelPlusCloud school = (ModelPlusCloud) model;
		try {
			if (isSave.booleanValue()) {
				school.setCreatedate(DateUtils.parse(DateUtils.formatNow(), "yyyy-MM-dd HH:mm:ss"));
			}
			school.setModifyDate(DateUtils.parse(DateUtils.formatNow(), "yyyy-MM-dd HH:mm:ss"));

			UserContext us = UserContextThread.get();
			if (StringUtils.notText(school.getCreator())) {
				school.setCreator(us == null ? "nouserid" : us.getUserid());
			}
			if (StringUtils.notText(school.getSiteid())) {
				school.setSiteid(us == null ? "nositeid" : us.getSiteid());
			}
			if (StringUtils.notText(school.getEndpoint())) {
				school.setEndpoint(us == null ? "pc" : us.getEndpoint());
			}
			if (StringUtils.notText(school.getMaps())) {
				school.setMaps(us == null ? "nomaps" : us.getMaps());
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.log.info("放入ModelPlusCloudAdapter相关值时出错，请检查该对象是否应该去掉ModelPlusCloudAdapter实现。");
		}
		return school;
	}
}
