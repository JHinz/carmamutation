/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class HelperClassLoader extends URLClassLoader {

	private String id;

	private URL[] urls;

	public HelperClassLoader(URL[] urls, String id, ClassLoader parent) {
		super(new URL[] {}, parent);
		this.id = id;
		this.urls = urls;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		if (name.startsWith("java"))
			return super.loadClass(name);

		if (name.equals("C"))
			return super.loadClass(name); // indirectly uses parent

		System.out.println("ClassLoader <" + id + "> tries to load class: " + name);

		for (URL url : urls) {
			File file = new File(url.getPath() + "/" + name + ".class");

			try {
				FileInputStream reader = new FileInputStream(file);
				byte[] input = new byte[10000];
				int length = reader.read(input);

				return defineClass(name, input, 0, length);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}
}
