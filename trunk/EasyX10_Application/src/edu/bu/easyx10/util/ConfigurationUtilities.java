package edu.bu.easyx10.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.bu.easyx10.device.X10Device;
import edu.bu.easyx10.gui.User;

/**
 * This class implements static methods that provide access to the EasyX10
 * configuration data.
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 16, 2008
 */
public class ConfigurationUtilities {
	
	/** The name of the Device Configuration File */
	public static final String DEVICE_FILE_NAME = "/WEB-INF/DeviceConfig.xml";

	// The paths for the SysConfig and DeviceConfig files 
	private static String systemConfigFilePath = "web/WEB-INF/SysConfig.xml";
	private static String deviceConfigFilePath = "web/WEB-INF/DeviceConfig.xml";
	
	/**
	 * Sets the path to the system configuration file.
	 * 
	 * @param filePath the system config file path to set
	 */
	public static void setSystemConfigFilePath(String filePath) {
		systemConfigFilePath = filePath;
	}
	
	/**
	 * Sets the path to the device configuration file.
	 * 
	 * @param filePath the device config file path to set
	 */
	public static void setDeviceConfigFilePath(String filePath) {
		deviceConfigFilePath = filePath;
	}
	
	/**
	 * Gets the SystemConfiguration data.
	 * 
	 * @return a system configuration object.
	 */
	public static SystemConfiguration getSystemConfiguration(){
		return getSystemConfiguration(systemConfigFilePath);
	}
	
	/**
	 * Gets the SystemConfiguration data.
	 * 
	 * @param filePath the path to the system configuration file.
	 * @return a system configuration object.
	 */
	public static SystemConfiguration getSystemConfiguration(String filePath){
		XStream xs = new XStream(new DomDriver());
        SystemConfiguration sysConfig = new SystemConfiguration();

        try {
            FileInputStream fis = new FileInputStream(filePath);
            xs.fromXML(fis, sysConfig);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return sysConfig;
	}
	
	/**
	 * Gets the Device configuration data.
	 * @return
	 */
	public static List<X10Device> getDeviceConfiguration(){
		return getDeviceConfiguration(deviceConfigFilePath);
	}
	
	/**
	 * Gets the Device configuration data.
	 * 
	 * @param filePath the path to the config file.
	 * @return a list of Device objects.
	 */
	public static List<X10Device> getDeviceConfiguration(String filePath){
		XStream xs = new XStream(new DomDriver());
        List<X10Device> devices = new ArrayList<X10Device>();

        try {
            FileInputStream fis = new FileInputStream(filePath);
            devices = (List<X10Device>)xs.fromXML(fis);
        } catch (FileNotFoundException ex) {
        	LoggingUtilities.logInfo("ConfigurationUtilities", 
        			"setDeviceConfiguration", "WARNING: Device " +
        					"Configuration File Not Found, will " +
        					"Create new one");
        }
        
        return devices;
	}

	/**
	 * Sets the Device configuration data.
	 * 
	 * @param deviceList the list of devices to write to the config file.
	 */
	public static void setDeviceConfiguration(List<X10Device> deviceList){
		//Create the xStream object
        XStream xs = new XStream();

        //Write the list of devices to the device config file
        try {
            FileOutputStream fs = new FileOutputStream(deviceConfigFilePath);
            xs.toXML(deviceList, fs);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
	}
	
	/**
	 * Test method used to test the configuration file functionality.
	 * 
	 * @param args runtime parameters.
	 */
	public static void main(String[] args) {
		LoggingUtilities.logInfo(ConfigurationUtilities.class.getCanonicalName(),
				"main()", "Starting ConfigUtil test method");
		writeDefaultSysConfigFile();
		readDefaultSysConfigFile();
	}
	
	/**
	 * A test method that is used to read the Default System Configuration file.
	 */
	private static void readDefaultSysConfigFile(){
		
		SystemConfiguration sysConfig = getSystemConfiguration("DefaultSysConfig.xml");
		
		System.out.println("Port Name = " + sysConfig.getCm11aPortName());
		System.out.println("User List:");
		for( User user : sysConfig.getUsers() ){
			System.out.println("\t" + user);
		}
		System.out.println("Floor Count = " + sysConfig.getFloorCount());
	}

	/**
	 * A test method that is used to generate a template XML file for
	 * the Configuration Data.
	 */
	private static void writeDefaultSysConfigFile(){
		SystemConfiguration sysConfig = new SystemConfiguration();

        // Determine the default CM11A port name base upon OS type
        String x10Port = "";
        String osType = "";

        // Determine our operating system environment
        try {
                osType= System.getProperty("os.arch");
                LoggingUtilities.logInfo("ConfigurationUtilities", "writeDefaultSystemConfigFile",
                                "Operating system type =>" + osType);
        } catch (Exception e) {
                LoggingUtilities.logInfo("ConfigurationUtilities", "writeDefaultSystemConfigFile",
                                "Exception caught = " + e.getMessage());
        }
        // Determine the default portName
        if (osType.equals("linux")) {
                x10Port = "/dev/ttyUSB0";
        } else if (osType.equals("i386")) {
                x10Port = "/dev/ttyUSB0";
        } else if (osType.equals("amd64")) {
                x10Port = "/dev/ttyUSB0";
        } else {
                x10Port = "COM4";
        }
        sysConfig.setCm11aPortName(x10Port);
		
		ArrayList<User> users = new ArrayList<User>();
		User user1 = new User();
		user1.setUsername("john");
		user1.setPassword("john1");
		
		User user2 = new User();
		user2.setUsername("damon");
		user2.setPassword("damon1");
		
		User user3 = new User();
		user3.setUsername("jim");
		user3.setPassword("jim1");
		
		User user4 = new User();
		user4.setUsername("bruce");
		user4.setPassword("bruce1");
		
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		
		sysConfig.setFloorCount(3);
		
		System.out.println("Preparing to Write Data");
		
		sysConfig.setUsers(users);
		
		//Serialize the object
        XStream xs = new XStream();

        //Write to a file in the file system
        try {
            FileOutputStream fs = new FileOutputStream("DefaultSysConfig.xml");
            xs.toXML(sysConfig, fs);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        
        System.out.println("Finished Writing Data");
	}
}
