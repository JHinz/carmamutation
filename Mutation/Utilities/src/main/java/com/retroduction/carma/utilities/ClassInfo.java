package com.retroduction.carma.utilities;

public class ClassInfo implements Comparable<ClassInfo> {

	private String packageName;

	private String className;

	private String fullyQualifiedClassName;

	private ClassInfo() {
	}

	public ClassInfo(String className, String packageName) {
		super();
		extractClassNameInfo(className, packageName);
	}

	public ClassInfo(String fullyQualifiedClassName) {
		super();
		extractClassNameInfo(fullyQualifiedClassName);
	}

	private void extractClassNameInfo(String fullyQualifiedClassName) {

		int lastDotIndex = fullyQualifiedClassName.lastIndexOf(".");

		if (lastDotIndex > 0) {
			setClassName(fullyQualifiedClassName.substring(lastDotIndex + 1, fullyQualifiedClassName.length()));
			setPackageName(fullyQualifiedClassName.substring(0, lastDotIndex));
		} else {
			setClassName(fullyQualifiedClassName);
			setPackageName("");
		}

		setFullyQualifiedClassName(fullyQualifiedClassName);
	}

	private void extractClassNameInfo(String className, String packageName) {

		setClassName(className);
		setPackageName(packageName);

		if ("".equals(packageName))
			setFullyQualifiedClassName(className);
		else
			setFullyQualifiedClassName(packageName + "." + className);

	}

	public String getFullyQualifiedClassName() {
		return fullyQualifiedClassName;
	}

	private void setFullyQualifiedClassName(String fullyQualifiedClassName) {
		this.fullyQualifiedClassName = fullyQualifiedClassName;
	}

	public String getClassName() {
		return className;
	}

	private void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	private void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int compareTo(ClassInfo o) {
		return fullyQualifiedClassName.compareTo(o.getFullyQualifiedClassName());
	}

	@Override
	public boolean equals(Object obj) {
		return fullyQualifiedClassName.equals(((ClassInfo) obj).getFullyQualifiedClassName());
	}

	@Override
	public int hashCode() {
		return fullyQualifiedClassName.hashCode();
	}
}