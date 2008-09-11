/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator;

import java.io.IOException;
import java.io.Writer;

import com.retroduction.carma.utilities.Logger;
import com.retroduction.carma.utilities.LoggerFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author arau
 *
 */
public class FreeMarkerRenderer implements IRenderer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Configuration config;

	private String templateName;

	private String templateBaseFolder;

	public FreeMarkerRenderer(String templateName, String templateBaseFolder) {
		super();
		this.templateBaseFolder = templateBaseFolder;
		this.templateName = templateName;
	}

	public void render(Object context, Writer writer) throws RendererException {

		this.logger.debug("Rendering template: " + templateName);

		try {
			this.config.setTemplateLoader(new ClassTemplateLoader(FreeMarkerRenderer.class,
					templateBaseFolder));
			Template templ = this.config.getTemplate(templateName);

			try {
				templ.process(context, writer);
			} catch (TemplateException e) {
				throw new RendererException("Failed to render template: " + templateName, e);
			}
		} catch (IOException e) {
			throw new RendererException("Failed loading template: " + templateName, e);
		}

		this.logger.debug("Template successfully rendered.");

	}

	public Configuration getConfig() {
		return this.config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

}
