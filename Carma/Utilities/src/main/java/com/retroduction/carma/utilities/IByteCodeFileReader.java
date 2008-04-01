/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
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
