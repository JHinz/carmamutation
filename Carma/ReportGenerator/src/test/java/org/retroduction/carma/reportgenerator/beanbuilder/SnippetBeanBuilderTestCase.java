/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.beanbuilder;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.retroduction.carma.reportgenerator.beans.SourceCodeBean;

import com.retroduction.carma.report.om.SourceFile;
import com.retroduction.carma.xmlreport.om.ClassUnderTest;
import com.retroduction.carma.xmlreport.om.Mutant;

/**
 * @author arau
 *
 */
public class SnippetBeanBuilderTestCase extends TestCase {

	public void test_SourceCodeRanges_MultipleLineMutant() {

		SourceFile sourceFile = new SourceFile();

		List<String> sourceCode = new ArrayList<String>();
		sourceCode.add("1 xxxxxx");
		sourceCode.add("2 xxxxxx");
		sourceCode.add("3 xxxxxx");
		sourceCode.add("4 xxxxxx");
		sourceCode.add("5 xxxxxx");
		sourceCode.add("6 xxxxxx");
		sourceCode.add("7 xxxxxx");
		sourceCode.add("8 xxxxxx");

		sourceFile.setSourceText(sourceCode);

		{
			ClassUnderTest report = new ClassUnderTest();

			Mutant mutant = new Mutant();
			mutant.setBaseSourceLineStart(4);
			mutant.setBaseSourceLineEnd(5);

			report.getMutant().add(mutant);

			SnippetBuilder sBuilder = new SnippetBuilder(report, sourceFile);

			SourceCodeBean snippet = sBuilder.getSnippets().get(0);

			assertEquals(6, snippet.getCodeEntries().size());

			assertEquals("2 xxxxxx", snippet.getCodeEntries().get(0).getCode());
			assertEquals("3 xxxxxx", snippet.getCodeEntries().get(1).getCode());
			assertEquals("4 xxxxxx", snippet.getCodeEntries().get(2).getCode());
			assertEquals("5 xxxxxx", snippet.getCodeEntries().get(3).getCode());
			assertEquals("6 xxxxxx", snippet.getCodeEntries().get(4).getCode());
			assertEquals("7 xxxxxx", snippet.getCodeEntries().get(5).getCode());
		}

		{
			ClassUnderTest report = new ClassUnderTest();

			Mutant mutant = new Mutant();
			mutant.setBaseSourceColumnStart(1);
			mutant.setBaseSourceLineEnd(2);

			report.getMutant().add(mutant);

			SnippetBuilder sBuilder = new SnippetBuilder(report, sourceFile);

			SourceCodeBean snippet = sBuilder.getSnippets().get(0);

			assertEquals(4, snippet.getCodeEntries().size());
			assertEquals("1 xxxxxx", snippet.getCodeEntries().get(0).getCode());
			assertEquals("2 xxxxxx", snippet.getCodeEntries().get(1).getCode());
			assertEquals("3 xxxxxx", snippet.getCodeEntries().get(2).getCode());
			assertEquals("4 xxxxxx", snippet.getCodeEntries().get(3).getCode());
		}

		{
			ClassUnderTest report = new ClassUnderTest();

			Mutant mutant = new Mutant();
			mutant.setBaseSourceLineStart(7);
			mutant.setBaseSourceLineEnd(8);

			report.getMutant().add(mutant);

			SnippetBuilder sBuilder = new SnippetBuilder(report, sourceFile);

			SourceCodeBean snippet = sBuilder.getSnippets().get(0);

			assertEquals(4, snippet.getCodeEntries().size());

			assertEquals("5 xxxxxx", snippet.getCodeEntries().get(0).getCode());
			assertEquals("6 xxxxxx", snippet.getCodeEntries().get(1).getCode());
			assertEquals("7 xxxxxx", snippet.getCodeEntries().get(2).getCode());
			assertEquals("8 xxxxxx", snippet.getCodeEntries().get(3).getCode());
		}
	}

	public void test_SourceCodeRanges_SingleLineMutant() {

		SourceFile sourceFile = new SourceFile();

		List<String> sourceCode = new ArrayList<String>();
		sourceCode.add("1 xxxxxx");
		sourceCode.add("2 xxxxxx");
		sourceCode.add("3 xxxxxx");
		sourceCode.add("4 xxxxxx");
		sourceCode.add("5 xxxxxx");
		sourceCode.add("6 xxxxxx");
		sourceCode.add("7 xxxxxx");
		sourceCode.add("8 xxxxxx");

		sourceFile.setSourceText(sourceCode);

		{
			ClassUnderTest report = new ClassUnderTest();

			Mutant mutant = new Mutant();
			mutant.setBaseSourceLineStart(4);
			mutant.setBaseSourceLineEnd(4);

			report.getMutant().add(mutant);

			SnippetBuilder sBuilder = new SnippetBuilder(report, sourceFile);

			SourceCodeBean snippet = sBuilder.getSnippets().get(0);

			assertEquals(5, snippet.getCodeEntries().size());

			assertEquals("2 xxxxxx", snippet.getCodeEntries().get(0).getCode());
			assertEquals("3 xxxxxx", snippet.getCodeEntries().get(1).getCode());
			assertEquals("4 xxxxxx", snippet.getCodeEntries().get(2).getCode());
			assertEquals("5 xxxxxx", snippet.getCodeEntries().get(3).getCode());
			assertEquals("6 xxxxxx", snippet.getCodeEntries().get(4).getCode());
		}

		{
			ClassUnderTest report = new ClassUnderTest();

			Mutant mutant = new Mutant();
			mutant.setBaseSourceColumnStart(1);
			mutant.setBaseSourceLineEnd(1);

			report.getMutant().add(mutant);

			SnippetBuilder sBuilder = new SnippetBuilder(report, sourceFile);

			SourceCodeBean snippet = sBuilder.getSnippets().get(0);

			assertEquals(3, snippet.getCodeEntries().size());
			assertEquals("1 xxxxxx", snippet.getCodeEntries().get(0).getCode());
			assertEquals("2 xxxxxx", snippet.getCodeEntries().get(1).getCode());
			assertEquals("3 xxxxxx", snippet.getCodeEntries().get(2).getCode());
		}

		{
			ClassUnderTest report = new ClassUnderTest();

			Mutant mutant = new Mutant();
			mutant.setBaseSourceLineStart(8);
			mutant.setBaseSourceLineEnd(8);

			report.getMutant().add(mutant);

			SnippetBuilder sBuilder = new SnippetBuilder(report, sourceFile);

			SourceCodeBean snippet = sBuilder.getSnippets().get(0);

			assertEquals(3, snippet.getCodeEntries().size());

			assertEquals("6 xxxxxx", snippet.getCodeEntries().get(0).getCode());
			assertEquals("7 xxxxxx", snippet.getCodeEntries().get(1).getCode());
			assertEquals("8 xxxxxx", snippet.getCodeEntries().get(2).getCode());
		}
	}

}
