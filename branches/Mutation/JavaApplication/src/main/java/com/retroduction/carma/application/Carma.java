package com.retroduction.carma.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import com.retroduction.carma.application.util.CLIValidator;
import com.retroduction.carma.core.Core;

public class Carma {

	private static final String DEFAULT_USER_CONFIG = "carma.properties";

	/**
	 * command line test runner, reads configuration from mutationconfig.xml
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException {

		CommandLine line = new CLIValidator().readCLI(args);

		File customPropertiesFile;
		if (line.hasOption(CLIValidator.USER_CONFIG_OPTION_SHORT)) {
			customPropertiesFile = new File(line.getOptionValue(CLIValidator.USER_CONFIG_OPTION_SHORT));
		} else {
			customPropertiesFile = new File(DEFAULT_USER_CONFIG);
		}

		CarmaDriverSetup setup = new CarmaDriverSetup();

		Properties customProps = new Properties();
		try {
			customProps.load(new FileInputStream(customPropertiesFile));

		} catch (IOException e) {
			throw new CarmaException("Failed to load configuration", e);
		}
		setup.addCustomConfiguration(customProps);
		Core driver = setup.getDriver();
		driver.execute();

	}

}
