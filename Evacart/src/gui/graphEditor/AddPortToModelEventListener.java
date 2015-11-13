/*
 * Created on 19/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.graphEditor;

import gui.OkCancelJDialog;
import gui.PortDialog;
import gui.model.model.AbstractModel;
import gui.model.port.AbstractPort;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddPortToModelEventListener implements ActionListener {
	//private final ModelEditor editor;
	private AbstractModel model;
	private Component relative;
	public AddPortToModelEventListener(Component relative,AbstractModel aModel){
		model = aModel;
		this.relative = relative;
	}
	public void actionPerformed(ActionEvent e) {
		AbstractPort port2Add = model.createNewPort();
		port2Add.setUniqueId(""+model.getSequence().next());
		PortDialog theaddPortDialog = new PortDialog(null, "Add Port", true,  port2Add);
		theaddPortDialog.setLocationRelativeTo(relative);
		theaddPortDialog.setVisible(true);
		
		if(theaddPortDialog.getReturnState() == OkCancelJDialog.OK_RETURN_STATE){
			port2Add = theaddPortDialog.getPort();
			model.addPort(port2Add);
			
			//rebuildTreeModel();
			//this.editor.setChanged(true);
		}
		//this.editor.setLastSelectableClicked(null);
	}
}