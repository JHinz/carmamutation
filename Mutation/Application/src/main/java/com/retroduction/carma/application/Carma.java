package com.retroduction.carma.application;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.retroduction.carma.application.util.CLIValidator;
import com.retroduction.carma.core.Core;
import com.retroduction.carma.core.ICarmaConfigConsts;

public class Carma {

	private static final String DEFAULT_USER_CONFIG = "mutationConfig.xml";

	/**
	 * command line test runner, reads configuration from mutationconfig.xml
	 * 
	 * @throws ParseException
	 */
	public static void main(String[] args) throws MalformedURLException, FileNotFoundException, ParseException {

		CommandLine line = new CLIValidator().readCLI(args);

		List<String> springResources = new ArrayList<String>();

		if (line.hasOption(CLIValidator.USER_CONFIG_OPTION_SHORT)) {
			springResources.add("file:" + line.getOptionValue(CLIValidator.USER_CONFIG_OPTION_SHORT));
		} else {
			springResources.add("file:" + DEFAULT_USER_CONFIG);
		}

		AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext(springResources
				.toArray(new String[0]));

		appContext.registerShutdownHook();

		Core driver = (Core) appContext.getBean(ICarmaConfigConsts.CORE_BEAN);
		driver.execute();
	}

}
