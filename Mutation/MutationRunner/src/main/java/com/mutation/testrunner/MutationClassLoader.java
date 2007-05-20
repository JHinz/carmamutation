package com.mutation.testrunner;

import java.net.URL;
import java.net.URLClassLoader;

import org.objectweb.asm.ClassVisitor;

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

	public MutationClassLoader(String mutantClassName, byte[] mutantByteCode) {
		this(null, mutantClassName, mutantByteCode);
	}

	public MutationClassLoader(URL[] urls, String mutantClassName, byte[] mutantByteCode) {
		super(urls);
		this.mutantClassName = mutantClassName;
		this.mutantByteCode = mutantByteCode;

	}

	public MutationClassLoader(URL[] urls, String mutantClassName, byte[] mutantByteCode, ClassLoader parent) {
		super(urls);
		this.mutantClassName = mutantClassName;
		this.mutantByteCode = mutantByteCode;

	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		//System.out.print("Loading class: " + name);

		Class clazz;
		// TODO is that needed for all mutation dependencies? Find a better
		// solution
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
				
				if ( clazz != null) return clazz;
				
				return findClass(name);
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
				return null;
				//clazz = super.loadClass(name, false);
			}
		}
		return clazz;
	}
}
