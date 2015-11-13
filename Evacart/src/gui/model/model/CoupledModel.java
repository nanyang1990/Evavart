/*
 * Created on 09/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.model;

import gui.MainFrame;
import gui.OkCancelJDialog;
import gui.QueryDialog;
import gui.graphEditor.Layoutable;
import gui.javax.swing.FinderTreeModel;
import gui.model.link.AbstractCoupledLink;
import gui.model.link.AbstractLink;
import gui.model.link.CoupledLink;
import gui.model.link.SelfLink;
import gui.model.port.AbstractPort;
import gui.model.port.CoupledPort;
import gui.model.unit.AbstractUnit;
import gui.model.unit.EditableAtomicModelUnit;
import gui.model.unit.EditableCoupledModelUnit;
import gui.model.unit.ImportedCoupledModelUnit;
import gui.model.unit.ImportedFromCDDAtomicModelUnit;
import gui.model.unit.ImportedFromRegisterAtomicModelUnit;

import java.awt.Point;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;

/**
 * @author jcidre
 *
 */
public abstract class CoupledModel extends AbstractModel {

/**
 * Control if the links should show the port connection
 */
	private boolean showPorts = true;
	
    public CoupledModel() {
		this(null);
	}

	public CoupledModel(MainFrame mainFrame) {
		this(mainFrame, null, new Vector(), new Vector(), new Vector());
	}
	public CoupledModel(MainFrame mainFrame, String atitle, Vector v1, Vector v2, Vector v3) {
		super();
		this.setMainFrame(mainFrame);
		setModelName(atitle);
		setModels(v1);
		setLinks(v2);
		setPorts(v3);
	}


	protected MutableTreeNode getRootStatesNode() {
		if(getMainFrame() != null){
			return getMainFrame().getCoupledUnitsTree().getUnitsNode();
		}
		else{
			//if there is no MainFrame, there is no RootPort
			//this may happen when this Model is a Model of a ModelUnit
			return null;
		}		
		
	}
	protected MutableTreeNode getRootPortsNode() {
		if(getMainFrame() != null){
			return getMainFrame().getCoupledUnitsTree().getUnitPortsNode();
		}
		else{
			//if there is no MainFrame, there is no RootPort
			//this may happen when this Model is a Model of a ModelUnit
			return null;
		}		
		
	}
/**
	 * @see gui.model.Model#getRootLinksNode()
	 */
	protected MutableTreeNode getRootLinksNode() {
		if(getMainFrame() != null){
			return getMainFrame().getCoupledUnitsTree().getUnitLinksNode();
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
			return getMainFrame().getCoupledUnitsTree().getCoupledUnitsTreeModel();
		}
		else{
			//if there is no MainFrame, there is no RootPort
			//this may happen when this Model is a Model of a ModelUnit
			return null;
		}				
	}
	/* (non-Javadoc)
	 * @see gui.graphEditor.ModelEditor#createNewPort()
	 */
	public AbstractPort createNewPort() {
		return new CoupledPort();
	}
	public AbstractLink createNewLink(Point startPoint, Point endPoint, Point through, String linkType) {
		Layoutable start = (Layoutable)getDragablesAt(startPoint).iterator().next();
		Layoutable end = (Layoutable)getDragablesAt(endPoint).iterator().next();
		int id = getSequence().next();
		if (AbstractCoupledLink.checkLinkEdges(start, end, this)) {
			if (start != end) {
				return new CoupledLink(start, end, "" + id);
			}
			else {

				QueryDialog dialog = new QueryDialog("Create self link?");
				dialog.setVisible(true);
				if (dialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE) {
					return new SelfLink(start, through, "" + id);
				}
				else {
					return null;
				}

			}
		}
		else {
			return null;
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
		if (model instanceof EditableAtomicModel) {
			//creating a new atomic model unit
			theNewUnit = new EditableAtomicModelUnit(point, model, unitName, "" + id);
		}
		else if(model instanceof EditableCoupledModel){
			//crating a new coupled model unit
			theNewUnit = new EditableCoupledModelUnit(point, model, unitName, "" + id);
		}
		else if(model instanceof ImportedFromRegisterAtomicModel) {
			//creating a new atomic model 
			try {
				theNewUnit = new ImportedFromRegisterAtomicModelUnit(point, (ImportedFromRegisterAtomicModel)((ImportedFromRegisterAtomicModel)model).clone(), unitName, "" + id);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				theNewUnit = null;
			}
		}
		else if(model instanceof ImportedFromCDDAtomicModel) {
			//creating a new atomic model 
			theNewUnit = new ImportedFromCDDAtomicModelUnit(point, model, unitName, "" + id);
		}
		else if(model instanceof ImportedCoupledModel) {
			//creating a new atomic model 
			theNewUnit = new ImportedCoupledModelUnit(point, model, unitName, "" + id);
		}
		else{
			throw new RuntimeException("Model type not supported");
		}

		return theNewUnit;
	}
    /**
     * @return Returns the showPorts.
     */
    public boolean isShowPorts() {
        return showPorts;
    }
    /**
     * @param showPorts The showPorts to set.
     */
    public void setShowPorts(boolean showPorts) {
        this.showPorts = showPorts;
    }
	
    /* (non-Javadoc)
     * @see gui.model.model.AbstractModel#prepareLinkToBeRendered(gui.model.link.AbstractLink)
     */
    protected void prepareLinkToBeRendered(AbstractLink each) {
        ((AbstractCoupledLink)each).setShowPorts(this.isShowPorts());
    }
	
}
