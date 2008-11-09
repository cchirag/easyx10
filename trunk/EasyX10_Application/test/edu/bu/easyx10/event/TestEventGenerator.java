package edu.bu.easyx10.event;

import junit.framework.TestCase;
import edu.bu.easyx10.event.*;
import java.util.*;

public class TestEventGenerator extends TestCase {

	private EventGenerator eventGenerator;
	private TimerEvent expectedTimerEvent = null;
	private X10ProtocolEvent expectedProtocolEvent = null;
	private X10DeviceEvent expectedDeviceEvent = null;
	private Integer numberOfObservers;
	private Integer numberOfTimerEvents;
	private Integer numberOfProtocolEvents;
	private Integer numberOfDeviceEvents;
	private List<Observer> listOfObservers;

	public TestEventGenerator (String name) {
		super(name);
	}

    public class Observer implements EventHandlerListener { 
    	
    	/**
    	 * Catch all DeviceEvents and check against expected.
    	 */
    	public void processDeviceEvent ( Event e ) {  	 
    		boolean properClass = e instanceof X10DeviceEvent;
    		assertEquals ("Received Event not a X10DeviceEvent" + e, properClass, true );
    	}

    	/**
    	 * Catch all ProtocolEvents and check against expected.
    	 */
    	public void processProtocolEvent ( Event e ) {
    		boolean properClass = e instanceof X10ProtocolEvent;
    		assertEquals ("Received Event not a X10ProtocolEvent" + e, properClass, true );
    	}

    	/**
    	 * Catch all TimerEvents and check against expected.
    	 */
    	public void processTimerEvent ( Event e ) {
    		boolean properClass = e instanceof TimerEvent;
    		assertEquals ("Received Event not a TimerEvent" + e, properClass, true );
    	}
   }

	/**
	 * Sets up the test fixture. 
	 * (Called before every test case method.) 
	 */ 
	protected void setUp() { 
		eventGenerator = new EventGenerator( );
		
		numberOfTimerEvents = 0;
		numberOfProtocolEvents = 0;
		numberOfDeviceEvents = 0;
		
		listOfObservers = new ArrayList<Observer>( );
		
		listOfObservers.add ( new Observer( ) );
		numberOfObservers = 1;
	} 

	/**
	 * Tears down the test fixture. 
	 * (Called after every test case method.) 
	 */ 
	protected void tearDown() { 
		eventGenerator = null;
		listOfObservers = null;
		
	} 

	public void testDeviceEvent ( ) {
		expectedDeviceEvent = new X10DeviceEvent ("someDeviceString", 'A', 1, "ON");
		numberOfDeviceEvents = 1;
		eventGenerator.fireEvent( expectedDeviceEvent );
		assertEquals ( "Unexpected number of X10DeviceEvents", numberOfDeviceEvents, numberOfObservers);
	}

	public void testProtocolEvent ( ) {
		expectedProtocolEvent = new X10ProtocolEvent ("someDeviceString", 'A', 1, "ON");
		numberOfProtocolEvents = 1;
		eventGenerator.fireEvent( expectedProtocolEvent );
		assertEquals ( "Unexpected number of X10ProtocolEvents", numberOfProtocolEvents, numberOfObservers);
	}

	public void testTimerEvent ( ) {
		expectedTimerEvent = new TimerEvent ("someDeviceString", "OFF");
		numberOfTimerEvents = 1;
		eventGenerator.fireEvent( expectedTimerEvent );
		assertEquals ( "Unexpected number of TimerEvents", numberOfTimerEvents, numberOfObservers);
	}


}
