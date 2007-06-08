package com.retroduction.carma.application.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import com.retroduction.carma.application.util.CLIValidator;

import junit.framework.TestCase;

public class CLIValidatorTestCase extends TestCase {

	public void test_Help() throws ParseException {

		CLIValidator validator = new CLIValidator();

		validator.help();

	}

	public void test_DefaultOptions() throws ParseException {

		CLIValidator validator = new CLIValidator();

		CommandLine result = validator.readCLI(new String[] {});

		assertNull("assignment.xml", result.getOptionValue(CLIValidator.ASSIGNMENT_CONFIG_OPTION_SHORT));
	}

	public void test_OptionPassing() throws ParseException {

		CLIValidator validator = new CLIValidator();

		CommandLine result = validator.readCLI(new String[] { "--" + CLIValidator.ASSIGNMENT_CONFIG_OPTION_LONG,
				"otherConfigFile.txt" });

		assertEquals("otherConfigFile.txt", result.getOptionValue(CLIValidator.ASSIGNMENT_CONFIG_OPTION_SHORT));
	}

}
