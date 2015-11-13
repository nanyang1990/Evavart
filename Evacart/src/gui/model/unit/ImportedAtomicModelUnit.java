/*
 * Created on 09/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.unit;

import gui.model.model.AbstractModel;

import java.awt.Point;


/**
 * @author jcidre
 *
 */
public abstract class ImportedAtomicModelUnit extends AtomicModelUnit {

	/**
	 * 
	 */
	public ImportedAtomicModelUnit() {
		super();
	}

	/**
	 * @param aPoint
	 * @param model
	 * @param name
	 * @param id
	 */
	public ImportedAtomicModelUnit(Point aPoint, AbstractModel model, String name, Object id) {
		super(aPoint, model, name, id);
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

}
