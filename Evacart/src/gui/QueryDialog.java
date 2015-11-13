/*
 * Created on 15/04/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author jcidre
 *
 */
public class QueryDialog extends OkCancelJDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel buttonsPanel;
	private JLabel textLabel;
	private String message;
	public QueryDialog(String message){
		super();
		this.message = "    "+message+"    ";
		try {
			jbInit();
			pack();

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		
	
			
	}
	public JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.add(getOkButton());
			buttonsPanel.add(getCancelButton());
			buttonsPanel.setBackground(Color.WHITE);
		}
		return buttonsPanel;
	}

	public JLabel getTextLabel() {
		if (textLabel == null) {
			textLabel = new JLabel();
			textLabel.setHorizontalAlignment(JLabel.CENTER);
			//textLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			textLabel.setOpaque(false);
		
		}
		return textLabel;
	}
	
	protected void jbInit() throws Exception {
		super.jbInit();

		this.setTitle("Warning Message");
		this.setModal(true);
		this.getContentPane().setBackground(Color.WHITE);
		

		getTextLabel().setText(message);

		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(20);
		borderLayout.setVgap(20); 
		getContentPane().setLayout(borderLayout);
		getContentPane().add(getTextLabel(),BorderLayout.CENTER);
		getContentPane().add(getButtonsPanel(),BorderLayout.SOUTH);


		
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
				
		this.setLocation((screenSize.width - frameSize.width) / 2, 
						(screenSize.height - frameSize.height) / 2);

	}

	

}
