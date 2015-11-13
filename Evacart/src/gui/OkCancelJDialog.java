package gui;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * @author jicidre@dc.uba.ar
 * This is a JDialog with a return state.
 */
public class OkCancelJDialog extends JDialog  implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int UNDEFINED_RETURN_STATE = -1;
	public static final int OK_RETURN_STATE = 1;
	public static final int CANCEL_RETURN_STATE = 2;
	public static final int CLOSED_RETURN_STATE = 3;

	private JButton okButton;
	private JButton cancelButton;

	/**
	 * The state that sais if the dialog was closed by OK button, Cancel Button or Closed
	 */
	private int returnState = UNDEFINED_RETURN_STATE;

	/**
	 * Constructor for OkCancelJDialog.
	 * @throws HeadlessException
	 */
	public OkCancelJDialog() throws HeadlessException {
		super();
	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
	}

	/**
	 * Constructor for OkCancelJDialog.
	 * @param arg0
	 * @throws HeadlessException
	 */
	public OkCancelJDialog(Dialog arg0) throws HeadlessException {
		super(arg0);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * Constructor for OkCancelJDialog.
	 * @param arg0
	 * @param arg1
	 * @throws HeadlessException
	 */
	public OkCancelJDialog(Dialog arg0, boolean arg1) throws HeadlessException {
		super(arg0, arg1);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * Constructor for OkCancelJDialog.
	 * @param arg0
	 * @throws HeadlessException
	 */
	public OkCancelJDialog(Frame arg0) throws HeadlessException {
		super(arg0);
	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
	}

	/**
	 * Constructor for OkCancelJDialog.
	 * @param arg0
	 * @param arg1
	 * @throws HeadlessException
	 */
	public OkCancelJDialog(Frame arg0, boolean arg1) throws HeadlessException {
		super(arg0, arg1);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * Constructor for OkCancelJDialog.
	 * @param arg0
	 * @param arg1
	 * @throws HeadlessException
	 */
	public OkCancelJDialog(Dialog arg0, String arg1) throws HeadlessException {
		super(arg0, arg1);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * Constructor for OkCancelJDialog.
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws HeadlessException
	 */
	public OkCancelJDialog(Dialog arg0, String arg1, boolean arg2) throws HeadlessException {
		super(arg0, arg1, arg2);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * Constructor for OkCancelJDialog.
	 * @param arg0
	 * @param arg1
	 * @throws HeadlessException
	 */
	public OkCancelJDialog(Frame arg0, String arg1) throws HeadlessException {
		super(arg0, arg1);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * Constructor for OkCancelJDialog.
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws HeadlessException
	 */
	public OkCancelJDialog(Frame arg0, String arg1, boolean arg2) throws HeadlessException {
		super(arg0, arg1, arg2);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	protected void jbInit() throws Exception {
		okButton = new JButton("OK");
		okButton.setMnemonic('O');
		okButton.setDefaultCapable(true);
		okButton.setName("OK");

		cancelButton = new JButton("Cancel");
		cancelButton.setMnemonic('C');
		cancelButton.setName("Cancel");
				
		this.connectActionPerformedListeners();
		this.connectWindowClosingListeners();
		this.connectKeyListeners();
	}

	/**
	 * Connect the buttons to the default listeners
	 * This is encapsulated in a method so the subimplementor can overwrite it
	 */
	protected void connectActionPerformedListeners() {

		//I'm my own KeyListener so I can press
		//the OK button on Key-Enter Typed
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				okButtonClicked();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cancelButtonClicked();
			}
		});
	}

	/**
	 * Connect the window to the default listeners
	 * This is encapsulated in a method so the subimplementor can overwrite it
	 */
	protected void connectWindowClosingListeners(){
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				OkCancelJDialog.this.windowClosed();
			}
		});
	}

	/**
	 * Connect the buttons to the default key listeners
	 * This is encapsulated in a method so the subimplementor can overwrite it
	 */
	protected void connectKeyListeners() {
		getOkButton().addKeyListener(this);
		getCancelButton().addKeyListener(this);
	}
	/**
	 * Returns the returnState.
	 * @return int
	 */
	public int getReturnState() {
		return returnState;
	}

	/**
	 * Sets the returnState.
	 * @param returnState The returnState to set
	 */
	protected void setReturnState(int returnState) {
		this.returnState = returnState;
	}

	/**
	 * The OK or Connect or whatever "OK" button was pressed
	 * Set OK_RETURN_STATE and exit
	 */
	protected void okButtonClicked() {
		this.returnState = OK_RETURN_STATE;
		dispose();
	}

	/**
	 * The Cancel or whatever "FAIL" button was pressed
	 * Set CANCEL_RETURN_STATE and exit
	 */
	protected void cancelButtonClicked() {
		this.returnState = CANCEL_RETURN_STATE;
		dispose();
	}

	/**
	 * The Window is closed
	 * Set CLOSED_RETURN_STATE and exit
	 */
	protected void windowClosed() {
		setReturnState(CLOSED_RETURN_STATE);
		dispose();
	}

	/**
	 * Returns the cancelButton.
	 * @return JButton
	 */
	protected JButton getCancelButton() {
		return cancelButton;
	}

	/**
	 * Returns the okButton.
	 * @return JButton
	 */
	protected JButton getOkButton() {
		return okButton;
	}

	/**
	 * Sets the cancelButton.
	 * @param cancelButton The cancelButton to set
	 */
	protected void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	/**
	 * Sets the okButton.
	 * @param okButton The okButton to set
	 */
	protected void setOkButton(JButton okButton) {
		this.okButton = okButton;
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
				okButtonClicked();
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
	
}