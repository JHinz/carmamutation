package mut.executer.event;

import com.mutation.Mutant;

import junit.framework.TestResult;
import mut.util.ToStringUtils;

public class UnitTestFinished {
	@Override
	public String toString() {
		return ToStringUtils.toString(this);
	}
	private long execuTime;
	private String testCase;
	private Mutant mutant;
	private TestResult testResult;
	public Mutant getMutant() {
		return mutant;
	}
	public void setMutant(Mutant mutant) {
		this.mutant = mutant;
	}
	public String getTestCase() {
		return testCase;
	}
	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}
	public TestResult getTestResult() {
		return testResult;
	}
	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}
	public long getExecuTime() {
		return execuTime;
	}
	public void setExecuTime(long time) {
		this.execuTime = time;
	}
}
