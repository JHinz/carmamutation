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
		beanDefinitionResources.add(resourceLocation);
	}

	public CarmaDriverSetup() throws CarmaException {
		// add base configuration from classpath
		beanDefinitionResources.add("classpath:com/retroduction/carma/config/carma-config.xml");

		try {
			// default settings
			configurationParameters = getDefaultConfiguration();

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
		configurationParameters.putAll(customProps);

	}

	public void init() {
		GenericApplicationContext newAppContext = new GenericApplicationContext(parentContext);
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(newAppContext);

		for (String res : beanDefinitionResources) {
			xmlReader.loadBeanDefinitions(res);
		}

		PropertyPlaceholderConfigurer customerPropsProcessor = new PropertyPlaceholderConfigurer();
		customerPropsProcessor.setProperties(configurationParameters);
		newAppContext.addBeanFactoryPostProcessor(customerPropsProcessor);

		newAppContext.refresh();
		newAppContext.registerShutdownHook();
		this.appContext = newAppContext;
	}

	public ApplicationContext getApplicationContext() {
		if (null == this.appContext) {
			init();
		}
		return this.appContext;
	}

	public Core getDriver() {
		Core driver = (Core) getApplicationContext().getBean(ICarmaConfigConsts.CORE_BEAN);
		return driver;
	}


	private Properties getDefaultConfiguration() throws IOException {
		Properties p = new Properties();
		p.load(Carma.class.getResourceAsStream("/com/retroduction/carma/config/carma-defaults.properties"));
		return p;
	}
}
