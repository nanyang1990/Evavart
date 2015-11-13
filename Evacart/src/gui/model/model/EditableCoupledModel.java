package gui.model.model;

import gui.MainFrame;
import gui.graphEditor.Layoutable;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.util.DimensionUtil;
import gui.model.Expression;
import gui.model.link.AbstractCoupledLink;
import gui.model.link.AbstractLink;
import gui.model.port.AbstractPort;
import gui.model.port.CoupledPort;
import gui.model.unit.AbstractModelUnit;
import gui.model.unit.AbstractUnit;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

public class EditableCoupledModel extends CoupledModel {

	/**
	 * 
	 */
	public EditableCoupledModel() {
		super();
	}

	/**
	 * @param mainFrame
	 */
	public EditableCoupledModel(MainFrame mainFrame) {
		super(mainFrame);
	}

	/**
	 * @param mainFrame
	 * @param atitle
	 * @param v1
	 * @param v2
	 * @param v3
	 */
	public EditableCoupledModel(
		MainFrame mainFrame,
		String atitle,
		Vector v1,
		Vector v2,
		Vector v3) {
		super(mainFrame, atitle, v1, v2, v3);
	}

	public void deletePort(AbstractPort aPort) {
		Iterator someLinks = getIncidentLinks((Layoutable) aPort).iterator();
		while (someLinks.hasNext()) {
			AbstractLink aLink = (AbstractLink) someLinks.next();

			//if the port has links delete the links first.
			this.deleteLink(aLink);
		}

		getPorts().removeElement(aPort);

		getUnitsTreeModel().removeNodeFromParent(aPort);

	}

	public String checkModelForSave(String identifyingId) throws IOException {
		StringBuffer retr = new StringBuffer();

		// check each state should have ID and a Classpath
		for (int j = 0; j < getModels().size(); j++) {
			AbstractModelUnit aState =
				(AbstractModelUnit) getModels().elementAt(j);
			retr.append(aState.checkModelForParentSave());
		}
		return retr.toString();
	}

	/*
	 * Makes a light check of this model to see if my parent (a Coupled model)
	 * is ok to be saved
	 */
	public String checkModelForParentSave() {
		StringBuffer retr = new StringBuffer();
		File filename = getModelFileName();

		if ((filename == null) || (!( new File(getActualPath().getAbsolutePath() + File.separator + filename.getName())).exists())) {
			String line =
				"Model "
					+ this.toString()
					+ " can't be found. It may has not been exploded and saved. \n";
			retr.append(line);
		}

		return retr.toString();
	}

	public String checkModelForExport(String identifyingId)
		throws IOException {
		StringBuffer retr = new StringBuffer();
		
		retr.append(checkModelForParentExport(identifyingId));
		
		//check that the model has been saved at least once
		if (getActualPath() == null || getActualPath().equals("")) {
			retr.append(
				"The model "
					+ identifyingId
					+ " must be at least saved one time.\n");
		}
		
		// check that the model has an exportFilename
		if (getExportClasspath() == null || getExportClasspath().equals("")) {
			retr.append(
				"Must select an export file for "
					+ identifyingId
					+ " model.\n");
		}
	
		return retr.toString();

	}

	/* (non-Javadoc)
	 * @see gui.model.model.Model#checkModelForParentExport(java.lang.String)
	 */
	public String checkModelForParentExport(String identifyingId)
		throws IOException {
		StringBuffer retr = new StringBuffer();

		retr.append(checkModelForSave(identifyingId));

		// check that the model has a classname
		if (getModelName() == null || getModelName().equals("")) {
			retr.append(
				"Must select a name for " + identifyingId + " Model.\n");
		}

		//check if my models are able to be exported
		for (int i = 0; i < getModels().size(); i++) {
			AbstractModelUnit each = (AbstractModelUnit) getModels().get(i);
			retr.append(each.getModel().checkModelForParentExport(each.getName()));
		}

		// check each port first, every port should have ID and at leat one incident link
		for (int i = 0; i < getPorts().size(); i++) {
			AbstractPort aport = (AbstractPort) getPorts().elementAt(i);

			String id = aport.getPortId();

			if ((id == null)) {
				String line = "Port " + aport.getName() + " have not ID\n";
				retr.append(line);
			}
		}

		// check each link havs two connected ports
		retr.append(checkLink());
		return retr.toString();
	}

	/**
	 * 
	 * Save a Model to a File
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		File filename = this.getModelFileName();
		if(filename != null && (new File(getActualPath(), filename.getName())).exists()){
			writer.writeln("Filename",filename.getName());
		}
		else{
			writer.writeln("fileName","");		
		
		}
		
		File exportFile = this.getExportClasspath();
		if(exportFile != null){
			writer.writeln("export classpath",exportFile.getName());
		}
		else{
			writer.writeln("export classpath","");		
		}


		writer.writeln("Title", getModelName());
		if(getBackgroundImage() != null){
			writer.writeln("background", getBackgroundImage().getName());
		}
		else{
			writer.writeln("background", "");
		}
		
		//States
		writer.writeln("cant units", getModels().size());
		Iterator tmpStates = getModels().iterator();
		writer.increaseTab();
		while (tmpStates.hasNext()) {
			//save the Unit
			AbstractUnit tmpState = (AbstractUnit) tmpStates.next();
			tmpState.saveTo(writer);
		}
		writer.decreaseTab();
		//save the model ports
		 
		Iterator ports = this.getPorts().iterator();
		writer.writeln("cant ports", this.getPorts().size());
		writer.increaseTab();
		while (ports.hasNext()) {
			AbstractPort eachPort = (AbstractPort) ports.next();
			eachPort.saveTo(writer);
		}
		writer.decreaseTab();
		//Links

		writer.writeln("Cant Links", getLinks().size());
		Iterator tmpLinks = getLinks().iterator();
		writer.increaseTab();
		while (tmpLinks.hasNext()) {
			AbstractLink tmpLink = (AbstractLink) tmpLinks.next();
			tmpLink.saveTo(writer);
		}
		writer.decreaseTab();

	}

	/**
	 * Load Model from file
	 */
	public void loadFrom(CommentBufferedReader reader, MainFrame mainFrame)
		throws Exception {

		this.setMainFrame(mainFrame);

		String modelClasspath = reader.readLine();
		//perhaps there is not modelclasspath and I don't want to store an empty classpath
		if(modelClasspath != null && !"".equals(modelClasspath)){
			this.setModelFileName(new File(modelClasspath));
		}

		String exportClasspath = reader.readLine();
		//perhaps there is not exportclasspath and I don't want to store an empty classpath
		if(exportClasspath != null && !"".equals(exportClasspath)){
			setExportClasspath(new File(exportClasspath));
		}

		//title
		String modelname = reader.readLine();
		this.setModelName(modelname);
		String background = reader.readLine();
		this.setBackgroundImage(new File(background));
		
		//States
		int cantUnits = Integer.parseInt(reader.readLine());

		for (int i = 0; i < cantUnits; i++) {
			//load the Unit
			AbstractModelUnit aUnit =
				(AbstractModelUnit) AbstractModelUnit.loadFrom(reader, this);
			this.addUnit(aUnit);
		}

		//Ports
		int cantPorts = Integer.parseInt(reader.readLine());

		for (int i = 0; i < cantPorts; i++) {
			//load the Unit
			AbstractPort aPort = AbstractPort.loadFrom(reader, this);
			this.addPort(aPort);
		}

		//Links
		int cantLinks = Integer.parseInt(reader.readLine());

		for (int i = 0; i < cantLinks; i++) {
			this.addLink(AbstractLink.loadFrom(reader, this));
		}

	}

	/**
	 * @see gui.model.Model#draw(Graphics, ImageObserver)
	 */
	public Dimension draw(Graphics aPen, ImageObserver observer) {
		Dimension newSize = super.draw(aPen, observer);

		//draw the ports that are from the model itself

		Iterator ports = getPorts().iterator();
		while (ports.hasNext()) {
			CoupledPort each = (CoupledPort) ports.next();
			newSize =
				DimensionUtil.getMaxEdges(newSize, each.draw(aPen, observer));
		}
		return newSize;

	}

	/**
	 * in a coupledGraph all the OnePointLayautables are Dragables
	 */
	public Collection getDragablesAt(Point aPoint) {
		return getOnePointLayoutablesAt(aPoint);
	}

	/**
	 * rebuilds the tree based on the data I have
	 *
	 */
	public void rebuildTree() {
		//		if this graph gas a tree of itself..
		if (getMainFrame() != null) {

			getMainFrame().getCoupledUnitsTree().clearCoupledTreeNodes();

			Iterator states = getModels().iterator();
			while (states.hasNext()) {
				addToTree((AbstractUnit) states.next());
			}
			Iterator ports = getPorts().iterator();
			while (ports.hasNext()) {
				addToTree((AbstractPort) ports.next());
			}
			Iterator links = getLinks().iterator();
			while (links.hasNext()) {
				addToTree((AbstractLink) links.next());
			}

		}
	}

	/** 
	 * @see gui.model.Model#addToTree(gui.model.AbstractUnit)
	 */
	protected DefaultMutableTreeNode addToTree(AbstractUnit aState) {
		//insert the node to the treee
		DefaultMutableTreeNode retr = super.addToTree(aState);

		AbstractModelUnit unit = (AbstractModelUnit) aState;
		Iterator ports = unit.getPorts().iterator();
		while (ports.hasNext()) {
			AbstractPort each = (AbstractPort) ports.next();
			if (getUnitsTreeModel() != null) {
				getUnitsTreeModel().insertNodeInto(
					each,
					retr,
					retr.getChildCount());
			}

		}
		return retr;
	}
	/* (non-Javadoc)
	 * @see gui.model.Model#getExportExtension()
	 */
	public String getExportExtension() {
		return "MA";
	}

	/**
	 * Checks if all the links are attached to ports
	 * Returns the errors if any or ""
	 */
	public String checkLink() {

		StringBuffer retr = new StringBuffer();

		Iterator links = getLinks().iterator();
		while (links.hasNext()) {
			AbstractCoupledLink link = (AbstractCoupledLink) links.next();

			Expression start = link.getStartExpression();
			Expression end = link.getEndExpression();

			if (start == null || end == null) {
				retr.append(link.getDescription() + " is not attached.\n");
			}
		}
		return retr.toString();
	}

	public void delete(Object item) {
		if (item instanceof AbstractLink) {
			deleteLink((AbstractLink) item);
		}
		if (item instanceof AbstractUnit) {
			deleteState((AbstractUnit) item);
		}
		if (item instanceof CoupledPort) {
			deletePort((AbstractPort) item);
		}
	}

}