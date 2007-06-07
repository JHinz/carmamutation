package com.retroduction.carma.core.transform;

public abstract class AbstractTransitionGroup {

	//TODO why is the useDefaultTransitions ? In the abstract class it is not used at all.
	public AbstractTransitionGroup(boolean useDefaultTransitions) {
	}

	public abstract ITransition[] getTransitions();

	public abstract void setTransitions(ITransition[] transitions);

	public abstract void initWithDefaultTransitions();

	public abstract String getName();

}
