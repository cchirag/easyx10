package edu.bu.easyx10.protocol;

import edu.bu.easyx10.protocol.CM11A_X10Protocol;

/**
 * This class provides the singleton generator for the Protocol class.
 * The initProtocol( ) must be called at least once to cause all the
 * protocols to be instantiated.
 * 
 * @author   Jim Duda
 * @revision please refer to sub version
 * @data     11/5/08 
 *
 */
public class ProtocolFactory {

	// declare our private variables
	private static boolean protocolInitialized = false;
	private static CM11A_X10Protocol x10Protocol;

	// create a static default constructor which is the singleton design pattern
	private ProtocolFactory ( ) {
		
	}

	/**
	 * The initProtocol method is responsible for instantiating all the 
	 * available protocols.  We only want to do this once, so we guard
	 * it with synchronized and a boolean control flag.
	 */
	public synchronized static void initProtocols( ) throws Exception {
		
		// create some local method variables    
		String osType = "";
		
        // Determine our operating system environment			
		try {
	      osType= System.getProperty("os.arch");
	      System.out.println("Operating system type =>" + osType);
	    } catch (Exception e) {
	      System.out.println("Exception caught =" + e.getMessage());
	    }

		// instantiate all the possible protocols here
		// @TODO - the serial port needs to come from the configuration package
 		if (protocolInitialized == false) {

 			// Initialize the X10 Protocol Driver
 			String x10Port;

 			// Determine the default portName
 			if (osType.equals("linux")) {
 			    x10Port = "/dev/ttyUSB0";
 			} else if (osType.equals("amd64")) {
 			    x10Port = "/dev/ttyUSB0";
 			} else {
 			    x10Port = "COM4";
 			}

 		    // Now instantiate the x10Protocol using our default port name.
 		    try {
			    x10Protocol = new CM11A_X10Protocol( x10Port );
			} catch ( Exception e ) {
				System.out.println ( "Error attempting to initialize CM11A_X10Protocol\n");
				System.out.println ( e );
				throw e;
			};
			
			// finish by marking the protocol factory as initialized
			protocolInitialized = true;
		}
	}
	
	/**
	 * The shutdown method is responsible for shuting down each of the various
	 * protocols by simply removing their references which should result in the
	 * orderly shutdown of each protocol class via their finalize methods.
	 * 
	 */
	public synchronized static void shutdown( ) {
		// Shutdown all the various protocols by deleting their references.
		if (protocolInitialized) {
			x10Protocol.close();
		    x10Protocol = null;
		}
	}
}
