/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.core.om;

import java.util.HashSet;
import java.util.Set;

/**
 * Class which allows defining associations between classes and test classes.
 * 
 * @author arau
 * 
 */
public class TestedClassInfo extends PersistentClassInfo {

	private Set<PersistentClassInfo> associatedTestNames;

	public TestedClassInfo(String qualifiedClassName) {
		super(qualifiedClassName);
		this.associatedTestNames = new HashSet<PersistentClassInfo>();
	}

	public TestedClassInfo(String className, String packageName) {
		super(className, packageName);
		this.associatedTestNames = new HashSet<PersistentClassInfo>();
	}

	public TestedClassInfo(PersistentClassInfo classInfo) {
		super(classInfo);
		this.associatedTestNames = new HashSet<PersistentClassInfo>();
	}

	public Set<PersistentClassInfo> getAssociatedTestNames() {
		return this.associatedTestNames;
	}

}