package mut.mutantgen.bcel.common;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.mutation.util.ByteCodeFileReader;

import junit.framework.TestCase;

public class ByteCodeFileReaderTestCase extends TestCase {

	public void testReadByteCode() {

		ByteCodeFileReader reader = new ByteCodeFileReader();

		byte[] input = new byte[1025];

		for (int offset = 0; offset < input.length; offset++) {
			input[offset] = (byte) offset;
		}

		assertEquals(0, input[0]);
		assertEquals(127, input[127]);
		assertEquals(-128, input[128]);
		assertEquals(0, input[256]);
		assertEquals(0, input[512]);
		assertEquals(1, input[513]);
		assertEquals(0, input[1024]);

		byte[] output = null;
		try {
			output = reader.readByteCodeFromStream(new ByteArrayInputStream(input));
		} catch (IOException e) {
			fail("Unexcepted exception");
			return;
		}
		
		assertEquals(1025, output.length);
		assertEquals(0, output[0]);
		assertEquals(127, output[127]);
		assertEquals(-128, output[128]);
		assertEquals(0, output[256]);
		assertEquals(0, output[512]);
		assertEquals(1, output[513]);
		assertEquals(0, output[1024]);

	}

}
