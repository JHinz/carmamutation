/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator;

/**
 * this exception is thrown if any problem occurs while creating a report from a template
 * @author mike
 *
 */
public class RendererException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -444781269464372459L;

	public RendererException(String arg0) {
		super(arg0);
	}

	public RendererException(Throwable arg0) {
		super(arg0);
	}

	public RendererException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
