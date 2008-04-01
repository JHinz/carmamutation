/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

public class ASMTreeTranslator {

	public class ByteCodeDiff {

		public int line;

		public String left;

		public String right;
	}

	public String getHumanReadableByteCode(byte[] byteCode1) {

		if (byteCode1 == null) {
			return null;
		}

		StringWriter stringWriter1 = new StringWriter();

		ClassReader classReader = new ClassReader(byteCode1);
		PrintWriter writer = new PrintWriter(stringWriter1);
		TraceClassVisitor traceVisitor = new TraceClassVisitor(writer);
		classReader.accept(traceVisitor, 0);

		return stringWriter1.toString();

	}

}
