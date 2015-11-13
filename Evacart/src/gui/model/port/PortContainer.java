package gui.model.port;

import java.util.Vector;

/**
 * @author Administrador
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface PortContainer {
	public Vector getPorts();
	public void setPorts(Vector ports);	
	public void addPort(AbstractPort aPort);
	public Vector getInPorts();	
	public Vector getOutPorts();
}
