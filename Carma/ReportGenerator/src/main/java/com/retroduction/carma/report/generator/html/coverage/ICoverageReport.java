/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.html.coverage;

import com.retroduction.carma.report.generator.IRenderer;
import com.retroduction.carma.report.generator.RendererException;
import com.retroduction.carma.report.om.Project;
import com.retroduction.carma.xmlreport.om.MutationRun;

public interface ICoverageReport {
	void generateReport(MutationRun report, Project project, IRenderer renderer) throws RendererException;
}
