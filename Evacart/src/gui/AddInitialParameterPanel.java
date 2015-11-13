package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddInitialParameterPanel extends JPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTextField ActionTextField;
  JLabel ActionLabel = new JLabel();

  //BorderLayout borderLayout1 = new BorderLayout();

  public AddInitialParameterPanel() {
	ActionTextField=new JTextField();
	ActionTextField.setText("");

	try {
	  jbInit();
	}
	catch(Exception ex) {
	  ex.printStackTrace();
	}
  }

  void jbInit() throws Exception {
	this.setLayout(new GridLayout(2,2,3,3));
	ActionLabel.setMaximumSize(new Dimension(42, 17));
	ActionLabel.setPreferredSize(new Dimension(42, 17));
	ActionLabel.setText("  New Initial Parameter:");
	ActionTextField.setPreferredSize(new Dimension(30, 21));
	this.add(ActionLabel, null);
	this.add(ActionTextField);
  }

  public JTextField getActionTextField(){return ActionTextField;}
}