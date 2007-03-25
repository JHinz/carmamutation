package mut;

/**
 * Specification of a mutant
 * @author mike
 *
 */
public class Mutant {
	@Override
	public boolean equals(Object other) {
		if(other instanceof Mutant){
			return byteCode.equals( ((Mutant)other).byteCode );
		}
		return false;
	}
	@Override
	public int hashCode() {
		return byteCode.hashCode();
	}
	@Override
	public String toString() {
		return name + " " +className +" " +mutationType +" " +changeDescription;
	}
	
	private String name;
	private String className;
	private byte[] byteCode;
	private SourceCodeMapping sourceMapping;
	private String changeDescription;
	private EMutationType mutationType;
	private EMutationOperator mutationOperator;
	public byte[] getByteCode() {
		return byteCode;
	}
	public void setByteCode(byte[] byteCode) {
		this.byteCode = byteCode;
	}
	public String getChangeDescription() {
		return changeDescription;
	}
	public void setChangeDescription(String changeDescription) {
		this.changeDescription = changeDescription;
	}
	public EMutationType getMutationType() {
		return mutationType;
	}
	public void setMutationType(EMutationType mutationType) {
		this.mutationType = mutationType;
	}
	public SourceCodeMapping getSourceMapping() {
		return sourceMapping;
	}
	public void setSourceMapping(SourceCodeMapping sourceMapping) {
		this.sourceMapping = sourceMapping;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EMutationOperator getMutationOperator() {
		return mutationOperator;
	}
	public void setMutationOperator(EMutationOperator mutationOperator) {
		this.mutationOperator = mutationOperator;
	}
}
