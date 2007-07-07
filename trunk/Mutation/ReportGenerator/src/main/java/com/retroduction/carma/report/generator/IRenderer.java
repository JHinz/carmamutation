package com.retroduction.carma.report.generator;

/**
* interface for report renderer engine
* @author mike
*
*/
public interface IRenderer {
	/**
	 * 
	 * @param templateName name of the template
	 * @param context contains attributes used within template
	 * @param outputFileName relative name of the file to be written
	 */
	//TODO add title parameter which is logged by renderer
	void render(String templateName, Object context, String outputFileName) throws RendererException;
}
