package gui.model.link;

import gui.graphEditor.Layoutable;
import gui.model.Action;
import gui.model.ExpressionAndValue;
import gui.representation.Arrow;
import gui.representation.Representable;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Iterator;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public class AtomicInternalLink extends AbstractAtomicLink {
		
	public AtomicInternalLink(){
		super();
	}

	public AtomicInternalLink(Layoutable start, Layoutable end,Object id){
		super(start, end, id);
	}


	/**
	 * @see gui.model.AtomicLink#getLinkInternalExternal()
	 */
	public String getLinkInternalExternal() {
		return "int";
	}

	public String getLinkLabel() {
		
		StringBuffer linkLabel = new StringBuffer();
		
		//append the start Unit's name
		linkLabel.append(this.getStartLinkPlugable().getName());
		
		//append the arrow
		linkLabel.append(" -> ");
		
		//append the end Unit's name
		linkLabel.append(this.getEndLinkPlugable().getName());
		
		//append the @
		linkLabel.append("@"); 
		
		//append all the Expressions:Values one by one
		Iterator expressionsAndValues = this.getExpressionsAndValues().iterator();
		
		while(expressionsAndValues.hasNext()){
			//obtain next
			ExpressionAndValue each = (ExpressionAndValue)expressionsAndValues.next();
			
			//append the name
			linkLabel.append(each.getExpression().getShortDescription()+":"+each.getValue()+" ");
		
		}
		
		return linkLabel.toString();
	}
	/* (non-Javadoc)
	 * @see gui.model.AbstractLink#prepareImage(gui.representation.Representable)
	 */
	protected void prepareImage(Representable image) {
		super.prepareImage(image);

		float dash1[] = {10.0f};		
		Stroke tempStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,  BasicStroke.JOIN_MITER, 10.0f,dash1, 0.0f);
		
		((Arrow)image).setStroke(tempStroke);

		StringBuffer linkText = new StringBuffer();
		
		if(isShowExpressions()){
			for (Iterator exps = this.getExpressionsAndValues().iterator(); exps.hasNext();) {
	            ExpressionAndValue each = (ExpressionAndValue) exps.next();
	            linkText.append(each.getExpression()+"!"+each.getValue() + " ");
	        }
		}
		//show the actions
		if(isShowActions()){
     		for (Iterator actions = this.getActions().iterator(); actions.hasNext();) {
    		    Action each = (Action) actions.next();
                linkText.append("\n" + each.getActionString());
            }
    	}

		image.setText(linkText.toString());
		
		
		
	}
	
}
