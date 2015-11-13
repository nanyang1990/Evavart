/*
 * Created on 10-sep-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import gui.animate.Serie;
import gui.javax.swing.JNumberField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Administrador3 jicidre@dc.uba.ar
  *
 */
public class SeriesSelectorPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<?> series;
	private AtomicAnimate atomicAnimate;
	private JPanel checks;
	private String actualModel;
	
	
	public SeriesSelectorPanel(){
		super();
		this.setChecks(new JPanel());
		GridLayout lay = new GridLayout();
		this.getChecks().setLayout(lay);
		this.add(this.getChecks(), BorderLayout.CENTER);
		
	}
	/**
	 * @return
	 */
	public Collection<?> getSeries() {
		return series;
	}

	/**
	 * @param collection
	 */
	public void setSeries(Collection<?> collection, final String actualModel) {
		if(collection != null){	
			series = collection;
			this.actualModel = actualModel;
			this.getChecks().removeAll();
			
			
			Iterator<?> seriesIter = series.iterator();
			((GridLayout)this.getChecks().getLayout()).setRows(collection.size());
			((GridLayout)this.getChecks().getLayout()).setColumns(1);
			while(seriesIter.hasNext()){
				final Serie each = (Serie)seriesIter.next();
				JCheckBox eachCheck = new JCheckBox(each.getPort());
				
				eachCheck.setActionCommand(each.getPort());
				eachCheck.setName("");
				eachCheck.setSelected(each.getVisible());
				eachCheck.setForeground(each.getColor());
				eachCheck.addActionListener(new ActionListener(){
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					public void actionPerformed(ActionEvent e) {
					    atomicAnimate.getSeriesPropertiesManager().toogleVisibility(actualModel, e.getActionCommand());
					}
				});

				JButton zoomIn = new JButton("");
				ImageIcon zoomXInIcon = getIcon("lupamas.gif");
				zoomIn.setIcon(zoomXInIcon);
				
				JButton zoomOut = new JButton("");
				ImageIcon zoomXOutIcon = getIcon("lupamenos.gif");
				zoomOut.setIcon(zoomXOutIcon);
				
				zoomIn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					    try{
					        atomicAnimate.getSeriesPropertiesManager().zoomYIn(actualModel, each.getPort());
					    }
					    catch(Exception ex){
					        Toolkit.getDefaultToolkit().beep();
					    }
					}
				});
				zoomOut.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					    try{
					        atomicAnimate.getSeriesPropertiesManager().zoomYOut(actualModel, each.getPort());
					    }
					    catch(Exception ex){
					        Toolkit.getDefaultToolkit().beep();
					    }

					}
				});

				//scale factor
				JLabel scale = new JLabel("Scale");
				final JNumberField scaleFrom = new JNumberField();
				scaleFrom.setColumns(6);
				FocusListener scaleFromFocusListener = new FocusListener() {
					Serie theSerie = each;
					
					public void focusGained(FocusEvent e) {
					}
					public void focusLost(FocusEvent e) {
					    atomicAnimate.getSeriesPropertiesManager().scaleFromChanged(theSerie.getModel(), theSerie.getPort(), ((JNumberField)e.getComponent()).getNumber() );
					}
					
				};
				scaleFrom.addFocusListener(scaleFromFocusListener);
				KeyListener scaleFromKeyListener = new KeyListener() {
				    Serie theSerie = each;
                    public void keyPressed(KeyEvent e) {
                    }
                    public void keyReleased(KeyEvent e) {
                    }
                    public void keyTyped(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER){
                            atomicAnimate.getSeriesPropertiesManager().scaleFromChanged(theSerie.getModel(), theSerie.getPort(), ((JNumberField)e.getComponent()).getNumber() );     
                        }
                    }
                };

                scaleFrom.addKeyListener(scaleFromKeyListener);

                scaleFrom.setNumber(each.getMinRenderingValue());


				final JNumberField scaleTo = new JNumberField();
				scaleTo.setColumns(6);
				scaleTo.setNumber(each.getMaxRenderingValue());
				
				FocusListener scaleToFocusListener = new FocusListener() {
					Serie theSerie = each;
					
					public void focusGained(FocusEvent e) {
					}
					public void focusLost(FocusEvent e) {
					    atomicAnimate.getSeriesPropertiesManager().scaleToChanged(theSerie.getModel(), theSerie.getPort(), ((JNumberField)e.getComponent()).getNumber() );
					}
					
				};
				scaleTo.addFocusListener(scaleToFocusListener);
				KeyListener scaleToKeyListener = new KeyListener() {
				    Serie theSerie = each;
                    public void keyPressed(KeyEvent e) {
                    }
                    public void keyReleased(KeyEvent e) {
                    }
                    public void keyTyped(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER){
                            atomicAnimate.getSeriesPropertiesManager().scaleToChanged(theSerie.getModel(), theSerie.getPort(), ((JNumberField)e.getComponent()).getNumber() );     
                        }
                    }
                };

                scaleTo.addKeyListener(scaleToKeyListener);
				
                JButton rescale = new JButton("rescale");
                ActionListener rescaleLst = new ActionListener() {
                    Serie theSerie = each;
                    public void actionPerformed(ActionEvent e) {
                        atomicAnimate.getSeriesPropertiesManager().rescale(theSerie.getModel(),theSerie.getPort());
                        scaleTo.setNumber(each.getMaxRenderingValue());
                        scaleFrom.setNumber(each.getMinRenderingValue());

                        
                    }
                };
                rescale.addActionListener(rescaleLst);
				
				

				//serie and scale panel
				JPanel integrator = new JPanel();
				//integrator.setLayout(new GridLayout(2,1,5,5));
				integrator.setLayout(new GridBagLayout());


				GridBagConstraints a = new GridBagConstraints();
				a.weightx = 1;
				a.gridwidth = 2;
				a.gridx = 0;
				a.gridy = 0;
				integrator.add(eachCheck,a);

				GridBagConstraints b = new GridBagConstraints();
				b.weightx = 0.5;
				b.gridx = 2;
				b.gridy = 0;
				integrator.add(zoomIn,b);

				GridBagConstraints c = new GridBagConstraints();
				c.weightx = 0.5;
				c.gridx = 3;
				c.gridy = 0;
				integrator.add(zoomOut,c);
				
				GridBagConstraints d = new GridBagConstraints();
				d.gridx = 0;
				d.gridy = 1;
				d.gridwidth = 2;
				integrator.add(scale,d);
				
				GridBagConstraints f = new GridBagConstraints();
				f.gridx = 2;
				f.gridy = 1;
				integrator.add(scaleFrom,f);
				
				GridBagConstraints g = new GridBagConstraints();
				g.gridx = 3;
				g.gridy = 1;
				integrator.add(scaleTo,g);
				
				GridBagConstraints h = new GridBagConstraints();
				h.gridx = 0;
				h.gridy = 2;
				h.fill = 4;
				integrator.add(rescale,h);
							
				integrator.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				this.getChecks().add(integrator);
			}
			this.getChecks().revalidate();
			this.getChecks().doLayout();
		}
			


}
	/**
	 * @return
	 */
	public AtomicAnimate getAtomicAnimate() {
		return atomicAnimate;
	}

	/**
	 * @param canvas
	 */
	public void setAtomicAnimate(AtomicAnimate canvas) {
		atomicAnimate = canvas;
	}

	/**
	 * @return
	 */
	public JPanel getChecks() {
		return checks;
	}

	/**
	 * @param panel
	 */
	public void setChecks(JPanel panel) {
		checks = panel;
	}

	/**
	 * @param string
	 * @return
	 */
	private ImageIcon getIcon(String imageName) {
		URL imageURL = null;
		imageURL = getClass().getResource(imageName);
		return new ImageIcon(imageURL);
	}

}