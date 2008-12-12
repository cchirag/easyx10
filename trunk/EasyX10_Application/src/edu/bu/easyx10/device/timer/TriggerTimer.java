package edu.bu.easyx10.device.timer;

import edu.bu.easyx10.event.Event;
import java.util.*;

/**
 * This is the concrete class for the TriggerTimer class.  This version
 * of the DeviceTimer provides a very simple function.  The object
 * is given a time (Hour/Minute) to wait for.  Once the time has
 * has arrived, the Event defined by eventToFire is sent to the
 * EventGenerator.
 *
 * @author:  Jim Duda
 * @version: please refer to subversion
 * @date:    10/30/08
 *
 */
public class TriggerTimer extends DeviceTimer {

	// define our member variables
	private Calendar mTriggerTime;
	private Calendar mCalendar;
	private boolean  mTimerActive;
	private Thread mThread;

	/**
	 * Construct a new TriggerTimer object.  This object is given a
	 * specific time to wait for and an EventToFire when the specific
	 * time has arrived.
	 *
	 * @param Calendar stores the Hour and Minute of the day.
	 * @param Event eventToFire
	 */
	public TriggerTimer (Event eventToFire, Calendar triggerTime){
		super(eventToFire);
		mCalendar = Calendar.getInstance( );
		mTriggerTime = triggerTime;
		mTimerActive = false;
		// Create a new runnable thread.
		mThread = new Thread(this);
		mThread.start( );
	}

	/**
	 * This method sets the current Trigger Time.  We wait
	 * for TriggerTime then fire eventToFire.
	 *
	 * @param Calendar triggerTime
	 */
	public void setTriggerTime(Calendar triggerTime) {
		mTriggerTime = triggerTime;
	}

	/**
	 * This method returns the current Trigger Time.
	 *
	 * @return Calendar
	 */
	public Calendar getTriggerTime( ) {
		return mTriggerTime;
	}

	/**
	 * The run method provides the main timer function.  The run
	 * method runs once per second.  Each second, the mTriggerTime
	 * is 
	 
	  to the current time.  If they match, we send
	 * the eventToFire.
	 */
	public void run( ) {
		while (true) {
			// refresh the current TOD
			mCalendar = Calendar.getInstance( );
			if (  mTimerActive &&
					(mCalendar.get(Calendar.HOUR_OF_DAY) == mTriggerTime.get(Calendar.HOUR_OF_DAY)) &&
					(mCalendar.get(Calendar.MINUTE) == mTriggerTime.get(Calendar.MINUTE))  &&
					(mCalendar.get(Calendar.SECOND) == 0)
					) {
				eventGenerator.fireEvent( getEventToFire( ));
			}
			try {
				Thread.sleep(1000);
			} catch ( InterruptedException e ) {};
		}
	}

	/**
	 * This method simply allows the timer to operate.
	 */
	public void startTimer( ) {
		mTimerActive = true;
	}

	/**
	 * This method simply disables the timer from operating.
	 */
	public void stopTimer( ) {
		mTimerActive = false;
	}

}
