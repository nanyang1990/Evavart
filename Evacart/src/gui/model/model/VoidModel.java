/*
 * Created on 20/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.model;

import gui.MainFrame;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.swing.FinderTreeModel;
import gui.model.link.AbstractLink;
import gui.model.port.AbstractPort;
import gui.model.unit.AbstractUnit;

import java.awt.Point;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class VoidModel extends AbstractModel {

	/* (non-Javadoc)
	 * @see gui.model.Model#getDragablesAt(java.awt.Point)
	 */
	public Collection getDragablesAt(Point aPoint) {
		return null;
	}
	/* (non-Javadoc)
	 * @see gui.model.Model#getModels()
	 */
	public Vector getModels() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#getLinks()
	 */
	public Vector getLinks() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#getUnitsTreeModel()
	 */
	protected FinderTreeModel getUnitsTreeModel() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#getRootStatesNode()
	 */
	protected MutableTreeNode getRootStatesNode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#getRootLinksNode()
	 */
	protected MutableTreeNode getRootLinksNode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#getRootPortsNode()
	 */
	protected MutableTreeNode getRootPortsNode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#setMainFrame(gui.MainFrame)
	 */
	public void setMainFrame(MainFrame mainFrame) {
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#getMainFrame()
	 */
	public MainFrame getMainFrame() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#rebuildTree()
	 */
	public void rebuildTree() {

	}

	/* (non-Javadoc)
	 * @see gui.model.Model#getExportExtension()
	 */
	public String getExportExtension() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#delete(java.lang.Object)
	 */
	public void delete(Object item) {
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#deletePort(gui.model.Port)
	 */
	public void deletePort(AbstractPort aPort) {

	}

	/* (non-Javadoc)
	 * @see gui.model.Model#saveTo(gui.javax.io.CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {

	}

	/* (non-Javadoc)
	 * @see gui.model.Model#loadFrom(gui.javax.io.CommentBufferedReader, gui.MainFrame)
	 */
	public void loadFrom(CommentBufferedReader reader, MainFrame mainFrame)
		throws Exception {

	}

	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForSave(java.lang.String)
	 */
	public String checkModelForSave(String identifyingId) throws IOException {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForExport(java.lang.String)
	 */
	public String checkModelForExport(String identifyingId)
		throws IOException {
		return null;
	}
	/* (non-Javadoc)
	 * @see gui.model.model.Model#checkModelForParentExport(java.lang.String)
	 */
	public String checkModelForParentExport(String identifyingId) throws IOException {
		return checkModelForExport(identifyingId);
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForParentSave()
	 */
	public String checkModelForParentSave() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#createNewLink(java.awt.Point, java.awt.Point, java.awt.Point, java.lang.String)
	 */
	public AbstractLink createNewLink(
		Point start,
		Point end,
		Point through,
		String linkType) {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#createNewUnit(gui.model.Model, java.awt.Point)
	 */
	public AbstractUnit createNewUnit(AbstractModel model, Point point) {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#createNewPort()
	 */
	public AbstractPort createNewPort() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.PortContainer#getPorts()
	 */
	public Vector getPorts() {
		return null;
	}

	/* (non-Javadoc)
	 * @see gui.model.PortContainer#setPorts(java.util.Vector)
	 */
	public void setPorts(Vector ports) {

	}
    /* (non-Javadoc)
     * @see gui.model.model.AbstractModel#prepareLinkToBeRendered(gui.model.link.AbstractLink)
     */
    protected void prepareLinkToBeRendered(AbstractLink each) {
        // Void model, nothing to do
        
    }

}
