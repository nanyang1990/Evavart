package gui;

import gui.graphEditor.Layoutable;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.Collection;
import java.util.Vector;
public class SelectItemDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector<?> selectedItems;
	boolean showAddOption;
	java.awt.List selctItems;

	public SelectItemDialog(Collection<?> items, boolean showAddOption) {
		super(new Frame(), "Select Entity", true);
		selectedItems = new Vector<Object>(items);
		this.showAddOption = showAddOption;
	try {
	  jbInit();
	  pack();

	}
	catch(Exception ex) {
	  ex.printStackTrace();
	}
	}
	protected void jbInit() throws Exception{
		super.jbInit();
		
		selctItems = new java.awt.List(5, true);
		selctItems.setMultipleMode(false);
		selctItems.select(0);
		getContentPane().add("Center", selctItems);
		getContentPane().add("South", getOkButton());
		
		if(showAddOption){
			selctItems.add("Add new Unit");
		}

		for (int i = 0; i < selectedItems.size(); i++) {
			Layoutable state = (Layoutable) selectedItems.elementAt(i);
			String m = state.getName();
			if (m== null || m.length() == 0)
				m = state.toString();
			selctItems.add(m);
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();

		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public int getSelectedIndex() {
		return selctItems.getSelectedIndex();
	}
	public Object getSelectedItem(){
		if(showAddOption){
			return selectedItems.get(getSelectedIndex()-1);
		}
		else{
			return selectedItems.get(getSelectedIndex());
		}
	}

}