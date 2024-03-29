package edu.bu.easyx10.event;

import edu.bu.easyx10.event.EventHandlerListener;
import edu.bu.easyx10.event.Event;
import edu.bu.easyx10.util.LoggingUtilities;

import java.util.*;
import java.util.concurrent.*;

/**
 * This class provides the Event Generator.  Events to be transmitted are
 * provided to the fireEvent method.  The fireEvent method distributes the
 * Event to the proper EventHandlerListener method for reception.  The
 * EventGenerator maintains a list of objects which are listening to
 * events.
 *
 * @author:  Jim Duda
 * @version: refer to EasyX10 subversion
 * @date:    10/30/08
 *
 */
public class EventGenerator {

	// Declare the private member variables.
	private static List<EventHandlerListener> m_eventListeners;
	private static Semaphore m_listSemaphore;

	/**
	 * Constructor - default implicit constructor
	 */
	public EventGenerator ( ) {
		// Create a new empty List
		m_eventListeners = new ArrayList<EventHandlerListener>( );
		
		// create a Mutex using the static semaphore
		m_listSemaphore = new Semaphore(1);
	}

	/**
	 * This method is called to provide a listener to the Event Generator to
	 * send new Events to when they arrive via the fireEvent( ) method.
	 *
	 * @param EventHandlerListner listener object which desires to receive Event objects.
	 */
	public void addEventListener ( EventHandlerListener listener ) {

		// First, let's acquire the Mutex to allow only one updater of the list
		m_listSemaphore.acquireUninterruptibly();

		// Add to the list, if it's not already in the list
		if (!m_eventListeners.contains(listener)) {
			m_eventListeners.add( listener );
			debug(this.getClass( ).getCanonicalName(), "addEventListener",
					 "Registered:: " + listener.getClass( ).getCanonicalName());
		} 
		
		// return the Mutex now
		m_listSemaphore.release();
	}

	/**
	 * This method is called to remove a listener from the list.
	 *
	 * @param EventHandlerListener listener which is to be removed.
	 */
	public void deleteEventListener ( EventHandlerListener listener ) {

		// First, let's acquire the Mutex to allow only one updater of the list
		m_listSemaphore.acquireUninterruptibly();

		// Delete the entry if it's in our list
		if (m_eventListeners.contains(listener)) {
			m_eventListeners.remove(listener);
		}
		// return the Mutex now
		m_listSemaphore.release();
	}

	/**
	 * This method is used to interface with the system logger.  We
	 * only print debug information if debugging is enabled for this class.
	 * 
	 * @param className
	 * @param methodName
	 * @param infoText
	 */
	public static void debug(String className, String methodName, String infoText) {
		if (System.getProperty("DEBUG_EVENT") != null) {
    		LoggingUtilities.logInfo(className,	methodName, infoText);
		}
	}
	/**
	 * The fireEvent( ) method is called to send an Event to all registered listeners.
	 * The destination method is a function of the Event type.  As such, the Event
	 * type is responsible for routing to the proper receiving method.
	 *
	 * @param e Event which is to be delivered.
	 */
	public void fireEvent ( Event event ) {
		
		// First, let's acquire the Mutex to allow only one updater of the list
		m_listSemaphore.acquireUninterruptibly();
		List<EventHandlerListener> localList = new LinkedList<EventHandlerListener>( );
		localList.addAll(m_eventListeners);
		// return the Mutex now
		m_listSemaphore.release();
		
		if (event == null) {
			LoggingUtilities.logError(this.getClass( ).getCanonicalName(), "fireEvent", "event is null");
		}
		
		// Iterate though our existing m_eventListeners and send the Event to all.
		ListIterator<EventHandlerListener> i = localList.listIterator( );
		while (i.hasNext( )) {
			EventHandlerListener object = i.next( );
			debug(this.getClass( ).getCanonicalName(), "fireEvent",
			     "EventType:: " + event.getClass( ).getCanonicalName( ) + 
			     " Event:: " + event.toString( ) + 
			     " To:: " + object.getClass( ).getCanonicalName());
			event.fireEvent( object );
		}
	}
}
