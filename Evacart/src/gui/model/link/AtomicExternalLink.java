package gui.model.link;

import gui.graphEditor.Layoutable;
import gui.model.Action;
import gui.model.Expression;
import gui.model.ExpressionAndValue;
import gui.representation.Representable;

import java.util.Iterator;

/**
 * @author jicidre@dc.uba.ar
 *  
 */
public class AtomicExternalLink extends AbstractAtomicLink {

    public AtomicExternalLink() {
        super();
    }

    public AtomicExternalLink(Layoutable start, Layoutable end, Object id) {
        super(start, end, id);
    }

    /**
     * @see gui.model.AtomicLink#getLinkInternalExternal()
     */
    public String getLinkInternalExternal() {
        return "ext";
    }

    public String getLinkLabel() {

        StringBuffer linkLabel = new StringBuffer();

        //append the start Unit's name
        linkLabel.append(this.getStartLinkPlugable().getName());

        //append the @
        linkLabel.append("@");

        //append all the Expressions:Values one by one
        Iterator expressionsAndValues = this.getExpressionsAndValues().iterator();

        while (expressionsAndValues.hasNext()) {
            //obtain next
            ExpressionAndValue each = (ExpressionAndValue) expressionsAndValues.next();

            //append the name
            linkLabel.append(each.getExpression().getShortDescription() + ":" + each.getValue() + " ");

        }

        //append the arrow
        linkLabel.append(" -> ");

        //append the end Unit's name
        linkLabel.append(this.getEndLinkPlugable().getName());

        return linkLabel.toString();
    }

    /**
     * Ensure that there is only one (non-Javadoc)
     * 
     * @see gui.model.link.AbstractAtomicLink#addExpressionAndValue(gui.model.Expression,
     *      java.lang.String)
     */
    public void addExpressionAndValue(Expression expression, String value) {
        getExpressionsAndValues().clear();
        if (expression != null && value != null) {
            super.addExpressionAndValue(expression, value);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see gui.model.link.AbstractAtomicLink#prepareImage(gui.representation.Representable)
     */
    protected void prepareImage(Representable image) {
        super.prepareImage(image);
        StringBuffer linkText = new StringBuffer();
        if (isShowExpressions()) {
            for (Iterator exps = this.getExpressionsAndValues().iterator(); exps.hasNext();) {
                ExpressionAndValue each = (ExpressionAndValue) exps.next();
                linkText.append(each.getExpression() + "?" + each.getValue() + " ");
            }
        }

        //show the actions
        if (isShowActions()) {
            for (Iterator actions = this.getActions().iterator(); actions.hasNext();) {
                Action each = (Action) actions.next();
                linkText.append("\n" + each.getActionString());
            }
        }

        image.setText(linkText.toString());
    }
}
