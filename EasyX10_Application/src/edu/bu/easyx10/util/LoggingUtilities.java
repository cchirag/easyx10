package edu.bu.easyx10.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 20, 2008
 */
public class LoggingUtilities {
	
	/**
	 * @param className
	 * @param methodName
	 * @param errorText
	 */
	public static void logError(String className, 
			String methodName, String errorText){
		Logger.getLogger("easyx10").logp(Level.SEVERE, className,
				methodName, errorText);
	}
	
	/**
	 * @param className
	 * @param methodName
	 * @param infoText
	 */
	public static void logInfo(String className, 
			String methodName, String infoText){
		Logger.getLogger("easyx10").logp(Level.INFO, className,
				methodName, infoText);
	}
}
