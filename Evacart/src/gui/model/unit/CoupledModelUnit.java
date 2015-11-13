/*
 * Created on 09/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.unit;

import gui.Constants;
import gui.model.model.AbstractModel;
import gui.representation.Representable;
import gui.representation.Square;

import java.awt.Point;

/**
 * @author jcidre
 *
 */
public abstract class CoupledModelUnit extends AbstractModelUnit {

	public CoupledModelUnit() {
		this(null, null, null,null);
	}
	public CoupledModelUnit(Point aPoint,AbstractModel model, String name, Object id) {
		super();
		this.setLocation(aPoint);
		this.setModel(model);
		this.setName(name);
		this.setUniqueId(id);		
		
	}


	public String toString() {
		return (getName());
	}





	protected Representable getDefaultImage(){
		return new Square();
	}
	
	/**
	 * @see gui.model.EditableCoupledModel#setOtherImageData(SelectableDrawable)
	 */
	public void prepareImage(Representable image) {
		super.prepareImage(image);
		image.setWidth(Constants.getInstance().getInt("draw.coupledModel.width","30"));
		image.setHeight(Constants.getInstance().getInt("draw.coupledModel.height","30"));			
	}



}
