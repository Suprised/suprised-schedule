package com.suprised.schedule.listener;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.suprised.schedule.HelloJob;

public class ListenerTest {
	
	public static void main(String[] args) throws Exception {
		Scheduler scheduler = null;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			cornExecute(scheduler);
			while(true){
			}
		} catch (SchedulerException se) {
			se.printStackTrace();
			if (scheduler != null)
				scheduler.shutdown();
		}

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
				null).usingJobData("sayHello", "你好").usingJobData("name","job2").build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")
				.withSchedule(cronSchedule("0/5 * * * * ?")).forJob("job2").build(); //每五秒执行一次
		
		scheduler.scheduleJob(job, trigger);
		// add job listener
		scheduler.getListenerManager().addJobListener(new JobListenerImpl());
		// add trigger listener
		scheduler.getListenerManager().addTriggerListener(new TriggerListenerImpl());
		// add scheduler listener
		scheduler.getListenerManager().addSchedulerListener(new SchedulerListenerImpl());
		
		// 暂停
		scheduler.pauseTrigger(new TriggerKey("trigger3","group1"));
//		scheduler.getTrigger(new TriggerKey("trigger3", "group1"));
		
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 唤醒
		scheduler.resumeTrigger(new TriggerKey("trigger3","group1"));
		
		
//		for (int i=0; i<=5; i++) {
//			System.out.println(i + "立即执行。。。");
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("sayHello", "立即执行00" + i);
//			scheduler.triggerJob(new JobKey("job2"), new JobDataMap(params)); // 立即执行
//			System.out.println(i + "立即执行结束。。。");
//		}
	}
}
