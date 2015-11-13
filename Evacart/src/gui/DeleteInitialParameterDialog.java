package gui;
import gui.model.model.AtomicModel;

import java.awt.Frame;
import java.util.Enumeration;

public class DeleteInitialParameterDialog extends DeleteObjectDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AtomicModel aModel;

	void populate() throws Exception {

		Enumeration<?> theActions = aModel.getInitialParameters().elements();
		while (theActions.hasMoreElements()) {
			String theParam = (String) theActions.nextElement();
			thedeleteObjectPanel.getObjectsComboBox().addItem(theParam);
		}

	}

	public DeleteInitialParameterDialog(Frame frame, AtomicModel theLink) {
		super(frame, "Delete Initial Parameter", true);
		thedeleteObjectPanel = new DeleteObjectPanel("Initial Parameter:");
		this.aModel = theLink;
		try {
			jbInit();
			populate();
			pack();
		} catch (Exception ex) {
			(new InformDialog(ex.toString(),ex)).setVisible(true);
		}
	}
}