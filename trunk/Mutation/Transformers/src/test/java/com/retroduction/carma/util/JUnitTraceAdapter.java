package com.retroduction.carma.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class JUnitTraceAdapter {

	public static String readReferenceText(File file) throws FileNotFoundException {

		StringBuilder stringBuilder = new StringBuilder();

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
		} catch (IOException e) {
			// Do nothing - EOF
		}
		return stringBuilder.toString();

	}

}
