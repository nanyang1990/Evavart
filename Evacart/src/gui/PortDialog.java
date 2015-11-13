package gui;

import gui.model.port.AbstractPort;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;
public class PortDialog extends OkCancelJDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JPanel apanel;
	private PortPanel portPanel;
	private AbstractPort port = null;

	protected void jbInit() throws Exception {
		super.jbInit();
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
		buttonsPanel.add(getOkButton());
		buttonsPanel.add(getCancelButton());

		BorderLayout mainLayout = new BorderLayout(5,5);
		getContentPane().setLayout(mainLayout);
		getContentPane().add(getPortPanel(),BorderLayout.CENTER);
		getContentPane().add(buttonsPanel,BorderLayout.SOUTH);

		getPortPanel().getPortIDTextField().addKeyListener(this);
		getPortPanel().getInorOutComboBox().addKeyListener(this);
		getPortPanel().getPortTypeComboBox().addKeyListener(this);
		
	}

	public AbstractPort getPort() {
		return port;
	}

	private String checkPortData(){
		StringBuffer retr = new StringBuffer();
		
		if(getPortPanel().getPortIDTextField().getText().length() == 0){
			retr.append("Set a Port ID\n");
		}
		if((getPortPanel().getInorOutComboBox().getSelectedItem()) == null){
			retr.append("Set port In/Out\n");
		}
		if((getPortPanel().getPortTypeComboBox().getSelectedItem()) == null){
			retr.append("Set port type\n");
		}
		return retr.toString();
		
	}
	protected void okButtonClicked() {
		String checkPortData = checkPortData(); 
		if (checkPortData.length() == 0) {
			getPort().setPortId(getPortPanel().getPortIDTextField().getText());
			getPort().setInOrOut((String) getPortPanel().getInorOutComboBox().getSelectedItem());
			getPort().setPortType((String) getPortPanel().getPortTypeComboBox().getSelectedItem());
			super.okButtonClicked();
		}
		else{
			(new InformDialog(checkPortData,null)).setVisible(true);
		}
	}
	
	public PortDialog(Frame frame, String title, boolean modal, AbstractPort aPort) {
		super(frame, title, modal);
		//this.theContainer = theContainer;
		setPortPanel( new PortPanel());
		try {
			jbInit();
			pack();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		this.setPort(aPort);
	}
	/**
	 * @param port
	 */
	public void setPort(AbstractPort port) {
		this.port = port;
		getPortPanel().getPortIDTextField().setText(getPort().getPortId());
		getPortPanel().getInorOutComboBox().setSelectedItem(getPort().getInOrOut());
		getPortPanel().getPortTypeComboBox().setSelectedItem(getPort().getPortType());
	}

	/**
	 * @return
	 */
	public PortPanel getPortPanel() {
		return portPanel;
	}

	/**
	 * @param panel
	 */
	public void setPortPanel(PortPanel panel) {
		portPanel = panel;
	}

}