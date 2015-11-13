/*
 * Created on 02-sep-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import gui.animate.Pair;
import gui.animate.Serie;
import gui.javax.util.DimensionUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
//import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * @author Administrador3 jicidre@dc.uba.ar
  *
 */
public class AtomicSerieGraphCanvas extends AtomicSerieCanvas {


	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param each
	 * @param parent
	 */
	public AtomicSerieGraphCanvas(Serie each, AtomicAnimate parent) {
		super(each,parent);
	}


	/**
	 * @param log
	 * @param parent
	 */
	public AtomicSerieGraphCanvas(AtomicAnimate parent) {
		super(parent);
		//this.setBackground(Color.WHITE);
	}


	/**
	 * Dibuja los ejes
	 * @param collection
	 */
	protected Dimension drawAxis(Serie serie,Graphics2D g) {
		//NOW MAXX IS THE MAXIMUM OF THE HOLE GRAPH
		float maxX = scale(serie.getMaxRenderingDate(), serie.getMinRenderingDate());
		float maxY = scale(serie.getMaxRenderingValue(),serie.getMinRenderingValue());

		double scalex = g.getTransform().getScaleX();
		double scaley = g.getTransform().getScaleY();
		
		AffineTransform trans = g.getTransform();

		trans.scale(1f/scalex,1f/-scaley);
		g.setTransform(trans);
		
		int linex = (int)Math.round(maxX * scalex);
		int liney = (int)Math.round(maxY * -scaley);

		g.drawLine(0,0, linex ,0);
		g.drawLine(0,0,0,liney);

		//Devuelvo el valor que dibujo adaptado al transformer del g
		int retrx = (int)Math.round(linex * g.getTransform().getScaleX());
		int retry = (int)Math.round(liney * -g.getTransform().getScaleY());
  
 		trans.scale(scalex,-scaley);
		g.setTransform(trans);

		
		Dimension retr = new Dimension(retrx, retry);
		return retr;

	}

	/**
	 * @param values
	 */
	protected void drawXAxisValues(List<?> values, Date offset, Graphics2D g) {
		Iterator<?> dates = values.iterator();
		while(dates.hasNext()){
			Date each = (Date)dates.next();
			g.drawString(parent.getTimePattern().format(each),scale(each,offset),0);
		}
		
	}

	/**
	 * @param values
	 */
	protected void drawYAxisValues(Serie serie,Graphics2D g) {
	}

	/**
	 * @param serie
	 * @param g
	 */
	protected Dimension draw(Serie serie, Graphics2D g) {
		Dimension retr = new Dimension(0,0);
		if(serie.getVisible()){
			Iterator<?> values = serie.getValues().iterator();
			//float color = 1;
			
			Pair lineFrom = null;
			Pair lineTo = null;
			while(values.hasNext()){
				Pair each = (Pair)values.next();
				lineTo = each;
				
				
				//if it is between rendering scope
				if(each.getValue() >= serie.getMinRenderingValue() && each.getValue() <= serie.getMaxRenderingValue()){
					draw(each,g,serie.getColor(),serie.getValuesOnOff(),serie.getMinRenderingValue(), serie.getMinRenderingDate());
				}
				
				Dimension eachDim = drawLine(lineFrom, lineTo,g,serie.getColor(),serie.getMinRenderingValue(), serie.getMaxRenderingValue(),serie.getMinRenderingDate());
				retr = DimensionUtil.getMaxEdges(eachDim,retr);
				
				lineFrom = lineTo;
				
			}
			
			if(lineFrom != null){
				//now, extend the line up to the maxDateValu
				Pair end = new Pair(serie.getMaxRenderingDate(), lineFrom.getValue());
				Dimension lastDim = drawLine(lineFrom,end,g,serie.getColor(),serie.getMinRenderingValue(), serie.getMaxRenderingValue(),serie.getMinRenderingDate());
				retr = DimensionUtil.getMaxEdges(lastDim,retr);
			}
			
		}		
		return retr;		
		 
		
	}

	/**
	 * @param lineFrom
	 * @param lineTo
	 * @param g
	 * @param color
	 */
	private Dimension drawLine(Pair lineFrom, Pair lineTo, Graphics2D g, Color color, float minRenderingValue, float maxRenderingValue, Date timeOffset) {
			Dimension retr = new Dimension(0,0);
			if(lineFrom != null && lineTo != null){
				double scalex = g.getTransform().getScaleX();
				double scaley = g.getTransform().getScaleY();


				AffineTransform trans = g.getTransform();
				trans.scale(1f/scalex,1f/-scaley);



				g.setTransform(trans);
				if(lineFrom.getValue() <= maxRenderingValue && lineFrom.getValue() >= minRenderingValue){
					int x1 = (int)Math.round(scale(lineFrom.getTime(),timeOffset)*scalex);
					int y1 = (int)Math.round(scale(lineFrom.getValue(),minRenderingValue)* -scaley);
					int x2 = (int)Math.round(scale(lineTo.getTime(),timeOffset)*scalex);
					
					g.drawLine(x1, y1, x2, y1);
				}


				int y1 = (int)Math.round(scale(lineFrom.getValue(),minRenderingValue)* -scaley);
				int x2 = (int)Math.round(scale(lineTo.getTime(),timeOffset)*scalex);
				int y2 = (int)Math.round(scale(lineTo.getValue(),minRenderingValue)* -scaley);


				if(lineFrom.getValue() < minRenderingValue){
					//LINEFROME UNDER SCOPE
					y1 = (int)Math.round(scale(minRenderingValue,minRenderingValue)* -scaley);
					//y1 = (int)Math.round(scale(lineFrom.getValue())* -scaley);

				}
				else if(lineFrom.getValue() > maxRenderingValue){
					//LINE FROM ABOVE SCOPE
					y1 = (int)Math.round(scale(maxRenderingValue,minRenderingValue)* -scaley);
					//y1 = (int)Math.round(scale(lineFrom.getValue())* -scaley);
				}
				else{
					//in scope
					y1 = (int)Math.round(scale(lineFrom.getValue(),minRenderingValue)* -scaley);
					//y1 = (int)Math.round(scale(lineFrom.getValue())* -scaley);
					
				}
				
				if(lineTo.getValue() < minRenderingValue){
					//LINETO UNDER SCOPE
					y2 = (int)Math.round(scale(minRenderingValue,minRenderingValue)* -scaley);
					//y2 = (int)Math.round(scale(lineTo.getValue())* -scaley);
				}
				else if(lineTo.getValue() > maxRenderingValue){
					//LINE TO ABOVE SCOPE
					y2 = (int)Math.round(scale(maxRenderingValue,minRenderingValue)* -scaley);
					//y2 = (int)Math.round(scale(lineTo.getValue())* -scaley);
				}
				else{
					//in scope
					y2 = (int)Math.round(scale(lineTo.getValue(),minRenderingValue)* -scaley);
					//y2 = (int)Math.round(scale(lineTo.getValue())* -scaley);
			
				}

				g.drawLine(x2, y1, x2, y2);

				
				//Now, define the size of this draw.
				retr.setSize(x2,y2);
				
				trans.scale(scalex,-scaley);
				g.setTransform(trans);
				//System.err.println("transform despues " + g.getTransform());				
			}
			return retr;
	}

	/**
	 * @param serie
	 * @param g
	 */
	private void draw(Pair pair, Graphics2D g, Color color, boolean valuesOnOff, float minRenderingValue, Date timeOffset) {
		
		g.setColor(color);
		float x = scale(pair.getTime(),timeOffset);
		float y = scale(pair.getValue(),minRenderingValue);
		
		if(valuesOnOff){
			g.drawString(""+pair.getValue(),x,y);
		}
		
	}
	/**
	 * @return
	 */
	/*
	 * unused
	private Polygon getDot(int x, int y,Graphics2D g) {
		AffineTransform orig = g.getTransform();
		//System.out.println(orig);
		
		int width = (int)Math.round(1f/orig.getScaleX()*5);
		int height = -(int)Math.round(1f/orig.getScaleY()*5);
		Polygon dot = new Polygon();
		
		dot.addPoint(x,y);
		dot.addPoint(x+width,y);
		dot.addPoint(x+width,y+height);
		dot.addPoint(x,y+height);
		
		return dot;
	}
	*
	*
	*/
		
}
