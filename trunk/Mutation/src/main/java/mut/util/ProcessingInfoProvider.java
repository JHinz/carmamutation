package mut.util;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.mutation.report.om.ProcessingInfo;

public class ProcessingInfoProvider {

	private GregorianCalendar calendar = new GregorianCalendar();

	public ProcessingInfo fillProcessInfo(long start, long stop) {

		ProcessingInfo info = new ProcessingInfo();

		try {

			calendar.setTimeInMillis(start);
			info.setStart(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			calendar.setTimeInMillis(stop);
			info.setEnd(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			info.setDuration(stop - start);

		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return info;
	}

}
