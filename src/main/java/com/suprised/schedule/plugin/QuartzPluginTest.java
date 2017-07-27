package com.suprised.schedule.plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;

import com.suprised.schedule.QuartzMainTest;
//import com.suprised.utils.Threads;

public class QuartzPluginTest {

	public static void main(String[] args) throws Exception {
		QuartzMainTest quartzTest = new QuartzMainTest();
//		quartzTest.scheduleJob();
		Scheduler scheduler = quartzTest.getStartScheduler();
		List<TriggerKey> triggerKeys = quartzTest.getTriggers();
		TriggerKey starting = null;
		if (triggerKeys != null) {
			System.out.println("触发器名称\t所在组\t上次执行时间\t下次执行时间");
			for (TriggerKey triggerKey : triggerKeys) {
				Trigger trigger = scheduler.getTrigger(triggerKey);
				String preTime = trigger.getPreviousFireTime() != null ? DateFormatUtils.format(trigger.getPreviousFireTime(), "yyyy-MM-dd hh:mm:ss") : "";
				String nextTime = trigger.getNextFireTime() != null ? DateFormatUtils.format(trigger.getNextFireTime(), "yyyy-MM-dd hh:mm:ss") : "";
				System.out.println(String.format("%s\t%s\t%s\t%s\t", 
								triggerKey.getName(), 
								triggerKey.getGroup(), 
								preTime, nextTime));
				if ("my-trigger3".equals(triggerKey.getName())) {
					starting = triggerKey;//立即执行
				}
			}
		}
		if (starting != null) {
			System.out.println("立即执行BEGIN。。。");
			Trigger trigger = scheduler.getTrigger(starting);
			JobDetail job = scheduler.getJobDetail(trigger.getJobKey());
			JobDataMap map = new JobDataMap();
			map.put("jobName", "立即执行");
			map.put("jobGroup", "立即执行");
//			scheduler.getTrigger(starting).getJobDataMap().put("", "");
			scheduler.triggerJob(job.getKey(), map);//scheduleJob(job, trigger);
			Thread.sleep(1000);
			System.out.println("立即执行END。。。");
		}
		
		while(true) {
		    printTriggerInfo();
		    Thread.sleep(6000);//每隔一分钟打印一次状态
		}
	}
	
	public static void printTriggerInfo() throws Exception {
		QuartzMainTest quartzTest = new QuartzMainTest();
		Scheduler scheduler = quartzTest.getStartScheduler();
		/*List<JobKey> jobKeys = quartzTest.getJobs();
		if (jobKeys != null) {
		    System.out.println("任务名称\t\t所在组\t\t上次执行时间\t\t下次执行时间");
		    for (JobKey jobKey : jobKeys) {
		        JobDetail job = scheduler.getJobDetail(jobKey);
		        System.out.println("");
		    }
		}*/
		List<TriggerKey> triggerKeys = quartzTest.getTriggers();
		if (triggerKeys != null) {
			System.out.println("状态\t任务名称\t\t任务组\t\t触发器名称\t\t所在组\t\t上次执行时间\t\t下次执行时间");
			for (TriggerKey triggerKey : triggerKeys) {
				Trigger trigger = scheduler.getTrigger(triggerKey);
				TriggerState state = scheduler.getTriggerState(triggerKey);
				JobKey jobKey = trigger.getJobKey();
				String preTime = trigger.getPreviousFireTime() != null ? DateFormatUtils.format(trigger.getPreviousFireTime(), "yyyy-MM-dd hh:mm:ss") : "pre还未执行";
				String nextTime = trigger.getNextFireTime() != null ? DateFormatUtils.format(trigger.getNextFireTime(), "yyyy-MM-dd hh:mm:ss") : "next还未执行";
				System.out.println(String.format("%s\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t",
				                state.toString(),
				                jobKey.getName(),
				                jobKey.getGroup(),
								triggerKey.getName(), 
								triggerKey.getGroup(), 
								preTime, nextTime));
			}
		}
	}
	
	// 暂停，启动，立即启动
	public void otherUse() throws Exception {
	    QuartzMainTest test = new QuartzMainTest();
        TriggerKey key = new TriggerKey("my-trigger","MYTRIGGER_GROUP");
        Trigger trigger = test.getStartScheduler().getTrigger(key);
        
        // 暂停触发器
        // test.getStartScheduler().pauseTrigger(key);
        
        // 启动触发器
        /*
        JobDetail jobDetail = test.getStartScheduler().getJobDetail(trigger.getJobKey());
        Set<Trigger> triggers = new HashSet<Trigger>();
        triggers.add(trigger);
        test.getStartScheduler().scheduleJob(jobDetail, triggers, true);*/
        
        // 立即启动
        test.getStartScheduler().triggerJob(trigger.getJobKey(), trigger.getJobDataMap());
        printTriggerInfo();
	}
	
}
