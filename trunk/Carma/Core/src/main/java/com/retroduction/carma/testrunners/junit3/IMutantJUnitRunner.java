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

package com.retroduction.carma.testrunners.junit3;

import java.net.URL;

import com.retroduction.carma.core.api.testrunners.om.Mutant;

/**
 *
 * Threaded runner for testcases. Must not throw Exceptions ! Instead signal failure by setting
 * the error count appropriately !
 * 
 * @author arau
 *
 */
public interface IMutantJUnitRunner extends Runnable {

	public void setMutant(Mutant mutant);

	public void setTestCase(String testCase);

	public void setTestClassesLocation(URL[] testClassesLocation);

	public int getErrorCount();
	
	public boolean finished();
	
	public void setFinishedSynchroLock(Object lock);
}
