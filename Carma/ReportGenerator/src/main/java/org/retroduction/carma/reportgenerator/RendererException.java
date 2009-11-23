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
