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
	public static void initProtocol( ) {
		// instantiate all the possible protocols here
		if (protocolInitialized == false) {
			try {
			    x10Protocol = new CM11A_X10Protocol( "/dev/ttyUSB0" );
			} catch ( Exception e ) {};
		}
	}
}
