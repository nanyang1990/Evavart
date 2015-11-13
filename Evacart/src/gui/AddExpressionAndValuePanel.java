package gui;



import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;



public class AddExpressionAndValuePanel extends JPanel implements ActionListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTextField expressionTextField;
  private JTextField ValueTextField;
  JLabel ExpressionLabel = new JLabel();
  JLabel ValueLabel = new JLabel();
  JList itemLists;
  JButton copyPortButton = new JButton();

  //BorderLayout borderLayout1 = new BorderLayout();

  public AddExpressionAndValuePanel(Vector<?> theItems) {
	itemLists = new JList(theItems);
  	
  	expressionTextField=new JTextField(60);
    ValueTextField=new JTextField(60);
	expressionTextField.setText("");
    ValueTextField.setText("");
	
	try {
	  jbInit();
	}
	catch(Exception ex) {
	  ex.printStackTrace();
	}
  }

  void jbInit() throws Exception {
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	this.setLayout(gridbag);	
	ExpressionLabel.setText("  expression");
    ValueLabel.setText("  value");
	expressionTextField.setMinimumSize(new Dimension(60, 21));
	ValueTextField.setMinimumSize(new Dimension(60, 21));
	copyPortButton.setText("<<");
	c.weightx = 0.5;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	c.gridwidth = 1;
	gridbag.setConstraints(ExpressionLabel, c);
	this.add(ExpressionLabel);
	c.gridx = 1;
	c.gridy = 0;
	gridbag.setConstraints(expressionTextField, c);
	this.add(expressionTextField);
	c.gridx = 0; 
	c.gridy = 1;
	gridbag.setConstraints(ValueLabel, c);
    this.add(ValueLabel);
	c.gridx = 1;
	c.gridy = 1;
	gridbag.setConstraints(ValueTextField, c);
	this.add(ValueTextField);
	c.gridx = 2;
	c.gridy = 1;
	gridbag.setConstraints(copyPortButton, c);
	this.add(copyPortButton);
	c.gridx = 3;
	c.gridy = 0;
	c.gridheight = 3;
	c.gridwidth = 2;		
	JScrollPane listScrollPane = new JScrollPane(itemLists);
	gridbag.setConstraints(listScrollPane, c);
	listScrollPane.setMinimumSize(new Dimension(60,60));
	this.add(listScrollPane);
	copyPortButton.addActionListener(this);
	copyPortButton.setActionCommand("copy");		
  }

  public JTextField getExpressionTextField(){return expressionTextField;}
  public JTextField getValueTextField(){return ValueTextField;}

  public JButton getCopyPortButton() {return copyPortButton;}
 
  public void actionPerformed(ActionEvent e) {
	if ("copy".equals(e.getActionCommand())) {
		String selectedItem = (String)itemLists.getSelectedValue();

		String expr = getExpressionTextField().getText();
		int pos = getExpressionTextField().getCaretPosition();

		StringBuffer newExpr = new StringBuffer(expr);
		newExpr.insert(pos, selectedItem);
		
		getExpressionTextField().setText(newExpr.toString());
		getExpressionTextField().setCaretPosition(pos + selectedItem.length());
		
		getExpressionTextField().requestFocus();
		
	}
  	
  }
} 
