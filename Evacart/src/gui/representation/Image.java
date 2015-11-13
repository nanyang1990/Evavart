package gui.representation;


import gui.image.Repository;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class Image implements Representable, Saveable{
	
	private String text;
	private boolean selected;
	private Point location;
	private File imageFileName;
	private int width;
	private int height;	
	


	/**
	 * Constructor for Image.
	 */
	public Image(File imageName) {
		super();
		this.setImageFileName(new File(imageName.getName()));
	}

	/**
	 * Constructor for Class for name.
	 */
	public Image() {
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
	public Dimension draw(Graphics g, ImageObserver observer) {
		Graphics2D aPen = (Graphics2D) g;
		
		aPen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//aPen.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		//aPen.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		//aPen.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		
		aPen.setStroke(new BasicStroke(2));
/*		
		if(isSelected()){
			aPen.setXORMode(Color.RED);
		}
*/		
		String imageFilename = Repository.getInstance().getImage(getImageFileName()).getPath();
		
		

		//System.out.println("Image draw " + imageFilename);
		java.awt.Image image =  Toolkit.getDefaultToolkit().getImage(imageFilename);

		int xPos = getLocation().x - (getWidth()/2);
		int yPos = getLocation().y - (getHeight()/2);

		Color originalColor = aPen.getColor();
		if(isSelected()){
			aPen.setColor(Color.RED);
			aPen.setBackground(Color.RED);
		}
		else{
			
		}
		aPen.drawImage(image,xPos,yPos, width,height,observer);
		//aPen.drawImage(image,xPos,yPos, image.getWidth(observer),image.getHeight(observer),observer);
		//dibujo el nombrecito.
		aPen.drawString(getText(), xPos, yPos);
	
		//reset the color
		aPen.setColor(originalColor);
		return new Dimension(xPos+width,yPos+height);
		
	}

	/**
	 * @see gui.representation.Drawable#setLocation(Point)
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * Returns the imageName.
	 * @return String
	 */
	protected File getImageFileName() {
		return imageFileName;
	}

	/**
	 * Returns the location.
	 * @return Point
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Sets the imageName.
	 * @param imageName The imageName to set
	 */
	protected void setImageFileName(File imageName) {
		this.imageFileName = imageName;
		//System.out.println("Image file : " + imageName);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return imageFileName.getPath();
	}

	/**
	 * Returns the text.
	 * @return String
	 */
	public String getText() {
		return text;
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
		java.awt.Image image =  Toolkit.getDefaultToolkit().getImage(getImageFileName().getPath());

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
		writer.writeln("  filename", this.getImageFileName().getName());		
	}

	/**
	 * @see gui.representation.Drawable#loadFrom(CommentBufferedReader)
	 */
	public void loadFrom(CommentBufferedReader reader) throws IOException {
		this.setImageFileName(new File(reader.readLine()))	;
	}

	/**
	 * @see gui.representation.Representable#isOK()
	 */
	public boolean isOK() {
		if(this.getImageFileName() != null){
			
				File img = (Repository.getInstance().getImage(this.getImageFileName()));
				
				if(img != null){ 
					return img.exists();
				}
				else{
					return false;
				}
		}
		else{
			return false;
		}
	}
	

	/**
	 * @see gui.representation.Representable#getHeight()
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * @see gui.representation.Representable#getWidth()
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * @see gui.representation.Representable#setHeight(int)
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @see gui.representation.Representable#setWidth(int)
	 */
	public void setWidth(int width) {
		this.width = width;
	}

}
