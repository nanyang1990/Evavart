package gui.model.link;

import gui.graphEditor.Layoutable;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.Action;
import gui.model.Expression;
import gui.model.ExpressionAndValue;
import gui.model.model.AbstractModel;
import gui.representation.Arrow;
import gui.representation.Representable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public abstract class AbstractAtomicLink extends AbstractLink {
	private Object uniqueId;
	protected String LinkLabel;
	public java.util.Vector actions = new Vector();
	private Vector expressionsAndValues = new Vector();
    private boolean showExpressions = true;
    private boolean showActions = true;

	/**
	 * null constructor for Class.forName()
	 */
	public AbstractAtomicLink() {
		super();
	}

	public AbstractAtomicLink(Layoutable start, Layoutable end, Object id) {
		super(start, end, id);
	}

	public void addExpressionAndValue(Expression expression, String value){
		this.getExpressionsAndValues().add(new ExpressionAndValue(expression,value));
	}

	public void deleteExpressionAndValue(ExpressionAndValue expressionAndValue){
			this.getExpressionsAndValues().remove(expressionAndValue);
		}

	public void addAction(Action anAction) {
		actions.addElement(anAction);
	}
	public Vector getActions() {
		return actions;
	}
	public void deleteAction(Action anAction) {
		actions.remove(anAction);
	}

	public void setSelected(boolean ifselected) {
		selected = ifselected;
	}
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		writer.increaseTab();
		writer.increaseTab();
		super.saveTo(writer);

		//Expressions and values
		writer.writeln("cant Expressions", this.getExpressionsAndValues().size());
		Iterator expressionsAndValues =	this.getExpressionsAndValues().iterator();
		while(expressionsAndValues.hasNext()){
			ExpressionAndValue each = (ExpressionAndValue)expressionsAndValues.next();
			each.getExpression().saveObjectOrReferenceTo(writer);
			writer.writeln("Value ", each.getValue());
		}
		//Actions
		Iterator it = actions.iterator();

		writer.writeln("cant Actions", actions.size());
		while (it.hasNext()) {
			Action action = (Action) it.next();
			writer.writeln("Action", action.toString());
			writer.writeln("Action Id", (String)action.getUniqueId());
		}
		
		writer.decreaseTab();
		writer.decreaseTab();
	}
	public void loadOtherLinkDataFrom(CommentBufferedReader reader,AbstractModel graphContainerOfLink) throws Exception {

		//Expressions and values
		//cant Expressions
		int cantExpressions = Integer.parseInt(reader.readLine());
		for(int i = 0 ; i < cantExpressions ; i++){
			Expression eachExpr = Expression.loadOrFindFrom(reader,graphContainerOfLink);
			String eachValue = reader.readLine();
			this.addExpressionAndValue(eachExpr,eachValue);
			
		}

		//Actions
		int cantActions = Integer.parseInt(reader.readLine());
		for (int i = 0; i < cantActions; i++) {
			String action = reader.readLine();
			String actionId = reader.readLine();
			this.addAction(new Action(action, actionId));
		}
	}
	
	public String getShortDescription(){
		StringBuffer linkString = new StringBuffer();
		linkString.append("(" + this.getStartLinkPlugable().getName() + ")");
		linkString.append(" -> ");
		linkString.append("(" + this.getEndLinkPlugable().getName() + ")");
		return linkString.toString();
	}
	
	public String getDescription(){
		StringBuffer linkString = new StringBuffer();
		linkString.append(getShortDescription());
		linkString.append("\n");
		if(this.getActions().size() > 0){
		    linkString.append("Actions:\n");
		    for (Iterator iter = this.getActions().iterator(); iter.hasNext();) {
                Action a = (Action) iter.next();
                linkString.append(a.getActionString()+"\n");
            }
		}
		if(this.getExpressionsAndValues().size() > 0){
		    linkString.append("Expressions And Values:\n");
		    for (Iterator iter = this.getExpressionsAndValues().iterator(); iter.hasNext();) {
                ExpressionAndValue e = (ExpressionAndValue) iter.next();
                linkString.append(e.getExpression().getShortDescription()+ "!" + e.getValue() + "\n");
            }
		}

		
		return linkString.toString();
	}
	


	public Layoutable otherEndFrom(Layoutable aState) {
		if (startLinkPlugable == aState)
			return endLinkPlugable;
		else
			return startLinkPlugable;
	}
	
	public abstract String getLinkInternalExternal();


	/**
	 * Returns the uniqueId.
	 * @return Object
	 */
	public Object getUniqueId() {
		return uniqueId;
	}

	/**
	 * Sets the uniqueId.
	 * @param uniqueId The uniqueId to set
	 */
	public void setUniqueId(Object uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void unlink(Expression ex){
		/*
		if(ex.equals(this.getStartExpression())){
			this.setStartExpression(null);
		}
		if(ex.equals(this.getEndExpression())){
			this.setEndExpression(null);
		}
		*/
		throw new RuntimeException("Redo this");
	}

	/**
	 * Returns the expressionsAndValues.
	 * @return Vector
	 */
	public Vector getExpressionsAndValues() {
		return expressionsAndValues;
	}

	/**
	 * Sets the expressionsAndValues.
	 * @param expressionsAndValues The expressionsAndValues to set
	 */
	public void setExpressionsAndValues(Vector expressionsAndValues) {
		this.expressionsAndValues = expressionsAndValues;
	}
	
	/* (non-Javadoc)
	 * @see gui.model.AbstractLink#prepareImage(gui.representation.Representable)
	 */
	protected void prepareImage(Representable image) {
		super.prepareImage(image);
		((Arrow)image).setHeadRetraction(this.getEndLinkPlugable().getImage().getWidth()/2);
	}

	/**
		 * Draw this element in the Graphics
		 * Creation date: (14/08/2002 12:41:43)
		 * @param aPen java.awt.Graphics
		 */
	public Dimension draw(Graphics aPen, ImageObserver observer) {
		return this.getImage().draw(aPen, observer);		
		
	}

    /**
     * @return Returns the showActions.
     */
    public boolean isShowActions() {
        return showActions;
    }
    /**
     * @param showActions The showActions to set.
     */
    public void setShowActions(boolean showActions) {
        this.showActions = showActions;
    }
    /**
     * @return Returns the showExpressions.
     */
    public boolean isShowExpressions() {
        return showExpressions;
    }
    /**
     * @param showExpressions The showExpressions to set.
     */
    public void setShowExpressions(boolean showExpressions) {
        this.showExpressions = showExpressions;
    }
    
	
}