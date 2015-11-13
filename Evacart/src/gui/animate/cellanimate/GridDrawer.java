package gui.animate.cellanimate;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

/**
 * Extends JPanel and provides methods for drawing the grid
 */
public abstract class GridDrawer extends JPanel {
	private boolean showNames = false;
	private boolean showValues = true;
	boolean visible = true;
	Palette palette = new Palette();

	Matrix matrix = new SimpleMatrix(new double[0][0],"no name");
	Graphics2D graphics;

	public GridDrawer(){
		//this.setBackground(Color.WHITE);
	}

	/**
	 * Togles the grid visible or not visible
	 */
	public void setVisible(boolean vis) {
		visible = vis;
	}

	abstract Shape getShape();

	/**
	 * Method draw.
	 * Sets the Java2D enviroment and calls the draw method for
	 * every element in the Matrix
	 */
	public void draw() {
		//Set up the graphics2D transforms
		if (graphics == null) {
			graphics = (Graphics2D)this.getGraphics();
		}
		super.paintComponent(graphics);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

		//Creates a scaling transform
		AffineTransform aT = graphics.getTransform();
		AffineTransform scale = getTransform();
		graphics.transform(scale);

		for (int x = 0; x < matrix.getSizeX(); x++) {
			for (int y = 0; y < matrix.getSizeY(); y++) {
				draw(y, x, showValues, showNames, matrix.getName(x,y));
			}
		}

		//sets back the original transform
		graphics.setTransform(aT);
	}

	/**
	 * Method getTransform.
	 * @return AffineTransform
	 * Returns the AffineTransform to apply to the GridDrawer
	 * in order to see all the shapes in screen
	 */
	abstract AffineTransform getTransform();

	abstract void draw(int coordX, int coordY, boolean showValues, boolean showNames, String name);

	public void setMatrix(Matrix mat) {
		matrix = mat;
	}

	public void setPalette(Palette pal) {
		palette = pal;
	}

	public Palette getPalette() {
		return palette;
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(Graphics)
	 */
	public void paintComponent(Graphics g) {
		graphics = (Graphics2D)g;
		draw();
	}



	/**
	 * @param b
	 */
	protected void setShowValues(boolean b) {
		showValues = b;
		
	}

	/**
	 * @param b
	 */
	protected void setShowNames(boolean b) {
		showNames = b;
		
	}

}