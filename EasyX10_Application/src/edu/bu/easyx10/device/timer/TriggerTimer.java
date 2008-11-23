package edu.bu.easyx10.device.timer;

import edu.bu.easyx10.event.Event;
import edu.bu.easyx10.util.LoggingUtilities;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;




public class TriggerTimer extends DeviceTimer{

	// private member variables
	   
	private Calendar mTriggerTime = Calendar.getInstance();
	private Timer timer = new Timer();
	String DATE_FORMAT_NOW = "H:mm:ss:SSS";
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

	
	public TriggerTimer(Event eventToFire, Calendar triggerTime){
		super(eventToFire);
		setTriggerTime(triggerTime);	
	}
	
	
	public Calendar getTriggerTime() {
		return mTriggerTime;
	}

	public void setTriggerTime(Calendar triggerTime) {
		
		/* 
		 * We don't really restrict which Calendar fields are set on triggerTime
		 * when it's passed in. However, we only care about time of day
		 * so we should only set those values in mTriggerTime.
		 */
		
		mTriggerTime.set(Calendar.HOUR_OF_DAY, triggerTime.get(Calendar.HOUR_OF_DAY));
		mTriggerTime.set(Calendar.MINUTE, triggerTime.get(Calendar.MINUTE));
		mTriggerTime.set(Calendar.SECOND, triggerTime.get(Calendar.SECOND));
		mTriggerTime.set(Calendar.MILLISECOND, triggerTime.get(Calendar.MILLISECOND));
		
	}
	

	@Override
	public void run() {

		eventGenerator.fireEvent(mEventToFire);
		
		//Log a message indicating the timer was started
		LoggingUtilities.logInfo(TriggerTimer.class.getCanonicalName(),
				 "run()","Trigger Timer Fired and " + mEventToFire.toString() + 
				 " event to " + mEventToFire.getDeviceName());
		
	}

	@Override
	public Event getEventToFire() {
		
		
		return mEventToFire;
	}

	@Override
	public void setEventToFire(Event eventToFire) {

		mEventToFire = eventToFire;
		
	}

	@Override
	public void startTimer() {

		//Useful Debugging
			//System.out.println("I will do something at " + sdf.format(mTriggerTime.getTime()));
		
		//clean up previous scheduled tasks
		//timer.cancel();  //cancel the previous scheduled task because the time may have changed
		//timer.purge();   //Tell garbage collection to mark canceled tasks for cleanup
		
		//Schedule the TriggerTimer to fire once every 24 hrs from mTriggerTime
		timer.schedule(this,mTriggerTime.getTime(), 86400000);
		
		//Log a message indicating the timer was started
		LoggingUtilities.logInfo(TriggerTimer.class.getCanonicalName(),
				 "startTimer()","TriggerTimer was started. An event will " +
				 "fire at " + sdf.format(mTriggerTime.getTime()));
	}

}
