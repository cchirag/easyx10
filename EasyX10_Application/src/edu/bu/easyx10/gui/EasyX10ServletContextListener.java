package edu.bu.easyx10.gui;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.bu.easyx10.util.ConfigurationUtilities;
import edu.bu.easyx10.util.LoggingUtilities;
import edu.bu.easyx10.util.SystemConfiguration;

/**
 * 
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 24, 2008
 */
public class EasyX10ServletContextListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	//@Override
	public void contextInitialized(ServletContextEvent sce) {

		// Retrieve the file path for the configuration file
		String configFilePath = 
			sce.getServletContext().getRealPath( SystemConfiguration.FILE_NAME );
		
		// Set the path for the configuration file
		ConfigurationUtilities.setSystemConfigFilePath(configFilePath);
		LoggingUtilities.logInfo(this.getClass().getCanonicalName(), 
				"contextInitialized", "Set Config File Path To " + configFilePath);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	//@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Do nothing, not implemented for EasyX10
	}

}
