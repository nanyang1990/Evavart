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
public class AtomicUnitsTree extends JTree  implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MainFrame mainFrame;	
	
	private FinderTreeModel atomicStatesTreeModel;
	private DefaultMutableTreeNode atomicStatesNode;
	private DefaultMutableTreeNode atomicLinksNode;
	private DefaultMutableTreeNode atomicPortsNode;
	private DefaultMutableTreeNode atomicVarsNode;	
	private DefaultMutableTreeNode atomicRootNode;

	public AtomicUnitsTree(MainFrame mainFrame){
		this.setMainFrame(mainFrame);
		this.setModel(getAtomicStatesTreeModel());
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setShowsRootHandles(true);
		this.setAutoscrolls(true);
		this.setScrollsOnExpand(true);
		this.setToggleClickCount(3);			
		

		this.expandRow(0);
		this.expandRow(1);

		//add the coupledGraphEditor as a listener to manage the double click over nodes
		this.addMouseListener(new ComponentsTreeMouseAdapter(getMainFrame().getAtomicGraphEditor()));
		
		//link the Data Panel to show the selected object data		
		this.addTreeSelectionListener(this.getMainFrame().getAtomicDataPanel());
		
		this.addKeyListener(this);
		
	
	}

	public FinderTreeModel getAtomicStatesTreeModel() {
		if (atomicStatesTreeModel == null) {
			atomicStatesTreeModel = new FinderTreeModel(getAtomicRootNode());
			
			atomicStatesTreeModel.insertNodeInto(
				getAtomicStatesNode(),
				getAtomicRootNode(),
				getAtomicRootNode().getChildCount());
				
			atomicStatesTreeModel.insertNodeInto(
				getAtomicLinksNode(),
				getAtomicRootNode(),
				getAtomicRootNode().getChildCount());
				
			atomicStatesTreeModel.insertNodeInto(
				getAtomicPortsNode(),
				getAtomicRootNode(),
				getAtomicRootNode().getChildCount());

			atomicStatesTreeModel.insertNodeInto(
				getAtomicVarsNode(),
				getAtomicRootNode(),
				getAtomicRootNode().getChildCount());
			
		}
		return atomicStatesTreeModel;
	}
	public DefaultMutableTreeNode getAtomicLinksNode() {
		if (atomicLinksNode == null) {
			atomicLinksNode = new DefaultMutableTreeNode("Links");
		}
		return atomicLinksNode;
	}

	public DefaultMutableTreeNode getAtomicStatesNode() {
		if (atomicStatesNode == null) {
			atomicStatesNode = new DefaultMutableTreeNode("States");
		}
		return atomicStatesNode;
	}

	public DefaultMutableTreeNode getAtomicVarsNode() {
		if (atomicVarsNode == null) {
			atomicVarsNode = new DefaultMutableTreeNode("Vars");
		}
		return atomicVarsNode;
	}

	public DefaultMutableTreeNode getAtomicPortsNode() {
		if (atomicPortsNode == null) {
			atomicPortsNode = new DefaultMutableTreeNode("Ports");
		}
		return atomicPortsNode;
	}

	public DefaultMutableTreeNode getAtomicRootNode() {
		if (atomicRootNode == null) {
			atomicRootNode = new DefaultMutableTreeNode("Root Node");
		}
		return atomicRootNode;
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

	public void clearAtomicTreeNodes() {
		getAtomicLinksNode().removeAllChildren();
		getAtomicPortsNode().removeAllChildren();
		getAtomicStatesNode().removeAllChildren();
		getAtomicVarsNode().removeAllChildren();
		getAtomicStatesTreeModel().reload();
		getAtomicStatesTreeModel().clear();
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
		if(arg0.getKeyChar() == KeyEvent.VK_DELETE){
		
		//get the selected node
		
			TreePath treePath = getSelectionPath();
			if (treePath != null) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();

				//if the node is a Selectable...
				//if (selectedNode.getUserObject() instanceof Selectable) {
				//WHY THE UNIT MUST BE A SELECTABLE??
				//EXTEND THIS TO EVERY OBJECT IN THE TREE.

					this.getMainFrame().getSelectedGraphEditor().getModel().delete(selectedNode.getUserObject());
					this.getMainFrame().getAtomicGraphEditor().setChanged(true,false);		
				//}
			}		
		
	}
		
	}

}
