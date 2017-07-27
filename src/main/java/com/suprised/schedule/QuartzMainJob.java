package com.suprised.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * worker
 * 
 * @author AmyChen
 * 
 */
public class QuartzMainJob implements Job {

	private String name; // 工作名称
	private String operate;// 操作

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println(String.format("功能：[%s]，正在执行：[%s]操作", name, operate));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

}
