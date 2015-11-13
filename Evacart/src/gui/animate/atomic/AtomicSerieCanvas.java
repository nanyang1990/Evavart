/*
 * Created on 14-nov-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import gui.animate.Serie;
import gui.javax.util.DimensionUtil;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

/**
 * @author Administrador3 jicidre@dc.uba.ar Este jpanel tiene la forma de
 *         dibujar una serie
 *  
 */
public abstract class AtomicSerieCanvas extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Serie serie;

    protected AtomicAnimate parent;

    /**
     *  
     */
    public AtomicSerieCanvas(AtomicAnimate parent) {
        super();
        this.parent = parent;
        this.setOpaque(false);
    }

    /**
     * @param each
     */
    public AtomicSerieCanvas(Serie each, AtomicAnimate parent) {
        super();
        this.setSerie(each);

        this.parent = parent;
        this.setOpaque(false);
    }

    /**
     * @param g
     * @return
     */
    protected AffineTransform createFontAffineTransform(Graphics2D g) throws NoninvertibleTransformException {
        AffineTransform orig = g.getTransform();
        //AffineTransform retr = new
        // AffineTransform(1/orig.getScaleX()/1.5,0,0,1/orig.getScaleY()/1.5,0,0);
        AffineTransform retr = new AffineTransform(1 / orig.getScaleX(), 0, 0, 1 / orig.getScaleY(), 0, 0);
        return retr;
    }

    /**
     * @param g
     */
    protected void invertCoordinates(Graphics2D g) {
        translateCoordinates(g);
        invert(g);
    }

    protected void translateCoordinates(Graphics2D g) {
        Dimension size = this.getSize();
        g.translate(0, size.height);
    }

    protected void invert(Graphics2D g) {
        AffineTransform invert = getInvertYAxisTransform();
        g.transform(invert);
    }

    protected AffineTransform getInvertYAxisTransform() {
        return new AffineTransform(1, 0, 0, -1, 0, 0);
    }

    /**
     * @param series
     * @return
     */
    protected List<?> getXValues(Collection<?> s) {
        List retr = new ArrayList();
        Iterator series = s.iterator();
        while (series.hasNext()) {
            retr.addAll(((Serie) series.next()).getXValues());
        }
        return retr;
    }

    /**
     * Returns the values for the Y Axis
     * 
     * @param series
     * @return
     */
    protected List<?> getYValues(Collection<?> s) {
        List retr = new ArrayList();
        Iterator series = s.iterator();
        while (series.hasNext()) {
            retr.addAll(((Serie) series.next()).getYValues());
        }
        return retr;
    }

    /**
     * Devuelve el valor mas grande del eje de las Xs
     * 
     * @param collection
     * @return
     */
    protected float getMaxX(Serie serie,Date offset) {
        return scale(serie.getMaxX(),offset);
    }

    /**
     * @param f
     * @return
     */
    protected float scale(float f, float minRenderingValue) {
        //no more offset
        //return f - getMinYValue();
        return f - minRenderingValue;
    }

    /**
     * @param date
     * @return
     */
    protected float scale(Date date, Date offset) {

        float actual2 = date.getTime();
        float offset2 = offset.getTime();
        float retr2 = actual2 - offset2;

        return retr2;

    }

    /**
     * @return
     */
    public Serie getSerie() {
        return serie;
    }

    /**
     * @param collection
     */
    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    /**
     * @param string
     */
    protected void toogleVisibility() {
        getSerie().setVisible(!getSerie().getVisible());
    }

    protected void paintComponent(Graphics g1D) {
        super.paintComponent(g1D);
        Graphics2D g = (Graphics2D) g1D;

        float zoomx = 1;
        float zoomy = 1;
        try {
            zoomy = serie.getZoomY();
        }
        catch (Exception ex) {
        }
        try {
            zoomx = serie.getZoomX();
        }
        catch (Exception ex) {
        }

        g.setStroke(new BasicStroke(0.3f));

        invertCoordinates(g);
        g.scale(zoomx, zoomy);

        //System.out.println(g.getTransform());
        try {
            g.setFont(g.getFont().deriveFont(createFontAffineTransform(g)));
        }
        catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }

        //set a margin for all the graph
        g.translate(4f / zoomx, 4f / zoomy);

        //draw the axis values
        List<?> xValues = serie.getXValues();
        drawXAxisValues(xValues, serie.getMinRenderingDate(), g);

        drawYAxisValues(serie, g);

        //move up the axis lines
        g.translate(0 / zoomx, 7 / zoomy);

        //draw the axis lines
        Dimension canvasSize = drawAxis(getSerie(), g);
        //System.err.println("canvasSize 1" + canvasSize);
        //g.translate(-2,-5);

        Dimension otherCanvasSize = draw(getSerie(), g);

        canvasSize = DimensionUtil.getMaxEdges(canvasSize, otherCanvasSize);

        g.translate(0f / zoomx, -7f / zoomy);
        g.translate(-4f / zoomx, -4f / zoomy);

        double cW = canvasSize.getWidth() + 70;
        double cH = canvasSize.getHeight() + 21;

        //add a margin
        canvasSize.setSize(cW, cH);
        //System.err.println("canvasSize 2" + canvasSize);

        this.setPreferredSize(canvasSize);
        this.setMinimumSize(canvasSize);
        this.setMaximumSize(canvasSize);

        /*
         * if(!DimensionUtil.includes(this.getSize(),canvasSize)){
         * this.setSize(canvasSize); }
         */
    }

    /**
     * @param serie
     * @param g
     */
    protected abstract Dimension draw(Serie serie, Graphics2D g);

    /**
     * @param serie
     * @param g
     * @return
     */
    protected abstract Dimension drawAxis(Serie serie, Graphics2D g);

    /**
     * @param values
     * @param g
     */
    protected abstract void drawXAxisValues(List<?> values, Date offset, Graphics2D g);

    /**
     * @param values
     * @param g
     */
    protected abstract void drawYAxisValues(Serie serie, Graphics2D g);


}