package edu.bu.easyx10.device.timer;

import edu.bu.easyx10.event.*;
import edu.bu.easyx10.util.LoggingUtilities;

/**
 * This is the concrete class for the WaitTimer class.  This version
 * of the DeviceTimer provides a very simple function.  The object
 * is given a number of seconds to wait.  Once the number of seconds
 * has occurred, the Event defined by eventToFire is sent to the
 * EventGenerator.
 *
 * @author:  Jim Duda
 * @version: please refer to subversion
 * @date:    10/30/08
 *
 */
public class WaitTimer extends DeviceTimer {

	// private member variables
	private int mSecondsToWait;
	private int mCountDown;
	private Thread mThread;

	/**
	 * Construct a new WaitTimer object.  This object is given a 
	 * number of seconds to wait and an EventToFire when the number
	 * of seconds has occurred.
	 * 
	 * @param int   secondsToWait
	 * @param Event eventToFire
	 */
	public WaitTimer(int secondsToWait, Event eventToFire){
		// Create the base class
		super(eventToFire);
		// Store our member variables
		setSecondsToWait(secondsToWait);
		// Start the timer on creation if there is a time to wait
		mCountDown = -1;

		// Create a new runnable thread.
		mThread = new Thread(this);
		mThread.start( );
		
	}
	
	/**
	 * This access methods is used to load the seconds to wait.
	 * 
	 * @param int secondsToWait
	 */
	public void setSecondsToWait(int secondsToWait) {
		//set the number of seconds to wait
		mSecondsToWait = secondsToWait;
	}

	/**
	 * This method is used to return the seconds to wait.
	 * 
	 * @return int
	 */
	public int getSecondsToWait() {
		return mSecondsToWait;
	}

	/**
	 * The run method provides the main timer function.  The run
	 * method runs once per second.  Each second, the mCountDown
	 * variable is checked for zero.  If zero, we send our event
	 * along.  If non zero, we decrement.  We only decrement to
	 * zero then stop at -1.
	 * 
	 */
	public void run( ) {

		while (true) {
			// When the timer expires, send our Event to notify
			if (mCountDown == 0) {
				eventGenerator.fireEvent( getEventToFire( ) );
			} 
			// Decrement only until we reach -1
			if (mCountDown > -1) {
				mCountDown--;
				LoggingUtilities.logInfo(this.getClass( ).getCanonicalName(), "run",
						"mCountDown:: " + Integer.toString(mCountDown ) );
			}
			// Sleep for one second
			try {
				Thread.sleep(1000);
			} catch ( InterruptedException e ) {
				break;
			};
		}
	}

	/**
	 * This method simply reloads the mCountDown variable to
	 * allow the timer to operate.
	 */
	public void startTimer() {
		mCountDown = mSecondsToWait;
		LoggingUtilities.logInfo(this.getClass( ).getCanonicalName(), "startTimer",
				"mCountDown:: " + Integer.toString(mCountDown ) );
	}


	/**
	 * This method simply forces the counter to the idle
	 * condition of -1 to stop the timer from operating.
	 */
	public void stopTimer() {
		mCountDown = -1;
	}

}
