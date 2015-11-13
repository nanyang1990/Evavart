/*
 * Created on 10/07/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui;

import gui.cdd.IntegerDocument;
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

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CoupledAnimateDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel jlabelStopTime = new JLabel();
	JTextField jtextElapse = new JTextField();
	

	JTextField jTextLogFile = new JTextField();
	JTextField jTextModelFile = new JTextField();

	JButton jButtonLogFile = new JButton("...");
	JButton jButtonModelFile = new JButton("...");

	public CoupledAnimateDialog(MainFrame frame, String title, boolean modal) {

		super(frame, title, modal);
		try {
			jbInit();
			pack();			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}



	/* (non-Javadoc)
	 * @see gui.OkCancelJDialog#jbInit()
	 */
	protected void jbInit() throws Exception {
		super.jbInit();
		this.getContentPane().setLayout(new BorderLayout(5,5));
		this.getContentPane().add(getInputPanel(),BorderLayout.NORTH);		
		this.getContentPane().add(getButtonsPanel(),BorderLayout.CENTER);
		//this.setSize(100,200);
	}
	
	private JPanel getButtonsPanel(){
		JPanel retr = new JPanel();
		retr.setLayout(new GridLayout(1,2));
		retr.add(getOkButton());
		retr.add(getCancelButton());
		return retr;
	}

	private JPanel getInputPanel(){
		
		
		JPanel retr = new JPanel();
		retr.setLayout(new GridLayout(1,2,5,5));
		retr.add(getLabels());
		retr.add(getInputs());
		
		return retr;
	}
	

	
	private JPanel getInputs(){
		JPanel logFile = new JPanel();
		logFile.setLayout(new BorderLayout(5,5));
		logFile.add(jTextLogFile, BorderLayout.CENTER);
		logFile.add(jButtonLogFile, BorderLayout.EAST);
		jButtonLogFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new ExtensionFilter("LOG"));

				int returnVal = fileChooser.showDialog(CoupledAnimateDialog.this, "Set");

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//get the selected file
					String imageFileName = fileChooser.getSelectedFile().getPath();
					jTextLogFile.setText(imageFileName);
				}
				
			}
		});

		try{
			String logfile = FileDataPersister.getInstance().get("animate.coupled", "logFile");
			File logFileFile = new File(logfile);
			if(logFileFile.exists()){
				jTextLogFile.setText(logfile);
			}
		}catch(Exception ex){}

		
		JPanel modelFile = new JPanel();
		modelFile.setLayout(new BorderLayout());
		modelFile.add(jTextModelFile, BorderLayout.CENTER);
		modelFile.add(jButtonModelFile, BorderLayout.EAST);
		jButtonModelFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new ExtensionFilter("GCM"));

				int returnVal = fileChooser.showDialog(CoupledAnimateDialog.this, "Set");

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//get the selected file
					String imageFileName = fileChooser.getSelectedFile().getPath();
					jTextModelFile.setText(imageFileName);
				}
				
			}
		});

		try{
			String modelfile = FileDataPersister.getInstance().get("animate.coupled", "modelFile");
			File modelFileFile = new File(modelfile);
			if(modelFileFile.exists()){
				jTextModelFile.setText(modelfile);
			}
		}catch(Exception ex){}

		
		JPanel jpanelStopTime = new JPanel();
		jpanelStopTime.setLayout(new BorderLayout());

		jtextElapse.setColumns(4);
		IntegerDocument integerDocumentMs = new IntegerDocument(jtextElapse, 4);
		jtextElapse.setDocument(integerDocumentMs);

		jtextElapse.setText(FileDataPersister.getInstance().get("animate.coupled",
				"elapse", "1000"));
		
		jpanelStopTime.add(jtextElapse, BorderLayout.CENTER);
		
		JPanel inputs = new JPanel();
		inputs.setLayout(new GridLayout(3,1));
		inputs.add(logFile);
		inputs.add(modelFile);
		inputs.add(jpanelStopTime);
		return inputs;
	}
	
	private JPanel getLabels(){
		JPanel retr = new JPanel();
		retr.setLayout(new GridLayout(3,1));
		retr.add(new JLabel("Log File"));
		retr.add(new JLabel("Coupled Model Definition"));
		retr.add(new JLabel("Delay between displays"));

		return retr;
		
	}
	
	public String getModel(){
		return jTextModelFile.getText();
	}
	
	public String getLog(){
		return jTextLogFile.getText();
	}
	
	public int getElapse(){
		if(jtextElapse.getText() == null || jtextElapse.getText().equals("")){
			jtextElapse.setText("1000");
		}
		return Integer.parseInt(jtextElapse.getText());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see gui.OkCancelJDialog#okButtonClicked()
	 */
	protected void okButtonClicked() {
		FileDataPersister.getInstance().put("animate.coupled", "elapse",""+getElapse());
		FileDataPersister.getInstance().put("animate.coupled", "logFile",getLog());
		FileDataPersister.getInstance().put("animate.coupled", "modelFile",getModel());
		super.okButtonClicked();
	}	
}
