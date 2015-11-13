package gui;
import gui.model.ExpressionAndValue;
import gui.model.link.AbstractAtomicLink;

import java.awt.Frame;
import java.util.Enumeration;

public class DeleteExpressionAndValueDialog extends DeleteObjectDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractAtomicLink aLink;

	void populate() throws Exception {

		Enumeration<?> theExpressionssAndValues =
			aLink.getExpressionsAndValues().elements();
		while (theExpressionssAndValues.hasMoreElements()) {
			ExpressionAndValue theExpressionAndValue =
				(ExpressionAndValue) theExpressionssAndValues.nextElement();
			thedeleteObjectPanel.getObjectsComboBox().addItem(
				theExpressionAndValue);
		}

	}

	public DeleteExpressionAndValueDialog(
		Frame frame,
		String title,
		boolean modal,
		AbstractAtomicLink theLink) {
		super(frame, title, modal);
		thedeleteObjectPanel = new DeleteObjectPanel("Port & Value:");
		aLink = theLink;
		try {
			jbInit();
			populate();
			pack();
		} catch (Exception ex) {
			(new InformDialog(ex.toString(),ex)).setVisible(true);
		}
	}
}