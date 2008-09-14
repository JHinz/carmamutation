/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.reporter;

import java.io.IOException;
import java.io.Writer;

import org.retroduction.carma.reportgenerator.RendererException;

/**
 * @author arau
 * 
 */
public class StyleSheetReporter {

	private final static String styleSheetCode[] = {
			"<style type=\"text/css\">\n",//
			"body { font-family: Courier;}\n",//
			"table { font-family: Courier; font-size:75%;}\n",// 
			".covered_killed {background-color: green;float:left; }\n",//
			".covered_survived {background-color: red;float:left; }\n",//
			".uncovered {background-color: lightgrey;float:left; }\n",//
			".code {background-color: blue; float:block; }\n",//
			"</style>"//
	};

	public void generateReport(Writer outputWriter) throws RendererException,
			IOException {

		for (String line : styleSheetCode) {
			outputWriter.append(line);
		}

		outputWriter.close();

	}
}
