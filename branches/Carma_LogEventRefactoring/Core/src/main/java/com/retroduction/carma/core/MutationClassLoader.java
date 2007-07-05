package com.retroduction.carma.core;

import java.net.URL;
import java.net.URLClassLoader;

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

	private byte[] mutantByteCode;

	private String mutantClassName;

	public MutationClassLoader(URL[] urls, String mutantClassName, byte[] mutantByteCode, ClassLoader parent) {
		super(urls, parent);
		this.mutantClassName = mutantClassName;
		this.mutantByteCode = mutantByteCode;

	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		Class clazz;
		// TODO is that needed for all mutation dependencies? Find a better
		// solution (mike)

		// TODO: should be configurable via spring i think (alex) ->
		if (name.startsWith("junit")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("org.w3c")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("org.xml")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("com.sun")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("java")) {
			return super.loadClass(name, false);
		}

		if (name.startsWith("sun.")) {
			return super.loadClass(name, false);
		}

		if (name.equals(mutantClassName)) {
			clazz = super.defineClass(name, this.mutantByteCode, 0, this.mutantByteCode.length);
		} else {
			try {
				clazz = findLoadedClass(name);

				if (clazz != null)
					return clazz;

				return findClass(name);
			} catch (ClassNotFoundException e) {

				// Should never be the case as otherwise classes could be
				// resolved outside
				// of the "protected mutation" class loader which leads to
				// intransparent
				// faults
				throw e;
			}
		}
		return clazz;
	}
}
