package edu.bu.easyx10.event;

/**
 * This class provides the singleton generator for the EventGenerator class.
 * All classes which desire to use the EventGenerator should fetch the object
 * reference using the getEventGenerator method.
 * 
 * @author   Jim Duda
 * @revision please refer to sub version
 * @data     11/5/08 
 *
 */
public class EventGeneratorFactory {

	// declare our private variables
	private static EventGenerator m_eventGenerator = null;

	// create a static default constructor which is the singleton design pattern
	private EventGeneratorFactory ( ) {
		
	}

	// return the eventGenerator, create one new object when not yet created
	public static EventGenerator getEventGenerator() {
		if (m_eventGenerator == null) {
			m_eventGenerator = new EventGenerator();
		}
		return m_eventGenerator;
	}

}
