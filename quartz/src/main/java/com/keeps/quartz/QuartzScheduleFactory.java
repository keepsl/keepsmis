package com.keeps.quartz;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keeps.quartz.utils.CmdUtils;
import com.keeps.quartz.utils.InterfaceUtils;


/**
 * <p>Title: QuartzScheduleFactory.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Component
public class QuartzScheduleFactory {
	private Log log = LogFactory.getLog(QuartzScheduleFactory.class);
	private static final String UNIFORM_INTERFACE = "uniforminterface";
	private static final String CMD = "cmd";
	@Autowired
	private Scheduler scheduler;

	@PostConstruct
	public void execute() {
		Properties props = InterfaceUtils.init("requestinterface");
		Properties cmdProps = CmdUtils.init("requestcmd");
		if (!props.containsKey(UNIFORM_INTERFACE)) {
			log.error("缺少参数" + UNIFORM_INTERFACE);
			return;
		}
		for (Map.Entry<Object, Object> entry : cmdProps.entrySet()) {
			String key = entry.getKey().toString();
//			if (key.equals(UNIFORM_INTERFACE))
//				continue;
			if (StringUtils.isBlank(key) && !key.startsWith(CMD)) {
				log.error("无效的key值:" + key);
				return;
			}
			String value = (String) entry.getValue();
			String[] params = StringUtils.split(value, "_");
			if (params == null || params.length != 2) {
				log.error("无效的任务名:" + value);
				return;
			} else {
				JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
						.usingJobData("uniformInterface", props.get(UNIFORM_INTERFACE).toString())
						.usingJobData("paramKey", key.substring(0, 3)).usingJobData("paramValue", params[0]).build();
				CronTrigger cronTrigger = TriggerBuilder.newTrigger()
						.withSchedule(CronScheduleBuilder.cronSchedule(params[1])).build();
				try {
					scheduler = StdSchedulerFactory.getDefaultScheduler();
					scheduler.scheduleJob(jobDetail, cronTrigger);
					scheduler.start();
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@PreDestroy
	public void clear() {
		try {
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
