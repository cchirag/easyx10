package edu.bu.easyx10.device.timer;

import edu.bu.easyx10.event.Event;
import java.sql.Time;

public class TriggerTimer extends DeviceTimer{

	
	private Time mTriggerTime;
	
	public Time getTriggerTime() {
		return mTriggerTime;
	}

	public void setTriggerTime(Time triggerTime) {
		mTriggerTime = triggerTime;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Event getEventToFire() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEventToFire(Event eventToFire) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void startTimer() {
		// TODO Auto-generated method stub
		
	}

}
