package gui.graphEditor;

import gui.AbortProcessException;
import gui.AddActionDialog;
import gui.AddExpressionAndValueDialog;
import gui.AddPortAndValueDialog;
import gui.AtomicUnitPropertyDialog;
import gui.DeleteActionDialog;
import gui.DeleteExpressionAndValueDialog;
import gui.DeleteVariableDialog;
import gui.InformDialog;
import gui.MainFrame;
import gui.OkCancelJDialog;
import gui.VariableDialog;
import gui.javax.util.Selectable;
import gui.model.Expression;
import gui.model.ExpressionAndValue;
import gui.model.Variable;
import gui.model.link.AbstractAtomicLink;
import gui.model.link.AbstractLink;
import gui.model.link.AtomicExternalLink;
import gui.model.link.AtomicInternalLink;
import gui.model.model.AbstractModel;
import gui.model.model.AtomicModel;
import gui.model.model.EditableAtomicModel;
import gui.model.model.VoidModel;
import gui.model.port.AbstractPort;
import gui.model.unit.AbstractUnit;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTree;

//It is not any more a DialogClientInterface since it doesnt need to implemente 
//the dialogFinished and dialogCanceled methods.
//And so...What has a dialogFinished and canceled have to do in an AtomicModelEditor????
//public class AtomicModelEditor extends JPanel implements DialogClientInterface {

public class AtomicModelEditor extends AbstractModelEditor implements ActionListener {
	protected AbstractModel createNewGraph() {
		return new EditableAtomicModel(getMainFrame());
	}

	private boolean changed = false;
	public int selection = -1;
	
	//Chiril Chidisiuc: fixed bug by introducting the "checked" parameter(
	//BUG: if showExpressions and showActions were 
	//unchecked and the exploded view was then closed, 
	//the next time the exploded view was shown, 
	//the showExpressions and showActions 
	//checkboxes inverted the response...)
	public void actionPerformed(ActionEvent event) {
	    String cmd = event.getActionCommand();
	    if("ext".equals(cmd) || "int".equals(cmd)){
	        setLinkType(cmd);    
	    }
	    else if("showExpressions".equals(cmd)){
	    	boolean checked = ((JCheckBox) event.getSource()).isSelected(); //Chiril Chidisiuc: added 2006-08-11
	        if(this.getModel() != null){
	        	((AtomicModel)this.getModel()).setShowExpressions(checked); //Chiril Chidisiuc: added 2006-08-11
//	            ((AtomicModel)this.getModel()).setShowExpressions(!((AtomicModel)this.getModel()).isShowExpressions()); //Chiril Chidisiuc: this is OLD implementation (with  bug)
	            setChanged(true,false);
	        }
	    }
	    else if("showActions".equals(cmd)){
	    	boolean checked = ((JCheckBox) event.getSource()).isSelected(); //Chiril Chidisiuc: added 2006-08-11
	        if(this.getModel() != null){
	        	((AtomicModel)this.getModel()).setShowActions(checked); //Chiril Chidisiuc: added 2006-08-11
//	            ((AtomicModel)this.getModel()).setShowActions(!((AtomicModel)this.getModel()).isShowActions()); //Chiril Chidisiuc: this is OLD implementation (with  bug)
	            setChanged(true,false);
	        }
	    }
	    else{
	        InformDialog.showError("action command not recognized",null);
	    }
	    
		
	}

	class DeleteExpressionAndValueEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AbstractAtomicLink aLink = (AbstractAtomicLink)getLastSelectableClicked();

			if (aLink != null) {

				DeleteExpressionAndValueDialog thedeleteExpressionAndValueDialog = new DeleteExpressionAndValueDialog(null, "Delete action", true, aLink);
				thedeleteExpressionAndValueDialog.setSize(180, 80);
				thedeleteExpressionAndValueDialog.setLocationRelativeTo(AtomicModelEditor.this);
				thedeleteExpressionAndValueDialog.setVisible(true);
				if (thedeleteExpressionAndValueDialog.getReturnState() == DeleteExpressionAndValueDialog.DELETE_RETURN_STATE) {
					if (thedeleteExpressionAndValueDialog.getselectedObject() != null) {
						aLink.deleteExpressionAndValue((gui.model.ExpressionAndValue)thedeleteExpressionAndValueDialog.getselectedObject());
						setChanged(true);
					}
				}
			}

		}
	}
	class AddExpressionAndValueIntEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AbstractAtomicLink aLink = (AbstractAtomicLink)getLastSelectableClicked();

			addExpressionAndValueInt(aLink);
		}
	}

	private void addExpressionAndValueInt(AbstractAtomicLink aLink) {

		AddPortAndValueDialog theaddPortAndValueDialog = new AddPortAndValueDialog(null, "Add port & value", true, null, getModel().getPorts());
		theaddPortAndValueDialog.setSize(200, 150);
		theaddPortAndValueDialog.setLocationRelativeTo(AtomicModelEditor.this);
		theaddPortAndValueDialog.setVisible(true);

		if (theaddPortAndValueDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
			aLink.addExpressionAndValue(theaddPortAndValueDialog.getExpression(),theaddPortAndValueDialog.getValue());
			setChanged(true);
		}
	}

	class AddExpressionAndValueExtEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AbstractAtomicLink aLink = (AbstractAtomicLink)getLastSelectableClicked();
			addExpressionAndValueExt(aLink);
		}
	}

	private void addExpressionAndValueExt(AbstractAtomicLink aLink) {
		Vector itemsToShow = new Vector();
		Iterator ports = getModel().getInPorts().iterator();
		while (ports.hasNext()) {
			itemsToShow.add(((AbstractPort)ports.next()).getName());
		}
		Iterator vars = ((EditableAtomicModel)getModel()).getVariables().iterator();
		while (vars.hasNext()) {
			itemsToShow.add(((Variable)vars.next()).getName());
		}

		AddExpressionAndValueDialog theaddExpressionAndValueDialog = new AddExpressionAndValueDialog(null, "Port and value properties", true, itemsToShow);
		theaddExpressionAndValueDialog.setSize(300, 200);
		theaddExpressionAndValueDialog.setLocationRelativeTo(AtomicModelEditor.this);
		if (!aLink.getExpressionsAndValues().isEmpty()) {
			ExpressionAndValue aExpressionAndValue = (ExpressionAndValue)aLink.getExpressionsAndValues().firstElement();
			theaddExpressionAndValueDialog.setExpressionAndValue(aExpressionAndValue);
		}
		theaddExpressionAndValueDialog.setVisible(true);

		if (theaddExpressionAndValueDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
			if (!aLink.getExpressionsAndValues().isEmpty()) {
				aLink.getExpressionsAndValues().clear();
			}
			Expression expToAdd = theaddExpressionAndValueDialog.getExpression();
			String value = theaddExpressionAndValueDialog.getValue();
			aLink.addExpressionAndValue(expToAdd, value);
			setChanged(true);
		}

	}
	class InitialEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//System.out.println("aGraph.initialState = aState");
			 ((EditableAtomicModel)getModel()).setInitialUnit((AbstractUnit)getLastSelectableClicked());
		}
	}
	class DeclareVariableEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Variable aNewVar = new Variable();
			aNewVar.setUniqueId("" + getModel().getSequence().next());

			VariableDialog thedeclareVariableDialog = new VariableDialog(getMainFrame(), "Declare variable", true, aNewVar);
			thedeclareVariableDialog.setLocationRelativeTo(AtomicModelEditor.this);
			thedeclareVariableDialog.setVisible(true);

			if (thedeclareVariableDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
				((EditableAtomicModel)getModel()).addVariable(thedeclareVariableDialog.getVariable());
				setChanged(true);
			}

		}
	}

	class DeleteVariableEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			DeleteVariableDialog thedeleteDialog = new DeleteVariableDialog(getMainFrame(), "Delete variable", true, ((EditableAtomicModel)getModel()));
			thedeleteDialog.setSize(180, 80);
			thedeleteDialog.setLocationRelativeTo(AtomicModelEditor.this);
			thedeleteDialog.setVisible(true);
			if (thedeleteDialog.getReturnState() == DeleteActionDialog.DELETE_RETURN_STATE) {
				if (thedeleteDialog.getselectedObject() != null) {
					((EditableAtomicModel)getModel()).delete((Variable)thedeleteDialog.getselectedObject());
					setChanged(true);
				}
			}

		}
	}

	class AddActionEventHander implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AbstractAtomicLink aLink = (AbstractAtomicLink)getLastSelectableClicked();

			if (aLink != null) {
				int actionId = getModel().getSequence().next();
				AddActionDialog theaddActionDialog = new AddActionDialog(getMainFrame(), "Actions", true, aLink, "" + actionId);
				theaddActionDialog.setSize(180, 120);
				theaddActionDialog.setLocationRelativeTo(AtomicModelEditor.this);
				theaddActionDialog.setVisible(true);

				if (theaddActionDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
					if (theaddActionDialog.getnewAction() != null) {
						((EditableAtomicModel)getModel()).addAction(theaddActionDialog.getnewAction(), aLink);
						//aLink.addAction();
						setChanged(true);
					}
				}
			}
		}
	}

	class DeleteActionEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AbstractAtomicLink aLink = (AbstractAtomicLink)getLastSelectableClicked();

			if (aLink != null) {

				DeleteActionDialog thedeleteActionDialog = new DeleteActionDialog(getMainFrame(), "Delete action", true, aLink);
				thedeleteActionDialog.setSize(180, 80);
				thedeleteActionDialog.setLocationRelativeTo(AtomicModelEditor.this);
				thedeleteActionDialog.setVisible(true);
				if (thedeleteActionDialog.getReturnState() == DeleteActionDialog.DELETE_RETURN_STATE) {
					if (thedeleteActionDialog.getselectedObject() != null) {
						((EditableAtomicModel)getModel()).deleteAction((gui.model.Action)thedeleteActionDialog.getselectedObject(), aLink);
						setChanged(true);
					}
				}
			}
		}
	}

	public AtomicModelEditor(MainFrame theFrame) {
		super();
		setMainFrame(theFrame);

		enableEventHandler();
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
		this.changed = changed;
		//if the graph changes, repaint it
		if (changed) {
			//Thread.dumpStack();
			this.repaint();
			if (rebuildTree) {
				/*
				 * there is a bug reported that makes the nodes not to display
				 * the full name. Instead, they are showing "a part of the n..."
				 * updateUI is a good workarround
				 * Bug reported: http://developer.java.sun.com/developer/bugParade/bugs/4105239.html
				 */
				this.getMainFrame().getAtomicUnitsTree().updateUI();

				this.getMainFrame().getAtomicUnitsTree().repaint();
			}

		}
	}

	public void dirtyHandler() throws AbortProcessException, IOException {
		int saveResult = -1;
		if (this.isChanged()) {
			saveResult = JOptionPane.showConfirmDialog(this, "Save changes in Atomic Model?", "DEVS Graph Editor", JOptionPane.YES_NO_CANCEL_OPTION);
			switch (saveResult) {
				case JOptionPane.YES_OPTION :
					this.saveFile();
					break;
				case JOptionPane.NO_OPTION :
					//dont want to save. Ok, discard changes.
					this.setChanged(false);
					break;
				case JOptionPane.CANCEL_OPTION :
				case JOptionPane.CLOSED_OPTION :
					//abort process
					throw new AbortProcessException();
				default :
					//other?????
					throw new RuntimeException("ERROR 22323");
			}
		}

	}

	public void loadFrom(File path, File filename) throws Exception {

		//create a new one
		newGraph();

		if (filename != null) {

			this.getModel().loadFrom(path, filename);
		}
		//rebuild now
		this.setChanged(true);

		//since the graph is just loaded...
		this.setChanged(false);

		//atomicPanel.rebuildTreeModel();
		this.setModelFileName(filename);
		getMainFrame().setStatusText("opened " + filename);
	}

	protected String getFileExtension() {
		return "GAM";
	}

	protected void clearTreeNodes() {
		getMainFrame().getAtomicUnitsTree().clearAtomicTreeNodes();

	}

	private JPopupMenu getPopupMenuLink(Selectable selected) {
		if (selected instanceof AtomicInternalLink) {
			return getPopupMenuInternalLink(selected);
		}
		else if (selected instanceof AtomicExternalLink) {
			return getPopupMenuExternalLink(selected);
		}
		else {
			throw new RuntimeException("Type not contempled!!");
		}
	}
	private JPopupMenu getPopupMenuInternalLink(Selectable selected) {
		JPopupMenu popupMenuLink = new JPopupMenu();

		JMenuItem addExpressionAndValueItem = new JMenuItem("Add port & value...");
		JMenuItem deleteExpressionAndValueItem = new JMenuItem("Delete port & value...");
		JMenuItem addActionItem = new JMenuItem("Add action...");
		JMenuItem deleteActionItem = new JMenuItem("Delete action...");

		popupMenuLink.add(addExpressionAndValueItem);
		popupMenuLink.add(deleteExpressionAndValueItem);
		popupMenuLink.add(addActionItem);
		popupMenuLink.add(deleteActionItem);
		popupMenuLink.setInvoker(this);
		popupMenuLink.setInvoker(this);

		addActionItem.addActionListener(new AddActionEventHander());
		deleteActionItem.addActionListener(new DeleteActionEventHandler());
		addExpressionAndValueItem.addActionListener(new AddExpressionAndValueIntEventHandler());
		deleteExpressionAndValueItem.addActionListener(new DeleteExpressionAndValueEventHandler());

		return popupMenuLink;

	}
	private JPopupMenu getPopupMenuExternalLink(Selectable selected) {
		JPopupMenu popupMenuLink = new JPopupMenu();
		JMenuItem addActionItem = new JMenuItem("Add action...");
		JMenuItem deleteActionItem = new JMenuItem("Delete action...");
		JMenuItem propertiesItem = new JMenuItem("Properties...");
		popupMenuLink.add(propertiesItem);
		popupMenuLink.add(addActionItem);
		popupMenuLink.add(deleteActionItem);
		popupMenuLink.setInvoker(this);
		popupMenuLink.setInvoker(this);

		addActionItem.addActionListener(new AddActionEventHander());
		deleteActionItem.addActionListener(new DeleteActionEventHandler());
		propertiesItem.addActionListener(new AddExpressionAndValueExtEventHandler());

		return popupMenuLink;

	}

	protected JPopupMenu getPopupMenuUnit(Selectable selected) {
		JPopupMenu popupMenuState = new JPopupMenu();
		JMenuItem propertyItem = new JMenuItem("Properties");
		JMenuItem setInitialItem = new JCheckBoxMenuItem("Set initial");

		popupMenuState.add(setInitialItem);
		popupMenuState.add(propertyItem);
		popupMenuState.setInvoker(this);

		propertyItem.addActionListener(new AbstractModelEditor.SelectablePropertyEventHandler());
		setInitialItem.addActionListener(new InitialEventHandler());

		//if this unit is the initial....
		if (selected.equals(((EditableAtomicModel)getModel()).getInitialUnit())) {
			setInitialItem.setSelected(true);
		}
		return popupMenuState;
	}

	protected JPopupMenu getPopupMenuTitle() {
		JPopupMenu popupatomicGraphTitleMenu = new JPopupMenu();
		JMenuItem setTitleItem = new JMenuItem("Model title...");
		JMenuItem declareVariableItem = new JMenuItem("Declare variable...");
		JMenuItem deleteVariableItem = new JMenuItem("Delete variable...");
		JMenuItem addPortItem = new JMenuItem("Add port");
		JMenuItem deletePortItem = new JMenuItem("Delete port");
		JMenuItem selectBackgroundItem = new JMenuItem("Background");

		popupatomicGraphTitleMenu.add(setTitleItem);
		popupatomicGraphTitleMenu.add(declareVariableItem);
		popupatomicGraphTitleMenu.add(deleteVariableItem);
		popupatomicGraphTitleMenu.add(addPortItem);
		popupatomicGraphTitleMenu.add(deletePortItem);
		popupatomicGraphTitleMenu.add(selectBackgroundItem);

		setTitleItem.addActionListener(new ModelTitleSetEventHandler());
		declareVariableItem.addActionListener(new DeclareVariableEventHandler());
		deleteVariableItem.addActionListener(new DeleteVariableEventHandler());
		addPortItem.addActionListener(new AddPortToModelEventListener(this, getModel()));
		deletePortItem.addActionListener(new DeletePortToModelEventListener(this, getModel()));
		selectBackgroundItem.addActionListener(new SelectBackgroundEventHandler());

		return popupatomicGraphTitleMenu;

	}

	/**
	 * @see gui.model.Model#getStatesTreeModel()
	 */
	protected JTree getStatesTree() {
		return getMainFrame().getAtomicUnitsTree();
	}

	/**
	 * Shows the property dialog of an Unit
	 */
	protected void showUnitProperties(AbstractUnit anUnit) {

		AtomicUnitPropertyDialog astateUnitDialog = new AtomicUnitPropertyDialog(getMainFrame(), "Atomic Unit properties", true, anUnit);
		astateUnitDialog.setSize(180, 120);
		astateUnitDialog.setLocationRelativeTo(this);
		astateUnitDialog.setVisible(true);

		//It should by better not to pass the unit to the dialog.
		//Instead, pass only the needed data and keep the Unit this side.
		//After dialog closed, inspect the resultStatus and then, modify the Unit and setChanged() if necessary.
		if (astateUnitDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
			setChanged(true);
		}

	}

	/**
	 * @see gui.graph.ModelEditor#sonIsReturningFromCall(MainFrame)
	 */
	public void sonIsReturningFromCall(MainFrame son) {
		//son is never returning in Atomics, meantime
		Thread.dumpStack();
	}

	/**
	 * @see gui.graph.ModelEditor#canvasDoubleClicked(Point)
	 */
	protected void canvasDoubleClicked(Point aPoint) {
		AbstractModel voidModel = new VoidModel();
		voidModel.setModelName("newUnit");
		AbstractUnit theUnit = getModel().createNewUnit(voidModel, aPoint);
		getModel().addUnit(theUnit);
	}

	/**
	 * @see gui.graphEditor.ModelEditor#addPort()
	 */
	public void addCoupledUnit() {
		//late added to AtomicModelEditor.
		//Units are being added by other way.
		//study it and migrate it to here.
	}

	/**
	 * @see gui.graphEditor.ModelEditor#addPort()
	 */
	public void addAtomicUnit() {
		//late added to AtomicModelEditor.
		//Units are being added by other way.
		//study it and migrate it to here.
	}

	/**
	 * Sets the linkType.
	 * @param linkType The linkType to set
	 */
	private void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	/**
	 * @see gui.graphEditor.ModelEditor#statesTreePortDoubleClicked(Port)
	 */
	/*
	protected void statesTreePortDoubleClicked(Port aPort) {
		//properties???
	}
	*/

	/**
	 * @see gui.graphEditor.ModelEditor#getDataPanel()
	 */
	protected JTextArea getDataPanel() {
		return getMainFrame().getAtomicDataPanel();
	}

	/**
	 * @see gui.graphEditor.ModelEditor#showLinkProperties(AbstractLink)
	 */
	protected void showLinkProperties(AbstractLink aLink) {
		if (aLink instanceof AtomicExternalLink) {
			addExpressionAndValueExt((AbstractAtomicLink)aLink);
		}
		else if (aLink instanceof AtomicInternalLink) {
			addExpressionAndValueInt((AbstractAtomicLink)aLink);
		}
		else {
			throw new RuntimeException("Link Type not contempled at AtomicGraphEditor");
		}

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

}