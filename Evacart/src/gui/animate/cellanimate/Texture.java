package gui.animate.cellanimate;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Texture implements Drawable
{
  String fileName;
  BufferedImage buffImg;

  public Texture()
  {
  }

  public Texture(String file)
  {
    fileName = file;

    JPanel p = new JPanel();
    Component comp = (Component) p;
    Image img = Toolkit.getDefaultToolkit().getImage( fileName );

    MediaTracker tracker = new MediaTracker(comp);
    tracker.addImage( img,0 );
    try
    {
      tracker.waitForAll();
    }
    catch( InterruptedException e )
    {
    }
    try
    {
      buffImg = new BufferedImage( img.getWidth(comp),img.getHeight(comp), BufferedImage.TYPE_INT_RGB );
      Graphics2D g2 = buffImg.createGraphics();
      g2.drawImage( img,0,0,comp );
    }
    catch( IllegalArgumentException e )
    {
    }
  }

  public void draw(double coordX, double coordY, Shape shape, double sizeX, double sizeY, Graphics2D g, boolean gridVisible)
  {
  	
  	if (gridVisible)
    {
    	sizeX = sizeX * 0.94;
    	sizeY = sizeY * 0.94;
    	coordX = coordX * 1.07;
    	coordY = coordY * 1.07;
    }

    //Creates a scaling transform
    AffineTransform aT = g.getTransform();
    AffineTransform scale = AffineTransform.getScaleInstance( sizeX, sizeY);
    g.transform(scale);

	//Translates the shape, fill it and translate it again to the original position
	((Polygon) shape).translate((int) coordX,(int) coordY);

    Rectangle rectangle = shape.getBounds();
    Paint texture;
    try
    {
      texture = new TexturePaint( buffImg,rectangle );
    }
    catch (NullPointerException e)
    {
      texture = Color.gray;
    }
    g.setPaint(texture);
    g.fill(shape);

	((Polygon) shape).translate((int) -coordX,(int) -coordY);

    //sets back the original transform
    g.setTransform(aT);
  }

  public String getFileName()
  {
    return (fileName);
  }

}