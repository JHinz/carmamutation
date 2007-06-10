package com.retroduction.carma.application.resolver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.retroduction.carma.core.runner.ClassDescription;

public class AbstractFilteredResolverTestCase extends TestCase {

	private class DummyAbstractFilteredResolver extends AbstractFilteredResolver {

		public List<ClassDescription> resolve() {
			return null;
		}
	}

	public void test_PrivatClassLoaderCreation_WithInvalidDirectories() throws MalformedURLException {

		DummyAbstractFilteredResolver resolver = new DummyAbstractFilteredResolver();

		resolver.setClassesPath(new File[] { new File("classDir1"), null, new File("classDir2") });
		resolver.setTestClassesPath(new File[] { new File("testclassDir1"), null, new File("testclassDir2") });

		List<URL> sortedURLs = Arrays.asList(resolver.getLoader().getURLs());

		assertEquals("Wrong size of URL entries", 4, sortedURLs.size());
		assertTrue(sortedURLs.contains(new File("classDir1").toURL()));
		assertTrue(sortedURLs.contains(new File("classDir2").toURL()));
		assertTrue(sortedURLs.contains(new File("testclassDir1").toURL()));
		assertTrue(sortedURLs.contains(new File("testclassDir2").toURL()));

	}

}
