package com.mutation.eclipse.launch;

import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputer;

public class LaunchConfigurationType implements ILaunchConfigurationType {

	public String getAttribute(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public ILaunchConfigurationDelegate getDelegate() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public ILaunchConfigurationDelegate getDelegate(String mode) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPluginIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSourceLocatorId() {
		// TODO Auto-generated method stub
		return null;
	}

	public ISourcePathComputer getSourcePathComputer() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getSupportedModes() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPublic() {
		// TODO Auto-generated method stub
		return false;
	}

	public ILaunchConfigurationWorkingCopy newInstance(IContainer container, String name) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supportsMode(String mode) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
