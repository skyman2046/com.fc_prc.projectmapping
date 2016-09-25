package com.fc_prc.tomcat.mappingutil.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.fc_prc.tomcat.mappingutil.plugins.MyPreferencePlugin;
import com.fc_prc.tomcat.mappingutil.preferencepages.SetTomcatPath;

public class AddMappingHandler extends AbstractHandler {

	IPreferenceStore ips;
	String path;
	Shell shell;
	Text workname_txt;

	// This constructor runs only once during eclipse's open and close.
	// So create an IPreferenceStore in it.
	// This IPreferenceStore can read confirguration from disk.
	public AddMappingHandler() {
		// TODO Auto-generated constructor stub
		MyPreferencePlugin plugin = new MyPreferencePlugin();
		ips = plugin.getPreferenceStore();
		initDialog();
	}

	/**
	 * work flow 1、 right-mouse menu-->create tomcat mapping; 2、enter
	 * apache-tomcat-x.x.x\conf\Catalina\localhost\; 3、open a dialog,input the
	 * web project name in it; 4、click submit will create a mpping.xml in
	 * apache-tomcat-x.x.x\conf\Catalina\localhost\;
	 */

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		path = ips.getString(SetTomcatPath.TOMCAT_INSTALLATION_PATH);
		// whether the path doesn't exist
		if (path.trim().contentEquals("")) {
			MessageDialog.openInformation(null, "error", "Locate tomcat path in Window-->Preferences firstly!");
			return null;
		}
		workname_txt.setText(getIWork());
		shell.open();
		return null;
	}

	private void initDialog() {
		Display display = Display.getDefault();
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = true;
		shell = new Shell(display, SWT.DIALOG_TRIM);
		shell.setLayout(gridLayout);
		shell.setText("hello swt");
		shell.setSize(400, 200);

		shell.addShellListener(new ShellListener() {

			@Override
			public void shellIconified(ShellEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void shellDeiconified(ShellEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void shellDeactivated(ShellEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void shellClosed(ShellEvent e) {
				// TODO Auto-generated method stub
				e.doit = false;
				shell.setVisible(false);
			}

			@Override
			public void shellActivated(ShellEvent e) {
				// TODO Auto-generated method stub

			}
		});

		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Label name_lbl = new Label(shell, SWT.NONE);
		name_lbl.setText("Project Name：");
		new Label(shell, SWT.NONE);

		workname_txt = new Text(shell, SWT.BORDER);
		workname_txt.setText("your web project name or Context root");
		workname_txt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Button sub_btn = new Button(shell, SWT.NONE);
		sub_btn.setText("submit");
		sub_btn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		Button cancel_btn = new Button(shell, SWT.NONE);
		cancel_btn.setText("cancel");
		cancel_btn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		sub_btn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				doSave();
				shell.setVisible(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		cancel_btn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.setVisible(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

	}

	protected void doSave() {
		String tomcat_path = path;
		tomcat_path = tomcat_path.replace("\\", "/");

		String server_location = ips.getString(SetTomcatPath.SERVER_LOCATION);
		server_location = server_location.replace("\\", "/");

		String deploy_name = ips.getString(SetTomcatPath.DEPLOY_PATH);
		deploy_name = deploy_name.replace("\\", "/");

		// 1、生成mapping name
		String project_name = workname_txt.getText().toString();
		String project_path = server_location + "/" + deploy_name + "/" + project_name;
		String mapping_name = project_name + ".xml";
		System.out.println("name:" + mapping_name);

		// 2、生成mapping content
		String mapping_content = "<?xml version='1.0' encoding='UTF-8'?>" + "<Context path='/" + project_name + "'"
				+ " docBase='" + project_path + "'" + " debug='0' privileged='true'></Context>";
		System.out.println("content:" + mapping_content);

		// 3、生成mapping.xml parent directory
		File file = new File(tomcat_path, "/conf/Catalina/localhost");
		if (!file.exists()) {
			file.mkdirs();
		}
		System.out.println("directory:" + file.getAbsolutePath());

		// 4、生成mapping.xml
		File file2 = new File(file, mapping_name);
		System.out.println("file:" + file2.getAbsolutePath());

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file2);
			// String conent = "<?xml version='1.0' encoding='UTF-8'?> <Context
			// path='/工程名' docBase='工程完整路径' debug='0'
			// privileged='true'></Context>";
			fos.write(mapping_content.getBytes());
			System.err.println("Write Succeessfully-->" + file2.getPath());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getIWork() {
		IProject project = null;
		ISelectionService selectionService = Workbench.getInstance().getActiveWorkbenchWindow().getSelectionService();

		ISelection selection = selectionService.getSelection();
		IStructuredSelection iss = (IStructuredSelection) selection;
		// iss.getFirstElement().getClass()
		Object element = iss.getFirstElement();
		if (element instanceof IResource) {
			project = ((IResource) element).getProject();
		} else if (element instanceof PackageFragmentRootContainer) {
			IJavaProject ijp = ((PackageFragmentRootContainer) element).getJavaProject();
			project = ijp.getProject();
		} else if (element instanceof IJavaElement) {
			IJavaProject ijp = ((IJavaElement) element).getJavaProject();
			project = ijp.getProject();
		}
		String workname = project.getName();
		return workname;
	}

}
