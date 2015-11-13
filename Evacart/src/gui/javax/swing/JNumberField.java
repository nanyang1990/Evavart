/*
 * Created on 22-feb-2004
 * @author  jicidre@dc.uba.ar
 */
package gui.javax.swing;

import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * DOCUMENT ME
 *
 */
public class JNumberField extends JTextField {

	/**
	 * 
	 */
	public JNumberField() {
		super();
		
	}

	/**
	 * @param columns
	 */
	public JNumberField(int columns) {
		super(columns);
	}

	/**
	 * @param text
	 */
	public JNumberField(String text) {
		super(text);
	}

	/**
	 * @param text
	 * @param columns
	 */
	public JNumberField(String text, int columns) {
		super(text, columns);
	}

	/**
	 * @param doc
	 * @param text
	 * @param columns
	 */
	public JNumberField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	/**
	 * @param f
	 */
	public void setNumber(float f) {
		this.setText(""+f);
	}

	public float getNumber(){
		try {
			return Float.parseFloat(this.getText());
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}
}
