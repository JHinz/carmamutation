import java.util.ArrayList;
import java.util.List;

import com.mutation.report.generator.ProjectBuilder;
import com.mutation.report.source.om.Project;
import com.mutation.report.source.om.SourceFile;

import junit.framework.TestCase;

public class ProjectBuilderIntegrationTest extends TestCase {

	public void testBuildProject() throws Exception {

		ProjectBuilder builder = new ProjectBuilder();

		List<String> sourceFolders = new ArrayList<String>();
		sourceFolders.add("src/test/resources/it00001");
		Project project = builder.buildProject(sourceFolders);

		assertNotNull(project);
		assertNotNull(project.getSourceFiles());
		assertEquals(3, project.getSourceFiles().size());

		SourceFile file;

		file = project.getSourceFiles().get(2);
		assertEquals("src/test/resources/it00001/subfolder/anotherSubFolder/TestClass2.java", file.getFileName());
		assertEquals("x\n", file.getSourceText());
		assertEquals("subfolder.anotherSubFolder", file.getPackageName());
		assertEquals("TestClass2", file.getClassName());

		file = project.getSourceFiles().get(1);
		assertEquals("src/test/resources/it00001/subfolder/TestClass2.java", file.getFileName());
		assertEquals("a\nb\nc\n1\n2\n3\n", file.getSourceText());
		assertEquals("subfolder", file.getPackageName());
		assertEquals("TestClass2", file.getClassName());

		file = project.getSourceFiles().get(0);
		assertEquals("src/test/resources/it00001/TestClass1.java", file.getFileName());
		assertEquals("a1\nb2\n", file.getSourceText());
		assertEquals("", file.getPackageName());
		assertEquals("TestClass1", file.getClassName());

	}

}
