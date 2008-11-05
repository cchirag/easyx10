package edu.bu.easyx10.event;

import edu.bu.easyx10.event.X10Event;
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
	 *  Constructor - default implicit constructor
	 */
	public X10DeviceEvent ( ) {

	}

	/**
	 * Implementation of the fireEvent abstract method.  Call the processDeviceEvent( )
	 * method in the EventClassListener.
	 */
	protected void fireEvent ( EventHandlerListener object ) {
		object.processDeviceEvent ( this ) ;
	}

}

