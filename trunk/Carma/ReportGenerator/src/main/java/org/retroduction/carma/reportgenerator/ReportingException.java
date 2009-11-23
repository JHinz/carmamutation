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
