/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.xmlreport.utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.retroduction.carma.xmlreport.om.MutationRun;

public class ReportModelLoader {

	public ReportModelLoader() {
		super();
	}

	public MutationRun loadReportModel(File source) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(MutationRun.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		MutationRun report = (MutationRun) unmarshaller.unmarshal(new FileInputStream(source));
		return report;
	}
}
