/* Class Name: UI
 * Author: Nan Yang
 * Time: 01/10/2015
 * Class Description: This class implements the User Interface main page.
 */

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import java.io.File;




public class UI_old_1 extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private Text textMa;
	private JButton fileButtonVal = new JButton("Browse");
	private JButton simuButton = new JButton("Run Simulation");
	private JButton visualizeButton = new JButton("Visualize");
	private JTextField maTextField = new JTextField(25);
	JTextField numberField = new JTextField(25);
	Font myFont = new Font("Times New Roman", Font.BOLD, 14);

	public UI_old_1() {

		addFeatures();

	}

	private void addFeatures() {

		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		pane.getInsets().set(10, 10, 10, 10);
		// natural height, maximum width
		// c.fill = GridBagConstraints.HORIZONTAL;

		// Row 1 - Should be text description of ma text box

		JLabel fileLabel = new JLabel("Coupled Model File Name(.ma)");
		fileLabel.setFont(myFont);
		// c.weightx = 20;
		c.gridwidth = 5;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 5;
		c.ipadx = 10;
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		pane.add(fileLabel, c);

		// Row 2 - Text Box for the ma file and file button

		// Create a regular text field.

		maTextField.addActionListener(this);
		maTextField.setFont(myFont);
		// c.weightx = 16;
		c.gridwidth = 2;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		pane.add(maTextField, c);

		fileButtonVal.addActionListener(this);
		fileButtonVal.setFont(myFont);
		// c.weightx = 3;
		c.gridwidth = 1;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		pane.add(fileButtonVal, c);

		JLabel numberLabel = new JLabel("Number of people for evacuation.");
		numberLabel.setFont(myFont);
		// c.weightx = 20;
		c.gridwidth = 5;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 5;
		// c.ipadx = 10;
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		pane.add(numberLabel, c);

		// Create a text field for numbers

		numberField.setFont(myFont);
		// c.weightx = 16;
		c.gridwidth = 2;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		pane.add(numberField, c);

		JLabel methodLabel = new JLabel("Egress strategy: ");
		methodLabel.setFont(myFont);
		// c.weightx = 20;
		c.gridwidth = 1;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 5;
		// c.ipadx = 10;
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		pane.add(methodLabel, c);

		String[] method = { "follow-the-herd", "Random",
				"patrolled", "grouped", "disabled" };
		
	/*	SpinnerListModel model = new SpinnerListModel(method);
		final JSpinner spinner = new JSpinner(model);
		// spinner.setSize(2000, 1000);
		// spinner.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		spinner.setFont(myFont);*/
		
		final JComboBox cb = new JComboBox(method);
		 cb.setVisible(true);

		// c.weightx = 20;
		c.gridwidth = 1;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 5;
		// c.ipadx = 10;
		c.gridx = 2;
		c.gridy = 4;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		pane.add(cb, c);

		simuButton.addActionListener(this);
		simuButton.setFont(myFont);
		// c.weightx = 3;
		c.gridwidth = 1;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		pane.add(simuButton, c);

		visualizeButton.addActionListener(this);
		visualizeButton.setFont(myFont);
		// c.weightx = 3;
		c.gridwidth = 1;
		// c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 5;
		pane.add(visualizeButton, c);

	}

	
	private boolean checkNumberInput() {
		int d;
		try {
			d = Integer.parseInt(numberField.getText());
		} catch (NumberFormatException nfe) {
			JOptionPane
					.showMessageDialog(this,
							"Error, please verfy your number input. It can only be positive numbers.");
			return false;
		}
		if (d > 0) {
			return true;
		} else {
			JOptionPane
					.showMessageDialog(this,
							"Error, please verfy your number input. It can only be positive numbers.");
			return false;
		}
	}

	private boolean checkMaFile() {
		File myfile = new File(maTextField.getText());
		if (myfile.exists()) {
			// System.out.println("File exist");
			return true;
		} else {
			System.out.println("File do not exist");
			JOptionPane
					.showMessageDialog(
							this,
							"Error, please verfy your Ma file input. "
									+ "It can only be an absolute path,\n such as: C:\\Users\\YANG_1\\Documents\\unitTestingCB.ma");
			return false;
		}
	}
	
	private void selectMaFile(){
		final JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".MA file", "ma");
		fc.setFileFilter(filter);
		
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			maTextField.setText(file.getAbsolutePath());
			// This is where a real application would open the file.
			System.out.println("Opening: " + file.getName());

		} else {
			System.out.println("Open command cancelled by user.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO action performed
		if (e.getSource() == this.fileButtonVal) {
			selectMaFile();
		}

		else if (e.getSource() == this.simuButton) {
			if (checkMaFile()) {
				if (checkNumberInput()) {
					System.out.println("Simulation starts: ");
					simulator mysimulator=new simulator();
				}
			}
		}
		else if (e.getSource() == this.visualizeButton) {
			System.out.println("Simulation starts: ");
			simulator mysimulator=new simulator();
		}

	}

}