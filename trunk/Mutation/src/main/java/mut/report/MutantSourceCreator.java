package mut.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import mut.Mutant;
import mut.EMutationType;
import mut.SourceCodeMapping;
import mut.util.ClassNameConverter;

public class MutantSourceCreator {

	private File sourceBaseDir;
	private File mutantOutputDir;

	// sourceBaseDir

	// build location using class name and source code file name

	public static void main(String[] args) throws IOException {

		Mutant mutant = new Mutant();
		SourceCodeMapping sourceMapping = new SourceCodeMapping();
		sourceMapping.setLineNo(16);
		sourceMapping.setClassName("sample.Sample");
		sourceMapping.setSourceFile("Sample.java");
		mutant.setSourceMapping(sourceMapping);
		mutant.setChangeDescription("Mike was here!");
		mutant.setMutationType(EMutationType.AOI);
		mutant.setClassName("sample.Sample");
		mutant.setName("Mutant_007");
		MutantSourceCreator creator = new MutantSourceCreator();
		creator.sourceBaseDir = new File("src");
		creator.mutantOutputDir = new File("mutantSources");
		creator.createMutantSourceCode(mutant);

	}

	/**
	 * 
	 * @param mutant
	 * @return the file name where the mutant code was written to
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public File createMutantSourceCode(Mutant mutant) throws FileNotFoundException, IOException {
		String relFileName;
		String relMutantName;
		File mutantDir;
		String sourceFileName = mutant.getSourceMapping().getSourceFile();
		if (sourceFileName == null) {
			// guess using class name
			String relPath = mutant.getClassName().replace('.', '/');
			relFileName = relPath + ".java";
			relMutantName = relPath +"_" +mutant.getName() +".java";
			mutantDir = new File(this.mutantOutputDir, relPath);

		} else {
			String relPath = ClassNameConverter.classNameToPackageName(mutant.getClassName()).replace('.', '/')	+ "/";
			relFileName = relPath + sourceFileName;
			relMutantName = relPath + sourceFileName.substring(0, sourceFileName.length() - ".java".length()) +"_" +mutant.getName() +".java";
			mutantDir = new File(this.mutantOutputDir, relPath);

		}

		mutantDir.mkdirs();
		File origFile = new File(this.sourceBaseDir, relFileName); 
		File mutantFile = new File(this.mutantOutputDir, relMutantName);
		//createMutantSourceCode(mutant, new FileReader(origFile), new FileWriter(mutantFile));
		return mutantFile;
	}

	// create annotated source

	// input:
	// original source
	// mutant -

	// output: mutant source

	public void createMutantSourceCode(Mutant mutant,Reader origFileReader, 
			Writer outputWriter) throws IOException {
		BufferedReader bf = new BufferedReader(origFileReader);
		BufferedWriter wr = new BufferedWriter(outputWriter);
		String line = null;
		for (int lineNo = 1; (line = bf.readLine()) != null; lineNo++) {

			if (lineNo == mutant.getSourceMapping().getLineNo()) {
				line += " //MUTANT CODE: " + mutant.getName() + ": "
						+ mutant.getChangeDescription();
			}

			wr.write("/* " + lineNo + " */ " + line);
			wr.newLine();

			//System.out.println("/* " + lineNo + " /* " + line);
		}

		wr.flush();
		bf.close();
		wr.close();

	}

	public File getMutantOutputDir() {
		return mutantOutputDir;
	}

	public void setMutantOutputDir(File mutantOutputDir) {
		this.mutantOutputDir = mutantOutputDir;
	}

	public File getSourceBaseDir() {
		return sourceBaseDir;
	}

	public void setSourceBaseDir(File sourceBaseDir) {
		this.sourceBaseDir = sourceBaseDir;
	}
}
