package com.suprised.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {

	// 可以自动注入属性
	private String sayHello;
	private String name;

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
//		JobDataMap map = context.getJobDetail().getJobDataMap();
		
//		JobDataMap dataMap = context.getMergedJobDataMap(); 
		
		System.out.println("helloJob.execute();" + sayHello + ":" + name);
	}

	public String getSayHello() {
		return sayHello;
	}

	public void setSayHello(String sayHello) {
		this.sayHello = sayHello;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
