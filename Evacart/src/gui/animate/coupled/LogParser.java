/*
 * Created on 01-dic-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.coupled;

import gui.animate.LogElement;
import gui.animate.TypeToken;

import java.awt.Component;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * DOCUMENT ME
 *
 */
public class LogParser extends gui.animate.LogParser {

	/**
	 * @param parent
	 */
	public LogParser(Component parent) {
		super(parent);
	}

	/* (non-Javadoc)
	 * @see gui.animate.LogParser#isNextOK(gui.animate.LogElement)
	 */
	public boolean isNextOK(LogElement next) {
		boolean msgy = next.getType() == TypeToken.MENSAJE_Y;
		//boolean msgx = next.getType() == TypeToken.MENSAJE_X;
		boolean toTop = "top".equals(next.getTo());
		return (msgy  && toTop );
	}

}
