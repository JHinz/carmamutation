/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.reporter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.retroduction.carma.reportgenerator.FreeMarkerRenderer;
import org.retroduction.carma.reportgenerator.beans.SourceCodeBean;
import org.retroduction.carma.reportgenerator.beans.SourceCodeEntryBean;

import com.retroduction.carma.xmlreport.om.Mutant;

import freemarker.template.Configuration;

/**
 * @author arau
 * 
 */
public class StandardSnippetTemplateTestCase extends TestCase {

	private final String EOF_CHAR = System.getProperty("line.separator");

	public void test_1line() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer(
				"standardSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(true);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(7);
		mutant.setBaseSourceColumnStart(-1);
		mutant.setBaseSourceColumnEnd(-2);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_survived\">").append(
				EOF_CHAR);
		expectedResult.append("me is code").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter
				.toString());
		System.out.println(outputWriter.toString());

	}

	public void test_2lines_Mutant_Survived() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer(
				"standardSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 42));
		entries.add(new SourceCodeEntryBean("me thinks as well", 43));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(true);
		mutant.setBaseSourceLineStart(42);
		mutant.setBaseSourceLineEnd(43);
		mutant.setBaseSourceColumnStart(-1);
		mutant.setBaseSourceColumnEnd(-2);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();

		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("42").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_survived\">").append(
				EOF_CHAR);
		expectedResult.append("me is code").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("43").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_survived\">").append(
				EOF_CHAR);
		expectedResult.append("me thinks as well").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter
				.toString());
		System.out.println(outputWriter.toString());
	}

	public void test_2lines_Mutant_NotSurvived() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer(
				"standardSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 42));
		entries.add(new SourceCodeEntryBean("me thinks as well", 43));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(42);
		mutant.setBaseSourceLineEnd(43);
		mutant.setBaseSourceColumnStart(-1);
		mutant.setBaseSourceColumnEnd(-2);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("42").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">")
				.append(EOF_CHAR);
		expectedResult.append("me is code").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("43").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">")
				.append(EOF_CHAR);
		expectedResult.append("me thinks as well").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter
				.toString());
		System.out.println(outputWriter.toString());
	}

	public void test_3lines_Mutant_Survived_NotAllSourceCodeCovered() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer(
				"standardSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 42));
		entries.add(new SourceCodeEntryBean("me thinks as well", 43));
		entries.add(new SourceCodeEntryBean("more code", 44));
		entries.add(new SourceCodeEntryBean("no more code", 45));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(43);
		mutant.setBaseSourceLineEnd(44);
		mutant.setBaseSourceColumnStart(-1);
		mutant.setBaseSourceColumnEnd(-2);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("42").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("me is code").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("43").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">")
				.append(EOF_CHAR);
		expectedResult.append("me thinks as well").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("44").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">")
				.append(EOF_CHAR);
		expectedResult.append("more code").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("45").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("no more code").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter
				.toString());
		System.out.println(outputWriter.toString());
	}

	public void test_3lines_Mutant_Survived_SourceCodeExceeded() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer(
				"standardSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 42));
		entries.add(new SourceCodeEntryBean("me thinks as well", 43));
		entries.add(new SourceCodeEntryBean("more code", 44));
		entries.add(new SourceCodeEntryBean("no more code", 45));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(41);
		mutant.setBaseSourceLineEnd(46);
		mutant.setBaseSourceColumnStart(-1);
		mutant.setBaseSourceColumnEnd(-2);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("42").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">")
				.append(EOF_CHAR);
		expectedResult.append("me is code").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("43").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">")
				.append(EOF_CHAR);
		expectedResult.append("me thinks as well").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("44").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">")
				.append(EOF_CHAR);
		expectedResult.append("more code").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"lineNo\">").append(EOF_CHAR);
		expectedResult.append("45").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">")
				.append(EOF_CHAR);
		expectedResult.append("no more code").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter
				.toString());
		System.out.println(outputWriter.toString());
	}

}
