package mut.mutantgen.bcel;

public class InputClass {

	public int branch(int a, int b) {
		if (a == 1) {
			if (b == 2) {
				return 1;
			} else {
				return 2;
			}
		}
		return 3;
	}

}