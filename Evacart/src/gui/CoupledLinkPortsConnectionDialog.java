package gui;

import gui.graphEditor.Layoutable;
import gui.model.Expression;
import gui.model.link.AbstractCoupledLink;
import gui.model.link.AbstractLink;
import gui.model.port.AbstractPort;
import gui.model.port.CoupledPortContainer;

import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Insert the type's description here.
 * Creation date: (14/10/2002 19:36:06)
 * @author: Administrator
 */
public class CoupledLinkPortsConnectionDialog extends OkCancelJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel ivjButtonsContainerPanel = null;
//	private java.awt.GridLayout ivjButtonsContainerPanelGridLayout = null;
	private JPanel ivjCombosContainer = null;
//	private java.awt.GridLayout ivjCombosContainerGridLayout = null;
	private JComboBox ivjFromPortComboBox = null;
	private JLabel ivjFromPortLabel = null;
	private JPanel ivjJDialogContentPane = null;
	private JLabel ivjLinkDescriptionLabel = null;
	private JComboBox ivjToPortComboBox = null;
	private JLabel ivjToPortLabel = null;
//	private java.awt.BorderLayout ivjJDialogContentPaneBorderLayout = null;
	private AbstractCoupledLink link;
/**
 * LinkPortsConnectionDialog constructor comment.
 */
public CoupledLinkPortsConnectionDialog() {
	super();
	initialize();
}
/**
 * LinkPortsConnectionDialog constructor comment.
 * @param owner java.awt.Dialog
 */
public CoupledLinkPortsConnectionDialog(java.awt.Dialog owner) {
	super(owner);
	initialize();	
}
/**
 * LinkPortsConnectionDialog constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 */
public CoupledLinkPortsConnectionDialog(java.awt.Dialog owner, String title) {
	super(owner, title);
	initialize();	
}
/**
 * LinkPortsConnectionDialog constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 * @param modal boolean
 */
public CoupledLinkPortsConnectionDialog(java.awt.Dialog owner, String title, boolean modal) {
	super(owner, title, modal);
	initialize();	
}
/**
 * LinkPortsConnectionDialog constructor comment.
 * @param owner java.awt.Dialog
 * @param modal boolean
 */
public CoupledLinkPortsConnectionDialog(java.awt.Dialog owner, boolean modal) {
	super(owner, modal);
	initialize();	
}
/**
 * LinkPortsConnectionDialog constructor comment.
 * @param owner java.awt.Frame
 */
public CoupledLinkPortsConnectionDialog(java.awt.Frame owner) {
	super(owner);
	initialize();	
}
/**
 * LinkPortsConnectionDialog constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public CoupledLinkPortsConnectionDialog(java.awt.Frame owner, String title) {
	super(owner, title);
	initialize();	
}
/**
 * LinkPortsConnectionDialog constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 * @param modal boolean
 */
public CoupledLinkPortsConnectionDialog(java.awt.Frame owner, String title, boolean modal) {
	super(owner, title, modal);
	initialize();	
}
/**
 * LinkPortsConnectionDialog constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public CoupledLinkPortsConnectionDialog(java.awt.Frame owner, boolean modal) {
	super(owner, modal);
	initialize();	
}
/**
 * Return the ButtonsContainerPanel property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getButtonsContainerPanel() {
	if (ivjButtonsContainerPanel == null) {
		try {
			ivjButtonsContainerPanel = new javax.swing.JPanel();
			ivjButtonsContainerPanel.setName("ButtonsContainerPanel");
			ivjButtonsContainerPanel.setLayout(getButtonsContainerPanelGridLayout());
			getButtonsContainerPanel().add(getOkButton(), getOkButton().getName());
			getButtonsContainerPanel().add(getCancelButton(), getCancelButton().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjButtonsContainerPanel;
}
/**
 * Return the ButtonsContainerPanelGridLayout property value.
 * @return java.awt.GridLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.GridLayout getButtonsContainerPanelGridLayout() {
	java.awt.GridLayout ivjButtonsContainerPanelGridLayout = null;
	try {
		/* Create part */
		ivjButtonsContainerPanelGridLayout = new java.awt.GridLayout();
		ivjButtonsContainerPanelGridLayout.setVgap(15);
		ivjButtonsContainerPanelGridLayout.setHgap(15);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjButtonsContainerPanelGridLayout;
}
/**
 * Return the CombosContainer property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getCombosContainer() {
	if (ivjCombosContainer == null) {
		try {
			ivjCombosContainer = new javax.swing.JPanel();
			ivjCombosContainer.setName("CombosContainer");
			ivjCombosContainer.setLayout(getCombosContainerGridLayout());
			getCombosContainer().add(getFromPortLabel(), getFromPortLabel().getName());
			getCombosContainer().add(getFromPortComboBox(), getFromPortComboBox().getName());
			getCombosContainer().add(getToPortLabel(), getToPortLabel().getName());
			getCombosContainer().add(getToPortComboBox(), getToPortComboBox().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjCombosContainer;
}
/**
 * Return the CombosContainerGridLayout property value.
 * @return java.awt.GridLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.GridLayout getCombosContainerGridLayout() {
	java.awt.GridLayout ivjCombosContainerGridLayout = null;
	try {
		/* Create part */
		ivjCombosContainerGridLayout = new java.awt.GridLayout(2, 2);
		ivjCombosContainerGridLayout.setVgap(10);
		ivjCombosContainerGridLayout.setHgap(10);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjCombosContainerGridLayout;
}
/**
 * Return the FromPortComboBox property value.
 * @return javax.swing.JComboBox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JComboBox getFromPortComboBox() {
	if (ivjFromPortComboBox == null) {
		try {
			ivjFromPortComboBox = new javax.swing.JComboBox();
			ivjFromPortComboBox.setName("FromPortComboBox");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjFromPortComboBox;
}
/**
 * Return the FromPortLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getFromPortLabel() {
	if (ivjFromPortLabel == null) {
		try {
			ivjFromPortLabel = new javax.swing.JLabel();
			ivjFromPortLabel.setName("FromPortLabel");
			ivjFromPortLabel.setLabelFor(getFromPortComboBox());
			ivjFromPortLabel.setText("From Port :");
			ivjFromPortLabel.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjFromPortLabel;
}
/**
 * Return the JDialogContentPane property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJDialogContentPane() {
	if (ivjJDialogContentPane == null) {
		try {
			ivjJDialogContentPane = new javax.swing.JPanel();
			ivjJDialogContentPane.setName("JDialogContentPane");
			ivjJDialogContentPane.setLayout(getJDialogContentPaneBorderLayout());
			getJDialogContentPane().add(getLinkDescriptionLabel(), "North");
			getJDialogContentPane().add(getCombosContainer(), "Center");
			getJDialogContentPane().add(getButtonsContainerPanel(), "South");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJDialogContentPane;
}
/**
 * Return the JDialogContentPaneBorderLayout property value.
 * @return java.awt.BorderLayout
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private java.awt.BorderLayout getJDialogContentPaneBorderLayout() {
	java.awt.BorderLayout ivjJDialogContentPaneBorderLayout = null;
	try {
		/* Create part */
		ivjJDialogContentPaneBorderLayout = new java.awt.BorderLayout();
		ivjJDialogContentPaneBorderLayout.setVgap(10);
		ivjJDialogContentPaneBorderLayout.setHgap(10);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjJDialogContentPaneBorderLayout;
}
/**
 * Return the LinkDescriptionLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getLinkDescriptionLabel() {
	if (ivjLinkDescriptionLabel == null) {
		try {
			ivjLinkDescriptionLabel = new javax.swing.JLabel();
			ivjLinkDescriptionLabel.setName("LinkDescriptionLabel");
			ivjLinkDescriptionLabel.setText("JLabel1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLinkDescriptionLabel;
}
/**
 * Return the ToPortComboBox property value.
 * @return javax.swing.JComboBox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JComboBox getToPortComboBox() {
	if (ivjToPortComboBox == null) {
		try {
			ivjToPortComboBox = new javax.swing.JComboBox();
			ivjToPortComboBox.setName("ToPortComboBox");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjToPortComboBox;
}
/**
 * Return the ToPortLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getToPortLabel() {
	if (ivjToPortLabel == null) {
		try {
			ivjToPortLabel = new javax.swing.JLabel();
			ivjToPortLabel.setName("ToPortLabel");
			ivjToPortLabel.setText("To Port : ");
			ivjToPortLabel.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjToPortLabel;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	 System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	 exception.printStackTrace(System.out);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		super.jbInit();
		// user code end
		setName("LinkPortsConnectionDialog");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setSize(426, 131);
		setContentPane(getJDialogContentPane());
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		CoupledLinkPortsConnectionDialog aLinkPortsConnectionDialog;
		aLinkPortsConnectionDialog = new CoupledLinkPortsConnectionDialog();
		aLinkPortsConnectionDialog.setModal(true);
		aLinkPortsConnectionDialog.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		aLinkPortsConnectionDialog.show();
		java.awt.Insets insets = aLinkPortsConnectionDialog.getInsets();
		aLinkPortsConnectionDialog.setSize(aLinkPortsConnectionDialog.getWidth() + insets.left + insets.right, aLinkPortsConnectionDialog.getHeight() + insets.top + insets.bottom);
		aLinkPortsConnectionDialog.setVisible(true);
	} catch (Throwable exception) {
		//System.err.println("Exception occurred in main() of javax.swing.JDialog");
		exception.printStackTrace(System.out);
	}
}
	/**
	 * Returns the link.
	 * @return Link
	 */
	private AbstractLink getLink() {
		return link;
	}

	/**
	 * Sets the link.
	 * @param link The link to set
	 */
	public void setLink(AbstractCoupledLink link) {
		this.link = link;    
		this.getLinkDescriptionLabel().setText(link.getDescription());

		//get both states
		Layoutable startUnit = link.getStartLinkPlugable();
		Layoutable endUnit = link.getEndLinkPlugable();

		//set the state's names
		this.getFromPortLabel().setText(startUnit.getName() + " : ");
		this.getToPortLabel().setText(endUnit.getName() + " : ");
				
		//reset the combos
		this.getFromPortComboBox().removeAllItems();
		this.getToPortComboBox().removeAllItems();
		
		//add the null Selection
		this.getFromPortComboBox().addItem("Disconected");
		this.getToPortComboBox().addItem("Disconected");
		
		//add the ports		
		Vector<?> startPorts = ((CoupledPortContainer)startUnit).getStartLinkPorts();
		for(int i = 0; i < startPorts.size(); i++){
			this.getFromPortComboBox().addItem(startPorts.get(i)); 
		}
		
		Vector<?> endPorts = ((CoupledPortContainer)endUnit).getEndLinkPorts();
		for(int i = 0; i < endPorts.size(); i++){
			this.getToPortComboBox().addItem(endPorts.get(i)); 
		}

		//set defaults.
		if(link.getStartExpression() != null){
			this.getFromPortComboBox().setSelectedItem(link.getStartExpression());
		}
		
		if(link.getEndExpression() != null){
			this.getToPortComboBox().setSelectedItem(link.getEndExpression());
		}
			
		
		
	}

	/**
	 * returns de From Port selection;
	 */
	public Expression getFromSelection() {
		if(this.getFromPortComboBox().getSelectedItem() instanceof AbstractPort){
			return (AbstractPort)this.getFromPortComboBox().getSelectedItem();		
		}
		else{
			// is the "select a port" String			
			return null;
		}
	}

	/**
	 * returns de To Port selection;
	 */
	public Expression getToSelection() {
		if(this.getToPortComboBox().getSelectedItem() instanceof AbstractPort){		
			return (AbstractPort)this.getToPortComboBox().getSelectedItem();		
		}
		else{
			// is the "select a port" String
			return null;
		}
			
	}
	
}
