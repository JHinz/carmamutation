package com.mutation.testrunner;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * This class loader loads unit test and mutant.
 * 
 * Order:
 * 1. look for mutant byte code
 * 2. look in tests class path
 * 3. delegate to parent

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
		super(urls, parent);
		this.mutantClassName = mutantClassName;
		this.mutantByteCode = mutantByteCode;

	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class clazz;
		//TODO is that needed for all mutation dependencies? Find a better solution
		if(name.startsWith("junit")){
			return super.loadClass(name, false);
		}

		if(name.equals(mutantClassName)){
			clazz = super.defineClass(name, this.mutantByteCode, 0, this.mutantByteCode.length);
		}else{
			try{
				clazz = findClass(name);
			}catch(ClassNotFoundException e){
				clazz = super.loadClass(name, false);
			}
		}
		return clazz;
	}
}
