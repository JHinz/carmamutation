package com.retroduction.carma.core.transform;

import java.util.List;

import com.retroduction.carma.core.runner.Mutant;
import com.retroduction.carma.core.runner.events.IEventListener;

public interface ITransition {

	public List<Mutant> applyTransitions(byte[] byteCode, IEventListener eventListener);

	public String getName();

}
