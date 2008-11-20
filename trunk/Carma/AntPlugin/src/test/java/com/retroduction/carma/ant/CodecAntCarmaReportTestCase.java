package com.retroduction.carma.ant;

import java.io.IOException;
import java.security.Permission;
import java.util.Locale;

import junit.framework.TestCase;

import org.apache.tools.ant.Main;

/**
 * @author arau
 * 
 */
public class CodecAntCarmaReportTestCase extends TestCase {

	private SecurityManager originalSecurityManager;

	private Locale originalLocale;

	@Override
	public void setUp() {
		this.originalSecurityManager = System.getSecurityManager();
		this.originalLocale = Locale.getDefault();
		System.setSecurityManager(new NoSystemExitSecurityManager());
		Locale.setDefault(Locale.US);
	}

	@Override
	public void tearDown() {
		System.setSecurityManager(this.originalSecurityManager);
		Locale.setDefault(this.originalLocale);
	}

	class NoSystemExitSecurityManager extends SecurityManager {

		@Override
		public void checkPermission(Permission perm) {

			if (perm.implies(new RuntimePermission("setSecurityManager"))) {
				return;
			}

		}

		@Override
		public void checkExit(int arg0) {
			throw new SecurityException(
					"Muahahahaaa... Noob! You aren't allowed to terminate VM");
		}

	}

	public void test_ProjectUnderTestWithNoDependencies_CarmaTestAndReportAntRun()
			throws IOException {

		try {
			Main.main(new String[] { "-v", "-f",
					"/Users/arau/commons-codec-1.3/build.xml", "carmareport" });
		} catch (SecurityException e) {
		}

	}

}
