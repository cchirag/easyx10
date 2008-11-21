package edu.bu.easyx10.device.timer;

import edu.bu.easyx10.event.Event;
import java.sql.Time;  //TODO remove ASAP
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
//import java.text.SimpleDateFormat.*;



public class TriggerTimer extends DeviceTimer{

	// private member variables
	private Time mTriggerTime;
	private Timer timer = new Timer();


	
	public TriggerTimer(Event eventToFire, Time triggerTime){
		super(eventToFire);

	}
	
	public TriggerTimer(Event eventToFire, Calendar triggerTime){
		super(eventToFire);
		//setTriggerTime(triggerTime);
		
	}
	
	
	public Time getTriggerTime() {
		return mTriggerTime;
	}

	public void setTriggerTime(Time triggerTime) {
		mTriggerTime = triggerTime;
	}
	
	//public void setTriggerTime(Calendar triggerTime) {
		//mTriggerDate = triggerTime;
	//}

	@Override
	public void run() {

		Calendar currentTime = Calendar.getInstance();
		String DATE_FORMAT_NOW = "H:mm:ss:SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        System.out.println("The current time is " + sdf);
		System.out.println("I did something at " + sdf.format(currentTime.getTime()));
		
		//eventGenerator.fireEvent(mEventToFire);
		
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
	public void startTimer() {

		//Schedule the execution of run(),at mTriggerTime, every 24 hrs
		//timer.scheduleAtFixedRate(this, mTriggerDate.getTime(), 86400000);
		timer.scheduleAtFixedRate(this, 0, 10000);
		
	}

}
