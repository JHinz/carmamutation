package com.retroduction.carma.application.resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.retroduction.carma.core.runner.ClassDescription;
import com.retroduction.carma.utilities.ClassNameAnalyzer;
import com.retroduction.carma.utilities.ClassNameAnalyzer.ClassNameInfo;

public class ConfigBasedResolver extends AbstractFilteredResolver {

	private Log log = LogFactory.getLog(ConfigBasedResolver.class);

	private File configurationFile;

	public File getConfigurationFile() {
		return configurationFile;
	}

	public void setConfigurationFile(File configurationFile) {
		this.configurationFile = configurationFile;
	}

	protected String readInputConfiguration(InputStream steram) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(steram));

		StringBuffer resultBuffer = new StringBuffer();

		String line;
		while ((line = reader.readLine()) != null) {
			resultBuffer.append(line + "\n");
		}

		return resultBuffer.toString();
	}

	public List<ClassDescription> parseInputConfiguration(String inputConfig) {

		List<ClassDescription> result = new ArrayList<ClassDescription>();

		StringTokenizer lineTokenzier = new StringTokenizer(inputConfig, "\n");

		while (lineTokenzier.hasMoreTokens()) {

			String line = lineTokenzier.nextToken().replaceAll(" ", "").trim();

			if (line.startsWith("#"))
				continue;

			int commentChar = line.indexOf("#");

			if (commentChar >= 0)
				line = line.substring(0, commentChar);

			if (!line.contains("="))
				continue;

			int splitIndex = line.indexOf("=");

			if (splitIndex <= 0)
				continue;

			ClassNameAnalyzer analyzer = new ClassNameAnalyzer();

			String fqClassName = line.substring(0, splitIndex);
			ClassNameInfo info = analyzer.extractClassNameInfo(fqClassName);

			ClassDescription clazz = new ClassDescription();
			clazz.setClassName(info.getClassName());
			clazz.setPackageName(info.getPackageName());

			line = line.substring(splitIndex + 1, line.length());

			splitIndex = line.indexOf("=");

			if (splitIndex > 0)
				continue;

			StringTokenizer elementTokenizer = new StringTokenizer(line, ",");

			clazz.setAssociatedTestNames(new HashSet<String>());

			while (elementTokenizer.hasMoreTokens()) {
				clazz.getAssociatedTestNames().add(elementTokenizer.nextToken());

			}

			result.add(clazz);

		}

		return result;

	}

	public List<ClassDescription> resolve() {

		String inputConfiguration;
		try {
			FileInputStream reader = new FileInputStream(getConfigurationFile());
			inputConfiguration = readInputConfiguration(reader);
		} catch (IOException e1) {
			e1.printStackTrace();
			return new ArrayList<ClassDescription>();
		}

		return parseInputConfiguration(inputConfiguration);
	}

}
