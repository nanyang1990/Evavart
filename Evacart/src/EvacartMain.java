/* Class Name: EvacartMain
 * Author: Nan Yang
 * Time: 01/10/2015
 * Class Description: This class is created as the main class which contains the main method of the application.
 * 
  Assignments are as follow:
  
  Floor Plan Setup

   -1	WALL
	0	EMPTY
	1-8	PERSON WITH DIRECTION
	9	EXIT

 Distance Plane

	-1	WALL
	0	EXIT
	1-8	GUIDANCE DIRECTION
 * 
 * 
 * 
 * 
 * 
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;


public class EvacartMain {
	
	
	public static void main(String[] args) throws Exception{
		createFrame();
		
	}
	
	private static void createFrame(){
		/* get the size of screen and set the size of frame */
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = new Dimension(700,
				380);
	
		
		main_view frame = new main_view();  // Create a frame
		
		frame.setTitle("Evacart ")		;
		frame.setSize(size); // Set the frame size
		frame.setLocationRelativeTo(null); // New since JDK 1.4
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); // Display the frame
		//frame.setResizable(false);// set the frame not resizable
		
		
	}
}