
public class Sample {

	public int decide(boolean a, boolean b) {

		if (a && b) {
			return 1;
		}

		if (a || b) {
			return 2;
		}

		if (!a) {
			return 3;
		}

		return 4;
	}
}
