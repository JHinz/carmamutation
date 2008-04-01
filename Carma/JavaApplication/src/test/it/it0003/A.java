/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
public class A {

	private B b;

	private C c;

	public B getB() {

		if (b == null)
			b = new B();

		return b;
	}

	public void setB(B b) {
		this.b = b;
	}

	public C getC() {
		if (c == null)
			c = new C();
		return c;
	}

	public void setC(C c) {
		this.c = c;
	}

}
