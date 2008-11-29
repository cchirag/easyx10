package edu.bu.easyx10.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements a couple of static methods that provide the
 * EasxyX10 application a way to log error and informational messages.
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 20, 2008
 */
public class LoggingUtilities {
	
	/**
	 * Logs an error message.
	 * 
	 * @param className the calling class.
	 * @param methodName the calling method.
	 * @param errorText the text to log.
	 */
	public static void logError(String className, 
			String methodName, String errorText){
		Logger.getLogger("easyx10").logp(Level.SEVERE, className,
				methodName, errorText);
	}
	
	/**
	 * Logs an informational message.
	 * 
	 * @param className the calling class.
	 * @param methodName the calling method.
	 * @param errorText the text to log.
	 */
	public static void logInfo(String className, 
			String methodName, String infoText){
		Logger.getLogger("easyx10").logp(Level.INFO, className,
				methodName, infoText);
	}
}
