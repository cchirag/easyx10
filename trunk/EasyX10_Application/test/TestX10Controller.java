import java.io.*;
import edu.bu.easyx10.protocol.*;
import edu.bu.easyx10.event.*;
import edu.bu.easyx10.event.EventGenerator;
import edu.bu.easyx10.event.X10ProtocolEvent;
import edu.bu.easyx10.event.EventHandlerListener;

/**
 Application which exercises the FireCracker library, as well as providing
 a command-line interface to the FireCracker interface.
 */
public class TestX10Controller implements EventHandlerListener {

	private static CM11A_X10Protocol Controller;
	private static EventGenerator eventGenerator;

	/**
	 * Constructor
	 */
	TestX10Controller ( String portName ) {

		// fetch the EventGenerator
		eventGenerator = EventGeneratorFactory.getEventGenerator( );
		eventGenerator.addEventListener(this);

		try {
			System.out.println("Connecting to CM11A on port " + portName);
			Controller = new CM11A_X10Protocol(portName);
		} catch (IOException e) {
			//  listPorts( );
			System.exit(1);
		}


	}

	public static void main(String args[]) throws IOException {

		String portName = "/dev/ttyUSB0";
		if (args.length > 0) {
			portName = args[0];
		}

		// Instantiate the Test Controller
		TestX10Controller Test = new TestX10Controller(portName );

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
						if (Controller != null) {
							Controller.processProtocolEvent(protocolEvent );
						}
						//********************************************
						//Tell the user their command was queued
						//********************************************
						System.out.println("Your command was delivered to CM11a");
					}
					System.out.print("\nSEND>");
				}
			}
		} finally {
			System.out.println("\nDisconnecting from CM11A");
			Controller.close( );
		}
	}

	public void processProtocolEvent ( Event protocolEvent ) {

	}

	public void processTimerEvent ( Event timerEvent ) {

	}

	public void processDeviceEvent( Event deviceEvent ) {
		System.out.println ( "Message Received:: " + (X10DeviceEvent)deviceEvent );
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

	/*
    private static void listPorts() {
        System.out.println("Listing available ports (possibly including fictional ones):");
        Enumeration e = FireCracker.getPorts();
        while(e.hasMoreElements()) {
            System.out.println("   " + e.nextElement());
        }
        System.out.println();
    }
	 */

}
