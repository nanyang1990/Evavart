package gui;/*
 * Copyright (c) 1997-2000 Inprise Corporation. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by Inprise as part
 * of an Inprise product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of Inprise.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD INPRISE, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 */


import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TextEditFrame_AboutBox extends JDialog implements ActionListener {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JPanel insetsPanel1 = new JPanel();
  JPanel insetsPanel2 = new JPanel();
  JPanel insetsPanel3 = new JPanel();
  JButton button1 = new JButton();
  JLabel imageControl1 = new JLabel();
  ImageIcon imageIcon;
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JLabel label3 = new JLabel();
  JLabel label4 = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  FlowLayout flowLayout2 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
  String product = "TextEdit Tutorial";
  String version = "Version 1.0";
  String copyright = "Copyright (c) 2000";
  String comments = "Creates a simple Java text editor application.";
  public TextEditFrame_AboutBox(Frame parent) {
	super(parent);
this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);	
	enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	try {
	  jbInit();
	}
	catch(Exception e) {
	  e.printStackTrace();
	}
	//imageControl1.setIcon(imageIcon);
	pack();
  }

  private void jbInit() throws Exception  {
	this.setTitle("About");
	setResizable(false);
	panel1.setLayout(borderLayout1);
	panel2.setLayout(borderLayout2);
	insetsPanel1.setLayout(flowLayout1);
	insetsPanel2.setLayout(flowLayout1);
	insetsPanel2.setBorder(new EmptyBorder(10, 10, 10, 10));
	gridLayout1.setRows(4);
	gridLayout1.setColumns(1);
	label1.setText(product);
	label2.setText(version);
	label3.setText(copyright);
	label4.setText(comments);
	insetsPanel3.setLayout(gridLayout1);
	insetsPanel3.setBorder(new EmptyBorder(10, 60, 10, 10));
	button1.setText("Ok");
	button1.addActionListener(this);
	insetsPanel2.add(imageControl1, null);
	panel2.add(insetsPanel2, BorderLayout.WEST);
	this.getContentPane().add(panel1, null);
	insetsPanel3.add(label1, null);
	insetsPanel3.add(label2, null);
	insetsPanel3.add(label3, null);
	insetsPanel3.add(label4, null);
	panel2.add(insetsPanel3, BorderLayout.CENTER);
	insetsPanel1.add(button1, null);
	panel1.add(insetsPanel1, BorderLayout.SOUTH);
	panel1.add(panel2, BorderLayout.NORTH);
  }

  protected void processWindowEvent(WindowEvent e) {
	if (e.getID() == WindowEvent.WINDOW_CLOSING) {
	  cancel();
	}
	super.processWindowEvent(e);
  }

  void cancel() {
	dispose();
  }

  public void actionPerformed(ActionEvent e) {
	if (e.getSource() == button1) {
	  cancel();
	}
  }
}