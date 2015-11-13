/*
 * Created on 15/05/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.representation;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author jcidre
 *
 */
public class Arrow implements Representable, Saveable {
	private boolean selected = false;
	private Point location;
	private Stroke stroke;
	
	/**
	 * it is posible to retract the triangle a headRetraction points
	 */
	private int headRetraction;
	
	private int width;
	private int height;	
	private String text;

	/* (non-Javadoc)
	 * @see gui.representation.Representable#isOK()
	 */
	public boolean isOK() {
		return true;
	}

	/* (non-Javadoc)
	 * @see gui.representation.Representable#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see gui.representation.Representable#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see gui.representation.Representable#setHeight(int)
	 */
	public void setHeight(int height) {
		this.height = height;

	}

	/* (non-Javadoc)
	 * @see gui.representation.Representable#setWidth(int)
	 */
	public void setWidth(int width) {
		this.width = width;

	}

	/* (non-Javadoc)
	 * @see gui.representation.Adornable#setText(java.lang.String)
	 */
	public void setText(String text) {
		this.text = text;

	}

	/* (non-Javadoc)
	 * @see gui.representation.Adornable#getText()
	 */
	public String getText() {
		return text;
	}

	/* (non-Javadoc)
	 * @see gui.javax.util.Selectable#isSelected()
	 */
	public boolean isSelected() {
		return selected;
	}

	/* (non-Javadoc)
	 * @see gui.javax.util.Selectable#setSelected(boolean)
	 */
	public void setSelected(boolean selectornot) {
		this.selected = selectornot;

	}

	/* (non-Javadoc)
	 * @see gui.javax.util.Selectable#toggleSelected()
	 */
	public void toggleSelected() {
		setSelected(!isSelected());

	}

	/* (non-Javadoc)
	 * @see gui.representation.Drawable#draw(java.awt.Graphics, java.awt.image.ImageObserver)
	 */
	public Dimension draw(Graphics g, ImageObserver observer) {
		
		Graphics2D a2DPen = (Graphics2D) g;
		a2DPen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		a2DPen.setStroke(getStroke());

		if (selected)
			a2DPen.setColor(Color.red);
		else
			a2DPen.setColor(Color.black);

		//Algorithm
		//Calculate it in an horizontal position (3:00) 

		int startx = getLocation().x;
		int starty = getLocation().y;
		int endx = startx + getWidth();
		int endy = starty + getHeight();
		
		int length = (int)Math.round(getLocation().distance(new Point(endx, endy)));

		//now, rotate it as needed	
		double radians = Math.atan2(endy-starty,endx-startx);
		a2DPen.rotate(radians, startx, starty);

		//draw
		a2DPen.drawLine(startx, starty, startx + length, starty);
		if(getText() != null && ! "".equals(getText())){
		    StringTokenizer toker = new StringTokenizer(getText(),"\n",false);
		    int lineNum = 1;
		    while(toker.hasMoreTokens()){
		        String line = toker.nextToken();
			    double stringLength = a2DPen.getFontMetrics().getStringBounds(line,a2DPen).getWidth();
			    double stringHeight = a2DPen.getFontMetrics().getStringBounds(line,a2DPen).getHeight();
			    a2DPen.drawString(line,Math.round(startx+length/2 - stringLength/2),starty+Math.round(lineNum*stringHeight));
			    lineNum++;
		        
		    }
		}
		
		//draw the arrow triangle
		Polygon triangle = new Polygon();
		Point p1 = new Point(startx + length - getHeadRetraction(),getLocation().y );
		Point p2 = new Point(p1.x - 10,getLocation().y - 10);		
		Point p3 = new Point(p2.x  ,getLocation().y + 10);		
		triangle.addPoint(p1.x,p1.y);
		triangle.addPoint(p2.x,p2.y);
		triangle.addPoint(p3.x,p3.y);
				
				
		a2DPen.fillPolygon(triangle);

		
		//unrotate it
		a2DPen.rotate(-radians, startx,starty);

		//Not implemented yet. Shoould be the edge of the drawing
		return new Dimension(0,0);	
	}

	/* (non-Javadoc)
	 * @see gui.representation.Drawable#setLocation(java.awt.Point)
	 */
	public void setLocation(Point location) {
		this.location = location;

	}
	public boolean inside(Point aPoint) {
		return distPointToArrow(aPoint) < 4;	
	}
	/* (non-Javadoc)
	 * @see gui.representation.Drawable#inside(java.awt.Point)
	 */
	public boolean inside2(Point aPoint) {
		boolean retr = true;
		
		double xa = getLocation().getX();
		double ya = getLocation().getY();
		
		double xb = xa + getWidth();
		double yb = ya + getHeight();
		
		double h = ya - (xa * ((ya - yb)/(xa - xb)));
		double m = (ya - yb) / (xa - xb);
		
		double xp = aPoint.getX();
		double yp = aPoint.getY();

		
		//second condition: between the two vertical points 
		//to_do give tolerance with relative numbers!!
		if(!((ya-10 <= yp && yp <= yb + 10)||(ya+10 >= yp && yp >= yb-10))){
			retr = false;
			System.err.println((ya-10)+ " <= " + yp + " <= " + (yb+10));
			System.err.println((ya+10)+ " >= " + yp + " >= " + (yb-10) + " ERROR");
		}
		else{
			System.err.println((ya-10)+ " <= " + yp + " <= " + (yb+10));
			System.err.println((ya+10)+ " >= " + yp + " >= " + (yb-10) + " OK");
		
		}
		
		//third condition: between the two horizontal points 
		if(!((xa-10 <= xp && xp <= xb+10)||(xa+10 >= xp && xp >= xb-10))){
			retr = false;
			System.err.println((xa-10) + " <= " + xp + " <= " + (xb+10));
			System.err.println((xa+10) + " >= " + xp + " >= " + (xb-10) + " ERROR");
		}
		else{
			System.err.println((xa-10) + " <= " + xp + " <= " + (xb+10));
			System.err.println((xa+10) + " >= " + xp + " >= " + (xb-10) + " OK");
		}
		
		//first condition : in the same line with some tolerance
	
		//to_do give tolerance with relative numbers and not this horrible 10!!
		// ths first if is because it fails on vertical segments
		if( (Math.abs(xa - xb) > Math.abs(ya - yb) )){
			double expectedyp = xp*m+h;
			double relativeError =Math.abs((expectedyp-yp)/yp);
			
			if(!(relativeError <0.03)){
				retr= false;
				System.err.println(expectedyp + " " + yp + " " + relativeError + " H ERROR");
			}
			else{
				System.err.println(expectedyp + " " + yp + " " + relativeError + " H OK");
			}
		}
		else{
			double expectedxp = (yp-h)/m;
			double relativeError =Math.abs((expectedxp-xp)/xp);
			
			if(!(relativeError <0.03)){
				retr= false;
				System.err.println(expectedxp + " " + xp + " " + relativeError + " V ERROR");
			}
			else{
				System.err.println(expectedxp + " " + xp + " " + relativeError + " V OK");
			}
			
		}
		
		System.err.println(retr);
		System.err.println();
		return retr;
		
		
	}
	
	// dot product (2D) which allows vector operations in arguments
	/*
#define dot(u,v)   ((u).x * (v).x + (u).y * (v).y)
#define norm(v)    sqrt(dot(v,v))  // norm = length of vector
#define d(u,v)     norm(u-v)       // distance = norm of difference
*/
	private double dot(Point u, Point v){
		return u.x * v.x + u.y * v.y;
	}
	private double norm(Point v){
		return Math.sqrt(dot(v,v));
	}
	private double d(Point u, Point v){
		return norm(new Point(u.x - v.x, u.y - v.y));
	}
	
// dist_Point_to_Segment(): get the distance of a point to a segment.
//    Input:  a Point P and a Segment S (in any dimension)
//    Return: the shortest distance from P to S
	private double distPointToArrow( Point p) {

    Point v = new Point(getWidth(), getHeight());
    Point w = new Point((int)Math.round(p.getX() - getLocation().getX()),(int)Math.round(p.getY() - getLocation().getY()));

    double c1 = dot(w,v);
    if ( c1 <= 0 )
        return d(p, getLocation());

    double c2 = dot(v,v);
    if ( c2 <= c1 )
        return d(p, new Point(getLocation().x + getWidth(), getLocation().y + getHeight()));

    double b = c1 / c2;
    Point pb = new Point((int)Math.round(getLocation().x + b * v.x),(int)Math.round(getLocation().y + b * v.y));
    return d(p, pb);
}
	

	/* (non-Javadoc)
	 * @see gui.representation.Saveable#saveTo(gui.javax.io.CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		

	}

	/* (non-Javadoc)
	 * @see gui.representation.Saveable#loadFrom(gui.javax.io.CommentBufferedReader)
	 */
	public void loadFrom(CommentBufferedReader reader) throws IOException {
		

	}



	/**
	 * @return
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return
	 */
	public int getHeadRetraction() {
		return headRetraction;
	}

	/**
	 * @param i
	 */
	public void setHeadRetraction(int i) {
		headRetraction = i;
	}

	/**
	 * @return
	 */
	public Stroke getStroke() {
		if(stroke == null){
			stroke = new BasicStroke(2);
		}
		return stroke;
	}

	/**
	 * @param stroke
	 */
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

}
