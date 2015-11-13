package gui;

import java.net.URL;


//packages needed to show help
//import javax.help.*;
//import javax.help.event.*;
//import javax.help.plaf.basic.*;

/**
 * @author jicidre@dc.uba.ar
 * This class has the methods necesary to display the help
 */
public class HelpLoader {
//	unused
//	private String helpSetName = null;
//	private String helpSetURL = null;
	private javax.help.HelpSet hs;
	private javax.help.HelpBroker hb;

	/**
	 * Constructor for HelpLoader.
	 */
	public HelpLoader() {
		super();
	}

	public static void showHelp() {
		HelpLoader helpLoader = new HelpLoader();
		helpLoader.loadAndShowHelp();
	}
	private void loadAndShowHelp(){
		if (hs == null) {
			createHelpSet();
			hb = hs.createHelpBroker();
		}
		hb.setDisplayed(true);
	}
	
	/**
	* Crea el HelpSet
	*/
	private void createHelpSet() {

		try {
			
			URL helpURL = null;
//jar:file:/C:/eclipse/plugins/CD++Builder_1.1.0/cdmodelerFull/CDModeler.jar!/gui/Help/GUI.hs			
			helpURL = getClass().getResource("Help/GUI.hs");			
			System.err.println(helpURL);
			System.err.println(this.getClass().getClassLoader());

			hs = new javax.help.HelpSet(this.getClass().getClassLoader(), helpURL);
		}
		catch (Throwable ee) {
			ee.printStackTrace();
			return;
		}

	}
}