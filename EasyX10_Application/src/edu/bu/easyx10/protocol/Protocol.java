package edu.bu.easyx10.protocol;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.bu.easyx10.event.X10ProtocolEvent;
import edu.bu.easyx10.event.X10DeviceEvent;
import edu.bu.easyx10.event.TimerEvent;
import edu.bu.easyx10.event.EventGeneratorFactory;
import edu.bu.easyx10.event.EventGenerator;
import edu.bu.easyx10.event.EventHandlerListener;

/**
 * This class provides the abstract base class for the protocol package.
 * All concrete protocol classes must be derived from this class.  This
 * base class provides a Queue for inbound transmit messages.  Inbound
 * transmit messages arrive via the EventHandlerListener interface,
 * specifically, the processProtocolEvent( ) method.
 *
 * @author  Jim Duda
 * @version please refer to subversion
 * @date:   11/05/08
 */
public abstract class Protocol implements EventHandlerListener {
	
	// private member variables
	protected BlockingQueue<X10ProtocolEvent> m_txQueue;
	protected EventGenerator m_eventGenerator;

	/**
	 * Construct a new Protocol class.  The constructor always registers
	 * with the EventGenerator in order to receive inbound events.
	 */
	public Protocol ( ) {
		
		// fetch the eventGenerator instance from the EventGeneratorFactory
		m_eventGenerator = EventGeneratorFactory.getEventGenerator( );
		
		// register the Protocol class with the EventGenerator
		m_eventGenerator.addEventListener(this);
		
		// Create the txQueue
		m_txQueue = new LinkedBlockingQueue<X10ProtocolEvent>( );
	}
	
	/**
	 * Destructor for the Protocol class.  The destructor always unregisters
	 * with the EventGenerator in order to properly shutdown.
	 */
	public void finalize ( ) {
		// unregister the Protocol class with the EventGenerator
		m_eventGenerator.deleteEventListener(this);
	}
	
	/**
	 * Implement the implicit processProtocolEvent( ) handler.  Any concrete
	 * Protocol class which needs this listener must override this method.
	 * 
	 * @param listener
	 */
	public void processProtocolEvent ( X10ProtocolEvent e ) {
		
	}
	
	/**
	 * Implement the implicit processDeviceEvent( ) handler.  Any concrete
	 * Protocol class which needs this listener must override this method.
	 * 
	 * @param listener
	 */
	public void processDeviceEvent ( X10DeviceEvent e ) {
		
	}
	
	/**
	 * Implement the implicit processTimerEvent( ) handler.  Any concrete
	 * Protocol class which needs this listener must override this method.
	 * 
	 * @param listener
	 */
	public void processTimerEvent ( TimerEvent e ) {
		
	}
}
