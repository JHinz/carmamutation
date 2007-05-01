import java.util.ArrayList;
import java.util.List;

import com.mutation.report.generator.ProjectBuilder;
import com.mutation.report.source.om.Project;
import com.mutation.report.source.om.SourceFile;

import junit.framework.TestCase;

public class ProjectBuilderIntegrationTest extends TestCase {

	public void testBuildProject_MultipleFilesInDifferentFolders() throws Exception {

		ProjectBuilder builder = new ProjectBuilder();

		List<String> sourceFolders = new ArrayList<String>();
		sourceFolders.add("src/test/resources/it00001");
		Project project = builder.buildProject(sourceFolders);

		assertNotNull(project);
		assertNotNull(project.getSourceFiles());
		assertEquals(3, project.getSourceFiles().size());
		assertNotNull(project.getSourceFile("subfolder.anotherSubFolder", "TestClass2"));
		assertNotNull(project.getSourceFile("subfolder", "TestClass2"));
		assertNotNull(project.getSourceFile("", "TestClass1"));

		SourceFile file;

		file = project.getSourceFiles().get(1);
		assertEquals("src/test/resources/it00001/subfolder/anotherSubFolder/TestClass2.java", file.getFileName());
		assertEquals(1, file.getSourceText().size());
		assertEquals("x\n", file.getSourceText().get(0));
		assertEquals("subfolder.anotherSubFolder", file.getPackageName());
		assertEquals("TestClass2", file.getClassName());

		file = project.getSourceFiles().get(0);
		assertEquals("src/test/resources/it00001/subfolder/TestClass2.java", file.getFileName());
		assertEquals(6, file.getSourceText().size());
		assertEquals("a\n", file.getSourceText().get(0));
		assertEquals("b\n", file.getSourceText().get(1));
		assertEquals("c\n", file.getSourceText().get(2));
		assertEquals("1\n", file.getSourceText().get(3));
		assertEquals("2\n", file.getSourceText().get(4));
		assertEquals("3\n", file.getSourceText().get(5));
		assertEquals("subfolder", file.getPackageName());
		assertEquals("TestClass2", file.getClassName());

		file = project.getSourceFiles().get(2);
		assertEquals("src/test/resources/it00001/TestClass1.java", file.getFileName());
		assertEquals(2, file.getSourceText().size());
		assertEquals("a1\n", file.getSourceText().get(0));
		assertEquals("b2\n", file.getSourceText().get(1));
		assertEquals("", file.getPackageName());
		assertEquals("TestClass1", file.getClassName());

	}

	public void testBuildProject_SourceFolderWithoutTrailingSlash() throws Exception {

		ProjectBuilder builder = new ProjectBuilder();

		List<String> sourceFolders = new ArrayList<String>();
		sourceFolders.add("src/test/resources/it00002");
		Project project = builder.buildProject(sourceFolders);

		assertNotNull(project);
		assertNotNull(project.getSourceFiles());
		assertEquals(1, project.getSourceFiles().size());
		assertNotNull(project.getSourceFile("subfolder", "TestClass1"));

		SourceFile file;

		file = project.getSourceFiles().get(0);
		assertEquals("src/test/resources/it00002/subfolder/TestClass1.java", file.getFileName());
		assertEquals(2, file.getSourceText().size());
		assertEquals("a1\n", file.getSourceText().get(0));
		assertEquals("b2\n", file.getSourceText().get(1));
		assertEquals("subfolder", file.getPackageName());
		assertEquals("TestClass1", file.getClassName());

	}

	public void testBuildProject_SourceFolderWithTrailingSlash() throws Exception {

		ProjectBuilder builder = new ProjectBuilder();

		List<String> sourceFolders = new ArrayList<String>();
		sourceFolders.add("src/test/resources/it00002/");
		Project project = builder.buildProject(sourceFolders);

		assertNotNull(project);
		assertNotNull(project.getSourceFiles());
		assertEquals(1, project.getSourceFiles().size());
		assertNotNull(project.getSourceFile("subfolder", "TestClass1"));

		SourceFile file;

		file = project.getSourceFiles().get(0);
		assertEquals("src/test/resources/it00002/subfolder/TestClass1.java", file.getFileName());
		assertEquals(2, file.getSourceText().size());
		assertEquals("a1\n", file.getSourceText().get(0));
		assertEquals("b2\n", file.getSourceText().get(1));
		assertEquals("subfolder", file.getPackageName());
		assertEquals("TestClass1", file.getClassName());

	}

}
