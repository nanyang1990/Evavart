import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;

import org.jfree.data.category.DefaultCategoryDataset; /* We will use this dataset class to populate data for our bar chart */
import org.jfree.chart.ChartFactory; /* used to create a chart object */
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities; /* We will use this class to convert the chart to a PNG image file */

public class compare_view extends JFrame implements ActionListener {
	String path;
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JButton addButton = new JButton("Add");
	JButton deleteButton = new JButton("Delete");
	JButton compareButton = new JButton("Compare Models");
	Container pane = this.getContentPane();
	
	ImageIcon chart;
	JLabel label;

	Font myFont = new Font("Times New Roman", Font.BOLD, 14);
	private static JTextField logField1 = new JTextField(20);
	private static JTextField logField2 = new JTextField(20);
	private static JTextField logField3 = new JTextField(20);
	private static JTextField logField4 = new JTextField(20);
	private static JTextField logField5 = new JTextField(20);
	private static JTextField logField6 = new JTextField(20);
	private static JTextField logField7 = new JTextField(20);
	private static JTextField logField8 = new JTextField(20);
	private static JTextField logField9 = new JTextField(20);
	// private JTextField textFields
	// []={logField1,logField2,logField3,logField4,logField5,logField6,logField7,logField8,logField9};
	ArrayList<JTextField> textFields = new ArrayList<JTextField>();

	private JButton browse1 = new JButton("Browse");
	private JButton browse2 = new JButton("Browse");
	private JButton browse3 = new JButton("Browse");
	private JButton browse4 = new JButton("Browse");
	private JButton browse5 = new JButton("Browse");
	private JButton browse6 = new JButton("Browse");
	private JButton browse7 = new JButton("Browse");
	private JButton browse8 = new JButton("Browse");
	private JButton browse9 = new JButton("Browse");

	// private JButton browseButtons
	// []={browse1,browse2,browse3,browse4,browse5,browse6,browse7,browse8,browse9};

	ArrayList<JButton> browseButtons = new ArrayList<JButton>();

	int fieldcount = 2;

	public compare_view(String executionPath) {
		path = executionPath;
		initializeVariables();
		initializeFrame();
	}

	private void initializeVariables() {
		textFields.add(logField1);
		textFields.add(logField2);
		textFields.add(logField3);
		textFields.add(logField4);
		textFields.add(logField5);
		textFields.add(logField6);
		textFields.add(logField7);
		textFields.add(logField8);
		textFields.add(logField9);

		browseButtons.add(browse1);
		browseButtons.add(browse2);
		browseButtons.add(browse3);
		browseButtons.add(browse4);
		browseButtons.add(browse5);
		browseButtons.add(browse6);
		browseButtons.add(browse7);
		browseButtons.add(browse8);
		browseButtons.add(browse9);
	}

	private void initializeFrame() {
		pane.setLayout(new BorderLayout());
		setupLeftPanel();
		setupRightPanel();
		pane.add(leftPanel, BorderLayout.WEST);
		pane.add(rightPanel, BorderLayout.CENTER);

	}

	private void setupLeftPanel() {
		leftPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		pane.getInsets().set(12, 12, 12, 12);
		// natural height, maximum width
		// c.fill = GridBagConstraints.HORIZONTAL;

		// line 1 Evacuate Environment label
		JLabel NoteLabel = new JLabel(
				"Please select your execution log files:(2~9)");
		NoteLabel.setFont(myFont);

		c.gridwidth = 2;
		// c.ipady = 5;
		// c.ipadx = 10;
		// c.gridwidth = 2;
		c.gridx = 2;
		c.ipadx = 20;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		leftPanel.add(NoteLabel, c);

		c.gridx = 1;
		c.gridwidth = 1;
		c.ipadx = 20;
		c.gridy = 1;
		leftPanel.add(this.addButton, c);
		addButton.addActionListener(this);

		c.gridx = 2;
		c.gridwidth = 1;
		c.ipadx = 20;
		c.gridy = 1;
		leftPanel.add(this.deleteButton, c);
		deleteButton.addActionListener(this);

		c.gridx = 4;
		c.ipadx = 20;
		c.gridy = 1;
		c.gridwidth = 1;
		leftPanel.add(compareButton, c);
		compareButton.addActionListener(this);

		for (int x = 0; x < 9; x++) {
			JTextField testField = textFields.get(x);
			JButton button = browseButtons.get(x);

			testField.setFont(myFont);
			c.gridx = 2;
			c.gridwidth = 2;
			c.gridy = x + 2;
			leftPanel.add(testField, c);

			button.setFont(myFont);
			c.gridx = 4;

			c.gridy = x + 2;
			leftPanel.add(button, c);
			button.addActionListener(this);
			if (x >= 2) {
				testField.setVisible(false);
				button.setVisible(false);
			}

		}

	}

	private void setupRightPanel() {
		rightPanel.setLayout(new BorderLayout());
		label=new JLabel();
		rightPanel.add(label,BorderLayout.CENTER);
		
	    label.setIcon(new ImageIcon("Note.png"));

	}

	private boolean checkLogFiles() {
		File myLogfile;
		for (int x = 0; x < fieldcount; x++) {
			myLogfile = new File(textFields.get(x).getText());
			if (myLogfile.exists() && myLogfile.length() != 0) {
				// System.out.println("File exist");
			} else {
				System.out.println("File do not exist, or is empty.");
				JOptionPane
						.showMessageDialog(
								this,
								"Error, please verfy your Log file input Number "
										+ (x + 1)
										+ ".(No empty file) It can only be an absolute path,\n such as: C:\\Users\\YANG_1\\Documents\\unitTestingCB.log");
				return false;
			}
		}
		return true;
	}

	private void selectLogFile(int index) {
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(path));

		fc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".LOG file", "log");
		fc.setFileFilter(filter);

		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			textFields.get(index).setText(file.getAbsolutePath());
			// This is where a real application would open the file.
			System.out.println("Opening: " + file.getName());

		} else {
			System.out.println("Open command cancelled by user.");
		}
	}

	private void GenerateChart(int[] input,int fastindex) {
		int length=input.length;
		String [] models=new String[length];
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		for(int x=0;x<length;x++){
			String filename=(new File(textFields.get(x).getText())).getName();
			dataset.addValue(input[x]/1000,filename,filename);
			
		}
	      JFreeChart barChart =
	    		  ChartFactory.createBarChart(
	         "Model Comparasion Result", 
	         "Models", "Time (Seconds)", 
	         dataset);
	         
	      int width = 640; /* Width of the image */
	      int height = 480; /* Height of the image */ 
	      int x=(int) (Math.random()*10000);
	      File BarChart = new File( x+".jpeg" ); 
	      try {
			ChartUtilities.saveChartAsJPEG( BarChart , barChart , width , height );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      //File BarChart1=new File("output_chart.png");
	      //chart=new ImageIcon("BarChart.jpeg");
	      
	      //.removeAll();
	      //rightPanel.remove(label);
	      //rightPanel.add(label);
	      try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      label.setIcon(new ImageIcon(x+".jpeg"));
	      BarChart.delete();
	      //repaint();
	      //BarChart.delete();
	    //Sounds.burp();
	      //this.update(getGraphics());

	}
	
	private String [] getSimulationTime (){
		String [] result=new String [fieldcount];
		for(int x=0;x<fieldcount;x++){
			logReader myreader=new logReader(new File(textFields.get(x).getText()));
			result[x]=myreader.getData();
			//System.out.println(x+": "+result[x]);
			//result[x].s
		}
		return result;
	}
	
	private int [] convertToInt(String [] input){
		String [] temp=new String [4];
		int [] result = new int [input.length];
		for(int x=0;x<fieldcount;x++){
			temp=input[x].split(":");
			//System.out.println(temp[0] +" "+temp[1]);
			result[x]=Integer.parseInt(temp[0])*3600000 +Integer.parseInt(temp[1])*60000+Integer.parseInt(temp[2])*1000+Integer.parseInt(temp[3]);
			System.out.println(x+": "+result[x]);
		}
		
		
		return result;
	}
	private int getFastestIndex (int [] input){
		int fast = 0;
		int index = 0;
		for(int x=0;x<input.length;x++){
			if(x==0){
				fast=input[0];
				index=0;
			}else{
				if(input[x]<fast){
					fast=input[x];
					index=x;
				}
			}
			
		}
		
		return index;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("source=="+e.getSource());

		if (e.getSource() == addButton && fieldcount <= 8) {
			textFields.get(fieldcount).setVisible(true);
			browseButtons.get(fieldcount).setVisible(true);
			fieldcount += 1;

		} else if (e.getSource() == deleteButton && fieldcount >= 1) {
			fieldcount -= 1;
			textFields.get(fieldcount).setVisible(false);
			browseButtons.get(fieldcount).setVisible(false);
		} else if (browseButtons.contains(e.getSource())) {
			System.out.println("index=" + browseButtons.indexOf(e.getSource()));
			int index = browseButtons.indexOf(e.getSource());
			selectLogFile(index);
		} else if (e.getSource() == this.compareButton) {
			if (checkLogFiles()) {
				//TODO
				String [] times=getSimulationTime();
				int [] executionTime= convertToInt(times);
				int fastindex=getFastestIndex(executionTime);
				String filename=(new File(textFields.get(fastindex).getText())).getName();
				GenerateChart(executionTime,getFastestIndex(executionTime));
				JOptionPane
				.showMessageDialog(this,
						"As shown in the bar chart, the NO."+(fastindex+1)+" "+filename +" model has the bast performance: "+times[fastindex]+" seconds.");
				
			}
		}

	}

}