package gui.representation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;

/**
 * @author Administrador
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InPort extends Port {

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
		
		//armo un triangulo
		Polygon triangle = new Polygon();
		
		Point p1 = new Point(getLocation().x - getWidth()/2,getLocation().y - getHeight()/2);
		Point p2 = new Point(getLocation().x - getWidth()/2,getLocation().y + getHeight()/2);		
		Point p3 = new Point(getLocation().x + getWidth()/2,getLocation().y);		
		triangle.addPoint(p1.x,p1.y);
		triangle.addPoint(p2.x,p2.y);
		triangle.addPoint(p3.x,p3.y);
				
				
		aPen.fillPolygon(triangle);
		
		//build the little model-square
		aPen.fillRect(p3.x,p1.y,getWidth()/10,getHeight());
		
		//dibujo el texto
		a1DPen.drawString(this.getText(), getLocation().x - getWidth() / 2 , getLocation().y - getHeight() / 2 );
		Dimension retr = new Dimension(getLocation().x+ getWidth(), getLocation().y+getHeight());
 
		return retr;
				
	}

}
