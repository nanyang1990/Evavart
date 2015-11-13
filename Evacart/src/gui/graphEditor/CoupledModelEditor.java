package gui.graphEditor;

import gui.AbortProcessException;
import gui.AddInitialParameterDialog;
import gui.CoupledLinkPortsConnectionDialog;
import gui.CoupledUnitPropertyDialog;
import gui.DeleteInitialParameterDialog;
import gui.InformDialog;
import gui.MainFrame;
import gui.OkCancelJDialog;
import gui.cdd.Translator;
import gui.image.Repository;
import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
import gui.javax.util.Identifiable;
import gui.javax.util.Selectable;
import gui.model.Expression;
import gui.model.link.AbstractCoupledLink;
import gui.model.link.AbstractLink;
import gui.model.model.AbstractModel;
import gui.model.model.AtomicModel;
import gui.model.model.CoupledModel;
import gui.model.model.EditableAtomicModel;
import gui.model.model.EditableCoupledModel;
import gui.model.port.AbstractPort;
import gui.model.port.CoupledPort;
import gui.model.unit.AbstractModelUnit;
import gui.model.unit.AbstractUnit;
import gui.model.unit.AtomicModelUnit;
import gui.model.unit.Editable;
import gui.model.unit.EditableAtomicModelUnit;
import gui.model.unit.EditableCoupledModelUnit;
import gui.model.unit.Initializable;
import gui.representation.ImageFactory;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class CoupledModelEditor extends AbstractModelEditor implements ActionListener{
	
	//protected AssignPortToLinkEvent theassignPortToLinkEvent;
	private AbstractModelUnit explodedUnit;

	private boolean changed = false;

	public CoupledModelEditor(MainFrame theFrame) {
		super();
		this.setMainFrame(theFrame);

		//theassignPortToLinkEvent = new AssignPortToLinkEvent();
		enableEventHandler();
	}

	class AddInitialParameterEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AtomicModelUnit aModelUnit = (AtomicModelUnit)getLastSelectableClicked();

			if (aModelUnit != null) {
				AddInitialParameterDialog theaddActionDialog = new AddInitialParameterDialog(getMainFrame());
				theaddActionDialog.setSize(180, 120);
				theaddActionDialog.setLocationRelativeTo(CoupledModelEditor.this);
				theaddActionDialog.setVisible(true);

				if (theaddActionDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
					if (theaddActionDialog.getNewInitialParameter() != null){
						((AtomicModel)aModelUnit.getModel()).getInitialParameters().add(theaddActionDialog.getNewInitialParameter());
						setChanged(true);
					}
				}
			}
		}
	}
	class DeleteInitialParameterEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AtomicModelUnit aModel = (AtomicModelUnit)getLastSelectableClicked();

			if (aModel != null) {
				DeleteInitialParameterDialog theaddActionDialog = new DeleteInitialParameterDialog(getMainFrame(),(AtomicModel)aModel.getModel());
				theaddActionDialog.setSize(220, 90);
				theaddActionDialog.setLocationRelativeTo(CoupledModelEditor.this);
				theaddActionDialog.setVisible(true);

				if (theaddActionDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
					if (theaddActionDialog.getselectedObject() != null){
						((AtomicModel)aModel.getModel()).getInitialParameters().remove(theaddActionDialog.getselectedObject());
						setChanged(true);
					}
				}
			}
		}
	}

	
	class ReloadUnitEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				reloadUnit((AbstractModelUnit)getLastSelectableClicked());
			}
			catch (Exception ex) {
				(new InformDialog(ex.toString(),ex)).setVisible(true);
			}
		}
	}

	class ExplodeEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {

				explodeUnit((AbstractModelUnit)getLastSelectableClicked());
			}
			catch (Exception ex) {
				(new InformDialog(ex.toString(),ex)).setVisible(true);

			}
		}
	}

	class SelectImageEventHandler implements ActionListener {
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
					
					//get the node
					 ((Layoutable)getLastSelectableClicked()).setImage(ImageFactory.newImage(imageFileName));
					
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

	public AbstractModel getModel() {
		return model;
	}

	/**
				* Returns the changed.
				* @return boolean
				*/
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Sets the changed.
	 * @param changed The changed to set
	 */
	public void setChanged(boolean changed, boolean rebuildTree) {
		//System.err.println(changed);
		this.changed = changed;
		if (changed) {
			getMainFrame().getMainTabbedPane().repaint();
			//getMainFrame().getCoupledUnitsTree().setSelectionPath(getMainFrame().getCoupledClassesTree().getSelectionPath());
			getMainFrame().getCoupledDataPanel().repaint();
			if (rebuildTree) {
				//Thread.dumpStack();		

				/*
				 * there is a bug reported that makes the nodes not to display
				 * the full name. Instead, they are showing "a part of the n..."
				 * updateUI is a good workarround
				 * Bug reported: http://developer.java.sun.com/developer/bugParade/bugs/4105239.html
				 */
				this.getMainFrame().getCoupledUnitsTree().updateUI();
				this.getMainFrame().getCoupledUnitsTree().repaint();
			}
		}

	}

	/**
	 * A coupledUnit was double-clicked at the tree
	 */
	/*
	protected void statesTreeUnitDoubleClicked(AbstractUnit anUnit) {
		showUnitProperties(anUnit);
	}
	*/
	/**
	 * Shows the property dialog of an Unit
	 */
	protected void showUnitProperties(AbstractUnit anUnit) {
		CoupledUnitPropertyDialog aUnitPropertyDialog = new CoupledUnitPropertyDialog(getMainFrame(), "Model Properties", true, anUnit);

		aUnitPropertyDialog.setSize(240, 100);
		aUnitPropertyDialog.setLocationRelativeTo(this);
		aUnitPropertyDialog.setVisible(true);

		if (aUnitPropertyDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
			setChanged(true);
		}

	}

	/**
	 * Shows the LinkPortsConnectionDialog and assigns ports to the link
	 */
	protected void showLinkProperties(AbstractLink link) {
		AbstractCoupledLink coupledLink = (AbstractCoupledLink)link;
		CoupledLinkPortsConnectionDialog linkPortsDialog = new CoupledLinkPortsConnectionDialog(getMainFrame(), "Port@Link", true);
		linkPortsDialog.setLink((AbstractCoupledLink)coupledLink);
		linkPortsDialog.setLocationRelativeTo(getMainFrame());
		linkPortsDialog.setVisible(true);
		if (linkPortsDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
			Expression from = linkPortsDialog.getFromSelection();
			Expression to = linkPortsDialog.getToSelection();
			coupledLink.setStartExpression(from);
			coupledLink.setEndExpression(to);
			this.setChanged(true);
		}
	}

	public void dirtyHandler() throws AbortProcessException, IOException {
		int saveResult = -1;
		if (this.alive && this.isChanged()) {
			saveResult = JOptionPane.showConfirmDialog(this, "Save changes in Coupled Model?", "DEVS Graph Editor", JOptionPane.YES_NO_CANCEL_OPTION);
			switch (saveResult) {
				case JOptionPane.YES_OPTION :
					boolean saved = this.saveFile();
					if (saved) {
						break;
					}
					else {
						//oops something happend.
						//User must know it because saveFile() should inform
						//I just abort
						throw new AbortProcessException();
					}
				case JOptionPane.NO_OPTION :
					//dont want to save. Ok, do not save changes.

					//keep the isChanged status
					//the dirty handler is
					//not marking the object as not dirty, cause it is not
					//applicable for all cases. mark it here.
					//this.setChanged(false);
					break;
				case JOptionPane.CANCEL_OPTION :
				case JOptionPane.CLOSED_OPTION :
					//abort process
					throw new AbortProcessException();
				default :
					//other?????
					throw new RuntimeException("ERROR 22433");
			}
		}
	}

	protected String getFileExtension() {
		return "GCM";
	}



	protected void clearTreeNodes() {
		getMainFrame().getCoupledUnitsTree().clearCoupledTreeNodes();
	}

	public void loadFrom(File path, File filename) throws Exception {

		//create a new one
		newGraph();

		getModel().loadFrom(path, filename);

		//rebuild now
		this.setChanged(true);

		//since the graph is just loaded...
		this.setChanged(false);

		setModelFileName(filename);

		getMainFrame().setStatusText("opened " + filename);
	}


	protected AbstractModel createNewGraph() {
		return new EditableCoupledModel(getMainFrame());
	}


	protected JPopupMenu getPopupMenuLink(Selectable selected) {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem propertiesItem = new JMenuItem("Properties");
		popupMenu.add(propertiesItem);
		propertiesItem.addActionListener(new SelectablePropertyEventHandler());
		popupMenu.setInvoker(this);
		return popupMenu;
	}

	protected JPopupMenu getPopupMenuUnit(Selectable selected) {
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setInvoker(this);
		
		JMenuItem selectImageItem = new JMenuItem("Select Image");
		popupMenu.add(selectImageItem);
		selectImageItem.addActionListener(new SelectImageEventHandler());
			
		JMenuItem propertyItem = new JMenuItem("Properties");
		popupMenu.add(propertyItem);
		propertyItem.addActionListener(new SelectablePropertyEventHandler());
		
		if(selected instanceof Editable){		

			JMenuItem explodeItem = new JMenuItem("Explode");
			JMenuItem reloadItem = new JMenuItem("Reload");

		
			popupMenu.add(explodeItem);
			popupMenu.add(reloadItem);

			
			explodeItem.addActionListener(new ExplodeEventHandler());
			reloadItem.addActionListener(new ReloadUnitEventHandler());

		}
		if(selected instanceof Initializable){
			JMenuItem addInitialParamItem = new JMenuItem("Add Initial parameter");
			JMenuItem deleteInitialParamItem = new JMenuItem("Delete Initial parameter");

		
			popupMenu.add(addInitialParamItem);
			popupMenu.add(deleteInitialParamItem);

			
			addInitialParamItem.addActionListener(new AddInitialParameterEventHandler());
			deleteInitialParamItem.addActionListener(new DeleteInitialParameterEventHandler());
		
		}

		return popupMenu;
	}

	protected JPopupMenu getPopupMenuPort(Selectable selected) {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem propertyItem = new JMenuItem("Properties");
		JMenuItem selectImageItem = new JMenuItem("Select Image");
		popupMenu.add(propertyItem);
		popupMenu.add(selectImageItem);
		popupMenu.setInvoker(this);
		propertyItem.addActionListener(new SelectablePropertyEventHandler());
		selectImageItem.addActionListener(new SelectImageEventHandler());

		return popupMenu;
	}

	protected JPopupMenu getPopupMenuTitle() {
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem setTitleItem = new JMenuItem("Model Title");

		JMenuItem selectBackgroundItem = new JMenuItem("Background");

		popupMenu.add(setTitleItem);
		popupMenu.add(selectBackgroundItem);
		
		popupMenu.setInvoker(this);
		
		setTitleItem.addActionListener(new ModelTitleSetEventHandler());
		selectBackgroundItem.addActionListener(new SelectBackgroundEventHandler());
		return popupMenu;
	}

	/**
	 * @see gui.model.Model#getStatesTreeModel()
	 */
	protected JTree getStatesTree() {
		return getMainFrame().getCoupledUnitsTree();
	}

	/**
	 * Explode a unit to define it in a new Model
	 * Sory but since the ports of the exploded unit should be modificated, it is necesary to
	 * unatach the actual ports from all the Links.
	 */
	public void explodeUnit(AbstractModelUnit unit) throws Exception {
		//remember the unit exploded
		this.setExplodedUnit(unit);

		//sory but since the ports of the exploded unit should be modificated, it is necesary to
		//unatach the actual ports from all the Links.

		Iterator links = this.getModel().getLinks().iterator();
		while (links.hasNext()) {
			((AbstractLink)links.next()).unlink(unit.getPorts());
		}

		//create a new MainFrame to define the Unit
		MainFrame theNewMainFrame = new MainFrame();

		//theNewMainFrame.setDefaultCloseOperation(mainFrame.DO_NOTHING_ON_CLOSE);
		//set this as the crearter
		theNewMainFrame.setCreator(getMainFrame());

		//set it up
		if (unit instanceof EditableCoupledModelUnit) {
			theNewMainFrame.getCoupledModelEditor().newGraph();
		    theNewMainFrame.disableAtomicGraphEditor();


			unit.getModel().setMainFrame(theNewMainFrame);
			theNewMainFrame.getCoupledModelEditor().setModel(unit.getModel());

			//the model of this unit may be shared by other units and it may be changed elsewhere.
			theNewMainFrame.getCoupledModelEditor().getModel().reload();
		}
		else if (unit instanceof EditableAtomicModelUnit) {
			theNewMainFrame.getAtomicGraphEditor().newGraph();
		    theNewMainFrame.disableCoupledGraphEditor();


			unit.getModel().setMainFrame(theNewMainFrame);
			theNewMainFrame.getAtomicGraphEditor().setModel(unit.getModel());

			//the model of this unit may be shared by other units and it may be changed elsewhere.
			theNewMainFrame.getAtomicGraphEditor().getModel().reload();

		}


		//my size, son's size
		theNewMainFrame.setSize(this.getMainFrame().getSize());

		//my position, son's position
		theNewMainFrame.setLocation(this.getMainFrame().getLocation());

		//hide this editor
		getMainFrame().setVisible(false);

		//show the new one
		theNewMainFrame.setVisible(true);

		//now wait until theNewMainFrame is closed so it calls this to resume operation.

	}

	
	/**
	 * Reload a unit from it define file.
	 * Sory but since the ports of the unit should be modificated, it is necesary to
	 * unatach the actual ports from all the Links.
	 */
	protected void reloadUnit(AbstractModelUnit unit) throws Exception {

		//sory but since the ports of the exploded unit should be modificated, it is necesary to
		//unatach the actual ports from all the Links.

		Iterator links = this.getModel().getLinks().iterator();
		while (links.hasNext()) {
			((AbstractLink)links.next()).unlink(unit.getPorts());
		}

		unit.getModel().reload();

		//now, rebuild this tree
		this.getModel().rebuildTree();

	}

	/**
	 * @see gui.graph.ModelEditor#sonIsReturningFromCall(MainFrame)
	 */
	public void sonIsReturningFromCall(MainFrame son) {
		//get the unit that has been exploded and set the new model.
		//this makes the unit to try to rebuild the tree of nodes.
		//The tree must the one where we edited the unit.
		//this is OK. we dont  want to let the model rebuild the tree of nodes of
		//this EditableCoupledModel so, first set the graph and then set the mainframe

	    getExplodedUnit().setModel(son.getSelectedGraphEditor().getModel());
		getExplodedUnit().getModel().setMainFrame(this.getMainFrame());
		
		
		//now, rebuild this tree
		this.getModel().rebuildTree();

		//now change de visibility.
		this.getMainFrame().setVisible(true);
		son.setVisible(false);
		son.dispose();

	}

	/**
	 * @see gui.graph.ModelEditor#canvasDoubleClicked(Point)
	 */
	protected void canvasDoubleClicked(Point place) {
		DefaultMutableTreeNode aSelectedNode = (DefaultMutableTreeNode)getMainFrame().getCoupledClassesTree().getLastSelectedPathComponent();
		if (aSelectedNode != null) {
			Object selectedObject = aSelectedNode.getUserObject();
			if (selectedObject instanceof AbstractModel) {
				AbstractUnit theUnit = getModel().createNewUnit((AbstractModel)selectedObject, place);
				getModel().addUnit(theUnit);
			}
			else if (selectedObject instanceof AbstractPort) {
				AbstractPort model = (AbstractPort)selectedObject;
				int portId = getModel().getSequence().next();
				CoupledPort port2Add = new CoupledPort("Port" + portId, model.getInOrOut(), model.getPortType(), "" + portId);
				port2Add.setLocation(place);
				getModel().addPort(port2Add);
			}
			else {
				//do nothing
			}
		}

	}

	/**
	 * @see gui.graphEditor.ModelEditor#addPort()
	 */
	public void addCoupledUnit() {
		//Have to create a new empty Unit.
		//create it in the middle of the graph.
		AbstractModel model = new EditableCoupledModel();
		model.setModelName("newCoupledModel");
		AbstractUnit aNewUnit = getModel().createNewUnit(model, new Point(30, 30));

		this.getModel().addUnit(aNewUnit);
		this.setChanged(true, true);
	}

	/**
	 * @see gui.graphEditor.ModelEditor#addPort()
	 */
	public void addAtomicUnit() {
		//Have to create a new empty Unit.
		//create it in the middle of the graph.
		AbstractModel model = new EditableAtomicModel();
		model.setModelName("newAtomicModel");
		AbstractUnit aNewUnit = getModel().createNewUnit(model, new Point(30, 30));

		this.getModel().addUnit(aNewUnit);
		this.setChanged(true, true);
	}

	/**
	 * Returns the explodedUnit.
	 * @return AbstractUnit
	 */
	public AbstractModelUnit getExplodedUnit() {
		return explodedUnit;
	}

	/**
	 * Sets the explodedUnit.
	 * @param explodedUnit The explodedUnit to set
	 */
	public void setExplodedUnit(AbstractModelUnit explodedUnit) {
		this.explodedUnit = explodedUnit;
	}

	/**
	 * @see gui.graphEditor.ModelEditor#statesTreePortDoubleClicked(Port)
	 */
	/*
	protected void statesTreePortDoubleClicked(Port aPort) {
		showPortProperties(aPort);
	}
	*/
	/**
	 * @see gui.graphEditor.ModelEditor#getDataPanel()
	 */
	protected JTextArea getDataPanel() {
		return getMainFrame().getCoupledDataPanel();
	}

	/* (non-Javadoc)
	 * @see gui.graphEditor.ModelEditor#getPopupMenu(gui.javax.util.Selectable)
	 */
	protected JPopupMenu getPopupMenu(Selectable selectedNode) {
		//if the node is a Link...
		if (selectedNode instanceof AbstractLink) {
			return getPopupMenuLink(((AbstractLink)selectedNode));
		}

		//if the node is a State...
		if (selectedNode instanceof AbstractUnit) {
			return getPopupMenuUnit((AbstractUnit)selectedNode);
		}

		if (selectedNode instanceof CoupledPort) {
			return getPopupMenuPort((CoupledPort)selectedNode);
		}

		return null;
	}
	/**
	 * A Link was double-clicked at the tree
	 */
	/*
	protected void statesTreeLinkDoubleClicked(AbstractLink abstractLink) {
		showLinkProperties(abstractLink);
	}
	*/
	/* (non-Javadoc)
	 * @see gui.graphEditor.ModelEditor#statesTreeDoubleClicked(gui.javax.util.Selectable)
	 */
	protected void showProperties(Selectable selectedNode) {
		//if the node is a Link...
		if (selectedNode instanceof AbstractLink) {
			showLinkProperties((AbstractLink)selectedNode);
		}

		//if the node is a State...
		if (selectedNode instanceof AbstractUnit) {
			showUnitProperties((AbstractUnit)selectedNode);
		}

		if (selectedNode instanceof AbstractPort) {
			showPortProperties((AbstractPort)selectedNode);
		}
	}


	/**
	*	the file should be an .ma with a Coupled Model or an cdd with an Atomic Model 
	*	or a cpp with the definition of default registered models.
	* @param aReader
	* @param aFileName
	* @throws IOException
	*/
	public void importModelsFromFile(File modelFile) throws IOException {
		Iterator models = Translator.importFrom(modelFile).iterator();
		while(models.hasNext()){
			getMainFrame().getCoupledClassesTree().insertNodeInto((Identifiable)models.next());
		}
	}

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	 
	//Chiril Chidisiuc: fixed bug by introducting the "checked" parameter(
	//BUG: if showPorts was unchecked and the
	//exploded view was then closed, 
	//the next time the exploded view was shown, 
	//the showPorts checkbox inverted the response...)
    public void actionPerformed(ActionEvent event) {
	    String cmd = event.getActionCommand();
	    boolean checked = ((JCheckBox) event.getSource()).isSelected(); //Chiril Chidisiuc: added 2006-08-10
	    if("showPorts".equals(cmd)){
	        if(this.getModel() != null){
	        	((CoupledModel)this.getModel()).setShowPorts(checked); //Chiril Chidisiuc: added 2006-08-10
//	        	((CoupledModel)this.getModel()).setShowPorts(!((CoupledModel)this.getModel()).isShowPorts()); //Chiril Chidisiuc: old implementation with bug
	            setChanged(true,false);
	        }
	    }
	    else{
	        InformDialog.showError("action command not recognized",null);
	    }
    }


}