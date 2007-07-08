package com.retroduction.carma.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerFactory {

	public static Logger getLogger(String className) {
		Log log = LogFactory.getLog(className);
		Logger logger = new Logger();
		logger.setLog(log);
		return logger;
	}

	public static Logger getLogger(Class clazz) {
		Log log = LogFactory.getLog(clazz);
		Logger logger = new Logger();
		logger.setLog(log);
		return logger;
	}

}
