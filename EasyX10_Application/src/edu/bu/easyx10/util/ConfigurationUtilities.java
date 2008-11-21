package edu.bu.easyx10.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.bu.easyx10.device.Device;
import edu.bu.easyx10.gui.User;

/**
 * 
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 16, 2008
 */
public class ConfigurationUtilities {
	
	private static final String SYS_CONFIG_FILE_NAME = "SysConfig.xml";
	
	/**
	 * @return
	 */
	public static SystemConfiguration getSystemConfiguration(){
		return getSystemConfiguration(SYS_CONFIG_FILE_NAME);
	}
	
	/**
	 * @param filePath
	 * @return
	 */
	public static SystemConfiguration getSystemConfiguration(
			String filePath){
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
	 * @return
	 */
	public static ArrayList<Device> getDeviceConfiguration(){
		// @ TODO Fill in Get Device Configuration Code
		return new ArrayList<Device>();
	}

	/**
	 * @param deviceList
	 */
	public static void setDeviceConfiguration(ArrayList<Device> deviceList){
		// @ TODO Fill in Set Device Config Code
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoggingUtilities.logInfo(ConfigurationUtilities.class.getCanonicalName(),
				"main()", "Starting ConfigUtil test method");
		writeDefaultSysConfigFile();
		readDefaultSysConfigFile();
	}
	
	/**
	 * This method is used to read the Default System Configuration file.
	 */
	private static void readDefaultSysConfigFile(){
		
		SystemConfiguration sysConfig = getSystemConfiguration("DefaultSysConfig.xml" );
		
		System.out.println("Port Name = " + sysConfig.getCm11aPortName());
		System.out.println("User List:");
		for( User user : sysConfig.getUsers() ){
			System.out.println("\t" + user);
		}
	}

	/**
	 * This method is used to generate a template XML file for
	 * the Configuration Data.
	 */
	private static void writeDefaultSysConfigFile(){
		SystemConfiguration sysConfig = new SystemConfiguration();
		sysConfig.setCm11aPortName("COM4");
		
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
