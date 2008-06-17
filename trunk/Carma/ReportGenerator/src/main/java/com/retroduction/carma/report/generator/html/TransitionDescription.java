/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.retroduction.carma.report.generator.html;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class TransitionDescription implements ITransitionDescription {
	private static final String BUNDLE_NAME = "com.retroduction.carma.report.generator.html.TransitionDescription"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public TransitionDescription() {
	}

	public String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "" + key + " (no description available)";
		}
	}
}
