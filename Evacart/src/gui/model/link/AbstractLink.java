package gui.model.link;

import gui.graphEditor.Layoutable;
import gui.graphEditor.OnePointLayoutable;
import gui.graphEditor.TwoPointsLayoutable;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.util.Identifiable;
import gui.model.Descriptable;
import gui.model.Expression;
import gui.model.model.AbstractModel;
import gui.representation.Arrow;
import gui.representation.Representable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author jicidre@dc.uba.ar
 * A Link is a connection beetween two Units. 
 * It is expected to all Links to be connected to two ports also.
 * One of type out and the other of type in.
 */
public abstract class AbstractLink implements Identifiable, TwoPointsLayoutable, Descriptable{
	private Representable image;		
	protected boolean selected;
	protected Layoutable startLinkPlugable;
	protected Layoutable endLinkPlugable;
	public abstract void loadOtherLinkDataFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception;	

	public static int LtoM = 15;

	/**
	 * Constructor for AbstractLink.
	 */
	public AbstractLink(Layoutable start, Layoutable end, Object id) {
		super();
		
		//link me
		setStartLinkPlugable(start);
		setEndLinkPlugable(end);
		setUniqueId(id);

	}

	/**
	 * Constructor for AbstractLink.
	 */
	public AbstractLink() {
		super();
	}

	public abstract Dimension draw(Graphics g, ImageObserver observer);
	/**
		* Insert the method's description here.
		* Creation date: (14/08/2002 11:58:07)
		* @return gui.model.Unit
		*/
	public Layoutable getStartLinkPlugable() {
		return startLinkPlugable;
	}

	public void toggleSelected() {
		selected = !selected;
	}

	/**
		* Insert the method's description here.
		* Creation date: (14/08/2002 11:58:07)
		* @return gui.model.Unit
		*/
	public Layoutable getEndLinkPlugable() {
		return endLinkPlugable;
	}

	/**
	 * @see gui.javax.swing.Identifiable#getId()
	 */
	public abstract Object getUniqueId();

	/**
	* Insert the method's description here.
	* Creation date: (14/08/2002 11:58:07)
	* @param newEndState gui.model.Unit
	*/
	public void setEndLinkPlugable(Layoutable newEndState) {
		endLinkPlugable = newEndState;
	}

	/**
		 * Insert the method's description here.
		 * Creation date: (14/08/2002 11:58:07)
		 * @param newStartState gui.model.Unit
		 */
	public void setStartLinkPlugable(Layoutable newStartState) {
		startLinkPlugable = newStartState;

	}

	public Layoutable otherEndFrom(Layoutable aState) {
		if (startLinkPlugable == aState)
			return getEndLinkPlugable();
		else
			return getStartLinkPlugable();
	}

	public Point getStartLocation() {
		return ((OnePointLayoutable)getStartLinkPlugable()).getLocation();
	}
	public Point getEndLocation() {
		return ((OnePointLayoutable)getEndLinkPlugable()).getLocation();
	}
	public abstract String getLinkLabel();


	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected){
		this.selected = selected;
	}
	public String toString() {
		return getShortDescription();
	}

	public void saveTo(CommentBufferedWriter writer) throws IOException {
		writer.writeln("Link Type", this.getClass().getName());
		writer.writeln("Link unique Id", this.getUniqueId().toString());
		writer.writeln("start unit Point" , ((Identifiable)getStartLinkPlugable()).getUniqueId().toString());
		writer.writeln("end unit Point" , ((Identifiable)getEndLinkPlugable()).getUniqueId().toString());
	}

	public static AbstractLink loadFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception {
		String linkClass = reader.readLine();
		AbstractLink link = (AbstractLink)(java.lang.Class.forName(linkClass)).newInstance();
		String id = reader.readLine();
		link.setUniqueId(id);
		graph.getSequence().setUsed(Integer.parseInt(id));
		String startUniqueId = reader.readLine();
		String endUniqueId = reader.readLine();
		Layoutable start = graph.findLinkPlugableByUniqueId(startUniqueId);
		Layoutable end = graph.findLinkPlugableByUniqueId(endUniqueId);
		link.setStartLinkPlugable(start);
		link.setEndLinkPlugable(end);
			
		link.loadOtherLinkDataFrom(reader, graph);
		
		return link;
	}
	
	
	/**
	 * @see gui.graphEditor.Layoutable#getImage()
	 */
	public Representable getImage() {
		//if image is null, try to initialize with the image of the class
		if(image == null){
				image = getImageClass();
		}
		
		this.prepareImage(image);
		return image;

	}

	/**
	 * @return
	 */
	protected Representable getImageClass() {
		return new Arrow();
	}

	protected void prepareImage(Representable image){
		
		image.setSelected(this.isSelected());
		image.setLocation(this.getStartLocation());
	
		image.setWidth(this.getEndLocation().x-getStartLocation().x );
		image.setHeight(this.getEndLocation().y-getStartLocation().y);			
	
	}

	/**
	 * @see gui.graphEditor.Layoutable#getImageFileName()
	 */
	public String getImageFileName() {
		return null;
	}

	/**
	 * @see gui.graphEditor.Layoutable#getName()
	 */
	public String getName() {
		return null;
	}

	/**
	 * @see gui.graphEditor.Layoutable#inside(Point)
	 */
	public boolean inside(Point aPoint) {
		return getImage().inside(aPoint);
	}

	/**
	 * @see gui.graphEditor.Layoutable#setImage(AdornableSelectableDrawable)
	 */
	public void setImage(Representable image) {
	}

	/**
	 * @see gui.graphEditor.Layoutable#setOtherImageData(AdornableSelectableDrawable)
	 */
	public Representable setOtherImageData(Representable image) {
		return null;
	}
	
	
	public abstract void unlink(Expression ex);
	public void unlink(Collection expressions){
		Iterator expres = expressions.iterator();
		while(expres.hasNext()){
			unlink((Expression)expres.next());
		}
	}

}
