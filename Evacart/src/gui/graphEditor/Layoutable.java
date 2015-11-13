package gui.graphEditor;

import gui.javax.util.Selectable;
import gui.representation.Representable;

import java.awt.Point;

/**
 * @author Administrador
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface Layoutable extends Selectable {
		
	/**
	 * get the image of this unit
	 */
	public Representable getImage();
	/**
	 * set the image of this unit
	 */
	public void setImage(Representable image);	
	/**
	 * Return the name of the Unit.
	 * Creation date: (14/08/2002 11:44:32)
	 * @return java.lang.String
	 */
	public String getName();

	/**
	 * Says where aPoint is inside this Unit
	 */
	public boolean inside(Point aPoint);

	

}
