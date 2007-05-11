package com.mutation.transform;

import java.util.List;

import com.mutation.runner.Mutant;
import com.mutation.runner.events.IEventListener;

public interface ITransition {

	public List<Mutant> applyTransitions(byte[] byteCode, IEventListener eventListener);

}
