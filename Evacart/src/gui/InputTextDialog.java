package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class InputTextDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel textPanel;
	private JPanel buttonsPanel;
	private JTextField textField;
	private JLabel textLabel;
	


	protected void jbInit() throws Exception {
		super.jbInit();

		getContentPane().setLayout(new GridLayout(2, 1, 5, 5));
		getContentPane().add(getTextPanel());
		getContentPane().add(getButtonsPanel());
		
		getOkButton().setText("Set");
		getOkButton().setMnemonic('S');
	}
	public JPanel getTextPanel() {
		if (textPanel == null) {

			textField = new JTextField(20);
			//this is necessary so the ENTER key is absorbed by the okButtonClicked otherwise it is consumed by the jTextField
			textField.addKeyListener(this);
		
			textPanel = new JPanel();
			textPanel.setLayout(new GridLayout(1, 2));
			textPanel.add(textLabel);
			textPanel.add(textField);
			
		}
		return textPanel;
	}
	public JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
			buttonsPanel.add(getOkButton());
			buttonsPanel.add(getCancelButton());
		}
		return buttonsPanel;
	}

	/**
	 * Returns the textField.
	 * @return JTextField
	 */
	public JTextField getTextField() {
		return textField;
	}

	/**
	 * Returns the textLabel.
	 * @return JLabel
	 */
	public JLabel getTextLabel() {
		return textLabel;
	}

	/**
	 * Sets the buttonsPanel.
	 * @param buttonsPanel The buttonsPanel to set
	 */
	public void setButtonsPanel(JPanel buttonsPanel) {
		this.buttonsPanel = buttonsPanel;
	}

	/**
	 * Sets the textField.
	 * @param textField The textField to set
	 */
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	/**
	 * Sets the textLabel.
	 * @param textLabel The textLabel to set
	 */
	public void setTextLabel(JLabel textLabel) {
		this.textLabel = textLabel;
	}

	/**
	 * Sets the textPanel.
	 * @param textPanel The textPanel to set
	 */
	public void setTextPanel(JPanel textPanel) {
		this.textPanel = textPanel;
	}

}
