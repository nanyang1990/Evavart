/*
 * Created on 09/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.unit;

import gui.Constants;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.model.AbstractModel;
import gui.model.model.AtomicModel;
import gui.representation.Circle;
import gui.representation.Representable;

import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author jcidre
 *
 */
public abstract class AtomicModelUnit extends AbstractModelUnit {

	public AtomicModelUnit() {
		this(null, null, null, null);
	}
	public AtomicModelUnit(Point aPoint, AbstractModel model, String name, Object id) {

		super();
		this.setLocation(aPoint);
		this.setModel(model);
		this.setName(name);
		this.setUniqueId(id);
	}


	/**
	 * @see gui.model.AbstractUnit#getDefaultImage()
	 */
	protected Representable getDefaultImage() {
		return new Circle();
	}


	/**
	 * @see gui.model.EditableCoupledModel#setOtherImageData(SelectableDrawable)
	 */
	public void prepareImage(Representable image) {
		super.prepareImage(image);
		image.setWidth(Constants.getInstance().getInt("draw.atomicModel.width", "30"));
		image.setHeight(Constants.getInstance().getInt("draw.atomicModel.height", "30"));

	}

	/* (non-Javadoc)
	 * @see gui.model.unit.AbstractUnit#loadOtherUnitDataFrom(gui.javax.io.CommentBufferedReader, gui.model.model.Model)
	 */
	protected void loadOtherUnitDataFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception {
		super.loadOtherUnitDataFrom(reader, graph);


		//Params
		int cantParams = Integer.parseInt(reader.readLine());

		for (int i = 0; i < cantParams; i++) {
			//load the param
			String param = reader.readLine();
			((AtomicModel)this.getModel()).getInitialParameters().add(param);
		}

	}

	/* (non-Javadoc)
	 * @see gui.model.unit.AbstractUnit#saveTo(gui.javax.io.CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		super.saveTo(writer);


		//Since classpath is not enough to retreive ports, I need to store de ports information here
		//save the model ports 
		Iterator params = ((AtomicModel)this.getModel()).getInitialParameters().iterator();
		writer.writeln("cant Initial params", ((Initializable)this.getModel()).getInitialParameters().size());
		while (params.hasNext()) {
			String eachParam = (String)params.next();
			writer.writeln("InitialParam", eachParam);
		}

	}
	/* (non-Javadoc)
	 * @see gui.model.unit.Initializable#setInitialParameters(java.util.Vector)
	 */
	public void setInitialParameters(Vector vector) {
		((Initializable)this.getModel()).setInitialParameters(vector);	
	}

	/* (non-Javadoc)
	 * @see gui.model.unit.Initializable#setInitialParameters(java.util.Vector)
	 */
	public Vector getInitialParameters() {
		return ((Initializable)this.getModel()).getInitialParameters();	
	}

}
