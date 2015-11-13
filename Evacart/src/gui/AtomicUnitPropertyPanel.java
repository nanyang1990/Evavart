package gui;

import gui.model.unit.AbstractUnit;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AtomicUnitPropertyPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractUnit aState;
	private JTextField IDTextField = new JTextField();
	private JTextField TLTextField = new JTextField();

	public JTextField getIDTextField() {
		return IDTextField;
	}
	public JTextField getTLTextField() {
		return TLTextField;
	}
	public AtomicUnitPropertyPanel(AbstractUnit theState) {
		this.aState = theState;
//		IDTextField.setText(aState.getName());
//		TLTextField.setText(String.valueOf(((AtomicUnit)aState).getTL()));
		setLayout(new GridLayout(2, 2, 5, 5));
		add(new JLabel("state ID"));
		add(IDTextField);
		add(new JLabel("state TL"));
		add(TLTextField);
	}
}