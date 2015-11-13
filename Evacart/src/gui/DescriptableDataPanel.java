/*
 * Created on 28/04/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui;

import gui.model.Descriptable;

import java.awt.Graphics;

import javax.swing.JTextArea;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author jcidre
 *
 */
public class DescriptableDataPanel extends JTextArea implements TreeSelectionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Object to be shown
	 */
	private Descriptable descriptable;


	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		if(descriptable != null){
			if(!this.getText().equals(descriptable.getDescription())){
				this.setText(descriptable.getDescription());
			}
		}
		else{
			if(!this.getText().equals("")){
				this.setText("");
			}
		}
		super.paint(g);		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent e) {
		if(e.getNewLeadSelectionPath() != null){
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)e.getNewLeadSelectionPath().getLastPathComponent();
			//if the node is a Descriptable
			if (selectedNode.getUserObject() instanceof Descriptable) {
				this.descriptable = (Descriptable)selectedNode.getUserObject();
			}
			else{
				this.descriptable = null;
			}
		}
		else{
			this.descriptable = null;
		}
			this.repaint();
	}

}
