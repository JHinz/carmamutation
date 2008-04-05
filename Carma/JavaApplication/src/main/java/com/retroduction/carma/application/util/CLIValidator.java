/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
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