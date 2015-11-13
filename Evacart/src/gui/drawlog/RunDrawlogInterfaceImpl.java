/*
 *
 */
package gui.drawlog;

import gui.RunDrawlogInterface;
import gui.cdd.Translator;
import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
import gui.javax.util.FileDataPersister;
import gui.javax.util.SessionDataPersister;
import gui.model.model.ImportedCoupledModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

//import sun.awt.VerticalBagLayout;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RunDrawlogInterfaceImpl extends JDialog implements RunDrawlogInterface {
	//~ Instance fields ----------------------------------------------------------------------------

	CommandRunThread aCommandRunThread;

	JLabel jlabelCoupledCellName = new JLabel();
	JComboBox coupledCellNameCombo = new JComboBox();



	JLabel jlabelModelFile = new JLabel();
	JTextField jtextModelFile = new JTextField();
	JButton jbuttonModelFile = new JButton();

	JButton jbuttonReset = new JButton();
	JButton jbuttonStop = new JButton();
	JButton jbuttonRun = new JButton();
	JButton jbuttonClose = new JButton();
	
	JLabel drawlogLabel = new JLabel();
	JButton drawlogButton = new JButton();
	JTextField drawlogTextField = new JTextField();

	JLabel modelDirLabel = new JLabel();
	JButton modelDirButton = new JButton();
	JTextField modelDirTextField = new JTextField();



	JCheckBox precisionCheck = new JCheckBox();
	JTextField precisionInput = new JTextField();

	JCheckBox widthCheck = new JCheckBox();
	JTextField widthInput = new JTextField();

	JCheckBox timeCheck = new JCheckBox();
	JTextField timeInput = new JTextField();


	JLabel jlabelLogFile = new JLabel();
	JTextField jtextLogFile = new JTextField();
	JButton jbuttonLogFile = new JButton();

	JLabel jlabelOutputFile = new JLabel();
	JTextField jtextOutputFile = new JTextField();
	JButton jbuttonOutputFile = new JButton();


	JLabel jlabelStopTime = new JLabel();
	JTextArea textDrawlogOutput = new JTextArea();



	JScrollPane jscrollDrawlogOutput = new JScrollPane();
	
	//~ Constructors -------------------------------------------------------------------------------

	//private JFileChooser jFileChooserSimuRun;
	//Construct the dialog
	public RunDrawlogInterfaceImpl() {
			//this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		try {
			jbInit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//~ Methods ------------------------------------------------------------------------------------


	void checkWidth_actionPerformed(ActionEvent e) {
		if (widthCheck.isSelected()) {
			widthInput.setEnabled(true);
			widthInput.setBackground(SystemColor.white);
		}
		else {
			widthInput.setEnabled(false);
			widthInput.setBackground(SystemColor.control);
		}
	}

	void checkPrecision_actionPerformed(ActionEvent e) {
		if (precisionCheck.isSelected()) {
			precisionInput.setEnabled(true);
			precisionInput.setBackground(SystemColor.white);
		}
		else {
			precisionInput.setEnabled(false);
			precisionInput.setBackground(SystemColor.control);
		}
	}

	void checkTime_actionPerformed(ActionEvent e) {
		if (timeCheck.isSelected()) {
			timeInput.setEnabled(true);
			timeInput.setBackground(SystemColor.white);
		}
		else {
			timeInput.setEnabled(false);
			timeInput.setBackground(SystemColor.control);
		}
	}


	void jbuttonClose_actionPerformed(ActionEvent e) {
		dispose();
	}
/*
	void coupledCellName_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFixedDir(true);
		//getJFileChooserSimuRun().setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new ExtensionFilter("ev"));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			// Call openFile to attempt to load the text from file into JTextArea
			jtextCoupledCellName.setText(fileChooser.getSelectedFile().getName());
		}
	}
*/
	void jbuttonLogFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(modelDirTextField.getText()));
		fileChooser.setFixedDir(true);
		
		
		fileChooser.addChoosableFileFilter(new ExtensionFilter("log"));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
			jtextLogFile.setText(fileChooser.getSelectedFile().getName());
		}
	}

	void jbuttonOutputFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(modelDirTextField.getText()));
		fileChooser.setFixedDir(true);
		
		
		fileChooser.addChoosableFileFilter(new ExtensionFilter("drw"));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
			String filename = fileChooser.getSelectedFile().getName();
			if(!filename.toLowerCase().endsWith(".drw")){
				filename += ".drw";
			}
			jtextOutputFile.setText(filename);
		}
	}


	void jbuttonModelDir_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();

		// Use the OPEN version of the dialog, test return for Approve/Cancel
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//jFileChooserSimuRun.addChoosableFileFilter();
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			// Call openFile to attempt to load the text from file into JTextArea
			modelDirTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			jtextModelFile.setText("");
			jtextLogFile.setText("");
			updateCoupledCellNames("");
		}
	}


	void jbuttonModelFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(modelDirTextField.getText()));
		fileChooser.setFixedDir(true);

		fileChooser.addChoosableFileFilter(new ExtensionFilter("ma"));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			// Call openFile to attempt to load the text from file into JTextArea
			String modelName = fileChooser.getSelectedFile().getName();
			jtextModelFile.setText(modelName);
			updateCoupledCellNames(modelName);
		}
	
	}



	/**
	 * @param modelName
	 */
	private void updateCoupledCellNames(String modelName) {
		coupledCellNameCombo.removeAllItems();
		File model = new File(modelDirTextField.getText(),modelName);
		if(!"".equals(modelName) && model.exists()){
			try {
				Collection retr = Translator.importFrom(model);
				ImportedCoupledModel coupledModel = (ImportedCoupledModel)retr.iterator().next();
				
				
				Vector names = coupledModel.getComponentNames();
				for (Iterator iter = names.iterator(); iter.hasNext();) {
					String each = (String)iter.next();
					coupledCellNameCombo.addItem(each);

				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	void jbuttonReset_actionPerformed(ActionEvent e) {
		try {
			aCommandRunThread.destroy();
		} catch (NullPointerException exc) {
		}
		jbuttonRun.setEnabled(true);
		
	
		jtextModelFile.setText("");
		modelDirTextField.setText("");
		coupledCellNameCombo.removeAllItems();
		jtextLogFile.setText("");
		jtextOutputFile.setText("");		
		widthInput.setText("");
		precisionInput.setText("");
		timeInput.setText("");		
		widthInput.setEnabled(false);
		precisionInput.setEnabled(false);
		timeInput.setEnabled(false);		
		widthInput.setBackground(SystemColor.control);
		precisionInput.setBackground(SystemColor.control);
		timeInput.setBackground(SystemColor.control);		
		widthCheck.setSelected(false);
		precisionCheck.setSelected(false);
		timeCheck.setSelected(false);				
		textDrawlogOutput.setText("");
	}




	void jbuttonStop_actionPerformed(ActionEvent e) {
		try {
			aCommandRunThread.destroy();
		}
		catch (NullPointerException exc) {}
	}

	//When the "simulate" button is pressed, the program starts a new thread to run the simulator
	void jbuttonRun_actionPerformed(ActionEvent e) {
		aCommandRunThread = new CommandRunThread();
		aCommandRunThread.start();
	}

	//Opens the file chooser to find the simulator executable
	void jbuttonGraflogr_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		//fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// Use the OPEN version of the dialog, test return for Approve/Cancel
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			// Call openFile to attempt to load the text from file into JTextArea
			drawlogTextField.setText(fileChooser.getSelectedFile().getPath());
			FileDataPersister.getInstance().put("drawlog", "exe", fileChooser.getSelectedFile().getPath());
		}
	}

	//Component initialization
	private void jbInit() throws Exception {
		this.setTitle("Run Drawlog");
		Container contentPane = new JPanel();
		//contentPane.setLayout(new VerticalBagLayout());
		
			
			

		drawlogLabel.setText("Drawlog:");
		drawlogTextField.setText(FileDataPersister.getInstance().get("drawlog", "exe", ""));

		drawlogButton.setText("...");
		drawlogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonGraflogr_actionPerformed(e);
			}
		});


		modelDirLabel.setText("Model Dir:");
		modelDirButton.setText("...");
		modelDirButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonModelDir_actionPerformed(e);
			}
		});



		jlabelModelFile.setText("Model File:");
		jtextModelFile.setText(SessionDataPersister.getInstance().get("drawlog", "modelFile", ""));
		jbuttonModelFile.setText("...");
		jbuttonModelFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonModelFile_actionPerformed(e);
			}
		});


		jlabelCoupledCellName.setText("Coupled Cell Name:");
		//jtextCoupledCellName.setText(SessionDataPersister.getInstance().get("drawlog", "eventsFile", ""));


		jlabelLogFile.setText("Log File:");
		jtextLogFile.setText(SessionDataPersister.getInstance().get("drawlog", "logFile", ""));
		jbuttonLogFile.setText("...");
		jbuttonLogFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonLogFile_actionPerformed(e);
			}
		});

		jlabelOutputFile.setText("Output File:");
		jtextOutputFile.setText(SessionDataPersister.getInstance().get("drawlog", "outputFile", ""));
		jbuttonOutputFile.setText("...");
		jbuttonOutputFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonOutputFile_actionPerformed(e);
			}
		});



		JPanel labels = new JPanel();
		labels.setLayout(new GridLayout(6,1,5,5));
		labels.add(drawlogLabel);
		labels.add(modelDirLabel);
		labels.add(jlabelModelFile);
		labels.add(jlabelCoupledCellName);
		labels.add(jlabelLogFile);
		labels.add(jlabelOutputFile);
		
		JPanel inputs = new JPanel();
		inputs.setLayout(new GridLayout(6,1,5,5));
		inputs.add(drawlogTextField);
		inputs.add(modelDirTextField);
		inputs.add(jtextModelFile);
		inputs.add(coupledCellNameCombo);
		inputs.add(jtextLogFile);
		inputs.add(jtextOutputFile);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(6,1,5,5));
		buttons.add(drawlogButton);
		buttons.add(modelDirButton);		
		buttons.add(jbuttonModelFile);
		buttons.add(new JPanel());
		buttons.add(jbuttonLogFile);
		buttons.add(jbuttonOutputFile);
		
		JPanel files = new JPanel();
		files.setLayout(new BorderLayout());
		files.add(labels, BorderLayout.WEST);
		files.add(inputs, BorderLayout.CENTER);
		files.add(buttons, BorderLayout.EAST);				
		
		contentPane.add(files);

		widthCheck.setText("sets the width to show numbers");
		//checkOptionW.setBounds(new Rectangle(6, 191, 274, 21));
		widthCheck.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkWidth_actionPerformed(e);
			}
		});

		precisionCheck.setText("sets the precision to show numbers");
		//checkOptionW.setBounds(new Rectangle(6, 191, 274, 21));
		precisionCheck.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkPrecision_actionPerformed(e);
			}
		});

		timeCheck.setText("sets the time to start from");
		//checkOptionW.setBounds(new Rectangle(6, 191, 274, 21));
		timeCheck.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkTime_actionPerformed(e);
			}
		});


		precisionInput.setBackground(SystemColor.control);
		precisionInput.setEnabled(false);
		precisionInput.setColumns(3);
		precisionInput.setHorizontalAlignment(SwingConstants.RIGHT);
		//jtextWint.setBounds(new Rectangle(280, 191, 30, 21));
		widthInput.setBackground(SystemColor.control);
		widthInput.setEnabled(false);
		widthInput.setColumns(3);
		widthInput.setHorizontalAlignment(SwingConstants.RIGHT);

		timeInput.setBackground(SystemColor.control);
		timeInput.setEnabled(false);
		timeInput.setColumns(3);
		timeInput.setHorizontalAlignment(SwingConstants.RIGHT);

		JPanel checks = new JPanel();
		checks.setLayout(new GridLayout(3, 1, 5, 5));
		checks.add(widthCheck);
		checks.add(precisionCheck);		
		checks.add(timeCheck);
		
		JPanel data = new JPanel();
		data.setLayout(new GridLayout(3, 1, 5, 5));
		data.add(widthInput);
		data.add(precisionInput);
		data.add(timeInput);

		JPanel jpanelOptions = new JPanel();
		jpanelOptions.setBorder(BorderFactory.createEtchedBorder());
		jpanelOptions.setLayout(new GridLayout(1, 2, 5, 5));

		jpanelOptions.add(checks);
		jpanelOptions.add(data);

		contentPane.add(jpanelOptions);

		//jpanelSimuOutput elements
		textDrawlogOutput.setBackground(Color.black);
		textDrawlogOutput.setFont(new java.awt.Font("Dialog", 0, 10));
		textDrawlogOutput.setForeground(Color.green);
		textDrawlogOutput.setMinimumSize(new Dimension(100,300));
		textDrawlogOutput.setEditable(false);

		


		jscrollDrawlogOutput.getViewport().add(textDrawlogOutput, null);
		jscrollDrawlogOutput.setAutoscrolls(true);
		jscrollDrawlogOutput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jscrollDrawlogOutput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jscrollDrawlogOutput.setPreferredSize(new Dimension(100,300));				
		JPanel jpanelGraflogOutput = new JPanel();
		jpanelGraflogOutput.setBorder(BorderFactory.createEtchedBorder());
		jpanelGraflogOutput.setLayout(new BorderLayout());

		jpanelGraflogOutput.add(jscrollDrawlogOutput, BorderLayout.CENTER);

		contentPane.add(jpanelGraflogOutput);

		//jpanelOk_Cancel elements
		JPanel jpanelOk_Cancel = new JPanel();
		jpanelOk_Cancel.setBorder(BorderFactory.createEtchedBorder());
		jpanelOk_Cancel.setLayout(new GridLayout(1, 4, 5, 5));

		jbuttonRun.setText("Run");
		jbuttonRun.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonRun_actionPerformed(e);
			}
		});
		jbuttonClose.setText("Close");
		jbuttonClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonClose_actionPerformed(e);
			}
		});
		jbuttonReset.setText("Reset");
		jbuttonReset.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonReset_actionPerformed(e);
			}
		});

		jbuttonStop.setText("Stop");
		jbuttonStop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonStop_actionPerformed(e);
			}
		});

		jpanelOk_Cancel.add(jbuttonRun);
		jpanelOk_Cancel.add(jbuttonStop);
		jpanelOk_Cancel.add(jbuttonReset);
		jpanelOk_Cancel.add(jbuttonClose);

		contentPane.add(jpanelOk_Cancel, null);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(contentPane, BorderLayout.CENTER);

		this.setSize(550, 620);
		//this.setSize(getContentPane().getPreferredSize());


		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


		//Center the window
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();


		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	}


	//Creates the command line to run and runs it. The simulator output is
	// redirected to textSimuOutput.
	class CommandRunThread extends Thread {
		private Process p;
		private Thread t1;
		private Thread t2;
		
		public CommandRunThread() {
			super();
		}

		public void destroy() {
			try {
				p.destroy();
				t1.interrupt();
				t2.interrupt();
			}
			catch (NullPointerException e) {}
		}

		public void run() {
			Runtime rt = Runtime.getRuntime();

			//executionLines contains the simulator file and all the parameters.
			//The max number of posible parameters is 15
			String[] executionLines = new String[15];
			int index = 0;
			executionLines[index] = drawlogTextField.getText();
			index++;
			if (!(jtextModelFile.getText().equals(""))) {
				executionLines[index] = "-m";
				executionLines[index] = executionLines[index].concat(jtextModelFile.getText());
				index++;
			}
			if (!(coupledCellNameCombo.getSelectedItem().equals(""))) {
				executionLines[index] = "-c";
				executionLines[index] = executionLines[index].concat(coupledCellNameCombo.getSelectedItem().toString());
				index++;
			}

			if (timeCheck.isSelected() && !(timeInput.getText().equals(""))) {
				executionLines[index] = "-t";
				executionLines[index] = executionLines[index].concat(timeInput.getText());
				index++;
			}

			if (!(jtextLogFile.getText().equals(""))) {
				executionLines[index] = "-l";
				executionLines[index] = executionLines[index].concat(jtextLogFile.getText());
				index++;
			}

			if (widthCheck.isSelected() && !(widthInput.getText().equals(""))) {
				executionLines[index] = "-w";
				executionLines[index] = executionLines[index].concat(widthInput.getText());
				index++;
			}

			if (precisionCheck.isSelected() && !(precisionInput.getText().equals(""))) {
				executionLines[index] = "-p";
				executionLines[index] = executionLines[index].concat(precisionInput.getText());
				index++;
			}
			if(true){
				executionLines[index] = "-0";
				index++;
			}
			if(jtextOutputFile.getText() != null && !jtextOutputFile.getText().equals("")){
				executionLines[index] = ">";
				executionLines[index] = executionLines[index].concat(jtextOutputFile.getText());
				index++;
			}

			//cmdLine is the same as ExecutionLines, but without the null strings
			String[] cmdLine = new String[index];
			for (int j = 0; j < index; j++) {
				cmdLine[j] = executionLines[j];
				System.err.print(cmdLine[j] + " ");
			}
			System.err.println();
			
			try {
				jbuttonRun.setEnabled(false);

				p = Runtime.getRuntime().exec(cmdLine, null, new File(modelDirTextField.getText()));
				final BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(p.getInputStream()));
				final BufferedReader errorBuffer = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				//BufferedWriter outputBuffer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
				try {
					// Tries to open the model file indicated.
					// If it can open it, it uses the first form of the redirection
					// If it can't, it uses the second form
					String modelFile = jtextModelFile.getText();
					if (modelFile.equals("")) {
						modelFile = "model.ma";
					}

					File file = new File(modelDirTextField.getText()+"/"+modelFile);
					FileReader in = new FileReader(file);
					

					//create the output file if necessary
					final FileWriter outputFileWriter;
					if(jtextOutputFile.getText() != null && !jtextOutputFile.getText().equals("")){
						outputFileWriter = new FileWriter(new File(modelDirTextField.getText()+"/"+jtextOutputFile.getText()));
					}
					else{
						outputFileWriter = null;
					}
					

					//This code works if the simulator doesn't throw an error

					t1 = new Thread(new Runnable() {
						public void run() {
							
							try {
								//
								if(outputFileWriter != null){
									textDrawlogOutput.append("Writting output to file\r\n");
								}
								 
								String line;
								while ((line = inputBuffer.readLine()) != null) {
									if(outputFileWriter != null){
										outputFileWriter.write(line.concat("\r\n"));
									}
									else{
										textDrawlogOutput.append(line.concat("\r\n"));
										//System.err.println("* ");
										int max = jscrollDrawlogOutput.getVerticalScrollBar().getModel().getMaximum();
										jscrollDrawlogOutput.getVerticalScrollBar().getModel().setValue(max);			
									}
								}
								if(outputFileWriter != null){
									textDrawlogOutput.append("Process Finished\r\n");
								}
								
								inputBuffer.close();
							}
							catch (IOException e) {
									textDrawlogOutput.append(e.toString() + "\r\n");
									int max = jscrollDrawlogOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollDrawlogOutput.getVerticalScrollBar().getModel().setValue(max);			
								 
								e.printStackTrace();
							}
							
						}
					});
					t1.start();
					
					t2 = new Thread(new Runnable() {
						public void run() {
							try {
								String line;
								while ((line = errorBuffer.readLine()) != null) {
									textDrawlogOutput.append(line.concat("\r\n"));
									//System.err.print("# ");	
									int max = jscrollDrawlogOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollDrawlogOutput.getVerticalScrollBar().getModel().setValue(max);			
								}
								errorBuffer.close();
							}
							catch (IOException e) {
									textDrawlogOutput.append(e.toString() + "\r\n");
									int max = jscrollDrawlogOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollDrawlogOutput.getVerticalScrollBar().getModel().setValue(max);			
 
								e.printStackTrace();
							}
							
						}
					});
					t2.start();

					while(t1.isAlive() || t2.isAlive()){
						//System.err.print(". ");
					}
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
					//This code works if the simulator throws an error
					t1 = new Thread(new Runnable() {
						public void run() {
							
							try {
								// 
								String line;
								while ((line = inputBuffer.readLine()) != null) {
									textDrawlogOutput.append(line.concat("\r\n"));
									int max = jscrollDrawlogOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollDrawlogOutput.getVerticalScrollBar().getModel().setValue(max);			
								}
								inputBuffer.close();
							}
							catch (IOException e) {
									textDrawlogOutput.append(e.toString() + "\r\n");
									int max = jscrollDrawlogOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollDrawlogOutput.getVerticalScrollBar().getModel().setValue(max);			
								 
								e.printStackTrace();
							}
							
						}
					});
					t1.start();
					
					t2 = new Thread(new Runnable() {
						public void run() {
							try {
								String line;
								while ((line = errorBuffer.readLine()) != null) {
									textDrawlogOutput.append(line.concat("\r\n"));
									int max = jscrollDrawlogOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollDrawlogOutput.getVerticalScrollBar().getModel().setValue(max);			
								}
								errorBuffer.close();
							}
							catch (IOException e) {
									textDrawlogOutput.append(e.toString() + "\r\n");
									int max = jscrollDrawlogOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollDrawlogOutput.getVerticalScrollBar().getModel().setValue(max);			

								e.printStackTrace();
							}
							
						}
					});
					t2.start();
					while(t1.isAlive() || t2.isAlive()){
						//System.err.print(". ");
					}
					
				}
				finally{
					inputBuffer.close();
					errorBuffer.close();
					textDrawlogOutput.append("Writting output to file finished\r\n");
					int max = jscrollDrawlogOutput.getVerticalScrollBar().getModel().getMaximum();
					jscrollDrawlogOutput.getVerticalScrollBar().getModel().setValue(max);			
					
				}
				
			}
			catch (Exception err) {
				err.printStackTrace();
				System.err.println("Error executing the graflog");
				textDrawlogOutput.append("Writting output to file finished\r\n");
			}
			
			jbuttonRun.setEnabled(true);
		}
	}
}
