/*
 * Created on 14-nov-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import gui.animate.Serie;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Date;
import java.util.List;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * dibuja el eje y de una serie. Sirve para dejar el eje y siempre visible
 *
 */
public class AtomicSerieYAxisCanvas extends AtomicSerieCanvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param each
	 * @param parent
	 */
	public AtomicSerieYAxisCanvas(Serie each, AtomicAnimate parent) {
		super(each, parent);
	}

	/**
	 * @param log
	 * @param parent
	 */
	public AtomicSerieYAxisCanvas(AtomicAnimate parent) {
		super(parent);
	
	}
	
	/**
	 * Dibuja los ejes
	 * @param collection
	 */
	protected Dimension drawAxis(Serie serie,Graphics2D g) {
		//NOW MAXX IS THE MAXIMUM OF THE HOLE GRAPH
	//	float maxX = scale(serie.getMaxRenderingDate(),serie.getMinRenderingDate());
		float maxY = scale(serie.getMaxRenderingValue(),serie.getMinRenderingValue());

		double scalex = g.getTransform().getScaleX();
		double scaley = g.getTransform().getScaleY();
		
		AffineTransform trans = g.getTransform();

		trans.scale(1f/scalex,1f/-scaley);
		g.setTransform(trans);
		
	//	int linex = (int)Math.round(maxX * scalex);
		int liney = (int)Math.round(maxY * -scaley);

		//g.drawLine(0,0, linex ,0);
		g.drawLine(0,0,0,liney);

		//Devuelvo el valor que dibujo adaptado al transformer del g
		//int retrx = (int)Math.round(linex * g.getTransform().getScaleX());
		int retrx = (int)Math.round(0 * g.getTransform().getScaleX());
		int retry = (int)Math.round(liney * -g.getTransform().getScaleY());
  
 		trans.scale(scalex,-scaley);
		g.setTransform(trans);

		
		Dimension retr = new Dimension(retrx, retry);
		return retr;
	}

	/**
	 * @param values
	 */
	protected void drawXAxisValues(List<?> values,Date offset,Graphics2D g) {
	}

	/**
	 * @param values
	 */
	protected void drawYAxisValues(Serie serie,Graphics2D g) {
		
		//draw the first one
			g.drawString(""+serie.getMinRenderingValue(),0,scale(serie.getMinRenderingValue(),serie.getMinRenderingValue()));
		
			g.drawString(""+serie.getMaxRenderingValue(),0,scale(serie.getMaxRenderingValue(),serie.getMinRenderingValue()));

	}

	/* (non-Javadoc)
	 * @see gui.animate.AtomicSerieCanvas#draw(gui.animate.Serie, java.awt.Graphics2D)
	 */
	protected Dimension draw(Serie serie, Graphics2D g) {
			return new Dimension(0,0);
	}

	
	}
