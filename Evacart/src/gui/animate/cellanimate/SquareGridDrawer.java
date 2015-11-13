package gui.animate.cellanimate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class SquareGridDrawer extends GridDrawer {
	Shape shape;

	public SquareGridDrawer() {
		super();
		int[] squareX = new int[4];
		int[] squareY = new int[4];

		squareX[0] = (int) (1000 * (0.0));
		squareY[0] = (int) (1000 * (0.0));
		squareX[1] = (int) (1000 * (1.0));
		squareY[1] = (int) (1000 * (0.0));
		squareX[2] = (int) (1000 * (1.0));
		squareY[2] = (int) (1000 * (1.0));
		squareX[3] = (int) (1000 * (0.0));
		squareY[3] = (int) (1000 * (1.0));
		shape = new Polygon(squareX, squareY, 4);

	}

	/**
	 * Draws the square in the Matrix with indexes coordX and CoordY
	 */
	public void draw(int coordX, int coordY, boolean showValue, boolean showNames, String name) {
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if (!matrix.contains(coordY, coordX))
			return;

		double value = matrix.getValue(coordY, coordX);
		String showedString = Double.isNaN(value) ? "?" : Double.toString(Math.rint(value * 10000) / 10000);

		Drawable drawable = palette.getDrawable(value);

		// the size is 0.001x0.001 because it is scaled to 1000x1000 for precition
		double sizeX = 1.0 / 1000.0;
		double sizeY = 1.0 / 1000.0;

		drawable.draw(1000.0 * coordX, 1000.0 * coordY, shape, sizeX, sizeY, graphics, visible);
		Font font = graphics.getFont();
		font = font.deriveFont(0.5f);
		graphics.setFont(font);
		graphics.setColor(Color.BLACK);

		if (showValue) {
			graphics.drawString(showedString, (float)coordX, (float) (coordY) + 0.85f);
		}
		if (showNames && name != null) {
			font = font.deriveFont(0.2f);
			graphics.setFont(font);	

			graphics.drawString(name, (float)coordX, (float) (coordY) + 0.35f);
		}

	}

	public Shape getShape() {
		return shape;
	}

	/**
	 * @see grid.GridDrawer#getTransform()
	 */
	public AffineTransform getTransform() {
		double sx = (this.getWidth() / (double)matrix.getSizeY());
		double sy = (this.getHeight() / (double)matrix.getSizeX());
		double scale = Math.min(sx, sy);
		return (AffineTransform.getScaleInstance(scale, scale));
	}

}