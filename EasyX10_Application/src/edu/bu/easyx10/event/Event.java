package edu.bu.easyx10.event;

/**
 * This is the abstract base class for the Event class.  Concrete
 * Event types must extended from this class.
 *
 * The deviceName attribute is common to all Event types.
 *
 * @author:  Jim Duda
 * @version: please refer to subversion
 * @date:    10/30/08
 *
 */
public abstract class Event {

	// Declare the private member variables.
	private String m_deviceName;

	/**
	 * Constructor - default implicit constructor
	 */
	public Event ( ) {

	}

	/**
	 * Constructor
	 *
	 * @param deviceName String which identifies the Device destination for this Event.
	 */
	public Event ( String deviceName ) {
		setDeviceName(deviceName);
	}

	/**
	 * Accessor for m_deviceName member variable.
	 *
	 * @param name String which identifies the Device destination for this Event.
	 */
	public void setDeviceName ( String name ) {
		m_deviceName = name;
	}

	/**
	 * Accessor for m_deviceName member variable.
	 *
	 * @return String which identifies the Device destination for this Event.
	 */
	public String getDeviceName ( ) {
		return m_deviceName;
	}

	/**
	 * Override the equality operator for the TimerEvent
	 * 
	 * @param timerEvent
	 * @return boolean true when objects are equivalent.
	 */
	public boolean equals ( Event event ) {
		boolean equivalent = true;
		// check the local class for its fields
		if (event.getDeviceName() != this.getDeviceName()) {
			equivalent = false;
		}
		return(equivalent);
	}
	
	/**
	 * The fireEvent method must be implemented by any concrete class.
	 * This method will make a call to one of the methods provided by
	 * the EventHandlerListener interface.  The proper method called is
	 * a function of the concrete Event class.
	 *
	 * @param e Event which is to be sent to EventHandlerListener.
	 */
	abstract protected void fireEvent ( EventHandlerListener object );
	
};

