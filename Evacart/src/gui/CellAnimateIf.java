/*
 * Created on 04-abr-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

/**
 * @author Juan Ignacio
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface CellAnimateIf {
	/**
	 * @param frame
	 */
	public void setLocationRelativeTo(Component arg0);

	/**
	 * @param point
	 */
	void setLocation(Point point);

	/**
	 * @param dimension
	 */
	void setSize(Dimension dimension);

	/**
	 * @param b
	 */
	void setVisible(boolean b);

}
