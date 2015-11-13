/*
 * Created on 23-sep-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import gui.animate.Serie;



/**
 * @author Administrador3 jicidre@dc.uba.ar
  *
 */
public class AtomicGraphCanvas extends AtomicCanvas { 


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param string
	 */
	public AtomicGraphCanvas(AtomicAnimate animate) {
		//super(BoxLayout.Y_AXIS);
		super(animate);
		//this.setBackground(Color.YELLOW);
	}

	/* (non-Javadoc)
	 * @see gui.animate.AtomicCanvas#getAtomicSerieCanvas(gui.animate.Serie, gui.animate.AtomicAnimate)
	 */
	
	protected AtomicSerieCanvas getAtomicSerieCanvas(Serie each, AtomicAnimate parent) {
		return new AtomicSerieGraphCanvas(each,parent);
	}


}
