package com.suprised.schedule.listener;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

// 实现SchedulerListener  或者 extends SchedulerListenerSupport
public class SchedulerListenerImpl implements SchedulerListener {

	@Override
	public void jobScheduled(Trigger trigger) {

	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {

	}

	@Override
	public void triggerFinalized(Trigger trigger) {

	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		System.out.println("暂停：triggerPaused(triggerKey)");
	}

	@Override
	public void triggersPaused(String triggerGroup) {

	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		System.out.println("启动triggersResumed(triggerKey)");
	}

	@Override
	public void triggersResumed(String triggerGroup) {
		System.out.println("triggersResumed(String)");
	}

	@Override
	public void jobAdded(JobDetail jobDetail) {

	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobPaused(JobKey jobKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobsPaused(String jobGroup) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobResumed(JobKey jobKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobsResumed(String jobGroup) {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerInStandbyMode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerStarting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerShutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulerShuttingdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedulingDataCleared() {
		// TODO Auto-generated method stub

	}

}
