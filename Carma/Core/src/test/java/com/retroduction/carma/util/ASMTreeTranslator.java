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
