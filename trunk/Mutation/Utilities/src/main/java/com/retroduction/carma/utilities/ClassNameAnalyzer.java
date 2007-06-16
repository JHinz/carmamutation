package com.retroduction.carma.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassNameAnalyzer {

	public class ClassNameInfo {

		private String packageName;

		private String className;

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}
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
	public String extractPackageName(URL sourceFolderUrl, URL sourceFileUrl) {

		String folderName = sourceFolderUrl.getPath();

		if (folderName.trim().length() > 0 && !folderName.endsWith("/")) {
			folderName += "/";
		}

		String sourceFileName = sourceFileUrl.getPath();

		if (!sourceFileName.startsWith(folderName)) {
			return "";
		}

		if (!sourceFileName.endsWith(".java")) {
			return "";
		}

		String nameWithoutSourceFolder = sourceFileName.substring(folderName.length());
		nameWithoutSourceFolder = nameWithoutSourceFolder.substring(0, nameWithoutSourceFolder.length() - 5);

		nameWithoutSourceFolder = nameWithoutSourceFolder.replaceAll("/", ".");

		String javaPackageName = extractClassNameInfo(nameWithoutSourceFolder).getPackageName();

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
	public String extractClassName(URL sourceFileNameUrl) {

		String sourceFileNameWithPath = sourceFileNameUrl.getPath();

		if (!sourceFileNameWithPath.endsWith(".java")) {
			return "";
		}

		sourceFileNameWithPath = sourceFileNameWithPath.substring(0, sourceFileNameWithPath.length() - 5);
		sourceFileNameWithPath = sourceFileNameWithPath.replaceAll("/", ".");

		return extractClassNameInfo(sourceFileNameWithPath).getClassName();
	}

	public ClassNameInfo extractClassNameInfo(String fullyQualifiedClassName) {

		ClassNameInfo result = new ClassNameInfo();

		int lastDotIndex = fullyQualifiedClassName.lastIndexOf(".");

		if (lastDotIndex > 0) {
			result.setClassName(fullyQualifiedClassName.substring(lastDotIndex + 1, fullyQualifiedClassName.length()));
			result.setPackageName(fullyQualifiedClassName.substring(0, lastDotIndex));
		} else {
			result.setClassName(fullyQualifiedClassName);
			result.setPackageName("");
		}

		return result;
	}

	/**
	 * Returns the reader's contents line by line in the order they appear in
	 * the file.
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public List<String> extractFileContent(BufferedReader reader) throws IOException {

		List<String> sourceText = new ArrayList<String>();

		String lineRead = null;
		while ((lineRead = reader.readLine()) != null) {
			sourceText.add(lineRead + "\n");
		}

		return sourceText;

	}

}
