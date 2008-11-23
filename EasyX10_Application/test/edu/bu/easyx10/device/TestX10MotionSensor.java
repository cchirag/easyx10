package edu.bu.easyx10.device;

import java.util.*;

import edu.bu.easyx10.device.X10Device.*;
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
	private Calendar startTime;
	private Calendar endTime;

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
	 * Create a random houseCode
	 * 
	 */
	private char RandomHouseCode( ) {
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
        return (houseCode);
	}
	
	/**
	 * Create a random deviceCode
	 */
	private int RandomDeviceCode( ) {
		int deviceCode = (m_rv.nextInt( ) & 0xf) + 1;
        return (deviceCode);
	}
	
	/**
	 * Create a random X10DeviceState
	 */
	private X10DeviceState RandomX10DeviceState( ) {
		X10DeviceState state = X10DeviceState.OFF;
		switch (m_rv.nextInt( ) & 1) {
		case 0: state = X10DeviceState.OFF;
		case 1: state = X10DeviceState.ON;
		}
		return (state);
	}
	
	/**
	 * Create a random ProxyX10MotionSensor
	 * 
	 * @return
	 */
	private ProxyX10MotionSensor RandomProxyX10MotionSensor ( ) {

		// Randomize a houseCode
		char houseCode = RandomHouseCode( );

		// Randomize a deviceCode
		int deviceCode = RandomDeviceCode( );
		
		// Create the name
		String name = Character.toString(houseCode) + Integer.toString(deviceCode);

		Set<String> applianceList = new HashSet<String>( );
		applianceList.clear( );
		for (int i = 0; i < (m_rv.nextInt( ) & 7); i++) {
			char house = RandomHouseCode( );
			int device = RandomDeviceCode( );
			applianceList.add( Character.toString(house) + Integer.toString(device));
		}
		
		ProxyX10MotionSensor proxySensor = new ProxyX10MotionSensor ( name, houseCode, deviceCode );
		proxySensor.setLocation(new DeviceLocation(1,20,50));
		proxySensor.setApplianceList(applianceList);
		proxySensor.setDetectionPeriodEnabled(false);
		startTime = Calendar.getInstance( );
		startTime.set(Calendar.HOUR_OF_DAY, 13);
		startTime.set(Calendar.MINUTE, 33);
        proxySensor.setStartTime(startTime);
        endTime = Calendar.getInstance( );
		endTime.set(Calendar.HOUR_OF_DAY, 17);
		endTime.set(Calendar.MINUTE, 45);
		System.out.println("startTime: " + startTime.get(Calendar.HOUR_OF_DAY) + " endTime: " + endTime.get(Calendar.HOUR_OF_DAY));
        proxySensor.setStartTime(startTime); 
        proxySensor.setInactivityTimeEnabled(true);
		proxySensor.setInactivityTime( m_rv.nextInt( ) & 0x7);
		proxySensor.setState(RandomX10DeviceState( ));
		return (proxySensor);
	}
	
	/**
	 * Compare 2 proxy objects for equality.
	 */
    private boolean ProxyEqual ( ProxyX10MotionSensor a, ProxyX10MotionSensor b ) {
    	boolean proxyEqual = true;
    	if (!a.getName().equals(b.getName())) proxyEqual = false;
    	if (!a.getLocation().equals(b.getLocation())) proxyEqual = false;
    	if (a.getHouseCode() != b.getHouseCode()) proxyEqual = false;
    	if (a.getDeviceCode() != b.getDeviceCode()) proxyEqual = false;
    	if (!a.getApplianceList().equals(b.getApplianceList())) proxyEqual = false;
    	if (a.getDetectionPeriodEnabled() != b.getDetectionPeriodEnabled()) proxyEqual = false;
    	if (a.getStartTime( ).get(Calendar.HOUR_OF_DAY) != b.getStartTime( ).get(Calendar.HOUR_OF_DAY)) {
    		proxyEqual = false;
    	}
    	if (a.getStartTime( ).get(Calendar.MINUTE) != b.getStartTime( ).get(Calendar.MINUTE)) {
    		proxyEqual = false;
    	}
    	if (a.getInactivityTimeEnabled() != b.getInactivityTimeEnabled()) proxyEqual = false;
    	if (a.getInactivityTime() != b.getInactivityTime()) proxyEqual = false;
    	if (a.getState() != b.getState()) proxyEqual = false;
    	return (proxyEqual);
    }

    public void testUpdateDevice() {
		ProxyX10MotionSensor originalProxy = RandomProxyX10MotionSensor( );
		X10MotionSensor testMotionSensor = new X10MotionSensor ( originalProxy );
		ProxyX10MotionSensor updatedProxy = RandomProxyX10MotionSensor( );
		updatedProxy.setName(testMotionSensor.getName());
		updatedProxy.setState(testMotionSensor.getState());
		testMotionSensor.updateDevice(updatedProxy);
		ProxyX10MotionSensor getProxy = testMotionSensor.getProxyDevice( );
		System.out.println("originalProxy.state " + originalProxy.getState() + " updateProxy.state " + updatedProxy.getState() + " getProxy " + getProxy.getState());
		boolean proxyEqual = ProxyEqual ( updatedProxy, getProxy );
		assertEquals ( "getProxyDevice not equal to updatedProxy", proxyEqual, true);
	}
}
