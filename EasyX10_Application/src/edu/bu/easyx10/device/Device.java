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
	
	public Device (String name){

		// fetch the eventGenerator instance from the EventGeneratorFactory
		eventGenerator = EventGeneratorFactory.getEventGenerator( );
		
		// register the Protocol class with the EventGenerator
		eventGenerator.addEventListener(this);

		// set the name of the device
		setName(name);
	}
	/**
	 * Destructor for the Device.  The destructor always unregisters
	 * with the EventGenerator in order to properly shutdown.
	 */
	public void finalize ( ) {
		// unregister the Device with the EventGenerator
	    eventGenerator.deleteEventListener(this);
	}
	
	/**
	 * 
	 * This method sets the location of Device. Location is implementation 
	 * specific. For instance this can be used to represent where the device 
	 * is physically located within a GUI or where it's located within the home. 
	 * 
	 * @return Return true if the location is successfully set or false if it isn't.
	 * Currently as long as it's a valid string it should never return false as
	 * there are no restrictions on length enforced at this level.
	 */	
	
	public boolean setLocation(String location) {
		mLocation = location;
		return true;
	}

	/**
	 * @return Returns a string containing the Device location
	 */	
	public String getLocation() {
		return mLocation;
	}
	
	/**
	 * 
	 * This method is used to set the name of the Device.
	 * 
	 * @param Sets the Device name and return true if successful
	 * 
	 * @return Returns true if the name is successfully set and false if it isn't.
	 */
	public boolean setName(String name) {
		mName = name;
		return true;
	}
	
	/**
	 * @return Returns the name of the Device
	 */
	public String getName() {
		return mName;
	}

	/**
	 * The Derived class must implement this method.
	 * 
	 * @param update this device by reading in the attributes of a proxyDevice
	 */
	public abstract void updateDevice(Device proxyDevice);

	
	/**
	 * The Derived class must implement this method.
	 * 
	 * @return a copy of the Device in the form of a ProxyDevice
	 */
	public abstract Device getProxyDevice(); 

};