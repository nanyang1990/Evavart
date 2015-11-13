package gui;

import gui.model.unit.AbstractUnit;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;
public class CoupledUnitPropertyDialog extends OkCancelJDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractUnit anUnit;
	private JPanel apanel;
	private CoupledUnitPropertyPanel aPropertyPanel;

	public CoupledUnitPropertyDialog(Frame frame, String title, boolean modal, AbstractUnit theUnit) {
		super(frame, title, modal);
		this.anUnit = theUnit;
		aPropertyPanel = new CoupledUnitPropertyPanel();
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
		getContentPane().add(aPropertyPanel);
		apanel.add(getOkButton());
		apanel.add(getCancelButton());
		getContentPane().add(apanel);

		if (anUnit.getName() == null)
			aPropertyPanel.gettitleTextField().setText(" ");
		else
			aPropertyPanel.gettitleTextField().setText(anUnit.getName());

	}

	protected void okButtonClicked() {
		anUnit.setName(aPropertyPanel.gettitleTextField().getText());
		super.okButtonClicked();
	}
}