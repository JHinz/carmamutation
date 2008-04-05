/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.chart;

public class ChartException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5243985248322943592L;

	public ChartException(Throwable cause) {
		super(cause);
	}

	public ChartException(String message, Throwable cause) {
		super(message, cause);
	}

}