package gui.animate.cellanimate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class PlainColor implements Drawable {
	Color color;

	public PlainColor() {
		//Black is the default color
		color = Color.white;
	}

	public PlainColor(Color colorValue) {
		color = colorValue;
	}

	public void draw(double coordX, double coordY, Shape shape, double sizeX, double sizeY, Graphics2D g, boolean gridVisible) {

		if (gridVisible) {
			sizeX = sizeX * 0.94;
			sizeY = sizeY * 0.94;
			coordX = coordX * 1.07;
			coordY = coordY * 1.07;
		}

		//Creates a scaling transform
		AffineTransform aT = g.getTransform();
		AffineTransform scale = AffineTransform.getScaleInstance(sizeX, sizeY);
		g.transform(scale);

		g.setPaint(color);
		//Translates the shape, fill it and translate it again to the original position
		 ((Polygon)shape).translate((int)coordX, (int)coordY);

		g.fill(shape);
		((Polygon)shape).translate((int) - coordX, (int) - coordY);

		//sets back the original transform
		g.setTransform(aT);

	}

	public Color getColor() {
		return (color);
	}
}