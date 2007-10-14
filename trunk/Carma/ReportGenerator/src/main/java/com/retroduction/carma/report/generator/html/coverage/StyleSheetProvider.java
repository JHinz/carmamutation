package com.retroduction.carma.report.generator.html.coverage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

public class StyleSheetProvider {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String baseDir;

	private List<String> resources;

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	public StyleSheetProvider() {
	}

	public void provideResources(File outputBaseDir) throws IOException {
		for (String r : resources) {
			provideStyleSheet(outputBaseDir, r);
		}
	}

	private void provideStyleSheet(File outputBaseDir, String styleSheet) throws IOException {

		InputStream is = getClass().getResourceAsStream(baseDir + "/" + styleSheet);
		File cssOutputDir = new File(outputBaseDir, baseDir);
		if (!cssOutputDir.exists()) {
			cssOutputDir.mkdirs();
		}
		File cssFile = new File(cssOutputDir, styleSheet);

		FileOutputStream fout = new FileOutputStream(cssFile);
		try {
			byte[] buffer = new byte[10];
			int len;
			while (-1 != (len = is.read(buffer))) {
				fout.write(buffer, 0, len);
			}
		} catch (IOException e) {
			logger.error("Failed to provide resource: " +styleSheet, e);
			throw e;
		} catch (NullPointerException e) {
			logger.error("Failed to provide resource: " +styleSheet, e);
			throw e;
		} finally {
			is.close();
			fout.close();

		}
	}
}