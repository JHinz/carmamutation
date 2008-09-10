/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.retroduction.carma.core.Core;
import com.retroduction.carma.core.ICarmaConfigConsts;

public class CarmaDriverSetup {
	private List<String> beanDefinitionResources = new ArrayList<String>();

	private Properties configurationParameters;

	private ApplicationContext parentContext;

	private ApplicationContext appContext;

	public void setParentContext(ApplicationContext parent) {
		this.parentContext = parent;
	}

	public void addBeanDefinitionResource(String resourceLocation) {
		this.beanDefinitionResources.add(resourceLocation);
	}

	public CarmaDriverSetup() throws CarmaException {
		// add base configuration from classpath
		this.beanDefinitionResources.add("classpath:org/retroduction/carma/integration/carma-config.xml");

		try {
			// default settings
			this.configurationParameters = this.getDefaultConfiguration();

		} catch (IOException e) {
			throw new CarmaException("failed to read default config", e);
		}
	}

	/**
	 * overrides default configuration with custom configuration attributes
	 * 
	 * @param customProps
	 */
	public void addCustomConfiguration(Properties customProps) {
		this.configurationParameters.putAll(customProps);

	}

	public void init() {
		GenericApplicationContext newAppContext = new GenericApplicationContext(this.parentContext);
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(newAppContext);

		for (String res : this.beanDefinitionResources) {
			xmlReader.loadBeanDefinitions(res);
		}

		PropertyPlaceholderConfigurer customerPropsProcessor = new PropertyPlaceholderConfigurer();
		customerPropsProcessor.setProperties(this.configurationParameters);
		newAppContext.addBeanFactoryPostProcessor(customerPropsProcessor);

		newAppContext.refresh();
		newAppContext.registerShutdownHook();
		this.appContext = newAppContext;
	}

	public ApplicationContext getApplicationContext() {
		if (null == this.appContext) {
			this.init();
		}
		return this.appContext;
	}

	public Core getDriver() {
		Core driver = (Core) this.getApplicationContext().getBean(ICarmaConfigConsts.CORE_BEAN);
		return driver;
	}

	private Properties getDefaultConfiguration() throws IOException {
		Properties p = new Properties();
		p.load(CarmaDriverSetup.class
				.getResourceAsStream("/org/retroduction/carma/integration/carma-defaults.properties"));
		return p;
	}
}
