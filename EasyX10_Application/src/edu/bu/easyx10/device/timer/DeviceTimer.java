package edu.bu.easyx10.device.timer;

import java.util.TimerTask;
import edu.bu.easyx10.event.Event;
import edu.bu.easyx10.event.EventGenerator;
import edu.bu.easyx10.event.EventGeneratorFactory;


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
	
	protected abstract Event getEventToFire();

	protected abstract void setEventToFire(Event eventToFire);

}
