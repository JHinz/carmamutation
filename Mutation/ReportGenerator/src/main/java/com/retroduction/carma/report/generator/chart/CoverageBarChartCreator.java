package com.retroduction.carma.report.generator.chart;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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

/**
 * creates a bar chart for clases of motation coverage. for each class, 
 * the number of instances is displayed as a bar
 * @author mike
 *
 */
public class CoverageBarChartCreator {
	private File baseDir;
	
	/**
	 * the class ranges
	 */
	private List<ClassRange> ranges;
	
	private int width = 800;
	private int height = 450;
	private String xLabel = "Coverage Class";
	private String yLabel = "# Instances";
	
	public CoverageBarChartCreator(){
		//TODO use spring
		ranges = new ArrayList<ClassRange>();
		ranges.add(new ClassRange("0", 0.0, 0.00001));
		ranges.add(new ClassRange("< 0.25", 0.00001, 0.25));
		ranges.add(new ClassRange("< 0.5", 0.25, 0.5));
		ranges.add(new ClassRange("< 0.75", 0.5, 0.75));
		ranges.add(new ClassRange("< 1", 0.75, 1.0));
		ranges.add(new ClassRange("1", 1.0, Double.MAX_VALUE));
	}
	public void createChart(List<ClassInfo> values, String pngFileName, String title) throws ChartException{
		MCoverageClassCreator classCreator = new MCoverageClassCreator(ranges);
		final List<DataClass> classes = classCreator.classifyMCoverage(values);
		CategoryDataset categoryDataset = createDataset(classes);
		
		JFreeChart chart = createBarChart(categoryDataset, title);
		
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

		try {
			File pngFile = new File(getBaseDir(), pngFileName);
			chartToFile(chart, pngFile);
		} catch (IOException e) {
			throw new ChartException(e);
		}
		
	}
	
	public void chartToFile(JFreeChart chart, File pngFile) throws IOException {
		// save it to an image
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

			ChartUtilities.saveChartAsPNG(pngFile, chart, width, height, info);

			// write an HTML page incorporating the image with an image map
//			File file2 = new File(pngFile.getPath() + "barchart100.html");
//			OutputStream out = new BufferedOutputStream(new FileOutputStream(file2));
//			PrintWriter writer = new PrintWriter(out);
//			writer.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"");
//			writer.println("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
//			writer.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">");
//			writer.println("<head><title>JFreeChart Image Map Demo</title></head>");
//			writer.println("<body><p>");
//			ImageMapUtilities.writeImageMap(writer, "chart", info);
//			writer.println("<img src=\"" + pngFile.toURL() + "\" "
//					+ "width=\"" +width +"\" height=\"" +height +"\" usemap=\"#chart\" alt=\"" +pngFile.toURL() +"\"/>");
//			writer.println("</p></body>");
//			writer.println("</html>");
//			writer.close();

	}	
	
	private CategoryDataset createDataset(List<DataClass> classes) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (DataClass dataClass : classes) {
			dataset.addValue(dataClass.getNumInstances(), xLabel, dataClass.getRange().getTitle());
		}
		return dataset;
	}
	
	public JFreeChart createBarChart(CategoryDataset dataSet, String title) {

		// create the chart...
		JFreeChart chart = ChartFactory.createBarChart(title, // chart
				// title
				xLabel, // domain axis label
				yLabel, // range axis label
				dataSet, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips?
				false // URLs?
				);
		chart.setBackgroundPaint(java.awt.Color.white);

		return chart;

	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setRanges(List<ClassRange> ranges) {
		this.ranges = ranges;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setXLabel(String label) {
		xLabel = label;
	}

	public void setYLabel(String label) {
		yLabel = label;
	}
	public int getHeight() {
		return height;
	}
	public List<ClassRange> getRanges() {
		return ranges;
	}
	public int getWidth() {
		return width;
	}
	public String getXLabel() {
		return xLabel;
	}
	public String getYLabel() {
		return yLabel;
	}
	public File getBaseDir() {
		return baseDir;
	}
	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}
}
