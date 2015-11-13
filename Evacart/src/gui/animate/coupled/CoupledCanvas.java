/*
 * Created on 11/07/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.animate.coupled;

import gui.animate.LogElement;
import gui.image.Repository;
import gui.javax.util.DimensionUtil;
import gui.javax.util.Selectable;
import gui.model.link.AbstractLink;
import gui.model.model.CoupledModel;
import gui.model.port.AbstractPort;
import gui.model.unit.AbstractModelUnit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CoupledCanvas extends JPanel {
	//private CoupledAnimate parent;
	private CoupledModel model;
	private File log;
	private long elapse;
	private LogParser parser;
	private CoupledModelRunner runner;
	private List elementsOfCurrentChunc = new ArrayList();
	private String actualTime = "NO TIME";
	private StateController controller;

	public CoupledCanvas(
		StateController controller,
		CoupledModel model,
		File log,
		long elapse)
		throws FileNotFoundException {
		this.controller = controller;
		this.model = model;
		this.log = log;
		setElapse(elapse);

		this.setBackground(Color.WHITE);

	}

	public void start() throws FileNotFoundException {
		if (runner == null) {
			this.parser = new LogParser(this);
			this.parser.setFile(log);

			runner = new CoupledModelRunner();
			runner.start();
		}
		else {
			runner.setStoped(false);
		}

	}

	public void stop() {
		if (runner != null) {
			runner.setStoped(true);
			runner.interrupt();
			runner = null;
		}

	}
	public void pause() {
		runner.setStoped(true);

	}
	public void next() {
		runner.consumeNextChunk();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.model.getBackgroundImage() != null) {

			File imageFilename =
				Repository.getInstance().getImage(
					this.model.getBackgroundImage());
			if (imageFilename != null) {
				//System.out.println("Image draw " + imageFilename);
				java.awt.Image background =
					Toolkit.getDefaultToolkit().getImage(
						imageFilename.getPath());

				g.drawImage(
					background,
					0,
					0,
					this.getHeight(),
					this.getWidth(),
					this);
			}
		}

		paintTime(g);

		//reset all the selected links
		Iterator selecteds = this.model.getSelectedSelectables().iterator();
		while (selecteds.hasNext()) {
			Selectable eachSelected = (Selectable) selecteds.next();
			eachSelected.setSelected(false);
		}

		//select the links involved
		Iterator elements = elementsOfCurrentChunc.iterator();
		//and remember them to render
		List linksToDraw = new ArrayList();
		while (elements.hasNext()) {
			LogElement each = (LogElement) elements.next();
			//it is an out message
			//get the unit
			//System.err.println(each);
			AbstractModelUnit theUnit =
				(AbstractModelUnit) this.model.findUnitByName(each.getModel());
			if (theUnit != null) {
				//get the port of this unit
				AbstractPort thePort = theUnit.getModel().getPortbyID(each.getPort());

				//get all the links attached to this port   (only one, no?)
				Iterator links =
					this.model.findLinksAttachedTo(thePort).iterator();
				while (links.hasNext()) {
					//select the links
					AbstractLink eachLink = (AbstractLink) links.next();
					eachLink.setSelected(true);
					//remember link and data
					linksToDraw.add(eachLink);
					linksToDraw.add(each);
				}
			}

		}

		//draw the model		
		Dimension newSize = this.model.draw(g, this);

		//draw the values
		Iterator valuesToDraw = linksToDraw.iterator();
		while (valuesToDraw.hasNext()) {
			AbstractLink eachLink = (AbstractLink) valuesToDraw.next();
			LogElement theElement = (LogElement) valuesToDraw.next();
			Point startLocation = eachLink.getStartLocation();
			Point endLocation = eachLink.getEndLocation();
			int newX = startLocation.x + (endLocation.x - startLocation.x) / 3;
			int newY = startLocation.y + (endLocation.y - startLocation.y) / 3;
			g.setColor(Color.green.darker().darker());
			g.drawString(
				theElement.getPort() + " " + theElement.getValue(),
				newX,
				newY);

		}

		/*
		Iterator portsElems = logElementsByPort.keySet().iterator();
		while (portsElems.hasNext()) {
			String port = (String) portsElems.next();
			ClassicLogElement each =
				(ClassicLogElement) logElementsByPort.get(port);
			AbstractModelUnit theUnit =
				(AbstractModelUnit) this.model.findUnitByName(each.getModel());
			if (theUnit != null) {
				Port thePort = theUnit.getModel().getPortbyID(port);
		
				Iterator links =
					this.model.findLinksAttachedTo(thePort).iterator();
				while (links.hasNext()) {
					AbstractLink eachLink = (AbstractLink) links.next();
					Point startLocation = eachLink.getStartLocation();
					Point endLocation = eachLink.getEndLocation();
					int newX =
						startLocation.x + (endLocation.x - startLocation.x) / 3;
					int newY =
						startLocation.y + (endLocation.y - startLocation.y) / 3;
					g.drawString(
						each.getPort() + " " + each.getValue(),
						newX,
						newY);
				}
			}
		}
		*/
		if (!DimensionUtil.includes(this.getSize(), newSize)) {
			this.setPreferredSize(newSize);
			this.setSize(newSize);
		}

	}

	/**
	 * Dibuja la Hora del evento
	 */
	private void paintTime(Graphics g) {
		Font origFont = g.getFont();
		g.setFont(origFont.deriveFont(4));
		g.drawString(actualTime, 10, 10);
		g.setFont(origFont);

	}

	public void setElapse(long elapse) {
		this.elapse = elapse;
	}

	public long getElapse() {
		return elapse;
	}

	class CoupledModelRunner extends Thread {
		private boolean stoped;
		private LogElement lastReaded;

		public CoupledModelRunner() {
		}
		public void run() {
			boolean stillHaveData = true;
			while (stillHaveData) {
				if (!isStoped()) {
					stillHaveData = consumeNextChunk();
				}
				try {
					Thread.sleep(getElapse());
				}
				catch (InterruptedException e) {
					//e.printStackTrace();
				}
			}
		}

		/**
		 * consume all the log elements of the same time
		 *
		 */
		public boolean consumeNextChunk() {
			elementsOfCurrentChunc.clear();
			if (lastReaded == null) {
				//first element
				//initialize
				if (parser.hasMoreElements()) {
					lastReaded = (LogElement) parser.nextElement();
					consume(lastReaded);
				}
				else {
					CoupledCanvas.this.controller.end();
					return false;
				}
			}
			else {
				consume(lastReaded);
			}

			actualTime = lastReaded.getTime();
			if (parser.hasMoreElements()) {
				do {
					lastReaded = (LogElement) parser.nextElement();
					if (lastReaded.getTime().equals(actualTime)) {
						//still in the same chunk of data
						consume(lastReaded);
					}
					else {
						//other time.
						//next chunk of data
						break;
					}
				}
				while (parser.hasMoreElements());
			}
			else {
				CoupledCanvas.this.controller.end();
				return false;
			}
			paint();
			return true;
		}
		/**
		 * @param element
		 */
		private void consume(LogElement element) {
			elementsOfCurrentChunc.add(element);
		}

		public void paint() {

			repaint();
		}
		/**
		 * @return
		 */
		public boolean isStoped() {
			return stoped;
		}

		/**
		 * @param b
		 */
		public void setStoped(boolean b) {
			stoped = b;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#finalize()
		 */
		protected void finalize() throws Throwable {
			super.finalize();
		}

	}
}
