import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.mutation.report.om.MutationRun;

public class ReportGenerator {

	private String source;

	private String destination;

	private String template;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.out.println("Use arguments: source, destination, template");
			System.exit(-1);
		}

		new ReportGenerator(args[0], args[1], args[2]).perform();

	}

	public ReportGenerator(String source, String destination, String template) {
		super();
		this.source = source;
		this.destination = destination;
		this.template = template;
	}

	public void perform() {

		try {
			VelocityEngine engine = new VelocityEngine();
			engine.init();

			MutationRun report = loadReportModel();

			StringWriter w = createVelocityOutput(report);

			FileWriter writer = new FileWriter(destination);
			writer.write(w.toString());
			writer.close();

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private StringWriter createVelocityOutput(MutationRun report) throws Exception {
		VelocityContext vcontext = new VelocityContext();
		vcontext.put("report", report);

		StringWriter w = new StringWriter();

		Velocity.mergeTemplate(template, "UTF-8", vcontext, w);
		System.out.println(" template : " + w);
		return w;
	}

	private MutationRun loadReportModel() throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(MutationRun.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		MutationRun report = (MutationRun) unmarshaller.unmarshal(new FileInputStream(new File(source)));
		return report;
	}
}
