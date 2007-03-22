package mut;

/**
 * This enum defines the possible mutation operators.
 * See http://ise.gmu.edu/~offutt/mujava/mutopsMethod.pdf for description of each
 * mutation operator.
 * 
 * @author mike
 * 
 */
public enum MutationOperator {
	/**
	 * Arithmetic Operator Replacement
	 */
	AOR,
	/**
	 * Arithmetic Operator Insertion
	 */
	AOI,
	/**
	 * Arithmetic Operator Deletion
	 */
	AOD,
	/**
	 * Relational Operator Replacement
	 */
	ROR,
	/**
	 * Conditional Operator Replacement
	 */
	COR,
	/**
	 * Conditional Operator Insertion
	 */
	COI,
	/**
	 * Conditional Operator Deletion
	 */
	COD,
	/**
	 * Shift Operator Replacement Replace shift operators with other shift
	 * operators.
	 */
	SOR,
	/**
	 * Logical Operator Replacement
	 */
	LOR,
	/**
	 * Logical Operator Insertion
	 */
	LOI,
	/**
	 * Logical Operator Delete
	 */
	LOD,
	/**
	 * Short-Cut Assignment Operator Replacement
	 */
	ASRS

}
