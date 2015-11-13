/*
 * Created on 24/04/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.model.port;



import java.util.Vector;

/**
 * @author jcidre
 *
 */
public interface CoupledPortContainer extends PortContainer {
	/**
	 * Return the ports that may be the end edge of a Link;
	 * @return
	 */
	public Vector getEndLinkPorts();
	/**
	 * Return the ports that may be the start edge of a Link;
	 * @return
	 */
	public Vector getStartLinkPorts();

}
