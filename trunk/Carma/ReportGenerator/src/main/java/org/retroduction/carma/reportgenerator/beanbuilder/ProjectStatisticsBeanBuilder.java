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
package org.retroduction.carma.reportgenerator.beanbuilder;

import java.sql.Timestamp;

import org.retroduction.carma.reportgenerator.beans.ProjectStatisticsBean;

import com.retroduction.carma.xmlreport.om.MutationRun;

/**
 * @author arau
 * 
 */
public class ProjectStatisticsBeanBuilder {

	public ProjectStatisticsBean get(MutationRun report) {

		ProjectStatisticsBean project = new ProjectStatisticsBean();

		if (report.getProcessingInfo() == null)
			return project;

		project.setCreationDate(new Timestamp(report.getProcessingInfo().getEnd().toGregorianCalendar().getTime()
				.getTime()));
		project.setDurationInMilli(report.getProcessingInfo().getDuration());

		return project;

	}

}
