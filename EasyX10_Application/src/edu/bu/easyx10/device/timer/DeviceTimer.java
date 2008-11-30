package edu.bu.easyx10.device.timer;

import java.util.TimerTask;
import edu.bu.easyx10.event.Event;
import edu.bu.easyx10.event.EventGenerator;
import edu.bu.easyx10.event.EventGeneratorFactory;

/**
 * The DeviceTimer class is base class of the Timer package. It is an abstract 
 * class used by the device class to perform time related events. It is
 * only responsibility is to firing events to the EventGenerator at a specific
 * moment in time. Actual device Timer implementation is handled by the derived
 * classes. Timer contains only one attribute; Event EventToFire and contains 
 * two methods to get and set EventToFire. It also contains an abstract method
 * called startTimer() that must be overridden by the derived class.
 * .
 * @author dgabriel
 * @version please refer to subversion
 * @date:   11/29/08
 */

public abstract class DeviceTimer extends TimerTask {
	
	// private member variables
	protected Event mEventToFire;
	
	protected final EventGenerator eventGenerator;
	
	/**
	 * Constructor for a new DeviceTimer.
	 */
	public DeviceTimer(Event eventToFire){
		
		setEventToFire(eventToFire);
		// fetch the eventGenerator instance from the EventGeneratorFactory
		eventGenerator = EventGeneratorFactory.getEventGenerator( );
		
	}
	
	public abstract void startTimer();
	
	/**
	 * This method is used to set the eventToFire Event.
	 * 
	 * @param Event eventToFire
	 */
	public void setEventToFire(Event eventToFire) {
		mEventToFire = eventToFire;
	}

	/**
	 * This method returns the eventToFire Event.
	 * 
	 * @return Event
	 */
	public Event getEventToFire( ) {
		 return (mEventToFire);
	}
}
