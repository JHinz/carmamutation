/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
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
