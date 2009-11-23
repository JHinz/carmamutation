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
