/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.reporter;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import org.retroduction.carma.reportgenerator.FreeMarkerRenderer;
import org.retroduction.carma.reportgenerator.RendererException;
import org.retroduction.carma.reportgenerator.beanbuilder.PackageListingBeanBuilder;
import org.retroduction.carma.reportgenerator.beanbuilder.ProjectStatisticsBeanBuilder;
import org.retroduction.carma.reportgenerator.beans.PackageDetailBean;
import org.retroduction.carma.reportgenerator.beans.ProjectStatisticsBean;

import com.retroduction.carma.xmlreport.om.MutationRun;

import freemarker.template.Configuration;

/**
 * @author arau
 * 
 */
public class ProjectViewReporter {

	private HashMap<String, Object> context;

	public ProjectViewReporter() {
		super();
		this.context = new HashMap<String, Object>();
	}

	public ProjectViewReporter(HashMap<String, Object> context) {
		super();
		this.context = context;
	}

	public void generateReport(MutationRun report, Writer outputWriter) throws RendererException {

		ProjectStatisticsBean projectStatisticsBean = new ProjectStatisticsBeanBuilder().get(report);

		List<PackageDetailBean> packageDetailBeans = new PackageListingBeanBuilder().get(report);

		context.put("projectStatisticsBean", projectStatisticsBean);
		context.put("projectDetailBean", packageDetailBeans.get(0));
		context.put("packageDetailBeans", packageDetailBeans.subList(1, packageDetailBeans.size()));

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("projectView.ftl", "/templates/");
		renderer.setConfig(new Configuration());

		renderer.render(context, outputWriter);

	}

}
