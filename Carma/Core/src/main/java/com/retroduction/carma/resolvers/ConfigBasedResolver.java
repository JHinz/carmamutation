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

package com.retroduction.carma.resolvers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.retroduction.carma.core.api.resolvers.ITestClassResolver;
import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

public class ConfigBasedResolver implements ITestClassResolver {

	private Logger log = LoggerFactory.getLogger(ConfigBasedResolver.class);

	private File configurationFile;

	public void setTestClassesPath(File[] testClassesPath) throws MalformedURLException {
	}

	private File getConfigurationFile() {
		return this.configurationFile;
	}

	public void setConfigurationFile(File configurationFile) {
		this.configurationFile = configurationFile;
	}

	protected String readInputConfiguration(InputStream stream) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		StringBuffer resultBuffer = new StringBuffer();

		String line;
		while ((line = reader.readLine()) != null) {
			resultBuffer.append(line + "\n");
		}

		return resultBuffer.toString();
	}

	public HashMap<String, Set<String>> parseInputConfiguration(String inputConfig, Set<String> classDescriptions) {

		HashMap<String, Set<String>> result = new HashMap<String, Set<String>>();

		for (String classDescription : classDescriptions) {
			result.put(classDescription, new HashSet<String>());
		}

		StringTokenizer lineTokenzier = new StringTokenizer(inputConfig, "\n");

		while (lineTokenzier.hasMoreTokens()) {

			String line = lineTokenzier.nextToken().replaceAll(" ", "").trim();

			if (line.startsWith("#")) {
				continue;
			}

			int commentChar = line.indexOf("#");

			if (commentChar >= 0) {
				line = line.substring(0, commentChar);
			}

			if (!line.contains("=")) {
				continue;
			}

			int splitIndex = line.indexOf("=");

			if (splitIndex <= 0) {
				continue;
			}

			String fqClassName = line.substring(0, splitIndex);

			Set<String> tests = result.get(fqClassName);

			if (tests == null) {
				this.log.info("Class" + fqClassName + " defined in assignment map but could not be found on disk");
				continue;
			}

			line = line.substring(splitIndex + 1, line.length());

			splitIndex = line.indexOf("=");

			if (splitIndex > 0) {
				continue;
			}

			StringTokenizer elementTokenizer = new StringTokenizer(line, ",");

			while (elementTokenizer.hasMoreTokens()) {
				tests.add(elementTokenizer.nextToken());
			}

			result.put(fqClassName, tests);
		}

		return result;

	}

	public HashMap<String, Set<String>> resolve(Set<String> classNames) {

		String inputConfiguration;
		try {
			FileInputStream reader = new FileInputStream(this.getConfigurationFile());
			inputConfiguration = this.readInputConfiguration(reader);
		} catch (IOException e1) {
			this.log.error("Error during reading assignment mapping", e1);
			return new HashMap<String, Set<String>>();
		}

		return this.parseInputConfiguration(inputConfiguration, classNames);
	}
}
