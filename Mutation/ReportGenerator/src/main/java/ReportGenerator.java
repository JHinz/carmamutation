import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.mutation.report.om.MutationRun;

public class ReportGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			VelocityEngine engine = new VelocityEngine();
			engine.init();

			JAXBContext context = JAXBContext.newInstance(MutationRun.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			MutationRun report = (MutationRun) unmarshaller.unmarshal(new FileInputStream(new File(
					"src/main/resources/report.xml")));

			Template reportTemplate = engine.getTemplate("src/main/resources/report.vm");

			VelocityContext vcontext = new VelocityContext();
			vcontext.put("report", report);

			StringWriter w = new StringWriter();

			Velocity.mergeTemplate("src/main/resources/report.vm", "UTF-8", vcontext, w);
			System.out.println(" template : " + w);

			FileWriter writer = new FileWriter("target/report.html");
			writer.write(w.toString());
			writer.close();

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
