package gui.representation;

import gui.javax.util.Selectable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;



/**
 * @author jicidre@dc.uba.ar
 *
 */
public interface Representable extends Selectable {
	public boolean isOK();
	public int getHeight();
	public int getWidth();
	public void setHeight(int height);
	public void setWidth(int width);
	public void setText(String text);
	public String getText();
	public Dimension draw (Graphics g, ImageObserver observer);
	public void setLocation(Point location);
	
	/**
	 * Says where aPoint is inside this Unit
	 */
	public boolean inside(Point aPoint);	
	


}
