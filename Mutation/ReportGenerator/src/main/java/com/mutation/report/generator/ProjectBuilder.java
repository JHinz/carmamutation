package com.mutation.report.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mutation.report.source.om.Project;
import com.mutation.report.source.om.SourceFile;

public class ProjectBuilder {

	public Project buildProject(List<File> sourceFolders) {

		Project project = new Project();

		for (File folderName : sourceFolders) {

			try {
				URL sourceFolderUrl = folderName.toURL();

				Set<File> sourceFileNames = getSourceFiles(folderName);

				for (File sourceFileName : sourceFileNames) {
					try {

						URL sourceFileUrl = sourceFileName.toURL();

						SourceFile sourceFile = new SourceFile();
						
						sourceFile.setFileName(sourceFileName.getPath());
						sourceFile.setPackageName(extractPackageName(sourceFolderUrl, sourceFileUrl));
						sourceFile.setClassName(extractClassName(sourceFileUrl));

						BufferedReader reader = new BufferedReader(new FileReader(new File(sourceFileUrl.getPath())));

						sourceFile.setSourceText(extractFileContent(reader));
						project.addSourceFile(sourceFile);
					} catch (IOException e) {
						System.err.println("Source folder " + folderName + " is invalid. Skipping ...");
						continue;
					}

				}
			} catch (IOException e) {
				System.err.println("Source folder " + folderName + " is invalid. Skipping ...");
				continue;
			}

		}

		return project;

	}

	/**
	 * Extracts the package name of a given java source file name under
	 * consideration of an source folder being part of that name. The source
	 * folder part gets stripped of. The java source file name must begin and
	 * match with the given source folder.
	 * 
	 * @param folderName
	 *            Describes the source folder where the java source file
	 *            resides.
	 * @param sourceFileName
	 * @return
	 */
	String extractPackageName(URL sourceFolderUrl, URL sourceFileUrl) {

		String folderName = sourceFolderUrl.getPath();

		if (folderName.trim().length() > 0 && !folderName.endsWith("/")) {
			folderName += "/";
		}
		String sourceFileName = sourceFileUrl.getPath();

		if (!sourceFileName.startsWith(folderName)) {
			return "";
		}

		String nameWithoutSourceFolder = sourceFileName.substring(folderName.length());

		String packageName = "";

		int lastSlashIndex = nameWithoutSourceFolder.lastIndexOf("/");

		if (lastSlashIndex > 0) {
			packageName = nameWithoutSourceFolder.substring(0, lastSlashIndex);
		}

		String javaPackageName = packageName.replaceAll("/", ".");

		if (javaPackageName.startsWith(".")) {
			javaPackageName = javaPackageName.substring(1);
		}

		return javaPackageName;
	}

	/**
	 * Extracts the class name of a given java source file name.
	 * 
	 * @param sourceFileNameWithPath
	 *            The name of the source file with or without package folders.
	 *            The name must end with ".java".
	 * @return
	 */
	String extractClassName(URL sourceFileNameUrl) {

		String sourceFileNameWithPath = sourceFileNameUrl.getPath();

		if (!sourceFileNameWithPath.endsWith(".java")) {
			return "";
		}

		String fileNameWithoutPath;

		int lastSlashIndex = sourceFileNameWithPath.lastIndexOf("/");

		if (lastSlashIndex > 0) {

			fileNameWithoutPath = sourceFileNameWithPath.substring(lastSlashIndex + 1, sourceFileNameWithPath.length());

		} else
			fileNameWithoutPath = sourceFileNameWithPath;

		// Strip ".java" before returning result
		return fileNameWithoutPath.substring(0, fileNameWithoutPath.length() - 5);
	}

	/**
	 * Returns the reader's contents line by line in the order they appear in
	 * the file.
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	List<String> extractFileContent(BufferedReader reader) throws IOException {

		List<String> sourceText = new ArrayList<String>();

		String lineRead = null;
		while ((lineRead = reader.readLine()) != null) {
			sourceText.add(lineRead + "\n");
		}

		return sourceText;

	}

	private Set<File> getSourceFiles(File folder) throws IOException {

		// TODO: verify whether symbolic links can lead to infinite loops

		TreeSet<File> result = new TreeSet<File>();

		File[] javaFiles = folder.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".java");
			}

		});

		if (javaFiles != null) {
			for (int fileCount = 0; fileCount < javaFiles.length; fileCount++) {

				result.add(javaFiles[fileCount]);
			}
		}

		File[] directories = folder.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return dir.isDirectory();
			}

		});

		if (directories != null) {
			for (int dirCount = 0; dirCount < directories.length; dirCount++) {

				result.addAll(getSourceFiles(directories[dirCount]));
			}
		}

		return result;

	}
}
