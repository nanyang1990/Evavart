package gui.animate.cellanimate;

import gui.CellAnimateIf;
import gui.javax.file.JFileChooser;
import gui.javax.util.FileDataPersister;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;


public class CellAnimate extends JFrame implements CellAnimateIf{
	JPanel contentPane;

	JButton buttonModifyPalette = new JButton();
	JComboBox comboGridType = new JComboBox();
	JCheckBox show2DOnly = new JCheckBox("Show 2D Only");

	DefaultListModel availableListModel = new DefaultListModel();
	JList availableModels = new JList(availableListModel);
	DefaultListModel selectedListModel = new DefaultListModel();
	JList selectedModels = new JList(selectedListModel);
	JButton buttonModelAdd = new JButton("Add Model");
	JButton buttonModelLoad = new JButton("Load Model");

	JButton buttonPlay = new JButton();
	JButton buttonPause = new JButton();
	JButton buttonStop = new JButton();
	JTextField speedCoef = new JTextField();
	JButton speedCoefApply = new JButton();
	JCheckBox showValues = new JCheckBox("Show Values");
	JCheckBox showNames = new JCheckBox("Show Names");
	
	JSlider speedSlider = new JSlider();
	JButton buttonPrevious = new JButton();
	JTextField textNumFrame = new JTextField();
	JButton buttonNext = new JButton();

	JTextField textTime = new JTextField();
	JButton buttonAddRemoveGrid = new JButton();

	javax.swing.Timer playTimer = new javax.swing.Timer(10 + 100 * 2, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			playTimer_actionPerformed(e);
		}
	});
	

	JFileChooser jFileChooserGuiFrame = new JFileChooser();
	GridDrawer gridDrawer = new SquareGridDrawer();
	int currentFrame;
	DrwParser drwParser;
	//JButton buttonAspectRatio = new JButton();
	
	DrawLog logParser = new DrawLog();
	ExtensionFileFilter cdDrwMaFilter = new ExtensionFileFilter("drw;ma", "CD++ DRW or INI Files");
	ExtensionFileFilter cdLogFilter = new ExtensionFileFilter("log", "CD++ LOG Files");
	
	//Construct the frame
	public CellAnimate() {
			jbInit();
	}

	//Component initialization
	private void jbInit(){
		this.setTitle("Cell-DEVS animation");
		setBackground(Color.white);


		JPanel buttonsPane = new JPanel(new BorderLayout());
		buttonsPane.add(createFilePane(), BorderLayout.NORTH);
		buttonsPane.add(createModelPane(), BorderLayout.CENTER);
		buttonsPane.add(createReproductionPane(), BorderLayout.SOUTH);
		

		contentPane = (JPanel)this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(buttonsPane, BorderLayout.WEST);
		contentPane.add(gridDrawer, BorderLayout.CENTER);
		

		JFileChooser chooser;
		currentFrame = 0;
		jFileChooserGuiFrame.addChoosableFileFilter(cdDrwMaFilter);
		jFileChooserGuiFrame.addChoosableFileFilter(cdLogFilter);

		setHotkeys();
	}

	/**
	 * @param string
	 * @return
	 */
	private ImageIcon getIcon(String imageName) {
		URL imageURL = null;
		imageURL = getClass().getResource(imageName);
		return new ImageIcon(imageURL);
	}

	private JPanel createReproductionPane()
	{
		ImageIcon playIcon = getIcon("play.jpg");
		ImageIcon pauseIcon = getIcon("pause.jpg");
		ImageIcon stopIcon = getIcon("stop.jpg");
		ImageIcon nextIcon = getIcon("next.jpg");
		ImageIcon previousIcon = getIcon("previous.jpg");
		ImageIcon disPlayIcon = getIcon("display.jpg");
		ImageIcon disPauseIcon = getIcon("dispause.jpg");
		ImageIcon disNextIcon = getIcon("disnext.jpg");
		ImageIcon disPreviousIcon = getIcon("disprevious.jpg");
		ImageIcon disStopIcon = getIcon("disstop.jpg");

		buttonPlay.setEnabled(false);
		buttonPlay.setPreferredSize(new Dimension(40, 25));
		buttonPlay.setDisabledIcon(disPlayIcon);
		buttonPlay.setIcon(playIcon);
		buttonPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPlay_actionPerformed(e);
			}
		});

		buttonPause.setEnabled(false);
		buttonPause.setPreferredSize(new Dimension(40, 25));
		buttonPause.setDisabledIcon(disPauseIcon);
		buttonPause.setIcon(pauseIcon);
		buttonPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPause_actionPerformed(e);
			}
		});

		buttonStop.setEnabled(false);
		buttonStop.setPreferredSize(new Dimension(40, 25));
		buttonStop.setDisabledIcon(disStopIcon);
		buttonStop.setIcon(stopIcon);
		buttonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonStop_actionPerformed(e);
			}
		});

		showValues.setSelected(true);
		showValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gridDrawer.setShowValues(showValues.isSelected());
				gridDrawer.repaint();
			}
		});

		showNames.setSelected(false);
		showNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gridDrawer.setShowNames(showNames.isSelected());
				gridDrawer.repaint();
			}
		});


		JLabel jDelayLabel = new JLabel("Delay");

		speedCoef.setPreferredSize(new Dimension(33, 23));
		speedCoef.setText(FileDataPersister.getInstance().get("grid", "speedCoef.value", "10"));

		speedCoefApply.setText("Apply");
		speedCoefApply.setPreferredSize(new Dimension(65, 25));
		speedCoefApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double newValue = Double.parseDouble(speedCoef.getText());
					FileDataPersister.getInstance().put("grid", "speedCoef", "" + newValue);
					speedSlider_stateChanged(null);

				}
				catch (NumberFormatException nan) {
					speedCoef.setText(FileDataPersister.getInstance().get("grid", "speedCoef.value", "10"));
				}
			}
		});

		JPanel coef = new JPanel();
		coef.setPreferredSize(new Dimension(190, 30));
		coef.add(jDelayLabel);
		coef.add(speedCoef);
		coef.add(speedCoefApply);


		speedSlider.setMaximum(4);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPreferredSize(new Dimension(140, 30));
		speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				speedSlider_stateChanged(e);
			}
		});
		speedSlider.setSnapToTicks(true);
		speedSlider.setValue(2);

		buttonPrevious.setEnabled(false);
		buttonPrevious.setPreferredSize(new Dimension(43, 25));
		buttonPrevious.setDisabledIcon(disPreviousIcon);
		buttonPrevious.setIcon(previousIcon);
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPrevious_actionPerformed(e);
			}
		});

		textNumFrame.setEnabled(false);
		//textNumFrame.setMaximumSize(new Dimension(39, 21));
		//textNumFrame.setMinimumSize(new Dimension(39, 21));
		textNumFrame.setPreferredSize(new Dimension(43, 25));
		textNumFrame.setCaretPosition(0);
		textNumFrame.setText("0");
		textNumFrame.setColumns(4);
		textNumFrame.setHorizontalAlignment(SwingConstants.CENTER);
		textNumFrame.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				textNumFrame_keyPressed(k);
			}
		});

		buttonNext.setEnabled(false);
		//buttonNext.setMaximumSize(new Dimension(33, 11));
		//buttonNext.setMinimumSize(new Dimension(33, 11));
		buttonNext.setPreferredSize(new Dimension(43, 25));
		buttonNext.setDisabledIcon(disNextIcon);
		buttonNext.setIcon(nextIcon);
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonNext_actionPerformed(e);
			}
		});

		
		JLabel jTimeLabel = new JLabel("Time");
		jTimeLabel.setPreferredSize(new Dimension(33, 21));
		
		textTime.setEnabled(false);
		textTime.setColumns(9);
		textTime.setHorizontalAlignment(SwingConstants.CENTER);
		textTime.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				textNumFrame_keyPressed(k);
			}
		});
		textTime.setText("00:00:00:000");
		
		buttonAddRemoveGrid.setEnabled(false);
		//buttonAddRemoveGrid.setMaximumSize(new Dimension(110, 27));
		//buttonAddRemoveGrid.setMinimumSize(new Dimension(110, 27));
		buttonAddRemoveGrid.setPreferredSize(new Dimension(110, 27));
		buttonAddRemoveGrid.setText("Remove Grid");
		buttonAddRemoveGrid.setMnemonic(KeyEvent.VK_R);
		buttonAddRemoveGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonAddRemoveGrid_actionPerformed(e);
			}
		});

		JPanel pane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 12));
		pane.setBorder(BorderFactory.createEtchedBorder());
		pane.setPreferredSize(new Dimension(200, 280));

		pane.add(Box.createHorizontalStrut(30), null);		
		pane.add(buttonPlay, null);
		pane.add(buttonStop, null);
		pane.add(buttonPause, null);
		pane.add(Box.createHorizontalStrut(30), null);		

		//pane.add(jDelayLabel, null);
		pane.add(showValues,null);
		pane.add(showNames,null);
		pane.add(coef, null);
		
		pane.add(Box.createHorizontalStrut(25), null);		
		pane.add(speedSlider, null);
		pane.add(Box.createHorizontalStrut(25), null);		

		pane.add(Box.createHorizontalStrut(30), null);		
		pane.add(buttonPrevious, null);
		pane.add(textNumFrame, null);
		pane.add(buttonNext, null);
		pane.add(Box.createHorizontalStrut(30), null);		
		
		pane.add(jTimeLabel, null);
		pane.add(textTime, null);
		pane.add(buttonAddRemoveGrid, null);
		
		return pane;
	}


	private JPanel createFilePane()
	{
		buttonModifyPalette.setText("Modify Palette");
		buttonModifyPalette.setMnemonic(KeyEvent.VK_M);
		buttonModifyPalette.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonModifyPalette_actionPerformed(e);
			}
		});

		comboGridType.addItem("Square");
		comboGridType.addItem("Triangle");
		comboGridType.addItem("Hexagonal");
		comboGridType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboGridType_actionPerformed(e);
			}
		});
		
		show2DOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(drwParser!=null)
				{
					drwParser.setShow2DOnly(show2DOnly.isSelected());
					changeMatrix(currentFrame);
				}
			}
		});
		show2DOnly.setSelected(true);

		JPanel pane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
		pane.setBorder(BorderFactory.createEtchedBorder());
		pane.setPreferredSize(new Dimension(150, 100));
		
		pane.add(Box.createHorizontalStrut(40), null);		
		pane.add(buttonModifyPalette, null);
		pane.add(Box.createHorizontalStrut(40), null);		
		pane.add(Box.createHorizontalStrut(40), null);		
		pane.add(comboGridType, null);
		pane.add(Box.createHorizontalStrut(40), null);		
		pane.add(show2DOnly, null);

		return pane;		
	}

	private JPanel createModelPane()
	{
		availableModels.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount()==2)
				{
					int index = availableModels.locationToIndex(e.getPoint());
					Object obj = availableListModel.elementAt(index);
					if(selectedListModel.indexOf(obj)<0)
					{
						selectedListModel.addElement(obj);
						buttonModelLoad.setEnabled(true);
					}
					
				}
							
			}
		});
		
		KeyAdapter keyhandler = new KeyAdapter(){
			public void keyTyped(KeyEvent e)
			{
				if(e.getKeyChar()==KeyEvent.VK_DELETE)
				{
					JList list = (JList)e.getSource();
					int index = list.getSelectedIndex();
					if(index>=0)
					{
						int size = list.getModel().getSize();
						int next = (index==size-1)? index-1: index;
						if(list==availableModels)
						{
							availableListModel.removeElementAt(index);
							availableModels.setSelectedIndex(next);
						}
						else
						{
							selectedListModel.removeElementAt(index);
							selectedModels.setSelectedIndex(next);
							if(next<0)
							{
								buttonModelLoad.setEnabled(false);
							}
						}
					}
					
				}
			}
		};
		
		availableModels.addKeyListener(keyhandler);

		JScrollPane availablePane = new JScrollPane(availableModels);
		availablePane.setPreferredSize(new Dimension(90, 120));
		

		selectedModels.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount()==2)
				{
					int index = selectedModels.locationToIndex(e.getPoint());
					selectedListModel.removeElementAt(index);
					if(selectedListModel.getSize()==0)
					{
						buttonModelLoad.setEnabled(false);
					}
				}
							
			}
		});
		selectedModels.addKeyListener(keyhandler);
		
		
		JScrollPane selectedPane = new JScrollPane(selectedModels);
		selectedPane.setPreferredSize(new Dimension(90, 120));
		
		buttonModelAdd.setPreferredSize(new Dimension(90, 25));
		buttonModelAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonModelAdd_actionPerformed(e);
			}
		});

		
		buttonModelLoad.setPreferredSize(new Dimension(90, 25));
		buttonModelLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				buttonModelLoad_actionPerformed(e);
			}
		});
		buttonModelLoad.setEnabled(false);
		
		
		JPanel left = new JPanel(new BorderLayout());
		left.add(new JLabel("Available"), BorderLayout.NORTH);
		left.add(availablePane, BorderLayout.CENTER);
		left.add(buttonModelAdd, BorderLayout.SOUTH);

		JPanel right = new JPanel(new BorderLayout());
		right.add(new JLabel("Selected"), BorderLayout.NORTH);
		right.add(selectedPane, BorderLayout.CENTER);
		right.add(buttonModelLoad, BorderLayout.SOUTH);
		
		JPanel pane = new JPanel(new BorderLayout());
		pane.add(left, BorderLayout.WEST);
		pane.add(right, BorderLayout.EAST);
			
		
		//pane.setBorder(BorderFactory.createTitledBorder("Cell Models"));
		javax.swing.border.Border border = BorderFactory.createCompoundBorder(
			BorderFactory.createEtchedBorder(),
			BorderFactory.createEmptyBorder(6, 6, 6, 6)
		);
		
		pane.setBorder(border);
		pane.setPreferredSize(new Dimension(200, 200));
		return pane;
	}

	void buttonModifyPalette_actionPerformed(ActionEvent e) {
		resetHotkeys();
		DialogPalette dialog = new DialogPalette(gridDrawer.getPalette());
		gridDrawer.repaint();
		setHotkeys();
	}

	void buttonPlay_actionPerformed(ActionEvent e) {
		int temp;
		playTimer.start();
		disableAllButtons();
		buttonPause.setEnabled(true);
		buttonStop.setEnabled(true);

		VTime tempTime;
		//if frame number or time has been changed, then goes to that frame/time
		if (validateFields()) {
			temp = Integer.parseInt(textNumFrame.getText());
			tempTime = new VTime(textTime.getText());
			if (temp == currentFrame && !tempTime.equals(drwParser.getTime(currentFrame))) {
				temp = drwParser.getIndexByTime(new VTime(textTime.getText()));
			}

			if (temp < drwParser.getMatrixCount()) {
				currentFrame = temp;
			}
		}
		else {
			updateFields();
		}
		
	}

	protected void playPause_actionPerformed(ActionEvent e) {
		if (buttonPlay.isEnabled()) {
			buttonPlay_actionPerformed(e);
		}
		else {
			if (buttonPause.isEnabled()) {
				buttonPause_actionPerformed(e);
			}
		}
	}

	private void enableAllButtons()
	{
		buttonPlay.setEnabled(true);
		buttonPause.setEnabled(true);
		buttonStop.setEnabled(true);
		textNumFrame.setEnabled(true);
		buttonPrevious.setEnabled(true);
		buttonNext.setEnabled(true);
		textTime.setEnabled(drwParser!=null && drwParser.supportTime());

		buttonModifyPalette.setEnabled(true);
		comboGridType.setEnabled(true);
		show2DOnly.setEnabled(true);

		buttonAddRemoveGrid.setEnabled(true);
		
		availableModels.setEnabled(true);
		selectedModels.setEnabled(true);
		buttonModelAdd.setEnabled(true);
		buttonModelLoad.setEnabled(selectedListModel.getSize()>0);
	}
	
	private void disableAllButtons()
	{
		buttonPlay.setEnabled(false);
		buttonPause.setEnabled(false);
		buttonStop.setEnabled(false);
		textNumFrame.setEnabled(false);
		buttonPrevious.setEnabled(false);
		buttonNext.setEnabled(false);
		textTime.setEnabled(false);
		buttonModifyPalette.setEnabled(false);
		comboGridType.setEnabled(false);
		buttonAddRemoveGrid.setEnabled(false);
		
		availableModels.setEnabled(false);
		selectedModels.setEnabled(false);
		buttonModelAdd.setEnabled(false);
		buttonModelLoad.setEnabled(false);
		show2DOnly.setEnabled(false);
	}
	
	void playTimer_actionPerformed(ActionEvent e) {
		int temp;
		temp = currentFrame + 1;
		if (temp < drwParser.getMatrixCount()) {
			changeMatrix(temp);
		}
		else {
			playTimer.stop();
			enableAllButtons();
			buttonPause.setEnabled(false);
		}
	}

	void buttonPause_actionPerformed(ActionEvent e) {
		int temp;
		playTimer.stop();
		enableAllButtons();
		buttonPause.setEnabled(false);

		VTime tempTime;
		//if frame number or time has been changed, then goes to that frame/time
		if (validateFields()) {
			temp = Integer.parseInt(textNumFrame.getText());
			tempTime = new VTime(textTime.getText());
			if (temp == currentFrame && !tempTime.equals(drwParser.getTime(currentFrame))) {
				temp = drwParser.getIndexByTime(new VTime(textTime.getText()));
			}

			changeMatrix(temp);
		}
		else {
			updateFields();
		}
	}

	void buttonStop_actionPerformed(ActionEvent e) {
		int temp;
		playTimer.stop();
		enableAllButtons();
		buttonPause.setEnabled(false);
		changeMatrix(0);
	}

	void buttonNext_actionPerformed(ActionEvent e) {
		int temp;
		VTime tempTime;
		//if frame number or time has been changed, then goes to that frame/time
		if (validateFields()) {
			temp = Integer.parseInt(textNumFrame.getText());
			tempTime = new VTime(textTime.getText());
			if (temp == currentFrame && !tempTime.equals(drwParser.getTime(currentFrame))) {
				temp = drwParser.getIndexByTime(new VTime(textTime.getText()));
			}

			temp++;
			if (temp < drwParser.getMatrixCount()) {
				changeMatrix(temp);
			}
		}
		else {
			updateFields();
		}

	}

	void buttonPrevious_actionPerformed(ActionEvent e) {
		int temp;
		VTime tempTime;
		//if frame number or time has been changed, then goes to that frame/time
		if (validateFields()) {
			temp = Integer.parseInt(textNumFrame.getText());
			tempTime = new VTime(textTime.getText());
			if (temp == currentFrame && !tempTime.equals(drwParser.getTime(currentFrame))) {
				temp = drwParser.getIndexByTime(new VTime(textTime.getText()));
			}

			temp--;
			if (temp >= 0) {
				changeMatrix(temp);
			}
		}
		else {
			updateFields();
		}

	}

	//checks if frame number and time fields have valid values
	boolean validateFields() {
		int temp;
		try {
			temp = Integer.parseInt(textNumFrame.getText());
			if (temp != currentFrame) {
				if ((temp >= 0) && (temp < drwParser.getMatrixCount()) && validateTime(textTime.getText())) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				if (new VTime(textTime.getText()).compareTo(drwParser.getTime(currentFrame)) != 0) {
					return validateTime(textTime.getText());
				}
				else {
					return true;
				}
			}

		}
		catch (NumberFormatException exc) {
			return false;
		}
	}

	//returns true in case time field value is valid
	boolean validateTime(String t) {
		boolean res;
		try {
			VTime time = new VTime(t);
			res = true;
		}
		catch (NumberFormatException e) {
			res = false;
		}
		return res;
	}

	void changeMatrix(int numFr) {
		gridDrawer.setMatrix(drwParser.getMatrix(numFr));
		gridDrawer.repaint();
		currentFrame = numFr;
		updateFields();
	}

	void updateFields() {
		//Update the frame number
		textNumFrame.setText(Integer.toString(currentFrame));
		//Update the time
		textTime.setText(drwParser.getTime(currentFrame).toString());
	}

	void speedSlider_stateChanged(ChangeEvent e) {
		double coef;
		try {
			coef = Double.parseDouble(speedCoef.getText());
		}
		catch (NumberFormatException e1) {
			coef = 1;
			speedCoef.setText("1");
		}
		int delay = (int)Math.round((10 + speedSlider.getValue()) * coef);
		System.err.println(delay);
		playTimer.setDelay(delay);

	}

	void buttonAddRemoveGrid_actionPerformed(ActionEvent e) {
		if (buttonAddRemoveGrid.getText().compareTo("Add Grid") == 0) {
			gridDrawer.setVisible(true);
			buttonAddRemoveGrid.setText("Remove Grid");
			gridDrawer.repaint();
		}
		else {
			gridDrawer.setVisible(false);
			buttonAddRemoveGrid.setText("Add Grid");
			gridDrawer.repaint();
		}
	}

	void buttonModelAdd_actionPerformed(ActionEvent e) {
		resetHotkeys();
		// Use the OPEN version of the dialog, test return for Approve/Cancel
		jFileChooserGuiFrame.setFileFilter(cdDrwMaFilter);
		jFileChooserGuiFrame.setDialogTitle("Choose a CD++ DRW or Model File");
		if (JFileChooser.APPROVE_OPTION == jFileChooserGuiFrame.showOpenDialog(this))
		{
			String filename = jFileChooserGuiFrame.getSelectedFile().getPath();
			String extension = "";
			int pos = filename.lastIndexOf('.');
			if(pos>=0) extension = filename.substring(pos+1).toLowerCase();

			if ( ! "drw".equals(extension) && 
				logParser.loadIniFile(filename) &&
				logParser.ini!=null && 
			    logParser.ini.get("top", "components")!=null )
			{
				String[] modelNames = logParser.allModelNames;
				if(modelNames==null || modelNames.length==0)
				{
					JOptionPane.showMessageDialog(this, "Can't find Cell-DEVS models in file " + filename, " Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					jFileChooserGuiFrame.setFileFilter(cdLogFilter);
					jFileChooserGuiFrame.setDialogTitle("Choose a CD++ LOG File");
					if (JFileChooser.APPROVE_OPTION == jFileChooserGuiFrame.showOpenDialog(this))
					{
						String logFilename = jFileChooserGuiFrame.getSelectedFile().getPath();
						for(int i=0; i<modelNames.length; i++)
						{
							LogDrwParser drawer = new LogDrwParser(logParser.ini, modelNames[i], logFilename); 
							if(!availableListModel.contains(drawer))
							{
								availableListModel.addElement(drawer);
							}
						}
					}
				}
			}
			else
			{
				try {
					DrwParser drawer = DrwParser.getParser(filename);
					if(!availableListModel.contains(drawer))
					{
						availableListModel.addElement(drawer);
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(this, "Can't load file: " + ex, " Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		setHotkeys();
	}

	void buttonModelLoad_actionPerformed(ActionEvent e)
	{
		int size = selectedListModel.size();
		if(size==0)	return;

		if(drwParser!=null)
		{
			drwParser.clear();		
		}
		
		boolean loadingError = false;
		if (size==1)
		{
			drwParser = (DrwParser) selectedListModel.firstElement();
			if(drwParser instanceof LogDrwParser)
			{
				if(!DrawLog.loadLogFile((LogDrwParser)drwParser))
				{
					JOptionPane.showMessageDialog(this, "Can't load log file " + drwParser.getFileName(), " Error", JOptionPane.ERROR_MESSAGE);
					loadingError = true;
				}
			}
			
			if (!loadingError)
			{
				try{
					drwParser.makeIndex();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(this, "Can't load file " + drwParser.getFileName(), " Error", JOptionPane.ERROR_MESSAGE);
					loadingError = true;
				}
			}
		}
		else
		{
			DrwParser[] drawers = new DrwParser[selectedListModel.size()];
			selectedListModel.copyInto(drawers);
		
			Hashtable logDrawers = new Hashtable();
			boolean timeSupported = drawers[0].supportTime();
			for(int i=0; i<drawers.length && !loadingError; i++)
			{
				if(timeSupported!=drawers[i].supportTime())
				{
					JOptionPane.showMessageDialog(this, 
						"Cell-Animate cannot show both time-supported and unsupported components at same time",
						"Error", JOptionPane.ERROR_MESSAGE);
					loadingError = true;
					continue;
				}
				if(drawers[i] instanceof LogDrwParser)
				{
					Vector v = (Vector)logDrawers.get(drawers[i].getFileName());
					if(v==null)
					{
						v= new Vector();
						v.addElement(drawers[i]);
						logDrawers.put(drawers[i].getFileName(), v);
					}
					else
					{
						v.addElement(drawers[i]);
					}
				}
				else
				{
					// should be SimpleDrwParser or BorderDrwParser
					try {
						drawers[i].makeIndex();
					}catch(Exception ex) {
						loadingError = true;
					}
				}
			}
			
			if(!loadingError)
			{
				LogDrwParser items[] = null;
				Enumeration iter = logDrawers.elements();
				while(iter.hasMoreElements() && !loadingError)
				{
					Vector v = (Vector) iter.nextElement();
					items = new LogDrwParser[v.size()];
					v.copyInto(items);
			
					if(DrawLog.loadLogFile(items))
					{
						for(int i=0; i<items.length; i++)
						{
							items[i].makeIndex();
						}
					}
					else
					{
						JOptionPane.showMessageDialog(this, "Can't load log file " + items[0].getFileName(), " Error", JOptionPane.ERROR_MESSAGE);
						loadingError = true;
						break;
					}
				}
				
				if(!loadingError)
				{
					drwParser = new CompoundDrwParser(drawers);
					drwParser.fileName = drawers[0].getFileName();
					drwParser.makeIndex();
				}
			}
		
		}

		if(loadingError)
		{
			this.disableAllButtons();
			buttonModelLoad.setEnabled(true);
			buttonModelAdd.setEnabled(true);
			availableModels.setEnabled(true);
			selectedModels.setEnabled(true);
			return;
		}

		drwParser.setShow2DOnly(show2DOnly.isSelected());
		this.changeMatrix(0);
		
		// The PAL file should locates in same directory of DRW or LOG file.
		String path = drwParser.getFileName();
		int pos = path.lastIndexOf(File.separatorChar);
		if(pos>=0)
			path = path.substring(0, pos+1);
		else
			path = "";
			 
		String palleteName = drwParser.toString();
		pos = palleteName.indexOf('.');
		if(pos>=0)
			palleteName = palleteName.substring(0, pos);
		
		pos = palleteName.indexOf('@');
		if(pos>=0)
			palleteName = palleteName.substring(pos+1);
			
		palleteName = palleteName + ".pal";
			
		palleteName = path + palleteName;
			
		if ((new File(palleteName)).exists())
		{
			PaletteTableModel palModels = new PaletteTableModel();
			palModels.loadData(palleteName);
			gridDrawer.getPalette().fromTable(palModels.getData());
		}
		enableAllButtons();
		buttonPause.setEnabled(false);
	}

	void comboGridType_actionPerformed(ActionEvent e) {
		Palette tempPal = gridDrawer.getPalette();
		boolean tempVis = gridDrawer.visible;
		switch (comboGridType.getSelectedIndex()) {
			case 0 :
				contentPane.remove(gridDrawer);
				gridDrawer = new SquareGridDrawer();
				contentPane.add(gridDrawer, BorderLayout.CENTER);
				gridDrawer.setPalette(tempPal);
				gridDrawer.setVisible(tempVis);
				gridDrawer.setShowValues(this.showValues.isSelected());
				gridDrawer.setShowNames(this.showNames.isSelected());
				try {
					changeMatrix(currentFrame);
				}
				catch (NullPointerException ex) {}
				buttonAddRemoveGrid_actionPerformed(new ActionEvent(buttonAddRemoveGrid, 1, ""));
				buttonAddRemoveGrid_actionPerformed(new ActionEvent(buttonAddRemoveGrid, 1, ""));
				break;
			case 1 :
				contentPane.remove(gridDrawer);
				gridDrawer = new TriangleGridDrawer();
				contentPane.add(gridDrawer, BorderLayout.CENTER);
				gridDrawer.setPalette(tempPal);
				gridDrawer.setVisible(tempVis);
				gridDrawer.setShowValues(this.showValues.isSelected());
				gridDrawer.setShowNames(this.showNames.isSelected());				
				try {
					changeMatrix(currentFrame);
				}
				catch (NullPointerException ex) {}
				buttonAddRemoveGrid_actionPerformed(new ActionEvent(buttonAddRemoveGrid, 1, ""));
				buttonAddRemoveGrid_actionPerformed(new ActionEvent(buttonAddRemoveGrid, 1, ""));
				break;
			case 2 :
				contentPane.remove(gridDrawer);
				gridDrawer = new HexagonalGridDrawer();
				contentPane.add(gridDrawer, BorderLayout.CENTER);
				gridDrawer.setPalette(tempPal);
				gridDrawer.setVisible(tempVis);
				gridDrawer.setShowValues(this.showValues.isSelected());
				gridDrawer.setShowNames(this.showNames.isSelected());				
				try {
					changeMatrix(currentFrame);
				}
				catch (NullPointerException ex) {}
				buttonAddRemoveGrid_actionPerformed(new ActionEvent(buttonAddRemoveGrid, 1, ""));
				buttonAddRemoveGrid_actionPerformed(new ActionEvent(buttonAddRemoveGrid, 1, ""));
				break;
		}

	}

	void textNumFrame_keyPressed(KeyEvent k) {
		int temp;
		if (k.getKeyCode() == KeyEvent.VK_ENTER) {
			if (validateFields()) {
				temp = Integer.parseInt(textNumFrame.getText());
				if (temp == currentFrame) {
					temp = drwParser.getIndexByTime(new VTime(textTime.getText()));
				}
				changeMatrix(temp);
			}
			else {
				updateFields();
			}
		}
	}
	/*
		private void buttonAspectRatio_actionPerformed(ActionEvent e)
		{
			int coefX;
			if (gridDrawer instanceof HexagonalGridDrawer)
			{
				coefX = (int) ((double) gridDrawer.getWidth()/(double) ((double) gridDrawer.matrix.getSizeY() + 0.5));
			}
			else
			{
				coefX = gridDrawer.getWidth()/gridDrawer.matrix.getSizeY();
			}
			int coefY = gridDrawer.getHeight()/gridDrawer.matrix.getSizeX();
			if (coefX > coefY)
			{
				this.setSize(new Dimension(this.getWidth() - gridDrawer.matrix.getSizeY()*coefX + gridDrawer.matrix.getSizeY()*coefY,this.getHeight()));
			}
			else
			{
				this.setSize(new Dimension(this.getWidth(), this.getHeight() - gridDrawer.matrix.getSizeX()*coefY + gridDrawer.matrix.getSizeX()*coefX));
			}
				
		}
	*/
	private void setHotkeys() {
		HotkeyManager hotkeyManager = HotkeyManager.getInstance();
		hotkeyManager.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "PLAYPAUSE");
		hotkeyManager.getActionMap().put("PLAYPAUSE", hotkeyPlayPause);
		hotkeyManager.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "STOP");
		hotkeyManager.getActionMap().put("STOP", hotkeyStop);
		hotkeyManager.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "NEXT");
		hotkeyManager.getActionMap().put("NEXT", hotkeyNext);
		hotkeyManager.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "PREVIOUS");
		hotkeyManager.getActionMap().put("PREVIOUS", hotkeyPrevious);
		hotkeyManager.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "SPEED_UP");
		hotkeyManager.getActionMap().put("SPEED_UP", hotkeySpeedUp);
		hotkeyManager.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "SPEED_DOWN");
		hotkeyManager.getActionMap().put("SPEED_DOWN", hotkeySpeedDown);
	}

	private void resetHotkeys() {
		HotkeyManager hotkeyManager = HotkeyManager.getInstance();
		hotkeyManager.getInputMap().clear();
		hotkeyManager.getActionMap().clear();
	}

	private final Action hotkeyPlayPause = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			setEnabled(false); // stop any other events from interfering
			playPause_actionPerformed(e);
			setEnabled(true);
		}
	};

	private final Action hotkeyStop = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (buttonStop.isEnabled()) {
				setEnabled(false); // stop any other events from interfering
				buttonStop_actionPerformed(e);
				setEnabled(true);
			}
		}
	};

	private final Action hotkeyNext = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (buttonNext.isEnabled()) {
				setEnabled(false); // stop any other events from interfering
				buttonNext_actionPerformed(e);
				setEnabled(true);
			}
		}
	};

	private final Action hotkeyPrevious = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (buttonPrevious.isEnabled()) {
				setEnabled(false); // stop any other events from interfering
				buttonPrevious_actionPerformed(e);
				setEnabled(true);
			}
		}
	};

	private final Action hotkeySpeedUp = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (speedSlider.isEnabled()) {
				setEnabled(false); // stop any other events from interfering
				int speedValue = speedSlider.getValue() + 1;
				speedSlider.setValue(speedValue);
				setEnabled(true);
			}
		}
	};

	private final Action hotkeySpeedDown = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (speedSlider.isEnabled()) {
				setEnabled(false); // stop any other events from interfering
				int speedValue = speedSlider.getValue() - 1;
				speedSlider.setValue(speedValue);
				setEnabled(true);
			}
		}
	};

	/* (non-Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	public void dispose() {
		if(drwParser!=null)
			drwParser.close();
		super.dispose();
	}

}