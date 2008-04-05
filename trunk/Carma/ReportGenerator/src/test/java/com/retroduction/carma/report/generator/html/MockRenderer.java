/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.html;

import java.io.File;
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
	public File getOutputBaseDir() {
		throw new  UnsupportedOperationException();
	}

}
