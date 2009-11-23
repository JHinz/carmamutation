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

package com.retroduction.carma.core;

/**
 * this interface defines names of configuration files and spring beans
 * @author mike
 *
 */
public interface ICarmaConfigConsts {
	/**
	 * name of the file that holds the Carma Application configuration
	 * <br/>Value: "mutationConfig.xml"
	 */
	String CARMA_APPLICATION_CONFIG_FILE = "file:mutationConfig.xml";
	
	/**
	 * name of the Carma bean. 
	 * <br/>Value: "testDriver"
	 * <br/>Type: Carma
	 */
	String CORE_BEAN = "testDriver";

	/**
	 * name of the resolver bean that provides the classes and test set. 
	 * <br/>Value: "resolver"
	 * <br/>Type: IResolver
	 */
	String BEAN_RESOLVER = "resolver";

}
