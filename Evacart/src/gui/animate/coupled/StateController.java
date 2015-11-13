/*
 * Created on 24-jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui.animate.coupled;

import gui.InformDialog;

import java.io.FileNotFoundException;

/**
 * @author Juan Ignacio
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StateController {
	private CoupledCanvas canvas;
	private CoupledAnimate animate;
	
	public synchronized void start(){
		//System.err.print("start");
		try {
			getCanvas().start();
		}
		catch (FileNotFoundException ex) {
			(new InformDialog(ex.toString(),ex)).setVisible(true);
		}

		getAnimate().start.setEnabled(false);
		getAnimate().stop.setEnabled(true);
		getAnimate().next.setEnabled(false);
		getAnimate().pause.setEnabled(true);

	}
	public synchronized void stop(){
		//System.err.print("stop");
		getCanvas().stop();
		getAnimate().start.setEnabled(true);
		getAnimate().stop.setEnabled(false);
		getAnimate().next.setEnabled(false);
		getAnimate().pause.setEnabled(false);
	}
	public void next(){
		getCanvas().next();
		getAnimate().start.setEnabled(true);
		getAnimate().stop.setEnabled(true);
		getAnimate().next.setEnabled(true);
		getAnimate().pause.setEnabled(false);
		
	}
	public void pause(){
		
		getCanvas().pause();
		getAnimate().start.setEnabled(true);
		getAnimate().stop.setEnabled(true);
		getAnimate().next.setEnabled(true);
		getAnimate().pause.setEnabled(false);
	}
	public synchronized void end(){
		//System.err.println("end");
		stop();
	}

	/**
	 * @return
	 */
	public CoupledAnimate getAnimate() {
		return animate;
	}

	/**
	 * @return
	 */
	public CoupledCanvas getCanvas() {
		return canvas;
	}

	/**
	 * @param animate
	 */
	public void setAnimate(CoupledAnimate animate) {
		this.animate = animate;
	}

	/**
	 * @param canvas
	 */
	public void setCanvas(CoupledCanvas canvas) {
		this.canvas = canvas;
	}

}
