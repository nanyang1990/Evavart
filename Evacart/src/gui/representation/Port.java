package gui.representation;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.awt.Point;
import java.io.IOException;

/**
 * @author Administrador
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class Port implements Representable, Saveable {
	private boolean selected = false;
	private Point location;
	private int width;
	private int height;	
	private String text;

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
		writer.writeln("  class" , this.getClass().getName());
		writer.writeln("  radius", this.getHeight());
		writer.writeln("  width", this.getWidth());		
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
