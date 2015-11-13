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
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class Circle implements Representable, Saveable {
	private boolean selected = false;
	private Point location;
	private int radius;
	private String text;
		
	/**
	 * Constructor for Circle.
	 */
	public Circle() {
		super();
	}
	
	public Dimension draw(Graphics a1DPen, ImageObserver oblserver){
		Graphics2D aPen = (Graphics2D) a1DPen;
		aPen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		aPen.setStroke(new BasicStroke(2));
		
		//Si estoy seleccionado me dibujo en rojo, sino en azul
		if (isSelected()) {
			//estoy seleccionado!!!! en rojo
			aPen.setColor(Color.red);
		}
		else {
			//No estoy seleccionado!!! en azul
			aPen.setColor(Color.blue);
		}
		
		//dibujo el circulito
		int locationX = getLocation().x - getRadius();
		int locationY = getLocation().y - getRadius();
		int side = getRadius() *2;
		aPen.fillOval(locationX, locationY, side,side);
		//dibujo el nombrecito.
		aPen.drawString(getText(), getLocation().x - getRadius(), getLocation().y - getRadius());

		Dimension retr =new Dimension(getLocation().x + getRadius() , getLocation().y + getRadius());
		
		return 	retr;

	}

	/**
	 * @see gui.representation.SelectableDrawable#isSelected()
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @see gui.representation.SelectableDrawable#setSelected(boolean)
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Point getLocation() {
		return this.location;
	}
	public void setLocation(Point aPoint) {
		this.location = aPoint;
	}

	/**
	 * Returns the radius.
	 * @return int
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Returns the text.
	 * @return String
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the radius.
	 * @param radius The radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Sets the text.
	 * @param text The text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @see gui.representation.Drawable#inside(Point)
	 */
	public boolean inside(Point aPoint) {
		if(getLocation().distance(aPoint) > getRadius()){
			return false;
		}
		else{
			return true;
		}
	}
	public void toggleSelected() {
		setSelected(!isSelected());
	}

	/**
	 * @see gui.representation.Drawable#saveTo(CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException{
		writer.writeln("  class" , this.getClass().getName());		
		writer.writeln("  radius", this.getRadius());
	}

	/**
	 * @see gui.representation.Drawable#loadFrom(CommentBufferedReader)
	 */
	public void loadFrom(CommentBufferedReader reader) throws IOException {
		 this.setRadius(Integer.parseInt(reader.readLine()));
	}
	/**
	 * @see gui.representation.Representable#isOK()
	 */
	public boolean isOK() {
		return true;
	}

	/**
	 * @see gui.representation.Representable#getHeight()
	 */
	public int getHeight() {
		return getRadius()*2;
	}

	/**
	 * @see gui.representation.Representable#getWidth()
	 */
	public int getWidth() {
		return getRadius()*2;
	}


	/**
	 * @see gui.representation.Representable#setHeight(int)
	 */
	public void setHeight(int height) {
		setRadius(height/2);
	}

	/**
	 * @see gui.representation.Representable#setWidth(int)
	 */
	public void setWidth(int width) {
		setRadius(width/2);		
	}

}
