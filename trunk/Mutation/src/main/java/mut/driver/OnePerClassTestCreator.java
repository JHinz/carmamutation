package mut.driver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import mut.MutationTestSpec;
import mut.log.ConsoleEventLogger;
import mut.log.Event;
import mut.log.IEventLogger;

import com.mutation.EMutationOperator;

/**
 * this class creates mutation tests following a naming policy that defines one
 * unit test for each class. The unit test is names like the class but with a
 * suffix. eg. MyClassTest tests MyClass
 * 
 * @author mike
 * 
 */
public class OnePerClassTestCreator implements IMutationTestsCreator {
	private IEventLogger logger = new ConsoleEventLogger(OnePerClassTestCreator.class);
	/**
	 * base directory for classes
	 */
	private File classesBaseDir;

	/**
	 * base directory for test classes
	 */
	private File testClassesBaseDir;
	/**
	 * test case suffix added to the class name
	 */
	private String testCaseSuffix = "Test";

	public List<MutationTestSpec> createTests(Set<EMutationOperator> operators) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("classesBaseDir", classesBaseDir);
		params.put("testClassesBaseDir", testClassesBaseDir);
		params.put("testCaseSuffix", testCaseSuffix);
		getLogger().log(Event.START_CREATETESTS, params);

		List<String> classNames = new ArrayList<String>();
		iterate(this.classesBaseDir, "", "", classNames);

		List<MutationTestSpec> specs = new ArrayList<MutationTestSpec>();
		for (String className : classNames) {
			
			// check if test class exists
			
			String testClassName = className + this.testCaseSuffix;
			String testClassRelPath = testClassName.replace('.', '/') +".class";
			File testClassFile = new File(getTestClassesBaseDir(), testClassRelPath);
			if(testClassFile.exists()){
				MutationTestSpec testSpec = new MutationTestSpec();
				testSpec.setOperators(operators);
				testSpec.setClassUnderTest(className);
				List<String> testSet = new ArrayList<String>();
				testSet.add(testClassName);
				testSpec.setTestSet(testSet);
				specs.add(testSpec);
				
			}else{
				getLogger().log(Event.TESTCLASS_NOT_FOUND, className +" -> " +testClassFile);
			}
			
			
		}
		return specs;
	}

	// void iterate(File baseDir, String packagePrefix, String relPath){
	//		
	// for(File file : baseDir.listFiles())
	// {
	// // System.out.println();
	// // System.out.println("Path" + file.getAbsolutePath());
	// // System.out.println("Name" +file.getName());
	// if(file.isDirectory()){
	// String prefix = packagePrefix.equals("") ? file.getName() : packagePrefix
	// +"." +file.getName();
	// String relSubPath = relPath +"/" +file.getName();
	// iterate(file, prefix, relSubPath);
	// }
	// else{
	// String fileName = file.getName();
	//				
	// if(fileName.endsWith(".class")){
	// String relClassName = fileName.substring(0, fileName.length()
	// -".class".length());
	// String className = packagePrefix +"." +relClassName;
	// String relFileName = relPath +"/" +fileName;
	// System.out.println("Class: " +className);
	// System.out.println("Rel Filename: " +relFileName);
	// //File classFileName = new File(this.classesBaseDir,
	// relFileName).getAbsolutePath());
	// File testFileName = new File(this.testsBaseDir, relClassName
	// +testCaseSuffix +".class");
	// if(testFileName.exists()){
	// MutationTestSpec testSpec = new MutationTestSpec();
	// testSpec.setClassUnderTest(className);
	// List<String> testSet = ArrayList<String>();
	// testSet.add(className +this.testCaseSuffix);
	// testSpec.setTestSet(testSet);
	// }else{
	// System.out.println(testFileName.getAbsolutePath() +" does not exist");
	// }
	// }
	// }
	// }
	//		
	// }

	void iterate(File baseDir, String packagePrefix, String relPath,
			List<String> classNames) {


		File[] files = baseDir.listFiles();
		if(files == null){
			return;
		}
		
		for (File file : files) {
			// System.out.println();
			// System.out.println("Path" + file.getAbsolutePath());
			// System.out.println("Name" +file.getName());
			if (file.isDirectory()) {
				String prefix = packagePrefix.equals("") ? file.getName()
						: packagePrefix + "." + file.getName();
				String relSubPath = relPath + "/" + file.getName();
				iterate(file, prefix, relSubPath, classNames);
			} else {
				String fileName = file.getName();

				if (fileName.endsWith(".class")) {
					String relClassName = fileName.substring(0, fileName
							.length()
							- ".class".length());
					String className = packagePrefix + "." + relClassName;
					classNames.add(className);
				}
			}
		}

	}

	public File getClassesBaseDir() {
		return classesBaseDir;
	}

	public void setClassesBaseDir(File classesBaseDir) {
		this.classesBaseDir = classesBaseDir;
	}

	public String getTestCaseSuffix() {
		return testCaseSuffix;
	}

	public void setTestCaseSuffix(String testCaseSuffix) {
		this.testCaseSuffix = testCaseSuffix;
	}

	public File getTestClassesBaseDir() {
		return testClassesBaseDir;
	}

	public void setTestClassesBaseDir(File testClassesBaseDir) {
		this.testClassesBaseDir = testClassesBaseDir;
	}

	public IEventLogger getLogger() {
		return logger;
	}

	public void setLogger(IEventLogger logger) {
		this.logger = logger;
	}

}
