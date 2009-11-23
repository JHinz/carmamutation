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
package org.retroduction.carma.reportgenerator.reporter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

import org.retroduction.carma.reportgenerator.RendererException;

/**
 * @author arau
 * 
 */
public class StyleSheetReporter {

	private final static String EOL = System.getProperty("line.separator");

	public void generateReport(Writer outputWriter) throws RendererException, IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader()
				.getResourceAsStream("templates/style.css")));

		String inputLine = null;
		while ((inputLine = reader.readLine()) != null) {
			outputWriter.append(inputLine);
			outputWriter.write(EOL);
		}

		outputWriter.close();

	}
}
