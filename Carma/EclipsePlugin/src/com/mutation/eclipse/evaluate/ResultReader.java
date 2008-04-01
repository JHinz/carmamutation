/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.mutation.eclipse.evaluate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.mutation.report.om.MutationRun;

public class ResultReader {

	public MutationRun readResults(String file, String path) {

		try {
			Thread.sleep(30000);
			JAXBContext context = JAXBContext.newInstance(MutationRun.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			MutationRun report = (MutationRun) unmarshaller.unmarshal(new FileInputStream(new File(path, file)));

			System.out.print(report.toString());

			return report;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;

	}
}
