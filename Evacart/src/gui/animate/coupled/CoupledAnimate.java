/*
 * Created on 11/07/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.animate.coupled;

import gui.CoupledAnimateIf;
import gui.InformDialog;
import gui.model.model.CoupledModel;
import gui.model.model.EditableCoupledModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CoupledAnimate extends JFrame  implements CoupledAnimateIf{
	private CoupledModel model;
	private File log;
	private long elapse;
	private CoupledCanvas coupledCanvas;
	private StateController controller;

	public CoupledAnimate(){
	} 
	

	
	public void init(File model, File log, long elapse) throws Exception {
		this.controller = new StateController();
		this.controller.setAnimate(this);
		this.model = new EditableCoupledModel();
		this.model.loadFrom(model.getParentFile(), new File(model.getName()));
		this.log = log;
		this.elapse = elapse;
		this.jbInit();		
	}

	private JLabel currentTime = new JLabel();
	protected final JButton start = new JButton("Start");
	protected final JButton stop = new JButton("Stop");
	protected final JButton pause = new JButton("Pause");
	protected final JButton next = new JButton("Next");
	private final JLabel elapseLabel = new JLabel("Elapse");
	private final JTextField elapseTF = new JTextField();
	private final JButton setElapse = new JButton("Set");
	
	private void jbInit() {
		setTitle("Coupled Animate");
		setBackground(Color.white);

		start.addActionListener(new ActionListener() {
			public  void actionPerformed(ActionEvent e) {
				controller.start();
			}
		});

		stop.addActionListener(new ActionListener() {
			public  void actionPerformed(ActionEvent e) {
				controller.stop();
			}
		});

		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.pause();
			}
		});

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.next();
			}
		});

		elapseTF.setText(""+elapse);
		elapseTF.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
			}

			public void focusLost(FocusEvent arg0) {
				//ensure is a number
				String data = elapseTF.getText();
				try{
					elapse = Long.parseLong(data);
					
				}
				catch(Exception tt){
					//it is not a number
					elapseTF.setText(""+elapse);
				}
			}
		});
		
		setElapse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getCoupledCanvas().setElapse(elapse);
			}
		});
		
		JPanel topPanel = new JPanel();

		topPanel.setLayout(new FlowLayout());
		topPanel.add(currentTime);
		topPanel.add(start);
		topPanel.add(stop);
		topPanel.add(pause);
		topPanel.add(next);
		topPanel.add(elapseLabel);
		topPanel.add(elapseTF);
		topPanel.add(setElapse);
		next.setEnabled(false);
		stop.setEnabled(false);
		start.setEnabled(true);
		pause.setEnabled(false);

		JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.getViewport().setLayout(new BorderLayout());
		scroll.getViewport().add(getCoupledCanvas(), BorderLayout.CENTER);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
		this.getContentPane().add(scroll, BorderLayout.CENTER);

	}


	private CoupledCanvas getCoupledCanvas() {
		if (coupledCanvas == null) {
			try {
				coupledCanvas = new CoupledCanvas(this.controller,this.model, log, elapse);
				this.controller.setCanvas(coupledCanvas);
			}
			catch (FileNotFoundException e) {
				(new InformDialog(e.toString(),e)).setVisible(true);
			}
		}
		return coupledCanvas;
	}


}
