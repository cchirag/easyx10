package edu.bu.easyx10.event;

import edu.bu.easyx10.event.EventHandlerListener;


/**
 * This is the concrete class for the X10DeviceEvent class.
 * This class inherits all attributes and methods from the abstract
 * base X10Event class.
 *
 * @author:  Jim Duda
 * @version: refer to EasyX10 subversion
 * @date:    10/30/08
 *
 */

public class X10DeviceEvent extends X10Event {

	/**
	 * Construct a new X10DeviceEvent using human readable parameters.
	 * 
	 * @param deviceName - String which defines the destination device for the event
	 * @param houseCode  - char    X10 house code
	 * @param deviceCode - integer X10 device code
	 * @param eventCode  - String  X10 event code
	 * @throws IllegalArgumentException
	 */
	public X10DeviceEvent(String deviceName, char houseCode, int deviceCode,
			String eventCode) throws IllegalArgumentException {
		super(deviceName, houseCode, deviceCode, eventCode);
	}

	/**
	 * Construct a new X10DeviceEvent using enumerations.
	 * 
	 * @param deviceName - String which defines the destination device for the event
	 * @param houseCode  - X10_HOUSE_CODE  X10 house code
	 * @param deviceCode - X10_DEVICE_CODE X10 device code
	 * @param eventCode  - X10_EVENT_CODE  X10 event code
	 * @throws IllegalArgumentException
	 */
	public X10DeviceEvent(String deviceName, X10_HOUSE_CODE houseCode,
			X10_DEVICE_CODE deviceCode, X10_EVENT_CODE eventCode)
			throws IllegalArgumentException {
		super(deviceName, houseCode, deviceCode, eventCode);
	}

	/**
	 * Construct a new X10DeviceEvent using enumerations with only a 
	 * deviceName and an Event.
	 * 
	 * @param deviceName - String which defines the destination device for the event
	 * @param eventCode  - X10_EVENT_CODE  X10 event code
	 * @throws IllegalArgumentException
	 */
	public X10DeviceEvent(String deviceName, X10_EVENT_CODE eventCode)
			throws IllegalArgumentException {
		super(deviceName, eventCode);
	}

	/**
	 * Copy constructor
	 * 	 * 
	 * @param e X10DeviceEvent
	 */
	public X10DeviceEvent(X10DeviceEvent e) {
		super(e);
	}

	/**
	 * Implementation of the fireEvent abstract method.  Call the processDeviceEvent( )
	 * method in the EventClassListener.
	 */
	protected void fireEvent ( EventHandlerListener object ) {
		object.processDeviceEvent ( this ) ;
	}

}

