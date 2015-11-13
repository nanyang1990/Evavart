/*
 * Created on 26-abr-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui;

import gui.model.Variable;
import gui.model.model.EditableAtomicModel;

import java.awt.Frame;
import java.util.Enumeration;

/**
 * @author Juan Ignacio
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DeleteVariableDialog extends DeleteObjectDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EditableAtomicModel model;

	void populate() throws Exception {

		Enumeration<?> vars = model.getVariables().elements();
		while (vars.hasMoreElements()) {
			Variable theVar = (Variable) vars.nextElement();
			thedeleteObjectPanel.getObjectsComboBox().addItem(theVar);
		}

	}

	public DeleteVariableDialog(Frame frame, String title, boolean modal, EditableAtomicModel model) {
		super(frame, title, modal);
		thedeleteObjectPanel = new DeleteObjectPanel("Delete Variable:");
			
		this.model = model;
		try{
			jbInit();
			populate();
			pack();

		}
		catch (Exception ex) {
			(new InformDialog(ex.toString(),ex)).setVisible(true);
		}
	}

}
