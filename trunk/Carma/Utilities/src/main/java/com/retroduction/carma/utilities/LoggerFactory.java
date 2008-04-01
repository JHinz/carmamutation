/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
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

	public static Logger getLogger(Class<?> clazz) {
		Log log = LogFactory.getLog(clazz);
		Logger logger = new Logger();
		logger.setLog(log);
		return logger;
	}

}
