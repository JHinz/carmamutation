package com.mutation.report.generator.html;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;


public class VelocityRenderer extends VelocityEngine implements IRenderer {
	public void render(String templateName, Context context, File outputFile) throws RenderException{
		try {
			Writer writer = new FileWriter(outputFile);
			super.mergeTemplate(templateName, "UTF-8", context, writer);
			writer.close();
		} catch (Exception e) {
			throw new RenderException(e);
		}
	}
}
