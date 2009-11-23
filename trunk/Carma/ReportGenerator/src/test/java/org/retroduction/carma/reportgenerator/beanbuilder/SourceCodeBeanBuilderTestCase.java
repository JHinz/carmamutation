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

package org.retroduction.carma.reportgenerator.beanbuilder;

import junit.framework.TestCase;

import org.retroduction.carma.reportgenerator.beans.SourceCodeBean;
import org.retroduction.carma.reportgenerator.beans.SourceCodeEntryBean;

import com.retroduction.carma.xmlreport.om.Mutant;

/**
 * @author arau
 *
 */
public class SourceCodeBeanBuilderTestCase extends TestCase {

	public void test() {

		SourceCodeBean code = null;

		Mutant dummyMutant = new Mutant();

		SourceCodeBuilder builder = new SourceCodeBuilder(dummyMutant);

		{
			code = builder.getSourceCode();

			assertEquals(0, code.getCodeEntries().size());
			assertEquals(dummyMutant, code.getMutant());
		}

		{
			builder.setCodeLine(new SourceCodeEntryBean("code1", 5));

			code = builder.getSourceCode();

			assertEquals(1, code.getCodeEntries().size());
			assertEquals("code1", code.getCodeEntries().get(0).getCode());
			assertEquals((Integer) 5, code.getCodeEntries().get(0).getLineNumber());
			assertEquals(dummyMutant, code.getMutant());
		}

		{
			builder.setCodeLine(new SourceCodeEntryBean("code2", 7));

			code = builder.getSourceCode();

			assertEquals(3, code.getCodeEntries().size());
			assertEquals("code1", code.getCodeEntries().get(0).getCode());
			assertEquals((Integer) 5, code.getCodeEntries().get(0).getLineNumber());
			assertEquals("", code.getCodeEntries().get(1).getCode());
			assertEquals((Integer) 6, code.getCodeEntries().get(1).getLineNumber());
			assertEquals("code2", code.getCodeEntries().get(2).getCode());
			assertEquals((Integer) 7, code.getCodeEntries().get(2).getLineNumber());
			assertEquals(dummyMutant, code.getMutant());
		}
		{
			builder.setCodeLine(new SourceCodeEntryBean("code3", 6));

			code = builder.getSourceCode();

			assertEquals(3, code.getCodeEntries().size());
			assertEquals("code1", code.getCodeEntries().get(0).getCode());
			assertEquals((Integer) 5, code.getCodeEntries().get(0).getLineNumber());
			assertEquals("code3", code.getCodeEntries().get(1).getCode());
			assertEquals((Integer) 6, code.getCodeEntries().get(1).getLineNumber());
			assertEquals("code2", code.getCodeEntries().get(2).getCode());
			assertEquals((Integer) 7, code.getCodeEntries().get(2).getLineNumber());
			assertEquals(dummyMutant, code.getMutant());
		}

		{
			builder.setCodeLine(new SourceCodeEntryBean("code4", 3));

			code = builder.getSourceCode();

			assertEquals(5, code.getCodeEntries().size());
			assertEquals("code4", code.getCodeEntries().get(0).getCode());
			assertEquals((Integer) 3, code.getCodeEntries().get(0).getLineNumber());
			assertEquals("", code.getCodeEntries().get(1).getCode());
			assertEquals((Integer) 4, code.getCodeEntries().get(1).getLineNumber());
			assertEquals("code1", code.getCodeEntries().get(2).getCode());
			assertEquals((Integer) 5, code.getCodeEntries().get(2).getLineNumber());
			assertEquals("code3", code.getCodeEntries().get(3).getCode());
			assertEquals((Integer) 6, code.getCodeEntries().get(3).getLineNumber());
			assertEquals("code2", code.getCodeEntries().get(4).getCode());
			assertEquals((Integer) 7, code.getCodeEntries().get(4).getLineNumber());
			assertEquals(dummyMutant, code.getMutant());
		}

		{
			builder.setCodeLine(new SourceCodeEntryBean("code5", 4));

			code = builder.getSourceCode();

			assertEquals(5, code.getCodeEntries().size());
			assertEquals("code4", code.getCodeEntries().get(0).getCode());
			assertEquals((Integer) 3, code.getCodeEntries().get(0).getLineNumber());
			assertEquals("code5", code.getCodeEntries().get(1).getCode());
			assertEquals((Integer) 4, code.getCodeEntries().get(1).getLineNumber());
			assertEquals("code1", code.getCodeEntries().get(2).getCode());
			assertEquals((Integer) 5, code.getCodeEntries().get(2).getLineNumber());
			assertEquals("code3", code.getCodeEntries().get(3).getCode());
			assertEquals((Integer) 6, code.getCodeEntries().get(3).getLineNumber());
			assertEquals("code2", code.getCodeEntries().get(4).getCode());
			assertEquals((Integer) 7, code.getCodeEntries().get(4).getLineNumber());
			assertEquals(dummyMutant, code.getMutant());
		}
	}

}
