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

package com.retroduction.carma.application.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CLIValidator {

	public static final String USER_CONFIG_OPTION_LONG = "userConfig";

	public static final String USER_CONFIG_OPTION_SHORT = "uc";

	public static final String ASSIGNMENT_CONFIG_OPTION_LONG = "assignmentConfig";

	public static final String ASSIGNMENT_CONFIG_OPTION_SHORT = "ac";

	public CLIValidator() {
		this.initOptions();
	}

	public CommandLine readCLI(String[] args) throws ParseException {

		return new GnuParser().parse(this.initOptions(), args);

	}

	private Options initOptions() {

		Options definedOptions = new Options();

		Option userConfigFile = OptionBuilder.withArgName("file").hasArg().withDescription(
				"use given file as user configuration definition").withLongOpt(USER_CONFIG_OPTION_LONG).isRequired(
				false).create(USER_CONFIG_OPTION_SHORT);

		definedOptions.addOption(userConfigFile);

		Option assignmentConfigFile = OptionBuilder.withArgName("file").hasArg().withDescription(
				"use given file as assignment configuration definition").withLongOpt(ASSIGNMENT_CONFIG_OPTION_LONG)
				.isRequired(false).create(ASSIGNMENT_CONFIG_OPTION_SHORT);

		definedOptions.addOption(assignmentConfigFile);

		return definedOptions;
	}

	public void help() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("BasicDriver", this.initOptions());
	}

}
