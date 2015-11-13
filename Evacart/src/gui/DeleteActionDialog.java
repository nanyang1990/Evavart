package gui;
import gui.model.link.AbstractAtomicLink;

import java.awt.Frame;
import java.util.Enumeration;

public class DeleteActionDialog extends DeleteObjectDialog {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private AbstractAtomicLink aLink;

  void Populate() throws Exception {

	Enumeration<?> theActions=aLink.getActions().elements();
	while(theActions.hasMoreElements()){
	  gui.model.Action theAction=(gui.model.Action)theActions.nextElement();
	  thedeleteObjectPanel.getObjectsComboBox().addItem(theAction);
	}

  }

  public DeleteActionDialog(Frame frame, String title, boolean modal
    			          ,AbstractAtomicLink theLink) {
	super(frame, title, modal);
        thedeleteObjectPanel=new DeleteObjectPanel("Action:");
	aLink = theLink;
      	try {
       	  jbInit();
          Populate();
      	  pack();
        }
	catch(Exception ex) {
	  ex.printStackTrace();
	}
   }
}