package com.mutation.transform.bcel.ROR;

public class IF_ICMPNE_to_IF_ICMPEQ_TemplateClass {

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