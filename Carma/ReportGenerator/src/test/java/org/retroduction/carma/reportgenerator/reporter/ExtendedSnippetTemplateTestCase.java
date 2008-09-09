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

public class ExtendedSnippetTemplateTestCase extends TestCase {
	
	private final String EOF_CHAR = System.getProperty("line.separator");

	public void test_1line() {

		Configuration cfg = new Configuration();

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("extendedSnippet.ftl", "/templates/classReport");
		renderer.setConfig(cfg);

		Writer outputWriter = new StringWriter();

		Map<String, Object> info = new HashMap<String, Object>();

		List<SourceCodeEntryBean> entries = new ArrayList<SourceCodeEntryBean>();
		entries.add(new SourceCodeEntryBean("me is code", 7));

		info.put("codeEntries", entries);

		Mutant mutant = new Mutant();
		mutant.setSurvived(false);
		mutant.setBaseSourceLineStart(7);
		mutant.setBaseSourceLineEnd(7);
		mutant.setBaseSourceColumnStart(3);
		mutant.setBaseSourceColumnEnd(5);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"code\">").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre>me</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("<pre> is</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre> code</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
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
		mutant.setBaseSourceColumnEnd(5);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"code\">").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre>me</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("<pre> is code</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre></pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"code\">").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre></pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("<pre>me is</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre> the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
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
		mutant.setBaseSourceColumnEnd(6);

		SourceCodeBean code = new SourceCodeBean(null, mutant);

		info.put("snippet", code);

		renderer.render(info, outputWriter);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("<table>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("7").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"code\">").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre>m</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("<pre>e is code</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre></pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("8").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"code\">").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre></pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("<pre>me is the overcoder</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre></pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("<tr>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("9").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("<td>").append(EOF_CHAR);
		expectedResult.append("<div class=\"code\">").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre></pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"covered_killed\">").append(EOF_CHAR);
		expectedResult.append("<pre>smarta</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("<div class=\"uncovered\">").append(EOF_CHAR);
		expectedResult.append("<pre>ss</pre>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</div>").append(EOF_CHAR);
		expectedResult.append("</td>").append(EOF_CHAR);
		expectedResult.append("</tr>").append(EOF_CHAR);
		expectedResult.append("</table>").append(EOF_CHAR);

		System.out.println(expectedResult);
		assertEquals("Output mismatch", expectedResult.toString(), outputWriter.toString());

	}

}
