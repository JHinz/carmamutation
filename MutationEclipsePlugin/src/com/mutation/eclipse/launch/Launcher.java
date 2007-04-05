package com.mutation.eclipse.launch;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import mutationeclipseplugin.Activator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaLaunchDelegate;
import org.eclipse.jdt.launching.JavaRuntime;
import org.osgi.framework.Bundle;

import com.mutation.Driver;

public class Launcher extends JavaLaunchDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {

		new Driver();

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager
				.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
		ILaunchConfigurationWorkingCopy wc = type.newInstance(null, "MutationConfig");
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "MutationEclipseSampleProject");
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "com.mutation.Driver");

		Activator.getDefault().getBundle().getRegisteredServices();

		try {
			Bundle bundle = Platform.getBundle("MutationCorePlugin");

			URL bundleURL = org.eclipse.core.runtime.FileLocator.find(bundle, new Path("/"), null);

			bundleURL = FileLocator.toFileURL(bundleURL);

			Enumeration enums = bundle.findEntries("/", "*.jar", true);

			List<String> classPath = new ArrayList<String>();

			while (enums.hasMoreElements()) {
				URL classPathURL = (URL) enums.nextElement();
				IPath iPath = new Path(bundleURL.getPath() + classPathURL.getPath());
				IRuntimeClasspathEntry entry = JavaRuntime.newArchiveRuntimeClasspathEntry(iPath);
				classPath.add(entry.getMemento());

			}

			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ILaunchConfiguration config = wc.doSave();

		super.launch(config, mode, launch, monitor);

	}

}
