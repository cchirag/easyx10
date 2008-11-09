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
	 * available protocols.
	 */
	public synchronized static void initProtocols( ) throws Exception {
		// instantiate all the possible protocols here
		// @TODO - the serial port needs to come from the configuration package
		if (protocolInitialized == false) {
			try {
			    x10Protocol = new CM11A_X10Protocol( "/dev/ttyUSB0" );
			} catch ( Exception e ) {
				System.out.println ( "Error attempting to initialize CM11A_X10Protocol\n");
				System.out.println ( e );
				throw e;
			};
			// mark the protocol factory as initialized
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
