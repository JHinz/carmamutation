/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
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
