package gui;

import gui.javax.file.JFileChooser;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;




public class TextEditFrame extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//IntlSwingSupport intlSwingSupport1 = new IntlSwingSupport();
  JPanel contentPane;
  JMenuBar menuBar1 = new JMenuBar();
  JMenu menuFile = new JMenu();
  JMenuItem menuFileExit = new JMenuItem();
  JMenu menuHelp = new JMenu();
  JMenuItem menuHelpAbout = new JMenuItem();
  JToolBar toolBar = new JToolBar();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  ImageIcon image1;
  ImageIcon image2;
  ImageIcon image3;
  JLabel statusBar = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane textAreaScrollPanel = new JScrollPane();
  JTextArea textArea = new JTextArea();
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenuItem jMenuItem2 = new JMenuItem();
  JMenuItem jMenuItem3 = new JMenuItem();
  JMenuItem jMenuItem4 = new JMenuItem();
  //FontChooser fontChooser1 = new FontChooser();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem5 = new JMenuItem();
  JMenuItem jMenuItem6 = new JMenuItem();
  JMenuItem jMenuItem7 = new JMenuItem();
  JFileChooser jFileChooser1 = new JFileChooser();
  String currFileName = null;  // Full path with filename. null means new/untitled.
  boolean dirty = false;
  Document document1;
  //DBTextDataBinder dBTextDataBinder1 = new DBTextDataBinder();  // True means modified text.

  //Construct the frame
  public TextEditFrame() {
	enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	try {
	  jbInit();
	  updateCaption();
	}
	catch(Exception e) {
	  e.printStackTrace();
	}
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
  
  //Component initialization
  private void jbInit() throws Exception  {
	image1 = getIcon("openfile.gif");
	image2 = getIcon("closefile.gif");
	image3 = getIcon("help.gif");
	contentPane = (JPanel) this.getContentPane();
	document1 = textArea.getDocument();
	contentPane.setLayout(borderLayout1);
	this.setSize(new Dimension(400, 300));
	this.setTitle("Text Editor");
	statusBar.setText(" ");
	menuFile.setText("File");
	menuFileExit.setText("Exit");
	menuFileExit.addActionListener(new TextEditFrame_menuFileExit_ActionAdapter(this));
	menuHelp.setText("Help");
	menuHelpAbout.setText("About");
	menuHelpAbout.addActionListener(new TextEditFrame_menuHelpAbout_ActionAdapter(this));
	jButton1.setIcon(image1);
	jButton1.addActionListener(new TextEditFrame_jButton1_actionAdapter(this));
	jButton1.setToolTipText("Open File");
	jButton2.setIcon(image2);
	jButton2.addActionListener(new TextEditFrame_jButton2_actionAdapter(this));
	jButton2.setToolTipText("Close File");
	jButton3.setIcon(image3);
	jButton3.addActionListener(new TextEditFrame_jButton3_actionAdapter(this));
	jButton3.setToolTipText("About");
	textArea.setLineWrap(true);
	textArea.setWrapStyleWord(true);
	textArea.setBackground(Color.white);
	jMenuItem1.setText("New");
	jMenuItem1.addActionListener(new TextEditFrame_jMenuItem1_actionAdapter(this));
	jMenuItem2.setText("Open");
	jMenuItem2.addActionListener(new TextEditFrame_jMenuItem2_actionAdapter(this));
	jMenuItem3.setText("Save");
	jMenuItem3.addActionListener(new TextEditFrame_jMenuItem3_actionAdapter(this));
	jMenuItem4.setText("Save As");
	jMenuItem4.addActionListener(new TextEditFrame_jMenuItem4_actionAdapter(this));
	//fontChooser1.setFrame(this);
	//fontChooser1.setTitle("Font");
	jMenu1.setText("Edit");
	jMenuItem5.setText("Font");
	jMenuItem5.addActionListener(new TextEditFrame_jMenuItem5_actionAdapter(this));
	jMenuItem6.setText("Foreground Color");
	jMenuItem6.addActionListener(new TextEditFrame_jMenuItem6_actionAdapter(this));
	jMenuItem7.setText("Background Color");
	jMenuItem7.addActionListener(new TextEditFrame_jMenuItem7_actionAdapter(this));
	document1.addDocumentListener(new TextEditFrame_document1_documentAdapter(this));
	//dBTextDataBinder1.setJTextComponent(jTextArea1);
	//Turn off right-click file Open... menu item.
	//dBTextDataBinder1.setEnableFileLoading(false);
	//Turn off right-click file Save... menu item.
	//dBTextDataBinder1.setEnableFileSaving(false);
	toolBar.add(jButton1);
	toolBar.add(jButton2);
	toolBar.add(jButton3);
	menuFile.add(jMenuItem1);
	menuFile.add(jMenuItem2);
	menuFile.add(jMenuItem3);
	menuFile.add(jMenuItem4);
	menuFile.addSeparator();
	menuFile.add(menuFileExit);
	menuHelp.add(menuHelpAbout);
	menuBar1.add(menuFile);
	menuBar1.add(jMenu1);
	menuBar1.add(menuHelp);
	this.setJMenuBar(menuBar1);
	contentPane.add(toolBar, BorderLayout.NORTH);
	contentPane.add(statusBar, BorderLayout.SOUTH);
	contentPane.add(textAreaScrollPanel, BorderLayout.CENTER);
	textAreaScrollPanel.getViewport().add(textArea, null);
	jMenu1.add(jMenuItem5);
	jMenu1.add(jMenuItem6);
	jMenu1.add(jMenuItem7);

  }

  // Display the About box.
  void helpAbout() {
	TextEditFrame_AboutBox dlg = new TextEditFrame_AboutBox(this);
	Dimension dlgSize = dlg.getPreferredSize();
	Dimension frmSize = getSize();
	Point loc = getLocation();
	dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
	dlg.setModal(false);
	dlg.show();
  }

  // Handle the File|Open menu or button, invoking okToAbandon and openFile
  // as needed.
  void fileOpen() {
	if (!okToAbandon()) {
	  return;
	}
	// Use the OPEN version of the dialog, test return for Approve/Cancel
	if (JFileChooser.APPROVE_OPTION == jFileChooser1.showOpenDialog(this)) {
	  // Call openFile to attempt to load the text from file into TextArea
	  openFile(jFileChooser1.getSelectedFile().getPath());
	}
	textArea.requestFocus();
	
	textArea.setCaretPosition(0);
	int min = textAreaScrollPanel.getVerticalScrollBar().getModel().getMinimum();
	textAreaScrollPanel.getVerticalScrollBar().getModel().setValue(min);			
	
	
	this.repaint();
  }

  // Open named file; read text from file into jTextArea1; report to statusBar.
  void openFile(String fileName)  {
	try
	{
	  // Open a file of the given name.
	  File file = new File(fileName);

	  // Get the size of the opened file.
	  int size = (int)file.length();

	  // Set to zero a counter for counting the number of
	  // characters that have been read from the file.
	  int chars_read = 0;

	  // Create an input reader based on the file, so we can read its data.
	  // FileReader handles international character encoding conversions.
	  FileReader in = new FileReader(file);

	  // Create a character array of the size of the file,
	  // to use as a data buffer, into which we will read
	  // the text data.
	  char[] data = new char[size];

	  // Read all available characters into the buffer.
	  while(in.ready()) {
		// Increment the count for each character read,
		// and accumulate them in the data buffer.
		chars_read += in.read(data, chars_read, size - chars_read);
	  }
	  in.close();

	  // Create a temporary string containing the data,
	  // and set the string into the JTextArea.
	  textArea.setText(new String(data, 0, chars_read));

	  // Cache the currently opened filename for use at save time...
	  this.currFileName = fileName;
	  // ...and mark the edit session as being clean
	  this.dirty = false;

	  // Display the name of the opened directory+file in the statusBar.
	  statusBar.setText("Opened "+fileName);
	  updateCaption();
	}
	catch (IOException e)
	{
	  statusBar.setText("Error opening "+fileName);
	}
  }

	// Save current file; handle not yet having a filename; report to statusBar.
  boolean saveFile() {

	// Handle the case where we don't have a file name yet.
	if (currFileName == null) {
	  return saveAsFile();
	}

	try
	{
	  // Open a file of the current name.
	  File file = new File (currFileName);

	  // Create an output writer that will write to that file.
	  // FileWriter handles international characters encoding conversions.
	  FileWriter out = new FileWriter(file);
	  
	  String text = textArea.getText();
	  out.write(text);
	  out.close();
	  this.dirty = false;
	  updateCaption();
	  return true;
	}
	catch (IOException e) {
	  statusBar.setText("Error saving "+currFileName);
	}
	return false;
  }

  // Save current file, asking user for new destination name.
  // Report to statuBar.
  boolean saveAsFile() {
	this.repaint();
	// Use the SAVE version of the dialog, test return for Approve/Cancel
	if (JFileChooser.APPROVE_OPTION == jFileChooser1.showSaveDialog(this)) {
	  // Set the current file name to the user's selection,
	  // then do a regular saveFile
	  currFileName = jFileChooser1.getSelectedFile().getPath();
	  //repaints menu after item is selected
	  this.repaint();
	  return saveFile();
	}
	else {
	  this.repaint();
	  return false;
	}
  }

  // Check if file is dirty.
  // If so get user to make a "Save? yes/no/cancel" decision.
  boolean okToAbandon() {
	if (!dirty) {
	  return true;
	}
	int value =  JOptionPane.showConfirmDialog(this, "Save changes?",
										 "Text Edit", JOptionPane.YES_NO_CANCEL_OPTION) ;

	switch (value) {
	   case JOptionPane.YES_OPTION:
		 // yes, please save changes
		 return saveFile();
	   case JOptionPane.NO_OPTION:
		 // no, abandon edits
		 // i.e. return true without saving
		 return true;
	   case JOptionPane.CANCEL_OPTION:
	   default:
		 // cancel
		 return false;
	}
  }

	// Update the caption of the application to show the filename and its dirty state.
  void updateCaption() {
	String caption;

	if (currFileName == null) {
	   // synthesize the "Untitled" name if no name yet.
	   caption = "Untitled";
	}
	else {
	  caption = currFileName;
	}

	// add a "*" in the caption if the file is dirty.
	if (dirty) {
	  caption = "* " + caption;
	}
	caption = "Text Editor - " + caption;
	this.setTitle(caption);
  }


  //File | Exit action performed
  public void fileExit_actionPerformed(ActionEvent e) {
	if (okToAbandon()) {
	  this.dispose();
	}
  }

  //Help | About action performed
  public void helpAbout_actionPerformed(ActionEvent e) {
	helpAbout();
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
	super.processWindowEvent(e);
	if (e.getID() == WindowEvent.WINDOW_CLOSING) {
	  fileExit_actionPerformed(null);
	}
  }

  void jMenuItem5_actionPerformed(ActionEvent e) {
   // Handle the "Edit Font" menu item

   // Pick up the existing font from the text area
   // and put it into the FontChooser before showing
   // the FontChooser, so that we are editing the
   // existing / previous font.
   //fontChooser1.setSelectedFont(jTextArea1.getFont());

   // Obtain the new Font from the FontChooser.
   // First test the return value of showDialog() to
   // see if the user pressed OK.
   //if (fontChooser1.showDialog()) {

	  // Set the font of jTextArea1 to the font
	  // the user selected before pressing the OK button
   //   jTextArea1.setFont(fontChooser1.getSelectedFont());
   //}
   //repaints menu after item is selected
   this.repaint();
   //Repaints text properly if some text is highlighted when font is changed.
   textArea.repaint();
}

  void jMenuItem6_actionPerformed(ActionEvent e) {
	// Handle the "Foreground Color" menu item
	Color color = JColorChooser.showDialog(this,"Foreground Color",textArea.getForeground());
	if (color != null) {
	  textArea.setForeground(color);
	}
	//repaints menu after item is selected
	this.repaint();
  }

  void jMenuItem7_actionPerformed(ActionEvent e) {
	// Handle the "Background Color" menu item
	Color color = JColorChooser.showDialog(this,"Background Color",textArea.getBackground());
	if (color != null) {
	 textArea.setBackground(color);
	}
	//repaints menu after item is selected
	this.repaint();
  }

  void jMenuItem1_actionPerformed(ActionEvent e) {
	// Handle the File|New menu item.
	if (okToAbandon()) {
	  // clears the text of the TextArea
	  textArea.setText("");
	  // clear the current filename and set the file as clean:
	  currFileName = null;
	  dirty = false;
	  updateCaption();
	}
  }

  void jMenuItem2_actionPerformed(ActionEvent e) {
	//Handle the File|Open menu item.
	fileOpen();
  }

  void jMenuItem3_actionPerformed(ActionEvent e) {
	//Handle the File|Save menu item.
	saveFile();
  }

  void jMenuItem4_actionPerformed(ActionEvent e) {
	//Handle the File|Save As menu item.
	saveAsFile();
  }

  void jButton1_actionPerformed(ActionEvent e) {
	//Handle tool bar Open button
	fileOpen();
  }

  void jButton2_actionPerformed(ActionEvent e) {
	//Handle tool bar Save button
	saveFile();
  }

  void jButton3_actionPerformed(ActionEvent e) {
   //Handle tool bar About button
   helpAbout();
  }

  void document1_changedUpdate(DocumentEvent e) {
	if (!dirty) {
	  dirty = true;
	  updateCaption();
	}
  }

  void document1_insertUpdate(DocumentEvent e) {
	if (!dirty) {
	  dirty = true;
	  updateCaption();
	}
  }

  void document1_removeUpdate(DocumentEvent e) {
	if (!dirty) {
	  dirty = true;
	  updateCaption();
	}
  }
}