package com.dascom.common.quartz;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/**
 * 获取Scheduler
 */
public class SuiteQuartzSchedulerManager {

    private static Scheduler scheduler;

    /**
     * 获取调度器
     */
    public static synchronized Scheduler getStartScheduler() {
        if (scheduler == null) {
            try {
                scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
            } catch (SchedulerException e) {
                e.printStackTrace();
                throw new RuntimeException("初始化调度器失败：" + e.getMessage());
            }
        }
        return scheduler;
    }

    /**
     * 获得所有的job
     * 
     */
    public static List<JobKey> getJobs(Scheduler scheduler) {
        List<JobKey> results = new ArrayList<>();
        if (scheduler == null) {
            return results;
        }
        try {
            Scheduler sched = getStartScheduler();
            for (String group : sched.getJobGroupNames()) {
                for (JobKey jobKey : sched.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
                    results.add(jobKey);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 获得所有的触发器
     * 
     * @return
     * @throws SchedulerException
     */
    public static List<TriggerKey> getTriggers(Scheduler scheduler) {
        List<TriggerKey> triggerKeys = new ArrayList<>();
        if (scheduler == null) {
            return triggerKeys;
        }
        try {
            for (String group : scheduler.getTriggerGroupNames()) {
                for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(group))) {
                    triggerKeys.add(triggerKey);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return triggerKeys;
    }
    
}
