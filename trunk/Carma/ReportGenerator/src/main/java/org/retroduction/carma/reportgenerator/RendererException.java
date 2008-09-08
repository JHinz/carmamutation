/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator;

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
