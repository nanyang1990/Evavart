package gui.javax.swing;

import gui.javax.util.Identifiable;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class FinderTreeModel extends DefaultTreeModel {
	private Hashtable keyNodeReference = new Hashtable();
	/**
	 * Constructor for FinderTreeModel.
	 * @param root
	 */
	public FinderTreeModel(TreeNode root) {
		super(root);
	}

	/**
	 * Constructor for FinderTreeModel.
	 * @param root
	 * @param asksAllowsChildren
	 */
	public FinderTreeModel(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
	}

	/**
	 * @see javax.swing.tree.DefaultTreeModel#insertNodeInto(MutableTreeNode, MutableTreeNode, int)
	 */
	public DefaultMutableTreeNode insertNodeInto(Identifiable userObject, MutableTreeNode parent, int index) {
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(userObject);

		//create a key of the user object
		Object key = buildKey(userObject);

		//save the reference of the key and the treenode
		getKeyNodeReference().put(key, newChild);

		//insert the node
		super.insertNodeInto(newChild, parent, index);
		
		return newChild;
	}

	/**
	 * return the node that represents userObject.
	 * null if not present
	 */
	public DefaultMutableTreeNode find(Identifiable userObject) {
		Object key = buildKey(userObject);
		DefaultMutableTreeNode retr =(DefaultMutableTreeNode) getKeyNodeReference().get(key); 
		return retr;

	}

	/*
	 * Returns a Vector with all the nodes of aClass
	 */
	public Vector find(Class type) {
		Vector retr = new Vector();
		Enumeration keys = getKeyNodeReference().keys();
		while (keys.hasMoreElements()) {
			Vector key = (Vector) keys.nextElement();
			if ((key.get(0).getClass()).isInstance(type)) {
				retr.add(getKeyNodeReference().get(key));
			}
		}
		return retr;
	}

	/**
	 * Returns the keyNodeReference.
	 * @return Hashtable
	 */
	private Hashtable getKeyNodeReference() {
		return keyNodeReference;
	}

	/**
	 * Sets the keyNodeReference.
	 * @param keyNodeReference The keyNodeReference to set
	 */
	private void setKeyNodeReference(Hashtable keyNodeReference) {
		this.keyNodeReference = keyNodeReference;
	}

	private Object buildKey(Identifiable userObject) {
		Vector key = new Vector(2);
		key.add(userObject.getClass());
		key.add(userObject.getUniqueId());
		return key;
	}

	public boolean contains(Identifiable userObject) {
		return (find(userObject) != null);
	}
	/**
	 * @see javax.swing.tree.DefaultTreeModel#removeNodeFromParent(MutableTreeNode)
	 */
	public void removeNodeFromParent(Identifiable node) {
		DefaultMutableTreeNode tmpLinkNode = this.find(node);
		if(tmpLinkNode != null){
			this.removeNodeFromParent(tmpLinkNode);
			getKeyNodeReference().remove(buildKey(node));
		}
		else{
			//node not found
		}
		//System.err.println(""+getKeyNodeReference());
	}

	/**
	 * 
	 */
	public void clear() {
		this.getKeyNodeReference().clear();
	}

}
