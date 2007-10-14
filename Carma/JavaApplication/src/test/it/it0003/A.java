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
