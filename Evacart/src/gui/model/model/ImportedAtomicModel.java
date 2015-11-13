/*
 * Created on 09/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.model;

import gui.MainFrame;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.awt.Point;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;



/**
 * @author jcidre
 *
 */
public abstract class ImportedAtomicModel extends AtomicModel{
	
	

	/**
	 * 
	 */
	public ImportedAtomicModel() {
		super();
		
	}

	/**
	 * @param mainFrame
	 */
	public ImportedAtomicModel(MainFrame mainFrame) {
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
	public ImportedAtomicModel(String atitle, Vector v1, Vector v2, Vector v3, Vector v4, Vector initParams, MainFrame mainFrame) {
		super(atitle, v1, v2, v3, v4, initParams, mainFrame);
		
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForExport(java.lang.String)
	 */
	public String checkModelForExport(String identifyingId) throws IOException {
		return "";
	}
	/* (non-Javadoc)
	 * @see gui.model.model.Model#checkModelForParentExport(java.lang.String)
	 */
	public String checkModelForParentExport(String identifyingId) throws IOException {
		return checkModelForExport(identifyingId);
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForSave(java.lang.String)
	 */
	public String checkModelForSave(String identifyingId) throws IOException {
		return "";
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#delete(java.lang.Object)
	 */
	public void delete(Object item) {
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
		/*
		String modelName = reader.readLine();
		String classpath = reader.readLine();
		
		this.setModelName(modelName);
		this.setClasspath(new File(classpath));
		*/
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
		//writer.writeln("ImportedAtomic name ", this.getModelName());
		//writer.writeln("ImportedAtomic classpath ", this.getClasspath().getAbsolutePath());
	}
	
	/* (non-Javadoc)
	 * @see gui.model.Model#checkModelForParentSave()
	 */
	public String checkModelForParentSave() {
		return "";
	}

}
