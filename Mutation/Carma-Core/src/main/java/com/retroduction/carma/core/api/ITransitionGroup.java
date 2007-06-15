package com.retroduction.carma.core.api;


public interface ITransitionGroup {

	public ITransition[] getTransitions();

	public void setTransitions(ITransition[] transitions);

	public void initWithDefaultTransitions();

	public String getName();

}
