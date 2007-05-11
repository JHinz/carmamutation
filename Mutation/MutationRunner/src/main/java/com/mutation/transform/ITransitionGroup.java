package com.mutation.transform;

public abstract class ITransitionGroup {

	public ITransitionGroup(boolean useDefaultTransitions) {
	}

	public abstract ITransition[] getTransitions();

	public abstract void setTransitions(ITransition[] transitions);

	public abstract void initWithDefaultTransitions();

	public abstract String getName();

}
