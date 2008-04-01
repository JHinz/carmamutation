/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.chart;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.retroduction.carma.report.generator.reportobjects.ClassInfo;
import com.retroduction.carma.report.generator.reportobjects.ClassInfoCreator;
import com.retroduction.carma.xmlreport.om.MutationRun;
import com.retroduction.carma.xmlreport.utilities.ReportModelLoader;

public class MutationCoverateClassBarChartGreator {

	private int width = 800;
	private int height = 400;
	public void createChart(List<ClassInfo> infos, File pngFile) {

		int numClasses = 10;
		double[] limits = new double[numClasses];
		double step = 1.0 / numClasses;
		for (int i = 0; i < limits.length - 1; i++) {
			limits[i] = step * (i + 1);
		}
		limits[numClasses - 1] = Double.MAX_VALUE;
		// double[] limits = { 0.01, 0.25, 0.5, 0.75, 0.9, 1.0, Double.MAX_VALUE
		// };
		MCoverageClassCreator creator = new MCoverageClassCreator(limits);
		final List<DataClass> classes = creator.classifyMCoverage(infos);
		CategoryDataset dataSet = this.createDataset("Mutation Coverage Class Distribution", classes);

		JFreeChart chart = this.createBarChart(dataSet);

		final NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryToolTipGenerator tooltipGenerator = new CategoryToolTipGenerator() {

			public String generateToolTip(CategoryDataset data, int series, int category) {
				List<ClassInfo> instances = classes.get(category).getInstances();
				String iStr = "";
				for (ClassInfo i : instances) {
					iStr += " " + i.getFqClassName() + ":" + nf.format(i.getMCoverageRatio());
				}
				return iStr;
			}

		};
		plot.getRenderer().setToolTipGenerator(tooltipGenerator);

		this.chartToFile(chart, pngFile);
	}

	public static void main(String[] args) throws FileNotFoundException, JAXBException {

		File reportFile = new File(args[0]);
		MutationRun run = new ReportModelLoader().loadReportModel(reportFile);
		ClassInfoCreator infoCreator = new ClassInfoCreator(run.getClassUnderTest());
		MutationCoverateClassBarChartGreator chartCreator = new MutationCoverateClassBarChartGreator();

		File pngFile = new File(reportFile.getPath() + "_MutCoverageChart.png");

		chartCreator.createChart(infoCreator.createClassInfos(), pngFile);

	}

	public void chartToFile(JFreeChart chart, File pngFile) {
		// save it to an image
		try {

			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

			File file1 = pngFile;
			ChartUtilities.saveChartAsPNG(file1, chart, this.width, this.height, info);

			// write an HTML page incorporating the image with an image map
//			File file2 = new File(file1.getPath() + "barchart100.html");
//			OutputStream out = new BufferedOutputStream(new FileOutputStream(file2));
//			PrintWriter writer = new PrintWriter(out);
//			writer.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"");
//			writer.println("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
//			writer.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">");
//			writer.println("<head><title>JFreeChart Image Map Demo</title></head>");
//			writer.println("<body><p>");
//			ImageMapUtilities.writeImageMap(writer, "chart", info);
//			writer.println("<img src=\"" + file1.toURL() + "\" "
//					+ "width=\"" +width +"\" height=\"" +height +"\" usemap=\"#chart\" alt=\"" +pngFile.toURL() +"\"/>");
//			writer.println("</p></body>");
//			writer.println("</html>");
//			writer.close();

		} catch (Exception e) {
			System.out.println("Schade");
		}
	}

	public JFreeChart createBarChart(CategoryDataset dataSet) {

		// create the chart...
		JFreeChart chart = ChartFactory.createBarChart("Mutation Test Coverage Distribution", // chart
				// title
				null, // domain axis label
				"Number of Instances", // range axis label
				dataSet, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips?
				false // URLs?
				);
		chart.setBackgroundPaint(java.awt.Color.white);

		return chart;

	}

	private CategoryDataset createDataset(String title, List<DataClass> classes) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (DataClass dataClass : classes) {
			dataset.addValue(dataClass.getNumInstances(), title, dataClass.getRange().getTitle());
		}
		return dataset;
	}
}
