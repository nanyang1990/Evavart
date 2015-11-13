/*
 * Created on 14-nov-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import gui.animate.Serie;


/**
 * @author Administrador3 jicidre@dc.uba.ar 
 *
 */
public class AtomicYAxisCanvas extends AtomicCanvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param animate
	 */
	public AtomicYAxisCanvas(AtomicAnimate animate) {
		super(animate);
		//this.setBackground(Color.BLUE);
	
	}

	/* (non-Javadoc)
	 * @see gui.animate.AtomicCanvas#getAtomicSerieCanvas(gui.animate.Serie, gui.animate.AtomicAnimate)
	 */
	protected AtomicSerieCanvas getAtomicSerieCanvas(Serie each, AtomicAnimate parent) {
		return new AtomicSerieYAxisCanvas(each,parent);
	}

}
