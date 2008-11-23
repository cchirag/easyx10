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

		// Iterate though our existing m_eventListeners and determine if the
		// new object is already in the list.
		ListIterator<EventHandlerListener> i = m_eventListeners.listIterator( );
		boolean found = false;
		while (i.hasNext( )) {
			if (i.next( ) == listener) {
				found = true;
			}
		}

		// If the new object is not already in the list, let's add it to the list.
		if (!found) {
			m_eventListeners.add( listener );
			LoggingUtilities.logInfo(this.getClass( ).getCanonicalName(), "addEventListener",
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

		// Iterate though our existing m_eventListeners and determine if the
		// object is in the list.  If so, simply remove it.
		ListIterator<EventHandlerListener> i = m_eventListeners.listIterator( );
		while (i.hasNext( )) {
			if (i.next( ) == listener) {
				m_eventListeners.remove(i);
			}
		}
		// return the Mutex now
		m_listSemaphore.release();
	}

	/**
	 * The fireEvent( ) method is called to send an Event to all registered listeners.
	 * The destination method is a function of the Event type.  As such, the Event
	 * type is responsible for routing to the proper receiving method.
	 *
	 * @param e Event which is to be delivered.
	 */
	public void fireEvent ( Event e ) {
		
		// First, let's acquire the Mutex to allow only one updater of the list
		m_listSemaphore.acquireUninterruptibly();
		List<EventHandlerListener> localList = new ArrayList<EventHandlerListener>( );
		localList.addAll(m_eventListeners);
		// return the Mutex now
		m_listSemaphore.release();
		
		// Iterate though our existing m_eventListeners and send the Event to all.
		ListIterator<EventHandlerListener> i = localList.listIterator( );
		while (i.hasNext( )) {
			EventHandlerListener object = i.next( );
			LoggingUtilities.logInfo(this.getClass( ).getCanonicalName(), "fireEvent",
					 "EventType:: " + e.getClass( ).getCanonicalName( ) + " Event:: " + e.toString( ) + " To:: " + object.getClass( ).getCanonicalName());
			e.fireEvent( object );
		}
	}
}
