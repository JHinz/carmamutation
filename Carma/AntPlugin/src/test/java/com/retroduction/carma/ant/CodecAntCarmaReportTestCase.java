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
