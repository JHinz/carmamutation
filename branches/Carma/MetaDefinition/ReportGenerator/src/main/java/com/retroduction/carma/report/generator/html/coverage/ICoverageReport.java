package com.retroduction.carma.report.generator.html.coverage;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.generator.RendererException;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;

public interface ICoverageReport {
	void generateReport(MutationRun report, Project project, IRenderer renderer) throws RendererException;
}
