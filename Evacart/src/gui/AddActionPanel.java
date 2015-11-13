package gui;

import gui.model.link.AbstractLink;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddActionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractLink theparentLink;
	private JTextField ActionTextField;
	JLabel ActionLabel = new JLabel();

	// BorderLayout borderLayout1 = new BorderLayout();

	public AddActionPanel(AbstractLink theLink) {
		this.theparentLink = theLink;
		ActionTextField = new JTextField();
		ActionTextField.setText("");

		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.setLayout(new GridLayout(2, 2, 3, 3));
		ActionLabel.setMaximumSize(new Dimension(42, 17));
		ActionLabel.setPreferredSize(new Dimension(42, 17));
		ActionLabel.setText("  New action:");
		ActionTextField.setPreferredSize(new Dimension(30, 21));
		this.add(ActionLabel, null);
		this.add(ActionTextField);
	}

	public JTextField getActionTextField() {
		return ActionTextField;
	}
}