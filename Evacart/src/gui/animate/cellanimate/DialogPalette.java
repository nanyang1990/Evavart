package gui.animate.cellanimate;

import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class DialogPalette extends JDialog {
  JPanel tablePanel;
  TitledBorder titledBorder1;
  BorderLayout borderLayout1 = new BorderLayout();
  JColorChooser jColorChooserPalette = new JColorChooser();
  JPanel buttonsPanelTable = new JPanel();
  JButton buttonRemove = new JButton();
  JButton buttonAdd = new JButton();
  JButton buttonColor = new JButton();
  JButton buttonLoad = new JButton();
  JButton buttonSave = new JButton();
  //JFileChooser jFileChooserPalette = new JFileChooser();
  JButton buttonTexture = new JButton();
  JTextField textTexture = new JTextField();
  JButton buttonTextureFile = new JButton();
  JButton buttonAccept = new JButton();
  JButton buttonCancel = new JButton();
  JPanel leftPanel = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel rightPanel = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel buttonsPanelGeneral = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  FlowLayout flowLayout2 = new FlowLayout();
  JLabel jLabel1 = new JLabel();

  PaletteTable paletteTable = new PaletteTable();
  Palette palette;

  public DialogPalette(Palette pal) {
	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);  	
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    palette = pal;
    //Load all the palette data in paletteTable
    paletteTable.setData(palette.toTable());

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("[Your Icon]")));
    tablePanel = (JPanel) this.getContentPane();
    titledBorder1 = new TitledBorder("");
    tablePanel.setLayout(borderLayout1);
    this.setResizable(false);
    this.setSize(new Dimension(650, 420));
    this.setTitle("Modify Palette");
    JScrollPane scrollpane = new JScrollPane(paletteTable);
    scrollpane.setBorder(new BevelBorder(BevelBorder.LOWERED));
    scrollpane.setPreferredSize(new Dimension(217, 200));
    buttonRemove.setPreferredSize(new Dimension(91, 27));
    buttonRemove.setText("Remove");
	buttonRemove.setMnemonic(KeyEvent.VK_R);
    buttonRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonRemove_actionPerformed(e);
      }
    });
    buttonAdd.setPreferredSize(new Dimension(91, 27));
    buttonAdd.setText("Add");
	buttonAdd.setMnemonic(KeyEvent.VK_D);
    buttonAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonAdd_actionPerformed(e);
      }
    });
    buttonColor.setPreferredSize(new Dimension(91, 27));
    buttonColor.setText("Set color");
	buttonColor.setMnemonic(KeyEvent.VK_C);
    buttonColor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonColor_actionPerformed(e);
      }
    });
    buttonLoad.setPreferredSize(new Dimension(81, 27));
    buttonLoad.setText("Load");
	buttonLoad.setMnemonic(KeyEvent.VK_L);
    buttonLoad.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonLoad_actionPerformed(e);
      }
    });
    buttonSave.setPreferredSize(new Dimension(81, 27));
    buttonSave.setText("Save");
	buttonSave.setMnemonic(KeyEvent.VK_S);
    buttonSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonSave_actionPerformed(e);
      }
    });
    buttonTexture.setText("Set texture");
	buttonTexture.setMnemonic(KeyEvent.VK_T);
    buttonTexture.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        buttonTexture_actionPerformed(e);
      }
    });
    textTexture.setColumns(8);
    buttonTextureFile.setText("...");
    buttonTextureFile.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        buttonFileTexture_actionPerformed(e);
      }
    });
    buttonAccept.setPreferredSize(new Dimension(81, 27));
    buttonAccept.setText("Accept");
	buttonAccept.setMnemonic(KeyEvent.VK_A);
    buttonAccept.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        buttonAccept_actionPerformed(e);
      }
    });
    buttonCancel.setPreferredSize(new Dimension(81, 27));
    buttonCancel.setText("Cancel");
	buttonCancel.setMnemonic(KeyEvent.VK_N);
    buttonCancel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        buttonCancel_actionPerformed(e);
      }
    });
    leftPanel.setLayout(borderLayout2);
    rightPanel.setLayout(borderLayout3);
    buttonsPanelTable.setPreferredSize(new Dimension(217, 100));
    buttonsPanelTable.setLayout(flowLayout2);
    buttonsPanelGeneral.setLayout(flowLayout1);
    flowLayout1.setHgap(10);
    flowLayout2.setHgap(10);
    jLabel1.setText("Texture:");
    buttonsPanelTable.add(buttonAdd, null);
    buttonsPanelTable.add(buttonRemove, null);
    buttonsPanelTable.add(buttonTexture, null);
    buttonsPanelTable.add(buttonColor, null);
    buttonsPanelTable.add(jLabel1, null);
    buttonsPanelTable.add(textTexture, null);
    buttonsPanelTable.add(buttonTextureFile, null);
    buttonsPanelGeneral.add(buttonLoad, null);
    buttonsPanelGeneral.add(buttonSave, null);
    buttonsPanelGeneral.add(buttonAccept, null);
    buttonsPanelGeneral.add(buttonCancel, null);
    leftPanel.setPreferredSize(new Dimension(217, 200));
    tablePanel.add(rightPanel,  BorderLayout.CENTER);
    rightPanel.add(jColorChooserPalette, BorderLayout.CENTER);
    tablePanel.add(leftPanel,  BorderLayout.WEST);
    leftPanel.add(scrollpane, BorderLayout.CENTER);
    leftPanel.add(buttonsPanelTable, BorderLayout.SOUTH);
    rightPanel.add(buttonsPanelGeneral,  BorderLayout.SOUTH);

    this.setModal(true);
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    this.show();
  }

  void buttonAdd_actionPerformed(ActionEvent e) {
    paletteTable.dataModel.addLine();
  }

  void buttonRemove_actionPerformed(ActionEvent e) {
  	int rowSel = paletteTable.getSelectedRow();
  	if (rowSel !=-1)
  	{
  		paletteTable.dataModel.removeLine(paletteTable.getSelectedRow());
  		if (rowSel < paletteTable.getRowCount())
  		{
  			paletteTable.setRowSelectionInterval(rowSel,rowSel);
  		}
  		else
  		{
  			if (rowSel > 0)
  			{
  				paletteTable.setRowSelectionInterval(rowSel-1,rowSel-1);
  			}
  		}
  	}
  }

  void buttonColor_actionPerformed(ActionEvent e) {
    if (paletteTable.getSelectedRow()!=-1)
      paletteTable.dataModel.setColor(paletteTable.getSelectedRow(), jColorChooserPalette.getColor());
  }

  void buttonLoad_actionPerformed(ActionEvent e) {
    // Use the OPEN version of the dialog, test return for Approve/Cancel
			JFileChooser fc = new JFileChooser();
			ExtensionFilter exts = new ExtensionFilter();
			exts.addExtension("pal");

			fc.addChoosableFileFilter(exts);
    if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(this)) {
      if (! paletteTable.dataModel.loadData(fc.getSelectedFile().getPath()))
      {
        JOptionPane.showMessageDialog(this, "Can't load file", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  void buttonSave_actionPerformed(ActionEvent e) {
  // Use the SAVE version of the dialog, test return for Approve/Cancel
			JFileChooser fc = new JFileChooser();
			ExtensionFilter exts = new ExtensionFilter();
			exts.addExtension("pal");

			fc.addChoosableFileFilter(exts);
  
  if (JFileChooser.APPROVE_OPTION == fc.showSaveDialog(this)) {
    String currFileName = fc.getSelectedFile().getPath();
    if(!currFileName.toLowerCase().endsWith(".pal")){
    	currFileName += ".pal";
    }
    paletteTable.dataModel.saveData(currFileName);
    }
  }

  void buttonTexture_actionPerformed(ActionEvent e)
  {
    if (paletteTable.getSelectedRow()!=-1)
      paletteTable.dataModel.setTexture(paletteTable.getSelectedRow(), textTexture.getText());
  }

  void buttonFileTexture_actionPerformed(ActionEvent e)
  {
			JFileChooser fc = new JFileChooser();
//			ExtensionFilter exts = new ExtensionFilter();
//			exts.addExtension("pal");

//			fc.addChoosableFileFilter(exts);
  	
  	int retval = fc.showOpenDialog(this);
    if (JFileChooser.APPROVE_OPTION == retval)
    {
      textTexture.setText(fc.getSelectedFile().getPath());
    }
  }

  void buttonCancel_actionPerformed(ActionEvent e)
  {
    this.dispose();
  }

  void buttonAccept_actionPerformed(ActionEvent e)
  {
    palette.fromTable(paletteTable.getData());
    this.dispose();
  }

}