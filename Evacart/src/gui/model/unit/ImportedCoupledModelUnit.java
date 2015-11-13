/*
 * Created on 09/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.unit;



import gui.javax.io.CommentBufferedReader;
import gui.model.model.AbstractModel;
import gui.model.model.ImportedCoupledModel;

import java.awt.Point;
import java.io.File;

/**
 * @author jcidre
 *
 */
public class ImportedCoupledModelUnit extends CoupledModelUnit {


	/**
	 * 
	 */
	public ImportedCoupledModelUnit() {
		super();
	}

	/**
	 * @param aPoint
	 * @param model
	 * @param name
	 * @param id
	 */
	public ImportedCoupledModelUnit(Point aPoint, AbstractModel model, String name, Object id) {
		super(aPoint, model, name, id);
	}

	/* (non-Javadoc)
	 * @see gui.model.AbstractModelUnit#createNewModel(java.io.File)
	 */
	protected AbstractModel createNewModel(File path, File classpath) throws Exception {
		ImportedCoupledModel model = new ImportedCoupledModel();
		model.setModelFileName(classpath);
		model.setActualPath(path);
		//model.reload();
		return model;
	}

	/* (non-Javadoc)
		 * @see gui.model.AbstractModelUnit#checkModelForParentSave()
		 */
		public String checkModelForParentSave() {
			StringBuffer retr = new StringBuffer();
			String id = this.getName();

			if ((id == null) || (id.equals(""))) {
				String line = "Model " + this.toString() + " have not ID\n";
				retr.append(line);
			}
			retr.append(getModel().checkModelForParentSave());
			return retr.toString();
		}

	/* (non-Javadoc)
	 * @see gui.model.Descriptable#getDescription()
	 */
	public String getDescription() {
		return 	
			"Unit Name: " + getName()+"\n" +
			"Class: " + getModel().getModelName() + "\n" + 
			"ClassPath: No Classpath since imported from .ma file\n" +
			"ExportClassPath: " + getModel().getExportClasspath() + "\n";
	}
	
	/* (non-Javadoc)
	 * @see gui.model.unit.AbstractUnit#loadOtherUnitDataFrom(gui.javax.io.CommentBufferedReader, gui.model.model.Model)
	 */
	protected void loadOtherUnitDataFrom(
		CommentBufferedReader reader,
		AbstractModel graph)
		throws Exception {
		super.loadOtherUnitDataFrom(reader, graph);
		this.getModel().reload();
	}

}
