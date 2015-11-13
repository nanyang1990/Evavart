/*
 * Created on 14-nov-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import gui.animate.Serie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JPanel;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * Esta es la clase que posee las multiples series y que sabe apilarlas
 * una sobre otra 
 *
 */
public abstract class AtomicCanvas extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
	private Collection<?> series;
	private AtomicAnimate parent;
	
	/**
	 * @param string
	 */
	public AtomicCanvas(AtomicAnimate animate) {
		//super(BoxLayout.Y_AXIS);
		super();
		//this.setOpaque(false);
		this.parent = animate;
		this.setBackground(Color.WHITE);
	}

	/**
		 * @param string
		 */
	protected void toogleVisibility(String port) {
		if(series != null){
			for (Iterator<?> iter = series.iterator(); iter.hasNext();) {
				Serie each = (Serie)iter.next();
				if(each.getPort().equals(port)){
					each.setVisible(!each.getVisible());
				}
			}
		}
		remake();
		parent.doLayoutAll();		
	}

	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		//doLayout();
		revalidate();
	
	}


	/**
		 * @param collection
		 */
	public void setSeries(Collection<?> collection) {
	
		this.series = collection;
		remake();
	}

	public void remake() {
		this.removeAll();
		if(series != null){
			
			//BoxLayout lay = new BoxLayout(this,BoxLayout.Y_AXIS);
			GridBagLayout lay = new GridBagLayout();
			this.setLayout(lay);
			//this.setLayout(new GridLayout(series.size(),1));
			int count = 0;			
			Iterator<?> iter = series.iterator();
			while (iter.hasNext()) {
				Serie each = (Serie)iter.next();
				if(each.getVisible()){
					AtomicSerieCanvas eachCanvas = getAtomicSerieCanvas(each,parent);
					GridBagConstraints cons  = new GridBagConstraints();
					cons.fill = GridBagConstraints.NONE;
					cons.gridx = 0;
					cons.gridy = count++;
					cons.anchor = GridBagConstraints.SOUTHWEST;
					cons.weightx = 1;
					cons.weighty = 1;
					lay.addLayoutComponent(eachCanvas,cons);
					
					this.add(eachCanvas);
				}
			}
		}
	}

	/**
	 * @param each
	 * @param parent
	 * @return
	 */
	protected abstract AtomicSerieCanvas getAtomicSerieCanvas(Serie each, AtomicAnimate parent );

}
