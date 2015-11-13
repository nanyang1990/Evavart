package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class VariablePanel extends JPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTextField NameTextField;
  private JTextField ValueTextField;
  JLabel NameLabel = new JLabel();
  JLabel ValueLabel = new JLabel();

  //BorderLayout borderLayout1 = new BorderLayout();

  public VariablePanel() {
	NameTextField=new JTextField();
        ValueTextField=new JTextField();
	NameTextField.setText("");
        ValueTextField.setText("");

	try {
	  jbInit();
	}
	catch(Exception ex) {
	  ex.printStackTrace();
	}
  }

  void jbInit() throws Exception {
	this.setLayout(new GridLayout(2,2,5,5));
	/*NameLabel.setMaximumSize(new Dimension(42, 17));
	NameLabel.setPreferredSize(new Dimension(42, 17));
        ValueLabel.setMaximumSize(new Dimension(42, 17));
	ValueLabel.setPreferredSize(new Dimension(42, 17));*/
	NameLabel.setText("  variable name");
        ValueLabel.setText("  variable value");
	/*NameTextField.setPreferredSize(new Dimension(30, 21));
        ValueTextField.setPreferredSize(new Dimension(30, 21));*/
	this.add(NameLabel, null);
	this.add(NameTextField);
        this.add(ValueLabel, null);
	this.add(ValueTextField);
  }

  public JTextField getNameTextField(){return NameTextField;}
  public JTextField getValueTextField(){return ValueTextField;}
}