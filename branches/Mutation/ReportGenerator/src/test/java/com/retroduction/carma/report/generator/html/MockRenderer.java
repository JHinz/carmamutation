package com.retroduction.carma.report.generator.html;

import java.util.Map;

import com.retroduction.carma.report.generator.IRenderer;


/**
 * impl. for testing
 * @author mike
 *
 */
public class MockRenderer implements IRenderer {

	public RenderException exceptionToThrow;
	public String templateName;
	public Map<String, Object> context;
	public String outputFile;
	@SuppressWarnings("unchecked")
	public void render(String templateName, Object context, String outputFile) throws RenderException {
		this.templateName = templateName;
		this.context = (Map<String, Object>) context;
		this.outputFile = outputFile;
		if(null != this.exceptionToThrow){
			throw this.exceptionToThrow;
		}
		
	}

}
