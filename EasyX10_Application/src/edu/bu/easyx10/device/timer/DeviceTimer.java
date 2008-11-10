package edu.bu.easyx10.device.timer;

import java.util.TimerTask;
import edu.bu.easyx10.event.Event;
import edu.bu.easyx10.event.EventGenerator;
import edu.bu.easyx10.event.EventGeneratorFactory;

public abstract class DeviceTimer extends TimerTask {
	
	// private member variables
	Event mEventToFire;
	protected final EventGenerator eventGenerator;
	
	/**
	 * Construct a new DeviceTimer. While this class is abstract, always
	 * call the super classes constructor to in
	 */
	public DeviceTimer(){
		
		// fetch the eventGenerator instance from the EventGeneratorFactory
		eventGenerator = EventGeneratorFactory.getEventGenerator( );
		
	}
	
	protected abstract void startTimer();
	
	protected abstract Event getEventToFire();

	protected abstract void setEventToFire(Event eventToFire);

}
