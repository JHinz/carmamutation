package com.retroduction.carma.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface IByteCodeFileReader {

	public byte[] readByteCodeFromMultipleFolders(String classUnderTestName, File[] paths) throws IOException;

	public byte[] readByteCodeFromDisk(File originalClassFile) throws FileNotFoundException, IOException;

	public byte[] readByteCodeFromStream(InputStream originalClassFileInputStream) throws FileNotFoundException,
			IOException;
}
