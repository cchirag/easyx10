package edu.bu.easyx10.protocol;

import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import edu.bu.easyx10.protocol.CM11A_X10Protocol;
import edu.bu.easyx10.util.ConfigurationUtilities;
import edu.bu.easyx10.util.LoggingUtilities;
import edu.bu.easyx10.util.SystemConfiguration;

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
	private static boolean m_protocolInitialized = false;
	private static CM11A_X10Protocol m_x10Protocol;
	private static Enumeration<Object> m_portIDs;

	/**
	 * Default implicit constructor
	 */
	private ProtocolFactory ( ) {

	}

	/**
	 * The initProtocol method is responsible for instantiating all the 
	 * available protocols.  We only want to do this once, so we guard
	 * it with synchronized and a boolean control flag.
	 */
	public synchronized static void initProtocols( ) throws Exception {

		// instantiate all the possible protocols here
		if (m_protocolInitialized == false) {

			/*
			 * Fetch the default port from the system configuration.
			 */
			SystemConfiguration sysConfig =  ConfigurationUtilities.getSystemConfiguration();
			String x10Port = sysConfig.getCm11aPortName();
			System.out.println("port: " + x10Port);
			
			/*
			 * This next block of code is used for debugging only.
			 * When debug is turned on, we dump all of the available
			 * COM port names to the console display.
			 */
			if (System.getProperty("DEBUG") != null) {
				m_portIDs = (Enumeration<Object>)CommPortIdentifier.getPortIdentifiers();
				while (m_portIDs.hasMoreElements()) {
					CommPortIdentifier portID = (CommPortIdentifier) m_portIDs.nextElement();
					if (portID.getPortType() == CommPortIdentifier.PORT_SERIAL) {
						LoggingUtilities.logInfo("ProtocolFactory", "initProtocols",
						        portID.getName());
						break;
					}
				}
			}

			// Now instantiate the x10Protocol using our default port name.
			try {
				m_x10Protocol = new CM11A_X10Protocol( x10Port );
			} catch ( Exception e ) {
				LoggingUtilities.logInfo("ProtocolFactory", "initProtocols",
                        "Error attempting to initialize CM11A_X10Protocol\n" + e);
				throw e;
			};

			// finish by marking the protocol factory as initialized
			m_protocolInitialized = true;
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
		if (m_protocolInitialized) {
			m_x10Protocol.close();
			m_x10Protocol = null;
		}
	}
}
