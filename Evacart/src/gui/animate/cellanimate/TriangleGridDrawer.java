package gui.animate.cellanimate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class TriangleGridDrawer extends GridDrawer {
	Shape shape;
	Shape shape2;
	double constTriang = Math.sqrt(4.0 / 3.0);

	public TriangleGridDrawer() {
		super();
		int[] triangX = new int[3];
		int[] triangY = new int[3];

		triangX[0] = (int) (1000 * (0.0));
		triangY[0] = (int) (1000 * (0.0));
		triangX[1] = (int) (1000 * (constTriang / (double)2.0));
		triangY[1] = (int) (1000 * (1.0));
		triangX[2] = (int) (1000 * (constTriang));
		triangY[2] = (int) (1000 * (0.0));
		shape = new Polygon(triangX, triangY, 3);

		triangX[0] = (int) (1000 * (0.0));
		triangY[0] = (int) (1000 * (1.0));
		triangX[1] = (int) (1000 * (constTriang / (double)2.0));
		triangY[1] = (int) (1000 * (0.0));
		triangX[2] = (int) (1000 * (constTriang));
		triangY[2] = (int) (1000 * (1.0));
		shape2 = new Polygon(triangX, triangY, 3);
	}

	/**
	 * Sets up a triangle to draw and calls the Drawable's draw
	 */
	public void draw(int coordX, int coordY, boolean showValue, boolean showName, String name) {
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if (!matrix.contains(coordY, coordX))
			return;

		double value = matrix.getValue(coordY, coordX);
		String showedString = Double.isNaN(value) ? "?" : Double.toString(Math.rint(value * 10000) / 10000);

		Drawable drawable = palette.getDrawable(value);

		// the size is 0.001x0.001 because it is scaled to 1000x1000 for precition
		double sizeX = 1.0 / 1000.0;
		double sizeY = 1.0 / 1000.0;

		//if the add of the two coords is even, the triangle is facing down(shape) esle is up (shape2)
		if ((coordY + coordX) % 2 == 0.0) {
			drawable.draw(1000.0 * (coordX * constTriang / (double)2.0), 1000.0 * coordY, shape, sizeX, sizeY, graphics, visible);

			Font font = graphics.getFont();
			font = font.deriveFont(0.4f);
			graphics.setFont(font);
			graphics.setColor(Color.BLACK);

			if (showValue) {
				graphics.drawString(showedString, (float) (coordX * constTriang / (double)2.0), (float) (coordY) + 0.5f);
			}
			if (showName & name != null) {
				font = font.deriveFont(0.2f);
				graphics.setFont(font);	
				graphics.drawString(name, (float) (coordX * constTriang / (double)2.0), (float) ((coordY) + 0.2f));

			}

		}
		else {
			drawable.draw(1000.0 * (coordX * constTriang / (double)2.0), 1000.0 * coordY, shape2, sizeX, sizeY, graphics, visible);
			//drawable.draw( 1000.0*(coordX*constTriang /(double) 2.0), 1000.0*coordY, shape, sizeX, sizeY, graphics, visible);
			if (showValue) {
				Font font = graphics.getFont();
				font = font.deriveFont(0.4f);
				graphics.setFont(font);
				graphics.setColor(Color.BLACK);
				graphics.drawString(showedString, (float) (coordX * constTriang / (double)2.0), (float) ((coordY) + 1));

			}
		}

	}

	public Shape getShape() {
		return shape;
	}

	/**
	 * @see grid.GridDrawer#getTransform()
	 */
	public AffineTransform getTransform() {
		double sx = (this.getWidth() / (double) ((matrix.getSizeY() + 1) * constTriang / (double)2.0));
		double sy = this.getHeight() / (double)matrix.getSizeX();
		double scale = Math.min(sx, sy);
		return (AffineTransform.getScaleInstance(scale, scale));

	}

}