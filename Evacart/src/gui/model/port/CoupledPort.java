package gui.model.port;

import gui.Constants;
import gui.graphEditor.OnePointLayoutable;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.model.AbstractModel;
import gui.representation.ImageFactory;
import gui.representation.InPort;
import gui.representation.OutPort;
import gui.representation.Representable;
import gui.representation.Saveable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Vector;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public class CoupledPort extends AbstractPort implements OnePointLayoutable, CoupledPortContainer{
	private Point location;	
	private Representable image;	
	/** 
	 * Constructor for CoupledPort.
	 */
	public CoupledPort() {
		super();
	}


	/**
	 * Constructor for CoupledPort.
	 * @param anID
	 * @param in/out
	 * @param aType
	 */
	public CoupledPort(String anID, String inOrOut, String aType, Object id) {
		super(anID, inOrOut, aType, id);
	}


	public Point getLocation() {
		return location;
	}
	/**
	 * Sets the location.
	 * @param location The location to set
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
	
	private void prepareImage(Representable image){
		image.setSelected(this.isSelected());
		image.setLocation(this.getLocation());
		image.setText(this.getName());
		
		image.setWidth(Constants.getInstance().getInt("draw.coupledPort.width","30"));
		image.setHeight(Constants.getInstance().getInt("draw.coupledPort.height","30"));			
		
	}
	/**
	 * Draw this element in the Graphics
	 * Creation date: (14/08/2002 12:41:43)
	 * @param aPen java.awt.Graphics
	 */
	public Dimension draw(Graphics aPen, ImageObserver observer) {
		Representable image = this.getImage();
		if(!image.isOK()){
			this.setImage(null);
		}
		return this.getImage().draw(aPen, observer);				
	}
	
	
	/**
	 * Says where aPoint is inside this Unit
	 */
	public boolean inside(Point aPoint){
		return this.getImage().inside(aPoint);
	}

	/**
	 * save the port data
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException{
		super.saveTo(writer);
		writer.writeln("location x", getLocation().x);
    	writer.writeln("location y", getLocation().y);			

		((Saveable)this.getImage()).saveTo(writer);
	}
	/**
	 * @see gui.model.Port#loadOtherPortDataFrom(CommentBufferedReader)
	 */
	protected void loadOtherPortDataFrom(CommentBufferedReader reader, AbstractModel graph)
		throws Exception {
		int x = Integer.parseInt(reader.readLine());
		int y = Integer.parseInt(reader.readLine());		
		this.setLocation(new Point(x,y));

		this.setImage(ImageFactory.loadFrom(reader,graph));
	}

	/**
	 * @see gui.model.EditableCoupledModel#getImage()
	 */
	public Representable getImage() {
		//if image is null, try to initialize with the image of the class
		if(image == null){
			if(this.getInOrOut().equals("In")){
				image = new InPort();
			}
			else{
				image = new OutPort();
			}
			
		}
		
		this.prepareImage(image);
		return image;
	
	}
	
	/**
	 * Sets the image.
	 * @param image The image to set
	 */
	public void setImage(Representable image) {
		this.image = image;
	}
	
	

	/**
	 * Return the ports that may be the end edge of a Link;
	 * @return
	 */
	public Vector getEndLinkPorts() {
		if(this.getInOrOut().equals("In")){
			return null;
		}
		else{
			Vector retr = new Vector();
			retr.add(this);
			return retr;
		}
	}


	/**
	 * Return the ports that may be the start edge of a Link;
	 * @return
	 */
	public Vector getStartLinkPorts() {
		if(this.getInOrOut().equals("Out")){
			return null;
		}
		else{
			Vector retr = new Vector();
			retr.add(this);
			return retr;
		}
	}

}
