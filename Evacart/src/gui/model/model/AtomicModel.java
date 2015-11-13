/*
 * Created on 09/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.model;

import gui.MainFrame;
import gui.graphEditor.Layoutable;
import gui.javax.swing.FinderTreeModel;
import gui.model.Variable;
import gui.model.link.AbstractAtomicLink;
import gui.model.link.AbstractLink;
import gui.model.link.AtomicExternalLink;
import gui.model.link.AtomicInternalLink;
import gui.model.port.AbstractPort;
import gui.model.port.AtomicPort;
import gui.model.unit.AbstractUnit;
import gui.model.unit.AtomicUnit;
import gui.model.unit.Initializable;

import java.awt.Point;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;


/**
 * @author jcidre
 *
 */
public abstract class AtomicModel extends AbstractModel implements Initializable {
	

	private Vector initialParameters;
	
	/**
	 * control if the links should show the expressions
	 *
	 */
	private boolean showExpressions = true;
	/**
	 * control if the links should show the actions
	 *
	 */
	private boolean showActions = true;
	
	
	public AtomicModel() {
		this(null, new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),null);
	}

	public AtomicModel(MainFrame mainFrame) {
		this(null, new Vector(), new Vector(), new Vector(), new Vector(), new Vector(), mainFrame);
	}
	public AtomicModel(String atitle, Vector v1, Vector v2, Vector v3, Vector v4, Vector initParams, MainFrame mainFrame) {
		this.setMainFrame(mainFrame);
		setModelName(atitle);
		setModels(v1);
		setLinks(v2);
		setPorts(v3);
		setVariables(v4);
		setInitialParameters(initParams);
	}
	

	/**
	 * @see gui.model.Model#getRootLinksNode()
	 */
	protected MutableTreeNode getRootLinksNode() {
		if(getMainFrame() != null){
			return getMainFrame().getAtomicUnitsTree().getAtomicLinksNode();
		}
		else{
			//if there is no MainFrame, there is no RootPort
			//this may happen when this Model is a Model of a ModelUnit
			return null;
		}		
		
				
	}
	protected MutableTreeNode getRootStatesNode(){
		if(getMainFrame() != null){
			return getMainFrame().getAtomicUnitsTree().getAtomicStatesNode();		
		}
		else{
			//if there is no MainFrame, there is no RootPort
			//this may happen when this Model is a Model of a ModelUnit
			return null;
		}		
		
	}

	/**
	 * @see gui.model.Model#getStatesTreeModel()
	 */
	protected FinderTreeModel getUnitsTreeModel() {
		if(getMainFrame() != null){
			return getMainFrame().getAtomicUnitsTree().getAtomicStatesTreeModel();
		}
		else{
			//if there is no MainFrame, there is no RootPort
			//this may happen when this Model is a Model of a ModelUnit
			return null;
		}		
	}

	/**
	 * returns the Variables tree parent
	 */
	protected MutableTreeNode getRootVarsNode() {
		if(getMainFrame() != null){
			return getMainFrame().getAtomicUnitsTree().getAtomicVarsNode();
		}
		else{
			//if there is no MainFrame, there is no RootVars
			//this may happen when this Model is a Model of a ModelUnit
			return null;
		}		
		
				
	}

/**
 * Returns the node where the ports hang on
 */
	protected MutableTreeNode getRootPortsNode(){
		if(getMainFrame() != null){
			return getMainFrame().getAtomicUnitsTree().getAtomicPortsNode();
		}
		else{
			//if there is no MainFrame, there is no RootPort
			//this may happen when this Model is a Model of a ModelUnit
			return null;
		}		
	}


	protected Vector variables;
	/**
		 * @return
		 */
	public Vector getVariables() {
		return variables;
	}

	/**
		 * @param vector
		 */
	public void setVariables(Vector vector) {
		variables = vector;
	}
	/* (non-Javadoc)
	 * @see gui.graphEditor.ModelEditor#createNewPort()
	 */
	public AbstractPort createNewPort() {
		return new AtomicPort();
	}
	public AbstractLink createNewLink(Point startPoint, Point endPoint, Point through, String linkType) {
		Layoutable start = (Layoutable)getDragablesAt(startPoint).iterator().next();
		Layoutable end = (Layoutable)getDragablesAt(endPoint).iterator().next();
		int linkId = getSequence().next();
		if (start != end) {
			if ("int".equals(linkType)) {
				return new AtomicInternalLink(start, end, "" + linkId);
			}
			else {
				return new AtomicExternalLink(start, end, "" + linkId);
			}
		}
		else {
			return null;
		}

	}

	public void deleteVariable(Variable var){
		this.getVariables().remove(var);

		//if this graph has a tree that shows the model..
		if(getUnitsTreeModel() != null){
			//remove the node of the tree
			getUnitsTreeModel().removeNodeFromParent(var);
		}
		else{
			//not needing to remove from anywhere
		}
		
	}
	public void deletePort(AbstractPort aPort) {
		//has to delete a port
	
		//first unlink the links to the port
		Iterator theLinks = getIncidentLinks(aPort).iterator();
			while (theLinks.hasNext()) {
				AbstractLink aLink = (AbstractLink) theLinks.next();
				aLink.unlink(aPort);
			}
		
	
		//remove the port
		getPorts().removeElement(aPort);
	
		//if this graph has a tree that shows the model..
		if(getUnitsTreeModel() != null){
			//remove the node of the tree
			getUnitsTreeModel().removeNodeFromParent(aPort);
		}
		else{
			//not needing to remove from anywhere
		}
	
	}


	/**
	 * Create a Unit from de Class selected in the class
	 * tree
	 */
	public AbstractUnit createNewUnit(AbstractModel model, Point point) {

		//create a name for this unit
		int id = getSequence().next();
		String unitName = model.getModelName() + id;

		AbstractUnit theNewUnit;
		if (model instanceof VoidModel) {
			//creating a new atomic model unit
			theNewUnit = new AtomicUnit(unitName, point,"" + id);
		}
		else{
			throw new RuntimeException("Model type not supported");
		}

		return theNewUnit;
	}

	/**
	 * @return
	 */
	public Vector getInitialParameters() {
		return initialParameters;
	}

	/**
	 * @param vector
	 */
	public void setInitialParameters(Vector vector) {
		initialParameters = vector;
	}

    /**
     * @return Returns the showActions.
     */
    public boolean isShowActions() {
        return showActions;
    }
    /**
     * @param showActions The showActions to set.
     */
    public void setShowActions(boolean showActions) {
        this.showActions = showActions;
    }
    /**
     * @return Returns the showExpressions.
     */
    public boolean isShowExpressions() {
        return showExpressions;
    }
    /**
     * @param showExpressions The showExpressions to set.
     */
    public void setShowExpressions(boolean showExpressions) {
        this.showExpressions = showExpressions;
    }
    
    /* (non-Javadoc)
     * @see gui.model.model.AbstractModel#prepareLinkToBeRendered(gui.model.link.AbstractLink)
     */
    protected void prepareLinkToBeRendered(AbstractLink each) {
        ((AbstractAtomicLink)each).setShowExpressions(this.isShowExpressions());
        ((AbstractAtomicLink)each).setShowActions(this.isShowActions());
    }
}
