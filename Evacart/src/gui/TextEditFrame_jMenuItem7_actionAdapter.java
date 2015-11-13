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



import java.awt.event.ActionEvent;


class TextEditFrame_jMenuItem7_actionAdapter implements java.awt.event.ActionListener {
  TextEditFrame adaptee;

  TextEditFrame_jMenuItem7_actionAdapter(TextEditFrame adaptee) {
	this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
	adaptee.jMenuItem7_actionPerformed(e);
  }
}