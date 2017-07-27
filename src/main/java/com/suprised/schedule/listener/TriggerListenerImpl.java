package com.suprised.schedule.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.Trigger.CompletedExecutionInstruction;

// 实现TriggerListener 或者extends TriggerListenerSupport
public class TriggerListenerImpl implements TriggerListener {

	@Override
	public String getName() {
		System.out.println("TriggerListenerImpl.getName");
		return "triggerListenerImpl";
	}

	@Override
	public void triggerComplete(Trigger arg0, JobExecutionContext arg1,
			CompletedExecutionInstruction arg2) {
		System.out.println("TriggerListenerImpl.triggerComplete");
	}

	@Override
	public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
		System.out.println("TriggerListenerImpl.triggerFired");
	}

	@Override
	public void triggerMisfired(Trigger arg0) {
		System.out.println("TriggerListenerImpl.triggerMisfired");
	}

	@Override
	public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
		return false;
	}

}
