package com.mutation.report.generator.html;

import java.io.File;

import org.apache.velocity.context.Context;


/**
 * impl. for testing
 * @author mike
 *
 */
public class MockRenderer implements IRenderer {

	public RenderException exceptionToThrow;
	public String templateName;
	public Context context;
	public File outputFile;
	public void render(String templateName, Context context, File outputFile) throws RenderException {
		this.templateName = templateName;
		this.context = context;
		this.outputFile = outputFile;
		if(null != exceptionToThrow){
			throw exceptionToThrow;
		}
		
	}

}
