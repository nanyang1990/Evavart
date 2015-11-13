package gui.model;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.model.AbstractModel;

import java.io.IOException;


/**
 * As is defined in the grammar, an Expression may be a Port, a Function, a Variable or a Constant.
 * The Links has Expressions on their extrems.
 */
public abstract class Expression {
	public abstract void saveObjectOrReferenceTo(CommentBufferedWriter writer) throws IOException;
	protected abstract Expression createInstance(CommentBufferedReader reader, AbstractModel graph) throws IOException;	

	/**
	 * Returns a short String that describes
	 * this Expression. Ideal to use in TreeViews
	 */
	public abstract String getShortDescription();



	/**
	 * needs to be named diferent than the rest of the clases (loadFrom) because it has a different
	 * mechanism. If it is a Port, we have to find it.
	 * There is a collission beetwen this class and Port if we use loadFrom
	 */
	public static Expression loadOrFindFrom(CommentBufferedReader reader,AbstractModel graph) throws Exception {
		String exprClass = reader.readLine();
		if(!exprClass.equals("")){
			Expression expr = (Expression)(java.lang.Class.forName(exprClass)).newInstance();
			return expr.createInstance(reader,graph);
		}
		else{
			return null;
		}
	}
		
	
}