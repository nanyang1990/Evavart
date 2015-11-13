/*
 * Created on 12/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.unit;

import gui.model.model.AbstractModel;
import gui.model.model.ImportedAtomicModel;
import gui.model.model.ImportedFromCDDAtomicModel;

import java.awt.Point;
import java.io.File;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ImportedFromCDDAtomicModelUnit extends ImportedAtomicModelUnit {
	/**
	 * 
	 */
	public ImportedFromCDDAtomicModelUnit() {
		super();
		
	}

	/**
	 * @param aPoint
	 * @param model
	 * @param name
	 * @param id
	 */
	public ImportedFromCDDAtomicModelUnit(Point aPoint, AbstractModel model, String name, Object id) {
		super(aPoint, model, name, id);

	}

	/* (non-Javadoc)
	 * @see gui.model.AbstractModelUnit#createNewModel(java.io.File)
	 */
	protected AbstractModel createNewModel(File path, File name) throws Exception {
		ImportedAtomicModel model = new ImportedFromCDDAtomicModel();
		model.setActualPath(path);
		model.setModelFileName(name);
		return model;
	}


	/* (non-Javadoc)
	 * @see gui.model.Descriptable#getDescription()
	 */
	public String getDescription() {
		return 	
			"Unit Name: " + getName()+"\n" +
			"Class: " + getModel().getModelName() + "\n" + 
			"ClassPath: No Classpath since imported from .CDD\n" +
			"ExportClassPath: " + getModel().getExportClasspath() + "\n";		
	}

}
