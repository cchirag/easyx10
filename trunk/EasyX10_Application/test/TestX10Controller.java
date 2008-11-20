import java.io.*;
import edu.bu.easyx10.protocol.*;
import edu.bu.easyx10.event.*;

/**
 Application which exercises the FireCracker library, as well as providing
 a command-line interface to the FireCracker interface.
 */
public class TestX10Controller implements EventHandlerListener {

	private static EventGenerator eventGenerator;

	/**
	 * Constructor
	 */
	TestX10Controller ( ) {

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

	}

	public static void main(String args[]) throws IOException {

		// Enable Debugging
//		System.setProperty("DEBUG", "1");

		// Instantiate the Test Controller
		new TestX10Controller( );

		//********************************************
		// Prompt to enter a command
		//********************************************

		System.out.println("Type a command to send to a device or type exit to quit");
		System.out.print("SEND>");

		BufferedReader keyboardInput;
		keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		String command = "";
		X10ProtocolEvent protocolEvent = null;

		try {
			while(!command.equalsIgnoreCase("exit")) {
				command = keyboardInput.readLine();
				if(!command.equalsIgnoreCase("exit")) {
					//********************************************
					//Send the users Message
					//********************************************
					protocolEvent = decode(command);
					if (protocolEvent == null) {
						System.out.println("    Unrecognized command: " + command);
						usage( );
					} else {
						eventGenerator.fireEvent( protocolEvent );
						//********************************************
						//Tell the user their command was queued
						//********************************************
						System.out.println("Your command was delivered to CM11a");
					}
					System.out.print("\nSEND>");
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

	}

	public void processTimerEvent ( TimerEvent timerEvent ) {

	}

	public void processDeviceEvent( X10DeviceEvent deviceEvent ) {
		System.out.println ( "Message Received:: " + (X10DeviceEvent)(Event)deviceEvent );
	}

	private static X10ProtocolEvent decode(String str) {
		str = str.toUpperCase();
		int index;
		char house;
		String sunit;
		int unit;
		String command;
		try {
			if ((index = str.indexOf("_ON")) != -1) {
				house = str.charAt(0);
				sunit = str.substring(1,index);
				unit = Integer.parseInt(sunit);
				command = str.substring(index+1);
				return new X10ProtocolEvent ( "", house, unit, command );
			}
			if ((index = str.indexOf("_OFF")) != -1) {
				house = str.charAt(0);
				sunit = str.substring(1,index);
				unit = Integer.parseInt(sunit);
				command = str.substring(index+1);
				return new X10ProtocolEvent ( "", house, unit, command);
			}
			if ((index = str.indexOf("_BRIGHT")) != -1) {
				house = str.charAt(0);
				sunit = str.substring(1,index);
				unit = Integer.parseInt(sunit);
				command = str.substring(index+1);
				return new X10ProtocolEvent ( "", house, unit, command);
			}
			if ((index = str.indexOf("_DIM")) != -1) {
				house = str.charAt(0);
				sunit = str.substring(1,index);
				unit = Integer.parseInt(sunit);
				command = str.substring(index+1);
				return new X10ProtocolEvent ( "", house, unit, command);
			}
			return null;
		} catch ( Exception e ){
			return null;
		}
	}

	private static void usage() {
		System.out.println("usage: java " + TestX10Controller.class.getName());
		System.out.println("    command examples: A1_ON B15_OFF A9_DIM B10_BRIGHT");
	}
}
