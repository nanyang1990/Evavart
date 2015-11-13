package gui.model;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.link.AbstractLink;
import gui.model.model.AbstractModel;

import java.io.IOException;


/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class Function extends Expression {

	private String functionString;
		
	/**
	 * Constructor for Function.
	 */
	public Function() {
		super();
		
	}

	/**
	 * Constructor for Function.
	 * @param aString
	 */
	public Function(String aString) {
		setFunctionString(aString);
	}

	public void setExpressionString(String aString) {
		setFunctionString(aString);
	}

	/**
	 * @see gui.model.Expression#addLink(AbstractLink)
	 */
	public void addLink(AbstractLink aLink) {
	}

	
	public String toString() {
		return getFunctionString();
	}

	public String getShortDescription(){
		return getFunctionString();	
	}

	/**
	 * @see gui.model.Expression#removeLink(AbstractLink)
	 */
	public void removeLink(AbstractLink aLink) {
	}

	protected void setFunctionString(String functionString) {
		this.functionString = functionString;
	}

	public String getFunctionString() {
		return functionString;
	}

	public void saveObjectOrReferenceTo(CommentBufferedWriter writer) throws IOException {
		writer.writeln("Expr Type", this.getClass().getName());
		writer.writeln("Expr", getFunctionString());
	}
	

	/**
	 * @see gui.model.Expression#createInstance(CommentBufferedReader, Model)
	 */
	protected Expression createInstance(CommentBufferedReader reader, AbstractModel graph) throws IOException {
		return new Function(reader.readLine());	
	}

}
