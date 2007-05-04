package com.mutation.transform.asm.transitions;


public class ROR_Transition implements ITransition {
	
	protected String sourceInstruction;
	
	protected String targetInstruction;
	
	public void mutate(byte[] byteCode) {
		
		// byteCode.exchangeInstruction(sourceInstruction, targetInstruction);
	}

}
