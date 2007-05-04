package com.mutation.transform.asm;

import java.util.ArrayList;
import java.util.List;

import com.mutation.runner.EMutationOperator;
import com.mutation.runner.IMutantGenerator;
import com.mutation.runner.Mutant;
import com.mutation.runner.events.IEventListener;
import com.mutation.runner.events.MutantsGenerated;

public class MutantGenerator implements IMutantGenerator {

	public List<Mutant> generateMutants(String classUnderTest, byte[] originalClassByteCode,
			EMutationOperator operator, IEventListener listener) {

		List<Mutant> result = new ArrayList<Mutant>();
		if (operator.equals(EMutationOperator.ROR)) {
			RORMutantCreator creator = new RORMutantCreator();
			result.addAll(creator.generateMutants(classUnderTest, originalClassByteCode, listener));
		}

		listener.notifyEvent(new MutantsGenerated(result, classUnderTest, operator));

		return result;
	}

}
