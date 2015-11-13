package gui;
import gui.model.Expression;
import gui.model.ExpressionAndValue;
import gui.model.Function;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;

public class AddExpressionAndValueDialog extends OkCancelJDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel apanel;
	private AddExpressionAndValuePanel theAddExpressionAndValuePanel;
	//private ExpressionAndValue anewExpressionAndValue = null;
	private Expression expression;
	private String value;
	

	public AddExpressionAndValueDialog(Frame frame, String title, boolean modal, Vector<?> thePorts) {
		super(frame, title, modal);
		theAddExpressionAndValuePanel = new AddExpressionAndValuePanel(thePorts);
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
		getContentPane().add(theAddExpressionAndValuePanel);
		apanel.add(getOkButton());
		apanel.add(getCancelButton());
		getContentPane().add(apanel);

		//I'm my own KeyListener so I can press
		//the OK button on Key-Enter Typed
		theAddExpressionAndValuePanel.getExpressionTextField().addKeyListener(this);
		theAddExpressionAndValuePanel.getValueTextField().addKeyListener(this);		
	}
	

	
	public void setExpressionAndValue(ExpressionAndValue aExpressionAndValue) {
		theAddExpressionAndValuePanel.getExpressionTextField().setText(aExpressionAndValue.getExpression().toString());
		String aValue = aExpressionAndValue.getValue();
		theAddExpressionAndValuePanel.getValueTextField().setText(aValue);
	}

	protected void okButtonClicked() {
		if (theAddExpressionAndValuePanel.getExpressionTextField().getText().length() != 0) {
			setExpression(new Function(theAddExpressionAndValuePanel.getExpressionTextField().getText()));
			setValue(theAddExpressionAndValuePanel.getValueTextField().getText());
			
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