package com.mutation.transform.bcel;

public class MultipleMethods_TemplateClass {

	public int branch1(int a, int b) {
		if (a != 1) {
			if (b != 2) {
				return 1;
			} else {
				return 2;
			}
		}
		return 3;
	}

	public int branch2(int a, int b) {
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