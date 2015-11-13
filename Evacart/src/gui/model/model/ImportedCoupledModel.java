/*
 * Created on 10/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.model;

import gui.MainFrame;
import gui.cdd.Translator;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.port.AbstractPort;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

/**
 * @author jcidre
 *
 */
public class ImportedCoupledModel extends CoupledModel {

	private Vector componentNames;

	/**
	 * 
	 */
	public ImportedCoupledModel() {
		super();
	}

	/**
	 * @param mainFrame
	 */
	public ImportedCoupledModel(MainFrame mainFrame) {
		super(mainFrame);
	}

	/**
	 * @param mainFrame
	 * @param atitle
	 * @param v1
	 * @param v2
	 * @param v3
	 */
	public ImportedCoupledModel(MainFrame mainFrame, String atitle, Vector v1, Vector v2, Vector v3) {
		super(mainFrame, atitle, v1, v2, v3);

	}

	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForExport(java.lang.String)
	 */
	public String checkModelForExport(String identifyingId) throws IOException {
		return "";
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForSave(java.lang.String)
	 */
	public String checkModelForSave(String identifyingId) throws IOException {
		return "";
	}
	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForParentSave()
	 */
	public String checkModelForParentSave() {
		return "";
	}
	

	/* (non-Javadoc)
	 * @see gui.model.Model#delete(java.lang.Object)
	 */
	public void delete(Object item) {
		throw new RuntimeException("this method shold not be used");

	}

	/* (non-Javadoc)
	 * @see gui.model.Model#deletePort(gui.model.Port)
	 */
	public void deletePort(AbstractPort aPort) {
		throw new RuntimeException("this method shold not be used");

	}



	/* (non-Javadoc)
	 * @see gui.model.Model#getDragablesAt(java.awt.Point)
	 */
	public Collection getDragablesAt(Point aPoint) {
		throw new RuntimeException("this method shold not be used");
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#getExportExtension()
	 */
	public String getExportExtension() {
		throw new RuntimeException("this method shold not be used");
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#loadFrom(gui.javax.io.CommentBufferedReader, gui.MainFrame)
	 */
	public void loadFrom(CommentBufferedReader reader, MainFrame mainFrame) throws Exception {
		//String modelName = reader.readLine();
		//String classpath = reader.readLine();
		
		//this.setModelName(modelName);
		//this.setClasspath(new File(classpath));


	}

	/* (non-Javadoc)
	 * @see gui.model.Model#rebuildTree()
	 */
	public void rebuildTree() {
		throw new RuntimeException("this method shold not be used");

	}

	/* (non-Javadoc)
	 * @see gui.model.Model#saveTo(gui.javax.io.CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		//writer.writeln("ImportedCoupled name ", this.getModelName());
		//writer.writeln("ImportedCoupled classpath ", this.getClasspath().getAbsolutePath());


	}

	/**
	 * @param componentNames
	 */
	public void setComponentNames(Vector componentNames) {
		this.componentNames = componentNames;
		
	}

	/**
	 * @return
	 */
	public Vector getComponentNames() {
		return componentNames;
	}

	/* (non-Javadoc)
	 * @see gui.model.model.Model#checkModelForParentExport(java.lang.String)
	 */
	public String checkModelForParentExport(String identifyingId) throws IOException {
		return checkModelForExport(identifyingId);
	}

	/**
	 * reload the graph from the file.
	 * If there is no filename setted, reset the graph
	 *
	 */
	public void reload() throws Exception {
		if(getActualPath() != null && getExportClasspath() != null){
			File exportFile = new File(getActualPath().getPath(), getExportClasspath().getName()); 
			AbstractModel model = (AbstractModel)Translator.importFrom(exportFile).iterator().next();
			this.setLinks(model.getLinks());
			this.setModelName(model.getModelName());
			this.setModels(model.getModels());
			this.setPorts(model.getPorts());
		}
	}
}
