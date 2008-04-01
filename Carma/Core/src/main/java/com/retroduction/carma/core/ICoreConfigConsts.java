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
