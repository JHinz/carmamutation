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

import junit.framework.TestCase;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

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
