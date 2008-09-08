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
import org.retroduction.carma.reportgenerator.beanbuilder.PackageDetailBeanBuilder;
import org.retroduction.carma.reportgenerator.beans.PackageDetailBean;

import com.retroduction.carma.xmlreport.om.MutationRun;

import freemarker.template.Configuration;

/**
 * @author arau
 *
 */
public class PackageDetailReporter {

	private HashMap<String, Object> context;

	public PackageDetailReporter() {
		super();
		this.context = new HashMap<String, Object>();
	}

	public PackageDetailReporter(HashMap<String, Object> context) {
		super();
		this.context = context;
	}

	public void generateReport(MutationRun report, Writer outputWriter) throws RendererException {

		PackageDetailBeanBuilder builder = new PackageDetailBeanBuilder();

		List<PackageDetailBean> packageDetailBeans = builder.get(report);

		PackageDetailBean projectDetailBean = (PackageDetailBean) packageDetailBeans.remove(0);

		context.put("projectDetailBean", projectDetailBean);
		context.put("packageDetailBeans", packageDetailBeans);

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("packageDetails.ftl", "/templates/");
		renderer.setConfig(new Configuration());

		renderer.render(context, outputWriter);

	}

}
