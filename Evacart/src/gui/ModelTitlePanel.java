package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ModelTitlePanel extends JPanel {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTextField titleTextField;
  private JLabel modelLabel;


  public ModelTitlePanel() {
	titleTextField=new JTextField();
	modelLabel=new JLabel("Model Title");
	try {
	  jbInit();
	}
	catch(Exception ex) {
	  ex.printStackTrace();
	}
  }
  void jbInit() throws Exception {
	this.setLayout(new GridLayout(1,2));
	add(modelLabel);
	add(titleTextField);
  }

  public JTextField gettitleTextField(){return titleTextField;}
}