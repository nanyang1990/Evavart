package gui.graphEditor;

import java.awt.Point;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public interface TwoPointsLayoutable extends Layoutable {
	public Point getStartLocation();
	public Point getEndLocation();

}
