package com.retroduction.carma.testrunners.junit3;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

public class JUnitRunnerTestCase extends TestCase {

	public void test_CalculateCombinedClassPath() throws MalformedURLException {
		JUnitRunner runner = new JUnitRunner();

		runner.setClassesLocations(new URL[] { new URL("file:classDir1"), new URL("file:classDir2") });
		runner.setTestClassesLocations(new URL[] { new URL("file:testclassDir1"), new URL("file:testclassDir2") });
		runner.setLibraries(new URL[] { new URL("file:libDir1"), new URL("file:libDir2") });

		URL[] urls = runner.calculateCombinedClassPath();

		assertEquals(6, urls.length);

		assertEquals("file:classDir1", urls[0].toExternalForm());
		assertEquals("file:classDir2", urls[1].toExternalForm());
		assertEquals("file:testclassDir1", urls[2].toExternalForm());
		assertEquals("file:testclassDir2", urls[3].toExternalForm());
		assertEquals("file:libDir1", urls[4].toExternalForm());
		assertEquals("file:libDir2", urls[5].toExternalForm());
	}

	public void test_CalculateCombinedClassPath_EmptyNoTestDirectories() throws MalformedURLException {
		JUnitRunner runner = new JUnitRunner();

		runner.setClassesLocations(new URL[] { new URL("file:classDir1"), new URL("file:classDir2") });
		runner.setLibraries(new URL[] { new URL("file:libDir1"), new URL("file:libDir2") });

		URL[] urls = runner.calculateCombinedClassPath();

		assertEquals(4, urls.length);

		assertEquals("file:classDir1", urls[0].toExternalForm());
		assertEquals("file:classDir2", urls[1].toExternalForm());
		assertEquals("file:libDir1", urls[2].toExternalForm());
		assertEquals("file:libDir2", urls[3].toExternalForm());
	}

}
