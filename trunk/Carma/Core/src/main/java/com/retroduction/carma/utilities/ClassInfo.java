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

package com.retroduction.carma.utilities;

public class ClassInfo implements Comparable<ClassInfo> {

	private String packageName;

	private String className;

	private String fullyQualifiedClassName;

	public ClassInfo(String className, String packageName) {
		super();
		this.extractClassNameInfo(className, packageName);
	}

	public ClassInfo(String fullyQualifiedClassName) {
		super();
		this.extractClassNameInfo(fullyQualifiedClassName);
	}

	private void extractClassNameInfo(String fullyQualifiedClassName) {

		int lastDotIndex = fullyQualifiedClassName.lastIndexOf(".");

		if (lastDotIndex > 0) {
			this.setClassName(fullyQualifiedClassName.substring(lastDotIndex + 1, fullyQualifiedClassName.length()));
			this.setPackageName(fullyQualifiedClassName.substring(0, lastDotIndex));
		} else {
			this.setClassName(fullyQualifiedClassName);
			this.setPackageName("");
		}

		this.setFullyQualifiedClassName(fullyQualifiedClassName);
	}

	private void extractClassNameInfo(String className, String packageName) {

		this.setClassName(className);
		this.setPackageName(packageName);

		if ("".equals(packageName)) {
			this.setFullyQualifiedClassName(className);
		} else {
			this.setFullyQualifiedClassName(packageName + "." + className);
		}

	}

	public String getFullyQualifiedClassName() {
		return this.fullyQualifiedClassName;
	}

	private void setFullyQualifiedClassName(String fullyQualifiedClassName) {
		this.fullyQualifiedClassName = fullyQualifiedClassName;
	}

	public String getClassName() {
		return this.className;
	}

	private void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return this.packageName;
	}

	private void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int compareTo(ClassInfo o) {
		return this.fullyQualifiedClassName.compareTo(o.getFullyQualifiedClassName());
	}

	@Override
	public boolean equals(Object obj) {
		return this.fullyQualifiedClassName.equals(((ClassInfo) obj).getFullyQualifiedClassName());
	}

	@Override
	public int hashCode() {
		return this.fullyQualifiedClassName.hashCode();
	}
}