package gui;
import gui.model.Expression;
import gui.model.link.AbstractLink;
import gui.model.port.AbstractPort;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class AddPortAndValueDialog extends OkCancelJDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel apanel;
	private AddPortAndValuePanel theAddPortAndValuePanel;
	//private ExpressionAndValue anewExpressionAndValue = null;
	private Expression expression;
	private String value;
	

	public AddPortAndValueDialog(Frame frame, String title, boolean modal, AbstractLink aLink, Vector<?> thePorts) {
		super(frame, title, modal);
		theAddPortAndValuePanel = new AddPortAndValuePanel();
		try {
			jbInit();
			JComboBox thePortsComboBox = theAddPortAndValuePanel.getPortsComboBox();
			Iterator<?> it = thePorts.iterator();
			while  (it.hasNext()) {
				AbstractPort aPort = (AbstractPort)it.next();
				if (aPort.getInOrOut().equals("Out")) {
					thePortsComboBox.addItem(aPort);
				}
			}
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
		getContentPane().add(theAddPortAndValuePanel);
		apanel.add(getOkButton());
		apanel.add(getCancelButton());
		getContentPane().add(apanel);

		//I'm my own KeyListener so I can press
		//the OK button on Key-Enter Typed
		theAddPortAndValuePanel.getPortsComboBox().addKeyListener(this);
		theAddPortAndValuePanel.getValueTextField().addKeyListener(this);		
	}
	

	
	
	protected void okButtonClicked() {
		if (theAddPortAndValuePanel.getPortsComboBox().getSelectedIndex() != -1) {
			setExpression((AbstractPort)theAddPortAndValuePanel.getPortsComboBox().getSelectedItem());
			setValue(theAddPortAndValuePanel.getValueTextField().getText());
			
			
		}
		super.okButtonClicked();
	}
	
    /**
     * @return Returns the expression.
     */
    public Expression getExpression() {
        return expression;
    }
    /**
     * @param expression The expression to set.
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    /**
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value The value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }
}