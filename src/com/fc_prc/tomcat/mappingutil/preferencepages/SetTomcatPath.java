package com.fc_prc.tomcat.mappingutil.preferencepages;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.fc_prc.tomcat.mappingutil.Activator;

public class SetTomcatPath extends PreferencePage implements IWorkbenchPreferencePage, ModifyListener {

	public static final String TOMCAT_INSTALLATION_PATH = "$TOMCAT_INSTALLATION_PATH";
	public static final String SERVER_LOCATION = "$SERVER_LOCATION";
	public static final String DEPLOY_PATH = "$DEPLOY_PATH";

	public static final String TOMCAT_DEFAULT = "";
	public static final String SERVER_DEFAULT = "";
	public static final String DEPLOY_DEFAULT = "webapps";

	// 定义三个文本框
	private Text tomcat_install_path_txt, server_location_txt, deploy_path_txt;

	// 定义按钮
	Button browser_btn1, browser_btn2;

	// 定义一个文件选择对话框
	private JFileChooser jfc;

	private IPreferenceStore ips;

	// 现在这种情况下，每次打开preference，都要创建一次这个类
	public SetTomcatPath() {
		// TODO Auto-generated constructor stub
	}

	public SetTomcatPath(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public SetTomcatPath(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	// 接口IWorkbenchPreferencePage的方法，它负责初始化。在此方法中设置一个
	// PreferenceStore对象，由此对象提供文本框值的读入/写出方法
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	/**
	 * 父类的界面创建方法
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayout(new GridLayout(3, false));

		new Label(topComp, SWT.NONE).setText("Tomcat Installation Directory:");
		tomcat_install_path_txt = new Text(topComp, SWT.BORDER);
		tomcat_install_path_txt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		browser_btn1 = new Button(topComp, SWT.NONE);
		browser_btn1.setText("Browse");

		new Label(topComp, SWT.NONE).setText("Server Location:");
		server_location_txt = new Text(topComp, SWT.BORDER);
		server_location_txt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		browser_btn2 = new Button(topComp, SWT.NONE);
		browser_btn2.setText("Browse");

		new Label(topComp, SWT.NONE).setText("Deploy Path:");
		deploy_path_txt = new Text(topComp, SWT.BORDER);
		deploy_path_txt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		initFileChooser();

		browser_btn1.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				// urlText.setText("this is new url");
				jfc.showDialog(new JLabel(), "Finder");
				File file = jfc.getSelectedFile();
				if (file.isDirectory()) {
					tomcat_install_path_txt.setText(file.getAbsolutePath());
				} else if (file.isFile()) {
					tomcat_install_path_txt.setText(file.getAbsolutePath());
				}
				//server_location_txt.setText(jfc.getSelectedFile().getName());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		browser_btn2.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				// urlText.setText("this is new url");
				jfc.showDialog(new JLabel(), "Finder");
				File file = jfc.getSelectedFile();
				if (file.isDirectory()) {
					server_location_txt.setText(file.getAbsolutePath());
				} else if (file.isFile()) {
					server_location_txt.setText(file.getAbsolutePath());
				}
				//server_location_txt.setText(jfc.getSelectedFile().getName());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		ips = getPreferenceStore();

		String tomcat_path = ips.getString(TOMCAT_INSTALLATION_PATH);
		if (tomcat_path == null) {
			tomcat_install_path_txt.setText(TOMCAT_DEFAULT);
		} else {
			tomcat_install_path_txt.setText(tomcat_path);
		}

		String server_path = ips.getString(SERVER_LOCATION);
		if (server_path == null) {
			server_location_txt.setText(SERVER_DEFAULT);
		} else {
			server_location_txt.setText(server_path);
		}

		String deploy_name = ips.getString(DEPLOY_PATH);
		if (deploy_name == null) {
			deploy_path_txt.setText(DEPLOY_DEFAULT);
		} else {
			deploy_path_txt.setText(deploy_name);
		}

		server_location_txt.addModifyListener(this);
		deploy_path_txt.addModifyListener(this);
		tomcat_install_path_txt.addModifyListener(this);
		return topComp;
	}

	private void initFileChooser() {
		jfc = new JFileChooser();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(jfc);
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

	}

	/**
	 * 实现自ModifyListener接口的方法，当三个文本框中发生修改时将执行此方法。 方法中对输入值进行了验证并将“确定”、“应用”两按钮使能
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		String errorStr = null;// 将原错误信息清空
		if (tomcat_install_path_txt.getText().toString().trim().length() == 0) {
			errorStr = "Illegal Tomcat Installation Directory";
		} else if (server_location_txt.getText().toString().trim().length() == 0) {
			errorStr = "Illegal Server Location";
		} else if (deploy_path_txt.getText().toString().trim().length() == 0) {
			errorStr = "Illegal Deploy";
		}

		setErrorMessage(errorStr);
		setValid(errorStr == null);
		getApplyButton().setEnabled(errorStr == null);

	}

	/**
	 * 父类的方法 单击Restore Defaults将还原设置
	 */
	@Override
	protected void performDefaults() {
		tomcat_install_path_txt.setText(TOMCAT_DEFAULT);
		server_location_txt.setText(SERVER_DEFAULT);
		deploy_path_txt.setText(DEPLOY_DEFAULT);
	}

	/**
	 * 父类的方法 单击 Apply 按钮将应用属性，并保存到preferenceStore
	 */
	@Override
	protected void performApply() {
		doSave();
		MessageDialog.openInformation(getShell(), "Msg", "save done!");
	}

	/**
	 * 父类的方法 单击 OK 按钮将应用属性，并保存到preferenceStore
	 */
	@Override
	public boolean performOk() {
		doSave();
		MessageDialog.openInformation(getShell(), "Msg", "save done!");
		return true;
	}

	private void doSave() {
		ips.setValue(TOMCAT_INSTALLATION_PATH, tomcat_install_path_txt.getText());
		ips.setValue(SERVER_LOCATION, server_location_txt.getText());
		ips.setValue(DEPLOY_PATH, deploy_path_txt.getText());
	}

}
