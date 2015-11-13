package gui.cdd.local;

import gui.CDDInterface;
import gui.cdd.IntegerDocument;
import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
import gui.javax.util.FileDataPersister;
import gui.javax.util.SessionDataPersister;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LocalCDDServer extends JDialog implements CDDInterface{
	//~ Instance fields ----------------------------------------------------------------------------

	CommandRunThread aCommandRunThread;
	JButton jbuttonClose = new JButton();
	JButton jbuttonEventsFile = new JButton();
	JButton jbuttonF = new JButton();
	JButton jbuttonLogFile = new JButton();
	JButton jbuttonModelDir = new JButton();
	JButton jbuttonModelFile = new JButton();
	JButton jbuttonOutputFile = new JButton();
	JButton jbuttonP = new JButton();
	JButton jbuttonReset = new JButton();
	JButton jbuttonStop = new JButton();
	JButton jbuttonSimulate = new JButton();
	JButton jbuttonSimulator = new JButton();
	JButton jbuttonV = new JButton();
	JCheckBox checkOptionB = new JCheckBox();
	JCheckBox checkOptionD = new JCheckBox();
	JCheckBox checkOptionF = new JCheckBox();
	JCheckBox checkOptionP = new JCheckBox();
	JCheckBox checkOptionQ = new JCheckBox();
	JCheckBox checkOptionR = new JCheckBox();
	JCheckBox checkOptionS = new JCheckBox();
	JCheckBox checkOptionV = new JCheckBox();
	JCheckBox checkOptionW = new JCheckBox();
	JCheckBox infinityCheckbox = new JCheckBox();
	JLabel jlabelEventsFile = new JLabel();
	JLabel jlabelLogFile = new JLabel();
	JLabel jlabelModelDir = new JLabel();
	JLabel jlabelModelFile = new JLabel();
	JLabel jlabelOutputFile = new JLabel();
	JLabel jlabelSimulator = new JLabel();
	JLabel jlabelStopTime = new JLabel();
	JTextArea textSimuOutput = new JTextArea();
	JTextField jtextD = new JTextField();
	JTextField jtextEventsFile = new JTextField();
	JTextField jtextF = new JTextField();
	JTextField jtextLogFile = new JTextField();
	JTextField jtextModelDir = new JTextField();
	JTextField jtextModelFile = new JTextField();
	JTextField jtextOutputFile = new JTextField();
	JTextField jtextP = new JTextField();
	JTextField jtextQ = new JTextField();
	JTextField jtextSimulator = new JTextField();
	private JTextField stopTimeH = new JTextField();
	private JTextField stopTimeM = new JTextField();
	private JTextField stopTimeMs = new JTextField();
	private JTextField stopTimeS = new JTextField();
	JTextField jtextV = new JTextField();
	JTextField jtextWdec = new JTextField();
	JTextField jtextWint = new JTextField();
	JScrollPane jscrollSimuOutput = new JScrollPane();
	
	//~ Constructors -------------------------------------------------------------------------------

	//private JFileChooser jFileChooserSimuRun;
	//Construct the dialog
	public LocalCDDServer() {
		try {
			jbInit();
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	//~ Methods ------------------------------------------------------------------------------------

	void checkOptionD_actionPerformed(ActionEvent e) {
		if (checkOptionD.isSelected()) {
			jtextD.setEnabled(true);
			jtextD.setBackground(SystemColor.white);
		}
		else {
			jtextD.setEnabled(false);
			jtextD.setBackground(SystemColor.control);
		}
	}

	void checkOptionF_actionPerformed(ActionEvent e) {
		if (checkOptionF.isSelected()) {
			jtextF.setEnabled(true);
			jbuttonF.setEnabled(true);
			jtextF.setBackground(SystemColor.white);
		}
		else {
			jtextF.setEnabled(false);
			jbuttonF.setEnabled(false);
			jtextF.setBackground(SystemColor.control);
		}
	}

	void checkOptionP_actionPerformed(ActionEvent e) {
		if (checkOptionP.isSelected()) {
			jtextP.setEnabled(true);
			jbuttonP.setEnabled(true);
			jtextP.setBackground(SystemColor.white);
		}
		else {
			jtextP.setEnabled(false);
			jbuttonP.setEnabled(false);
			jtextP.setBackground(SystemColor.control);
		}
	}

	void checkOptionQ_actionPerformed(ActionEvent e) {
		if (checkOptionQ.isSelected()) {
			jtextQ.setEnabled(true);
			jtextQ.setBackground(SystemColor.white);
		}
		else {
			jtextQ.setEnabled(false);
			jtextQ.setBackground(SystemColor.control);
		}
	}

	void checkOptionV_actionPerformed(ActionEvent e) {
		if (checkOptionV.isSelected()) {
			jtextV.setEnabled(true);
			jbuttonV.setEnabled(true);
			jtextV.setBackground(SystemColor.white);
		}
		else {
			jtextV.setEnabled(false);
			jbuttonV.setEnabled(false);
			jtextV.setBackground(SystemColor.control);
		}
	}

	void checkOptionW_actionPerformed(ActionEvent e) {
		if (checkOptionW.isSelected()) {
			jtextWint.setEnabled(true);
			jtextWdec.setEnabled(true);
			jtextWint.setBackground(SystemColor.white);
			jtextWdec.setBackground(SystemColor.white);
		}
		else {
			jtextWint.setEnabled(false);
			jtextWdec.setEnabled(false);
			jtextWint.setBackground(SystemColor.control);
			jtextWdec.setBackground(SystemColor.control);
		}
	}

	private void toogleTimeInfinity(boolean infiniteOn, Component comp) {
		if (infiniteOn) {
			stopTimeH.setEnabled(false);
			stopTimeM.setEnabled(false);
			stopTimeS.setEnabled(false);
			stopTimeMs.setEnabled(false);
			infinityCheckbox.setSelected(true);
			
		}
		else {
			stopTimeH.setEnabled(true);
			stopTimeM.setEnabled(true);
			stopTimeS.setEnabled(true);
			stopTimeMs.setEnabled(true);
			infinityCheckbox.setSelected(false);
		}
		comp.requestFocus();
	}

	void jbuttonClose_actionPerformed(ActionEvent e) {
		dispose();
	}

	void jbuttonEventsFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(jtextModelDir.getText()));
		fileChooser.setFixedDir(true);
		//getJFileChooserSimuRun().setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new ExtensionFilter("ev"));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			// Call openFile to attempt to load the text from file into JTextArea
			String filename = fileChooser.getSelectedFile().getAbsolutePath();
			jtextEventsFile.setText(filename);
			SessionDataPersister.getInstance().put("cdd.run","eventsFile", filename);			
		}
	}

	void jbuttonF_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(jtextModelDir.getText()));
		fileChooser.setFixedDir(true);

		fileChooser.resetChoosableFileFilters();
		//  jFileChooserSimuRun.addChoosableFileFilter(new ExtensionFilter("txt")); //file extension for the states information
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
			jtextF.setText(fileChooser.getSelectedFile().getName());
		}
	}

	void jbuttonLogFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(jtextModelDir.getText()));
		fileChooser.setFixedDir(true);

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

	void jbuttonModelDir_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();

		// Use the OPEN version of the dialog, test return for Approve/Cancel
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//jFileChooserSimuRun.addChoosableFileFilter();
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			// Call openFile to attempt to load the text from file into JTextArea
			setModelDir(fileChooser.getSelectedFile());
		}
	}

	/**
     * @param filename
     */
    public void setModelDir(File file) {
        String filename = file.getPath();
        jtextModelDir.setText(filename);
        SessionDataPersister.getInstance().put("cdd.run","modelDir", filename);
        
        jtextModelFile.setText("");
        jtextEventsFile.setText("");
        jtextOutputFile.setText("");
        jtextLogFile.setText("");
        jtextP.setText("");
        jtextV.setText("");
        jtextF.setText("");
    }

    void jbuttonModelFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(jtextModelDir.getText()));
		fileChooser.setFixedDir(true);

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
        String filename = file.getName();
        jtextModelFile.setText(filename);
        SessionDataPersister.getInstance().put("cdd.run","modelFile", filename);
    }

    void jbuttonOutputFile_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(jtextModelDir.getText()));
		fileChooser.setFixedDir(true);

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

	void jbuttonP_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(jtextModelDir.getText()));
		fileChooser.setFixedDir(true);


		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
			jtextP.setText(fileChooser.getSelectedFile().getName());
		}
	}


	void jbuttonReset_actionPerformed(ActionEvent e) {
		jbuttonSimulate.setEnabled(true);
		//jtextSimulator.setText("");
		jtextModelDir.setText("");		
		jtextModelFile.setText("");
		jtextEventsFile.setText("");
		jtextOutputFile.setText("");
		jtextLogFile.setText("");
		jtextD.setText("");
		jtextF.setText("");
		jtextP.setText("");
		jtextQ.setText("");
		jtextV.setText("");
		jtextWdec.setText("");
		jtextWint.setText("");
		jtextD.setEnabled(false);
		jtextF.setEnabled(false);
		jtextP.setEnabled(false);
		jtextQ.setEnabled(false);
		stopTimeH.setEnabled(false);
		stopTimeM.setEnabled(false);
		stopTimeMs.setEnabled(false);
		stopTimeS.setEnabled(false);
		jtextV.setEnabled(false);
		jtextWdec.setEnabled(false);
		jtextWint.setEnabled(false);
		jtextD.setBackground(SystemColor.control);
		jtextF.setBackground(SystemColor.control);
		jtextP.setBackground(SystemColor.control);
		jtextQ.setBackground(SystemColor.control);
		jtextV.setBackground(SystemColor.control);
		jtextWdec.setBackground(SystemColor.control);
		jtextWint.setBackground(SystemColor.control);
		jbuttonF.setEnabled(false);
		jbuttonP.setEnabled(false);
		jbuttonV.setEnabled(false);
		infinityCheckbox.setSelected(true);
		checkOptionB.setSelected(true);
		checkOptionD.setSelected(false);
		checkOptionF.setSelected(false);
		checkOptionP.setSelected(false);
		checkOptionQ.setSelected(false);
		checkOptionR.setSelected(false);
		checkOptionS.setSelected(false);
		checkOptionV.setSelected(false);
		checkOptionW.setSelected(false);
		textSimuOutput.setText("");
		try {
			stopTimeH.getDocument().remove(0, 2);
		} catch (BadLocationException bleH) {
			//do nothing
		}
		try {
			stopTimeM.getDocument().remove(0, 2);
		} catch (BadLocationException bleM) {
			//do nothing
		}
		try {
			stopTimeS.getDocument().remove(0, 2);
		} catch (BadLocationException bleS) {
			//do nothing
		}
		try {
			stopTimeMs.getDocument().remove(0, 3);
		} catch (BadLocationException bleMs) {
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

	//Opens the file chooser to find the simulator executable
	void jbuttonSimulator_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		//fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// Use the OPEN version of the dialog, test return for Approve/Cancel
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			// Call openFile to attempt to load the text from file into JTextArea
			jtextSimulator.setText(fileChooser.getSelectedFile().getPath());
			FileDataPersister.getInstance().put("cdd.run", "cddFile", fileChooser.getSelectedFile().getPath());
		}
	}

	void jbuttonV_actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(jtextModelDir.getText()));
		fileChooser.setFixedDir(true);

		fileChooser.resetChoosableFileFilters();
		//  jFileChooserSimuRun.addChoosableFileFilter(new ExtensionFilter("txt")); //file extension for the debug information
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
			jtextV.setText(fileChooser.getSelectedFile().getName());
		}
	}

	//Component initialization
	private void jbInit() throws Exception {
		this.setTitle("Run Simulator");

		Box contentPane = new Box(BoxLayout.Y_AXIS);

		//contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		//Panels
		JPanel jpanelFiles = new JPanel();
		jpanelFiles.setBorder(BorderFactory.createEtchedBorder());
		jpanelFiles.setLayout(new GridLayout(6, 1, 5, 5));

		//Files Panel
		jlabelSimulator.setText("Simulator:");
		jtextSimulator.setPreferredSize(new Dimension(200, 21));
		jtextSimulator.setText(FileDataPersister.getInstance().get("cdd.run", "cddFile", ""));

		jbuttonSimulator.setText("...");
		jbuttonSimulator.setPreferredSize(new Dimension(43, 21));
		jbuttonSimulator.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonSimulator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonSimulator_actionPerformed(e);
			}
		});

		JPanel simulatorContainer = new JPanel();
		simulatorContainer.add(jlabelSimulator);
		simulatorContainer.add(jtextSimulator);
		simulatorContainer.add(jbuttonSimulator);

		jpanelFiles.add(simulatorContainer);

		jlabelModelDir.setText("Model Dir:");
		jtextModelDir.setPreferredSize(new Dimension(200, 21));
		jtextModelDir.setText(SessionDataPersister.getInstance().get("cdd.run", "modelDir", ""));
		jbuttonModelDir.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonModelDir.setPreferredSize(new Dimension(43, 21));
		jbuttonModelDir.setText("...");
		jbuttonModelDir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonModelDir_actionPerformed(e);
			}
		});

		JPanel modelContainer = new JPanel();
		modelContainer.add(jlabelModelDir);
		modelContainer.add(jtextModelDir);
		modelContainer.add(jbuttonModelDir);

		jpanelFiles.add(modelContainer);

		jlabelModelFile.setText("Model File:");
		jtextModelFile.setPreferredSize(new Dimension(200, 21));
		jtextModelFile.setText(SessionDataPersister.getInstance().get("cdd.run", "modelFile", ""));
		jbuttonModelFile.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonModelFile.setPreferredSize(new Dimension(43, 21));
		jbuttonModelFile.setText("...");
		jbuttonModelFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonModelFile_actionPerformed(e);
			}
		});

		JPanel modelFilesContainer = new JPanel();
		modelFilesContainer.add(jlabelModelFile);
		modelFilesContainer.add(jtextModelFile);
		modelFilesContainer.add(jbuttonModelFile);

		jpanelFiles.add(modelFilesContainer);

		jlabelEventsFile.setText("Events File:");
		jtextEventsFile.setPreferredSize(new Dimension(200, 21));
		jtextEventsFile.setText(SessionDataPersister.getInstance().get("cdd.run", "eventsFile", ""));
		jbuttonEventsFile.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonEventsFile.setPreferredSize(new Dimension(43, 21));
		jbuttonEventsFile.setText("...");
		jbuttonEventsFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonEventsFile_actionPerformed(e);
			}
		});

		JPanel eventsFilesContainer = new JPanel();
		eventsFilesContainer.add(jlabelEventsFile);
		eventsFilesContainer.add(jtextEventsFile);
		eventsFilesContainer.add(jbuttonEventsFile);

		jpanelFiles.add(eventsFilesContainer);

		jlabelOutputFile.setText("Output File:");
		jtextOutputFile.setPreferredSize(new Dimension(200, 21));
		jtextOutputFile.setText(SessionDataPersister.getInstance().get("cdd.run", "outputFile", ""));
		jbuttonOutputFile.setText("...");
		jbuttonOutputFile.setPreferredSize(new Dimension(43, 21));
		jbuttonOutputFile.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonOutputFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonOutputFile_actionPerformed(e);
			}
		});

		JPanel outputFilesContainer = new JPanel();
		outputFilesContainer.add(jlabelOutputFile);
		outputFilesContainer.add(jtextOutputFile);
		outputFilesContainer.add(jbuttonOutputFile);

		jpanelFiles.add(outputFilesContainer);

		jlabelLogFile.setText("  Log File:");
		jtextLogFile.setPreferredSize(new Dimension(200, 21));
		jtextLogFile.setText(SessionDataPersister.getInstance().get("cdd.run", "logFile", ""));
		jbuttonLogFile.setText("...");
		jbuttonLogFile.setPreferredSize(new Dimension(43, 21));
		jbuttonLogFile.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonLogFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonLogFile_actionPerformed(e);
			}
		});

		JPanel logFilesContainer = new JPanel();
		logFilesContainer.add(jlabelLogFile);
		logFilesContainer.add(jtextLogFile);
		logFilesContainer.add(jbuttonLogFile);

		jpanelFiles.add(logFilesContainer);

		contentPane.add(jpanelFiles);

		//jpanelStopTime elements
		jlabelStopTime.setText("Stop Time: ");
		stopTimeMs.setColumns(3);
		infinityCheckbox.setSelected(true);
		infinityCheckbox.setText("Infinity");
		infinityCheckbox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
					toogleTimeInfinity(infinityCheckbox.isSelected(),infinityCheckbox);
			}
		});
		stopTimeH.setColumns(2);
		stopTimeM.setColumns(2);
		stopTimeS.setColumns(2);
		stopTimeH.setEnabled(false);
		stopTimeM.setEnabled(false);
		stopTimeMs.setEnabled(false);
		stopTimeS.setEnabled(false);

		IntegerDocument integerDocumentH = new IntegerDocument(stopTimeH, 2);
		IntegerDocument integerDocumentM = new IntegerDocument(stopTimeM, 2);
		IntegerDocument integerDocumentS = new IntegerDocument(stopTimeS, 2);
		IntegerDocument integerDocumentMs = new IntegerDocument(stopTimeMs, 3);

		stopTimeH.setDocument(integerDocumentH);
		stopTimeM.setDocument(integerDocumentM);
		stopTimeS.setDocument(integerDocumentS);
		stopTimeMs.setDocument(integerDocumentMs);
	
		stopTimeH.addMouseListener(new TimeMouseListener());
		stopTimeM.addMouseListener(new TimeMouseListener());
		stopTimeS.addMouseListener(new TimeMouseListener());
		stopTimeMs.addMouseListener(new TimeMouseListener());
		
		JPanel jpanelStopTime = new JPanel();
		jpanelStopTime.setLayout(new GridLayout(1, 6, 5, 5));
		jpanelStopTime.setBorder(BorderFactory.createEtchedBorder());

		jpanelStopTime.add(jlabelStopTime);
		jpanelStopTime.add(stopTimeH);
		jpanelStopTime.add(stopTimeM);
		jpanelStopTime.add(stopTimeS);
		jpanelStopTime.add(stopTimeMs);

		jpanelStopTime.add(infinityCheckbox);

		
		contentPane.add(jpanelStopTime);

		//jpanelOptions elements
		//checkOptionD.setBounds(new Rectangle(6, 15, 268, 21));
		checkOptionD.setText("set tolerance used to compare real numbers:");
		checkOptionD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkOptionD_actionPerformed(e);
			}
		});

		checkOptionP.setText("print extra info when the parsing occurs");
		//checkOptionP.setBounds(new Rectangle(6, 15, 268, 21));
		checkOptionP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkOptionP_actionPerformed(e);
			}
		});

		//checkOptionV.setBounds(new Rectangle(6, 15, 268, 21));
		checkOptionV.setText("evaluate debug mode (only for cells models)");
		checkOptionV.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkOptionV_actionPerformed(e);
			}
		});

		checkOptionB.setText("bypass the preprocessor (macros are ignored)");
		checkOptionB.setSelected(true);
		//checkOptionB.setBounds(new Rectangle(6, 15, 268, 21));
		//checkOptionF.setBounds(new Rectangle(6, 15, 268, 21));
		checkOptionF.setText("flat debug mode (only for flat cells models)");
		
		checkOptionF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkOptionF_actionPerformed(e);
			}
		});

		checkOptionR.setText("debug cell rules mode (only for cells models)");
		//checkOptionR.setBounds(new Rectangle(6, 15, 268, 21));
		//checkOptionS.setBounds(new Rectangle(6, 147, 280, 21));
		checkOptionS.setText("show the virtual time when the simulation ends");

		checkOptionQ.setText("use quantum to compute cell values:");
		//checkOptionQ.setBounds(new Rectangle(6, 15, 268, 21));
		checkOptionQ.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkOptionQ_actionPerformed(e);
			}
		});

		checkOptionW.setText("sets the width and precision to show numbers");
		//checkOptionW.setBounds(new Rectangle(6, 191, 274, 21));
		checkOptionW.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkOptionW_actionPerformed(e);
			}
		});

		//jtextD.setBounds(new Rectangle(280, 15, 36, 21));
		jtextD.setHorizontalAlignment(SwingConstants.RIGHT);
		jtextD.setColumns(4);
		jtextD.setEnabled(false);
		jtextD.setBackground(SystemColor.control);

		//jtextP.setBounds(new Rectangle(280, 37, 100, 21));
		jtextP.setEnabled(false);
		jtextP.setBackground(SystemColor.control);

		jtextV.setBackground(SystemColor.control);
		jtextV.setEnabled(false);
		//jtextV.setBounds(new Rectangle(280, 59, 100, 21));
		jtextF.setBackground(SystemColor.control);
		jtextF.setEnabled(false);
		//jtextF.setBounds(new Rectangle(280, 103, 100, 21));
		jtextQ.setBackground(SystemColor.control);
		jtextQ.setEnabled(false);
		jtextQ.setColumns(7);
		jtextQ.setHorizontalAlignment(SwingConstants.RIGHT);
		//jtextQ.setBounds(new Rectangle(280, 169, 63, 21));
		jtextWint.setBackground(SystemColor.control);
		jtextWint.setEnabled(false);
		jtextWint.setColumns(3);
		jtextWint.setHorizontalAlignment(SwingConstants.RIGHT);
		//jtextWint.setBounds(new Rectangle(280, 191, 30, 21));
		jtextWdec.setBackground(SystemColor.control);
		jtextWdec.setEnabled(false);
		jtextWdec.setColumns(3);
		jtextWdec.setHorizontalAlignment(SwingConstants.RIGHT);
		//jtextWdec.setBounds(new Rectangle(313, 191, 30, 21));
		jbuttonP.setEnabled(false);
		jbuttonP.setFont(new java.awt.Font("Dialog", 1, 12));
		//jbuttonP.setBounds(new Rectangle(386, 37, 43, 21));
		//jbuttonP.setPreferredSize(new Dimension(43, 21));
		jbuttonP.setText("...");
		jbuttonP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonP_actionPerformed(e);
			}
		});

		jbuttonV.setEnabled(false);
		jbuttonV.setText("...");
		//jbuttonV.setPreferredSize(new Dimension(43, 21));
		//jbuttonV.setBounds(new Rectangle(386, 59, 43, 21));
		jbuttonV.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonV.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonV_actionPerformed(e);
			}
		});
		jbuttonF.setEnabled(false);
		jbuttonF.setText("...");
		//jbuttonF.setPreferredSize(new Dimension(43, 21));
		//jbuttonF.setBounds(new Rectangle(386, 103, 43, 21));
		jbuttonF.setFont(new java.awt.Font("Dialog", 1, 12));
		jbuttonF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbuttonF_actionPerformed(e);
			}
		});

		JPanel checks = new JPanel();
		checks.setLayout(new GridLayout(9, 1, 5, 5));
		checks.add(checkOptionD);
		checks.add(checkOptionP);
		checks.add(checkOptionV);
		checks.add(checkOptionB);
		checks.add(checkOptionF);
		checks.add(checkOptionR);
		checks.add(checkOptionS);
		checks.add(checkOptionQ);
		checks.add(checkOptionW);

		JPanel inputs = new JPanel();
		inputs.setLayout(new GridLayout(9, 1, 5, 5));

		inputs.add(jtextD);

		JPanel printExtra = new JPanel();
		printExtra.setLayout(new BorderLayout());
		printExtra.add(jtextP, BorderLayout.CENTER);
		printExtra.add(jbuttonP, BorderLayout.EAST);
		inputs.add(printExtra);

		JPanel evalu = new JPanel();
		evalu.setLayout(new BorderLayout());
		evalu.add(jtextV, BorderLayout.CENTER);
		evalu.add(jbuttonV, BorderLayout.EAST);
		inputs.add(evalu);

		inputs.add(new JLabel());

		JPanel flat = new JPanel();
		flat.setLayout(new BorderLayout());
		flat.add(jtextF, BorderLayout.CENTER);
		flat.add(jbuttonF, BorderLayout.EAST);
		inputs.add(flat);

		inputs.add(new JLabel());
		inputs.add(new JLabel());
		inputs.add(jtextQ);

		JPanel width = new JPanel();
		width.setLayout(new GridLayout(1, 2));
		width.add(jtextWint);
		width.add(jtextWdec);
		inputs.add(width);

		//jpanelOptions.add(jbuttonP, null);
		//jpanelOptions.add(jbuttonV, null);
		//jpanelOptions.add(jbuttonF, null);
		JPanel jpanelOptions = new JPanel();
		jpanelOptions.setBorder(BorderFactory.createEtchedBorder());
		jpanelOptions.setLayout(new GridLayout(1, 2, 5, 5));

		jpanelOptions.add(checks);
		jpanelOptions.add(inputs);

		contentPane.add(jpanelOptions);

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

		contentPane.add(jpanelSimuOutput);

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

		jpanelOk_Cancel.add(jbuttonSimulate);
		jpanelOk_Cancel.add(jbuttonStop);
		jpanelOk_Cancel.add(jbuttonReset);
		jpanelOk_Cancel.add(jbuttonClose);

		contentPane.add(jpanelOk_Cancel, null);

		this.setModal(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(contentPane, BorderLayout.CENTER);
	}

	//~ Inner Classes ------------------------------------------------------------------------------

	/*
	    JFileChooser getJFileChooserSimuRun() {
	        if(jFileChooserSimuRun == null){
	            jFileChooserSimuRun = new JFileChooser();
	            jFileChooserSimuRun.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        }
	        System.out.println(jFileChooserSimuRun.getFileSelectionMode());
	        return jFileChooserSimuRun;
	    }
	*/

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
			Runtime rt = Runtime.getRuntime();

			//executionLines contains the simulator file and all the parameters.
			//The max number of posible parameters is 15
			String[] executionLines = new String[15];
			int index = 0;
			executionLines[index] = jtextSimulator.getText();
			index++;
			if (!(jtextModelFile.getText().equals(""))) {
				executionLines[index] = "-m";
				executionLines[index] = executionLines[index].concat(jtextModelFile.getText());
				index++;
			}
			if (!(jtextEventsFile.getText().equals(""))) {
				executionLines[index] = "-e";
				executionLines[index] = executionLines[index].concat(jtextEventsFile.getText());
				index++;
			}
			if (!(jtextOutputFile.getText().equals(""))) {
				executionLines[index] = "-o";
				executionLines[index] = executionLines[index].concat(jtextOutputFile.getText());
				index++;
			}
			if (!(jtextLogFile.getText().equals(""))) {
				executionLines[index] = "-l";
				executionLines[index] = executionLines[index].concat(jtextLogFile.getText());
				index++;
			}
			if (!(infinityCheckbox.isSelected())) {
				executionLines[index] = "-t";
				if (stopTimeH.getText().length() <= 0) {
					executionLines[index] = executionLines[index].concat("00");
				}
				else {
					executionLines[index] = executionLines[index].concat(stopTimeH.getText());
				}
				executionLines[index] = executionLines[index].concat(":");
				if (stopTimeM.getText().length() <= 0) {
					executionLines[index] = executionLines[index].concat("00");
				}
				else {
					executionLines[index] = executionLines[index].concat(stopTimeM.getText());
				}
				executionLines[index] = executionLines[index].concat(":");
				if (stopTimeS.getText().length() <= 0) {
					executionLines[index] = executionLines[index].concat("00");
				}
				else {
					executionLines[index] = executionLines[index].concat(stopTimeS.getText());
				}
				executionLines[index] = executionLines[index].concat(":");
				if (stopTimeMs.getText().length() <= 0) {
					executionLines[index] = executionLines[index].concat("000");
				}
				else {
					executionLines[index] = executionLines[index].concat(stopTimeMs.getText());
				}
				index++;
			}
			if (checkOptionD.isSelected()) {
				executionLines[index] = "-d";
				executionLines[index] = executionLines[index].concat(jtextD.getText());
				index++;
			}
			if (checkOptionP.isSelected()) {
				executionLines[index] = "-p";
				if (!(jtextP.getText().equals(""))) {
					executionLines[index] = executionLines[index].concat(jtextP.getText());
				}
				index++;
			}
			if (checkOptionV.isSelected()) {
				executionLines[index] = "-v";
				if (!(jtextV.getText().equals(""))) {
					executionLines[index] = executionLines[index].concat(jtextV.getText());
				}
				index++;
			}
			if (checkOptionB.isSelected()) {
				executionLines[index] = "-b";
				index++;
			}
			if (checkOptionF.isSelected()) {
				executionLines[index] = "-f";
				if (!(jtextF.getText().equals(""))) {
					executionLines[index] = executionLines[index].concat(jtextF.getText());
				}
				index++;
			}
			if (checkOptionR.isSelected()) {
				executionLines[index] = "-r";
				index++;
			}
			if (checkOptionS.isSelected()) {
				executionLines[index] = "-s";
				index++;
			}
			if (checkOptionQ.isSelected()) {
				executionLines[index] = "-q";
				executionLines[index] = executionLines[index].concat(jtextQ.getText());
				index++;
			}
			if (checkOptionW.isSelected()) {
				executionLines[index] = "-w";
				executionLines[index] = executionLines[index].concat(jtextWint.getText());
				executionLines[index] = executionLines[index].concat("-");
				executionLines[index] = executionLines[index].concat(jtextWdec.getText());
				index++;
			}
			

			//cmdLine is the same as ExecutionLines, but without the null strings
			String[] cmdLine = new String[index];
			for (int j = 0; j < index; j++) {
				cmdLine[j] = executionLines[j];
				System.err.print(cmdLine[j] + " ");
			}
			System.err.println();


			Process p;
			Thread t1;
			Thread t2;
			

			try {
				p = Runtime.getRuntime().exec(cmdLine, null, new File(jtextModelDir.getText()));
				final BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(p.getInputStream()));
				final BufferedReader errorBuffer = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				
				jbuttonSimulate.setEnabled(false);
				//Runtime.getRuntime().exec("command \c cd c:\gui");

				//BufferedWriter outputBuffer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
				try {
					// Tries to open the model file indicated.
					// If it can open it, it uses the first form of the redirection
					// If it can't, it uses the second form
					String modelFile = jtextModelFile.getText();
					if (modelFile.equals("")) {
						modelFile = "model.ma";
					}

					File file = new File(jtextModelDir.getText()+"/"+modelFile);
					FileReader in = new FileReader(file);

					//This code works if the simulator doesn't throw an error

					t1 = new Thread(new Runnable() {
						public void run() {
							
							try {
								// 
								String line;
								while ((line = inputBuffer.readLine()) != null && !stopRunning) {
									textSimuOutput.append(line.concat("\r\n"));
									//System.err.println("* ");
									int max = jscrollSimuOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollSimuOutput.getVerticalScrollBar().getModel().setValue(max);			
									//System.out.print("%");
								}
								inputBuffer.close();
							}
							catch (IOException e) {
								System.err.println(e.toString());
							}
							
						}
					});
					t1.start();
					
					t2 = new Thread(new Runnable() {
						public void run() {
							try {
								String line;
								while ((line = errorBuffer.readLine()) != null && !stopRunning) {
									textSimuOutput.append(line.concat("\r\n"));
									int max = jscrollSimuOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollSimuOutput.getVerticalScrollBar().getModel().setValue(max);
									//System.out.print("#");			
								}
								errorBuffer.close();
							}
							catch (IOException e) {
								System.err.println(e.toString());
							}
							
						}
					});
					t2.start();

					while((t1.isAlive() || t2.isAlive())  && !stopRunning ){
						Thread.sleep(5000);
						//System.out.print(".");
					}
				}
				catch (IOException ioe) {
					System.err.println(ioe.toString());
					//This code works if the simulator throws an error
					t1 = new Thread(new Runnable() {
						public void run() {
							try {
								String line;
								while ((line = inputBuffer.readLine()) != null  && !stopRunning) {
									textSimuOutput.append(line.concat("\r\n"));
									int max = jscrollSimuOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollSimuOutput.getVerticalScrollBar().getModel().setValue(max);
									//System.out.print("%");
								}
								inputBuffer.close();
							}
							catch (IOException e) {
								System.err.println(e.toString());
							}
							
						}
					});
					t1.start();
					
					t2 = new Thread(new Runnable() {
						public void run() {
							try {
								String line;
								while ((line = errorBuffer.readLine()) != null  && !stopRunning) {
									textSimuOutput.append(line.concat("\r\n"));
									int max = jscrollSimuOutput.getVerticalScrollBar().getModel().getMaximum();
									jscrollSimuOutput.getVerticalScrollBar().getModel().setValue(max);
									//System.out.print("#");			
								}
								errorBuffer.close();
							}
							catch (IOException e) {
								// 
								System.err.println(e.toString());
							}
							
						}
					});
					t2.start();
					while(t1.isAlive() || t2.isAlive() && !stopRunning){
						System.err.print(".");
						Thread.sleep(5000);
					}
					
				}finally{
					inputBuffer.close();
					errorBuffer.close();
					System.err.println("closing buffers");
				}
				
			}
			catch (Exception err) {
				err.printStackTrace();
				System.err.println("Error executing the simulator");
			}
			finally{
				t1 = null;
				t2 = null;
				System.err.println("closing threads");
			}
			
			jbuttonSimulate.setEnabled(true);
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
	
	private class TimeMouseListener implements MouseListener{

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent e) {
			toogleTimeInfinity(false, e.getComponent());						
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		public void mouseEntered(MouseEvent e) {
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		public void mouseExited(MouseEvent e) {
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(MouseEvent e) {
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(MouseEvent e) {
		}
	
	}

  

}
