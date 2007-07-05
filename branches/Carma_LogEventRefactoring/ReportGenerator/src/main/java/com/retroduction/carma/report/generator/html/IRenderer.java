package com.retroduction.carma.report.generator.html;

import java.io.File;

import org.apache.velocity.context.Context;

/**
 * interface for report renderer engine
 * @author mike
 *
 */
public interface IRenderer {
	
	/**
	 * render a page
	 * @param templateName name of the template file
	 * @param context context parameters to be used by template
	 * @param outputFile file to write (to be created)
	 * @throws RenderException if page could not be rendered successfully
	 */
	void render(String templateName, Context context, File outputFile) throws RenderException;
}
