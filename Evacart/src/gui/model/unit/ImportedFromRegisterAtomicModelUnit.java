/*
 * Created on 12/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.unit;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.model.AbstractModel;
import gui.model.model.ImportedAtomicModel;
import gui.model.model.ImportedFromRegisterAtomicModel;
import gui.model.port.AbstractPort;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ImportedFromRegisterAtomicModelUnit extends ImportedAtomicModelUnit  implements Initializable  {
	/**
	 * 
	 */
	public ImportedFromRegisterAtomicModelUnit() {
		super();
		
	}

	/**
	 * @param aPoint
	 * @param model
	 * @param name
	 * @param id
	 */
	public ImportedFromRegisterAtomicModelUnit(Point aPoint, AbstractModel model, String name, Object id) {
		super(aPoint, model, name, id);
		
	}

	/* (non-Javadoc)
	 * @see gui.model.AbstractModelUnit#createNewModel(java.io.File)
	 */
	protected AbstractModel createNewModel(File path, File name) throws Exception {
		ImportedAtomicModel model = new ImportedFromRegisterAtomicModel();
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
			"ClassPath: No Classpath since imported from register.cpp\n" +
			"ExportClassPath: No Export Classpath since imported from register.cpp\n";		
	}


	/* (non-Javadoc)
	 * @see gui.model.unit.AbstractUnit#loadOtherUnitDataFrom(gui.javax.io.CommentBufferedReader, gui.model.model.Model)
	 */
	protected void loadOtherUnitDataFrom(
		CommentBufferedReader reader,
		AbstractModel graph)
		throws Exception {
		super.loadOtherUnitDataFrom(reader, graph);
		
		String modelImportedFromCPP = reader.readLine();
		this.getModel().setModelName(modelImportedFromCPP);

		
		//Ports
		int cantPorts = Integer.parseInt(reader.readLine());

		for (int i = 0; i < cantPorts; i++) {
			//load the Unit
			AbstractPort aPort = AbstractPort.loadFrom(reader, this.getModel());
			this.addPort(aPort);
		}

	}

	/* (non-Javadoc)
	 * @see gui.model.unit.AbstractUnit#saveTo(gui.javax.io.CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		super.saveTo(writer);
		
		//I need to store the Model name of the register.cppp
		writer.writeln("Model imported form register",this.getModel().getModelName());
		
		//Since classpath is not enough to retreive ports, I need to store de ports information here
		//save the model ports 
		Iterator ports = this.getPorts().iterator();
		writer.writeln("cant ports", this.getPorts().size());
		while (ports.hasNext()) {
			AbstractPort eachPort = (AbstractPort) ports.next();
			eachPort.saveTo(writer);
		}
		
	}

}
