package gui;


import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//import cdmodeler.CDModelerPlugin;

/*
 * The class HelpInfo creates a frame containing message and links 
 * to the CD++ Modeler manual in "*.doc" or "*.htm" formats.
 * The frame is instantiated when the User clicks "Help" button 
 * in the toolbar of CD++ Modeler. The actual line of code that 
 * activates the constructor is in the MainFrame.java:
 * 	private void showHelp() {
 *		new HelpInfo();  <--THIS IS THE LINE
 *		HelpLoader.showHelp();
 *	}
 * @author Chiril Chidisiuc
 * 
 * @version 2006-07-31
 */
public class HelpInfo extends JFrame implements ActionListener{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Checkbox optionDoc;
	private Checkbox optionHtm;
	private JButton buttonCancel;
	private JButton buttonOK;
	private JLabel messageLabel;
	private String classPathString;
	private String helpMessage;
	private String pluginPathString;
	private String buttonOkText;
	private String buttonCancelText;

	/**
	 * This constructor creates the look of the frame
	 * and instantiates all the components
	 * 
	 * @param	none
	 * @return	none
	 * @author Chiril Chidisiuc
	 * @version 2006-07-31
	 */
	public HelpInfo() {
		HelpInfo.setDefaultLookAndFeelDecorated(true); //set native look
		this.setLocationRelativeTo(null);	//center of the screen
		this.setIconImage(this.getIcon("Help/GUI_HTML/images/helpMini.GIF").getImage());//frame icon
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(this.createMessagePanel(), "North");
		cp.add(this.createOptionPanel(), "Center");
		cp.add(this.createButtonPanel(), "South");
		this.setTitle("CD++Modeler Help information");
		this.setContentPane(cp);
		this.setResizable(false);//locked size
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//close without terminating the whole process tree
		this.pack();
		this.setVisible(true);

		
		
		this.classPathString = this.getClass().getResource("HelpInfo.class").toString();	//path to this class
//		jar:file:/C:/eclipse/plugins/CD++Builder_1.1.0/cdmodelerFull/CDModeler.jar!/gui/HelpInfo.class
		int pluginIndexStart = this.classPathString.indexOf("jar:file:/")+10; //in theory this should be (0 + 10)
																			  //index of "jar:file:/" should be 0
		int pluginIndexEnd = this.classPathString.lastIndexOf("/"+ "CDModelerPlugin.PLUGIN_ID"+"/")+19;
		this.pluginPathString = this.classPathString.substring(pluginIndexStart,pluginIndexEnd); //plugin location
//		e.g. C:/eclipse/plugins/CD++Builder_1.1.0/		

		//Stream to write file
//	    try {
//	        BufferedWriter out = new BufferedWriter(new FileWriter("C:/HelpInfo.class_test.txt"));
//	        out.write( (new Date()) + "\ttest1: " + this.pluginPathString);
//	        out.close();
//	    } catch (IOException e) {
//			e.printStackTrace();
//	    }
	}
	
	/**
	 * This method converts the given path to command line format by replacing
	 * the path delimiters with Windows path delimiters. (each '/' is replaced
	 * with '\')
	 * 
	 * @param path:String -
	 *            the given current path (e.g. "C:/eclipse/workspace")
	 * @return converted path as a String - path with new delimiters (e.g.
	 *         "C:\eclipse\workspace")
	 * @author Chiril Chidisiuc
	 * 
	 * @version 2006-06-20
	 */
	protected String convertPathToCmd(String path){
		char c1 = 47; // character 47 is: '/'
		char c2 = 92; // character 92 is: '\'
		return path.replace(c1,c2); //replace(char oldChar, char newChar)
	}
	
	/**
	 * Creates image from a provided path and return it as parameter
	 * 
	 * @param imageName
	 * @return ImageIcon object
	 * @author Chidisiuc Chiril
	 * @version 2006-07-31
	 */
	private ImageIcon getIcon(String imageName) {
		URL imageURL = null;
		imageURL = getClass().getResource(imageName);
//		System.out.println("getClass():"+this.getClass()+"\n"+this.getClass().getResource(""));
		return new ImageIcon(imageURL);
	}
	
	/**
	 * Panel containing label with message is created and returned for further
	 * use.
	 * 
	 * @param none
	 * @return JPanel object
	 * @author Chiril Chidisiuc
	 * @version 2006-07-31
	 */
	private JPanel createMessagePanel(){
		JPanel messagePanel = new JPanel();
		this.helpMessage = "<html>" 
							+ "   CD++ Modeler manual contains the detailed <BR> " 
							+ "instructions on usage of the tool "
							+ "</html>";
							
		this.messageLabel = new JLabel();
		this.messageLabel.setFont(new Font("Courier New", Font.PLAIN, 12));
		this.messageLabel.setText(this.helpMessage);
		messagePanel.add(this.messageLabel);
		return messagePanel;
	}
	
	/**
	 * Panel containing checkboxes is created and returned for further use.
	 * 
	 * @param none
	 * @return JPanel object
	 * @author Chiril Chidisiuc
	 * @version 2006-07-31
	 */
	private JPanel createOptionPanel(){
		JPanel optionPanel = new JPanel();
//		optionPanel.setBackground(new Color(0,0,0,0));	//transparent
		this.optionDoc = new Checkbox("\"*.doc\"", true);
		this.optionHtm = new Checkbox("\"*.htm\"", false);
		optionPanel.setLayout(new BorderLayout());
		optionPanel.add(optionDoc, "West");
		optionPanel.add(optionHtm , "Center");
		return optionPanel;
	}
	
	/**
	 * Panel containing buttons "Open Manual" and "Cancel" is created and
	 * returned for further use.
	 * 
	 * @param none
	 * @return JPanel object
	 * @author Chiril Chidisiuc
	 * @version 2006-07-31
	 */
	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel();
//		buttonPanel.setBackground(new Color(0,0,0,0));	//transparent
		this.buttonOkText = "Open Manual...";
		this.buttonOK = new JButton(this.buttonOkText);
		this.buttonOK.setToolTipText("View the manual");
		this.buttonOK.addActionListener(this);
		this.buttonOK.setIcon(this.getIcon("Help/GUI_HTML/images/Open16.gif"));
		
		this.buttonCancelText = "Cancel";
		this.buttonCancel = new JButton(this.buttonCancelText);
		this.buttonCancel.setToolTipText("Close message");
		this.buttonCancel.addActionListener(this);
		this.buttonCancel.setIcon(getIcon("Help/GUI_HTML/images/Cancel.gif"));
		
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(this.buttonOK, "West");
		buttonPanel.add(this.buttonCancel, "East");
		return buttonPanel;
	}
	
	/**
	 * This method creates the and carries out the process of opening manual in
	 * native system application assigned to open the "*.doc" types of files
	 * 
	 * @param none
	 * @return none
	 * @throws IOException
	 * @author Chiril Chidisiuc
	 * @version 2006-07-31
	 */
	private void openManualDoc() throws IOException{
//		Runtime.getRuntime().exec("cmd /c start "+"C:\\eclipse\\plugins\\CD++Builder_1.1.0\\cdmodelerfull\\Help\\CDModeller_from_main_manual.doc");
		Runtime.getRuntime().exec("cmd /c start "
				+this.convertPathToCmd(this.pluginPathString).trim()
				+"cdmodelerfull\\Help\\CDModeller_from_main_manual.doc");
	}
	
	/**
	 * This method creates the and carries out the process of opening manual in
	 * native system application assigned to open the "*.htm" types of files
	 * 
	 * @param none
	 * @return none
	 * @throws IOException
	 * @author Chiril Chidisiuc
	 * @version 2006-07-31
	 */
	private void openManualHtm() throws IOException{
//		Runtime.getRuntime().exec("cmd /c start C:\\eclipse\\plugins\\CD++Builder_1.1.0\\cdmodelerfull\\Help\\CDModeller_from_main_manual.htm");
		Runtime.getRuntime().exec("cmd /c start "
				+this.convertPathToCmd(this.pluginPathString).trim()
				+"cdmodelerfull\\Help\\CDModeller_from_main_manual.htm");
	}
	
	/**
	 * This method opens the selected version(s) of the manual.
	 * If none are selected, no action is taken.
	 * 
	 * @param none
	 * @return none
	 * @author Chiril Chidisiuc
	 * @version 2006-07-31
	 */
	private void openManual(){
		try {
			if (!((this.optionDoc.getState() == false)&&(this.optionHtm.getState() == false))){
				if (this.optionDoc.getState() == true) {
					openManualDoc();
				}
				if (this.optionHtm.getState() == true) {
					openManualHtm();
				}
				this.dispose();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Implementation of the inherited method from ActionListener.
	 * Opens manual if "Open Manual" button was selected by calling openManual() method and
	 * Closes the frame otherwise.
	 * Implemented by Chiril Chidisiuc
	 * 
	 * 
	 * @param ev:ActionEvent
	 * @return none
	 * @version 2006-07-31
	 */
	public void actionPerformed(ActionEvent ev) {
		if (((JButton) ev.getSource()).getText().trim().equals(this.buttonOkText)){
			this.openManual();
		} else {
			this.dispose();
		}
	}
}
