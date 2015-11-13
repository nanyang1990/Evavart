/*
 * Created on 22-may-2004
 * @author  jicidre@dc.uba.ar
 */
package gui.representation;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * DOCUMENT ME
 *
 */
public class SelfLinkImage implements Representable, Saveable {
	private boolean selected = false;
	private Point location;
	private Point linkEdgeOffset;	
	private Stroke stroke;
	
	private int width;
	private int height;	
	private String text;

	/**
	 * 
	 */
	public SelfLinkImage() {
		super();
	}

	/* (non-Javadoc)
	 * @see gui.representation.Representable#isOK()
	 */
	public boolean isOK() {
		return true;
	}

	/* (non-Javadoc)
	 * @see gui.representation.Representable#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see gui.representation.Representable#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see gui.representation.Representable#setHeight(int)
	 */
	public void setHeight(int height) {
		this.height = height;

	}

	/* (non-Javadoc)
	 * @see gui.representation.Representable#setWidth(int)
	 */
	public void setWidth(int width) {
		this.width = width;

	}

	/* (non-Javadoc)
	 * @see gui.representation.Adornable#setText(java.lang.String)
	 */
	public void setText(String text) {
		this.text = text;

	}

	/* (non-Javadoc)
	 * @see gui.representation.Adornable#getText()
	 */
	public String getText() {
		return text;
	}

	/* (non-Javadoc)
	 * @see gui.javax.util.Selectable#isSelected()
	 */
	public boolean isSelected() {
		return selected;
	}

	/* (non-Javadoc)
	 * @see gui.javax.util.Selectable#setSelected(boolean)
	 */
	public void setSelected(boolean selectornot) {
		this.selected = selectornot;

	}

	/* (non-Javadoc)
	 * @see gui.javax.util.Selectable#toggleSelected()
	 */
	public void toggleSelected() {
		setSelected(!isSelected());

	}

	/* (non-Javadoc)
	 * @see gui.representation.Drawable#draw(java.awt.Graphics, java.awt.image.ImageObserver)
	 */
	public Dimension draw(Graphics g, ImageObserver observer) {
		//Thread.dumpStack();
		Graphics2D a2DPen = (Graphics2D) g;
		a2DPen.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		a2DPen.setStroke(new BasicStroke(2));

		if (selected)
			a2DPen.setColor(Color.red);
		else
			a2DPen.setColor(Color.black);

		//OnePointLayoutable unit = ((OnePointLayoutable)getStartLinkPlugable()); 
		
		
		//Now the big deal is to draw the oval in the direction of the LinkEdge!!!!
		
		//Algorithm
		//Calculate an oval in an horizontal position (3:00) with on edge on the node and verticale centered at the Unit with fixed width
		
		int startx = getLocation().x;
		int starty = getLocation().y - getHeight()/2;
		int width = (int)Math.round((new Point(0,0)).distance(new Point(Math.abs(getLinkEdgeOffset().x),Math.abs(getLinkEdgeOffset().y))));
		int height = getHeight();

		//now, rotate it as needed	
		int x = getLinkEdgeOffset().x;
		int y = getLinkEdgeOffset().y;
		double radians = Math.atan2(y,x);
		a2DPen.rotate(radians, getLocation().x,getLocation().y);

		//draw
		a2DPen.drawOval(startx,starty,width ,height);
		
/*
		if ((getStartExpression() != null) && (getEndExpression() != null)) {
			a2DPen.drawString(
				getStartExpression().getShortDescription()
					+ "->"
					+ getEndExpression().getShortDescription(),
				(startx + width / 2) ,
				(starty ));
			//System.err.println(System.currentTimeMillis());
		}
*/
		//unrotate it
		a2DPen.rotate(-radians, getLocation().x,getLocation().y);
		return new Dimension(getLinkEdgeOffset().x, getLinkEdgeOffset().y);
	}

	/* (non-Javadoc)
	 * @see gui.representation.Drawable#setLocation(java.awt.Point)
	 */
	public void setLocation(Point location) {
		this.location = location;

	}

	/* (non-Javadoc)
	 * @see gui.representation.Drawable#inside(java.awt.Point)
	 */
	public boolean inside(Point aPoint) {
		return false;
	}

	/* (non-Javadoc)
	 * @see gui.representation.Saveable#saveTo(gui.javax.io.CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {

	}

	/* (non-Javadoc)
	 * @see gui.representation.Saveable#loadFrom(gui.javax.io.CommentBufferedReader)
	 */
	public void loadFrom(CommentBufferedReader reader) throws IOException {

	}

	/**
	 * @return
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return
	 */
	public Point getLinkEdgeOffset() {
		return linkEdgeOffset;
	}

	/**
	 * @param point
	 */
	public void setLinkEdgeOffset(Point point) {
		linkEdgeOffset = point;
	}

}
