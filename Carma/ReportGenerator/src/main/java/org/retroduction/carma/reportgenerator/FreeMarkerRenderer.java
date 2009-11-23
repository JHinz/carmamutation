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
