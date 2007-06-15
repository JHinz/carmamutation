package com.retroduction.carma.core.api.transitions;

import java.util.List;

import com.retroduction.carma.core.api.events.IEventListener;
import com.retroduction.carma.core.api.testrunners.Mutant;

public interface ITransition {

	public List<Mutant> applyTransitions(byte[] byteCode, IEventListener eventListener);

	public String getName();

}
