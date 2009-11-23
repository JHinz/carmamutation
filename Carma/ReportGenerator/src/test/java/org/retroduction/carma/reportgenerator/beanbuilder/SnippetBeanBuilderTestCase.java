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
