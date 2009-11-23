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
