package com.dascom.common.quartz;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 加载调度信息
 */
public class SuiteQuartzAutoLoad implements BeanPostProcessor {

    public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
    
    private String[] packages;
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!isMatchPackage(bean)) {
            return bean;
        }
        /**
         * 解析调度
         */
        SuiteQuartz quarz = bean.getClass().getAnnotation(SuiteQuartz.class);
        
        if (quarz == null) {
            return bean;
        }
        
        String jobName = quarz.jobName();
        String jobGroup = quarz.jobGroup();
        String desc = quarz.desc();
        if (StringUtils.isBlank(jobName)) {
            jobName = bean.getClass().getName();
        }
        
        Method[] methods = bean.getClass().getDeclaredMethods();
        Scheduler scheduler = SuiteQuartzSchedulerManager.getStartScheduler();
        // 删除掉之前的job和触发器
        // clearAllJobAndTrigger(scheduler);
        
        for (Method method : methods) {
            SuiteQuartzMethod suiteQuarzMethod = method.getAnnotation(SuiteQuartzMethod.class);
            if (suiteQuarzMethod == null || suiteQuarzMethod.muiltMethod() == null) {
                continue;
            }
            SuiteQuartzMethodAttr[] methodAttrs = suiteQuarzMethod.muiltMethod();
            for (int i=0; i < methodAttrs.length; i++) {
                addJobAndTrigger(scheduler, methodAttrs[i], method, bean, jobName, jobGroup, desc, i);
            }
            
        }
        return bean;
    }
    
    private void addJobAndTrigger(Scheduler scheduler, SuiteQuartzMethodAttr methodAttr, Method method, Object bean,
        String jobName, String jobGroup, String desc, int index) {
        /**
         * 触发器表达式
         */
        String corn = methodAttr.corn();
        if (StringUtils.isBlank(corn)) {
            return ;
        }
        
        // 添加job和trigger
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("targetClass", bean.getClass().getName());
        jobDataMap.put("targetMethod", method.getName());
        /**
         * 参数
         */
        String defaultName = jobName.concat(".").concat(method.getName()).concat(String.valueOf(index));
        String param = methodAttr.param();
        if (StringUtils.isNotBlank(param)) {
            jobDataMap.put("param", param);
        }
        JobDetail job = JobBuilder.newJob(SuiteQuartzCommonJob.class)
            .withIdentity(defaultName, jobGroup)
            .usingJobData(jobDataMap).withDescription(desc)
            .storeDurably().build();
        try {
            scheduler.addJob(job, true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        
        String triggerName = methodAttr.name();
        if (StringUtils.isBlank(triggerName)) {
            triggerName = defaultName;
        }
        String triggerGroup = methodAttr.group();
        
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(triggerName, triggerGroup)
            .withSchedule(CronScheduleBuilder.cronSchedule(methodAttr.corn()))
            .startNow()
            .forJob(job).withDescription(methodAttr.desc()).build();
        try {
            scheduler.unscheduleJob(new TriggerKey(triggerName, triggerGroup));
            scheduler.scheduleJob(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public void clearAllJobAndTrigger(Scheduler scheduler) {
        if (scheduler == null) {
            return;
        }
        List<TriggerKey> triggerKeys = SuiteQuartzSchedulerManager.getTriggers(scheduler);
        try {
            scheduler.unscheduleJobs(triggerKeys);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        
        List<JobKey> jobKeys = SuiteQuartzSchedulerManager.getJobs(scheduler);
        try {
            scheduler.deleteJobs(jobKeys);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void setPackage(String quarzPakage) {
        this.packages = (quarzPakage == null || quarzPakage.length() == 0) ? null : COMMA_SPLIT_PATTERN.split(quarzPakage);
    }

    private boolean isMatchPackage(Object bean) {
        if (packages == null || packages.length == 0) {
            return true;
        }
        String beanClassName = bean.getClass().getName();
        for (String pkg : packages) {
            if (beanClassName.startsWith(pkg)) {
                return true;
            }
        }
        return false;
    }
}
