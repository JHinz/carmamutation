/**
 *
 *   Copyright Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the GPL 3
 *   (http://www.opensource.org/licenses/gpl-3.0.html).
 *
 */
package org.retroduction.carma.reportgenerator.beans;

import java.sql.Timestamp;

/**
 * @author arau
 * 
 */
public class ProjectStatisticsBean {

	private String projectName;

	private Timestamp creationDate;

	private long durationInMilli;

	public long getDurationInMilli() {
		return durationInMilli;
	}

	public void setDurationInMilli(long durationInMilli) {
		this.durationInMilli = durationInMilli;
	}

	private String hostName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

}
