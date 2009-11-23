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
public interface ICoreConfigConsts {
	/**
	 * name of the file that holds the runner configuration
	 * <br/>Value: "carma-runner.xml"
	 */
	String RUNNER_CONFIG_FILE = "carma-runner.xml";
	
	/**
	 * name of the file that holds the eventlistener configuration
	 * <br/>Value: "carma-eventlistener.xml"
	 */
	String EVENTLISTENER_CONFIG_FILE = "carma-eventlistener.xml";
	
	/**
	 * name of the file that holds the transistions configuration
	 * <br/>Value: "carma-transitions.xml"
	 */
	String TRANSITIONS_CONFIG_FILE = "carma-transitions.xml";
	
	/**
	 * name of the eventlistener bean. 
	 * <br/>Value: "eventListener"
	 * <br/>Type: IEventListener
	 */
	String BEAN_EVENTLISTENER = "eventListener";

	/**
	 * name of the XML report File bean.
	 * <br/>Value: "report.filename"
	 * <br/>Type: java.lang.String
	 */
	String BEAN_REPORTFILE = "report.filename";

	/**
	 * name of the bean that states the directories for the class files to be tested
	 * <br/>Value: "classesDir"
	 * <br/>Type: java.io.File[]
	 */
	String BEAN_CLASSESLOCATIONS = "classesDir";

	/**
	 * name of the bean that states the directories for the test class files
	 * <br/>Value: "testClassesDir"
	 * <br/>Type: java.io.File[]
	 */
	String BEAN_TESTCLASSESLOCATIONS = "testClassesDir";
	
	/**
	 * name of the bean that states the dependencies (jar files) of the project under test
	 * <br/>Value: "libraries"
	 * <br/>Type: java.net.URL[]
	 */
	String BEAN_LIBRARIES ="libraries";
	
	/**
	 * name of the bean that states the transitions to be applied for testing
	 * <br/>Value: "operators"
	 * <br/>Type: TransitionGroupConfig
	 */
	String BEAN_TRANSITIONS = "operators";

	/**
	 * name of the MutationRunner bean 
	 * <br/>Value: "runner"
	 * <br/>Type: MutationRunner
	 */
	String BEAN_RUNNER = "runner";


}
