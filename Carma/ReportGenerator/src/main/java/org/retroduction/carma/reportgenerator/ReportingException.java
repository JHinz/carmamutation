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
 * @author arau
 * 
 */
public class ReportingException extends Exception {

	private static final long serialVersionUID = 6266658828827977046L;

	private String message;

	public ReportingException(String message) {
		super();
		this.message = message;
	}

	public ReportingException(String message, Throwable throwable) {
		super();
		this.message = message;
		initCause(throwable);
	}

	@Override
	public String getMessage() {
		return message;
	}

}
