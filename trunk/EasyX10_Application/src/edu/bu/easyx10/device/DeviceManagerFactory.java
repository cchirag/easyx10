package edu.bu.easyx10.device;

/**
 * This class provides the singleton generator for the DeviceManager class.
 * All classes which desire to use the DeviceManager should fetch the object
 * reference using the getDeviceManager method.
 * 
 * @author   winderjj
 * @revision please refer to sub version
 * @data     11/20/08 
 *
 */
public class DeviceManagerFactory {

	// declare our private variables
	private static DeviceManager m_deviceManager = null;

	// create a static default constructor which is the singleton design pattern
	private DeviceManagerFactory ( ) {
		
	}

	// return the deviceManager, create one new object when not yet created
	public static DeviceManager getDeviceManager() {
		if (m_deviceManager == null) {
			m_deviceManager = new DeviceManager();
		}
		return m_deviceManager;
	}

}
