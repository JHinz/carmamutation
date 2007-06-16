package com.retroduction.carma.core.api.testrunners.om;

import java.util.Set;

import com.retroduction.carma.utilities.ToStringUtils;

public class ClassDescription implements Comparable<ClassDescription> {

	private String className;

	private String packageName;

	private String qualifiedClassName;

	private String classFile;

	private String sourceFile;

	private Set<String> associatedTestNames;

	public String getClassFile() {
		return classFile;
	}

	public void setClassFile(String classFile) {
		this.classFile = classFile;
	}

	/**
	 * 
	 * @return local class name withou package name
	 */
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
		if (getPackageName() == null || getPackageName().trim().equals(""))
			setQualifiedClassName(className.trim());
		else
			setQualifiedClassName(getPackageName().trim() + "." + className.trim());
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;

		if (packageName == null || packageName.trim().equals(""))
			setQualifiedClassName(getClassName().trim());
		else
			setQualifiedClassName(packageName.trim() + "." + getClassName().trim());
	}

	public Set<String> getAssociatedTestNames() {
		return associatedTestNames;
	}

	public void setAssociatedTestNames(Set<String> associatedTestNames) {
		this.associatedTestNames = associatedTestNames;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getQualifiedClassName() {
		return qualifiedClassName;
	}

	public void setQualifiedClassName(String qualifiedName) {
		this.qualifiedClassName = qualifiedName;
	}

	@Override
	public boolean equals(Object obj) {
		return getQualifiedClassName().equals(((ClassDescription) obj).getQualifiedClassName());
	}

	@Override
	public int hashCode() {
		return getQualifiedClassName().hashCode();
	}

	public int compareTo(ClassDescription o) {
		return this.getQualifiedClassName().compareTo(o.getQualifiedClassName());
	}
}