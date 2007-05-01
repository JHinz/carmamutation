package com.mutation.report.loader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.mutation.report.om.MutationRun;

public class ReportModelLoader {

	public ReportModelLoader() {
		super();
	}

	public MutationRun loadReportModel(String source) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(MutationRun.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		MutationRun report = (MutationRun) unmarshaller.unmarshal(new FileInputStream(new File(source)));
		return report;
	}

}
