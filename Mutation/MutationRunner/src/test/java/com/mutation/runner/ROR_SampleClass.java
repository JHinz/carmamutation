package com.mutation.runner;

public class ROR_SampleClass {

	public int IF_ICMPEQ(int a) {

		if (a == 1) {
			if (a == 1) {
				if (a != 1) {
					return 1;
				} else {
					return 2;
				}
			} else {
				return 3;
			}
		} else {
			return 4;
		}

	}
}
