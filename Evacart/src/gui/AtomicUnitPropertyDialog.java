package gui;

import gui.model.unit.AbstractUnit;
import gui.model.unit.AtomicUnit;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;
public class AtomicUnitPropertyDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractUnit aState;
	private JPanel apanel;
	private AtomicUnitPropertyPanel aPropertyPanel;

	public AtomicUnitPropertyDialog(Frame frame, String title, boolean modal, AbstractUnit theState) {
		super(frame, title, modal);
		this.aState = theState;

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
		aPropertyPanel = new AtomicUnitPropertyPanel(aState);
		apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 2, 5, 5));
		getContentPane().setLayout(new GridLayout(2, 1, 5, 5));
		getContentPane().add(aPropertyPanel);
		apanel.add(getOkButton());
		apanel.add(getCancelButton());
		getContentPane().add(apanel);
		
		aPropertyPanel.getIDTextField().setText(aState.getName());
		AtomicUnit atomicState = (AtomicUnit)aState;
		aPropertyPanel.getTLTextField().setText( atomicState.getTimeAdvance());

		//I'm my own KeyListener so I can press
		//the OK button on Key-Enter Typed
		aPropertyPanel.getIDTextField().addKeyListener(this);
		aPropertyPanel.getTLTextField().addKeyListener(this);
	}

	protected void okButtonClicked() {
		aState.setName(aPropertyPanel.getIDTextField().getText());
		AtomicUnit atomicState = (AtomicUnit)aState;
		try{
			atomicState.setTimeAdvance(aPropertyPanel.getTLTextField().getText());
			super.okButtonClicked();
		}catch(Exception pex){
			(new InformDialog(pex.toString(),pex)).setVisible(true);
		}
	}

}