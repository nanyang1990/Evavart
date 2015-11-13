/*
 * Created on 22-oct-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * Es el panel superior del AtomicAnimate. Tiene los botones de control
 *
 */
public class TopPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton zoomXIn;
	private JButton zoomXOut;
	private JButton drillDown;
	private JButton drillUp;
	
	private JCheckBox valuesOnOff;
	private JButton next;
	private JButton previous;
	
	private final AtomicAnimate parent;
	private JComboBox modelosCombo;

	/**
	 * patterns to display the time values
	 */		
	private JComboBox timePatterns;

	/**
	 * 
	 */
	public TopPanel(AtomicAnimate atomic) {
		super();
		this.parent = atomic;
		

		
		modelosCombo = new JComboBox();
		modelosCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				try {
					if(arg0.getStateChange() == ItemEvent.SELECTED){
						parent.modelSelection((String)arg0.getItem());
					}
					
				}
				catch (NumberFormatException e1) {
					e1.printStackTrace();
				}
				catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//init the patterns to display the time values
		timePatterns = new JComboBox();
		timePatterns.addItem("HH:mm:ss:SSS");
		timePatterns.addItem("HH:mm:ss");
		timePatterns.addItem("HH:mm");
		timePatterns.addItem("HH");
		timePatterns.addItem("mm:ss:SSS");
		timePatterns.addItem("mm:ss");
		timePatterns.addItem("ss:SSS");
		timePatterns.addItem("ss");
		timePatterns.addItem("mm");
		timePatterns.addItem("SSS");
		timePatterns.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent arg0) {
			if(arg0.getStateChange() == ItemEvent.SELECTED){
					parent.setTimePattern((String)arg0.getItem());
			}
		}
	});
		
		
		zoomXIn = new JButton();
		ImageIcon zoomXInIcon = getIcon("lupamas.gif");
		zoomXIn.setIcon(zoomXInIcon);
		
		zoomXOut = new JButton();
		ImageIcon zoomXOutIcon = getIcon("lupamenos.gif");
		zoomXOut.setIcon(zoomXOutIcon);

		drillUp = new JButton();
		ImageIcon drillUpImage = getIcon("drillmas.gif");
		drillUp.setIcon(drillUpImage);
		
		drillDown = new JButton();
		ImageIcon drillDownImage = getIcon("drillmenos.gif");
		drillDown.setIcon(drillDownImage);
		
		valuesOnOff = new JCheckBox("Values");
		valuesOnOff.setSelected(true);
		previous = new JButton();
		ImageIcon prev = getIcon("previous.gif");
		previous.setIcon(prev);
		
		next = new JButton();
		ImageIcon nex = getIcon("next.gif");
		next.setIcon(nex);
		
		zoomXIn.addActionListener(new ActionListener() {
			/* (non-Javadoc)
							 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
							 */
			public void actionPerformed(ActionEvent e) {
			    try{
			        parent.getSeriesPropertiesManager().zoomXIn();
			    }
			    catch(Exception ex){
			        Toolkit.getDefaultToolkit().beep();
			        ex.printStackTrace();
			    }

			}
		});
		zoomXOut.addActionListener(new ActionListener() {
			/* (non-Javadoc)
							 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
							 */
			public void actionPerformed(ActionEvent e) {
			    try{
			        parent.getSeriesPropertiesManager().zoomXOut();
			    }
			    catch(Exception ex){
			        Toolkit.getDefaultToolkit().beep();
			    }


			}
		});
		
		drillUp.addActionListener(new ActionListener() {
			/* (non-Javadoc)
							 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
							 */
			public void actionPerformed(ActionEvent e) {
			    try{
			        parent.drillUp();
			    }
			    catch(Exception ex){
			        Toolkit.getDefaultToolkit().beep();
			        ex.printStackTrace();
			    }

			}
		});
		drillDown.addActionListener(new ActionListener() {
			/* (non-Javadoc)
							 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
							 */
			public void actionPerformed(ActionEvent e) {
			    try{
			        parent.drillDwon();
			    }
			    catch(Exception ex){
			        Toolkit.getDefaultToolkit().beep();
			    }


			}
		});
		
		valuesOnOff.addActionListener(new ActionListener() {
			/* (non-Javadoc)
							 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
							 */
			public void actionPerformed(ActionEvent e) {
			    parent.getSeriesPropertiesManager().valuesOnOff();
			}
		});

		JButton refresh = new JButton("refresh");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.doLayoutAll();
			}
		});
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.doLayoutAll();
			}
		});

		
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					nextChunkOfData();
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					previousChunkOfData();
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//JPanel firstPanel = new JPanel(new GridLayout(1,1,5,5));
		JPanel firstPanel = new JPanel(new FlowLayout());
		firstPanel.add(modelosCombo);
		//firstPanel.add(refresh);
		
		JPanel zoomPanel = new JPanel(new GridLayout(1,2,5,5));
		zoomPanel.add(zoomXIn);
		zoomPanel.add(zoomXOut);
		
		JPanel drillPanel = new JPanel(new GridLayout(1,2,5,5));
		drillPanel.add(drillDown);
		drillPanel.add(drillUp);
		
		
		JPanel secondPanel = new JPanel(new GridLayout(1,2,5,5));
		secondPanel.add(zoomPanel);
		secondPanel.add(drillPanel);

		JPanel thirdPanel = new JPanel(new GridLayout(1,2,5,5));
		thirdPanel.add(previous);
		thirdPanel.add(next);
		

		JPanel chunkPanel = new JPanel(new FlowLayout());
		chunkPanel.add(secondPanel);
		chunkPanel.add(thirdPanel);
		
		//JPanel fifthPanel = new JPanel(new GridLayout(1,2,5,5));
		JPanel fifthPanel = new JPanel(new FlowLayout());
		fifthPanel.add(valuesOnOff);
		fifthPanel.add(timePatterns);
		

		
		//this.setLayout(new FlowLayout(FlowLayout.CENTER,35,1));
		this.setLayout(new BorderLayout(5,5));		
		this.add(firstPanel,BorderLayout.WEST);
		this.add(chunkPanel,BorderLayout.CENTER);
		//this.add(thirdPanel);
		//this.add(fourthPanel);
		this.add(fifthPanel,BorderLayout.EAST);
		
	}

	public String getSelectedModel(){
		return (String) modelosCombo.getSelectedItem();
	}
	/**
	 * @param set
	 */
	public void setModelos(Collection<?> set) {
	    String selectedModel = (String)modelosCombo.getSelectedItem();
		modelosCombo.removeAllItems();
		Iterator<?> models = set.iterator();
		while (models.hasNext()) {
			String each = (String)models.next();
			modelosCombo.addItem(each);
		}
		if(selectedModel != null){
		    modelosCombo.setSelectedItem(selectedModel);
		}
		else if(modelosCombo.getItemCount() > 0){
		    modelosCombo.setSelectedItem(modelosCombo.getItemAt(0));
		}
		
		modelosCombo.validate();
	}




	/**
	 * @throws Exception
	 * 
	 */

	protected void previousChunkOfData() throws Exception {
		parent.previousChunkOfData();
	}		

	/**
	 * @throws Exception
	 * 
	 */
	protected void nextChunkOfData() throws Exception {
		parent.nextChunkOfData();
	}
	

	/**
	 * 
	 */
	public void maxReached() {
		this.next.setEnabled(false);
		this.previous.setEnabled(true);
	}
	public void minReached() {
	    this.next.setEnabled(true);
		this.previous.setEnabled(false);
	}

	/**
	 * 
	 */
	public void inMiddleOfArchive() {
		this.previous.setEnabled(true);
		this.next.setEnabled(true);
		
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
