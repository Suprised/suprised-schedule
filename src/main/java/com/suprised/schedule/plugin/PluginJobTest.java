package com.suprised.schedule.plugin;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PluginJobTest implements Job {

	private String jobName;
	private String jobGroup;
	// private static int count = 4;

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
	    try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		System.out.println(String.format("jobName:%s, jobGroup:%s", jobName, jobGroup));
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

}
