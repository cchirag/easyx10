package edu.bu.easyx10.device;

import edu.bu.easyx10.event.Event;
import edu.bu.easyx10.event.X10ProtocolEvent;
import edu.bu.easyx10.event.EventGeneratorFactory;
import edu.bu.easyx10.event.EventGenerator;
import edu.bu.easyx10.event.EventHandlerListener;

/**
 * The Device class is an abstract base class that’s used to model
 * physical devices within the EasyX10 system. A device has only two 
 * attributes; a name and a location. The name is the unique identifier 
 * amongst other devices in the system, and the location is a string 
 * representing its physical location in the GUI. For the scope of 
 * this project it includes Motion sensors and appliance modules; 
 * derived from X10Device. Keep in mind that the Device base class 
 * is abstract and could be expanded to support other device types 
 * in future releases. 

@author  Damon Gabrielle
@version please refer to subversion
@date:   11/06/08
*/

public abstract class Device implements EventHandlerListener{

}
