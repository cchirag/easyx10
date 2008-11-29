package edu.bu.easyx10.gui;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.bu.easyx10.util.ConfigurationUtilities;
import edu.bu.easyx10.util.LoggingUtilities;
import edu.bu.easyx10.util.SystemConfiguration;

/**
 * This class implements a ServletContextListener for the EasxyX10 Application. The
 * class only implements the contextInitialized method that is called on application
 * initialization.
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 24, 2008
 */
public class EasyX10ServletContextListener implements ServletContextListener {

	/**
	 * This method is called on Servlet Initialization and sets the path for the
	 * EasyX10 configuration files.
	 * 
	 * @param sce the reference to the context event 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {

		// Retrieve the file paths for the configuration files
		String sysConfigFilePath = 
			sce.getServletContext().getRealPath( SystemConfiguration.FILE_NAME );
		String deviceConfigFilePath =
			sce.getServletContext().getRealPath( ConfigurationUtilities.DEVICE_FILE_NAME );
		
		// Set the path for the configuration file
		ConfigurationUtilities.setSystemConfigFilePath(sysConfigFilePath);
		ConfigurationUtilities.setDeviceConfigFilePath(deviceConfigFilePath);
		
		LoggingUtilities.logInfo(this.getClass().getCanonicalName(), 
				"contextInitialized", "Set System Config File Path To " + sysConfigFilePath);
		LoggingUtilities.logInfo(this.getClass().getCanonicalName(), 
				"contextInitialized", "Set Device Config File Path To " + deviceConfigFilePath);
	}
	
	/** 
	 * This method is called when the application is terminated.  Not Implemented for EasyX10.
	 * 
	 * @param sce the reference to the context event
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// Do nothing, not implemented for EasyX10
	}

}
