package edu.bu.easyx10.device;

import edu.bu.easyx10.event.EventGeneratorFactory;
import edu.bu.easyx10.event.EventGenerator;
import edu.bu.easyx10.event.EventHandlerListener;

/**
 * The Device class is an abstract base class that’s used to model
 * physical devices within the EasyX10 system. A device has only two 
 * attributes; a name and a location. The name is the unique identifier 
 * amongst other devices in the system, and the location is a string 
 * representing its physical location in the GUI.
 *
 * @author  Damon Gabrielle
 * @version please refer to subversion
 * @date:   11/06/08
 */

public abstract class Device implements EventHandlerListener{

	// Declare Private Member Variables
	private String mName;
	private String mLocation;
	protected EventGenerator eventGenerator;
	
	/**
	 * Construct a new Device class.  The constructor always registers
	 * with the EventGenerator in order to receive inbound events.
	 */
	
	public Device (){

		// fetch the eventGenerator instance from the EventGeneratorFactory
		eventGenerator = EventGeneratorFactory.getEventGenerator( );
		
		// register the Protocol class with the EventGenerator
		eventGenerator.addEventListener(this);
	
	}
	/**
	 * Destructor for the Device.  The destructor always unregisters
	 * with the EventGenerator in order to properly shutdown.
	 */
	public void finalize ( ) {
		// unregister the Device with the EventGenerator
	    eventGenerator.deleteEventListener(this);
	}
	
	
	public boolean setLocation(String location) {
		this.mLocation = location;
		return true;
	}

	/**
	 * @return Returns a string containing the Device location
	 */	
	public String getLocation() {
		return mLocation;
	}
	
	/**
	 * @param Sets the Device name and return true if successful
	 */
	public boolean setName(String name) {
		this.mName = name;
		return true;
	}
	
	/**
	 * @return the name of the Device
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param update this device by reading in the attributes of a proxyDevice
	 */
	public abstract void updateDevice(Device proxyDevice);

	
	/**
	 * @return a copy of the Device in the form of a ProxyDevice
	 */
	public abstract Device getProxyDevice(); 

};