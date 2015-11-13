/* Class Name: main_view
 * Author: Nan Yang
 * Time: 01/10/2015
 * Class Description: This class implements the User Interface main page.
 */

import gui.CellAnimateIf;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;




public class main_view extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private Text textMa;
	private JButton fileButtonVal = new JButton("Browse");
	private JButton defineButtonVal = new JButton("View & Define");
	private JButton createButtonVal = new JButton("Create new model");
	private JButton simuButton = new JButton("Run Simulation");
	private JButton visualizeButton = new JButton("Visualize");
	private JButton compareButton = new JButton("Compare Models");
	private static JTextField valTextField = new JTextField(25);

	String[] method = { "Standard","Directional","Follow-The-Herd", "Panic",
			"FTH with Random", "FTH Random Leader Emerges", "FTH Gets Direction" };
	String[] models = { "Standard","Directional","FTH", "Panic",
			"FTH_Ran", "FTH_Ran_LE", "FTH_GD" };

	// this describe second plane 0: direction 1: distance
	int [] plane={1,0,1,0,1,1,0};
	
	// this describe first plane layout 0:-1~9  1: -1~108
	int [] layout={0,0,1,1,1,1,1};
	
	//private int [][] exits=null;
	//private int peopleTotal=0;
	
	String simulatorPath="";
	String buildingPath="";
	String valPath="";
	String modelPath="";
	String executionPath="";
	
	
	final JComboBox cb = new JComboBox(method);

	private JCheckBox infinityCheckBox = new JCheckBox("");
	private JTextField timeField1 = new JTextField(2);
	private JTextField timeField2 = new JTextField(2);
	private JTextField timeField3 = new JTextField(2);
	private JTextField timeField4 = new JTextField(3);
	Font myFont = new Font("Times New Roman", Font.BOLD, 14);
	
	File myValfile;
	File logFile;
	//protected static mapInfo myMapInfo =new mapInfo();



	public main_view() {
		
		getProjectPathes();
		addFeatures();

	}

	private void addFeatures() {

		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		pane.getInsets().set(10, 10, 10, 10);
		// natural height, maximum width
		// c.fill = GridBagConstraints.HORIZONTAL;

		//line 0 Evacuate Environment label
		JLabel fileLabel = new JLabel("Evacuate Environment(.val)");
		fileLabel.setFont(myFont);
		// c.weightx = 20;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 5;
		c.ipady = 5;
		c.ipadx = 10;
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		pane.add(fileLabel, c);

		//line 1 val file text field and browse/ define&view button 	
		valTextField.addActionListener(this);
		valTextField.setFont(myFont);
		//valTextField.setToolTipText("herte it is");
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 1;
		pane.add(valTextField, c);

		fileButtonVal.addActionListener(this);
		fileButtonVal.setFont(myFont);
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 1;
		pane.add(fileButtonVal, c);
		
		defineButtonVal.addActionListener(this);
		defineButtonVal.setFont(myFont);
		c.gridwidth = 1;
		c.gridx = 4;
		c.gridy = 1;
		pane.add(defineButtonVal, c);
		
		
		//line 2 create button
		createButtonVal.addActionListener(this);
		createButtonVal.setFont(myFont);
		c.gridwidth = 1;
		c.gridx = 4;
		c.gridy = 2;
		pane.add(createButtonVal, c);
		


		//line 3 strategy label
		JLabel strategyLabel = new JLabel("Egress strategy");
		strategyLabel.setFont(myFont);
		c.gridwidth = 5;		
		c.ipady = 5;
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		pane.add(strategyLabel, c);


		//line 4 JComboBox for strategy 
		
		cb.setVisible(true);
		c.gridwidth = 1;
		c.ipady = 5;
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		pane.add(cb, c);
		
		
		//line 5 simulation stop time label 				
		JLabel timeLabel = new JLabel("Please enter simulation  stop time. (hh,mm,ss,ms)");
		timeLabel.setFont(myFont);
		c.gridwidth = 5;
		c.ipady = 5;
		c.gridx = 1;
		c.gridy = 5;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		pane.add(timeLabel, c);
		
		//line 6 simulation stop time label 				
		JLabel timeNoteLabel = new JLabel("(NOTE: uncheck time option means 'infinity' as stop time)");
		timeLabel.setFont(myFont);
		c.gridwidth = 5;
		c.ipady = 5;
		c.gridx = 1;
		c.gridy = 6;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		pane.add(timeNoteLabel, c);
		
/*		timeField1.addActionListener(this);
		timeField1.setFont(myFont);
		valTextField.setToolTipText("herte it is");
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 5;
		pane.add(timeField1, c);
		
		timeField2.addActionListener(this);
		timeField2.setFont(myFont);
		valTextField.setToolTipText("herte it is");
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 5;
		pane.add(timeField2, c);
		
		timeField3.addActionListener(this);
		timeField3.setFont(myFont);
		valTextField.setToolTipText("herte it is");
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 5;
		pane.add(timeField3, c);*/
		
		//line 7 simulation stop time text fields & infinity check box
		
		
		
		
		
		JPanel timePanel=new JPanel();
		
		
		infinityCheckBox.setMnemonic(KeyEvent.VK_0);
		infinityCheckBox.setSelected(false);
		infinityCheckBox.addActionListener(this);
		
		
		
		timePanel.add(infinityCheckBox);
		
		timeField1.addActionListener(this);
		timeField1.setFont(myFont);
		timeField1.setColumns(2);
		timeField1.setText("00");
		timePanel.add(timeField1);
		
		JLabel label1=new JLabel(":");
		timePanel.add(label1);
		
		
		timeField2.addActionListener(this);
		timeField2.setFont(myFont);
		timeField2.setColumns(2);
		timeField2.setText("00");
		timePanel.add(timeField2);
		
		JLabel label2=new JLabel(":");
		timePanel.add(label2);
		
		timeField3.addActionListener(this);
		timeField3.setFont(myFont);
		timeField3.setColumns(2);
		timeField3.setText("00");
		timePanel.add(timeField3);
		
		JLabel label3=new JLabel(":");
		timePanel.add(label3);
		
		timeField4.addActionListener(this);
		timeField4.setFont(myFont);
		timeField4.setColumns(3);
		timeField4.setText("000");
		timePanel.add(timeField4);
		
		c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 7;
		pane.add(timePanel, c);
		

		simuButton.addActionListener(this);
		simuButton.setFont(myFont);
		// c.weightx = 3;
		c.gridwidth = 1;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 8;
		pane.add(simuButton, c);

		visualizeButton.addActionListener(this);
		visualizeButton.setFont(myFont);
		// c.weightx = 3;
		c.gridwidth = 1;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 8;
		pane.add(visualizeButton, c);
		

		compareButton.addActionListener(this);
		compareButton.setFont(myFont);
		// c.weightx = 3;
		c.gridwidth = 1;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 8;
		pane.add(compareButton, c);

	}

	
	
	private boolean checkTimeInput(){
		int t1,t2,t3,t4;
		
		try {
			t1 = Integer.parseInt(timeField1.getText());
			t2 = Integer.parseInt(timeField2.getText());
			t3 = Integer.parseInt(timeField3.getText());
			t4 = Integer.parseInt(timeField4.getText());
		} catch (NumberFormatException nfe) {
			JOptionPane
					.showMessageDialog(this,
							"Error, please verfy your time input. It can only be positive numbers.");
			return false;
		}
		if (t1 >= 0 && t2>=0 && t3>=0 && t4>=0) {
			return true;
		} else {
			JOptionPane
					.showMessageDialog(this,
							"Error, please verfy your time input. It can only be positive numbers.");
			return false;
		}
	}
	private void getProjectPathes(){
		//URL x=getClass().getProtectionDomain().getCodeSource().getLocation();
		//File myfile=new File(x.getPath(),"findme.log");
		//System.out.println("myfile path is "+myfile.getAbsolutePath());
		//System.out.println("Old Building path is "+x.getPath());
		ClassLoader cl=Thread.currentThread().getContextClassLoader();
		java.net.URL path= cl.getResource("main_view.class");
		
		
		URI uri = null;
		try {
			uri = path.toURI();
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
		File file = new File(uri);
		
		
		
		if(file.exists()){
			System.out.println("file exists in "+file.getAbsolutePath());
		}
		
		
		buildingPath=file.getPath().replaceFirst("main_view.class", "");
		//buildingPath=x.getPath().replaceFirst("/", "");
		//simulatorPath=buildingPath+"simuOrig.exe";
		simulatorPath=buildingPath+"Evacart workspace\\simuOrig.exe";
		executionPath=buildingPath+"Evacart workspace\\executions";
		valPath=buildingPath+"Evacart workspace\\Vals";
		modelPath=buildingPath+"Evacart workspace\\Evacuation Models";
		
		//System.out.println("Building path is "+buildingPath);
		//System.out.println("Execution path is "+executionPath);
		//System.out.println("valPath path is "+valPath);
		//System.out.println("modelPath path is "+modelPath);
		
		
	}

	private boolean checkValFile() {
		
		myValfile = new File(valTextField.getText());
		if (myValfile.exists()) {
			// System.out.println("File exist");
			return true;
		} else {
			System.out.println("File do not exist");
			JOptionPane
					.showMessageDialog(
							this,
							"Error, please verfy your Val file input. "
									+ "It can only be an absolute path,\n such as: C:\\Users\\YANG_1\\Documents\\unitTestingCB.val");
			return false;
		}
	}
	
	private void copyValFile()throws IOException{
		
		File to=new File(executionPath,myValfile.getName());
		//FileUtils.
		 if ( !to.exists() ) { to.createNewFile(); }
		 else
		 {
			 to.delete();
			 to.createNewFile();
		 }

		  
		        FileChannel in = new FileInputStream( myValfile ).getChannel();
		        FileChannel out = new FileOutputStream( to ).getChannel();

		        out.transferFrom( in, 0, in.size() );
	}
	
	private void selectValFile(){
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(getValPath());
		
		fc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".VAL file", "val");
		fc.setFileFilter(filter);
		
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			valTextField.setText(file.getAbsolutePath());
			// This is where a real application would open the file.
			System.out.println("Opening: " + file.getName());

		} else {
			System.out.println("Open command cancelled by user.");
		}
	}
	
	private static void createEnvironmentFrame(int index){
		/* get the size of screen and set the size of frame */
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = new Dimension(550,
				250);
	
		
		final environment frame2 = new environment(index);  // Create a frame
		
		frame2.setTitle("Evacuation Environment ")		;
		frame2.setSize(size); // Set the frame size
		frame2.setLocationRelativeTo(null); // New since JDK 1.4
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setVisible(true); // Display the frame
		//frame.setResizable(false);// set the frame not resizable

		 frame2.addWindowListener(new WindowAdapter() {
		        @Override
		        public void windowClosing(WindowEvent event) {
		        	if(frame2.getValFile()!=null){
		        	getValTextField().setText(frame2.getValFile().getAbsolutePath());
			    	System.out.println("getValTextField executed 2.");
		        	}
		        	
		        }
		    });
		
	}
	
	private static void createEnvironmentFrame(File myfile, int index){
		
		
		/* get the size of screen and set the size of frame */
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = new Dimension(670,
				355);
	
		
		final environment frame2 = new environment(myfile,index);  // Create a frame
		
		frame2.setTitle("Evacuation Environment ")		;
		frame2.setSize(size); // Set the frame size
		frame2.setLocationRelativeTo(null); // New since JDK 1.4
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setVisible(true); // Display the frame
		//frame.setResizable(false);// set the frame not resizable

		 frame2.addWindowListener(new WindowAdapter() {
		        @Override
		        public void windowClosing(WindowEvent event) {
		        	getValTextField().setText(frame2.getValFile().getAbsolutePath());
		        	//setMyMapInfo(frame2.getMyMapInfo());
			    	System.out.println("getValTextField executed 2.");
		        }
		    });
		
	}
	
	private void createCompareFrame(String executionpath){
		/* get the size of screen and set the size of frame */
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = new Dimension(1250,
				520);
	
		
		final compare_view frame3 = new compare_view(executionpath);  // Create a frame
		
		frame3.setTitle("Compare Models")		;
		frame3.setSize(size); // Set the frame size
		frame3.setLocationRelativeTo(null); // New since JDK 1.4
		frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame3.setVisible(true); // Display the frame
		//frame.setResizable(false);// set the frame not resizable
	}
	
	private File getValPath(){
		ClassLoader cl=Thread.currentThread().getContextClassLoader();
		java.net.URL path= cl.getResource("main_view.class");
		URI uri = null;
		try {
			uri = path.toURI();
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
		File file = new File(uri);
		if(file.exists()){
			System.out.println("file exists in "+file.getAbsolutePath());
		}
		String valPath =file.getPath().replaceFirst("main_view.class", "") +"Evacart workspace\\executions";
		File myfile=new File(valPath);
		
		return myfile;
	}
	protected static JTextField getValTextField() {
		return valTextField;
	}
	
	

	
	private void extractInfo(File logfile){
		if(logfile.exists() && logfile.length()!=0){
			//long  x=logfile.getTotalSpace();
			//System.out.println("Total space="+logfile.length());
			//logfile.length()
			String l0="Simulation result:\n";
			String l1="Delay time: 1000ms\n";
			String l2="Execution time: ";
			//String l3="People evaculated: 0 \n";
			//String l4="People not evaculated: 0 \n ";
			//String l5="Number of exit: 3";
			logReader myreader=new logReader(logfile);
			//System.out.println(myreader.getData());
			l2+=myreader.getData();//+"(hh:mm:ss:ms)\n";
			JOptionPane.showMessageDialog(this, l0+l1+l2);
		}else{
			JOptionPane.showMessageDialog(this, "Error, log file do not exist, or have no content.");
		}
		
		
	}
	//for testing
	private void checkModelSelection(){
		
		System.out.println("Model Selection:");
		int index=cb.getSelectedIndex();
		System.out.println("index="+index);
		System.out.println("method="+method[index]);
		System.out.println("model name="+models[index]);
		System.out.println("method="+method[index]);
		if(plane[index]==0){
			System.out.println("direction model");
		}else{
			System.out.println("distance model");
		}
		if(layout[index]==0){
			System.out.println("value: -1~9");
		}else{
			System.out.println("value: -1~108");
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.fileButtonVal) {
			selectValFile();
		}
		else if(e.getSource() == this.defineButtonVal){
			if(checkValFile()){
				checkModelSelection();
				createEnvironmentFrame(myValfile,cb.getSelectedIndex());
			}

			
		}
		else if(e.getSource() == compareButton){
			//TODO
			createCompareFrame(executionPath);
			
		}
		
		else if(e.getSource() == createButtonVal){
			checkModelSelection();
			createEnvironmentFrame(cb.getSelectedIndex());
		}
		
		

		else if (e.getSource() == this.simuButton) {
			if (checkValFile()) {
				if (checkTimeInput()) {
					String time= timeField1.getText()+":"+timeField2.getText()+":"+timeField3.getText()+":"+timeField4.getText(); 
					if(infinityCheckBox.isSelected()){
						time="infinity";
					}
					checkModelSelection();
					
				/*	if(this.cb.getSelectedIndex()==0){
						System.out.println("Index: "+cb.getSelectedIndex());
					}
					else if(this.cb.getSelectedIndex()==1){
						System.out.println("Index: "+cb.getSelectedIndex());
					}*/
					
					System.out.println(cb.getSelectedItem().toString());
					System.out.println("Simulation starts: ");
					System.out.println("Simulation time: "+time);
					myValfile = new File(valTextField.getText());
					
					simulator mysimulator = null;
					
					try {
						mysimulator = new simulator(time,models[cb.getSelectedIndex()],myValfile.getName());
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
					
					System.out.println("val FIleName: "+myValfile.getName());
					logFile=mysimulator.getLogFile();
					//logFile=new File("C:\\Users\\yangn1\\Desktop\\Evacart\\bin\\Evacart workspace\\Evacuation Models\\Airport_Egress","AirportLOG.log");
					extractInfo(logFile);
				}
			}
		}
		else if (e.getSource() == this.visualizeButton) {
			System.out.println("isualization starts: ");
			//simulator mysimulator=new simulator("01:00:00:001");
			CellAnimateIf cellAnimate = (CellAnimateIf) new gui.animate.cellanimate.CellAnimate();
			cellAnimate.setSize(new Dimension(600,800));
			cellAnimate.setLocationRelativeTo(null);
			cellAnimate.setVisible(true);
		}
		else if(e.getSource() == this.infinityCheckBox ){
			
			if(infinityCheckBox.isSelected()){
				timeField1.setEditable(false);
				timeField2.setEditable(false);
				timeField3.setEditable(false);
				timeField4.setEditable(false);
				System.out.println("infinityCheckBox isSelected. ");
			}else{
				timeField1.setEditable(true);
				timeField2.setEditable(true);
				timeField3.setEditable(true);
				timeField4.setEditable(true);
			}
			
		}
		

	}
	
	/*protected static mapInfo getMyMapInfo() {
		return myMapInfo;
	}

	protected static void setMyMapInfo(mapInfo myMapInfo) {
		main_view.myMapInfo = myMapInfo;
	}
	*/


}