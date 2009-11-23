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
