/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
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
