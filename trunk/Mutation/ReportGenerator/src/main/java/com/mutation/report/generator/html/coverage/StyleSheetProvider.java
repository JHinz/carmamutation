package com.mutation.report.generator.html.coverage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StyleSheetProvider {
	private File cssOutputDir;

	public StyleSheetProvider(File cssOutputDir) {
		this.cssOutputDir = cssOutputDir;
	}

	public void provideStyleSheet(String styleSheet) throws IOException {
		InputStream is = getClass().getResourceAsStream("css/" + styleSheet);
		if (!cssOutputDir.exists()) {
			cssOutputDir.mkdirs();
		}
		File cssFile = new File(cssOutputDir, styleSheet);

		FileOutputStream fout = new FileOutputStream(cssFile);
		byte[] buffer = new byte[1024];
		int len;
		while (-1 != (len = is.read(buffer))) {
			fout.write(buffer, 0, len);
		}
		is.close();
		fout.close();
	}

}