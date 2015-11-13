package gui;

import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
import gui.javax.util.FileDataPersister;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AtomicAnimateDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField jTextLogFile = new JTextField();
	JTextField jTextModelFile = new JTextField();

	JButton jButtonLogFile = new JButton("...");
	JButton jButtonModelFile = new JButton("...");

	JTextField chunck = new JTextField("");

	public AtomicAnimateDialog(MainFrame frame, String title, boolean modal) {

		super(frame, title, modal);
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gui.OkCancelJDialog#jbInit()
	 */
	protected void jbInit() throws Exception {
		super.jbInit();
		this.getContentPane().setLayout(new BorderLayout(5, 5));
		this.getContentPane().add(getInputPanel(), BorderLayout.NORTH);
		this.getContentPane().add(getButtonsPanel(), BorderLayout.CENTER);
		this.pack();
		//this.setSize(100,200);
	}

	private JPanel getButtonsPanel() {
		JPanel retr = new JPanel();
		retr.setLayout(new GridLayout(1, 2));
		retr.add(getOkButton());
		retr.add(getCancelButton());
		return retr;
	}

	private JPanel getInputPanel() {

		JPanel retr = new JPanel();
		retr.setLayout(new GridLayout(1, 2, 5, 5));
		retr.add(getLabels());
		retr.add(getInputs());

		return retr;
	}

	private JPanel getInputs() {
		JPanel logFile = new JPanel();
		logFile.setLayout(new BorderLayout(5, 5));
		logFile.add(jTextLogFile, BorderLayout.CENTER);
		logFile.add(jButtonLogFile, BorderLayout.EAST);
		jButtonLogFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new ExtensionFilter("LOG"));

				int returnVal = fileChooser.showDialog(
						AtomicAnimateDialog.this, "Set");

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//get the selected file
					String imageFileName = fileChooser.getSelectedFile()
							.getPath();
					jTextLogFile.setText(imageFileName);
				}

			}
		});
		
		try{
			String logfile = FileDataPersister.getInstance().get("animate.atomic", "logFile");
			File logFileFile = new File(logfile);
			if(logFileFile.exists()){
				jTextLogFile.setText(logfile);
			}
		}catch(Exception ex){}

		JPanel modelFile = new JPanel();
		modelFile.setLayout(new BorderLayout(5, 5));
		modelFile.add(jTextModelFile, BorderLayout.CENTER);
		modelFile.add(jButtonModelFile, BorderLayout.EAST);
		jButtonModelFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new ExtensionFilter("GCM"));

				int returnVal = fileChooser.showDialog(
						AtomicAnimateDialog.this, "Set");

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//get the selected file
					String modelFileName = fileChooser.getSelectedFile()
							.getPath();
					jTextModelFile.setText(modelFileName);
				}

			}
		});
		
		try{
			String modelfile = FileDataPersister.getInstance().get("animate.atomic", "modelFile");
			File modelFileFile = new File(modelfile);
			if(modelFileFile.exists()){
				jTextModelFile.setText(modelfile);
			}
		}catch(Exception ex){}

		
		chunck.setColumns(7);
		chunck.setText(FileDataPersister.getInstance().get("animate.atomic",
				"chunck", "50"));
		
		

		JPanel inputs = new JPanel();
		inputs.setLayout(new GridLayout(3, 1));
		inputs.add(logFile);
		inputs.add(modelFile);
		inputs.add(chunck);
		return inputs;
	}

	private JPanel getLabels() {
		JPanel retr = new JPanel();
		retr.setLayout(new GridLayout(3, 1));
		retr.add(new JLabel("Log File"));
		retr.add(new JLabel("Model File"));
		retr.add(new JLabel("Chunk"));

		return retr;

	}

	public String getLog() {
		return jTextLogFile.getText();
	}

	public String getModel() {
		return jTextModelFile.getText();
	}

	public int getChunck() {
		try {
			return Integer.parseInt(chunck.getText());
		} catch (Exception ex) {
			return 1000;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gui.OkCancelJDialog#okButtonClicked()
	 */
	protected void okButtonClicked() {
		FileDataPersister.getInstance().put("animate.atomic", "chunck",""+getChunck());
		FileDataPersister.getInstance().put("animate.atomic", "logFile",getLog());
		FileDataPersister.getInstance().put("animate.atomic", "modelFile",getModel());
		super.okButtonClicked();
	}
}