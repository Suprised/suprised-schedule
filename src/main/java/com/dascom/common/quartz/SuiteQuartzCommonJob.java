package com.dascom.common.quartz;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.MethodInvoker;

/**
 * suite中调度通用的job
 */
public class SuiteQuartzCommonJob implements Job , Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            String targetClass = context.getMergedJobDataMap().getString("targetClass");
            Class<?> targetClassClass = null;
            if (targetClass != null) {
                targetClassClass = Class.forName(targetClass); 
            }
            String targetMethod = context.getMergedJobDataMap().getString("targetMethod");
            /**
             * 获取执行参数
             */
            String param = context.getMergedJobDataMap().getString("param");
            
            MethodInvoker methodInvoker = new MethodInvoker();
            methodInvoker.setTargetObject(targetClassClass.newInstance());
            methodInvoker.setTargetMethod(targetMethod);
            if (param != null) {
                try {
                    targetClassClass.getMethod(targetMethod, String.class);
                    methodInvoker.setArguments(new Object[]{ param });
                } catch (NoSuchMethodException nsme) {
                    // 忽略该异常
                }
            }
            methodInvoker.prepare();
            methodInvoker.invoke();
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobExecutionException(e);
            /**
             * TODO 之后处理: 一旦调度出现调度类或者方法不存在的报错，则可以在此处进行删除job和触发器处理，防止多次调用报错。
             */
        }
    }

}
