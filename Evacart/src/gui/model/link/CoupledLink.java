package gui.model.link;

import gui.graphEditor.Layoutable;
import gui.model.Expression;
import gui.representation.Arrow;
import gui.representation.Representable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class CoupledLink extends AbstractCoupledLink {

	/* (non-Javadoc)
	 * @see gui.model.AbstractLink#prepareImage(gui.representation.Representable)
	 */
	protected void prepareImage(Representable image) {
		super.prepareImage(image);
		((Arrow)image).setHeadRetraction(this.getEndLinkPlugable().getImage().getWidth()/2);

		StringBuffer linkText = new StringBuffer();
		
		if(isShowPorts()){
		    linkText.append(this.getDescription());
		}
		image.setText(linkText.toString());
		
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
	 * null constructor for Class.forName()
	 */
	public CoupledLink() {

	}

	public CoupledLink(Layoutable start, Layoutable end,Object id) {
		super(start, end, id);

		if(start instanceof Expression){
			this.setStartExpression((Expression)start);
		}
		
		if(end instanceof Expression){
			this.setEndExpression((Expression)end);
		}
	
	}

}