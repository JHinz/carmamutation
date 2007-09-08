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
