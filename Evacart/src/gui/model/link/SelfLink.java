package gui.model.link;

import gui.graphEditor.Layoutable;
import gui.graphEditor.OnePointLayoutable;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.Expression;
import gui.model.model.AbstractModel;
import gui.representation.Representable;
import gui.representation.SelfLinkImage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * @author Administrador
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SelfLink extends AbstractCoupledLink {
	private Point linkEdgeOffset;

	/**
	 * null constructor for Class.forName()
	 */
	public SelfLink() {}

	public SelfLink(Layoutable unit, Point linkEdge, Object id) {
		super(unit, unit, id);

		Point startLocation = ((OnePointLayoutable)unit).getLocation();

		this.linkEdgeOffset = new Point(linkEdge.x - startLocation.x, linkEdge.y - startLocation.y);

		if (unit instanceof Expression) {
			this.setStartExpression((Expression)unit);
			this.setEndExpression((Expression)unit);
		}

	}
	/* (non-Javadoc)
	 * @see gui.model.link.AbstractLink#prepareImage(gui.representation.Representable)
	 */
	protected void prepareImage(Representable image) {
		super.prepareImage(image);

		image.setWidth(getStartLinkPlugable().getImage().getWidth());
		image.setHeight(getStartLinkPlugable().getImage().getHeight());		
		((SelfLinkImage)image).setLinkEdgeOffset(getLinkEdgeOffset());
	}


	/**
	 * @see gui.graphEditor.Layoutable#draw(Graphics, ImageObserver)
	 */
	public Dimension draw(Graphics aPen, ImageObserver observer) {
		return getImage().draw(aPen, observer);
	}
	/* (non-Javadoc)
	 * @see gui.model.link.AbstractLink#getImageClass()
	 */
	protected Representable getImageClass() {

		return new SelfLinkImage();
	}

	/**
	 * @return
	 */
	private Point getLinkEdgeOffset() {
		return linkEdgeOffset;
	}

	/**
	 * @param point
	 */
	private void setLinkEdgeOffset(Point point) {
		linkEdgeOffset = point;
	}
	
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		super.saveTo(writer);
		writer.increaseTab();
		writer.increaseTab();
		writer.writeln("linkEdgeOffset x", getLinkEdgeOffset().x);
		writer.writeln("linkEdgeOffset y", getLinkEdgeOffset().y);		
		writer.decreaseTab();
		writer.decreaseTab();
	}

	public void loadOtherLinkDataFrom(CommentBufferedReader reader, AbstractModel graphContainerOfLink)
		throws Exception {
		super.loadOtherLinkDataFrom(reader,graphContainerOfLink);
		int x = Integer.parseInt(reader.readLine());
		int y = Integer.parseInt(reader.readLine());
		this.setLinkEdgeOffset(new Point(x,y));
	}
	

}
