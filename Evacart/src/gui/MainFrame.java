package gui;

import gui.graphEditor.AbstractModelEditor;
import gui.graphEditor.AtomicModelEditor;
import gui.graphEditor.CoupledModelEditor;
import gui.image.Repository;
import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.util.FileDataPersister;
import gui.model.model.AbstractModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static JApplet parent;

	JMenuBar mainFrameMenuBar = new JMenuBar();
	JMenu mainMenuFile = new JMenu();
	JMenuItem jMenuFileExit = new JMenuItem();
	JMenuItem fileexportMenuItem = new JMenuItem();
	JMenu jMenuHelp = new JMenu();
	JMenuItem jMenuHelpAbout = new JMenuItem();

	private JButton toolbaropenButton = new JButton();
	private JButton toolbarsaveButton = new JButton();
	private JButton toolbarNewButton = new JButton();
	private JCheckBox toolbarShowExpressions = new JCheckBox();
	private JCheckBox toolbarShowActions = new JCheckBox();
	private JCheckBox toolbarShowPorts = new JCheckBox();
	private JButton toolbarhelpButton = new JButton();
	private JButton toolbarEditorButton = new JButton();
	private JButton toolbarLocalSimulatorButton = new JButton();
	private JButton toolbarAddCoupledUnitButton = new JButton();
	private JButton toolbarAddAtomicUnitButton = new JButton();
	private JToggleButton toolbarInternalToggleButton = new JToggleButton();
	private JToggleButton toolbarExternalToggleButton = new JToggleButton();
	private ButtonGroup linkButtonsGroup = new ButtonGroup();
	private JButton toolbarDeexplodeUnitButton = new JButton();

	private ImageIcon openFile = getIcon("openfile.gif");
	private ImageIcon closeFile = getIcon("closefile.gif");
	private ImageIcon newFile = getIcon("new.gif");
	private ImageIcon help = getIcon("help.gif");
	private ImageIcon editor = getIcon("editor.gif");
	private ImageIcon localSimulator = getIcon("diskRun.gif");
	private ImageIcon addCoupledUnitButtonImage = getIcon("addcoupled.gif");
	private ImageIcon addAtomicUnitButtonImage = getIcon("addatomic.gif");
//	private ImageIcon explodeButtonImage = getIcon("lupamas.gif");
	private ImageIcon deexplodeButtonImage = getIcon("lupamenos.gif");
	private ImageIcon linkInterno = getIcon("linkInterno.gif");
	private ImageIcon linkExterno = getIcon("linkExterno.gif");
	JLabel mainstatusBar = new JLabel();

	JMenuItem fileopenMenuItem = new JMenuItem();
	JMenuItem filesaveMenuItem = new JMenuItem();
	JMenuItem fileSaveAndExportMenuItem = new JMenuItem();
	JMenuItem filesaveasMenuItem = new JMenuItem();
	JMenuItem fileCloseMenuItem = new JMenuItem();
	JMenuItem filenewMenuItem = new JMenuItem();
	JMenuItem fileImportMenuItem = new JMenuItem();

	JMenuItem homeDirectoryMenuItem = new JMenuItem();
	JMenuItem imageRepositoryDirectoryMenuItem = new JMenuItem();

	JMenu editMenu = new JMenu();
	JMenuItem editundoMenuItem = new JMenuItem();
	JMenuItem editredoMenuItem = new JMenuItem();
	JMenuItem editcopyMenuItem = new JMenuItem();
	JMenuItem editpasteMenuItem = new JMenuItem();
	JMenuItem editdeleteMenuItem = new JMenuItem();
	JMenuItem editshowMenuItem = new JMenuItem();
	JMenu executeMenu = new JMenu();
	JMenuItem localCDDMenuItem = new JMenuItem();
	JMenuItem remoteCDDMenuItem = new JMenuItem();
	JMenuItem drawlogMenuItem = new JMenuItem();
	JMenuItem textEditorMenuItem = new JMenuItem();
	///////////////////////////////////////
	JMenu animateMenu = new JMenu();
	JMenuItem cellAnimateMenuItem = new JMenuItem();
	JMenuItem atomicAnimateMenuItem = new JMenuItem();
	JMenuItem coupledAnimateMenuItem = new JMenuItem();
	//JMenuItem textEditorMenuItem=new JMenuItem();
	/////////////////////////////////////////
	private JSplitPane mainSplitPane;

	private JTabbedPane mainTabbedPane;
	private JScrollPane atomicScrollPane;
	private JScrollPane atomicUnitsTreeScrollPane;
	private AtomicModelEditor atomicGraphEditor;
	private CoupledModelEditor coupledModelEditor;
	private JScrollPane coupledGraphEditorScrollPane;
	private JScrollPane coupledUnitsTreeScrollPane;
	private JScrollPane coupledClassesTreeScrollPane;

	/****  LEFT PANEL ***********/
	/**
	 * Left Panel when Atomic Tab is selected
	 */
	private JPanel atomicLeftPanel;
	/**
	 * Left Panel when Atomic Tab is selected
	 */
	private JPanel coupledLeftPanel;

	/*** Objects for AtomicLeft Panel  */
	private JSplitPane atomicLeftSplitPane;
	/**
	 * The tree for the Units
	 */
	private AtomicUnitsTree atomicUnitsTree;

	/**
	 * The data panel
	 */
	private JScrollPane atomicDataPanelScrollPane;
	private DescriptableDataPanel atomicDataPanel;

	/*** Objects form CoupledLeft Panel  */
	private JSplitPane coupledLeftMainSplitPane;
	private JSplitPane coupledLeftModelSplitPane;

	/**
	 * the tree for the classes
	 */
	private CoupledClassesTree coupledClassesTree;

	/**
	 * The tree for the Units
	 */
	private CoupledUnitsTree coupledUnitTree;

	/**
	 * The data panel
	 */
	private JScrollPane coupledDataPanelScrollPane;
	private DescriptableDataPanel coupledDataPanel;

	/**
	 * when this MainFrame is called by another Mainframe to, for example,
	 * explode an Unit, this property keeps track of the caller.
	 */
	private MainFrame creator;

	public static Vector<String> coupParaVector = new Vector<String>();

	public MainFrame() {
		this(MainFrame.parent);
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

	/**Construct the frame*/
	public MainFrame(JApplet parent) {
		MainFrame.parent = parent;
		try {
			jbInit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * we 've called an other MainFrame to do somewhat and it is
	 * returning from that call
	 */
	public void sonIsReturningFromCall(MainFrame son) {
		//returning from explode
		//Thread.currentThread().dumpStack();
		this.getSelectedGraphEditor().sonIsReturningFromCall(son);

	}

	/**Component initialization*/
	private void jbInit() throws Exception {
		//init the DataPersister
		try {
			String dataPersisterFile = gui.Constants.getInstance().getString("dataPersistor.file", "");
			if (!dataPersisterFile.equals("")) {
				FileDataPersister.getInstance().loadFrom(new CommentBufferedReader(new FileReader(new File(dataPersisterFile))));
			}
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}

		//verify the Image Repository
		File rep = Repository.getInstance().getRepository();
		if (rep == null || !rep.exists() || !rep.isDirectory()) {
			//the image repository is wrong
			//try the default
			rep = new File(System.getProperty("user.home", System.getProperty("java.io.tmpdir", "\tmp")));

			if (rep == null || !rep.exists() || !rep.isDirectory()) {
				(new InformDialog("There is no ImageRepository. The ImageRepository is a directory where to store images. Try [CDModeler dir]/repository. Select a new Image Repository",null)).setVisible(
					true);
				ImageRepositoryActionListener select = new ImageRepositoryActionListener();
				select.actionPerformed(null);
			}
			else {
				setImageDir(rep);
			}
		}

		//control window close behaviour
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new ExitActionListener());

		coupParaVector.addElement(" ");
		coupParaVector.addElement(" ");
		coupParaVector.addElement("1000");

		this.getContentPane().setLayout(new BorderLayout());
		this.setSize(new Dimension(700, 600));
		this.setTitle("CD++ Modeler");

		mainstatusBar.setText("Simulator Starts ");

		mainMenuFile.setText("File");
		jMenuFileExit.setMnemonic('E');
		jMenuFileExit.setText("Exit");
		jMenuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					exit();
				}
				catch (Exception ex) {
					(new InformDialog(ex.toString(),ex)).setVisible(true);
				}

			}
		});
		jMenuHelp.setText("Help");
		jMenuHelpAbout.setText("About");
		jMenuHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuHelpAbout_actionPerformed(e);
			}
		});

		fileopenMenuItem.setMnemonic('O');
		fileopenMenuItem.setText("Open");
		filesaveMenuItem.setMnemonic('S');
		filesaveMenuItem.setText("Save");
		fileSaveAndExportMenuItem.setText("Save and Export");
		fileSaveAndExportMenuItem.setMnemonic('V');
		filesaveasMenuItem.setMnemonic('A');
		filesaveasMenuItem.setText("Save As");
		fileCloseMenuItem.setMnemonic('C');
		fileCloseMenuItem.setText("Close");
		filenewMenuItem.setMnemonic('N');
		filenewMenuItem.setText("New");
		fileexportMenuItem.setText("Export");
		fileexportMenuItem.setMnemonic('E');

		fileImportMenuItem.setText("Import");
		fileImportMenuItem.setMnemonic('I');
		//fileinputcomponentsMenuItem.setText("Input from Register");
		//fileinputcomponentsMenuItem.setMnemonic('f');

		homeDirectoryMenuItem.setText("Home Directory");
		homeDirectoryMenuItem.setMnemonic('H');

		imageRepositoryDirectoryMenuItem.setText("Image Repository Directory");
		imageRepositoryDirectoryMenuItem.setMnemonic('I');

		editMenu.setText("Edit");
		editundoMenuItem.setMnemonic('U');
		editundoMenuItem.setText("Undo");
		editredoMenuItem.setMnemonic('R');
		editredoMenuItem.setText("Redo");
		editcopyMenuItem.setMnemonic('C');
		editcopyMenuItem.setText("Copy");
		editpasteMenuItem.setMnemonic('P');
		editpasteMenuItem.setText("Paste");
		editdeleteMenuItem.setText("Delete");
		editdeleteMenuItem.setMnemonic('l');
		//	editshowMenuItem.setText("show parent model");
		//		editshowMenuItem.setMnemonic('s');
		executeMenu.setText("Execute");
		localCDDMenuItem.setText("Local CDD");
		localCDDMenuItem.setMnemonic('L');

		remoteCDDMenuItem.setText("Remote CDD");
		remoteCDDMenuItem.setMnemonic('R');

		drawlogMenuItem.setText("Drawlog");
		drawlogMenuItem.setMnemonic('D');

		textEditorMenuItem.setText("Text Editor");
		textEditorMenuItem.setMnemonic('x');
		/////////////////////////////////////
		animateMenu.setText("Animate");
		cellAnimateMenuItem.setText("Cell-DEVS animation");
		cellAnimateMenuItem.setMnemonic('C');
		//textEditorMenuItem.setText("Text Editor");
		//textEditorMenuItem.setMnemonic('x');
		//animateMenu.setText("Animate");
		atomicAnimateMenuItem.setText("AtomicAnimate");
		atomicAnimateMenuItem.setMnemonic('A');
		coupledAnimateMenuItem.setText("CoupledAnimate");
		coupledAnimateMenuItem.setMnemonic('P');

		mainMenuFile.add(filenewMenuItem);
		mainMenuFile.add(fileopenMenuItem);
		mainMenuFile.add(filesaveMenuItem);
		mainMenuFile.add(filesaveasMenuItem);
		mainMenuFile.add(fileCloseMenuItem);
		mainMenuFile.add(fileexportMenuItem);
		mainMenuFile.add(fileSaveAndExportMenuItem);
		mainMenuFile.add(fileImportMenuItem);
		//mainMenuFile.add(fileinputcomponentsMenuItem);
		mainMenuFile.addSeparator();

		mainMenuFile.add(imageRepositoryDirectoryMenuItem);
		mainMenuFile.addSeparator();
		mainMenuFile.add(jMenuFileExit);
		editMenu.add(editundoMenuItem);
		editMenu.add(editredoMenuItem);
		editMenu.addSeparator();
		editMenu.add(editcopyMenuItem);
		editMenu.add(editpasteMenuItem);
		editMenu.add(editdeleteMenuItem);
		executeMenu.add(localCDDMenuItem);
		executeMenu.add(remoteCDDMenuItem);
		executeMenu.add(drawlogMenuItem);
		executeMenu.add(textEditorMenuItem);
		animateMenu.add(cellAnimateMenuItem);
		animateMenu.add(atomicAnimateMenuItem);
		animateMenu.add(coupledAnimateMenuItem);

		/******TOOLBAR****/

		toolbarNewButton.setIcon(newFile);
		toolbarNewButton.setToolTipText("New");
		toolbarShowActions.setToolTipText("Show Link Actions");
		toolbarShowActions.setSelected(true);
		toolbarShowExpressions.setToolTipText("Show Link Expressions");
		toolbarShowExpressions.setSelected(true);
		toolbarShowPorts.setToolTipText("Show Link Ports");
		toolbarShowPorts.setSelected(true);
		toolbaropenButton.setIcon(openFile);
		toolbaropenButton.setToolTipText("Open File");
		toolbarsaveButton.setIcon(closeFile);
		toolbarsaveButton.setToolTipText("Save File");
		toolbarhelpButton.setIcon(help);
		toolbarhelpButton.setToolTipText("Help");

		toolbarEditorButton.setIcon(editor);
		toolbarEditorButton.setToolTipText("Editor");

	 	toolbarLocalSimulatorButton.setIcon(localSimulator);
		toolbarLocalSimulatorButton.setToolTipText("Local Simulator");

		toolbarInternalToggleButton.setIcon(linkInterno);
		toolbarInternalToggleButton.setToolTipText("Internal Link");
	
		toolbarExternalToggleButton.setIcon(linkExterno);
		toolbarExternalToggleButton.setToolTipText("External Link");
		
		toolbarAddCoupledUnitButton.setIcon(addCoupledUnitButtonImage);
		toolbarAddCoupledUnitButton.setToolTipText("Add new Coupled Model Unit");
		
		toolbarAddAtomicUnitButton.setIcon(addAtomicUnitButtonImage);
		toolbarAddAtomicUnitButton.setToolTipText("Add new Atomic Model Unit");
		
		toolbarDeexplodeUnitButton.setIcon(deexplodeButtonImage);
		toolbarDeexplodeUnitButton.setToolTipText("Close Explode Unit");
		
		JToolBar mainToolBar = new JToolBar();

		mainToolBar.add(toolbarNewButton);
		mainToolBar.add(toolbaropenButton);
		mainToolBar.add(toolbarsaveButton);
		mainToolBar.add(toolbarhelpButton);

		mainToolBar.add(new JToolBar.Separator());

		mainToolBar.add(toolbarInternalToggleButton);
		mainToolBar.add(toolbarExternalToggleButton);
		linkButtonsGroup.add(toolbarInternalToggleButton);
		linkButtonsGroup.add(toolbarExternalToggleButton);
		toolbarInternalToggleButton.setSelected(true);

		mainToolBar.add(toolbarShowExpressions);
		mainToolBar.add(toolbarShowActions);
		mainToolBar.add(toolbarShowPorts);
		
		mainToolBar.add(new JToolBar.Separator());

		mainToolBar.add(toolbarAddAtomicUnitButton);
		mainToolBar.add(toolbarAddCoupledUnitButton);

		mainToolBar.add(new JToolBar.Separator());

		mainToolBar.add(toolbarDeexplodeUnitButton);

		mainToolBar.add(new JToolBar.Separator());
		
		mainToolBar.add(toolbarLocalSimulatorButton);
		
		mainToolBar.add(toolbarEditorButton);

		/******TOOLBAR****/

		jMenuHelp.add(jMenuHelpAbout);
		mainFrameMenuBar.add(mainMenuFile);
		mainFrameMenuBar.add(editMenu);
		mainFrameMenuBar.add(executeMenu);
		///////////////////////////////////////////////
		mainFrameMenuBar.add(animateMenu);
		/////////////////////////////////////////////////
		mainFrameMenuBar.add(jMenuHelp);
		this.setJMenuBar(mainFrameMenuBar);
		this.getContentPane().add(mainToolBar, BorderLayout.NORTH);
		this.getContentPane().add(mainstatusBar, BorderLayout.SOUTH);
		this.getContentPane().add(getMainSplitPane(), BorderLayout.CENTER);

		enableEventHandler();
		setToolbarVisibility(false,false, true,true);		
	}


	/**Help | About action performed*/
	public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
		MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = getSize();
		Point loc = getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		dlg.show();
	}

	public CoupledClassesTree getCoupledClassesTree() {
		if (coupledClassesTree == null) {
			coupledClassesTree = new CoupledClassesTree(this);
		}
		return coupledClassesTree;
	}

	public JLabel getmainstatusBar() {
		return mainstatusBar;
	}

	class TabbedPaneChangedListener implements ChangeListener {
		public void stateChanged(ChangeEvent I) {
			tabPaneChanged();
		}
	}

	class toolbarDeleteActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			getSelectedGraphEditor().deleteSelectedItems();
		}
	}

	class saveActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				getSelectedGraphEditor().saveFile();
			}
			catch (IOException io) {
				(new InformDialog(io.toString(),io)).setVisible(true);
			}

		}
	}

	class saveAsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				getSelectedGraphEditor().saveAsFile();
			}
			catch (IOException io) {
				(new InformDialog(io.toString(),io)).setVisible(true);
			}

		}
	}
	class ExitActionListener extends WindowAdapter implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			try {
				exit();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				(new InformDialog(ex.toString(),ex)).setVisible(true);
			}

		}

		public void windowClosing(WindowEvent we) {
			try {
				exit();
			}
			catch (Exception ex) {
				(new InformDialog(ex.toString(),ex)).setVisible(true);
			}

		}
	}

	class CloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				getSelectedGraphEditor().closeGraph();
				if(isAtomicGraphSelected()){
				    setToolbarVisibility(false,false,true,false);
				}
				else{
				    setToolbarVisibility(false,false,false,true);    
				}
				
			}
			catch (AbortProcessException ab) {
				//aborted!!
			}
			catch (Exception io) {
				(new InformDialog(io.toString(),io)).setVisible(true);
			}
		}
	}

	class OpenActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				getSelectedGraphEditor().fileOpen();
			}
			catch (Exception io) {
				(new InformDialog(io.toString(),io)).setVisible(true);
			}
		}

	}
	class HelpActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			showHelp();
		}
	}

	class EditorActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			showTextEditor();
		}
	}

	class LocalSimulatorActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
   
			    if(isCoupledGraphSelected()){
				    //Run the local simulator upon the actual model
				    CDDInterface aSimuRunDialog = (CDDInterface)Class.forName(Constants.getInstance().getString("cddLocal", "gui.cdd.local.LocalCDDServer")).newInstance();

			        AbstractModel model = getSelectedGraphEditor().getModel();
				    
				    if(model != null){
					    File path = model.getActualPath();
					    File maFile = model.getExportClasspath();
					    			    
						if(path != null){
						    aSimuRunDialog.setModelDir(path);
						    if(maFile != null){
						        aSimuRunDialog.setMA(maFile);
						    }
						}
				    }
					aSimuRunDialog.setVisible(true);
			    }
			}
			catch (Exception io) {
				(new InformDialog(io.toString(),io)).setVisible(true);
			}
		}
	}

	class FileExportActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			exportfile();
		}
	}

	class FileImportActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			importModels();
		}
	}
	class LocalCDDActionEventHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				CDDInterface aSimuRunDialog = (CDDInterface)Class.forName(Constants.getInstance().getString("cddLocal", "gui.cdd.local.LocalCDDServer")).newInstance();
				aSimuRunDialog.setVisible(true);
			}
			catch (Exception io) {
				(new InformDialog(io.toString(),io)).setVisible(true);
			}

		}
	}

	class RemoteCDDActionEventHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				CDDInterface aSimuRunDialog = (CDDInterface)Class.forName(Constants.getInstance().getString("cddRemote", "gui.cdd.remote.RemoteCDDServer")).newInstance();
				aSimuRunDialog.setVisible(true);
			}
			catch (Exception io) {
				(new InformDialog(io.toString(),io)).setVisible(true);
			}
		}
	}


	class DrawlogActionEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				RunDrawlogInterface drawlog = (RunDrawlogInterface)Class.forName(Constants.getInstance().getString("drawlog", "gui.drawlog.RunDrawlogInterfaceImpl")).newInstance();
				drawlog.setVisible(true);
			}
			catch (Exception io) {
				(new InformDialog(io.toString(),io)).setVisible(true);
			}

		}
	}

	class HomeDirectoryActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			OptionDialog optionDialog = new OptionDialog(MainFrame.this, "Default Directory", true);

			optionDialog.setSize(240, 80);
			optionDialog.setLocationRelativeTo(MainFrame.this);
			optionDialog.setVisible(true);

			if (optionDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
				setDefaultDir(optionDialog.getDir());
			}

		}

	}

	class ImageRepositoryActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			File savedDir = chooser.getCurrentDirectory();
			chooser.setCurrentDirectory(Repository.getInstance().getRepository());

			if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(MainFrame.this)) {
				setImageDir(chooser.getSelectedFile());
			}

			chooser.setLastAccesedDir(savedDir);
		}

	}

	class AddCoupledUnitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			getSelectedGraphEditor().addCoupledUnit();
		}
	}

	class AddAtomicUnitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			getSelectedGraphEditor().addAtomicUnit();
		}
	}

	public static void setCoupAnimPars(Vector<String> vec) {
		coupParaVector = vec;
	}

	//////////////////////////////////////////////////////////////////////
	class cellAnimateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				CellAnimateIf cellAnimate = (CellAnimateIf)Class.forName(Constants.getInstance().getString("cellAnimate", "gui.animate.cellanimate.CellAnimate")).newInstance();
				cellAnimate.setLocationRelativeTo(MainFrame.this);
				cellAnimate.setLocation(MainFrame.this.getLocation());
				cellAnimate.setSize(MainFrame.this.getSize());

				cellAnimate.setVisible(true);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	////////////////////////////////////////////////////////////////////////

	class atomicAnimateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			AtomicAnimateDialog aatomicAnimateDialog = new AtomicAnimateDialog(MainFrame.this, "atomic animate", true);

			aatomicAnimateDialog.setSize(350, 130);
			aatomicAnimateDialog.setLocationRelativeTo(MainFrame.this);
			aatomicAnimateDialog.setVisible(true);

			if (aatomicAnimateDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
				//ANIMATE HERE

				try {
					AtomicAnimateIf aAtomicAnimate = (AtomicAnimateIf)Class.forName(Constants.getInstance().getString("atomicAnimate", "gui.animate.atomic.AtomicAnimate")).newInstance();

					aAtomicAnimate.init(new File(aatomicAnimateDialog.getLog()), new File(aatomicAnimateDialog.getModel()), aatomicAnimateDialog.getChunck());
					aAtomicAnimate.setLocation(MainFrame.this.getLocation());
					aAtomicAnimate.setSize(MainFrame.this.getSize());
					aAtomicAnimate.setVisible(true);
					aAtomicAnimate.startAnimation();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	//////////////////////////////////////////////////////////////////////
	class coupledAnimateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			CoupledAnimateDialog acoupledAnimateDialog = new CoupledAnimateDialog(MainFrame.this, "coupled animate", true);
			acoupledAnimateDialog.setLocationRelativeTo(MainFrame.this);
			acoupledAnimateDialog.setVisible(true);

			if (acoupledAnimateDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
				try {

					File model = new File(acoupledAnimateDialog.getModel());
					File log = new File(acoupledAnimateDialog.getLog());
					CoupledAnimateIf aCoupledAnimate = (CoupledAnimateIf)Class.forName(Constants.getInstance().getString("coupledAnimate", "gui.animate.coupled.CoupledAnimate")).newInstance();
					aCoupledAnimate.init(model, log, acoupledAnimateDialog.getElapse());
					aCoupledAnimate.setLocationRelativeTo(MainFrame.this);
					aCoupledAnimate.setLocation(MainFrame.this.getLocation());
					aCoupledAnimate.setSize(MainFrame.this.getSize());
					aCoupledAnimate.setVisible(true);
				}
				catch (Exception ex) {
					new InformDialog(ex.toString(),ex).setVisible(true);
				}
			}
		}
	}
	////////////////////////////////////////////////////////////////////////
	class textEditorActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			showTextEditor();
		}
	}

	class NewActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				getSelectedGraphEditor().newGraph();
				if(isAtomicGraphSelected()){
				    setToolbarVisibility(true,false,true,false);
				}
				else{
				    setToolbarVisibility(false,true,false,true);
				}
			}
			catch (IOException ab) {
				(new InformDialog(ab.toString(),ab)).setVisible(true);
			}

		}
	}
	private JScrollPane getAtomicGraphEditorScrollPane() {
		if (atomicScrollPane == null) {
			atomicScrollPane = new JScrollPane();
			atomicScrollPane.setName("Atomic");
			atomicScrollPane.getViewport().add(getAtomicGraphEditor());
		}
		return atomicScrollPane;

	}
	private JScrollPane getCoupledGraphEditorScrollPane() {
		if (coupledGraphEditorScrollPane == null) {
			coupledGraphEditorScrollPane = new JScrollPane();
			coupledGraphEditorScrollPane.setName("Coupled");
			coupledGraphEditorScrollPane.getViewport().add(getCoupledModelEditor());
		}
		return coupledGraphEditorScrollPane;

	}

	public void enableEventHandler() {

		filesaveMenuItem.addActionListener(new saveActionListener());
		fileSaveAndExportMenuItem.addActionListener(new FileExportActionListener());
		fileSaveAndExportMenuItem.addActionListener(new saveActionListener());
		filesaveasMenuItem.addActionListener(new saveAsActionListener());
		fileCloseMenuItem.addActionListener(new CloseActionListener());
		fileopenMenuItem.addActionListener(new OpenActionListener());
		filenewMenuItem.addActionListener(new NewActionListener());
		fileexportMenuItem.addActionListener(new FileExportActionListener());
		toolbarsaveButton.addActionListener(new saveActionListener());
		toolbarNewButton.addActionListener(new NewActionListener());
		toolbaropenButton.addActionListener(new OpenActionListener());
		toolbarhelpButton.addActionListener(new HelpActionListener());
		toolbarEditorButton.addActionListener(new EditorActionListener());
		toolbarLocalSimulatorButton.addActionListener(new LocalSimulatorActionListener());
		toolbarAddCoupledUnitButton.addActionListener(new AddCoupledUnitActionListener());
		toolbarAddAtomicUnitButton.addActionListener(new AddAtomicUnitActionListener());
		toolbarDeexplodeUnitButton.addActionListener(new ExitActionListener());
		toolbarInternalToggleButton.addActionListener(getAtomicGraphEditor());
		toolbarInternalToggleButton.setActionCommand("int");
		toolbarExternalToggleButton.addActionListener(getAtomicGraphEditor());
		toolbarExternalToggleButton.setActionCommand("ext");
		toolbarShowExpressions.addActionListener(getAtomicGraphEditor());
		toolbarShowExpressions.setActionCommand("showExpressions");
		toolbarShowActions.addActionListener(getAtomicGraphEditor());
		toolbarShowActions.setActionCommand("showActions");
		toolbarShowPorts.addActionListener(getCoupledModelEditor());
		toolbarShowPorts.setActionCommand("showPorts");
		
		homeDirectoryMenuItem.addActionListener(new HomeDirectoryActionListener());
		imageRepositoryDirectoryMenuItem.addActionListener(new ImageRepositoryActionListener());

		editdeleteMenuItem.addActionListener(new toolbarDeleteActionListener());
		getMainTabbedPane().addChangeListener(new TabbedPaneChangedListener());
		fileImportMenuItem.addActionListener(new FileImportActionListener());
		localCDDMenuItem.addActionListener(new LocalCDDActionEventHandler());
		remoteCDDMenuItem.addActionListener(new RemoteCDDActionEventHandler());
		drawlogMenuItem.addActionListener(new DrawlogActionEventHandler());
		cellAnimateMenuItem.addActionListener(new cellAnimateActionListener());
		atomicAnimateMenuItem.addActionListener(new atomicAnimateActionListener());
		coupledAnimateMenuItem.addActionListener(new coupledAnimateActionListener());
		textEditorMenuItem.addActionListener(new textEditorActionListener());

	}

	private boolean exportfile() {
		return getSelectedGraphEditor().exportFile();
	}

	private boolean importModels() {
		//inputclasses should be not abilitated if
		//atomic is selected!
		if (!isCoupledGraphSelected()) {
			new InformDialog("Option only allowed for Coupled Models",null);
			return false;
		}
		else {

			JFileChooser fc = new JFileChooser();
			ExtensionFilter exts = new ExtensionFilter();
			exts.addExtension("ma");
			exts.addExtension("cdd");
			exts.addExtension("cpp");

			fc.addChoosableFileFilter(exts);

			int returnVal = fc.showDialog(MainFrame.this, "Import");

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String inputFileName = fc.getSelectedFile().getPath();

				try {
					File modelFile = new File(inputFileName);
					getCoupledModelEditor().importModelsFromFile(modelFile);
				}
				catch (IOException e) {
					mainstatusBar.setText("Error importing" + inputFileName);
				}

				//System.out.println("118111");
				return true;
			}
			else {
				//this.repaint();
				//System.out.println("119111");
				return false;
			}
		}
	}

	public boolean IsCoupledFile(String aFileName) throws IOException {
		File aFile = new File(aFileName);
		BufferedReader aReader = new BufferedReader(new FileReader(aFile));
		aReader.readLine();
		String aString = aReader.readLine();
		if (aString.indexOf("components :") >= 0) {
			///////*********add a space(s :)/////////
			aReader.close();

			//System.out.println("122111");
			return true;
		}
		aReader.close();
		//System.out.println("123111");
		return false;
	}

	/**
	 * returns where Atomic Model Tab is selected
	 */
	private boolean isAtomicGraphSelected() {
		return (getMainTabbedPane().getSelectedComponent().getName().equals("Atomic"));
	}

	/**
	 * returns where Coupled Model Tab is selected
	 */

	private boolean isCoupledGraphSelected() {
		return (getMainTabbedPane().getSelectedComponent().getName().equals("Coupled"));
	}

	/**
	* Expands recursively a JTree from the last node of path up to the very bottom of the tree
	*/
	public void expandRecJTreeFromNode(TreePath path, JTree tree) {

		DefaultMutableTreeNode thisNode = ((DefaultMutableTreeNode)path.getLastPathComponent());

		//For each child
		Enumeration<?> hijos = thisNode.children();
		while (hijos.hasMoreElements()) {
			//get the child
			DefaultMutableTreeNode hijoActual = (DefaultMutableTreeNode)hijos.nextElement();
			javax.swing.tree.TreePath aux = path.pathByAddingChild(hijoActual);
			//expand from the child to the end
			expandRecJTreeFromNode(aux, tree);
		}

		//expand me
		tree.expandPath(path);
	}

	private JScrollPane getCoupledUnitsTreeScrollPane() {
		if (coupledUnitsTreeScrollPane == null) {
			coupledUnitsTreeScrollPane = new JScrollPane();
			coupledUnitsTreeScrollPane.setName("CoupledUnitsTreeScrollPane");
			//coupledUnitsTreeScrollPane.setMinimumSize(new Dimension(200, 200));			
			coupledUnitsTreeScrollPane.getViewport().add(getCoupledUnitsTree());
		}
		return coupledUnitsTreeScrollPane;

	}

	private JScrollPane getCoupledClassesTreeScrollPane() {
		if (coupledClassesTreeScrollPane == null) {
			coupledClassesTreeScrollPane = new JScrollPane();
			coupledClassesTreeScrollPane.setName("CoupledClassesTreeScrollPane");
			//coupledClassesTreeScrollPane.setMinimumSize(new Dimension(200, 200));
			coupledClassesTreeScrollPane.getViewport().add(getCoupledClassesTree());

		}
		return coupledClassesTreeScrollPane;

	}

	public CoupledUnitsTree getCoupledUnitsTree() {
		if (coupledUnitTree == null) {
			coupledUnitTree = new CoupledUnitsTree(this);
		}
		return coupledUnitTree;
	}

	public CoupledModelEditor getCoupledModelEditor() {
		if (coupledModelEditor == null) {
			coupledModelEditor = new CoupledModelEditor(this);
			//Thread.dumpStack();
		}
		return coupledModelEditor;
	}

	public AtomicModelEditor getAtomicGraphEditor() {
		if (atomicGraphEditor == null) {
			atomicGraphEditor = new AtomicModelEditor(this);
			//Thread.dumpStack();
		}
		return atomicGraphEditor;
	}

	private void showHelp() {
		new HelpInfo();
		HelpLoader.showHelp();
	}

	/**
	 * Returns the defaultDir.
	 * @return String
	 */
	public String getDefaultDir() {
		return FileDataPersister.getInstance().get("gui.configuration", "defaultDir", System.getProperty("user.home"));

	}

	/**
	 * Returns the default size of the application
	 * @param defaultDir
	 */
	public Dimension getDefaultSize() {
		int width = Integer.parseInt(FileDataPersister.getInstance().get("gui.configuration", "defaultWidth", "600"));
		int height = Integer.parseInt(FileDataPersister.getInstance().get("gui.configuration", "defaultHeight", "600"));
		return new Dimension(width, height);
	}
	/**
	 * Sets the defaultDir.
	 * @param defaultDir The defaultDir to set
	 */
	public void setDefaultDir(String defaultDir) {
		FileDataPersister.getInstance().put("gui.configuration", "defaultDir", defaultDir);
	}

	/**
	 * Sets the image repository Dir.
	 * @param defaultDir The defaultDir to set
	 */
	public void setImageDir(File imageRepDir) {
		FileDataPersister.getInstance().put("gui.configuration", "image.repository", imageRepDir.getPath());
		Repository.getInstance().setRepository(imageRepDir);
		this.getSelectedGraphEditor().repaint();
	}

	/**
	 * Returns the mainTabbedPane.
	 * @return JTabbedPane
	 */
	public JTabbedPane getMainTabbedPane() {
		if (mainTabbedPane == null) {
			mainTabbedPane = new JTabbedPane();
			mainTabbedPane.addTab("Atomic", getAtomicGraphEditorScrollPane());
			mainTabbedPane.addTab("Coupled", getCoupledGraphEditorScrollPane());
		}
		return mainTabbedPane;
	}

	/**
	 * Sets the mainTabbedPane.
	 * @param mainTabbedPane The mainTabbedPane to set
	 */
	public void setMainTabbedPane(JTabbedPane mainTabbedPane) {
		this.mainTabbedPane = mainTabbedPane;
	}

	public void setStatusText(String text) {
		mainstatusBar.setText(text);
	}
	public AbstractModelEditor getSelectedGraphEditor() {
		return (AbstractModelEditor) ((JScrollPane)getMainTabbedPane().getSelectedComponent()).getViewport().getComponent(0);

	}

	/**
	 * Returns the atomicStatesTree.
	 * @return JTree
	 */
	public AtomicUnitsTree getAtomicUnitsTree() {
		if (atomicUnitsTree == null) {
			atomicUnitsTree = new AtomicUnitsTree(this);
		}
		return atomicUnitsTree;
	}

	public JScrollPane getAtomicUnitsTreeScrollPane() {
		if (atomicUnitsTreeScrollPane == null) {
			atomicUnitsTreeScrollPane = new JScrollPane();
			//atomicUnitsTreeScrollPane.setMinimumSize(new Dimension(200, 200));
			//coupledLeftModelSplitPane.setDividerLocation(coupledLeftModelSplitPane.getHeight()/2);
			atomicUnitsTreeScrollPane.getViewport().add(getAtomicUnitsTree());
		}
		return atomicUnitsTreeScrollPane;
	}

	/**
	 * Returns the creator.
	 * @return MainFrame
	 */
	public MainFrame getCreator() {
		return creator;
	}

	/**
	 * Sets the creator.
	 * @param creator The creator to set
	 */
	public void setCreator(MainFrame creator) {
		this.creator = creator;
	}

	/**
	 * Returns the atomicDataPanel.
	 * @return JTextArea
	 */
	public DescriptableDataPanel getAtomicDataPanel() {
		if (atomicDataPanel == null) {
			atomicDataPanel = new DescriptableDataPanel();
		}
		return atomicDataPanel;
	}

	/**
	 * Returns the atomicDataPanelScrollPanel.
	 * @return JScrollPane
	 */
	private JScrollPane getAtomicDataPanelScrollPanel() {
		if (atomicDataPanelScrollPane == null) {
			atomicDataPanelScrollPane = new JScrollPane();
			atomicDataPanelScrollPane.getViewport().add(getAtomicDataPanel());
		}
		return atomicDataPanelScrollPane;
	}

	/**
	 * Returns the atomicLeftPanel.
	 * @return JPanel
	 */
	private JPanel getAtomicLeftPanel() {
		if (atomicLeftPanel == null) {
			atomicLeftPanel = new JPanel();
			atomicLeftPanel.setLayout(new BorderLayout());
			atomicLeftPanel.add(getAtomicLeftSplitPane(), BorderLayout.CENTER);
			//	getAtomicLeftSplitPane().setDividerLocation(getAtomicLeftSplitPane().getHeight()/3);

		}
		return atomicLeftPanel;
	}

	/**
	 * Returns the atomicLeftSplitPane.
	 * @return JSplitPane
	 */
	private JSplitPane getAtomicLeftSplitPane() {
		if (atomicLeftSplitPane == null) {
			atomicLeftSplitPane = new JSplitPane();
			atomicLeftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			//			atomicLeftSplitPane.setMinimumSize(new Dimension(100, 80));
			atomicLeftSplitPane.setTopComponent(getAtomicUnitsTreeScrollPane());
			atomicLeftSplitPane.setBottomComponent(getAtomicDataPanelScrollPanel());
			int dividerLocation = getDefaultSize().height / 2;
			atomicLeftSplitPane.setDividerLocation(dividerLocation);
		}

		return atomicLeftSplitPane;
	}

	/**
	 * Returns the coupledDataPanel.
	 * @return JTextArea
	 */
	public DescriptableDataPanel getCoupledDataPanel() {
		if (coupledDataPanel == null) {
			coupledDataPanel = new DescriptableDataPanel();
		}
		return coupledDataPanel;
	}

	/**
	 * Returns the coupledDataPanelScrollPane.
	 * @return JScrollPane
	 */
	private JScrollPane getCoupledDataPanelScrollPane() {
		if (coupledDataPanelScrollPane == null) {
			coupledDataPanelScrollPane = new JScrollPane();
			coupledDataPanelScrollPane.getViewport().add(getCoupledDataPanel());
		}

		return coupledDataPanelScrollPane;
	}

	/**
	 * Returns the coupledLeftMainSplitPane.
	 * @return JSplitPane
	 */
	private JSplitPane getCoupledLeftMainSplitPane() {
		if (coupledLeftMainSplitPane == null) {
			coupledLeftMainSplitPane = new JSplitPane();
			coupledLeftMainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			//coupledLeftMainSplitPane.setMinimumSize(new Dimension(200, 200));
			coupledLeftMainSplitPane.setTopComponent(getCoupledLeftModelSplitPane());
			coupledLeftMainSplitPane.setBottomComponent(getCoupledDataPanelScrollPane());
			int dividerLocation = getDefaultSize().height / 3 * 2;
			coupledLeftMainSplitPane.setDividerLocation(dividerLocation);
		}

		return coupledLeftMainSplitPane;
	}

	/**
	 * Returns the coupledLeftModelSplitPane.
	 * @return JSplitPane
	 */
	private JSplitPane getCoupledLeftModelSplitPane() {
		if (coupledLeftModelSplitPane == null) {
			coupledLeftModelSplitPane = new JSplitPane();
			coupledLeftModelSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			coupledLeftModelSplitPane.setTopComponent(getCoupledClassesTreeScrollPane());
			coupledLeftModelSplitPane.setBottomComponent(getCoupledUnitsTreeScrollPane());
			int dividerLocation = getDefaultSize().height / 3;
			coupledLeftModelSplitPane.setDividerLocation(dividerLocation);
		}
		return coupledLeftModelSplitPane;
	}

	/**
	 * Returns the coupledLeftPanel.
	 * @return JPanel
	 */
	private JPanel getCoupledLeftPanel() {
		if (coupledLeftPanel == null) {
			coupledLeftPanel = new JPanel();
			coupledLeftPanel.setLayout(new BorderLayout());
			coupledLeftPanel.add(getCoupledLeftMainSplitPane(), BorderLayout.CENTER);
		}
		return coupledLeftPanel;
	}

	/**
	 * Returns the mainSplitPane.
	 * @return JSplitPane
	 */
	public JSplitPane getMainSplitPane() {
		if (mainSplitPane == null) {
			mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getAtomicLeftPanel(), getMainTabbedPane());
			//	mainSplitPane.setMinimumSize(new Dimension(100, 80));
			int dividerLocation = getDefaultSize().height / 6;
			mainSplitPane.setDividerLocation(dividerLocation);
		}
		return mainSplitPane;
	}
	private void exit() throws Exception {
		try {

			//save the DataPersister
			String dataPersisterFile = gui.Constants.getInstance().getString("dataPersistor.file", "");
			if (!dataPersisterFile.equals("")) {
				FileDataPersister.getInstance().saveTo(new CommentBufferedWriter(new FileWriter(new File(dataPersisterFile))));
			}
		}
		catch (IOException ex) {
			QueryDialog query = new QueryDialog("Constants cannot be saved! Continue? " + ex.toString());
			query.setVisible(true);
			if (query.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
				//continue closing
				innerExit();
				return;
			}
			else {
				//do anything else.
				//give the user a chance to correct the problem.
				return;
			}
		}

		//everithing is ok.
		//close the graphs
		innerExit();

	}

	private void innerExit() throws Exception {

		//call the creator, if any, to tell it we are ready
		//or bye bye
		if (this.getCreator() != null) {
			//if there is a creator, this is an explosion, I have only one graph!!
			try {
				this.getSelectedGraphEditor().dirtyHandler();

				//saved or not saved, reload the graph from file
			    this.getSelectedGraphEditor().getModel().reload();

				//now, return
				this.getCreator().sonIsReturningFromCall(this);
				//this.getAtomicGraphEditor().closeGraph();
				//this.getCoupledGraphEditor().closeGraph();

			}
			catch (AbortProcessException ab) {
				//ABORT the process!!!!
				//OK, do nothing
				//Not returning
			}
		}
		else {
			//exiting the application.
			try {
				this.getAtomicGraphEditor().dirtyHandler();
				this.getCoupledModelEditor().dirtyHandler();
				System.exit(0);
			}
			catch (AbortProcessException abb) {
				//ABORT the process!!!!
				//OK, do nothing
				//Not exiting
			}
			catch (IOException io) {
				(new InformDialog(io.toString(),io)).setVisible(true);
			}

		}

	}
	public void disableAtomicGraphEditor() {
		getMainTabbedPane().remove(getAtomicGraphEditorScrollPane());
		fileCloseMenuItem.setEnabled(false);
		tabPaneChanged();
	}
	public void disableCoupledGraphEditor() {
		getMainTabbedPane().remove(getCoupledGraphEditorScrollPane());
		fileCloseMenuItem.setEnabled(false);
		tabPaneChanged();
	}

	private void setToolbarVisibility(boolean atomic, boolean coupled, boolean resetAtomicValues, boolean resetCoupledValues){
	    if(atomic && !coupled){
			toolbarAddAtomicUnitButton.setEnabled(false);
			toolbarAddCoupledUnitButton.setEnabled(false);
			toolbarExternalToggleButton.setEnabled(true);
			toolbarInternalToggleButton.setEnabled(true);
			toolbarShowActions.setEnabled(true);
			toolbarShowExpressions.setEnabled(true);
			toolbarShowPorts.setEnabled(false);
			toolbarLocalSimulatorButton.setEnabled(false);
	    }
	    else if(!atomic && coupled){
			toolbarAddAtomicUnitButton.setEnabled(true);
			toolbarAddCoupledUnitButton.setEnabled(true);
			toolbarExternalToggleButton.setEnabled(false);
			toolbarInternalToggleButton.setEnabled(false);
			toolbarShowActions.setEnabled(false);
			toolbarShowExpressions.setEnabled(false);
			toolbarShowPorts.setEnabled(true);
			toolbarLocalSimulatorButton.setEnabled(true);
	    }
	    else if(!atomic && !coupled){
	        //the selected has no model, it is closed
			toolbarAddAtomicUnitButton.setEnabled(false);
			toolbarAddCoupledUnitButton.setEnabled(false);
			toolbarExternalToggleButton.setEnabled(false);
			toolbarInternalToggleButton.setEnabled(false);
			toolbarShowActions.setEnabled(false);
			toolbarShowExpressions.setEnabled(false);
			toolbarShowPorts.setEnabled(false);
			toolbarLocalSimulatorButton.setEnabled(false);
			toolbarDeexplodeUnitButton.setEnabled(false);
	    }
	    else{
	        //Atomic && couped!!!????
	        //this make no sense
			throw new RuntimeException("inconsistent state!! 454654");
	    	        
	    }
	    
	    if(resetAtomicValues){
	    	toolbarShowActions.setSelected(true);
	    	toolbarShowExpressions.setSelected(true);
	    }

	    if(resetCoupledValues){
	    	toolbarShowPorts.setSelected(true);
	    }
	    
		/**
		 * I am an explosion of a node!
		 */
		if (this.getCreator() != null) {
			toolbarDeexplodeUnitButton.setEnabled(true);
		}
		else{
			toolbarDeexplodeUnitButton.setEnabled(false);
		}
	    
	}
	private void tabPaneChanged() {
		if (isAtomicGraphSelected()) {
			getMainSplitPane().setLeftComponent(getAtomicLeftPanel());
			if(getSelectedGraphEditor().getModel() != null){
			    setToolbarVisibility(true, false,false,false);
			}
			else{
			    setToolbarVisibility(false, false, false,false);
			}
		}
		else if (isCoupledGraphSelected()) {
			getMainSplitPane().setLeftComponent(getCoupledLeftPanel());
			if(getSelectedGraphEditor().getModel() != null){
			    setToolbarVisibility(false, true, false,false);
			}
			else{
			    setToolbarVisibility(false, false, false,false);
			}
		}
		else {
			throw new RuntimeException("new panel added, change this!! 4654654");
		}
	}
	private void showTextEditor() {
		TextEditFrame frame = new TextEditFrame();
		frame.validate();
		frame.setVisible(true);
	}

	/**
	 * @return
	 */
	public String getImageRepositoryDir() {
		return FileDataPersister.getInstance().get("gui.configuration", "image.repository");

	}

}