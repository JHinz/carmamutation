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
public class ExtendedSnippetTemplateTestCase extends TestCase {

	private final String EOF_CHAR = System.getProperty("line.separator");

	public void test_1line_PartOnly() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
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
		mutant.setBaseSourceColumnStart(3);
		mutant.setBaseSourceColumnEnd(5);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_survived\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_survived\"> i</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">s code</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_1line_FirstTwoCharacters() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
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
		mutant.setBaseSourceColumnStart(1);
		mutant.setBaseSourceColumnEnd(2);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_survived\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"></pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_survived\">m</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">e is code</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_1line_InBetweenUntilEnd() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
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
		mutant.setBaseSourceColumnStart(3);
		mutant.setBaseSourceColumnEnd(11);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_survived\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_survived\"> is code</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"></pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_1line_FromStartUntilInBetween() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
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
		mutant.setBaseSourceColumnStart(1);
		mutant.setBaseSourceColumnEnd(6);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_survived\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"></pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_survived\">me is</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"> code</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_1line_WholeLine() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
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
		mutant.setBaseSourceColumnStart(1);
		mutant.setBaseSourceColumnEnd(11);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_survived\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"></pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_survived\">me is code</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"></pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_2lines() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(8);
		mutant.setBaseSourceColumnStart(3);
		mutant.setBaseSourceColumnEnd(6);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\"> is code</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">me is</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"> the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_2lines_WholeLines() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(8);
		mutant.setBaseSourceColumnStart(1);
		mutant.setBaseSourceColumnEnd(20);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"></pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">me is code</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"></pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_2lines_StartAtEndOfFirstLine() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(8);
		mutant.setBaseSourceColumnStart(11);
		mutant.setBaseSourceColumnEnd(20);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me is code</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\"></pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"></pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_2lines_EndBeforeSecondLine_EndIndexIs0() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(8);
		mutant.setBaseSourceColumnStart(5);
		mutant.setBaseSourceColumnEnd(0);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me i</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">s code</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\"></pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_2lines_EndBeforeSecondLine_EndIndexIs1() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(8);
		mutant.setBaseSourceColumnStart(5);
		mutant.setBaseSourceColumnEnd(1);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me i</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">s code</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\"></pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_3lines() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));
		entries.add(new SourceCodeEntryBean("smartass", 9));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(9);
		mutant.setBaseSourceColumnStart(2);
		mutant.setBaseSourceColumnEnd(7);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">m</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">e is code</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("9").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">smarta</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">ss</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		System.out.println(expectedResult);
		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_3lines_StartAtEndOfFirstLine() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));
		entries.add(new SourceCodeEntryBean("smartass", 9));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(9);
		mutant.setBaseSourceColumnStart(11);
		mutant.setBaseSourceColumnEnd(4);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me is code</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\"></pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("9").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">sma</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">rtass</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		System.out.println(expectedResult);
		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_3lines_EndBeforeLastLine_3WholeLines() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));
		entries.add(new SourceCodeEntryBean("smartass", 9));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(9);
		mutant.setBaseSourceColumnStart(11);
		mutant.setBaseSourceColumnEnd(0);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me is code</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\"></pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("9").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\"></pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">smartass</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		System.out.println(expectedResult);
		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_3lines_EndBeforeLastLine_StartAfterEndOfFirstLine_EndBeforeStartOfLastLine() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));
		entries.add(new SourceCodeEntryBean("smartass", 9));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(9);
		mutant.setBaseSourceColumnStart(11);
		mutant.setBaseSourceColumnEnd(9);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">me is code</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\"></pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("9").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">smartass</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\"></pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		System.out.println(expectedResult);
		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

	public void test_5lines_first_and_last_line_uncovered() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("yyyyyyyyyy", 6));
		entries.add(new SourceCodeEntryBean("me is code", 7));
		entries.add(new SourceCodeEntryBean("me is the overcoder", 8));
		entries.add(new SourceCodeEntryBean("smartass", 9));
		entries.add(new SourceCodeEntryBean("xxxxxxxxxxx", 10));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(9);
		mutant.setBaseSourceColumnStart(2);
		mutant.setBaseSourceColumnEnd(7);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table class=\"codeTable\">").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("6").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">yyyyyyyyyy</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">m</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">e is code</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("9").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"covered_killed\">smarta</pre>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">ss</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("10").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<pre class=\"uncovered\">xxxxxxxxxxx</pre>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		System.out.println(expectedResult);
		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

}
