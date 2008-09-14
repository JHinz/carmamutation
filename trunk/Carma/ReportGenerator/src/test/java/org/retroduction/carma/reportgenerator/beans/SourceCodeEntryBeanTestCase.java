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
