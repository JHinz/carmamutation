/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.html;

public class RenderException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5037192677740440547L;

	public RenderException(Throwable cause) {
		super(cause);
	}

	public RenderException(String message, Throwable cause) {
		super(message, cause);
	}

}
