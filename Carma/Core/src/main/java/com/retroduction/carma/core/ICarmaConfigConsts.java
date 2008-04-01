/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
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
