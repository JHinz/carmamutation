package mut.log;

/**
 * all possible event types
 * @author mike
 *
 */
public enum Event {
	/**
	 * loaded mutant byte code
	 */
	MUTANT_CLASS_LOADED,
	
	/**
	 * a unit test execution has been finished
	 */
	UNITTEST_FINISHED, 
	
	/**
	 * test class not found for the specified class ot be tested
	 */
	TESTCLASS_NOT_FOUND, 
	
	/**
	 * mutation test specifications have been created
	 * 
	 */
	TESTS_CREATED, 
	
	/**
	 * Execution of a mutation test finished
	 */
	EXECUTION_FINISHED, 
	
	/**
	 * start creation of mutation test specs
	 */
	START_CREATETESTS

}

