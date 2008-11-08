package edu.bu.easyx10.event;

import edu.bu.easyx10.event.X10Event;
import edu.bu.easyx10.event.EventHandlerListener;

/**
 * This is the concrete class for the X10ProtocolEvent class.
 * This class inherits all attributes and methods from the abstract
 * base X10Event class.
 *
 * @author:  Jim Duda
 * @version: refer to EasyX10 subversion
 * @date:    10/30/08
 *
 */
public class X10ProtocolEvent extends X10Event {

	/**
	 *  Constructor - default implicit constructor
	 */
	public X10ProtocolEvent ( ) {

	}

	/**
	 * Construct a new X10Event Object using human readable parameters.
	 *
	 * @param deviceName - String Device name
	 * @param houseCode  - char House code value between 'A' and 'P' inclusive.
	 * @param deviceCode - integer Unit code value between 1 and 16 inclusive.
	 * @param commmand   - String ON, OFF, DIM, BRIGHT
	 */
	public X10ProtocolEvent(String deviceName, char houseCode, int deviceCode,
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
	public X10ProtocolEvent(String deviceName, X10_HOUSE_CODE houseCode,
			X10_DEVICE_CODE deviceCode, X10_EVENT_CODE eventCode)
			throws IllegalArgumentException {
		super(deviceName, houseCode, deviceCode, eventCode);
	}

	/**
	 * Construct a new X10DeviceEvent using implicit X10ProtocolEvent
	 * 
	 * @param e X10Event
	 */
	public X10ProtocolEvent(X10ProtocolEvent e) {
		super(e);
	}

	/**
	 * Implementation of the fireEvent abstract method.  Call the processProtocolEvent( )
	 * method in the EventClassListener.
	 */
	protected void fireEvent ( EventHandlerListener object ) {
		object.processProtocolEvent ( this ) ;
	}

}

