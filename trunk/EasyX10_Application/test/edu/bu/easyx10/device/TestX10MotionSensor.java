package edu.bu.easyx10.device;

import java.util.Random;

import edu.bu.easyx10.event.EventGenerator;
import edu.bu.easyx10.event.EventGeneratorFactory;
import edu.bu.easyx10.event.EventHandlerListener;
import edu.bu.easyx10.event.TimerEvent;
import edu.bu.easyx10.event.X10DeviceEvent;
import edu.bu.easyx10.event.X10ProtocolEvent;

import junit.framework.TestCase;

public class TestX10MotionSensor extends TestCase {

	private EventGenerator eventGenerator;
	private Observer observer;
	private TimerEvent expectedTimerEvent = null;
	private X10DeviceEvent expectedDeviceEvent = null;
	private Integer numberOfTimerEvents;
	private Integer numberOfProtocolEvents;
	private Integer numberOfDeviceEvents;
	private Random m_rv;

	/*
	 * Construct the TestCase itself
	 */
	public TestX10MotionSensor (String name) {
		super(name);
		m_rv = new Random( );
	}

	public class Observer implements EventHandlerListener { 

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
		public void processDeviceEvent ( X10DeviceEvent e ) {  	 
			boolean properClass = e instanceof X10DeviceEvent;
			assertEquals ("Received Event not a X10DeviceEvent" + e, properClass, true );
			boolean equivalent = ((X10DeviceEvent)e).equals(expectedDeviceEvent);
			assertEquals ("Received Event not equivalent to expected", equivalent, true );
			numberOfDeviceEvents++;
		}

		/**
		 * Catch all ProtocolEvents and check against expected.
		 */
		public void processProtocolEvent ( X10ProtocolEvent e ) {
			numberOfProtocolEvents++;
		}

		/**
		 * Catch all TimerEvents and check against expected.
		 */
		public void processTimerEvent ( TimerEvent e ) {
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
		observer = new Observer( );	
		numberOfTimerEvents = 0;
		numberOfProtocolEvents = 0;
		numberOfDeviceEvents = 0;
	} 

	/**
	 * Tears down the test fixture. 
	 * (Called after every test case method.) 
	 */ 
	protected void tearDown() { 
		eventGenerator = null;
		observer = null;
	} 

	/**
	 * Create a random ProxyX10MotionSensor
	 * 
	 * @return
	 */
	private ProxyX10MotionSensor randomProxyX10MotionSensor ( ) {
		
		// Randomize a houseCode
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
		
		// Randomize a deviceCode
		int deviceCode = (m_rv.nextInt( ) & 0xf) + 1;

		ProxyX10MotionSensor proxySensor = new ProxyX10MotionSensor ( );
		proxySensor.setHouseCode(houseCode);
		proxySensor.setDeviceCode(deviceCode);
		return (proxySensor);
	}

	public void testUpdateDevice() {
		fail("Not yet implemented");
	}

	public void testGetProxyDevice() {
		fail("Not yet implemented");
	}

	public void testGetState() {
		fail("Not yet implemented");
	}

	public void testX10MotionSensorProxyX10MotionSensor() {
		fail("Not yet implemented");
	}

	public void testX10MotionSensorStringCharInt() {
		fail("Not yet implemented");
	}

	public void testSetInactivityTime() {
		fail("Not yet implemented");
	}

	public void testGetInactivityTime() {
		fail("Not yet implemented");
	}

	public void testSetDetectionPeriodEnabled() {
		fail("Not yet implemented");
	}

	public void testGetDetectionPeriodEnabled() {
		fail("Not yet implemented");
	}

	public void testSetStartTime() {
		fail("Not yet implemented");
	}

	public void testGetStartTime() {
		fail("Not yet implemented");
	}

	public void testGetEndTime() {
		fail("Not yet implemented");
	}

	public void testSetEndTime() {
		fail("Not yet implemented");
	}

	public void testSetApplianceList() {
		fail("Not yet implemented");
	}

	public void testGetApplianceList() {
		fail("Not yet implemented");
	}

	public void testSetStateX10DeviceState() {
		fail("Not yet implemented");
	}

	public void testProcessDeviceEvent() {
		fail("Not yet implemented");
	}

	public void testProcessTimerEvent() {
		fail("Not yet implemented");
	}

	public void testProcessProtocolEvent() {
		fail("Not yet implemented");
	}

}
