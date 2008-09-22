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
