package gui;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;


abstract public class DeleteObjectDialog extends JDialog implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int UNDEFINED_RETURN_STATE = -1;
	public static final int DELETE_RETURN_STATE = 1;
	public static final int CANCEL_RETURN_STATE = 2;
	public static final int CLOSED_RETURN_STATE = 3;

	/**
	 * The state that sais if the dialog was closed by OK button, Cancel Button or Closed
	 */
	private int returnState = UNDEFINED_RETURN_STATE;

	//	private Unit aState;
	private JPanel apanel;
	private JButton deleteButton, cancelButton;
	protected DeleteObjectPanel thedeleteObjectPanel;
	private Object aselectedObject;

	void jbInit() throws Exception {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		apanel = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.setMnemonic('D');
		cancelButton = new JButton("Cancel");
		cancelButton.setMnemonic('C');
		apanel.setLayout(new GridLayout(1, 2, 5, 5));
		getContentPane().setLayout(new GridLayout(2, 1, 5, 5));
		getContentPane().add(thedeleteObjectPanel);
		apanel.add(deleteButton);
		apanel.add(cancelButton);
		getContentPane().add(apanel);

		aselectedObject = null;

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				deleteButtonClicked();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cancelButtonClicked();
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				DeleteObjectDialog.this.returnState = CLOSED_RETURN_STATE;
				dispose();
			}
		});

		deleteButton.addKeyListener(this);
		cancelButton.addKeyListener(this);
		thedeleteObjectPanel.getObjectsComboBox().addKeyListener(this);
	}

	public Object getselectedObject() {
		return aselectedObject;
	}

	protected void setSelectedObject(Object obj) {
		this.aselectedObject = obj;
	}

	/*
	  a private in an abstract class??? this make no sense at all!!
	private void deleteButtonClicked(){
		if(!(thedeleteObjectPanel.getObjectsComboBox().getSelectedItem()==null)){
		  aselectedObject=thedeleteObjectPanel.getObjectsComboBox().getSelectedItem();
		  if(client!=null)
			client.dialogFinished(this);
		}
		dispose();
	}
	*/
	protected void deleteButtonClicked() {
		if (!(thedeleteObjectPanel.getObjectsComboBox().getSelectedItem() == null)) {
			aselectedObject = thedeleteObjectPanel.getObjectsComboBox().getSelectedItem();
			this.returnState = DELETE_RETURN_STATE;
			this.dispose();
		}
	}
	/*
	  a private in an abstract class??? this make no sense at all!!
	private void cancelButtonClicked(){
		if(client!=null)
		  client.dialogCancelled(this);
		dispose();
	}
	*/
	protected void cancelButtonClicked() {
		this.returnState = CANCEL_RETURN_STATE;
		this.dispose();
	}

	public DeleteObjectDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
	}
	/**
	* Invoked when a key has been typed.
	* This event occurs when a key press is followed by a key release.
	* If the cancell button has the focus: cancell
	* else: ok
	*/
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			if (cancelButton.hasFocus()) {
				cancelButtonClicked();
			}
			else {
				deleteButtonClicked();
			}
		}
	}

	/**
	 * Invoked when a key has been pressed.
	 */
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * Invoked when a key has been released.
	 */
	public void keyReleased(KeyEvent e) {
	}

	public int getReturnState() {
		return returnState;
	}

}