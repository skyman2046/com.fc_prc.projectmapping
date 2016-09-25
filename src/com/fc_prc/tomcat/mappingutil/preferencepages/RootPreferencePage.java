package com.fc_prc.tomcat.mappingutil.preferencepages;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class RootPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	public RootPreferencePage() {
		// TODO Auto-generated constructor stub
	}

	public RootPreferencePage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public RootPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
	}

	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayout(new RowLayout());
		new Label(topComp, SWT.NONE).setText("If you don't use Tomcat installation directory as your Server Location,\n"
				+ "use this plug-in to do a project mapping!\n"
				+ "Working with Tomcat Manager Plugin obtain higher efficiency.\n" + "");
		return topComp;
	}

}
