package com.mutation;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;

public class MutationClassLoader extends URLClassLoader {

	private HashMap<String, Class> loadedClasses = new HashMap<String, Class>();

	public MutationClassLoader() {

		super(new URL[] {});

		String classPath = System.getProperty("java.class.path", ".");

		String[] classPathEntries = classPath.split(":");

		for (String entry : classPathEntries) {
			try {
				if (!entry.endsWith(".jar"))
					entry = entry + "/";
				addURL(new URL("file://" + entry));
				System.out.println("Adding classpath entry: " + "file://"
						+ entry);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	public MutationClassLoader(URL[] urls, ClassLoader parent,
			URLStreamHandlerFactory factory) {
		super(urls, Thread.currentThread().getContextClassLoader(), factory);
	}

	public MutationClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, Thread.currentThread().getContextClassLoader());
	}

	public MutationClassLoader(URL[] urls) {
		super(urls);
	}

	public Class overrideClass(String name, byte[] b, int off, int len) {
		Class clazz = defineClass(name, b, 0, b.length);
		this.loadedClasses.put(name, clazz);
		return clazz;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		if (loadedClasses.containsKey(name)) {
			return loadedClasses.get(name);
		}

		return super.findClass(name);

	}

}
