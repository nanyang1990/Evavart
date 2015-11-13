package gui;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeleteObjectPanel extends JPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JComboBox ObjectsComboBox;
  private JLabel ObjectIDLabel;

  public DeleteObjectPanel(String ObjectLabel) {
	ObjectsComboBox=new JComboBox();
	ObjectIDLabel=new JLabel(ObjectLabel);

	try {
	  jbInit();
	}
	catch(Exception ex) {
	  ex.printStackTrace();
	}
  }
  void jbInit() throws Exception {
	this.setLayout(new GridLayout(1,2));
	add(ObjectIDLabel);
	add(ObjectsComboBox);
  }

  public JComboBox getObjectsComboBox(){return ObjectsComboBox;}
}