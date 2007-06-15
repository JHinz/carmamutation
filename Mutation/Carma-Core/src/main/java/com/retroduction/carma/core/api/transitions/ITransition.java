package com.retroduction.carma.core.api.transitions;

import java.util.List;

import com.retroduction.carma.core.api.transitions.events.IEventListener;
import com.retroduction.carma.core.runner.Mutant;

public interface ITransition {

	public List<Mutant> applyTransitions(byte[] byteCode, IEventListener eventListener);

	public String getName();

}
