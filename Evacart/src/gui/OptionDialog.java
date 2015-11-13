package gui;

import gui.javax.file.JFileChooser;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;




public class OptionDialog extends OkCancelJDialog {

	// private mainFrame thisframe;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private JPanel dirPanel;
	private JPanel buttonsPanel;
	private JButton find;
	private JTextField dirField;
	private JLabel dirLabel;
	//private JFileChooser aFileChooser;

	public OptionDialog(MainFrame frame, String title, boolean modal) {
		super(frame, title, modal);
		this.mainFrame = frame;
		try {
			jbInit();
			pack();

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void jbInit() throws Exception {
		super.jbInit();

		getContentPane().setLayout(new GridLayout(2, 1, 5, 5));
		getContentPane().add(getDirPanel());
		getContentPane().add(getButtonsPanel());
		
		getOkButton().setText("Set");
		getOkButton().setMnemonic('S');
		
		getFind().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				findFile();
			}
		});
		
		

	}

	public void findFile() {
		JFileChooser aFileChooser = new JFileChooser();
		File actalDir = aFileChooser.getCurrentDirectory();
		File imageFile = new File(mainFrame.getImageRepositoryDir());
		aFileChooser.setCurrentDirectory(imageFile);
		aFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int retVal = aFileChooser.showOpenDialog(mainFrame);

		if (retVal == JFileChooser.APPROVE_OPTION) {
			File file = aFileChooser.getSelectedFile();
			if (file.isDirectory()) {
				dirField.setText(file.getPath());
			}
			else if (file.isFile()) {
				dirField.setText(file.getParent());
			}

		}
		aFileChooser.setLastAccesedDir(actalDir);
		
	}

	public String getDir() {
		return dirField.getText();
	}

	public JPanel getDirPanel() {
		if (dirPanel == null) {

			dirField = new JTextField(20);
			dirField.setText(mainFrame.getImageRepositoryDir());
			//this is necessary so the ENTER key is absorbed by the okButtonClicked otherwise it is consumed by the jTextField
			dirField.addKeyListener(this);
			
			dirLabel = new JLabel("Default Directory");

			dirPanel = new JPanel();
			dirPanel.setLayout(new GridLayout(1, 2));
			dirPanel.add(dirLabel);
			dirPanel.add(dirField);
			
		}
		return dirPanel;
	}
	public JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new GridLayout(1, 3, 5, 5));
			buttonsPanel.add(getFind());
			buttonsPanel.add(getOkButton());
			buttonsPanel.add(getCancelButton());
		}
		return buttonsPanel;
	}
	private JButton getFind(){
		if(find == null){
			find = new JButton();
			find.setText("Get");
			find.setMnemonic('G');
		}
		return find;
	}	
	/*
	private JFileChooser getFileChooser(){
		if(aFileChooser == null){
			aFileChooser = new JFileChooser(mainFrame.getDefaultDir());
			//JFileChooser does whatever is needed.
			//MyFileChooser seems to make some kind of prediction
			//of the file that the user wants.
			//If that functionality is needed, extend JFileChooser in 
			//a new class but never use the schema that follows.
			//new MyFileChooser(aFileChooser);
			aFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		return aFileChooser;
	}
	*/
}