/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.utilities;

public interface ILog {

	public void debug(Object message);

	public void debug(Object message, Throwable t);

	public void error(Object message);

	public void error(Object message, Throwable t);

	public void fatal(Object message);

	public void fatal(Object message, Throwable t);

	public void info(Object message);

	public void info(Object message, Throwable t);

	public void trace(Object message);

	public void trace(Object message, Throwable t);

	public void warn(Object message);

	public void warn(Object message, Throwable t);

}
