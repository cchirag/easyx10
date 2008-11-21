package edu.bu.easyx10.gui;

/**
 * This class stores data about a user.
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 16, 2008
 */
public class User {
	
	private String username;
	private String password;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "Username: " + username +
			   " Password: " + password;
	}
	

}
