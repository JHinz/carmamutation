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

package com.retroduction.carma.core.om;

import com.retroduction.carma.utilities.ClassInfo;
import com.retroduction.carma.utilities.ToStringUtils;

/**
 * 
 * Bean which describes a class file on the filesystem. Comparable on the fully
 * qualified class name property. Either the fully qualified class name or the
 * class name and package are constructed automatically. Properties related to
 * the fully qualified class name are immutable.
 * 
 * @author arau
 * 
 */
public class PersistentClassInfo extends ClassInfo {

	private String classFile;

	private String sourceFile;

	public PersistentClassInfo(String qualifiedClassName) {
		super(qualifiedClassName);
	}

	public PersistentClassInfo(String className, String packageName) {
		super(className, packageName);
	}

	public PersistentClassInfo(PersistentClassInfo classInfo) {
		super(classInfo.getFullyQualifiedClassName());
		this.setClassFile(classInfo.getClassFile());
		this.setSourceFile(classInfo.getSourceFile());
	}

	public String getClassFile() {
		return this.classFile;
	}

	public void setClassFile(String classFile) {
		this.classFile = classFile;
	}

	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}

	public String getSourceFile() {
		return this.sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

}