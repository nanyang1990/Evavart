package gui;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class AddInitialParameterDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel apanel;
	private AddInitialParameterPanel theaddActionPanel;
	private String newInitialParameter = null;

	public AddInitialParameterDialog(Frame frame) {
		super(frame, "Add Initial Parameter", true);
		theaddActionPanel = new AddInitialParameterPanel();
		try {
			jbInit();
			pack();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void jbInit() throws Exception {
		super.jbInit();
		apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 2, 5, 5));
		getContentPane().setLayout(new GridLayout(2, 1, 5, 5));
		getContentPane().add(theaddActionPanel);
		apanel.add(getOkButton());
		apanel.add(getCancelButton());
		getContentPane().add(apanel);

		theaddActionPanel.getActionTextField().addKeyListener(this);

	}

	public String getNewInitialParameter() {
		return newInitialParameter;
	}

	protected void okButtonClicked() {
		if (theaddActionPanel.getActionTextField().getText().length() != 0) {
			newInitialParameter = theaddActionPanel.getActionTextField().getText();
		}
		super.okButtonClicked();
	}


}