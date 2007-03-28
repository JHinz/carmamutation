package com.mutation;

import java.util.Set;

import com.mutation.events.IEventListener;

public interface IClassSetResolver {

	Set<String> determineClassNames(IEventListener eventListener);

}
