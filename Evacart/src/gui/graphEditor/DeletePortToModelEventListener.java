/*
 * Created on 19/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.graphEditor;

import gui.DeletePortDialog;
import gui.model.model.AbstractModel;
import gui.model.port.AbstractPort;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DeletePortToModelEventListener implements ActionListener {
	//private final ModelEditor editor;
	private AbstractModel model;
	private Container invoker;
	public DeletePortToModelEventListener(Container invoker,AbstractModel aModel){
		model = aModel;
		this.invoker = invoker;
	}

	public void actionPerformed(ActionEvent e) {
		DeletePortDialog thedeletePortDialog = new DeletePortDialog(null, "Delete Port", true, model);
		thedeletePortDialog.setSize(180, 80);
		thedeletePortDialog.setLocationRelativeTo(invoker);
		thedeletePortDialog.setVisible(true);

		if (thedeletePortDialog.getReturnState() == DeletePortDialog.DELETE_RETURN_STATE) {
			if (thedeletePortDialog.getselectedObject() != null) {
				model.deletePort((AbstractPort) thedeletePortDialog.getselectedObject());
				//this.editor.setChanged(true);
			}
		}
		//this.editor.setLastSelectableClicked(null);
	}
}