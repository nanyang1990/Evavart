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
public class Square implements  Representable, Saveable{
	private boolean selected = false;
	private Point location;
	private int width;
	private int height;	
	private String text;

	/**
	 * Constructor for Square.
	 */
	public Square() {
		super();
	}

	/**
	 * @see gui.representation.SelectableDrawable#setSelected(boolean)
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @see gui.representation.SelectableDrawable#isSelected()
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @see gui.representation.Drawable#draw(Graphics)
	 */
	public Dimension draw(Graphics a1DPen, ImageObserver observer) {
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
		aPen.fillRect(getLocation().x - getWidth() / 2, getLocation().y - getHeight() / 2, getWidth(), getHeight());
		//draw the name of the Unit
		a1DPen.drawString(this.getText(), getLocation().x - getWidth() / 2 , getLocation().y - getHeight() / 2 );
		
		return new Dimension(getLocation().x+ getWidth(), getLocation().y+getHeight());
		
	}

	/**
	 * @see gui.representation.Drawable#setLocation(Point)
	 */
	public void setLocation(Point location) {
		this.location = location;
		
	}

	/**
	 * Returns the edge.
	 * @return int
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the location.
	 * @return Point
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Returns the text.
	 * @return String
	 */
	public String getText() {
		if(text == null){
			text = "";
		}
		return text;
	}

	/**
	 * Sets the edge.
	 * @param edge The edge to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets the text.
	 * @param text The text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the height.
	 * @return int
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 * @param height The height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @see gui.representation.Drawable#inside(Point)
	 */
	public boolean inside(Point aPoint) {
		int xPos = getLocation().x - (getWidth()/2);
		int yPos = getLocation().y - (getHeight()/2);

		if(aPoint.x > xPos && aPoint.x < xPos+getWidth() && aPoint.y > yPos && aPoint.y < yPos+getHeight()){
			return true;
		}		
		else{
			return false;
		}
	}
	public void toggleSelected() {
		setSelected(!isSelected());
	}
	
	/**
	 * @see gui.representation.Drawable#saveTo(CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException{
		writer.increaseTab();
		writer.writeln("class" , this.getClass().getName());
		writer.writeln("height", this.getHeight());
		writer.writeln("width", this.getWidth());
		writer.decreaseTab();		
	}

	/**
	 * @see gui.representation.Drawable#loadFrom(CommentBufferedReader)
	 */
	public void loadFrom(CommentBufferedReader reader) throws IOException {
		this.setHeight(Integer.parseInt(reader.readLine()));
		this.setWidth(Integer.parseInt(reader.readLine()));
	}

	/**
	 * @see gui.representation.Representable#isOK()
	 */
	public boolean isOK() {
		return true;
	}

}
