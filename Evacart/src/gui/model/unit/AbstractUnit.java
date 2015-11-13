package gui.model.unit;


import gui.graphEditor.OnePointLayoutable;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.util.Identifiable;
import gui.model.Descriptable;
import gui.model.model.AbstractModel;
import gui.representation.Representable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.io.IOException;


/**
 * Insert the type's description here.
 * Creation date: (14/08/2002 11:07:24)
 * @author: Administrator
 */
public abstract class AbstractUnit  implements Identifiable, OnePointLayoutable, Descriptable{
	private Representable image;
	private Point location;	
	private boolean selected = false;
	private Object uniqueId;
	private String name = "";
	 			
	/**
	 * Unit constructor comment.
	 */
	public AbstractUnit() {
		super();
	}
	 


	/*
	public void addIncidentLink(AbstractLink L) { 
		getIncidentLinks().add(L);
	}
	*/
	/**
	 * Insert the method's description here.
	 * Creation date: (14/08/2002 12:41:43)
	 * @param aPen java.awt.Graphics
	 */
	public Dimension draw(Graphics aPen, ImageObserver observer){
		//System.err.println("abstractUnit draw " + this.getImage() );
		return this.getImage().draw(aPen, observer);
		
	}
	
	protected void prepareImage(Representable image){
		image.setSelected(this.isSelected());
		image.setLocation(this.getLocation());
		//image.setText(this.getName());
	}

	/**
	 * Sets the image.
	 * @param image The image to set
	 */
	public void setImage(Representable image) {
		this.image = image;
		//System.out.println("AbstractUnit img " + image);
	}

	public Representable getImage(){
		return this.image;
	}
	/**
	 * load extra Unit data
	 */
	protected abstract void loadOtherUnitDataFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception;
	
	/**
	 * returns a Description of this Unit as big as possible
	 */
	public abstract String getDescription();
	
	public void setLocation(int x, int y) {
		setLocation(new Point(x, y));
	}

	public void toggleSelected() {
		setSelected(!isSelected());
	}

	public void saveTo(CommentBufferedWriter writer)throws IOException{
			writer.writeln("Unit class" , this.getClass().getName());
			writer.writeln("Unique ID" , this.getUniqueId().toString());
			writer.writeln("location x", getLocation().x);
			writer.writeln("location y", getLocation().y);			
			writer.writeln("name",getName());
	}
	
	public static AbstractUnit loadFrom(CommentBufferedReader reader, AbstractModel graph)throws Exception{
		String unitClass = reader.readLine();
		AbstractUnit unit = (AbstractUnit)(java.lang.Class.forName(unitClass)).newInstance();
		
		//unique id
		String id = reader.readLine();
		unit.setUniqueId(id);
		//ensure not to use it again
		graph.getSequence().setUsed(Integer.parseInt((String)id));
		
				
		int x = Integer.parseInt(reader.readLine());
		int y = Integer.parseInt(reader.readLine());		
		unit.setLocation(x,y);
		String name =reader.readLine(); 
		unit.setName(name);
		unit.loadOtherUnitDataFrom(reader, graph);
		
		return unit;
	}
	
	
	/**
	 * Says where aPoint is inside this Unit
	 */
	public boolean inside(Point aPoint){
		return this.getImage().inside(aPoint);
	}


	public void setLocation(Point aPoint) {
		location = aPoint;
	}

	public Point getLocation() {
		return location;
	}

	/**
	 * @see gui.javax.util.Selectable#isSelected()
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * @see gui.javax.util.Selectable#setSelected(boolean)
	 */
	public void setSelected(boolean selectornot) {
		this.selected = selectornot;
	}

	/**
	 * returns an Id that identifies this Unit inequivocally
	 * in such a way that it can by persisted.
	 */
	public Object getUniqueId() {
		return uniqueId;
	}

	/**
	 * Sets the uniqueId.
	 * @param uniqueId The uniqueId to set
	 */
	public void setUniqueId(Object uniqueId) {
		this.uniqueId = uniqueId;
	}
	/**
	 * @see gui.model.AbstractUnit#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;

	}

	/**
	 * @see gui.graphEditor.Layoutable#getName()
	 */
	public String getName() {
		return name;
	}

}
