package gui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JPanel;
import javax.swing.JTextArea;



/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class InformDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel buttonsPanel;
	private JTextArea textField;
	private String message;
	public static void showError(String message,Throwable t){
		(new InformDialog(message, t)).setVisible(true);
	}
	public InformDialog(String message,Throwable t){
		super();
		if(t != null){
		    this.message = message + " " + t.getMessage();    
		}
		else{
		    this.message = message;    
		}
		
		/*FOR DEBUGGING PORPOSES*/
		if(t != null){
			t.printStackTrace();
			StringWriter writer = new StringWriter();
			t.printStackTrace(new PrintWriter(writer));
			this.message += writer.toString();

		}
		/**/
		
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
			buttonsPanel.setBackground(Color.WHITE);
		}
		return buttonsPanel;
	}

	public JTextArea getTextField() {
		if (textField == null) {
			textField = new JTextArea();
			textField.setOpaque(false);
			textField.setMargin(new Insets(5,5,5,5));
			textField.setEditable(false); //Chiril Chidisiuc: 2006-08-30... 
										  //this prevents client to change contents of warning message...
		}
		return textField;
	}
	
	protected void jbInit() throws Exception {
		super.jbInit();

		this.setTitle("Warning Message");
		this.setModal(true);
		this.getContentPane().setBackground(Color.WHITE);
		

		getTextField().setText(message);

		BorderLayout borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		getContentPane().add(getTextField(),BorderLayout.CENTER);
		getContentPane().add(getButtonsPanel(),BorderLayout.SOUTH);


		
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = this.getSize();
				
		this.setLocation((screenSize.width - frameSize.width) / 2, 
						(screenSize.height - frameSize.height) / 2);

	}

	
}
