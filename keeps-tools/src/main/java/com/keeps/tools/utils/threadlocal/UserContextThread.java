package com.keeps.tools.utils.threadlocal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.keeps.tools.utils.UserContext;

public class UserContextThread {

	private static Log log = LogFactory.getLog(UserContextThread.class);
	private static ThreadLocal<UserContext> THREAD = new ThreadLocal();

	public static void set(UserContext school) {
		if ((THREAD.get() != null) && (school != null)) {
			if ((THREAD.get() instanceof UserContext)) {
				log.error("UserSchoolThread中set时出错，发现存在" + ((UserContext) THREAD.get()).getUserid() + ":"
						+ ((UserContext) THREAD.get()).getUsername());
			} else {
				log.error("UserSchoolThread中set时出错,但所放的值属于未知类型");
			}
			THREAD.set(null);
			THREAD.remove();
		}
		THREAD.set(school);
	}

	public static UserContext get() {
		return (UserContext) THREAD.get();
	}

	public static void remove() {
		THREAD.set(null);
		THREAD.remove();
	}
}
