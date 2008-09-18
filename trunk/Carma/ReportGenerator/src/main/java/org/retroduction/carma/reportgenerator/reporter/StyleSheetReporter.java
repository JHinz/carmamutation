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
			"html { background-color: white; font-family: Verdana; width: 100%; }\n",//
			"body { width: 100%;}\n",//
			"table { font-size:75%; width: 100%;}\n",// 
			".covered_killed {background-color: green; float:left; }\n",//
			".covered_survived { background-color: red; float:left; }\n",//
			".uncovered { float:left; }\n",//
			".code { font-family: Courier; background-color: blue; float:block; }\n",//
			".lineNo { font-family: Courier; background-color: silver; float:block; }\n",//
			".codeTable { background-color: lightgrey; font-family: Courier; border-width: 1px 0px 0px 0px; border-style: solid; float:block; }\n",//
			".descriptionTable { font-size: 50%; background-color: white; border-width: 1px 0px 1px 0px; border-style: solid; float:block; }",//
			".footer { font-size:50%; width: 150pt; margin-left: auto; margin-right: auto; }\n" };

	public void generateReport(Writer outputWriter) throws RendererException,
			IOException {

		for (String line : styleSheetCode) {
			outputWriter.append(line);
		}

		outputWriter.close();

	}
}
