package gui;
import gui.model.Variable;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class VariableDialog extends OkCancelJDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel apanel;
	private VariablePanel variablePanel;
	private Variable variable;

	public VariableDialog(Frame frame, String title, boolean modal, Variable var) {
		super(frame, title, modal);
		this.setVariable(var);
		variablePanel = new VariablePanel();
		this.setSize(200, 150);		
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
		getContentPane().add(getVariablePanel());
		apanel.add(getOkButton());
		apanel.add(getCancelButton());
		getContentPane().add(apanel);

		//I'm my own KeyListener so I can press
		//the OK button on Key-Enter Typed
		getVariablePanel().getValueTextField().addKeyListener(this);
		getVariablePanel().getNameTextField().addKeyListener(this);		
	}

	public Variable getVariable() {
		return variable;
	}

	protected void okButtonClicked() {
		if (getVariablePanel().getNameTextField().getText().length() != 0) {
			getVariable().setName(getVariablePanel().getNameTextField().getText());
			getVariable().setValue(getVariablePanel().getValueTextField().getText());
		}
		super.okButtonClicked();
	}

	/**
	 * @param variable
	 */
	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	/**
	 * @return
	 */
	public VariablePanel getVariablePanel() {
		return variablePanel;
	}

	/**
	 * @param panel
	 */
	public void setVariablePanel(VariablePanel panel) {
		variablePanel = panel;
	}

}