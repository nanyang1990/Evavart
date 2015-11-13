
/* Class Name: environment.java
 * Author: Nan Yang
 * Time: 02/02/2015
 * Class Description: This class implements the User Interface of the environment page.
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class environment extends JFrame implements ActionListener {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	Container pane = this.getContentPane();

	JPanel leftPane = new JPanel();
	JPanel centerPane = new JPanel();
	JPanel topPane = new JPanel();
	JPanel downPane = new JPanel();
	
	Font myFont = new Font("Times New Roman", Font.BOLD, 14);

	private JRadioButton wallButton;
	private JRadioButton emptyButton;
	private JRadioButton peopleButton;
	
	private JRadioButton peopleButton2;
	private JRadioButton peopleButton3;
	private JRadioButton exitButton;
	private JRadioButton exitButton2;
	private JRadioButton blockButton;
	
	private JButton saveValButton = new JButton("Save");
	private JButton useValButton = new JButton("Use");
	private JTextField lengthField = new JTextField(3);
	private JTextField widthField = new JTextField(3);
	private JButton updateButton = new JButton("Update Cell");
	JButton helpButton=new JButton("Help Menu");
	
	JFileChooser fc=new JFileChooser();
	
	JTable table;
	DefaultTableModel model;
	
	FileWriter writer = null;

	private int tableLength = 10;
	private int cellValue = -1;
	private boolean editable=true;
	private int tableWidith = 10;
	private File valFile;
	
	
	private int  [][] map;
	private int [][] directionmap;
	private directionAnalysiser mapAnalysiser;
	
	private int [][] exits=null;
	private int peopleTotal=0;

	// this describe second plane 0: direction 1: distance
	private int [] plane={1,0,1,0,1,1,0};
	
	// this describe first plane layout 0:-1~9  1: -1~108
	private int [] layout={0,0,1,1,1,1,1};
	
	private int index;
	//protected mapInfo myMapInfo=new mapInfo();




	public environment(File inputFile,int index) {
		//myMapInfo.setUserModel(false);
		valFile = inputFile;
		this.index=index;

		if(layout[index]==0){
			createPanels(false);
		}
		else
		{
			createSecondPanel(false);
		}
		
		loadTable();
		addFeatures();

	}

	public environment(int index) {
		//myMapInfo.setUserModel(true);
		this.index=index;
		if(layout[index]==0){
			createPanels(true);
		}
		else
		{
			createSecondPanel(true);
		}
		createNewTable();
		addFeatures();

	}


	
	
	private void createPanels(boolean flag) {
		pane.setLayout(new BorderLayout());

		// create left panel
		leftPane.setLayout(new GridBagLayout());
		GridBagConstraints e = new GridBagConstraints();
		leftPane.getInsets().set(10, 10, 10, 10);

		/*
		 * //line 1 JLabel mapSizeLabel=new
		 * JLabel("Please enter map size (Length x Widith).");
		 * mapSizeLabel.setFont(myFont); e.gridwidth = 5; e.ipady = 5; e.ipadx =
		 * 10; e.gridx = 1; e.gridy = 0; e.anchor =
		 * GridBagConstraints.FIRST_LINE_START; leftPane.add(mapSizeLabel, e);
		 * 
		 * //line 2 JLabel mapSizeNoteLabel1=new
		 * JLabel("Note:length and widith shall be positive,");
		 * mapSizeNoteLabel1.setFont(myFont); e.gridwidth = 5; e.gridx = 1;
		 * e.gridy = 1; leftPane.add(mapSizeNoteLabel1, e);
		 * 
		 * //line 3 JLabel mapSizeNoteLabel2=new
		 * JLabel("     and the value shall be less than 10.");
		 * mapSizeNoteLabel2.setFont(myFont); e.gridwidth = 5; e.gridx = 1;
		 * e.gridy = 2; leftPane.add(mapSizeNoteLabel2, e);
		 */

		// line 2
		JLabel mapValueNoteLabel = new JLabel(
				"Note:objects in the map are reprecented");
		mapValueNoteLabel.setFont(myFont);
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 0;
		leftPane.add(mapValueNoteLabel, e);

		// create map set setter when flag is true
		if(flag){
			JLabel mapSizeLabel = new JLabel(
					"Please enter map size length x width (up to 40)");
			mapSizeLabel.setFont(myFont);
			e.gridwidth = 5;
			e.gridx = 1;
			e.gridy = 1;
			leftPane.add(mapSizeLabel, e);
			
			JLabel lengthLabel = new JLabel(
					"Length: ");
			lengthLabel.setFont(myFont);
			e.gridwidth = 1;
			e.gridx = 1;
			e.gridy = 2;
			leftPane.add(lengthLabel, e);
			
			e.gridwidth = 1;
			e.gridx = 2;
			e.gridy = 2;
			leftPane.add(lengthField, e);
			
			JLabel widthLabel = new JLabel(
					"Width: ");
			widthLabel.setFont(myFont);
			e.gridwidth = 1;
			e.gridx = 3;
			e.gridy = 2;
			leftPane.add(widthLabel, e);
			
			e.gridwidth = 1;
			e.gridx = 4;
			e.gridy = 2;
			leftPane.add(widthField, e);
			
			e.gridwidth = 1;
			e.gridx = 5;
			e.gridy = 2;
			leftPane.add(updateButton, e);
			updateButton.addActionListener(this);
			
		}
		
		
		// line 4,5,6,7
		// Create the radio buttons.
		wallButton = new JRadioButton("Walls : -1");
		wallButton.setMnemonic(KeyEvent.VK_B);
		wallButton.setFont(myFont);
		// birdButton.setActionCommand(birdString);
		wallButton.setSelected(true);

		emptyButton = new JRadioButton("Empty : 0");
		emptyButton.setMnemonic(KeyEvent.VK_C);
		// catButton.setActionCommand(catString);
		emptyButton.setFont(myFont);

		peopleButton = new JRadioButton("People : 2");
		peopleButton.setMnemonic(KeyEvent.VK_D);
		// dogButton.setActionCommand(dogString);
		peopleButton.setFont(myFont);

		exitButton = new JRadioButton("Exit : 9");
		exitButton.setMnemonic(KeyEvent.VK_R);
		// rabbitButton.setActionCommand(rabbitString);
		exitButton.setFont(myFont);

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(wallButton);
		group.add(emptyButton);
		group.add(peopleButton);
		group.add(exitButton);

		// Register a listener for the radio buttons.
		wallButton.addActionListener(this);
		emptyButton.addActionListener(this);
		peopleButton.addActionListener(this);
		exitButton.addActionListener(this);

		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 4;
		leftPane.add(wallButton, e);
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 5;
		leftPane.add(emptyButton, e);
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 6;
		leftPane.add(peopleButton, e);
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 7;
		leftPane.add(exitButton, e);

		// create center panel
		centerPane.setLayout(new BorderLayout());

		JLabel tableNoteLabel = new JLabel("Please enter values");
		centerPane.add(tableNoteLabel, BorderLayout.NORTH);


		// create down panel
		downPane.setLayout(new GridBagLayout());
		GridBagConstraints d = new GridBagConstraints();
		downPane.getInsets().set(10, 10, 10, 10);

		e.gridwidth = 2;
		e.ipady = 1;
		e.ipadx = 1;

		// d.gridwidth =2;
		d.gridx = 1;
		d.gridy = 0;
		d.anchor = GridBagConstraints.FIRST_LINE_START;
		downPane.add(this.saveValButton, d);
		saveValButton.addActionListener(this);

		// d.gridwidth =2;
		d.gridx = 4;
		d.gridy = 0;

		downPane.add(this.useValButton, d);
		useValButton.addActionListener(this);
		
		//set use button to false for now. 
		useValButton.setVisible(false);

	}
	
	private void createSecondPanel(boolean flag){

		pane.setLayout(new BorderLayout());



		// create left panel
		leftPane.setLayout(new GridBagLayout());
		GridBagConstraints e = new GridBagConstraints();
		leftPane.getInsets().set(10, 10, 10, 10);

		/*
		 * //line 1 JLabel mapSizeLabel=new
		 * JLabel("Please enter map size (Length x Widith).");
		 * mapSizeLabel.setFont(myFont); e.gridwidth = 5; e.ipady = 5; e.ipadx =
		 * 10; e.gridx = 1; e.gridy = 0; e.anchor =
		 * GridBagConstraints.FIRST_LINE_START; leftPane.add(mapSizeLabel, e);
		 * 
		 * //line 2 JLabel mapSizeNoteLabel1=new
		 * JLabel("Note:length and widith shall be positive,");
		 * mapSizeNoteLabel1.setFont(myFont); e.gridwidth = 5; e.gridx = 1;
		 * e.gridy = 1; leftPane.add(mapSizeNoteLabel1, e);
		 * 
		 * //line 3 JLabel mapSizeNoteLabel2=new
		 * JLabel("     and the value shall be less than 10.");
		 * mapSizeNoteLabel2.setFont(myFont); e.gridwidth = 5; e.gridx = 1;
		 * e.gridy = 2; leftPane.add(mapSizeNoteLabel2, e);
		 */
		// create map set setter when flag is true
				if(flag){
					JLabel mapSizeLabel = new JLabel(
							"Please enter map size length x width (up to 40)");
					mapSizeLabel.setFont(myFont);
					e.gridwidth = 5;
					e.gridx = 1;
					e.gridy = 0;
					leftPane.add(mapSizeLabel, e);
					
					JLabel lengthLabel = new JLabel(
							"Length: ");
					lengthLabel.setFont(myFont);
					e.gridwidth = 1;
					e.gridx = 1;
					e.gridy = 1;
					leftPane.add(lengthLabel, e);
					
					e.gridwidth = 1;
					e.gridx = 2;
					e.gridy = 1;
					leftPane.add(lengthField, e);
					
					JLabel widthLabel = new JLabel(
							"Width: ");
					widthLabel.setFont(myFont);
					e.gridwidth = 1;
					e.gridx = 3;
					e.gridy = 1;
					leftPane.add(widthLabel, e);
					
					e.gridwidth = 1;
					e.gridx = 4;
					e.gridy = 1;
					leftPane.add(widthField, e);
					
					e.gridwidth = 1;
					e.gridx = 5;
					e.gridy = 1;
					leftPane.add(updateButton, e);
					updateButton.addActionListener(this);
					
				}
		

		// line 2
		JLabel mapValueNoteLabel = new JLabel(
				"Note:objects in the map are reprecented");
		mapValueNoteLabel.setFont(myFont);
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 3;
		leftPane.add(mapValueNoteLabel, e);

		// line 3,4,5,6
		// Create the radio buttons.
		wallButton = new JRadioButton("Walls : -1");
		wallButton.setMnemonic(KeyEvent.VK_B);
		wallButton.setFont(myFont);
		// birdButton.setActionCommand(birdString);
		wallButton.setSelected(true);

		emptyButton = new JRadioButton("Empty : 0");
		emptyButton.setMnemonic(KeyEvent.VK_C);
		// catButton.setActionCommand(catString);
		emptyButton.setFont(myFont);
		
		
		blockButton = new JRadioButton("Cell Blocked : 9");
		blockButton.setMnemonic(KeyEvent.VK_D);
		// dogButton.setActionCommand(dogString);
		blockButton.setFont(myFont);

		peopleButton2 = new JRadioButton("Familiar People with direction : 1~8");
		peopleButton2.setMnemonic(KeyEvent.VK_E);
		// dogButton.setActionCommand(dogString);
		peopleButton2.setFont(myFont);
		
		peopleButton3 = new JRadioButton("Unfamiliar People with direction : 101~108");
		peopleButton3.setMnemonic(KeyEvent.VK_F);
		// dogButton.setActionCommand(dogString);
		peopleButton3.setFont(myFont);
		
		
		exitButton2 = new JRadioButton("Exit : 900");
		exitButton2.setMnemonic(KeyEvent.VK_R);
		// rabbitButton.setActionCommand(rabbitString);
		exitButton2.setFont(myFont);

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(wallButton);
		group.add(emptyButton);
		group.add(blockButton);
		group.add(peopleButton2);
		group.add(peopleButton3);
		group.add(exitButton2);
		group.add(helpButton);

		// Register a listener for the radio buttons.
		wallButton.addActionListener(this);
		emptyButton.addActionListener(this);
		blockButton.addActionListener(this);
		exitButton2.addActionListener(this);
		peopleButton3.addActionListener(this);
		peopleButton2.addActionListener(this);
		helpButton.addActionListener(this);

		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 4;
		leftPane.add(wallButton, e);
		
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 5;
		leftPane.add(emptyButton, e);
		
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 6;
		leftPane.add(blockButton, e);
		
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 7;
		leftPane.add(peopleButton2, e);

		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 8;
		leftPane.add(peopleButton3, e);
		
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 9;
		leftPane.add(exitButton2, e);
		
		e.gridwidth = 5;
		e.gridx = 1;
		e.gridy = 10;
		leftPane.add(helpButton, e);
		
		
		// create center panel
		centerPane.setLayout(new BorderLayout());

		JLabel tableNoteLabel = new JLabel("Please enter values");
		centerPane.add(tableNoteLabel, BorderLayout.NORTH);


		// create down panel
		downPane.setLayout(new GridBagLayout());
		GridBagConstraints d = new GridBagConstraints();
		downPane.getInsets().set(10, 10, 10, 10);

		e.gridwidth = 2;
		e.ipady = 1;
		e.ipadx = 1;

		// d.gridwidth =2;
		d.gridx = 1;
		d.gridy = 0;
		d.anchor = GridBagConstraints.FIRST_LINE_START;
		downPane.add(this.saveValButton, d);
		saveValButton.addActionListener(this);

		// d.gridwidth =2;
		d.gridx = 4;
		d.gridy = 0;

		downPane.add(this.useValButton, d);
		useValButton.addActionListener(this);

	
	}

	private void updateTable(){
		//TODO
		System.out.println("lenght="+tableLength);
		System.out.println("width="+tableWidith);
		//table.setSize(tableLength, tableWidith);
		//myMapInfo.setMaplength(tableLength);
		//myMapInfo.setMawidth(tableWidith);
		model.setRowCount(tableLength);
		model.setColumnCount(tableWidith);
		table.setModel(model);
		
		int column=table.getColumnCount();
		int row=table.getRowCount();
		
			for(int i=0;i<column; i++){
				
				for(int t=0; t<row; t++){
					table.setValueAt("0", t, i);
					
				}
			}
			
		//table.update(getGraphics());
		//table.updateUI();
	}
	
	private void createNewTable() {

		
		table = new JTable(tableLength, tableWidith);
		model = new DefaultTableModel(tableLength, tableWidith);
		table.setModel(model);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// Dimension tablesize = new Dimension(1600 ,800);

		// table.setPreferredSize(tablesize);
		// table.setRowSelectionAllowed(false);
		// table.setColumnSelectionAllowed(false);
		table.setCellSelectionEnabled(false);

		centerPane.add(table, BorderLayout.CENTER);
		
		int column=table.getColumnCount();
		int row=table.getRowCount();
		
			for(int i=0;i<column; i++){
				
				for(int t=0; t<row; t++){
					table.setValueAt("0", t, i);
					
				}
			}
		// JLabel tableNoteLabel2=new JLabel("Please enter values");
		// centerPane.add(tableNoteLabel2,BorderLayout.SOUTH);
		// table.setBackground(Color);
		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		cellSelectionModel
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				//change the cell value unless the button group is in people state/ editable ==false
				if(editable){
					int selectedRow = table.getSelectedRow();
					int selectedColumns = table.getSelectedColumn();
					table.setValueAt(cellValue, selectedRow, selectedColumns);
					//System.out.println("Selected: " + selectedRow + selectedColumns);
					
				}
				

			}
		});
	}

	private void loadTable() {
		valReader myreader=new valReader(valFile);
		ArrayList<ArrayList<String>> data=myreader.getData() ;
		//data.
		System.out.println("Max_x="+myreader.getMax_x());
		System.out.println("Max_y="+myreader.getMax_y());
		System.out.println("data size="+data.size());
		setTableLength(myreader.getMax_x()+1);
		setTableWidith(myreader.getMax_y()+1);
		table = new JTable(tableLength, tableWidith);
		for(int i=0;i<data.size();i++){
			try{
				
				
			}catch (NumberFormatException e){
				System.out.println("NumberFormatException 2");
				
			}catch (ClassCastException e){
				System.out.println("ClassCastException");
				
			}
			//System.out.println("data length="+data.get(i).size());
			if(Integer.parseInt(data.get(i).get(2))==0){
				
				table.setValueAt(data.get(i).get(3),Integer.parseInt(data.get(i).get(0)),Integer.parseInt(data.get(i).get(1)));
				table.updateUI();
				//System.out.println(data.get(i).get(3)+Integer.parseInt(data.get(i).get(0))+Integer.parseInt(data.get(i).get(1)));
			}
		}
		
	
		
		
		
		
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// Dimension tablesize = new Dimension(1600 ,800);

		// table.setPreferredSize(tablesize);
		// table.setRowSelectionAllowed(false);
		// table.setColumnSelectionAllowed(false);
		table.setCellSelectionEnabled(false);

		centerPane.add(table, BorderLayout.CENTER);

		// JLabel tableNoteLabel2=new JLabel("Please enter values");
		// centerPane.add(tableNoteLabel2,BorderLayout.SOUTH);
		// table.setBackground(Color);
		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		cellSelectionModel
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				int selectedRow = table.getSelectedRow();
				int selectedColumns = table.getSelectedColumn();
				table.setValueAt(cellValue, selectedRow, selectedColumns);
				System.out
						.println("Selected: " + selectedRow + selectedColumns);
			}
		});


	}

	

	
	private void addFeatures() {
		pane.add(leftPane, BorderLayout.WEST);
		pane.add(centerPane, BorderLayout.CENTER);
		pane.add(downPane, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == wallButton){
			editable=true;
			cellValue = -1;
		}
			
		else if (e.getSource() == emptyButton){
			cellValue = 0;
			editable=true;
		}
			
		else if (e.getSource() == blockButton){
			cellValue = 9;
			editable=true;
		}
			
		else if (e.getSource() == exitButton){
			editable=true;
			cellValue = 9;
		}
		else if (e.getSource() == exitButton2){
			editable=true;
			cellValue = 900;
		}
		else if (e.getSource() == peopleButton){
			editable=true;
			cellValue = 2;
		}
		else if (e.getSource() == peopleButton2){
			editable=false;
			//cellValue = 900;
		}
		else if (e.getSource() == peopleButton2){
			editable=false;
			//cellValue = 900;
		}
		else if (e.getSource() == helpButton){
			
			try {
				displayHelpMenu();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		else if (e.getSource() == this.saveValButton) {
			if(checkCellTable()){
				System.out.println("Table valiade2");
				if(plane[index]==0){
					calculateDirectionMap();
				}
				else if(plane[index]==1){
					calculateDistanceMap();
				}
				
				saveFile();
			}else{
				System.out.println("Table invaliade3");
				JOptionPane
				.showMessageDialog(this,
						"Error, please verfy your input. The current input is invalide.");
			}

		} 
		else if (e.getSource() == this.useValButton) {
			if(checkCellTable()){
				if(plane[index]==0){
					calculateDirectionMap();
				}
				else if(plane[index]==1){
					calculateDistanceMap();
				}
				useFile();
				System.out.println("Table valiade0");
			}else{
				System.out.println("Table invaliade1");
			}

		}
		else if(e.getSource() == this.updateButton){
			// TODO Auto-generated catch block
			if(this.checkCellSizeInput()){
				this.setTableLength(Integer.parseInt(lengthField.getText()));
				this.setTableWidith(Integer.parseInt(widthField.getText()));
				updateTable();
			}
		}

	}
	
	private void displayHelpMenu() throws IOException{
		final String IMAGE_URL = "/resource/direction.jpg";
		String text = "Directional data is based on eight compass points, with directions equating to the following values:\n";
		String text2="Directions (North-North East-East-South East-South-South West-West-North West) are represejted by values from 1 to 8, as showned in the picture below. ";
		JEditorPane editorPane = new JEditorPane();
		editorPane.setSize(200, 100);
        editorPane.setEditable(false);
        editorPane.setText(text+text2);
		
		JPanel displayHelpMenu=new JPanel();
		//JPanel northPanel=new JPanel();
		displayHelpMenu.setLayout(new BorderLayout());
		JDialog dialog = new JDialog();     
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //dialog.setTitle("Image Loading Demo");
        dialog.setTitle("Help Menu");
        //JLabel imageLabel=
        //northPanel.add(editorPane);
        //northPanel.add(new JLabel(text2));
        //northPanel.add(myOutput);
        
        displayHelpMenu.add(editorPane,BorderLayout.NORTH);
        
		displayHelpMenu.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(IMAGE_URL)))),BorderLayout.CENTER);
		//dialog.add(new JLabel(text));
		dialog.add(displayHelpMenu);
        

        dialog.pack();
        dialog.setLocationByPlatform(true);
        dialog.setVisible(true);
		
	}
	
	
	
	private boolean checkCellTable(){
		int column=table.getColumnCount();
		int row=table.getRowCount();
		map=new int [row] [column];
		try{
			for(int i=0;i<column; i++){
				for(int t=0; t<row; t++){
					int x=  Integer.parseInt(table.getValueAt(t, i).toString()) ;
					//int x=  (Integer) table.getValueAt(t, i);
					//String x=table.getValueAt(t, i).toString();			
					//System.out.println("at "+t +i+" x="+x);
					map [t] [i]=x;
					if(layout[index]==0){
						if(x<=-2 || x>=10){
							System.out.println("Error at "+t +i+", x="+x);
							return false;
						}
					}else if(layout[index]==1){
						if(x<=-2 || (x>=10 && x<100) || (x>108 &&x<200)|| (x>900)){
							System.out.println("Error at "+t +i+", x="+x);
							return false;
						}
						
					}else{
						System.out.println("Error in checkCellTable()");
					}
					
					
				}
			}
		}catch (NumberFormatException e){
			System.out.println("NumberFormatException 0");
			return false;
		}catch (ClassCastException e){
			System.out.println("ClassCastException 0");
			return false;
		}
		return true;
	}

	private void useFile(){
		valFile=new File(getValPath() ,"temp.val");
		int column=table.getColumnCount();
		int row=table.getRowCount();
        try {
			writer = new FileWriter(valFile);
			//writeValLine("Course Name","Type","Item Name","Weihgt");
			for(int i=0;i<row; i++){
				for(int t=0; t<column; t++){
					String x=  table.getValueAt(i, t).toString() ;
					writeValLine(i,t,0,x);
				}
			}			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private void calculateDirectionMap(){
		checkCellTable();
		mapAnalysiser=new directionAnalysiser(map,layout[index]);
		mapAnalysiser.claculateMap_Dir();
		mapAnalysiser.getExits();
		mapAnalysiser.calculateDirection();
		directionmap=mapAnalysiser.getDirectionmap();
		exits=mapAnalysiser.getExit2D();
		peopleTotal=mapAnalysiser.getPeopleTotal();
		printDistanceMap();
	}
	


	private void calculateDistanceMap(){
		checkCellTable();
		mapAnalysiser=new directionAnalysiser(map,layout[index]);
		mapAnalysiser.claculateMap_Dis();
		mapAnalysiser.getExits();
		mapAnalysiser.calculateDistance();
		directionmap=mapAnalysiser.getDistanceMap();
		exits=mapAnalysiser.getExit2D();
		peopleTotal=mapAnalysiser.getPeopleTotal();
		printDistanceMap();
	}
	
	protected void printDistanceMap(){
		System.out.println("Print map");
		int row=directionmap.length;
		int column=directionmap[0].length;
		for(int x=0;x<row;x++){
			for(int y=0;y<column;y++){
				//Note directionmap is initial -2
				
					System.out.print(directionmap[x][y]);
				
			}
			System.out.println();
		}
	}
	
	
	//For testing
	private void displayDirectionMap(){
		mapAnalysiser=new directionAnalysiser(map,layout[index]);
		mapAnalysiser.claculateMap_Dir();
		directionmap=mapAnalysiser.getDirectionmap();
		
		for(int x=0;x<directionmap.length;x++){
			for(int y=0;y<directionmap[0].length;y++){
				//Note directionmap is initial -2
				table.setValueAt(directionmap[x][y],x,y);
			}
		}
		
		table.updateUI();
		
	}
	
	
	private void saveFile(){
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".VAL file", "val");
		fc.setFileFilter(filter);
		fc.setCurrentDirectory(getExecutionPath());
		
		int returnVal = fc.showSaveDialog(environment.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	
            valFile = fc.getSelectedFile();
            if(!valFile.getAbsolutePath().toLowerCase().endsWith(".val"))
            valFile=new File(valFile.getAbsolutePath()+".val");
            
            //This is where a real application would save the file.
            System.out.println("Saving: " + valFile.getName() + "." + "\n");
            
            int column=table.getColumnCount();
    		int row=table.getRowCount();
    		
    		
            try {
    			writer = new FileWriter(valFile);
    			
    			for(int i=0;i<row; i++){
    				for(int t=0; t<column; t++){
    					String x=  table.getValueAt(i, t).toString() ;
    					writeValLine(i,t,0,x);
    					//writer.write("\n");
    					writer.write(System.getProperty("line.separator"));
    				}
    				
    				//writer.write(System.getProperty("line.separator"));
    				for(int t=0; t<column; t++){
    					writeValLine(i,t,1,Integer.toString(directionmap[i][t]));
    					//writer.write("\n");
    					writer.write(System.getProperty("line.separator"));
    				}
    				if(i<row-1){//avoid final empty line
    					writer.write(System.getProperty("line.separator"));
    				}
    				
    				//writer.write("\n");	
    			}			
    			writer.flush();
    			writer.close();
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
        } else {
        	System.out.println("Save command cancelled by user." + "\n");
        }
        
    }
		
	private void writeValLine(int h1, int h2, int h3,String h4) throws IOException {
		String line = String.format("(%d,%d,%d) = %s", h1, h2, h3 ,h4);
		//line+="/n";
		writer.write(line);
	}
	
		
	private File getValPath(){
		ClassLoader cl=Thread.currentThread().getContextClassLoader();
		java.net.URL path= cl.getResource("simulator.class");
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
		String valPath =file.getPath().replaceFirst("simulator.class", "") +"Evacart workspace\\Vals";
		File myfile=new File(valPath);
		
		return myfile;
	}
	
	private File getExecutionPath(){
		ClassLoader cl=Thread.currentThread().getContextClassLoader();
		java.net.URL path= cl.getResource("simulator.class");
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
		String exePath =file.getPath().replaceFirst("simulator.class", "") +"Evacart workspace\\executions";
		File myfile=new File(exePath);
		
		return myfile;
	}
	
	private boolean checkCellSizeInput(){
		int t1,t2;
		
		try {
			t1 = Integer.parseInt(lengthField.getText());
			t2 = Integer.parseInt(widthField.getText());
		} catch (NumberFormatException nfe) {
			JOptionPane
					.showMessageDialog(this,
							"Error, please verfy your length/width input. It can only be positive numbers.");
			return false;
		}
		if (t1 >= 0 && t2>=0 && t1<=40 && t1<=40) {
			return true;
		} else {
			JOptionPane
					.showMessageDialog(this,
							"Error, please verfy your length/width input. The can be no more than 40.");
			return false;
		}
	}
	
	
	public int getTableLength() {
		return tableLength;
	}

	public void setTableLength(int tableLength) {
		this.tableLength = tableLength;
	}

	public int getTableWidith() {
		return tableWidith;
	}

	public void setTableWidith(int tableWidith) {
		this.tableWidith = tableWidith;
	}
	
	protected File getValFile() {
		return valFile;
	}
	
	protected int[][] getExits() {
		return exits;
	}
	
	protected int getPeopleTotal() {
		return peopleTotal;
	}
	
/*	protected void setMyMapInfo(mapInfo myMapInfo) {
		this.myMapInfo = myMapInfo;
	}
	
	protected mapInfo getMyMapInfo() {
		return myMapInfo;
	}*/
	

}