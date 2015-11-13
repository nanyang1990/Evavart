package gui.model.unit;

import gui.image.Repository;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.util.FileDataPersister;
import gui.model.model.AbstractModel;
import gui.model.port.AbstractPort;
import gui.model.port.CoupledPortContainer;
import gui.representation.Image;
import gui.representation.ImageFactory;
import gui.representation.Representable;
import gui.representation.Saveable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;



/**
 * @author jicidre@dc.uba.ar
 *
 */
public abstract class AbstractModelUnit extends AbstractUnit implements CoupledPortContainer {

	protected abstract Representable getDefaultImage();
	protected abstract AbstractModel createNewModel(File path , File name) throws Exception;

	/*
	 * Makes a light check of this model to see if my parent (a Coupled model)
	 * is ok to be saved
	 */
	public abstract String checkModelForParentSave();

	protected Representable getClassImage(){
		String imageFilename = FileDataPersister.getInstance().get("unit.class.image.filename", this.getModel().getModelName());
			if(imageFilename != null){
				File image = Repository.getInstance().getImage(new File(imageFilename));
				if(image != null && image.exists()){
					return new Image(image);
				}
				else{
					return null;
				}
			}
			else{
				return null;
			}			
	}

	/**
	 * @see gui.model.EditableCoupledModel#getImage()
	 */
	public Representable getImage() {
		//if image is null, try to initialize with the image of the class
		if (super.getImage() == null) {
			setImage(getClassImage());
			//if the class has no image
			if (super.getImage() == null) {
				//get the default image of the type
				setImage(getDefaultImage());
			}
		}

		//now, i have an image
		if (!super.getImage().isOK()) {
			setImage(getDefaultImage());
		}
		this.prepareImage(super.getImage());
		return super.getImage();
	}

	public void addPort(AbstractPort port) {
		getPorts().add(port);
	}

	public void addPorts(Collection ports) {
		Iterator portsIter = ports.iterator();
		while (portsIter.hasNext()) {
			AbstractPort each = (AbstractPort)portsIter.next();
			addPort(each);
		}

	}

	/**
	 * return the ports of type Out
	 */
	public Vector getOutPorts() {
		Vector retr = new Vector();
		Iterator ports = getPorts().iterator();
		while (ports.hasNext()) {
			AbstractPort each = (AbstractPort)ports.next();
			if (each.getInOrOut().equals("Out")) {
				retr.add(each);
			}
		}
		return retr;
	}
	/**
	 * return the ports of type In
	 */
	public Vector getInPorts() {
		Vector retr = new Vector();
		Iterator ports = getPorts().iterator();
		while (ports.hasNext()) {
			AbstractPort each = (AbstractPort)ports.next();
			if (each.getInOrOut().equals("In")) {
				retr.add(each);
			}
		}
		return retr;
	}

	public String toString() {
		return getShortDescription();
	}
	/**
		 * @see gui.model.PortContainer#getPorts()
		 */
	public Vector getPorts() {
		return getModel().getPorts();
	}
	/**
		 * @see gui.model.PortContainer#setPorts(Vector)
		 */
	public void setPorts(Vector ports) {
		this.getModel().setPorts(ports);
	}

	
	protected AbstractModel model;
	/**
		 * @see gui.model.AbstractModelContainerUnit#getGraph()
		 */
	public AbstractModel getModel() {
		return model;
	}
	/**
		 * Sets the model.
		 * @param model The model to set
		 */
	public void setModel(AbstractModel model) {
		this.model = model;
		/*
		if (this.getModel() != null) {
			this.getModel().rebuildTree();
		}
*/
	}

	/**
	 * Save the Unit and also save the image and 
	 * the model reference
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		super.saveTo(writer);
		
		/**
		 * Do not ask my model to be saved, otherwise, save how to locate it.
		 * If I ask the model to be saved, it should run deeper than the first level
		 */
		
		File filename = this.getModel().getModelFileName();
		if(filename != null && (new File(getModel().getActualPath(), filename.getName())).exists()){
			writer.writeln("Filename",filename.getName());
		}
		else{
			writer.writeln("fileName","");		
		
		}
		
		File exportFile = this.getModel().getExportClasspath();
		if(exportFile != null){
			writer.writeln("export classpath",exportFile.getName());
		}
		else{
			writer.writeln("export classpath","");		
		}
		
		((Saveable)this.getImage()).saveTo(writer);
	}



	/**
	 * @see gui.model.AbstractUnit#loadOtherUnitDataFrom(CommentBufferedReader, Model)
	 */
	protected void loadOtherUnitDataFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception {
		String modelClasspath = reader.readLine();
		if(modelClasspath != null && !"".equals(modelClasspath)){
			this.setModel(this.createNewModel(graph.getActualPath() ,new File(modelClasspath)));
		}
		else{
			this.setModel(this.createNewModel(graph.getActualPath() ,null));
		}
		
		String exportClasspath = reader.readLine();
		if(modelClasspath != null && !"".equals(modelClasspath)){
			this.getModel().setExportClasspath(new File(exportClasspath));
		}

		this.setImage(ImageFactory.loadFrom(reader, graph));
	}

	/* (non-Javadoc)
	 * @see gui.model.PortContainer#getEndLinkPorts()
	 */
	public Vector getEndLinkPorts() {
		return getInPorts();
	}

	/* (non-Javadoc)
	 * @see gui.model.PortContainer#getStartLinkPorts()
	 */
	public Vector getStartLinkPorts() {
		return getOutPorts();
	}
	public String getDescription() {
		StringBuffer retr = new StringBuffer();
		retr.append("Unit Name: " + getName()+"\n");
		retr.append("Class: " + getModel().getModelName() + "\n"); 
		if(getModel().getModelFileName() != null){
			retr.append("Filename: " + getModel().getModelFileName() + "\n");
		}
		else{
			retr.append("Filename: Model not saved yet\n");		
		}

		if(getModel().getExportClasspath() != null){
			retr.append("ExportClassPath: " + getModel().getExportClasspath() + "\n");
		}
		else{
			retr.append("ExportClassPath: Model not exported yet\n");		
		}
				return retr.toString();
	}

	public String getShortDescription() {
		StringBuffer retr = new StringBuffer();
		retr.append(getName());
		return retr.toString();
	}

	

	/* (non-Javadoc)
	 * @see gui.model.unit.AbstractUnit#prepareImage(gui.representation.Representable)
	 */
	protected void prepareImage(Representable image) {
		super.prepareImage(image);
		String text = this.getName()+"@";
		if(this.getModel().getModelName() != null ){
			text += this.getModel().getModelName();
		}
		else{
			text += "NotDefined";
		}
		image.setText(text);
	}

}
