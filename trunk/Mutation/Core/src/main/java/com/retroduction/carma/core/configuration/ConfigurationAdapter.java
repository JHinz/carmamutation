package com.retroduction.carma.core.configuration;

import java.util.HashMap;
import java.util.StringTokenizer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class ConfigurationAdapter implements BeanFactoryPostProcessor {

	public static final String NESTEDRESOLVER = "nestedresolver";

	public final static String CLASS_DIRECTORIES = "classDirectories";

	public final static String TESTCLASS_DIRECTORIES = "testClassDirectories";

	public final static String SOURCE_DIRECTORY = "sourceDirectory";

	public final static String CLASSMATCHRESOLVER_TESTCASESUFFIX = "classMatchResolver_testCaseSuffix";

	public final static String CONFIGBASEDRESOLVER_ASSIGNMENT = "configBasedResolver_assignment";

	public final static String XMLREPORTEVENTLISTENER_OUTPUTFILENAME = "xmlReportEventListener_outputFileName";

	public final static String XMLREPORTEVENTLISTENER_SURVIVORSONLY = "xmlReportEventListener_survivorsOnly";

	public final static String CONSOLEREPORTEVENTLISTENER_SUMMARYONLY = "consoleReportEventListener_summaryOnly";

	public final static String JUNIT3RUNNER_STOPONFIRSTFAILEDTEST = "junit3Runner_stopOnFirstFailedTest";

	public final static String LIBRARIES = "libraries";

	private HashMap<String, String> overrideProperties;

	public ConfigurationAdapter() {
		super();
		overrideProperties = new HashMap<String, String>();
	}

	public void overrideProperty(String key, String value) {
		overrideProperties.put(key, value);
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

		if (overrideProperties.containsKey(CLASS_DIRECTORIES)) {
			overrideURLList(CLASS_DIRECTORIES, beanFactory);
		}

		if (overrideProperties.containsKey(TESTCLASS_DIRECTORIES)) {
			overrideURLList(TESTCLASS_DIRECTORIES, beanFactory);
		}

		if (overrideProperties.containsKey(LIBRARIES)) {
			overrideURLList(LIBRARIES, beanFactory);
		}

		if (overrideProperties.containsKey(SOURCE_DIRECTORY)) {
			overrideStringProperty(SOURCE_DIRECTORY, beanFactory);
		}

		if (overrideProperties.containsKey(CLASSMATCHRESOLVER_TESTCASESUFFIX)) {
			overrideStringProperty(CLASSMATCHRESOLVER_TESTCASESUFFIX, beanFactory);
		}

		if (overrideProperties.containsKey(XMLREPORTEVENTLISTENER_OUTPUTFILENAME)) {
			overrideStringProperty(XMLREPORTEVENTLISTENER_OUTPUTFILENAME, beanFactory);
		}

		if (overrideProperties.containsKey(XMLREPORTEVENTLISTENER_SURVIVORSONLY)) {
			overrideBooleanProperty(XMLREPORTEVENTLISTENER_SURVIVORSONLY, beanFactory);
		}

		if (overrideProperties.containsKey(CONSOLEREPORTEVENTLISTENER_SUMMARYONLY)) {
			overrideBooleanProperty(CONSOLEREPORTEVENTLISTENER_SUMMARYONLY, beanFactory);
		}

		if (overrideProperties.containsKey(JUNIT3RUNNER_STOPONFIRSTFAILEDTEST)) {
			overrideBooleanProperty(JUNIT3RUNNER_STOPONFIRSTFAILEDTEST, beanFactory);
		}
		
		if (overrideProperties.containsKey(NESTEDRESOLVER)) {
			
			String resolverName = overrideProperties.get(NESTEDRESOLVER);
			
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition("resolver");
			
			ChildBeanDefinition child = new ChildBeanDefinition(resolverName);
			
			
			beanDefinition.getPropertyValues().addPropertyValue(NESTEDRESOLVER, child);
		}
	}

	private void overrideURLList(String key, ConfigurableListableBeanFactory beanFactory) {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition(key);

		ManagedList o = (ManagedList) beanDefinition.getPropertyValues().getPropertyValue("sourceList").getValue();

		o.clear();

		StringTokenizer tokenizer = new StringTokenizer(overrideProperties.get(key), ",");

		while (tokenizer.hasMoreTokens())
			o.add(new TypedStringValue(tokenizer.nextToken(), "java.net.URL"));
	}

	private void overrideStringProperty(String key, ConfigurableListableBeanFactory beanFactory) {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition(key);
		ConstructorArgumentValues constructorArgumentValues = (ConstructorArgumentValues) beanDefinition
				.getConstructorArgumentValues();

		TypedStringValue typedStringValue = new TypedStringValue(overrideProperties.get(key), "java.lang.String");
		constructorArgumentValues.clear();
		constructorArgumentValues.addGenericArgumentValue(typedStringValue);
	}

	private void overrideBooleanProperty(String key, ConfigurableListableBeanFactory beanFactory) {
		if (overrideProperties.containsKey(key)) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(key);
			ConstructorArgumentValues constructorArgumentValues = (ConstructorArgumentValues) beanDefinition
					.getConstructorArgumentValues();

			TypedStringValue typedStringValue = new TypedStringValue(overrideProperties.get(key), "java.lang.Boolean");
			constructorArgumentValues.clear();
			constructorArgumentValues.addGenericArgumentValue(typedStringValue);
		}
	}

}
