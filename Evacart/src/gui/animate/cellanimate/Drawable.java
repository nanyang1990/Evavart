package gui.animate.cellanimate;

import java.awt.Graphics2D;
import java.awt.Shape;

public interface Drawable
{

  public void draw(double coordX, double coordY, Shape shape, double sizeX, double sizeY, Graphics2D g, boolean gridVisible);

}