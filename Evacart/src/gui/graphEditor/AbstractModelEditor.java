package gui.graphEditor;

import gui.AbortProcessException;
import gui.InformDialog;
import gui.MainFrame;
import gui.ModelTitleDialog;
import gui.OkCancelJDialog;
import gui.PortDialog;
import gui.SelectItemDialog;
import gui.image.Repository;
import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.util.DimensionUtil;
import gui.javax.util.Selectable;
import gui.model.model.AbstractModel;
import gui.model.port.AbstractPort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;




/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public abstract class AbstractModelEditor extends JPanel{
	private Selectable lastSelectableClicked;
	/**
	 * Since dont want to create a Link for a small movement, this
	 * variable has the information if the dragging was far enough to
	 * consider that there is an intention of creating a Link
	 */
	private Point maxLinkCreationDistanceReached;
	private MainFrame mainFrame;		
	protected Point elasticStartLocation;
	protected Point elasticEndLocation;	
	/**
	 * Constructor for ModelEditor.
	 */
	public AbstractModelEditor() {
		super();
		this.setBackground(Color.LIGHT_GRAY);
	}


	protected abstract JTree getStatesTree();
	protected abstract void showProperties(Selectable aSelectable);
	protected abstract JTextArea getDataPanel();
	protected abstract void setChanged(boolean changeState, boolean rebuildTree);
	protected abstract JPopupMenu getPopupMenu(Selectable selected);
	protected abstract JPopupMenu getPopupMenuTitle();
	protected abstract void canvasDoubleClicked(Point aPoint);




	/**
	 * Returns the elasticEndLocation.
	 * @return Point
	 */
	protected Point getElasticStartLocation() {
		return elasticStartLocation;
	}

	/**
	 * Sets the elasticEndLocation.
	 * @param elasticEndLocation The elasticEndLocation to set
	 */
	public void setElasticStartLocation(Point elasticEndLocation) {
		this.elasticStartLocation = elasticEndLocation;
	}

	/**
	 * Creates a new Model
	 */
	public void newGraph() throws IOException{
		try {
			dirtyHandler();
			//really new

			//if the user decides not to save changes, the dirty handler is
			//not marking the object as not dirty, cause it is not
			//applicable for all cases. mark it here.
			this.setChanged(false,false);
			
			//close the old
			closeGraph();

			//create a new one
			setModel(createNewGraph());

		}
		catch (AbortProcessException ab) {
			//cancel new? ok. Do nothing
		}
	}
	
	
	/**
	 * Shows a Save File Dialago filtering with extension
	 * Returns the selected file or null if none
	 */
	protected File askFileName() {
		File chosenFileName = null;
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ExtensionFilter(getFileExtension()));
		int returnVal = fc.showDialog(getMainFrame(), "Save");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			chosenFileName = fc.getSelectedFile();
			//if the filename has no .gui extension, add it
			if (!chosenFileName.getName().toUpperCase().endsWith("." + getFileExtension())) {
				chosenFileName = new  File(chosenFileName.getAbsolutePath()+ "." + getFileExtension());
			}
		}
		return chosenFileName;
	}
	public void closeGraph() throws AbortProcessException, IOException{

		dirtyHandler();
		//not abored!

		//close!!
		if (getModel() != null) {
			this.setModel(null);
			this.clearTreeNodes();
			this.getDataPanel().setText("");		
		}

	}

	public boolean saveAsFile() throws IOException{
		File filename = askFileName();
		if (filename != null) {
			this.getModel().setActualPath(filename.getParentFile());
			setModelFileName(new File(filename.getName()));
			return saveFile();
		}
		else {
			return false;
		}
	}

	public void fileOpen() throws Exception {
		try {
			//save changes
			this.dirtyHandler();
			//not aborted!!!!
			//get the new model
			JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter(new ExtensionFilter(getFileExtension()));
			int returnVal = fc.showOpenDialog(getMainFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				//OK. Really want a new model so
				
				// create and fill it		
				loadFrom(fc.getSelectedFile().getParentFile(), new File(fc.getSelectedFile().getName()));
				//OOps cannot load the file!
				//close the new one recently created	
			}
			else {
				//He didnt open a new model.
				//keep the current.
			}

		}
		catch (AbortProcessException ex) {
			//nothing to do
		}
	}
	
	public void loadFrom(String classpath, String classname) throws Exception{
		this.loadFrom(new File(classpath),new File(classname));
	}

	public void paintComponent(Graphics aPen) {
		this.setDoubleBuffered(true);
		super.paintComponent(aPen);
		
		if(this.isAlive() && this.getModel().getBackgroundImage() != null){		
		
			File imageFilename = Repository.getInstance().getImage(this.getModel().getBackgroundImage());
			if(imageFilename != null){
				//System.out.println("Image draw " + imageFilename);
				try {
					java.awt.Image background =  Toolkit.getDefaultToolkit().getImage(imageFilename.toURL());
					
					aPen.drawImage(background,0,0,this.getHeight(),this.getWidth(),this);
				} catch (MalformedURLException e) {
					InformDialog.showError("Error showing image",e);
				}
			}
		}
		
		if (getModel() != null) {
			Dimension newSize = getModel().draw(aPen, this);
			if (getElasticStartLocation() != null && getElasticEndLocation() != null && !getElasticEndLocation().equals(getElasticStartLocation())) {
				//System.err.println("draw!!" + getElasticStartLocation() + " " + getElasticEndLocation());							
				aPen.setColor(Color.blue);
				((Graphics2D) aPen).setStroke(new BasicStroke(2.0f));
				aPen.drawLine(getElasticStartLocation().x, getElasticStartLocation().y, getElasticEndLocation().x, getElasticEndLocation().y);
			}
			else{
				//System.err.println("not draw!!" + getElasticStartLocation() + " " + getElasticEndLocation());			
			}
			if(!DimensionUtil.includes(this.getSize(),newSize)){
				this.setPreferredSize(newSize);
				this.setSize(newSize);			
			}
		}
		
	}

	public void deleteSelectedItems() {
		if (isAlive()) {
			Iterator selectables = getModel().getSelectedSelectables().iterator();
			while (selectables.hasNext()) {
				Selectable each = (Selectable) selectables.next();
				getModel().delete(each);
			}
			setChanged(true);
			//update();

		}
		setChanged(true);
	}
	public void enableEventHandler() {
		this.addMouseListener(new simpleMouseEvent());
		this.addMouseMotionListener(new simpleMouseMoveEvent());
		this.addKeyListener(new simpleKeyEvent());
	}
	protected void setChanged(boolean changeState) {
		this.setChanged(changeState, true);
	}

	protected File getModelFileName(){
			return getModel().getModelFileName();
	}
	
	protected void setModelFileName(File filename){
		getModel().setModelFileName(filename);
	}
	public abstract void dirtyHandler() throws AbortProcessException, IOException;
	protected abstract void clearTreeNodes();
	protected abstract String getFileExtension();
	public abstract AbstractModel getModel();
	public abstract void loadFrom(File path, File filename) throws Exception;
	protected abstract AbstractModel createNewGraph();
	protected OnePointLayoutable dragItem;
	/**
	 * Add an empty Coupled Model to the Model.
	 */
	public abstract void addCoupledUnit();
	
	/**
	 * Add an empty Atomicd Model to the Model.
	 */
	public abstract void addAtomicUnit();
	
	/**
	 * returns the tree model that has the Units
	 */

		class SelectablePropertyEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showProperties((Selectable)getLastSelectableClicked());
		}
	}

	class ModelTitleSetEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ModelTitleDialog themodelTitleDialog = new ModelTitleDialog(getMainFrame(), "Model Title", true, getModel().getModelName());
			themodelTitleDialog.setSize(240, 100);
			themodelTitleDialog.setLocationRelativeTo(AbstractModelEditor.this);
			themodelTitleDialog.setVisible(true);

			if (themodelTitleDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
				String tmpTitle = themodelTitleDialog.getModelTitle();
				getModel().setModelName(tmpTitle);
			}
			setLastSelectableClicked(null);

		}
	}
	class simpleKeyEvent implements KeyListener {
		public void keyPressed(KeyEvent anEvent) {
			if (anEvent.getKeyCode() == KeyEvent.VK_DELETE) {
				deleteSelectedItems();
			}

		}

		public void keyTyped(KeyEvent anEvent) {

		}
		public void keyReleased(KeyEvent event) {

		}
	}
	class SelectBackgroundEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			File savedDir = fc.getCurrentDirectory();
			fc.setCurrentDirectory(Repository.getInstance().getRepository());
			
			ExtensionFilter filter = new ExtensionFilter();
			filter.addExtension("gif");
			filter.addExtension("jpg");
			filter.addExtension("jpeg");
			fc.setFileFilter(filter);
			int returnVal = fc.showDialog(getMainFrame(), "Set");

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				//get the selected file
				File imageFileName = fc.getSelectedFile();
				
				//copy it to the repository
				try {
					Repository.getInstance().addImage(imageFileName);
					
					AbstractModelEditor.this.getModel().setBackgroundImage(imageFileName);
					
					//redraw the panel
					setChanged(true, false);
				}
				catch (IOException e1) {
					(new InformDialog(e1.toString(),e1)).setVisible(true);
				}
			}
			fc.setLastAccesedDir(savedDir);
		}
	}

	class simpleMouseMoveEvent implements MouseMotionListener {

		public void mouseDragged(MouseEvent anEvent) {
			if (isAlive()) {
				if (getDragItem() != null) {
						getDragItem().setLocation(anEvent.getPoint());
						setChanged(true,false);
				}
				else if(getElasticStartLocation() != null && getElasticEndLocation() != null){
						//making a Link!
						
						Point end = anEvent.getPoint();
						setElasticEndLocation(end);
						
						//if a new max reached...
						if(getElasticStartLocation().distance(end) > getElasticStartLocation().distance(getMaxLinkCreationDistanceReached())){
							//...store it
							setMaxLinkCreationDistanceReached(end);
						}
								
						setChanged(true,false);						
				}
				else{
		//				System.err.println("ARGGG!!" + getElasticStartLocation() + " " + getElasticEndLocation());				
				}
			}
			else{
		//		System.err.println("??====!!" + getElasticStartLocation() + " " + getElasticEndLocation());
			}
		}

		public void mouseMoved(MouseEvent anEvent) {
		}
	}


	class simpleMouseEvent implements MouseListener {
		public void mouseClicked(MouseEvent anEvent) {
			if (isAlive()) {
				AbstractModelEditor.this.requestFocus();
				try {
					if (anEvent.getModifiers() == InputEvent.BUTTON1_MASK) {
						//Left button!

						if (anEvent.getClickCount() == 2) {
							//double click	

							Collection selectedItems = getModel().getSelectablesAt(anEvent.getPoint());

							if (selectedItems.size() == 0) {
								//double clicked on canvas
								canvasDoubleClicked(anEvent.getPoint());
								setChanged(true);

							}
							else if (selectedItems.size() == 1) {
								Selectable aSelectable = (Selectable) selectedItems.iterator().next();
								aSelectable.toggleSelected();
								setChanged(true);
							}
							else {
								SelectItemDialog selectionDialog = new SelectItemDialog(selectedItems, true);
								selectionDialog.setVisible(true);
								if (selectionDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
									if (selectionDialog.getSelectedIndex() == 0) {
										//double clicked on canvas
										canvasDoubleClicked(anEvent.getPoint());
									}
									else {
										//one of the Items
										Selectable unit = (Selectable) selectionDialog.getSelectedItem();
										unit.toggleSelected();
									}
									//we have changed the model, update
									setChanged(true);
								}
							}
						}
					}
					else if (anEvent.getModifiers() == InputEvent.BUTTON3_MASK) {
						Vector selectables = (Vector)getModel().getSelectablesAt(anEvent.getPoint());
						if (selectables.size() > 0) {
							Selectable selected = null;
							if(selectables.size() == 1){
								//we have a winner!!
								selected = (Selectable)selectables.get(0); 
							}
							else{
								//have to choose beetween many
								SelectItemDialog chooser = new SelectItemDialog(selectables,false);
								chooser.setVisible(true);
								if(chooser.getReturnState() == OkCancelJDialog.OK_RETURN_STATE){
									selected = (Selectable)chooser.getSelectedItem();
								}
							}
							if(selected != null){
								if (selected instanceof Selectable) {
									//add code here for catching the right click on link
									setLastSelectableClicked(selected);								
									getPopupMenu(selected).show(AbstractModelEditor.this, anEvent.getX(), anEvent.getY());
								}
								else{
									//What a hell had happened?????!!!
									//ERROR
									throw new RuntimeException("Case not contempled at GraphEditor 45654654");
								}
								//we have changed the model, update
								setChanged(true);
							
							}
						}
						else {
							//no items. MenuTitle!!!
							getPopupMenuTitle().show(AbstractModelEditor.this,anEvent.getX(),anEvent.getY());
							setChanged(true);
							setLastSelectableClicked(null);
						}
					}
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		public void mousePressed(MouseEvent anEvent) {
			if (isAlive() && anEvent.getButton() == MouseEvent.BUTTON1) {
				Collection layoutables = getModel().getDragablesAt(anEvent.getPoint());
				if (layoutables.size() > 0) {
					OnePointLayoutable clickedOn = (OnePointLayoutable) layoutables.iterator().next();
					if (clickedOn.isSelected()) {
						//clicking on a selected unit.
						//prepare to drag
						setDragItem(clickedOn);
						setElasticStartLocation(null);
						setElasticEndLocation(null);
					}
					else {
						//clicking on a not selected unit 
						//preparing to make a link
						setDragItem(null);
						setElasticStartLocation(anEvent.getPoint());
						setElasticEndLocation(anEvent.getPoint());
						setMaxLinkCreationDistanceReached(anEvent.getPoint());
						//System.err.println("Link!!" + getElasticStartLocation() + " " + getElasticEndLocation());
					}
				}
				else {
					//clicking nowhere.
					//not dragging nor linking
					setDragItem(null);
					setElasticStartLocation(null);
					setElasticEndLocation(null);
				}
			}
		}

		public void mouseReleased(MouseEvent anEvent) {
			if (isAlive() && anEvent.getButton() == MouseEvent.BUTTON1) {
				Collection layoutables = getModel().getDragablesAt(anEvent.getPoint());
				if (layoutables.size() > 0) {
					//releasing on a Dragable
					if (getDragItem() == null && getElasticStartLocation() != null && getElasticEndLocation() != null && isLinkCreationDistanceReached()) {
						//linking
						getModel().addLink(getModel().createNewLink(getElasticStartLocation(),anEvent.getPoint(),getMaxLinkCreationDistanceReached(),getLinkType()));						
						setElasticStartLocation(null);
						setElasticEndLocation(null);
						setMaxLinkCreationDistanceReached(null);
						
					}
					else if (getDragItem() != null) {
						//were dragging
						//stop dragging
						setDragItem(null);
					}
				}
				else {
					//releasing nowhere
					//dragging or linking, stop doing so				
					setDragItem(null);
					setElasticStartLocation(null);
					setElasticEndLocation(null);
				}
				setChanged(true);
			}
		}
		public void mouseEntered(MouseEvent event) {
		}
		public void mouseExited(MouseEvent event) {
		}

	}

	/**
	 * The tree with the Units was singleclicked with the right button
	 */
	public void componentsTreeRightClicked(Component component, Point here) {
		//get the selected node
		TreePath treePath = getStatesTree().getPathForLocation(here.x, here.y);
		if (treePath != null) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();

			//if the node is a Selectable...
			if (selectedNode.getUserObject() instanceof Selectable) {
				this.setLastSelectableClicked((Selectable) selectedNode.getUserObject());				
				JPopupMenu menu = getPopupMenu((Selectable) selectedNode.getUserObject());
				if(menu != null){
					menu.show(component, here.x, here.y);
				}

			}


		}
		
	}

	
	/**
	 * The tree with the Units was doubleclicked
	 * 
	 */
	public void componentsTreeDoubleClicked(Component component, Point here) {
		//get the selected node
		TreePath treePath = getStatesTree().getPathForLocation(here.x, here.y);
		if (treePath != null) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();

			//if the node is a Selectable...
			if (selectedNode.getUserObject() instanceof Selectable) {
				this.setLastSelectableClicked((Selectable) selectedNode.getUserObject());
				showProperties((Selectable) selectedNode.getUserObject());				
			}

		}
	}

	public boolean saveFile() throws IOException {
		
		String errors = this.getModel().checkModelForSave("this");
		if(errors != null && !errors.equals("")){
			(new InformDialog(errors,null)).setVisible(true);
			return false;
		}
		
		if (getModelFileName() == null ) {
			File fullpath = askFileName();
			if(fullpath == null){
				return false;
			}
			
			this.getModel().setActualPath(fullpath.getParentFile());
			setModelFileName(new File(fullpath.getName()));
			if (getModelFileName() == null) {
				//still null?
				//No file selected! ByeBye
				return false;
			}

		}
		File fullFilename = new File(this.getModel().getActualPath() + File.separator + getModelFileName());
		if(!fullFilename.exists()){
			fullFilename.createNewFile();
		}
		try {
			File djfFile =fullFilename;
			CommentBufferedWriter writer = new CommentBufferedWriter(new FileWriter(djfFile));
			this.getModel().saveTo(writer);
			this.setChanged(false);
			getMainFrame().setStatusText( ""+fullFilename + " saved.");
			writer.close();
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			getMainFrame().setStatusText("Error Saving" + getModelFileName());
			return false;
		}
		
	}

	public abstract void sonIsReturningFromCall(MainFrame son);




	
	/**
	 * Returns the dragState.
	 * @return StateUnit
	 */
	protected OnePointLayoutable getDragItem() {
		return dragItem;
	}

	/**
	 * Sets the dragState.
	 * @param dragState The dragState to set
	 */
	protected void setDragItem(OnePointLayoutable dragItem) {
		this.dragItem = dragItem;
	}

	

	/**
	 * Returns the elasticEndLocation.
	 * @return Point
	 */
	public Point getElasticEndLocation() {
		return elasticEndLocation;
	}

	/**
	 * Sets the elasticEndLocation.
	 * @param elasticEndLocation The elasticEndLocation to set
	 */
	public void setElasticEndLocation(Point elasticEndLocation) {
		this.elasticEndLocation = elasticEndLocation;
	}
	/**
		 * contorls if the ModelEditor reacts against external events
		 */
	protected boolean alive;
	protected AbstractModel model;
	protected void setModel(AbstractModel aGraph) {
		this.model = aGraph;
		
		//use this to control the canvas color
		if (aGraph == null) {
			this.setBackground(Color.LIGHT_GRAY);
			this.setAlive(false);
		}
		else {
			this.setBackground(Color.WHITE);
			this.setAlive(true);
			this.getModel().rebuildTree();
			this.setModelFileName(aGraph.getModelFileName());			
		}
		
		
	}
	/**
		 * Returns the alive.
		 * @return boolean
		 */
	protected boolean isAlive() {
		return alive;
	}
	/**
		 * Sets the alive.
		 * @param alive The alive to set
		 */
	protected void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * @return
	 */
	public boolean isLinkCreationDistanceReached() {
		return getElasticStartLocation().distance(getMaxLinkCreationDistanceReached()) > Integer.parseInt(gui.Constants.getInstance().getString("link.distance","40"));
	}

	/**
	 * @return
	 */
	public Point getMaxLinkCreationDistanceReached() {
		return maxLinkCreationDistanceReached;
	}

	/**
	 * @param b
	 */
	public void setMaxLinkCreationDistanceReached(Point distance) {
		maxLinkCreationDistanceReached = distance;
	}


	/**
		 * Shows the property dialog of an Port
		 */
	protected void showPortProperties(AbstractPort aPort) {
		//CoupledPortPropertyDialog aPortPropertyDialog = new CoupledPortPropertyDialog(mainFrame, "Port Properties", true, aPort);
		PortDialog aPortPropertyDialog = new PortDialog(getMainFrame(),"Port Properties",true,aPort);
	
		aPortPropertyDialog.setLocationRelativeTo(this);
		aPortPropertyDialog.setVisible(true);
	
		if (aPortPropertyDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
			setChanged(true);
		}
	
	}


	protected String linkType = "int";
	/**
		 * Returns the linkType.
		 * @return String
		 */
	protected String getLinkType() {
		return linkType;
	}
	/**
	 * Returns the mainFrame.
	 * @return MainFrame
	 */
	protected MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Sets the mainFrame.
	 * @param mainFrame The mainFrame to set
	 */
	protected void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}	
	/**
	 * Returns the lastSelectableClicked.
	 * @return Unit
	 */
	public Selectable getLastSelectableClicked() {
		return lastSelectableClicked;
	}

	/**
	 * Sets the lastSelectableClicked.
	 * @param lastSelectableClicked The lastSelectableClicked to set
	 */
	protected void setLastSelectableClicked(Selectable lastSelectableClicked) {
		this.lastSelectableClicked = lastSelectableClicked;
	}


	/**
	 * @return
	 */
	public boolean exportFile() {
		boolean retr = getModel().exportfile();
		if(retr){
			//possible new export file. Need to save it
			setChanged(true,false);
		}
		return retr;
	}	
}
