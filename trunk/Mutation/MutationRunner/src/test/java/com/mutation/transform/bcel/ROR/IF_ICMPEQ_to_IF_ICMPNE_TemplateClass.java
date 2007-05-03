package com.mutation.transform.bcel.ROR;

public class IF_ICMPEQ_to_IF_ICMPNE_TemplateClass {

	public int branch(int a, int b) {
		if (a != 1) {
			if (b != 2) {
				return 1;
			} else {
				return 2;
			}
		}
		return 3;
	}

}