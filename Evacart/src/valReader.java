/* Class Name: valReader
 * Author: Nan Yang
 * Time: 01/10/2015
 * Class Description: This class reads the content of the val file. 
 * 
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class valReader {
	
	File valFile;
	BufferedReader  br;
	ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	int Max_x=0;
	int Max_y=0;
	
	//String temp;
	String line;
	String lastLine;
	String path;//="D:/study/C_eclipse/eclipse/workspace/Evacart/bin/Evacart workspace/Vals/Baggage.val";
	int length=0;
	int width=0;
	



	public valReader(File file, boolean x){
		File valFile=file;
		
		System.out.println(valFile.getAbsolutePath());
		try {
			br = new BufferedReader(new FileReader(valFile));
			
			/*while ((line = br.readLine()) != null && line!="" && line!="\n" && line!=System.getProperty("line.separator")) {
				lastLine=line;
			}*/
			while ((line = br.readLine()) != null){
				//if(line!=null){
					lastLine=line;
				//}
				
			}
			
			System.out.println("last line="+lastLine);
			String a = null;
			String b = null;
			
			lastLine=lastLine.replaceAll(" ", "");
			lastLine=lastLine.replaceAll("/n", "");
			int startIndex=lastLine.indexOf("(")+1;
			int middleIndex=lastLine.indexOf(")");
			//int endindex=lastLine.indexOf("=")+1;
			String data3=lastLine.substring(startIndex, middleIndex);

			
			String[] mydata=data3.split(",");
			a=mydata[0];
			b=mydata[1];
			//System.out.println("a, b, c, d:"+a+" "+b+" "+c+" "+d);
			
			
			
			try{
				length = Integer.parseInt(a)+1;
				width = Integer.parseInt(b)+1;
				
			}catch (NumberFormatException e){
				System.out.println("NumberFormatException 1");
				
			}catch (ClassCastException e){
				System.out.println("ClassCastException");
				
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File do not exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	public valReader(File file){
	//public valReader(){
		File valFile=file;
		//valFile=new File (path);
		System.out.println(valFile.getAbsolutePath());
		try {
			br = new BufferedReader(new FileReader(valFile));
			
			
			
			//DataInputStream br =DataInputStream();
				//DataInputStream(new BufferedInputStream(new FileInputStream(valFile)));
			
			//line=br.read
		//String format = String.format("(%d,%d,%d) = %s\n");
			String a = null;
			String b = null;
			String c = null;
			String d = null;
			
			
			
			
			while ((line = br.readLine()) != null) {
				if(!line.startsWith("%") && !line.trim().equals("") && !line.isEmpty()&& !line.trim().equals("\n")){
					//System.out.println(line);
					//System.out.println("a, b,c,d:"+a+b+c+d);
					line=line.replaceAll(" ", "");
					line=line.replaceAll("/n", "");
					int startIndex=line.indexOf("(")+1;
					int middleIndex=line.indexOf(")");
					int endindex=line.indexOf("=")+1;
					String data3=line.substring(startIndex, middleIndex);
					d=line.substring(endindex);
					
					String[] mydata=data3.split(",");
					a=mydata[0];
					b=mydata[1];
					c=mydata[2];
					//System.out.println("a, b, c, d:"+a+" "+b+" "+c+" "+d);
					
					try{
						if(Integer.parseInt(a)>Max_x){
							Max_x=Integer.parseInt(a);
						}
						if(Integer.parseInt(b)>Max_y){
							Max_y=Integer.parseInt(b);
						}
						
					}catch (NumberFormatException e){
						System.out.println("NumberFormatException 1");
						
					}catch (ClassCastException e){
						System.out.println("ClassCastException");
						
					}
					
					//a=line.substring(1, endIndex)
					ArrayList<String> myarray=	new ArrayList<String>();
					myarray.add(a);
					myarray.add(b);
					myarray.add(c);
					myarray.add(d);
					data.add(myarray);
					
				}else{
					//System.out.println("comment line:"+line);
				}		
			}		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File do not exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected int getMax_x() {
		return Max_x;
	}

	protected int getMax_y() {
		return Max_y;
	}

	protected ArrayList<ArrayList<String>> getData() {
		return data;
	}
	

	
	
	protected int getWidth() {
		return width;
	}


	protected int getLength() {
		return length;
	}
	
	
	
}