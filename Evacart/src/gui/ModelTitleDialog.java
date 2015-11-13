package gui;


import java.awt.Frame;
import java.awt.GridLayout;
// import gui.model.model.EditableAtomicModel;
import javax.swing.JPanel;
public class ModelTitleDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private EditableAtomicModel aGraph;
	private JPanel apanel;
	private ModelTitlePanel themodelTitlePanel;
	private String actualTitle;
	
	protected void jbInit() throws Exception {
		super.jbInit();
		apanel = new JPanel();
		themodelTitlePanel = new ModelTitlePanel();		
		apanel.setLayout(new GridLayout(1, 2, 5, 5));
		getContentPane().setLayout(new GridLayout(2, 1, 5, 5));
		getContentPane().add(themodelTitlePanel);
		apanel.add(getOkButton());
		apanel.add(getCancelButton());
		getContentPane().add(apanel);
		getOkButton().setText("Set");
		getOkButton().setMnemonic('S');

		themodelTitlePanel.gettitleTextField().setText(actualTitle);
		//I'm my own KeyListener so I can press
		//the OK button on Key-Enter Typed
		themodelTitlePanel.gettitleTextField().addKeyListener(this);

	}

	public String getModelTitle() {
		return themodelTitlePanel.gettitleTextField().getText();
	}
	
	public ModelTitleDialog(Frame frame, String title, boolean modal, String actualTitle) {
		super(frame, title, modal);
		this.actualTitle = actualTitle;
		try {
			jbInit();
			pack();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}