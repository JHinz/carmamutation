/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
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
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaLaunchDelegate;
import org.eclipse.jdt.launching.JavaRuntime;
import org.osgi.framework.Bundle;

import com.mutation.eclipse.evaluate.ResultReader;

public class Launcher extends JavaLaunchDelegate {

	private static final String MUTATION_CORE_PLUGIN_ID = "MutationEclipsePlugin";

	private static final String MUTATION_RUNNNER_CLASSNAME = "com.mutation.MutationRunner";

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();

		ILaunchConfigurationWorkingCopy wc = manager.getLaunchConfiguration(configuration.getMemento())
				.getWorkingCopy();

		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, MUTATION_RUNNNER_CLASSNAME);

		Activator.getDefault().getBundle().getRegisteredServices();

		try {
			Bundle bundle = Platform.getBundle(MUTATION_CORE_PLUGIN_ID);

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
			e.printStackTrace();
		}

		ILaunchConfiguration config = wc.doSave();

		super.launch(config, mode, launch, monitor);

		ResultReader reader = new ResultReader();

		while (!launch.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// MutationRun report = reader.readResults("report.xml",
		// this.getWorkingDirectory(config).toString());

		System.out.print("Finished");
	}
}
