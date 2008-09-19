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
