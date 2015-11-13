package gui;

import gui.model.port.AbstractPort;
import gui.model.port.PortContainer;

import java.awt.Frame;
import java.util.Enumeration;

public class DeletePortDialog extends DeleteObjectDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PortContainer theContainer;

	void populate() throws Exception {

		Enumeration<?> thePorts = theContainer.getPorts().elements();
		while (thePorts.hasMoreElements()) {
			AbstractPort thePort = (AbstractPort) thePorts.nextElement();
			thedeleteObjectPanel.getObjectsComboBox().addItem(thePort);
		}

	}

	public DeletePortDialog(
		Frame frame,
		String title,
		boolean modal,
		PortContainer theContainer) {
		super(frame, title, modal);
		thedeleteObjectPanel = new DeleteObjectPanel("Port ID:");
		this.theContainer = theContainer;
		try {
			jbInit();
			populate();
			pack();

		} catch (Exception ex) {
			(new InformDialog(ex.toString(),ex)).setVisible(true);
		}
	}
}