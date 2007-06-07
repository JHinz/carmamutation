package com.retroduction.carma.core.runner.utililties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ByteCodeFileReader {

	public byte[] readByteCodeFromDisk(File originalClassFile) throws FileNotFoundException, IOException {

		FileInputStream inputStream = new FileInputStream(originalClassFile);

		return readByteCodeFromStream(inputStream);

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
