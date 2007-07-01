package com.retroduction.carma.ant;

import java.io.File;
import java.io.IOException;
import java.security.Permission;

import junit.framework.TestCase;

import org.apache.tools.ant.Main;
import org.apache.tools.ant.launch.Launcher;

public class AntCarmaReportTestCase extends TestCase {

	private SecurityManager originalSecManager;

	public void setUp() {

		originalSecManager = System.getSecurityManager();
		// System.setSecurityManager(new NoSystemExitSecurityManager());
	}

	public void tearDown() {
		System.setSecurityManager(originalSecManager);
	}

	class NoSystemExitSecurityManager extends SecurityManager {

		@Override
		public void checkPermission(Permission perm) {

			if (perm.implies(new RuntimePermission("setSecurityManager")))
				return;

		}

		@Override
		public void checkExit(int arg0) {
			throw new SecurityException("Muahahahaaa... Noob! You aren't allowed to terminate VM");
		}

	}

	public void test_ProjectUnderTestWithNoDependencies_CarmaTestAndReportAntRun() throws IOException {

		File file = new File("target/report.xml");

		if (file.exists())
			file.delete();

		assertTrue(file.getCanonicalPath() + " could not be cleanup before execution", !file.exists());

		try {
			Main.main(new String[] { "-f", "src/test/it/it0001/build.xml", "all" });
		} catch (SecurityException e) {
		}

		assertTrue(file.getCanonicalPath() + " file has not been written", file.exists());
	}

}
