import java.io.*;
import java.util.*;
import edu.bu.easyx10.protocol.*;
import edu.bu.easyx10.util.LoggingUtilities;
import edu.bu.easyx10.event.*;
import edu.bu.easyx10.device.*;
import edu.bu.easyx10.device.X10Device.X10DeviceState;

/**
 * Application which exercises an integration test of Protocol, X10Appliance,
 * and X10MotionSensor.
 */
public class TestDeviceManagerProtocol implements EventHandlerListener {

	private static EventGenerator eventGenerator;

	/**
	 * Constructor
	 */
	TestDeviceManagerProtocol ( ) {
		
		// private member variables
		X10Appliance appliance;
		X10MotionSensor motionSensor;
		
		ProxyX10Appliance applianceProxy;
		ProxyX10MotionSensor motionSensorProxy;

		// fetch the EventGenerator
		eventGenerator = EventGeneratorFactory.getEventGenerator( );
		eventGenerator.addEventListener(this);
		
		// initialize the ProtocolFactory
		System.out.println ("Attempting to initialize the ProtocolFactory\n");
		try {
		    ProtocolFactory.initProtocols();
		} catch ( Exception e ) {
			System.out.println ("\nExiting program due to trouble initializing ProtocolFactory");
			System.exit (-1);
		}
 
		// Initialize the DeviceManager
		DeviceManager deviceManager = DeviceManagerFactory.getDeviceManager( );
		
		// Instantiate the MotionSensor object
		motionSensorProxy = new ProxyX10MotionSensor ("Motion C16", 'C', 16);
		motionSensorProxy.addAppliance ("Appliance C1");
		motionSensorProxy.setDetectionPeriodEnabled(false);
		Calendar startTime = Calendar.getInstance( );
		startTime.set(Calendar.HOUR_OF_DAY,18);
		startTime.set(Calendar.MINUTE,0);
		motionSensorProxy.setStartTime(startTime);
		Calendar endTime = Calendar.getInstance( );
		endTime.set(Calendar.HOUR_OF_DAY,19);
		endTime.set(Calendar.MINUTE,0);
		motionSensorProxy.setEndTime(endTime);
		motionSensorProxy.setInactivityTimeEnabled(true);
		motionSensorProxy.setInactivityTime (10);
		deviceManager.addDevice(motionSensorProxy);
		
		// Instantiate the Appliance object
		applianceProxy = new ProxyX10Appliance ( "Appliance C1", 'C', 1);
		applianceProxy.setTriggerTimerEnabled (false);
		deviceManager.addDevice(applianceProxy);
		
		try {
			Thread.sleep(10000);
		}
		catch(Exception e){}
		
		System.out.println ("Attempt to turn ON ApplianceProxy");
		applianceProxy.setState(X10DeviceState.ON);
		
		deviceManager.updateDevice(applianceProxy);
		
		try {
			Thread.sleep(10000);
		}
		catch(Exception e){}
		
		System.out.println ("Attempt to turn Off ApplianceProxy");
		applianceProxy.setState(X10DeviceState.OFF);
		deviceManager.updateDevice(applianceProxy);
		
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {

		// Enable Debugging
		System.setProperty("DEBUG", "1");

		// Instantiate the Test Controller
		new TestDeviceManagerProtocol( );

		//********************************************
		// Prompt to enter a command
		//********************************************

		System.out.println("Type exit to quit");
		System.out.print("SYSTEM>");

		BufferedReader keyboardInput;
		keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		String command = "";

		try {
			while(!command.equalsIgnoreCase("exit")) {
				command = keyboardInput.readLine();
				if(!command.equalsIgnoreCase("exit")) {
					System.out.print("\nSYSTEM>");
				}
			}
		} finally {
			System.out.println("\nDisconnecting from ProtocolFactory");
			try {
			    ProtocolFactory.shutdown( );
			} catch ( Exception e ) {};
		}
	}
	
	public void processProtocolEvent ( X10ProtocolEvent protocolEvent ) {
		LoggingUtilities.logInfo(this.getClass( ).getCanonicalName(), "processProtocolEvent",
				 "Message Received:: " + (X10ProtocolEvent)(Event)protocolEvent );
	}

	public void processTimerEvent ( TimerEvent timerEvent ) {
		LoggingUtilities.logInfo(this.getClass( ).getCanonicalName(), "processTimerEvent",
				 "Message Received:: " + (TimerEvent)(Event)timerEvent );

	}

	public void processDeviceEvent( X10DeviceEvent deviceEvent ) {
		LoggingUtilities.logInfo(this.getClass( ).getCanonicalName(), "processDeviceEvent",
				 "Message Received:: " + (X10DeviceEvent)(Event)deviceEvent );
	}

}
