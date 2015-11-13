package gui.animate.cellanimate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class HexagonalGridDrawer extends GridDrawer {
	Shape shape;
	double constHex = Math.sqrt(0.2);
	double divider = constHex + 1.0;

	public HexagonalGridDrawer() {
		super();
		int[] hexX = new int[6];
		int[] hexY = new int[6];
		hexX[0] = (int) (1000 * (0.0));
		hexY[0] = (int) (1000 * (constHex / (double)divider));
		hexX[1] = (int) (1000 * (0.0));
		hexY[1] = (int) (1000 * (1.0));
		hexX[2] = (int) (1000 * (2.0 * constHex / (double)divider));
		hexY[2] = (int) (1000 * ((1.0 + 2.0 * constHex) / (double)divider));
		hexX[3] = (int) (1000 * (4.0 * constHex / (double)divider));
		hexY[3] = (int) (1000 * (1.0));
		hexX[4] = (int) (1000 * (4.0 * constHex / (double)divider));
		hexY[4] = (int) (1000 * (constHex / (double)divider));
		hexX[5] = (int) (1000 * (2.0 * constHex / (double)divider));
		hexY[5] = (int) (1000 * (0.0));

		shape = new Polygon(hexX, hexY, 6);
	}

	/**
	 * Sets up a hexagon to draw and calls the Drawables's draw
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

		if (coordY % 2 == 0.0) {
			drawable.draw(1000.0 * ((coordX * 4.0 * constHex + 2.0 * constHex) / divider), 1000.0 * coordY, shape, sizeX, sizeY, graphics, visible);

			Font font = graphics.getFont();
			font = font.deriveFont(0.4f);
			graphics.setFont(font);
			graphics.setColor(Color.BLACK);
			

			if (showValue) {
				graphics.drawString(showedString, (float) ((coordX * 4.0 * constHex + 2.0 * constHex) / divider), (float) (coordY) + 1);
			}
			if (showNames && name != null) {
				font = font.deriveFont(0.2f);
				graphics.setFont(font);	

				graphics.drawString(name, (float) ((coordX * 4.0 * constHex + 2.0 * constHex) / divider), (float) (coordY) + 0.5f);
			}

		}
		else {
			drawable.draw(1000.0 * ((coordX * 4.0 * constHex) / divider), 1000.0 * coordY, shape, sizeX, sizeY, graphics, visible);
			if (showValue) {
				Font font = graphics.getFont();
				font = font.deriveFont(0.4f);
				graphics.setFont(font);
				graphics.setColor(Color.BLACK);
				graphics.drawString(showedString, (float) ((coordX * 4.0 * constHex) / divider), (float) (coordY) + 1);

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
		double sx = (this.getWidth() / (double) ((matrix.getSizeY() + 0.5) * 4.0 * constHex / (double)divider));
		double sy = (this.getHeight() / (double) (matrix.getSizeX() + (constHex / (double)divider)));
		double scale = Math.min(sx, sy);
		return (AffineTransform.getScaleInstance(scale, scale));
	}

}