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

public class Logger implements ILog {

	private Log log;

	public void setLog(Log log) {
		this.log = log;
	}

	public void debug(Object message) {
		this.log.debug(message);
	}

	public void debug(Object message, Throwable t) {
		this.log.debug(message, t);
	}

	public void error(Object message) {
		this.log.equals(message);
	}

	public void error(Object message, Throwable t) {
		this.log.error(message, t);
	}

	public void fatal(Object message) {
		this.log.fatal(message);
	}

	public void fatal(Object message, Throwable t) {
		this.log.fatal(message, t);
	}

	public void info(Object message) {
		this.log.info(message);
	}

	public void info(Object message, Throwable t) {
		this.log.info(message, t);
	}

	public void trace(Object message) {
		this.log.trace(message);
	}

	public void trace(Object message, Throwable t) {
		this.log.trace(message, t);
	}

	public void warn(Object message) {
		this.log.warn(message);
	}

	public void warn(Object message, Throwable t) {
		this.log.warn(message, t);
	}

}
