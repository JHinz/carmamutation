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
package org.retroduction.carma.reportgenerator.beans;

import junit.framework.TestCase;

/**
 * @author arau
 *
 */
public class SourceCodeEntryBeanTestCase extends TestCase {

	public void test_WhiteSpaces() {

		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("abc", 1);
			assertEquals(0, bean.getIndent());
			assertEquals("abc", bean.getCode());
		}

		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean(" abc", 1);
			assertEquals(1, bean.getIndent());
			assertEquals(" abc", bean.getCode());
		}

		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("  abc", 1);
			assertEquals(2, bean.getIndent());
			assertEquals("  abc", bean.getCode());
		}

		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("   abc", 1);
			assertEquals(3, bean.getIndent());
			assertEquals("   abc", bean.getCode());
		}

		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("    abc", 1);
			assertEquals(4, bean.getIndent());
			assertEquals("    abc", bean.getCode());
		}
		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("     abc", 1);
			assertEquals(5, bean.getIndent());
			assertEquals("     abc", bean.getCode());
		}

		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("      abc", 1);
			assertEquals(6, bean.getIndent());
			assertEquals("      abc", bean.getCode());
		}

		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("       abc", 1);
			assertEquals(7, bean.getIndent());
			assertEquals("       abc", bean.getCode());
		}

		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("        abc", 1);
			assertEquals(8, bean.getIndent());
			assertEquals("        abc", bean.getCode());
		}

	}

	public void test_Tabs() {

		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("\tabc", 1);
			assertEquals(8, bean.getIndent());
			assertEquals("        abc", bean.getCode());
		}
		{
			SourceCodeEntryBean bean = new SourceCodeEntryBean("\t\tabc", 1);
			assertEquals(16, bean.getIndent());
			assertEquals("                abc", bean.getCode());
		}
	}

}
