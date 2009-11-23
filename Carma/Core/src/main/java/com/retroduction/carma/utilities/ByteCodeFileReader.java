/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */

package com.retroduction.carma.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteCodeFileReader implements IByteCodeFileReader {

	public byte[] readByteCodeFromMultipleFolders(String classUnderTestName, File[] paths) throws IOException {

		for (File classDirectory : paths) {
			String path = classDirectory.getAbsolutePath() + "/" + classUnderTestName.replace('.', '/') + ".class";
			File originalClassFile = new File(path);
			if (originalClassFile.exists()) {
				return this.readByteCodeFromDisk(originalClassFile);
			}
		}
		throw new IOException("File not found");
	}

	public byte[] readByteCodeFromDisk(File originalClassFile) throws FileNotFoundException, IOException {

		FileInputStream inputStream = new FileInputStream(originalClassFile);

		return this.readByteCodeFromStream(inputStream);

	}
	
	public void writeByteCodeToDisk(File targetClassFile, byte[] byteCode) throws FileNotFoundException, IOException {

		if ( targetClassFile.exists()) {
			targetClassFile.delete();
		}
		
		FileOutputStream outputStream = new FileOutputStream(targetClassFile);
		
		outputStream.write(byteCode);
		
	}

	public byte[] readByteCodeFromStream(InputStream originalClassFileInputStream) throws FileNotFoundException,
			IOException {

		byte[] byteCode = new byte[0];
		byte[] buffer = new byte[512];
		int bytesRead = 0;

		while ((bytesRead = originalClassFileInputStream.read(buffer)) > 0) {
			byte[] newByteCode = new byte[byteCode.length + bytesRead];
			System.arraycopy(byteCode, 0, newByteCode, 0, byteCode.length);
			System.arraycopy(buffer, 0, newByteCode, byteCode.length, bytesRead);
			byteCode = newByteCode;
		}

		return byteCode;
	}

	public static int calculateByteCodeCRC(byte[] byteCode) {

		int result = 0;
		for (int i = 0; i < byteCode.length; i++) {
			result += (i % 13) * (byteCode[i] % 17);
		}
		return result;
	}

}
