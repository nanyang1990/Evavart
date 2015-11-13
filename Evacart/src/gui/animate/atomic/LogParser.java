/*
 * Created on 01-dic-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import gui.animate.LogElement;

import java.awt.Component;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * DOCUMENT ME
 *
 */
public class LogParser extends gui.animate.ChunkLogParser {

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
		if (next.getType() != -1){
			String model = next.getModel();
			if (model != null) {
				return true;	
			}
		}
			return false; 
	}


}
