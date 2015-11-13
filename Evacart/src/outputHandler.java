/*
 * Created on Jun 25, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */


/**
 * @author ssim
 * 
 * This class allow to interact with the eclipse console
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



//import org.eclipse.swt.widgets.Display;

public class outputHandler extends Thread
{
	private InputStream is;
//	private String type;
	private boolean print,inputTask=false;
//	private ConsoleDocument cdos;
//	private String line;

	
	public class conWrite implements Runnable {
		String line;
		public conWrite(String line) {
			this.line = line;
		}
		
		public void run() {
			System.out.println(line);
		}
	}

	public outputHandler(InputStream is, String type, boolean print)
	{
		this.is = is;
//		this.type = type;
		this.print = print;
			
		//cdos = ConsoleDocument.getCDOS();
	}
	

   
	synchronized public void run()
	{
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String msg,path,line=null;
		int firstIndex, lastIndex,lineNumber,currentLine=0;
		
		try{
			
			while ( (line = br.readLine()) != null) {
			
				//	System.out.println(type + ">" + line);
					
				if (print==true)
					//Display.getDefault().syncExec(new conWrite(line));
					System.out.println(line);
				
				if (inputTask == true){
					try{
						int pathIndex=line.indexOf("workspace");
						firstIndex=line.indexOf(":");
						lastIndex=line.indexOf(":",firstIndex+1);
						msg=line.substring(lastIndex+2);
						path=line.substring(pathIndex,firstIndex);
						path=path.substring(path.indexOf("/"));
						String msgType=line.substring(firstIndex,lastIndex);
						
						msgType=msgType.trim();
						if (msgType.length()!=0){
							lineNumber=new Integer(msgType.substring(1)).intValue();
							if(lineNumber==currentLine)
								continue;
					
							
							File file=new File(path);
						
							//IMarker marker = file.createMarker(IMarker.PROBLEM);
							//marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
							currentLine=lineNumber;
							//marker.setAttribute(IMarker.LINE_NUMBER,lineNumber);
							//marker.setAttribute(IMarker.MESSAGE,msg);
						}
	
															
						
					}catch (Exception e){
						System.out.println("exception here 1");
						continue;  
					}
				}
			}						
					
		} 
		catch (IOException ioe){
				ioe.printStackTrace();  
				System.out.println("The action has caused: 2");
			}
		catch (Exception e) {
			System.out.println("The action has caused: " + e);
		}
		finally
		{			
			try {
				if(br!= null)
					br.close();
				if(isr != null)
					isr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	
}
