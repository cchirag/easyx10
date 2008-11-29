package edu.bu.easyx10.util;

import java.util.ArrayList;
import edu.bu.easyx10.gui.User;

/**
 * This class stores the general system configuration data
 * required for the EasxyX10 Application.
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 16, 2008
 */
public class SystemConfiguration {
	
	public static final String FILE_NAME = "/WEB-INF/SysConfig.xml";
	
	// GUI Configuration
	private ArrayList<User> users;
	private int floorCount = 0;
	
	// Protocol Configuration
	private String cm11aPortName;
	
	/**
	 * Gets the CM11a port name.
	 * 
	 * @return the port
	 */
	public String getCm11aPortName() {
		return cm11aPortName;
	}
	
	/**
	 * Sets the CM11a port name.
	 * 
	 * @param port the port to set
	 */
	public void setCm11aPortName(String portName) {
		this.cm11aPortName = portName;
	}
	
	/**
	 * Gets the list of user objects.
	 * 
	 * @return the users
	 */
	public ArrayList<User> getUsers() {
		return users;
	}
	
	/**
	 * Sets the list of user objects.
	 * 
	 * @param users the users to set
	 */
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	/**
	 * Gets the number of floors.
	 * 
	 * @return the number of floors.
	 */
	public int getFloorCount(){
		return floorCount; 
	}
	
	/**
	 * Sets the number of floors.
	 * 
	 * @param floorCount the number of floors.
	 */
	public void setFloorCount(int floorCount) {
		this.floorCount = floorCount;
	}
}
