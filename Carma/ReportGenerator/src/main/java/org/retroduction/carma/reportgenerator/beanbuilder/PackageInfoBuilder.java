/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.beanbuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.retroduction.carma.xmlreport.om.ClassUnderTest;

/**
 * @author arau
 *
 */
public class PackageInfoBuilder {

	private Map<String, Set<ClassUnderTest>> classesByPackage;

	public PackageInfoBuilder(Collection<ClassUnderTest> classes) {
		this.classesByPackage = new HashMap<String, Set<ClassUnderTest>>();
		for (ClassUnderTest c : classes) {
			String packageName = c.getPackageName();
			if (!this.classesByPackage.containsKey(packageName)) {
				this.classesByPackage.put(packageName, new HashSet<ClassUnderTest>());
			}
			this.classesByPackage.get(packageName).add(c);
		}
	}

	public Set<ClassUnderTest> getClassesInPackage(String packageName) {
		if (this.classesByPackage.containsKey(packageName)) {
			return this.classesByPackage.get(packageName);
		} else {
			return new HashSet<ClassUnderTest>();
		}
	}

	public Set<ClassUnderTest> getClassesByPackagePrefix(String packagePrefix) {
		Set<ClassUnderTest> result = new HashSet<ClassUnderTest>();
		if (null == packagePrefix) {
			return result;
		}
		for (String pack : this.classesByPackage.keySet()) {
			if (pack.startsWith(packagePrefix)) {
				result.addAll(this.classesByPackage.get(pack));
			}
		}
		return result;
	}

	public Set<String> getPackageNames() {
		return new TreeSet<String>(this.classesByPackage.keySet());
	}

	public Set<String> getSubPackages(String packagePrefix) {
		if (null == packagePrefix || "".equals(packagePrefix)) {
			return new TreeSet<String>(this.classesByPackage.keySet());
		}

		Set<String> packages = new TreeSet<String>();
		for (String name : this.classesByPackage.keySet()) {
			if (name.startsWith(packagePrefix)) {
				packages.add(name);
			}
		}
		return packages;
	}

}
