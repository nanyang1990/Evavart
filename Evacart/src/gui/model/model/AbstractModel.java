package gui.model.model;

import gui.InformDialog;
import gui.MainFrame;
import gui.cdd.Translator;
import gui.graphEditor.Layoutable;
import gui.graphEditor.OnePointLayoutable;
import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.swing.FinderTreeModel;
import gui.javax.util.DimensionUtil;
import gui.javax.util.Identifiable;
import gui.javax.util.Selectable;
import gui.javax.util.Sequence;
import gui.model.link.AbstractCoupledLink;
import gui.model.link.AbstractLink;
import gui.model.port.AbstractPort;
import gui.model.port.PortContainer;
import gui.model.unit.AbstractUnit;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public abstract class AbstractModel implements PortContainer, Identifiable {
	/**
	 * path where resides the model
	 */
	private File actualPath;
	private Vector models;
	private Sequence sequence;
	private File modelFileName;
	private String modelName;
	private Object uniqueId;
	private File backgroundImage;
	private Vector links;
	private Vector ports;
	private MainFrame mainFrame;	
	

	/**
	 * classpath of the file exported to CD++
	 */
	private File exportClasspath;

	/**
	 * returns the Dragables. The Dragables are the Layoutables that are shown
	 * in the graph and can be moved.
	 */
	public abstract Collection getDragablesAt(Point aPoint);
	protected abstract FinderTreeModel getUnitsTreeModel();
	protected abstract MutableTreeNode getRootStatesNode();
	protected abstract MutableTreeNode getRootLinksNode();
	protected abstract MutableTreeNode getRootPortsNode();

	/**
	 * rebuilds the tree based on the data I have
	 *
	 */
	public abstract void rebuildTree();

	/**
	 * returns the extension of an exported file to CD++
	 */
	public abstract String getExportExtension();

	public void deleteState(AbstractUnit aState) {
		Iterator someLinks = getIncidentLinks(aState).iterator();
		while (someLinks.hasNext()) {
			AbstractLink aLink = (AbstractLink)someLinks.next();
			this.deleteLink(aLink);
		}
		/*
				Iterator somePorts = ((Vector)aState.getPorts().clone()).iterator();
				while (somePorts.hasNext()) {
					Port aPort = (Port) somePorts.next();
					this.deletePort(aPort);
				}
		*/
		getModels().removeElement(aState);
		
		//@BUG when reloading a unit, it first cleans it's model, wich calls this
		//method, but since the unit is not being edited, it has no tree model
		if(getUnitsTreeModel() != null){
		    getUnitsTreeModel().removeNodeFromParent(aState);
		}

	}

	public void delete(Collection items) {
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
			this.delete(iter.next());
		}
	}

	public abstract void delete(Object item);
	
	public void deleteLink(AbstractLink aLink) {
		//remove it from the store of links			 
		getLinks().removeElement(aLink);

		//remove it from the tree
		//@BUG when reloading a unit, it first cleans it's model, wich calls this
		//method, but since the unit is not being edited, it has no tree model
		if(getUnitsTreeModel() != null){
		    getUnitsTreeModel().removeNodeFromParent(aLink);
		}

	}

	public abstract void deletePort(AbstractPort aPort);

	/**
	 * draw this graph on aPen
	 */
	public Dimension draw(Graphics aPen, ImageObserver observer) {
		//max edge
		Dimension maxEdge = new Dimension(0, 0);
		for (Iterator links = getLinks().iterator(); links.hasNext();) {
            AbstractLink each = (AbstractLink) links.next();
            prepareLinkToBeRendered(each);
            maxEdge = DimensionUtil.getMaxEdges(maxEdge, each.draw(aPen, observer));
        }

		for (Iterator models = getModels().iterator(); models.hasNext();) {
            AbstractUnit each = (AbstractUnit) models.next();
            prepareModelToBeRendered(each);
            maxEdge = DimensionUtil.getMaxEdges(maxEdge, each.draw(aPen, observer));
        }
		return maxEdge;
	}

	/**
     * @param each
     * initialize the model before rendering it
     */
    protected void prepareModelToBeRendered(AbstractUnit each) {
    }
    /**
     * @param each
     * initialize the model before rendering it 
     */
    protected abstract void prepareLinkToBeRendered(AbstractLink each);
  
    
    public Vector getSelectedStates() {
		Vector selectedStates = new Vector();
		Enumeration alltheStates = getModels().elements();
		while (alltheStates.hasMoreElements()) {
			AbstractUnit tmpState = (AbstractUnit)alltheStates.nextElement();
			if (tmpState.isSelected())
				selectedStates.addElement(tmpState);
		}
		return selectedStates;
	}

	public Vector getSelectedLinks() {
		Vector selectedLinks = new Vector();
		Enumeration alltheLinks = getLinks().elements();
		while (alltheLinks.hasMoreElements()) {
			AbstractLink tmpLink = (AbstractLink)alltheLinks.nextElement();
			if (tmpLink.isSelected())
				selectedLinks.addElement(tmpLink);
		}
		return selectedLinks;
	}

	public void addPort(AbstractPort aPort) {
		if (aPort != null) {
			//adding a port to the model itself

			//add it to the collection
			getPorts().add(aPort);

			//add it tho the tree
			addToTree(aPort);
		}

	}

	protected DefaultMutableTreeNode addToTree(AbstractPort aPort) {
		MutableTreeNode parent = getRootPortsNode();
		if (parent != null) {
			return getUnitsTreeModel().insertNodeInto(aPort, parent, parent.getChildCount());
		}
		else {
			return null;
		}

	}

	public void addPorts(Collection ports) {
		Iterator iter = ports.iterator();
		while (iter.hasNext()) {
			AbstractPort each = (AbstractPort)iter.next();
			addPort(each);
		}
	}
	public void addUnit(AbstractUnit aState) {
		//Thread.dumpStack();
		//System.err.println("*" + System.currentTimeMillis());
		if (aState != null) {
			//add it to the collection
			getModels().addElement(aState);

			//add it to the tree
			addToTree(aState);
		}

	}
	protected DefaultMutableTreeNode addToTree(AbstractUnit aState) {
		MutableTreeNode parent = getRootStatesNode();
		if (parent != null) {
			return getUnitsTreeModel().insertNodeInto(aState, parent, parent.getChildCount());
		}
		else {
			return null;
		}
	}
	public void addLink(AbstractLink aLink) {
		if (aLink != null) {

			//add it to the collection
			getLinks().add(aLink);

			//add it to the tree
			addToTree(aLink);
		}

	}
	protected DefaultMutableTreeNode addToTree(AbstractLink aLink) {
		MutableTreeNode parent = getRootLinksNode();
		if (parent != null) {
			return getUnitsTreeModel().insertNodeInto(aLink, parent, parent.getChildCount());
		}
		else {
			return null;
		}
	}
	public AbstractUnit findUnitByName(String name) {
		for (int i = 0; i < getModels().size(); i++) {
			String modelname = ((AbstractUnit)getModels().get(i)).getName();
			if (name.equalsIgnoreCase(modelname)) {
				return (AbstractUnit)getModels().get(i);
			}
		}
		return null;
	}
	public Collection findLinksAttachedTo(AbstractPort aPort) {
		Collection retr = new ArrayList();
		for (int i = 0; i < getLinks().size(); i++) {
			AbstractCoupledLink aLink = (AbstractCoupledLink)getLinks().get(i);
			if (aLink.getStartExpression().equals(aPort)) {
				retr.add(aLink);
			}
			if (aLink.getEndExpression().equals(aPort)) {
				retr.add(aLink);
			}

		}
		return retr;
	}

	public Layoutable findUnitByUniqueId(String id) {
		Iterator units = getModels().iterator();
		while (units.hasNext()) {
			AbstractUnit each = (AbstractUnit)units.next();
			if (each.getUniqueId().equals(id)) {
				return each;
			}
		}
		return null;
	}

	public Layoutable findLinkPlugableByUniqueId(String id) {
		Iterator linkPlugables = getLayoutables().iterator();
		while (linkPlugables.hasNext()) {
			Layoutable each = (Layoutable)linkPlugables.next();
			if (((Identifiable)each).getUniqueId().equals(id)) {
				return each;
			}
		}
		return null;
	}

	public AbstractPort findPortByUniqueId(String id) {
		for (int i = 0; i < getPorts().size(); i++) {
			if (id.equals(((AbstractPort)getPorts().get(i)).getUniqueId())) {
				return (AbstractPort)getPorts().get(i);
			}
		}
		return null;
	}

	/**
	 * @deprecated try to use getSelectablesAt
	 */
	public Vector getLinksAt(Point p) {
		Vector retr = new Vector();
		Iterator links = getLinks().iterator();
		while (links.hasNext()) {
			AbstractLink each = (AbstractLink)links.next();
			if (each.inside(p)) {
				retr.add(each);
			}
		}
		return retr;
	}

	public Collection getLayoutables() {
		Collection retr = getAllItems();
		Iterator all = retr.iterator();
		while (all.hasNext()) {
			if (all.next() instanceof Layoutable) {
			}
			else {
				all.remove();
			}
		}
		return retr;
	}

	public Collection getOnePointLayoutables() {
		Collection retr = getAllItems();
		Iterator all = retr.iterator();
		while (all.hasNext()) {
			if (all.next() instanceof OnePointLayoutable) {
			}
			else {
				all.remove();
			}
		}
		return retr;
	}
	public Collection getOnePointLayoutablesAt(Point aPoint) {
		Collection retr = getOnePointLayoutables();
		Iterator all = retr.iterator();
		while (all.hasNext()) {
			OnePointLayoutable each = (OnePointLayoutable)all.next();
			if (each.inside(aPoint)) {
			}
			else {
				all.remove();
			}
		}
		return retr;

	}
	public Collection getSelectables() {
		Collection retr = getAllItems();
		Iterator all = retr.iterator();
		while (all.hasNext()) {
			if (all.next() instanceof Selectable) {
			}
			else {
				all.remove();
			}
		}
		return retr;
	}

	/*
	 * Returns a Vector with all the nodes of aClass
	 */
	public Vector getAll(Class type) {
		Vector retr = new Vector();
		Iterator all = getAllItems().iterator();
		while (all.hasNext()) {
			Object each = all.next();
			if (each.getClass().isInstance(type)) {
				//keep it
				retr.add(each);
			}
		}
		return retr;
	}

	public Collection getAllItems() {
		Collection retr = new Vector();
		retr.addAll(getLinks());
		retr.addAll(getPorts());
		retr.addAll(getModels());
		return retr;
	}

	public Collection getSelectedSelectables() {
		Vector retr = new Vector();
		Iterator selectables = getSelectables().iterator();
		while (selectables.hasNext()) {
			Selectable each = (Selectable)selectables.next();
			if (each.isSelected()) {
				//keep it
				retr.add(each);
			}
		}
		return retr;
	}
	public Collection getSelectablesAt(Point aPlace) {
		Vector retr = new Vector();
		Iterator selectables = getSelectables().iterator();
		while (selectables.hasNext()) {
			Selectable each = (Selectable)selectables.next();
			if (each instanceof Layoutable) {
				if (((Layoutable)each).inside(aPlace)) {
					//keep it
					retr.add(each);
				}
			}
			else {
				throw new RuntimeException("BUG at Graph.getSelectablesAt");
			}

		}
		return retr;
	}
	public AbstractPort getPortbyID(String anID) {
		AbstractPort retr = null;
		AbstractPort tempPort = null;
		Enumeration tempPorts = getPorts().elements();
		while (tempPorts.hasMoreElements()) {
			tempPort = (AbstractPort)tempPorts.nextElement();
			if (tempPort.getPortId().equalsIgnoreCase(anID)) {
				retr = tempPort;
				break;
			}
		}
		return retr;
	}

	/**
	 * 
	 * Save a Model to a File
	 */
	public abstract void saveTo(CommentBufferedWriter writer) throws IOException;

	/**
	 * Load Model from file
	 */

	public abstract void loadFrom(CommentBufferedReader reader, MainFrame mainFrame) throws Exception;
	/**
	 * Checks if all the links are attached to ports
	 * Returns the errors if any or ""
	 */
	public String checkLink() {

		StringBuffer retr = new StringBuffer();

		Iterator links = getLinks().iterator();
		while (links.hasNext()) {
			AbstractLink link = (AbstractLink)links.next();

			Layoutable start = link.getStartLinkPlugable();
			Layoutable end = link.getEndLinkPlugable();
		}
		return retr.toString();
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

	/**
	 * Returns the classname.
	 * @return String
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * Returns the classpath.
	 * @return String
	 */
	public File getModelFileName() {
		//System.err.println(getClass().getName() +" <-- " + classpath);
		return modelFileName;
	}

	public String getClassFilename() {
		return this.getModelFileName().getName();
	}
	/**
	 * Sets the classname.
	 * @param classname The classname to set
	 */
	public void setModelName(String classname) {
		modelName = classname;
	}

	/**
	 * Sets the classpath.
	 * @param classpath The classpath to set
	 */
	public void setModelFileName(File classpath) {
		this.modelFileName = classpath;
	}

	/**
	 * return all the links attached to a layoutable
	 * @param layoutable
	 * @return
	 */
	public Collection getIncidentLinks(Object layoutable) {
		Collection retr = new ArrayList();
		Iterator links = getLinks().iterator();
		while (links.hasNext()) {
			AbstractLink each = (AbstractLink)links.next();
			if (each.getEndLinkPlugable().equals(layoutable)) {
				retr.add(each);
			}
			else if (each.getStartLinkPlugable().equals(layoutable)) {
				retr.add(each);
			}
		}
		return retr;
	}

	/**
	 * reload the graph from the file.
	 * If there is no filename setted, reset the graph
	 *
	 */
	public void reload() throws Exception {
		//first, blank everithing
		this.clean();
		//now reload data
		this.loadFrom(getActualPath(), getModelFileName());
	}
	private void clean() {
		this.delete(this.getAllItems());
	}
	public void loadFrom(File path, File filename) throws Exception {
		if (path != null && filename != null) {
			File modelFile = new File(path.getAbsolutePath() + File.separator + filename.getName());

			if (modelFile != null && modelFile.exists()) {
				this.setActualPath(path);
					CommentBufferedReader reader = new CommentBufferedReader(new FileReader(modelFile));

				this.loadFrom(reader, getMainFrame());
				reader.close();
			}
			else {
				(new InformDialog("Error loading model \"" + filename + "\".It should be in " + path,null)).setVisible(true);
			}
		}
	}

	public abstract String checkModelForSave(String identifyingId) throws IOException;

	public abstract String checkModelForExport(String identifyingId) throws IOException;
	public abstract String checkModelForParentExport(String identifyingId) throws IOException;

	/*
	 * Makes a light check of this model to see if my parent (a Coupled model)
	 * is ok to be saved
	 */
	public abstract String checkModelForParentSave();

	/**
	 * @return
	 */
	public Sequence getSequence() {
		if (sequence == null) {
			sequence = new Sequence();
		}
		return sequence;
	}
	//		this.repaint();

	/**
	 * Exports this model to a file with the CD++ format
	 */
	public boolean exportfile() {
		try {
			JFileChooser fc = new JFileChooser();

			if (getExportClasspath() != null) {
				fc.setSelectedFile(getExportClasspath());
			}

			fc.addChoosableFileFilter(new ExtensionFilter(getExportExtension()));

			int returnVal = fc.showDialog(getMainFrame(), "Save");

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String exportFileName = fc.getSelectedFile().getName();
				this.setActualPath(fc.getSelectedFile().getParentFile());
				//if the exported file has no .ma extension, add it.
				if (!exportFileName.toUpperCase().endsWith("." + getExportExtension())) {
					exportFileName += "." + getExportExtension();
				}

				File file = new File(exportFileName);
				this.setExportClasspath(file);

				//now check that this model is able to be exported
				String check = this.checkModelForExport("this");
				if (!check.equals("")) {
					InformDialog diag = new InformDialog(check,null);
					diag.setVisible(true);
					return false;
				}
				File fullFilename = new File(this.getActualPath() + File.separator + file.getName());
			
				Translator.exportToCDPP(this, fullFilename);

				return true;
			}
			else {
				return false;
			}

		}
		catch (IOException e) {
			getMainFrame().getmainstatusBar().setText("Error exporting. \n" + e.toString());
			return false;
		}

	}

	/**
	 * @return
	 */
	public File getExportClasspath() {
		return exportClasspath;
	}

	/**
	 * @param string
	 */
	public void setExportClasspath(File string) {
		exportClasspath = string;
	}

	/**
	 * Returns the uniqueId.
	 * @return Object
	 */
	public Object getUniqueId() {

		if (uniqueId == null) {
			uniqueId = this.getClass().getName() + this.getModelName() + this.getSequence().next();
		}
		return uniqueId;
	}

	/**
	 * Sets the uniqueId.
	 * @param uniqueId The uniqueId to set
	 */
	public void setUniqueId(Object uniqueId) {
		this.uniqueId = uniqueId;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		boolean retr = true;
		if (!this.getClass().equals(obj.getClass())) {
			retr = false;
		}
		AbstractModel other = (AbstractModel)obj;

		if (!other.getModelFileName().equals(this.getModelFileName())) {
			retr = false;
		}
		return retr;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getModelName();
	}

	/**
	 * Create a Link from start to end
	 */
	public abstract AbstractLink createNewLink(Point start, Point end, Point through, String linkType);

	/**
	 * Create a Unit from de Class selected in the class
	 * tree
	 */
	public abstract AbstractUnit createNewUnit(AbstractModel model, Point point);

	/**
	 * Create a Port
	 */
	public abstract AbstractPort createNewPort();

	/**
	 * @return
	 */
	public File getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * @param string
	 */
	public void setBackgroundImage(File string) {
		backgroundImage = string;
	}

	/**
	 * @return
	 */
	public File getActualPath() {
		return actualPath;
	}

	/**
	 * @param file
	 */
	public void setActualPath(File file) {
		actualPath = file;
	}

	/**
	 * @return
	 */
	public Vector getModels() {
		return models;
	}

	/**
	 * @param vector
	 */
	public void setModels(Vector vector) {
		models = vector;
	}
	public Vector getPorts() {
		return ports;
	}


	/**
	 * Sets the ports.
	 * @param ports The ports to set
	 */
	public void setPorts(Vector ports) {
		this.ports = ports;
	}


	/**
		 * @return
		 */
	public Vector getLinks() {
		return links;
	}

	/**
		 * @param vector
		 */
	public void setLinks(Vector vector) {
		links = vector;
	}
	

	/**
	 * @return
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * @param sequence
	 */
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	/**
	 * @param frame
	 */
	public void setMainFrame(MainFrame frame) {
		mainFrame = frame;
	}

}