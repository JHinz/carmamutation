package com.mutation.eclipse.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends org.eclipse.jface.preference.PreferencePage implements IWorkbenchPreferencePage {

	@Override
	protected Control createContents(Composite arg0) {
		Composite container = new Composite(arg0, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		Label informationLabel = new Label(container, 1);
		informationLabel.setText("This is a sample mutation preference page");

		return container;
	}

	public void init(IWorkbench workbench) {

	}

}