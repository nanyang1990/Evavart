package gui;

import gui.javax.swing.FinderTreeModel;
import gui.javax.util.Identifiable;
import gui.model.model.AbstractModel;
import gui.model.model.EditableAtomicModel;
import gui.model.model.EditableCoupledModel;
import gui.model.model.ImportedAtomicModel;
import gui.model.model.ImportedCoupledModel;
import gui.model.port.CoupledPort;

import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
;

/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class CoupledClassesTree extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FinderTreeModel classesTreeModel;

	private DefaultMutableTreeNode treeRootNode;
	private DefaultMutableTreeNode classesRootNode;
	private DefaultMutableTreeNode classesAtomicNode;
	private DefaultMutableTreeNode classesCoupledNode;		
	private DefaultMutableTreeNode portsRootNode;	

	private MainFrame mainFrame;	

	/**
	 * Constructor for ClassesTree.
	 */
	public CoupledClassesTree(MainFrame mainFrame) {
		super();
		this.setMainFrame(mainFrame);
		this.setModel(getClassesTreeModel());
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setShowsRootHandles(true);
		this.setAutoscrolls(true);
		this.setToggleClickCount(3);
		
		//populate
		
		//the root
		getClassesTreeModel().setRoot(getTreeRootNode());		
		
		//the ports
		getClassesTreeModel().insertNodeInto(getPortsRootNode(),getTreeRootNode(),0);				
		TreeNode selected = getClassesTreeModel().insertNodeInto(new CoupledPort("In","In","Integer",null),getPortsRootNode(),0);				
		getClassesTreeModel().insertNodeInto(new CoupledPort("Out","Out","Integer",null),getPortsRootNode(),1);	
		
		this.setSelectionPath(new TreePath(getClassesTreeModel().getPathToRoot(selected)));
		//the classes					
		getClassesTreeModel().insertNodeInto(getClassesRootNode(),getTreeRootNode(),1);		
		getClassesTreeModel().insertNodeInto(getClassesAtomicNode(), getClassesRootNode(),0);		
		getClassesTreeModel().insertNodeInto(getClassesCoupledNode(), getClassesRootNode(),1);		
				
		//ensure visibility
		//@todo: correct this code. It works, but....
		this.expandRow(0);
		this.expandRow(1);		
		this.expandRow(2);		
		this.expandRow(3);			
		this.expandRow(4);			
		this.expandRow(5);				
		this.expandRow(6);						
		
		this.addMouseListener(new CoupledClassesTreeMouseAdapter(this, this.getMainFrame().getDefaultDir(),this.getMainFrame()));
	}

	public FinderTreeModel getClassesTreeModel() {
		if (classesTreeModel == null) {
			classesTreeModel = new FinderTreeModel(getClassesRootNode());
		}
		return classesTreeModel;
	}

	private DefaultMutableTreeNode getClassesRootNode() {
		if(classesRootNode == null){
			classesRootNode = new DefaultMutableTreeNode("Models");
		}
		return classesRootNode;
	}

	public DefaultMutableTreeNode getClassesAtomicNode() {
		if(classesAtomicNode == null){
			classesAtomicNode = new DefaultMutableTreeNode("Atomic");
		}
		return classesAtomicNode;
	}
	public DefaultMutableTreeNode getClassesCoupledNode() {
		if(classesCoupledNode == null){
			classesCoupledNode = new DefaultMutableTreeNode("Coupled");
		}
		return classesCoupledNode;
	}

	public DefaultMutableTreeNode getPortsRootNode() {
		if(portsRootNode == null){
			portsRootNode = new DefaultMutableTreeNode("Ports");
		}
		return portsRootNode;
	}

	public DefaultMutableTreeNode getTreeRootNode() {
		if(treeRootNode == null){
			treeRootNode = new DefaultMutableTreeNode("Coupled Model");
		}
		return treeRootNode;
	}

	public Vector find(java.lang.Class type) {
		return getClassesTreeModel().find(type);
	}
	
	public void insertNodeInto(gui.javax.util.Identifiable userObject) {	
		if(!getClassesTreeModel().contains(userObject)){
			
		
			if(((AbstractModel)userObject) instanceof EditableCoupledModel){
				getClassesTreeModel().insertNodeInto(userObject, getClassesCoupledNode(),getClassesCoupledNode().getChildCount());
			}
			else if(((AbstractModel)userObject) instanceof EditableAtomicModel){
				getClassesTreeModel().insertNodeInto(userObject, getClassesAtomicNode(),getClassesAtomicNode().getChildCount());
			}
			else if(((AbstractModel)userObject) instanceof ImportedAtomicModel){
				getClassesTreeModel().insertNodeInto(userObject, getClassesAtomicNode(),getClassesAtomicNode().getChildCount());
			}
			else if(((AbstractModel)userObject) instanceof ImportedCoupledModel){
				getClassesTreeModel().insertNodeInto(userObject, getClassesCoupledNode(),getClassesCoupledNode().getChildCount());
			}
		}				
	}
	
	public boolean contains(Identifiable userObject) {	
		return getClassesTreeModel().contains(userObject);
	}
	/**
	 * Returns the mainFrame.
	 * @return MainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Sets the mainFrame.
	 * @param mainFrame The mainFrame to set
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void rebuildTreeModel() {
	}
	
		
}
