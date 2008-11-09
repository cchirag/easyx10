package edu.bu.easyx10.event;

import edu.bu.easyx10.event.Event;

/**
 * This is the concrete class for the TimerEvent class.  This class inherits
 * all attributes and methods from the abstract base Event class.
 *
 *
 * @author:  Jim Duda
 * @version: refer to EasyX10 subversion
 * @date:    10/30/08
 *
 */
public class TimerEvent extends Event {

	// Create the private member variables for this class.
	private String  m_eventName;

	/**
	 * Constructor - default implicit constructor
	 */
	public TimerEvent ( ) {

	}

	/**
	 * Constructor - create object with an eventName
	 *
	 * @param deviceName String which identifies the destination Device name.
	 * @param eventName String which identifies the TimerEvent for the recipient.
	 */
	public TimerEvent ( String deviceName, String eventName ) {
		super ( deviceName );
		setEventName ( eventName );
	}

	/**
	 * Accessor for the member variable m_eventName.
	 *
	 * @param eventName - String which identifies the event for the recipient.
	 */
	public void setEventName ( String eventName ) {
		m_eventName = eventName;
	}

	/**
	 * Accessor for the member variable m_eventName
	 *
	 * @return Sting which identifies the event for the recipient.
	 */
	public String getEventName ( ) {
		return m_eventName;
	}

	/**
	 * Implementation of the fireEvent abstract method.  Call the processTimerEvent( )
	 * method in the EventClassListener.
	 */
	protected void fireEvent ( EventHandlerListener object ) {
		object.processDeviceEvent ( this ) ;
	}

}