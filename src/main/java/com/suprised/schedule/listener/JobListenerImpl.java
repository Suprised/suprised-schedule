package com.suprised.schedule.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

// 实现JobListener 或者extends JobListenerSupport
public class JobListenerImpl implements JobListener {

	@Override
	public String getName() {
		System.out.println("JobListenerImpl.getName");
		return "JobListenerImpl.jobListnerImpl";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		System.out.println("JobListenerImpl.jobExecutionVetoed");
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext arg0) {
		System.out.println("JobListenerImpl.jobToBeExecuted");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext arg0,
			JobExecutionException arg1) {
		System.out.println("JobListenerImpl.jobWasExecuted");
		System.out.println("下次执行时间：" + arg0.getNextFireTime());
	}

}
