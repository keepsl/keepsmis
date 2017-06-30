package com.keeps.tools.utils.threadlocal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.keeps.tools.utils.UserSchool;

public class UserSchoolThread {
	private static Log log = LogFactory.getLog(UserSchoolThread.class);
	private static ThreadLocal<UserSchool> THREAD = new ThreadLocal();

	public static void set(UserSchool school) {
		if ((THREAD.get() != null) && (school != null)) {
			if ((THREAD.get() instanceof UserSchool)) {
				log.info("UserSchoolThread中set时出错，发现存在" + ((UserSchool) THREAD.get()).getUserid() + ":"
						+ ((UserSchool) THREAD.get()).getNickname());
			} else {
				log.error("UserSchoolThread中set时出错,所放的值属于未知类型");
			}
			THREAD.set(null);
			THREAD.remove();
		}
		THREAD.set(school);
	}

	public static UserSchool get() {
		return (UserSchool) THREAD.get();
	}

	public static void remove() {
		THREAD.set(null);
		THREAD.remove();
	}
}
