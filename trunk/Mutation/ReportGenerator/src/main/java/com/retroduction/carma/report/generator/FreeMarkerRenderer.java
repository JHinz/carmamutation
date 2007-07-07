package com.retroduction.carma.report.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerRenderer implements IRenderer {
	private Logger logger = LoggerFactory.getLogger(getClass());

	Configuration config;

	File outputBaseDir;

	public void render(String templateName, Object context, String outputFileName) throws RendererException {
		logger.info("Rendering template: " +templateName);
		try {
			Template templ = config.getTemplate(templateName);

			File outputFile = new File(outputBaseDir, outputFileName);
			Writer writer = new FileWriter(outputFile);
			try {
				templ.process(context, writer);
			} catch (TemplateException e) {
				throw new RendererException("Failed to render template. template: " + templateName + " output: "
						+ outputFile);
			}
		} catch (IOException e) {
			throw new RendererException("Failed reading template: " + templateName, e);
		}

	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	public File getOutputBaseDir() {
		return outputBaseDir;
	}

	public void setOutputBaseDir(File outputBaseDir) {
		this.outputBaseDir = outputBaseDir;
	}
}
