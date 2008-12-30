/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.core;

import java.net.URL;
import java.net.URLClassLoader;

import com.retroduction.carma.resolvers.Resolver;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

/**
 * This class loader loads unit test and mutant.
 * 
 * Order: 1. look for mutant byte code 2. look in tests class path 3. delegate
 * to parent
 * 
 * @author mike
 * 
 */
public class MutationClassLoader extends URLClassLoader {

	private Logger logger = LoggerFactory.getLogger(MutationClassLoader.class);

	private byte[] mutantByteCode;

	private String mutantClassName;

	public MutationClassLoader(URL[] urls, String mutantClassName,
			byte[] mutantByteCode, ClassLoader parent) {
		super(urls, parent);
		this.mutantClassName = mutantClassName;
		this.mutantByteCode = mutantByteCode;

		logger.debug("Created new custom class loader with urls: ");
		for (URL url : urls)
			logger.debug("  " + url.toString());
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		logger.debug("Resolving class with custom classloader: " + name
				+ " ClassLoaderID: " + this);

		// TODO is that needed for all mutation dependencies? Find a better
		// solution (mike)

		// TODO: should be configurable via spring i think (alex) ->
		if (name.startsWith("junit.")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("org.w3c.")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("org.xml.")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("com.sun.")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("java.")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("sun.")) {
			return super.loadClass(name, false);
		}

		Class<?> clazz;
		
		if (name.equals(this.mutantClassName)) {
			clazz = super.defineClass(name, this.mutantByteCode, 0, this.mutantByteCode.length);
		} else {
			try {
				clazz = this.findLoadedClass(name);

				if (clazz != null) {
					return clazz;
				}

				return this.findClass(name);
			} catch (ClassNotFoundException e) {

				// Should never be the case as otherwise classes could be
				// resolved outside
				// of the "protected mutation" class loader which leads to
				// hidden faults
				throw e;
			}
		}
		return clazz;
	}
}
