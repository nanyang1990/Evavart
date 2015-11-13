import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;





public class test {
	static File logfile=new File("C:\\Users\\yangn1\\Desktop\\Evacart\\bin\\Evacart workspace\\Evacuation Models\\Airport_Egress","AirportLOG.log");
	
	public static void main(String[] args) throws Exception{
		//createFrame();
		/*if(logfile.exists()){
			System.out.println("File  exist");
			logReader myreader=new logReader(logfile);
			System.out.println(myreader.getData());
		}else{
			System.out.println("File do not exist");
		}*/
		int[][] mymap=new int[][] {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
		int x=mymap.length;
		int[][] mymap2=new int[2][2];
		System.out.println("00 is "+mymap2[0][0]);
		//getNeighbors(6,6);
		
	}
	
	private static void createFrame(){
		/* get the size of screen and set the size of frame */
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = new Dimension(screenSize.height * 4 / 7,
				screenSize.height * 1 / 3);
	
		
		main_view frame = new main_view();  // Create a frame
		
		frame.setTitle("Evacart ")		;
		frame.setSize(size); // Set the frame size
		frame.setLocationRelativeTo(null); // New since JDK 1.4
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); // Display the frame
		//frame.setResizable(false);// set the frame not resizable
		
		
	}
	
	protected static ArrayList<ArrayList<Integer>> getNeighbors(int x, int y){
		ArrayList<ArrayList<Integer>> Neighbors=new ArrayList<ArrayList<Integer>>();
		if(x>=7 || y>=7){
			System.out.println("Error, point out of the given map at "+x+","+y);
		}else
		{
			int TBDneighbour [] []=new int[][] {{x-1,y-1},{x, y-1},{x+1,y-1},{x-1,y},{x+1,y},{x-1,y+1},{x,y+1},{x+1,y+1}};
			
			for(int a=0;a<8;a++){
				//System.out.println("a= "+a);
				
				if(TBDneighbour[a][0]>=0 && TBDneighbour[a][0]<7 && TBDneighbour[a][1]>=0 && TBDneighbour[a][1]<7){
					ArrayList<Integer> line=new ArrayList<Integer>();
					line.add(TBDneighbour[a][0]);
					line.add(TBDneighbour[a][1]);
					System.out.println(TBDneighbour[a][0]+","+TBDneighbour[a][1]);
					Neighbors.add(line);
				}
			}
		}
		System.out.println("it has "+Neighbors.size()+" Neighbors");
		return Neighbors;
		
	}
	
	
	
}