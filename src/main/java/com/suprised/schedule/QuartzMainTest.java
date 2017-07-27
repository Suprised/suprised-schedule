package com.suprised.schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

public class QuartzMainTest {

	private static Scheduler scheduler;
	private static final String DEFAULT_GROUP = "default";

	public static void main(String[] args) throws Exception {
	    QuartzMainTest test = new QuartzMainTest();
	    TriggerKey key = new TriggerKey("my-trigger","MYTRIGGER_GROUP");
	    Trigger trigger = test.getStartScheduler().getTrigger(key);
	    // 暂停触发器
	    // test.getStartScheduler().pauseTrigger(key);
	    
	    // 启动触发器
	    /*JobDetail jobDetail = test.getStartScheduler().getJobDetail(trigger.getJobKey());
	    Set<Trigger> triggers = new HashSet<Trigger>();
	    triggers.add(trigger);
	    test.getStartScheduler().scheduleJob(jobDetail, triggers, true);*/
	    
	    // 立即启动
	    test.getStartScheduler().triggerJob(trigger.getJobKey(), trigger.getJobDataMap());
	    
	    
		/*QuartzMainTest quartzTest = new QuartzMainTest();
		quartzTest.scheduleJob();

		quartzTest.storeJob();// 储存一个Job
		quartzTest.triggerStoreJob();// 触发储存的job

		quartzTest.getJobs();// 获得所有的job

		quartzTest.addOldTrigger();// 添加一个旧的出发器

		Thread.sleep(3000);

		System.out.println("update trigger1....");
		quartzTest.updateTrigger(); // 更新触发器

		Thread.sleep(3000);
		System.out.println("update trigger2....");
		quartzTest.updateExitsTrigger();// 更新触发器
*/		while (true) {
		}
	}

	/**
	 * 获得一个已经开始调度器
	 * 
	 * @throws SchedulerException
	 */
	public synchronized Scheduler getStartScheduler() throws SchedulerException {
		if (scheduler == null) {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			// StdSchedulerFactory factory = new StdSchedulerFactory();
			// factory.initialize(new Properties()); //属性配置文件
			// factory.initialize(""); //属性文件名称
			// scheduler = factory.getScheduler();
		}
		return scheduler;
	}

	/**
	 * 关闭调度器
	 */
	public void shutdownScheduler() {
		try {
			getStartScheduler().shutdown(); // 等于false，立即关闭调度器，不等待正在执行的任务
			getStartScheduler().shutdown(true);// 等待正在执行的任务完成后关闭
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建一个工作任务 (定义一个job)
	 * 
	 * @param name
	 *            任务名称
	 * @param group
	 *            任务所在分组
	 * @param jobClass
	 *            任务类
	 * @return
	 */
	public JobDetail getJobDetail(String name, String group,
			Class<? extends Job> jobClass) {
		JobDetail jobDetail = JobBuilder.newJob(jobClass)
				.withIdentity(name, group).usingJobData("name", "默认任务")
				.usingJobData("operate", "默认操作").build();
		return jobDetail;
	}

	/**
	 * 创建一个触发器(定义一个触发器)
	 * 
	 * @param name
	 * @param group
	 * @return
	 */
	public Trigger getTrigger(String name, String group) {
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
				.build();
		return trigger;
	}

	/**
	 * 调度一个job
	 */
	public void scheduleJob() throws SchedulerException {
		// Define job instance
		JobDetail job = getJobDetail("default", DEFAULT_GROUP,
				QuartzMainJob.class);

		// 创建一个触发器，立即执行，不重复出发。
		Trigger trigger = getTrigger("default", DEFAULT_GROUP);
		trigger.getTriggerBuilder().startNow();// 立即触发

		// Schedule the job with the trigger
		getStartScheduler().scheduleJob(job, trigger);
	}

	/**
	 * 取消一个触发器对job的调度
	 * 
	 * @throws SchedulerException
	 */
	public void unScheduleJob() throws SchedulerException {
		getStartScheduler().unscheduleJob(
				TriggerKey.triggerKey("default", DEFAULT_GROUP));
	}

	/**
	 * 删除一个job，同时删掉了所有对这个job的触发器
	 * 
	 * @throws SchedulerException
	 */
	public void deleteJob() throws SchedulerException {
		getStartScheduler().deleteJob(JobKey.jobKey("default", DEFAULT_GROUP));
	}

	/**
	 * 存储一个job的调度器之后使用（没有指定一个触发器）
	 * 
	 * @throws SchedulerException
	 */
	public JobKey storeJob() throws SchedulerException {
		JobDetail job = JobBuilder.newJob(QuartzMainJob.class)
				.withIdentity("later", "later")
				.usingJobData("operate", "执行延迟job操作")
				.usingJobData("name", "延迟使用的job").storeDurably().build();

		getStartScheduler().addJob(job, true); // false:不替换已经存在的 true：更新已经存在的job
		return new JobKey("later", "later");
	}

	/**
	 * 触发一个已经存在调度器中的job
	 * 
	 * @throws SchedulerException
	 */
	public void triggerStoreJob() throws SchedulerException {
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("later", null).startNow()
				.forJob(JobKey.jobKey("later", "later")).usingJobData("operate", "触发器的参数")
				.usingJobData("name","触发器传的参数").build();
		// 触发器触发job，如果不重复触发，则会删除掉触发器
		getStartScheduler().scheduleJob(trigger);
	}

	public void addOldTrigger() throws SchedulerException {
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("oldTrigger", "group1")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(1).repeatForever())
				.startNow().forJob(JobKey.jobKey("later", "later")).build();
		getStartScheduler().scheduleJob(trigger);
	}

	/**
	 * 更新触发器
	 */
	public void updateTrigger() throws SchedulerException {
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("updateTrigger1", DEFAULT_GROUP)
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(5).repeatForever())
				.startNow().build();

		getStartScheduler().rescheduleJob(
				TriggerKey.triggerKey("oldTrigger", "group1"), trigger);
		// getStartScheduler().scheduleJob(trigger);
	}

	/**
	 * 更新一个已经存在的触发器
	 * 
	 * @throws SchedulerException
	 */
	public void updateExitsTrigger() throws SchedulerException {
		Trigger oldTrigger = getStartScheduler().getTrigger(
				TriggerKey.triggerKey("updateTrigger1", DEFAULT_GROUP));

		if (oldTrigger != null) {
			Trigger newTrigger = TriggerBuilder
					.newTrigger()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(10) // 十秒执行一次
									.withRepeatCount(10)) // 执行十次
					.withIdentity("updateTrigger2", DEFAULT_GROUP).build();

			getStartScheduler().rescheduleJob(oldTrigger.getKey(), newTrigger);
			// getStartScheduler().scheduleJob(newTrigger);
		}
	}

	/**
	 * 获得所有的job
	 * 
	 * @return
	 */
	public List<JobKey> getJobs() throws SchedulerException {
		List<JobKey> results = new ArrayList<>();
		Scheduler sched = getStartScheduler();
		for (String group : sched.getJobGroupNames()) {
			for (JobKey jobKey : sched.getJobKeys(GroupMatcher
					.jobGroupEquals(group))) {
				System.out.println("Found job identified by: " + jobKey);
				results.add(jobKey);
			}
		}
		return results;
	}

	/**
	 * 获得所有的触发器
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<TriggerKey> getTriggers() throws SchedulerException {
		List<TriggerKey> triggerKeys = new ArrayList<>();
		Scheduler sched = getStartScheduler();
		for (String group : sched.getTriggerGroupNames()) {
			for (TriggerKey triggerKey : sched.getTriggerKeys(GroupMatcher
					.triggerGroupEquals(group))) {
				triggerKeys.add(triggerKey);
//				System.out
//						.println("Found trigger identified by: " + triggerKey);
			}
		}
		return triggerKeys;
	}

	/**
	 * 根据job，找到该job的所有触发器
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<? extends Trigger> getTriggersOfJob() throws SchedulerException {
		List<? extends Trigger> jobTriggers = getStartScheduler()
				.getTriggersOfJob(JobKey.jobKey("default", DEFAULT_GROUP));
		return jobTriggers;
	}

	// 以下是触发器的时间设置
	@SuppressWarnings("unused")
	public void triggerSetting() throws SchedulerException {
		// Using SimpleTrigger
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("timeSetting", "group1")
				.startNow()
				.withSchedule( // 每10秒执行一次，无线循环
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(10).repeatForever())
				.build();

		// Using SimpleTrigger
		trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger3", "group1")
				.startNow()
				.withSchedule( // 每90分钟执行一次，无线循环
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInMinutes(90).repeatForever())
				.build();

		// Using CalendarIntervalTrigger
		trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger3", "group1")
				.startNow()
				.withSchedule(// 每90分钟执行一次，无线循环
						CalendarIntervalScheduleBuilder
								.calendarIntervalSchedule()
								.withIntervalInMinutes(90)).build();

		// Using CronTrigger
		trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger3", "group1").startNow()
				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(15, 0)) // 每天15：00触发执行
				.build();

		// Using SimpleTrigger
		trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger3", "group1")
				.startAt(DateBuilder.tomorrowAt(15, 0, 0))
				// 每天15：00触发执行,如果每两天执行一次：则设置withIntervalInHours(2*24)
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInHours(24).repeatForever())
				.build();
		// Using CalendarIntervalTrigger
		trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger3", "group1")
				.startAt(DateBuilder.tomorrowAt(15, 0, 0))
				// 每天15：00触发执行，如果每两天执行一次：则设置withIntervalInDays(2)
				.withSchedule(
						CalendarIntervalScheduleBuilder
								.calendarIntervalSchedule().withIntervalInDays(
										1)).build();

		// Using CronTrigger
		// 每周五15：30执行
		trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger3", "group1")
				.startNow()
				.withSchedule(
						CronScheduleBuilder.weeklyOnDayAndHourAndMinute(
								DateBuilder.WEDNESDAY, 15, 0)).build();
		// 或者
		trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger3", "group1")
				.startNow()
				.withSchedule(
						CronScheduleBuilder.cronSchedule("0 0 15 ? * WED"))
				.build();

		// Using CronTrigger 每个月第五天下午三点执行
		trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger3", "group1").startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 15 5 * ?"))
				.build();
	}
}
