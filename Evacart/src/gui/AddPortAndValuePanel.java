package gui;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class AddPortAndValuePanel extends JPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JComboBox PortsComboBox;
  private JTextField ValueTextField;
  JLabel PortLabel = new JLabel();
  JLabel ValueLabel = new JLabel();

  //BorderLayout borderLayout1 = new BorderLayout();

  public AddPortAndValuePanel() {
	PortsComboBox=new JComboBox();
    ValueTextField=new JTextField();
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
	PortLabel.setText("  port");
    ValueLabel.setText("  value");
	/*NameTextField.setPreferredSize(new Dimension(30, 21));
        ValueTextField.setPreferredSize(new Dimension(30, 21));*/
	this.add(PortLabel, null);
	this.add(PortsComboBox);
        this.add(ValueLabel, null);
	this.add(ValueTextField);
  }

  public JComboBox getPortsComboBox(){return PortsComboBox;}
  public JTextField getValueTextField(){return ValueTextField;}
}
