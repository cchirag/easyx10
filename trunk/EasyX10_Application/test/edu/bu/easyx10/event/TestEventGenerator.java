package edu.bu.easyx10.event;

import junit.framework.TestCase;
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
	private Random m_rv;

	public TestEventGenerator (String name) {
		super(name);
		m_rv = new Random( );
	}

	public class Observer implements EventHandlerListener { 

		// declare the class variables 
		private EventGenerator eventGenerator;

		/**
		 * Constructor
		 * 
		 * Register with the EventGenerator to listen for events.
		 */
		public Observer ( ) {
			eventGenerator = EventGeneratorFactory.getEventGenerator();
			eventGenerator.addEventListener(this);
		}

		/**
		 * Catch all DeviceEvents and check against expected.
		 */
		public void processDeviceEvent ( Event e ) {  	 
			boolean properClass = e instanceof X10DeviceEvent;
			assertEquals ("Received Event not a X10DeviceEvent" + e, properClass, true );
			boolean equivalent = ((X10DeviceEvent)e).equals(expectedDeviceEvent);
			assertEquals ("Received Event not equivalent to expected", equivalent, true );
			numberOfDeviceEvents++;
		}

		/**
		 * Catch all ProtocolEvents and check against expected.
		 */
		public void processProtocolEvent ( Event e ) {
			boolean properClass = e instanceof X10ProtocolEvent;
			assertEquals ("Received Event not a X10ProtocolEvent" + e, properClass, true );
			boolean equivalent = ((X10ProtocolEvent)e).equals(expectedProtocolEvent);
			assertEquals ("Received Event not equivalent to expected", equivalent, true );
			numberOfProtocolEvents++;
		}

		/**
		 * Catch all TimerEvents and check against expected.
		 */
		public void processTimerEvent ( Event e ) {
			boolean properClass = e instanceof TimerEvent;
			assertEquals ("Received Event not a TimerEvent" + e, properClass, true );
			boolean equivalent = ((TimerEvent)e).equals(expectedTimerEvent);
			assertEquals ("Received Event not equivalent to expected", equivalent, true );
			numberOfTimerEvents++;
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
		numberOfObservers = 0;

		listOfObservers = new ArrayList<Observer>( );

		for (Integer i = 0; i < (m_rv.nextInt( ) & 0xff); i++) {
			listOfObservers.add ( new Observer( ) );
			numberOfObservers++;
		}
	} 

	/**
	 * Tears down the test fixture. 
	 * (Called after every test case method.) 
	 */ 
	protected void tearDown() { 
		eventGenerator = null;
		listOfObservers = null;
	} 

	private X10DeviceEvent randomX10DeviceEvent ( ) {
		char houseCode = 'A';
		switch (m_rv.nextInt( ) & 0xf) {
		case 0x0: houseCode = 'A'; break;
		case 0x1: houseCode = 'B'; break;
		case 0x2: houseCode = 'C'; break;
		case 0x3: houseCode = 'D'; break;
		case 0x4: houseCode = 'E'; break;
		case 0x5: houseCode = 'F'; break;
		case 0x6: houseCode = 'G'; break;
		case 0x7: houseCode = 'H'; break;
		case 0x8: houseCode = 'I'; break;
		case 0x9: houseCode = 'J'; break;
		case 0xa: houseCode = 'K'; break;
		case 0xb: houseCode = 'L'; break;
		case 0xc: houseCode = 'M'; break;
		case 0xd: houseCode = 'N'; break;
		case 0xe: houseCode = 'O'; break;
		case 0xf: houseCode = 'P'; break;
		default: assert(false);
		}
		int deviceCode = (m_rv.nextInt( ) & 0xf) + 1;
		String eventCode = "";
		switch(m_rv.nextInt() & 0x3) {
		case 0: eventCode = "ON"; break;
		case 1: eventCode = "OFF"; break;
		case 2: eventCode = "DIM"; break;
		case 3: eventCode = "BRIGHT"; break;
		default: assert(false);
		}

		X10DeviceEvent deviceEvent = new X10DeviceEvent (
				Character.toString(houseCode) + Integer.toString(deviceCode), 
				houseCode, deviceCode, eventCode);
		return (deviceEvent);
	}

	private TimerEvent randomTimerEvent( ) {
		X10DeviceEvent deviceEvent = randomX10DeviceEvent( );
		TimerEvent timerEvent = new TimerEvent ( deviceEvent.getDeviceName(), deviceEvent.getEventCodeString() );
		return (timerEvent);
	}

	public void testDeviceEvent ( ) {
		expectedDeviceEvent = randomX10DeviceEvent( );
		X10DeviceEvent sendDeviceEvent = new X10DeviceEvent(expectedDeviceEvent);
		eventGenerator.fireEvent( sendDeviceEvent );
		assertEquals ( "Unexpected number of X10DeviceEvents", numberOfDeviceEvents, numberOfObservers);
	}

	public void testProtocolEvent ( ) {
		X10DeviceEvent deviceEvent = randomX10DeviceEvent( );
		expectedProtocolEvent = new X10ProtocolEvent (
				deviceEvent.getDeviceName( ),
				deviceEvent.getHouseCode( ),
				deviceEvent.getDeviceCode( ),
				deviceEvent.getEventCode( )
		);
		X10ProtocolEvent sendProtocolEvent = new X10ProtocolEvent(expectedProtocolEvent);
		eventGenerator.fireEvent( sendProtocolEvent );
		assertEquals ( "Unexpected number of X10ProtocolEvents", numberOfProtocolEvents, numberOfObservers);
	}

	public void testTimerEvent ( ) {
		expectedTimerEvent = randomTimerEvent( );
		TimerEvent sendTimerEvent = new TimerEvent(expectedTimerEvent);
		eventGenerator.fireEvent( sendTimerEvent );
		assertEquals ( "Unexpected number of TimerEvents", numberOfTimerEvents, numberOfObservers);
	}

	public void testRandomEvent ( ) {
		tearDown( );
		for (int i = 0; i < (m_rv.nextInt( ) & 0xff); i++) {
			setUp( );
			switch (m_rv.nextInt( ) & 0x3) {
			case 0: testDeviceEvent( ); break;
			case 1: testProtocolEvent( ); break;
			default: testDeviceEvent( ); break;
			}
			tearDown( );
		}
	}
}


