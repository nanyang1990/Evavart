package gui.model.unit;
import gui.model.model.AbstractModel;
import gui.model.model.EditableCoupledModel;

import java.awt.Point;
import java.io.File;

public class EditableCoupledModelUnit extends CoupledModelUnit implements Editable{
	

	/**
	 * 
	 */
	public EditableCoupledModelUnit() {
		super();
	}

	/**
	 * @param aPoint
	 * @param model
	 * @param name
	 * @param id
	 */
	public EditableCoupledModelUnit(Point aPoint, AbstractModel model, String name, Object id) {
		super(aPoint, model, name, id);
	}

	/* (non-Javadoc)
	 * @see gui.model.AbstractModelUnit#createNewModel()
	 */
	protected AbstractModel createNewModel(File path, File name) throws Exception{
		EditableCoupledModel retr = new EditableCoupledModel();
		retr.setModelFileName(name);
		retr.setActualPath(path);
		retr.reload();
		return retr;
		
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