package gui;
import gui.model.Action;
import gui.model.link.AbstractAtomicLink;
import gui.model.link.AbstractLink;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class AddActionDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractAtomicLink link;
	private JPanel apanel;
	private Object actionId;
	private AddActionPanel theaddActionPanel;
	private Action anewAction = null;

	public AddActionDialog(Frame frame, String title, boolean modal, AbstractAtomicLink aLink, Object actionId) {
		super(frame, title, modal);
		this.setLink(aLink);
		this.actionId = actionId;
		theaddActionPanel = new AddActionPanel(aLink);
		try {
			jbInit();
			pack();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void jbInit() throws Exception {
		super.jbInit();
		apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 2, 5, 5));
		getContentPane().setLayout(new GridLayout(2, 1, 5, 5));
		getContentPane().add(theaddActionPanel);
		apanel.add(getOkButton());
		apanel.add(getCancelButton());
		getContentPane().add(apanel);

		theaddActionPanel.getActionTextField().addKeyListener(this);

	}

	public Action getnewAction() {
		return anewAction;
	}
	public AbstractLink getLink() {
		return link;
	}

	protected void okButtonClicked() {
		if (theaddActionPanel.getActionTextField().getText().length() != 0) {
			anewAction = new Action(theaddActionPanel.getActionTextField().getText(),actionId);
		}
		super.okButtonClicked();
	}

	/**
	 * @param link
	 */
	public void setLink(AbstractAtomicLink link) {
		this.link = link;
	}

}