package gui.graphEditor;

import java.awt.Point;

/**
 * @author Juan Ignacio Cidre
 * jicidre@dc.uba.ar
 * 
 * Any unit that can by layed out in a canvas
 */
public interface OnePointLayoutable extends Layoutable {
	public abstract Point getLocation();
	public void setLocation(Point location);	
}
