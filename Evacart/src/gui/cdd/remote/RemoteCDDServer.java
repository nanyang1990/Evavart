package gui.cdd.remote;

import gui.CDDInterface;
import gui.InformDialog;
import gui.cdd.IntegerDocument;
import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
import gui.javax.util.FileDataPersister;
import gui.javax.util.SessionDataPersister;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RemoteCDDServer extends JDialog implements CDDInterface {
	//~ Instance fields ----------------------------------------------------------------------------

	private final static int INPUT_HEIGHT = 0;
	private final static int INPUT_WIDTH = 200;
	private final static int BUTTON_WIDTH = 40;
	
	CommandRunThread aCommandRunThread;
	
	JButton jbuttonClose = new JButton();
	JButton jbuttonEventsFile = new JButton();
	JButton jbuttonLogFile = new JButton();
	JButton jbuttonModelFile = new JButton();
	JButton jbuttonOutputFile = new JButton();
	JButton jbuttonReset = new JButton();
	JButton jbuttonStop = new JButton();
	JButton jbuttonSimulate = new JButton();
	JLabel jlabelEventsFile = new JLabel();
	JLabel jlabelLogFile = new JLabel();
	JLabel jlabelModelFile = new JLabel();
	JLabel jlabelOutputFile = new JLabel();
	JLabel jlabelSimulator = new JLabel();
	JLabel jlabelStopTime = new JLabel();
	JTextArea textSimuOutput = new JTextArea();
	JTextField jtextEventsFile = new JTextField();
	JTextField jtextLogFile = new JTextField();
	JTextField jtextModelFile = new JTextField();
	JTextField jtextOutputFile = new JTextField();
	JTextField jtextSimulatorIp = new JTextField();
	JTextField jtextSimulatorPort = new JTextField();
	private JTextField stopTimeH = new JTextField();
	private JTextField stopTimeM = new JTextField();
	private JTextField stopTimeMs = new JTextField();
	private JTextField stopTimeS = new JTextField();
	JScrollPane jscrollSimuOutput = new JScrollPane();

	//~ Constructors -------------------------------------------------------------------------------

	//private JFileChooser jFileChooserSimuRun;
	//Construct the dialog
	public RemoteCDDServer() {
		try {
			jbInit();
		}
		catch (Exception e) {
			(new InformDialog("Error starting Remote module", e)).setVisible(true);
		}
	}

	//~ Methods ------------------------------------------------------------------------------------

	void exit(ActionEvent e) {
	    FileDataPersister.getInstance().put("cdd.run", "cddIp", jtextSimulatorIp.getText());
	    FileDataPersister.getInstance().put("cdd.run", "cddPort", jtextSimulatorPort.getText());

		dispose();
	}

	void jbuttonEventsFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFixedDir(false);
		//getJFileChooserSimuRun().setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new ExtensionFilter("ev"));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			// Call openFile to attempt to load the text from file into JTextArea
			String filename = fileChooser.getSelectedFile().getAbsolutePath();
			jtextEventsFile.setText(filename);
			SessionDataPersister.getInstance().put("cdd.run","eventsFile", filename);
		}
	}

	void jbuttonLogFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFixedDir(false);

		fileChooser.addChoosableFileFilter(new ExtensionFilter("log"));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
			String filename = fileChooser.getSelectedFile().getAbsolutePath();
			if (!filename.toLowerCase().endsWith(".log")) {
				filename += ".log";
			}
			jtextLogFile.setText(filename);
			SessionDataPersister.getInstance().put("cdd.run","logFile", filename);
		}
	}

	void jbuttonModelFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFixedDir(false);

		fileChooser.addChoosableFileFilter(new ExtensionFilter("ma"));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			// Call openFile to attempt to load the text from file into JTextArea
			setMA(fileChooser.getSelectedFile());
		}
		
	}

	/**
     * @param fileChooser
     */
    public void setMA(File file) {
        String filename = file.getAbsolutePath();
        jtextModelFile.setText(filename);
        SessionDataPersister.getInstance().put("cdd.run","modelFile", filename);
    }

    void jbuttonOutputFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFixedDir(false);

		fileChooser.addChoosableFileFilter(new ExtensionFilter("out"));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {

			String filename = fileChooser.getSelectedFile().getAbsolutePath();
			if (!filename.toLowerCase().endsWith(".out")) {
				filename += ".out";
			}
			jtextOutputFile.setText(filename);
			SessionDataPersister.getInstance().put("cdd.run","outputFile", filename);

		}
	}

	void jbuttonReset_actionPerformed(ActionEvent e) {

		jbuttonSimulate.setEnabled(true);
		//jtextSimulator.setText("");
		jtextModelFile.setText("");
		jtextEventsFile.setText("");
		jtextOutputFile.setText("");
		jtextLogFile.setText("");

		textSimuOutput.setText("");
		try {
			stopTimeH.getDocument().remove(0, 2);
		}
		catch (BadLocationException bleH) {
			//do nothing
		}
		try {
			stopTimeM.getDocument().remove(0, 2);
		}
		catch (BadLocationException bleM) {
			//do nothing
		}
		try {
			stopTimeS.getDocument().remove(0, 2);
		}
		catch (BadLocationException bleS) {
			//do nothing
		}
		try {
			stopTimeMs.getDocument().remove(0, 3);
		}
		catch (BadLocationException bleMs) {
			//do nothing
		}
	}

	void jbuttonStop_actionPerformed(ActionEvent e) {
		try {
			aCommandRunThread.stopRunning();
		}
		catch (NullPointerException exc) {}

	}

	//When the "simulate" button is pressed, the program starts a new thread to run the simulator
	void jbuttonSimulate_actionPerformed(ActionEvent e) {
		aCommandRunThread = new CommandRunThread();
		aCommandRunThread.start();
	}

	//Component initialization
	private void jbInit() throws Exception {
		this.setTitle("CD++ Remote Simulator");

		//Files Panel
		jlabelSimulator.setText("Simulator:");
		jlabelSimulator.setHorizontalAlignment(JLabel.RIGHT);
		jtextSimulatorIp.setPreferredSize(new Dimension(INPUT_WIDTH - 40, INPUT_HEIGHT));
		jtextSimulatorIp.setText(FileDataPersister.getInstance().get("cdd.run", "cddIp", ""));
		jtextSimulatorPort.setPreferredSize(new Dimension(40, INPUT_HEIGHT));
		jtextSimulatorPort.setText(FileDataPersister.getInstance().get("cdd.run", "cddPort", ""));


		jlabelModelFile.setText("Model File:");
		jlabelModelFile.setHorizontalAlignment(JLabel.RIGHT);
		jtextModelFile.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		jtextModelFile.setText(SessionDataPersister.getInstance().get("cdd.run", "modelFile", ""));
		jbuttonModelFile.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonModelFile.setPreferredSize(new Dimension(BUTTON_WIDTH, INPUT_HEIGHT));
		jbuttonModelFile.setText("...");
		jbuttonModelFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonModelFile_actionPerformed(e);
			}
		});

		jlabelEventsFile.setText("Events File:");
		jlabelEventsFile.setHorizontalAlignment(JLabel.RIGHT);
		jtextEventsFile.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		jtextEventsFile.setText(SessionDataPersister.getInstance().get("cdd.run", "eventsFile", ""));
		jbuttonEventsFile.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonEventsFile.setPreferredSize(new Dimension(BUTTON_WIDTH, INPUT_HEIGHT));
		jbuttonEventsFile.setText("...");
		jbuttonEventsFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonEventsFile_actionPerformed(e);
			}
		});

		jlabelOutputFile.setText("Output File:");
		jlabelOutputFile.setHorizontalAlignment(JLabel.RIGHT);
		jtextOutputFile.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		jtextOutputFile.setText(SessionDataPersister.getInstance().get("cdd.run", "outputFile", ""));
		jbuttonOutputFile.setText("...");
		jbuttonOutputFile.setPreferredSize(new Dimension(BUTTON_WIDTH, INPUT_HEIGHT));
		jbuttonOutputFile.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonOutputFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonOutputFile_actionPerformed(e);
			}
		});

		jlabelLogFile.setText("Log File:");
		jlabelLogFile.setHorizontalAlignment(JLabel.RIGHT);
		jtextLogFile.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
		jtextLogFile.setText(SessionDataPersister.getInstance().get("cdd.run", "logFile", ""));
		jbuttonLogFile.setText("...");
		jbuttonLogFile.setPreferredSize(new Dimension(BUTTON_WIDTH, INPUT_HEIGHT));
		jbuttonLogFile.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonLogFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonLogFile_actionPerformed(e);
			}
		});

		//jpanelStopTime elements
		jlabelStopTime.setText("Stop Time:");
		jlabelStopTime.setHorizontalAlignment(JLabel.RIGHT);
		stopTimeMs.setColumns(3);
		stopTimeH.setColumns(2);
		stopTimeM.setColumns(2);
		stopTimeS.setColumns(2);

		IntegerDocument integerDocumentH = new IntegerDocument(stopTimeH, 2);
		IntegerDocument integerDocumentM = new IntegerDocument(stopTimeM, 2);
		IntegerDocument integerDocumentS = new IntegerDocument(stopTimeS, 2);
		IntegerDocument integerDocumentMs = new IntegerDocument(stopTimeMs, 3);

		stopTimeH.setDocument(integerDocumentH);
		stopTimeM.setDocument(integerDocumentM);
		stopTimeS.setDocument(integerDocumentS);
		stopTimeMs.setDocument(integerDocumentMs);

		JPanel jpanelStopTime = new JPanel();
		jpanelStopTime.setLayout(new GridLayout(1, 6, 5, 5));

		jpanelStopTime.add(jlabelStopTime);
		jpanelStopTime.add(stopTimeH);
		jpanelStopTime.add(stopTimeM);
		jpanelStopTime.add(stopTimeS);
		jpanelStopTime.add(stopTimeMs);

		//jpanelSimuOutput elements
		textSimuOutput.setBackground(Color.black);
		textSimuOutput.setFont(new java.awt.Font("Dialog", 0, 10));
		textSimuOutput.setForeground(Color.green);
		textSimuOutput.setBorder(null);
		textSimuOutput.setEditable(false);

		jscrollSimuOutput.getViewport().add(textSimuOutput, null);

		JPanel jpanelSimuOutput = new JPanel();
		jpanelSimuOutput.setBorder(BorderFactory.createEtchedBorder());
		jpanelSimuOutput.setLayout(new BorderLayout());

		jpanelSimuOutput.add(jscrollSimuOutput, BorderLayout.CENTER);

		//jpanelOk_Cancel elements
		JPanel jpanelOk_Cancel = new JPanel();
		jpanelOk_Cancel.setBorder(BorderFactory.createEtchedBorder());
		jpanelOk_Cancel.setLayout(new GridLayout(1, 4, 5, 5));

		jbuttonSimulate.setText("Simulate");
		jbuttonSimulate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonSimulate_actionPerformed(e);
			}
		});
		jbuttonClose.setText("Close");
		jbuttonClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit(e);
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

		jpanelOk_Cancel.add(jbuttonSimulate);
		jpanelOk_Cancel.add(jbuttonStop);
		jpanelOk_Cancel.add(jbuttonReset);
		jpanelOk_Cancel.add(jbuttonClose);

		this.setModal(false);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
            public void windowActivated(WindowEvent arg0) { }

            public void windowClosed(WindowEvent arg0) {}

            public void windowClosing(WindowEvent arg0) {
                exit(null);
            }

            public void windowDeactivated(WindowEvent arg0) {}

            public void windowDeiconified(WindowEvent arg0) {}

            public void windowIconified(WindowEvent arg0) {}

            public void windowOpened(WindowEvent arg0) {}
        });

		//Center the window
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(500, 700);

		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		JPanel jpanelFiles = new JPanel();
		jpanelFiles.setBorder(BorderFactory.createEtchedBorder());
		jpanelFiles.setLayout(new BorderLayout(5, 5));
		JPanel filesLabes = new JPanel();
		JPanel filesInputs = new JPanel();
		JPanel filesButtons = new JPanel();

		filesLabes.setLayout(new GridLayout(6, 1, 5, 5));
		filesInputs.setLayout(new GridLayout(6, 1, 5, 5));
		filesButtons.setLayout(new GridLayout(6, 1, 5, 5));

		filesLabes.add(jlabelSimulator);
		filesLabes.add(jlabelModelFile);
		filesLabes.add(jlabelEventsFile);
		filesLabes.add(jlabelOutputFile);
		filesLabes.add(jlabelLogFile);
		filesLabes.add(jlabelStopTime);

		JPanel simulatorPortIp = new JPanel();
		simulatorPortIp.setLayout(new BorderLayout(5, 5));
		simulatorPortIp.add(jtextSimulatorIp, BorderLayout.CENTER);
		simulatorPortIp.add(jtextSimulatorPort, BorderLayout.EAST);
		filesInputs.add(simulatorPortIp);
		filesInputs.add(jtextModelFile);
		filesInputs.add(jtextEventsFile);
		filesInputs.add(jtextOutputFile);
		filesInputs.add(jtextLogFile);
		filesInputs.add(jpanelStopTime);

		filesButtons.add(new JPanel());
		filesButtons.add(jbuttonModelFile);
		filesButtons.add(jbuttonEventsFile);
		filesButtons.add(jbuttonOutputFile);
		filesButtons.add(jbuttonLogFile);
		filesButtons.add(new JPanel());

		jpanelFiles.add(filesLabes, BorderLayout.WEST);
		jpanelFiles.add(filesInputs, BorderLayout.CENTER);
		jpanelFiles.add(filesButtons, BorderLayout.EAST);

		this.getContentPane().setLayout(new BorderLayout(5, 5));
		this.getContentPane().add(jpanelFiles, BorderLayout.NORTH);
		this.getContentPane().add(jpanelSimuOutput, BorderLayout.CENTER);
		this.getContentPane().add(jpanelOk_Cancel, BorderLayout.SOUTH);
	}

	//~ Inner Classes ------------------------------------------------------------------------------

	//Creates the command line to run and runs it. The simulator output is
	// redirected to textSimuOutput.
	class CommandRunThread extends Thread {
		private boolean stopRunning = false;
		
		public CommandRunThread() {
			super();
		}

		public void stopRunning() {
			this.stopRunning = true;
		}

		public void run() {
			// prepare the model file for sending
			BufferedReader maReader = null;
			try {
				FileReader modelReader = new FileReader(jtextModelFile.getText());
				maReader = new BufferedReader(modelReader);
			}
			catch (Exception ex) {
				InformDialog.showError("Error reading model file.", ex);
				return;
			}

			// prepare the event file for sending
			BufferedReader evReader = null;
			try {
				if(jtextEventsFile.getText() != null && !jtextEventsFile.getText().equals("")){
					FileReader eventReader = new FileReader(jtextEventsFile.getText());
					evReader = new BufferedReader(eventReader);
				}
			}
			catch (Exception ex) {
				InformDialog.showError("Error reading event file.", ex);
				return;
			}

			// prepare the receipt of the log file
			FileWriter logSb;
			try {
				if(jtextLogFile.getText() != null && !jtextLogFile.getText().equals("")){
					logSb = new FileWriter(jtextLogFile.getText());
				}
				else{
					logSb = null;
				}
			}
			catch (IOException e) {
				InformDialog.showError("Error initializing log file.", e);
				return;
			}

			// prepare the receipt of the output file
			FileWriter outSb;
			try {
				if(jtextOutputFile.getText() != null && !jtextOutputFile.getText().equals("")){
					outSb = new FileWriter(jtextOutputFile.getText());
				}
				else{
					outSb = null;
				}
			}
			catch (IOException e) {
				InformDialog.showError("Error initializing out file.", e);
				return;
			}

			// get the server address and port
			String address = jtextSimulatorIp.getText();
			String port = jtextSimulatorPort.getText();
			int iPort;

			try {
				iPort = Integer.parseInt(port);
			}
			catch (NumberFormatException ex) {
				InformDialog.showError("Error parsing port", ex);
				return;
			}

			// prepare the socket for read and write
			Socket simSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;

			try {
				FileDataPersister.getInstance().put("cdd.run", "cddIp", address);
				FileDataPersister.getInstance().put("cdd.run", "cddIp", port);
				
				simSocket = new Socket(address, iPort);
				out = new PrintWriter(simSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(simSocket.getInputStream()));

				
				// send the model file
				String line = null;
				while ((line = maReader.readLine()) != null && !stopRunning) {
					out.println(line);
				}

				// send delimiter
				out.println(".");

				// send the event file
				line = evReader.readLine();
				if (line == null || line.equals("")) {
					line = " ";
				}
				while ((line = evReader.readLine()) != null && !stopRunning) {
					out.println(line);
				}

				// send delimiter
				out.println(".");

				// send simulation stop time

				out.println(stopTimeH.getText() + ":" + stopTimeM.getText() + ":" + stopTimeS.getText() + ":" + stopTimeMs.getText());

				// read the log file
				textSimuOutput.append("----LOG FILE---\r\n");
				while ((line = in.readLine()) != null && !line.equals(".") && !stopRunning) {
						if(logSb != null){
							logSb.write(line + "\n");
						}
						else{
							textSimuOutput.append(line.concat("\r\n"));
							int max = jscrollSimuOutput.getVerticalScrollBar().getModel().getMaximum();
							jscrollSimuOutput.getVerticalScrollBar().getModel().setValue(max);
						}

				}
				textSimuOutput.append("----END OF LOG FILE---\r\n");
				textSimuOutput.append("----OUT FILE---\r\n");
				// read the output file
				while ((line = in.readLine()) != null && !stopRunning) {
						if(outSb != null){
							outSb.write(line + "\n");
						}
						else{
							textSimuOutput.append(line.concat("\r\n"));
							int max = jscrollSimuOutput.getVerticalScrollBar().getModel().getMaximum();
							jscrollSimuOutput.getVerticalScrollBar().getModel().setValue(max);
						}
				}
				textSimuOutput.append("----END OF OUT FILE---\r\n");
			}
			catch (IOException ex1) {
				InformDialog.showError("An error occurred while communicating with the server", ex1);
			}
			finally {
				try {
					if(in != null)	in.close();
				}
				catch (IOException e1) {
				}
				
				if(out != null ) out.close();
				
				try {
					if(simSocket != null) simSocket.close();
				}
				catch (IOException e2) {
				}
				try {
					if(maReader != null) maReader.close();
				}
				catch (IOException e3) {
				}
				try {
					if(evReader != null) evReader.close();
				}
				catch (IOException e4) {
				}
				try {
					if(logSb != null) logSb.close();
				}
				catch (IOException e4) {
				}
				try {
					if(outSb != null) outSb.close();
				}
				catch (IOException e4) {
				}
				
			}
		}

	}	

	/* (non-Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	public void dispose() {
		if(this.aCommandRunThread != null){
			System.err.println("dispose");
			this.aCommandRunThread.stopRunning();
		}
		super.dispose();
	}

    /* (non-Javadoc)
     * @see gui.CDDInterface#setModelDir(java.io.File)
     */
    public void setModelDir(File path) {
        //Nothing to do on remote server
        
    }

}
