/*
 * Created on 12/06/2003
 *	Represents an atomic model imported from the register.cpp
 */
package gui.model.model;

import gui.MainFrame;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.io.IOException;
import java.util.Vector;



/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ImportedFromRegisterAtomicModel extends ImportedAtomicModel implements Cloneable{

	/* (non-Javadoc)
	 * @see gui.model.Model#loadFrom(gui.javax.io.CommentBufferedReader, gui.MainFrame)
	 */
	public void loadFrom(CommentBufferedReader reader, MainFrame mainFrame)
		throws Exception {
			/*
		super.loadFrom(reader, mainFrame);

		//Ports
		int cantPorts = Integer.parseInt(reader.readLine());
		for (int i = 0; i < cantPorts; i++) {
			//load the Port
			Port aPort = Port.loadFrom(reader, this);
			this.addPort(aPort);
		}
		*/
	}

	/* (non-Javadoc)
	 * @see gui.model.Model#saveTo(gui.javax.io.CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		/*
		super.saveTo(writer);
		//ports
		writer.writeln("Cant Ports", getPorts().size());
		Iterator tmpPorts = getPorts().iterator();
		while (tmpPorts.hasNext()) {
			//save the Unit
			Port tmpPort = (Port) tmpPorts.next();
			tmpPort.saveTo(writer);
		}
		*/
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException {
		ImportedFromRegisterAtomicModel retr = (ImportedFromRegisterAtomicModel) super.clone();
		retr.setLinks((Vector)retr.getLinks().clone());
		retr.setModels((Vector) retr.getModels().clone());
		retr.setPorts((Vector)retr.getPorts().clone());
		retr.setVariables((Vector)retr.getVariables().clone());
		retr.setInitialParameters((Vector)retr.getInitialParameters().clone());
		
		return retr;
		
	}
	

}
