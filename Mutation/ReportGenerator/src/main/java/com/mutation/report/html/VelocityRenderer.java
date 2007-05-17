package com.mutation.report.html;

import java.io.Writer;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;


public class VelocityRenderer extends VelocityEngine  {
	public void render(String templateName, Context context, Writer  writer) throws RenderException{
		try {
			super.mergeTemplate(templateName, "UTF-8", context, writer);
		} catch (Exception e) {
			throw new RenderException(e);
		}
	}
}
