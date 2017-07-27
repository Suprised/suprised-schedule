package com.suprised.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.*;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

public class QuartzTest {

	public static void main(String[] args) throws Exception {
		Scheduler scheduler = null;
		try {
			// Grab the Scheduler instance from the Factory
			scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();
			execute1(scheduler);
			//execute2(scheduler);
//			cornExecute(scheduler);
			System.out.println(scheduler.isShutdown());
			while(true){
			}
		} catch (SchedulerException se) {
			se.printStackTrace();
			if (scheduler != null)
				scheduler.shutdown();
		}

	}

	public static final void execute1(Scheduler scheduler)
			throws SchedulerException {
		// define the job and tie it to our HelloJob class
		JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1",
				"group1").usingJobData("sayHello", "你好").usingJobData("name",
				"job1").build();

		// 每隔一秒重复执行一次
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1",
				"group1").startNow().withSchedule(
				SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1)
						.repeatForever()).build();

		// 立即开始执行
		// Trigger trigger =
		// TriggerBuilder.newTrigger().withIdentity("trigger1",
		// "group1").startAt(new Date()).build();

		// Tell quartz to schedule the job using our trigger
		scheduler.scheduleJob(job, trigger);
		
		try {
			Thread.sleep(5000);
			scheduler.shutdown();
			System.out.println("shutdow()");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static final void execute2(Scheduler scheduler)
			throws SchedulerException {
		HolidayCalendar cal = new HolidayCalendar();
		cal.addExcludedDate(new Date());
		cal.addExcludedDate(new Date());

//		scheduler.addCalendar("myHolidays", cal, false, false);
		
		JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job2",
			null).usingJobData("sayHello", "你好").usingJobData("name",
			"job2").build();
		Trigger t = TriggerBuilder.newTrigger().withIdentity("myTrigger").forJob("job2")
				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(19, 53)) // 每天9：30执行
//				.modifiedByCalendar("myHolidays") // but not on holidays
				.build();
		scheduler.scheduleJob(job, t);

		JobDetail job2 = JobBuilder.newJob(HelloJob.class).withIdentity("job3",
				null).usingJobData("sayHello", "你好").usingJobData("name",
				"job2").build();
		Trigger t2 = TriggerBuilder.newTrigger().withIdentity("myTrigger2").forJob("job3")
				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(19, 54)) // 每天11:30执行
//				.modifiedByCalendar("myHolidays") // but not on holidays
				.build();
		scheduler.scheduleJob(job2, t2);
	}
	
	public static final void cornExecute(Scheduler scheduler) throws SchedulerException {
//		Seconds
//		Minutes
//		Hours
//		Day-of-Month
//		Month
//		Day-of-Week
//		Year (optional field)
		JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job2",
				null).usingJobData("sayHello", "你好").usingJobData("name",
				"job2").build();
		Trigger trigger = TriggerBuilder.newTrigger()
	    					.withIdentity("trigger3", "group1")
	    .withSchedule(cronSchedule("0/5 * * * * ?")).forJob("job2").build(); //每五秒执行一次
		
		scheduler.scheduleJob(job, trigger);
		
		for (int i=0; i<=5; i++) {
			System.out.println(i + "立即执行。。。");
			Map<String, String> params = new HashMap<String, String>();
			params.put("sayHello", "立即执行00" + i);
			scheduler.triggerJob(new JobKey("job2"), new JobDataMap(params)); // 立即执行
			System.out.println(i + "立即执行结束。。。");
		}
	}
}
