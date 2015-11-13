/*
 * Created on 12/06/2003
 * Represents an Atomic Model imported from a .cdd file
 */
package gui.model.model;

import gui.model.port.AbstractPort;


/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ImportedFromCDDAtomicModel extends ImportedAtomicModel {


	/* (non-Javadoc)
	 * @see gui.model.Model#deletePort(gui.model.Port)
	 */
	public void deletePort(AbstractPort aPort) {
		throw new RuntimeException("this method should not be used");
	}


}
