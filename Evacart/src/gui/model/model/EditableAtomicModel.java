package gui.model.model;

import gui.MainFrame;
import gui.graphEditor.Layoutable;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.Action;
import gui.model.Variable;
import gui.model.link.AbstractAtomicLink;
import gui.model.link.AbstractLink;
import gui.model.port.AbstractPort;
import gui.model.unit.AbstractUnit;
import gui.model.unit.AtomicUnit;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public class EditableAtomicModel extends AtomicModel {

	private AbstractUnit initialUnit;

	/**
	 * 
	 */
	public EditableAtomicModel() {
		super();
	}

	/**
	 * @param mainFrame
	 */
	public EditableAtomicModel(MainFrame mainFrame) {
		super(mainFrame);
	}

	/**
	 * @param atitle
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param v4
	 * @param mainFrame
	 */
	public EditableAtomicModel(String atitle, Vector v1, Vector v2, Vector v3, Vector v4, Vector initParams, MainFrame mainFrame) {
		super(atitle, v1, v2, v3, v4, initParams, mainFrame);
	}

	public void addVariable(Variable aVariable) {
		if (aVariable != null) {
			//adding a var to the model itself

			//add it to the collection
			getVariables().add(aVariable);

			//add it tho the tree
			addToTree(aVariable);
		}
	}

	public void addAction(Action anAction, AbstractAtomicLink aLink) {
		if (anAction != null) {
			//adding the action to the Link

			//add it to the collection
			aLink.addAction(anAction);

			//add it tho the tree
			addToTree(anAction, aLink);
		}
	}

	protected DefaultMutableTreeNode addToTree(Variable aVariable) {
		MutableTreeNode parent = getRootVarsNode();
		if (parent != null) {
			return getUnitsTreeModel().insertNodeInto(aVariable, parent, parent.getChildCount());
		}
		else {
			return null;
		}
	}

	protected DefaultMutableTreeNode addToTree(Action anAction, AbstractAtomicLink aLink) {
		MutableTreeNode parent = getUnitsTreeModel().find(aLink);
		if (parent != null) {
			return getUnitsTreeModel().insertNodeInto(anAction, parent, parent.getChildCount());
		}
		else {
			return null;
		}
	}

	public void deleteAction(Action anAction, AbstractAtomicLink aLink) {
		aLink.deleteAction(anAction);
		getUnitsTreeModel().removeNodeFromParent(anAction);
	}
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
		if (getBackgroundImage() != null) {
			writer.writeln("background", getBackgroundImage().getName());
		}
		else {
			writer.writeln("background", "");
		}

		//States
		writer.writeln("cant units", getModels().size());
		writer.increaseTab();
		Iterator tmpStates = getModels().iterator();
		while (tmpStates.hasNext()) {
			//save the Unit
			AbstractUnit tmpState = (AbstractUnit)tmpStates.next();
			tmpState.saveTo(writer);
		}
		writer.decreaseTab();
		//ports
		writer.writeln("Cant Ports", getPorts().size());
		Iterator tmpPorts = getPorts().iterator();
		writer.increaseTab();
		while (tmpPorts.hasNext()) {
			//save the Unit
			AbstractPort tmpPort = (AbstractPort)tmpPorts.next();
			tmpPort.saveTo(writer);
		}
		writer.decreaseTab();

		//Links

		writer.writeln("Cant Links", getLinks().size());
		Iterator tmpLinks = getLinks().iterator();
		writer.increaseTab();
		while (tmpLinks.hasNext()) {
			AbstractLink tmpLink = (AbstractLink)tmpLinks.next();
			tmpLink.saveTo(writer);
		}
		writer.decreaseTab();

		//Initial state
		if (getInitialUnit() != null) {
			writer.writeln("Initial Name", getInitialUnit().getUniqueId().toString());
		}
		else {
			writer.writeln("Initial Name", "");
		}

		//Variables
		Enumeration tmpVars = getVariables().elements();
		writer.writeln("Vars cant", getVariables().size());
		writer.increaseTab();
		while (tmpVars.hasMoreElements()) {
			Variable tmpVar = (Variable)tmpVars.nextElement();
			tmpVar.saveTo(writer);
		}
		writer.decreaseTab();
	}
	/**
	 * Load Model from file
	 */
	public void loadFrom(CommentBufferedReader reader, MainFrame mainFrame) throws Exception {
		//String graphClass = reader.readLine();
		//Model graph = (Model)(java.lang.Class.forName(graphClass)).newInstance();

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
		this.setModelName(reader.readLine());
		
		this.setBackgroundImage(new File(reader.readLine()));

		//States
		int cantUnits = Integer.parseInt(reader.readLine());
		for (int i = 0; i < cantUnits; i++) {
			//load the Unit
			AbstractUnit aUnit = AbstractUnit.loadFrom(reader, this);
			this.addUnit(aUnit);
		}

		//Ports
		int cantPorts = Integer.parseInt(reader.readLine());
		for (int i = 0; i < cantPorts; i++) {
			//load the Port
			AbstractPort aPort = AbstractPort.loadFrom(reader, this);
			this.addPort(aPort);
		}

		//Links
		int cantLinks = Integer.parseInt(reader.readLine());

		for (int i = 0; i < cantLinks; i++) {
			this.addLink(AbstractLink.loadFrom(reader, this));
		}

		String initianUnit = reader.readLine();
		if (initianUnit != null && !initianUnit.equals("")) {
			setInitialUnit((AbstractUnit)findUnitByUniqueId(initianUnit));
		}

		//variables
		int cantVars = Integer.parseInt(reader.readLine());
		for (int i = 0; i < cantVars; i++) {
			this.addVariable(Variable.loadFrom(reader));
		}

	}

	/**
	 * Sets the initialUnit.
	 * @param initialUnit The initialUnit to set
	 */
	public void setInitialUnit(AbstractUnit initialState) {
		this.initialUnit = initialState;
	}

	/**
	 * in an atomicGraph only de Units are Dragables 
	 */
	public Collection getDragablesAt(Point aPoint) {
		Collection lays = (Collection)getModels().clone();
		Iterator retr = lays.iterator();
		while (retr.hasNext()) {
			if (((Layoutable)retr.next()).inside(aPoint)) {
			}
			else {
				retr.remove();
			}
		}
		return lays;
	}

	/**
	 * rebuilds the tree based on the data I have
	 *
	 */
	public void rebuildTree() {
		//if this graph gas a tree of itself..
		if (getMainFrame() != null) {

			getMainFrame().getAtomicUnitsTree().clearAtomicTreeNodes();

			Iterator states = getModels().iterator();
			while (states.hasNext()) {
				addToTree((AbstractUnit)states.next());
			}
			Iterator ports = getPorts().iterator();
			while (ports.hasNext()) {
				addToTree((AbstractPort)ports.next());
			}
			Iterator links = getLinks().iterator();
			while (links.hasNext()) {
				addToTree((AbstractLink)links.next());
			}

		}
	}

	public Collection getAllItems() {
		Collection retr = super.getAllItems();
		retr.addAll(getVariables());
		return retr;
	}
	/* (non-Javadoc)
	 * @see gui.model.Model#getExportExtension()
	 */
	public String getExportExtension() {
		return "CDD";
	}

	public void delete(Object item) {
		if (item instanceof AbstractLink) {
			deleteLink((AbstractLink)item);
		}
		if (item instanceof AbstractUnit) {
			deleteState((AbstractUnit)item);
		}
		if (item instanceof AbstractPort) {
			deletePort((AbstractPort)item);
		}
		if (item instanceof Variable) {
			deleteVariable((Variable)item);
		}
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#addToTree(gui.model.AbstractLink)
	 */
	protected DefaultMutableTreeNode addToTree(AbstractLink aLink) {
		//insert the node to the treee
		DefaultMutableTreeNode retr = super.addToTree(aLink);
		if (retr != null) {
			AbstractAtomicLink link = (AbstractAtomicLink)aLink;
			Iterator actions = link.getActions().iterator();
			while (actions.hasNext()) {
				Action each = (Action)actions.next();
				if (getUnitsTreeModel() != null) {
					getUnitsTreeModel().insertNodeInto(each, retr, retr.getChildCount());
				}

			}
		}
		return retr;

	}

	public String checkModelForSave(String identifyingId) throws IOException {
		return "";
	}

	public String checkModelForExport(String identifyingId) throws IOException {

		StringBuffer retr = new StringBuffer();
		retr.append(checkModelForSave(identifyingId));

		// check that the graph has a classname
		if (getModelName() == null || getModelName().equals("")) {
			retr.append("Must select a name for " + identifyingId + " Model.\n");
		}

		// check that the model has an exportFilename
		if (getExportClasspath() == null || getExportClasspath().equals("")) {
			retr.append("Must select an export file for " + identifyingId + " model.\n");
		}

		// check each state should have ID 
		for (int j = 0; j < getModels().size(); j++) {
			AtomicUnit aState = (AtomicUnit)getModels().elementAt(j);
			//			int m = aState.getPorts().size();

			String id = aState.getName().trim();

			if ((id.length() == 0)) {

				String line = "State " + " at " + aState.toString() + " have not ID\n";
				retr.append(line);
			}

		}

		// check each port first, every port should have ID and at leat one incident link
		for (int i = 0; i < getPorts().size(); i++) {
			AbstractPort aport = (AbstractPort)getPorts().elementAt(i);

			String id = aport.getPortId();

			if ((id == null)) {
				String line = "Port " + " at " + aport.getName() + " have not ID\n";
				retr.append(line);
			}

		}

		// check each link has two connected edges
		for (int m = 0; m < getLinks().size(); m++) {
			AbstractAtomicLink link = (AbstractAtomicLink)getLinks().get(m);

			Layoutable start = link.getStartLinkPlugable();
			Layoutable end = link.getEndLinkPlugable();

			if ((start == null) && (end == null)) {

				String line = "Two ends of a link are not connected" + ", one at " + start.getName() + ", the other at " + end.getName();
				retr.append(line);
			}

			if ((start == null)) {
				String line = "One end of a link is not connected" + ", at " + start.getName() + "\n";
				retr.append(line);
			}

			if ((end == null)) {
				String line = "One end of a link is not connected" + ", at " + end.getName() + "\n";
				retr.append(line);
			}
		}

		//there must be an initial unit
		if (getInitialUnit() == null) {
			String line = "There is not an initial Unit at Model " + identifyingId + ".\n";
			retr.append(line);

		}
		return retr.toString();

	}

	/**
		 * Returns the initialUnit.
		 * @return Unit
		 */
	public AbstractUnit getInitialUnit() {
		return initialUnit;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForParentSave()
	 */
	public String checkModelForParentSave() {
		return "";
	}

	/* (non-Javadoc)
	 * @see gui.model.model.Model#checkModelForParentExport(java.lang.String)
	 */
	public String checkModelForParentExport(String identifyingId) throws IOException {
		return checkModelForExport(identifyingId);
	}

}