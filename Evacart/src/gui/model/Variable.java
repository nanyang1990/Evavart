package gui.model;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.util.Identifiable;

import java.io.IOException;

public class Variable implements Identifiable, Descriptable{
	
	private Object uniqueId;	
	protected String name = new String();
	protected String value = new String();

	public Variable() {
	}

	public Variable(String aName, String aValue, Object id) {
		name = aName;
		value = aValue;
		setUniqueId(id);
	}

	public void setValue(String aString) {
		value = aString;
	}
	public String getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
	public void setName(String aString) {
		name = aString;
	}

	public void saveTo(CommentBufferedWriter writer) throws IOException {
		writer.writeln("varName", getName());
		writer.writeln("varVal", getValue());
		writer.writeln("Var UniqueId", this.getUniqueId().toString());		
	}

	public static Variable loadFrom(CommentBufferedReader reader) throws IOException {
		Variable var = new Variable();
		String varname = reader.readLine();
		String value = reader.readLine();		
		String id = reader.readLine();
		var.setName(varname);
		var.setValue(value);
		var.setUniqueId(id);
		return var;
	}
	
	/* (non-Javadoc)
	 * @see gui.javax.util.Identifiable#getUniqueId()
	 */
	public Object getUniqueId() {
		return uniqueId;
	}

	/* (non-Javadoc)
	 * @see gui.javax.util.Identifiable#setUniqueId(java.lang.Object)
	 */
	public void setUniqueId(Object id) {
		this.uniqueId = id;
	}

	public String toString() {
		return getName()+": " + getValue();
	}

	/* (non-Javadoc)
	 * @see gui.model.Descriptable#getDescription()
	 */
	public String getDescription() {
		return "Name: " + getName() + "\nValue: " + getValue();
	}
	
	public String getShortDescription(){
	    return getDescription();
	}
}