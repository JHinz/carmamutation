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
package com.retroduction.carma.ant;

import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.util.Locale;

import junit.framework.TestCase;
import junitx.framework.FileAssert;

import org.apache.tools.ant.Main;

/**
 * @author arau
 * 
 */
public class AntCarmaReportTestCase extends TestCase {

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
			throw new SecurityException("Muahahahaaa... Noob! You aren't allowed to terminate VM");
		}

	}

	public void test_ProjectUnderTestWithNoDependencies_CarmaTestAndReportAntRun() throws IOException {

		String[] requiredFiles = { "target/report.xml", "target/carmareport/index.html",
				"target/carmareport/com.mutation.sampleapp.html", "target/carmareport/com.mutation.sampleapp.App.html" };

		for (String fileName : requiredFiles) {
			File file = new File(fileName);

			if (file.exists()) {
				file.delete();
			}
			assertTrue(file.getCanonicalPath() + " could not be cleanup before execution", !file.exists());
		}

		try {
			Main.main(new String[] { "-v", "-f", "src/test/it/it0001/build.xml", "all" });
		} catch (SecurityException e) {
		}

		for (String fileName : requiredFiles) {
			File file = new File(fileName);
			assertTrue(file.getCanonicalPath() + " has not been written", file.exists());
		}

		if (System.getProperty("os.name").contains("Windows")) {
			FileAssert.assertEquals("Report.xml content unexpected", new File(
					"src/test/it/it0001/target/report_win32.xml"), new File("target/report.xml"));
		} else {
			FileAssert.assertEquals("Report.xml content unexpected", new File(
					"src/test/it/it0001/target/report_unix.xml"), new File("target/report.xml"));
		}
	}

}
