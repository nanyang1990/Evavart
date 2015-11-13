package gui;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PortPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private PortContainer theContainer;
	private JTextField PortIDTextField;
	private JComboBox InorOutComboBox;
	private JComboBox PortTypeComboBox;
	//private JLabel parentStateLabel;
	private static String[] InorOut = { "In", "Out" };
	private static String[] PortTypes = { "Integer", "Float" };

	void jbInit() throws Exception {
		this.setLayout(new GridLayout(4, 2, 5, 5));
		this.add(new JLabel("PortID"));
		this.add(PortIDTextField);
		this.add(new JLabel("In/Out"));
		this.add(InorOutComboBox);
		this.add(new JLabel("port type"));
		this.add(PortTypeComboBox);
	}

	public JTextField getPortIDTextField() {
		return PortIDTextField;
	}
	public JComboBox getInorOutComboBox() {
		return InorOutComboBox;
	}
	public JComboBox getPortTypeComboBox() {
		return PortTypeComboBox;
	}

	//BorderLayout borderLayout1 = new BorderLayout();

	public PortPanel() {
		//this.theContainer = theContainer;
		PortIDTextField = new JTextField();
		PortIDTextField.setText("");
		InorOutComboBox = new JComboBox(InorOut);
		PortTypeComboBox = new JComboBox(PortTypes);
		

		try {
			jbInit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}