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
import org.retroduction.carma.reportgenerator.beans.PackageDetailBean;

import com.retroduction.carma.xmlreport.om.MutationRun;

import freemarker.template.Configuration;

/**
 * @author arau
 * 
 */
public class PackageViewReporter {

	private HashMap<String, Object> context;

	public PackageViewReporter() {
		super();
		this.context = new HashMap<String, Object>();
	}

	public PackageViewReporter(HashMap<String, Object> context) {
		super();
		this.context = context;
	}

	public void generateReport(MutationRun report, Writer outputWriter) throws RendererException {

		PackageListingBeanBuilder builder = new PackageListingBeanBuilder();

		List<PackageDetailBean> packageDetailBeans = builder.get(report);

		packageDetailBeans.remove(0);

		context.put("packageDetailBeans", packageDetailBeans);

		FreeMarkerRenderer renderer = new FreeMarkerRenderer("packageView.ftl", "/templates/");
		renderer.setConfig(new Configuration());

		renderer.render(context, outputWriter);

	}

}
