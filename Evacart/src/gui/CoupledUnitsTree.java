/*
 * Created on 03/04/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui;

import gui.javax.swing.FinderTreeModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CoupledUnitsTree extends JTree implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FinderTreeModel coupledUnitsTreeModel;
	private MainFrame mainFrame;
	
	private DefaultMutableTreeNode unitsNode = new DefaultMutableTreeNode("Units");	
	private DefaultMutableTreeNode unitRootNode = new DefaultMutableTreeNode("Coupled Model");
	private DefaultMutableTreeNode unitLinksNode = new DefaultMutableTreeNode("Links");
	private DefaultMutableTreeNode unitPortsNode = new DefaultMutableTreeNode("Ports");
		
	public CoupledUnitsTree(MainFrame mainFrame) {
		this.setMainFrame(mainFrame);
		this.setModel(getCoupledUnitsTreeModel());
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setShowsRootHandles(true);
		this.setAutoscrolls(true);
		this.setScrollsOnExpand(true);
		this.setToggleClickCount(3);		
		

		this.expandRow(0);
		this.expandRow(1);

		//add the coupledGraphEditor as a listener to manage the double click over nodes
		this.addMouseListener(new ComponentsTreeMouseAdapter(this.getMainFrame().getCoupledModelEditor()));
		
		//link the Data Panel to show the selected object data		
		this.addTreeSelectionListener(this.getMainFrame().getCoupledDataPanel());
		
		this.addKeyListener(this);
			
	}

	public FinderTreeModel getCoupledUnitsTreeModel() {
		if (coupledUnitsTreeModel == null) {
			coupledUnitsTreeModel = new FinderTreeModel(unitRootNode);
			coupledUnitsTreeModel.insertNodeInto(unitsNode, unitRootNode, unitRootNode.getChildCount());
			coupledUnitsTreeModel.insertNodeInto(unitLinksNode, unitRootNode, unitRootNode.getChildCount());
			coupledUnitsTreeModel.insertNodeInto(unitPortsNode, unitRootNode, unitRootNode.getChildCount());
		}
		return coupledUnitsTreeModel;
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
			
	public DefaultMutableTreeNode getUnitsNode() {
		return unitsNode;
	}

	public DefaultMutableTreeNode getUnitLinksNode() {
		return unitLinksNode;
	}
	public DefaultMutableTreeNode getUnitPortsNode() {
		return unitPortsNode;
	}

	public void clearCoupledTreeNodes() {
		getUnitsNode().removeAllChildren();
		getUnitLinksNode().removeAllChildren();
		getUnitPortsNode().removeAllChildren();
		getCoupledUnitsTreeModel().reload();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent arg0) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {
		if (arg0.getKeyChar() == KeyEvent.VK_DELETE) {

			//get the selected node

			TreePath treePath = getSelectionPath();
			if (treePath != null) {
				DefaultMutableTreeNode selectedNode =
					(DefaultMutableTreeNode) treePath.getLastPathComponent();

				//if the node is a Selectable...
				//WHY THE UNIT MUST BE A SELECTABLE??
				//EXTEND THIS TO EVERY OBJECT IN THE TREE.
				//if (selectedNode.getUserObject() instanceof Selectable) {
					this
						.getMainFrame()
						.getCoupledModelEditor()
						.getModel()
						.delete(
						selectedNode.getUserObject());
					this.getMainFrame().getCoupledModelEditor().setChanged(
						true,
						false);
				//}
			}

		}

	}
			
}
