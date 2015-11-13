

/* Class Name: Simulator
 * Author: Nan Yang
 * Time: 01/16/2015
 * Class Description: This class implements simulator,
 * 		which is built on top of the existing CD++ Builder project.
 */

//"D:/study/C_eclipse/eclipse/plugins/cdBuilder.simulator_1.0.0.201109022248/internal/simuOrig.exe"  
// 		-m"ship_2.ma" -o"ship_2OUT.out" -l"ship_2LOG.log" -t"00:00:00:000"



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;










public class simulator {
	String executationPath;
	File tempExecutionFile;
	File logFile;
	File outFile;
	String simulatorPath="";
	//String simulatorPath1="C:/eclipse/plugins/cdBuilder.simulator_1.0.0.201109022248/internal/simuOrig.exe";
	//String simulatorPath2="C:\\eclipse\\plugins\\cdBuilder.simulator_1.0.0.201109022248\\internal\\simuOrig.exe";
	//String simulatorPath3="C:/Users/yangn1/Desktop/New folder/simuOrig.exe";
	String buildingPath="";
	String valPath="";
	String modelPath="";
	
	String simulationTime="";
	//String evaStrategy=null;
	File maCopy;
	private Process simuProcess;
	public simulator() {
		
		
	}
	

	
	public simulator(String time, String strategy, String valName) throws IOException {
		//evaStrategy=strategy;
		simulationTime=time;
		
		//executationPath="C:\\Users\\YANGN_000\\Desktop\\New folder";
		//executationPath="C:\\Users\\yangn1\\Desktop\\New folder";
		getProjectPathes();		
		createMaCopy(strategy,valName);

		//executationPath="C:\\Users\\yangn1\\Desktop\\workspace\\evaca\\bin\\Evacart workspace\\executions";
		//executationPath="C:\\Users\\yangn1\\Desktop\\New folder";
		tempExecutionFile=new File(executationPath,"tempExecutionFile.bat");
		tempExecutionFile.exists();
		
		logFile=new File(executationPath,valName.replaceAll(".val",".log" ));
		outFile=new File(executationPath,valName.replaceAll(".val",".out" ));
		
		String command = "cd /D \"" + executationPath + "\"\n";
		command += "\"" + simulatorPath + "\" "+ collectParameters(strategy) + " ";
		
			try {
				FileWriter tempFileWriter =	new FileWriter(tempExecutionFile);
				
				// write command to tempSimu.bat
				tempFileWriter.write(command);
				tempFileWriter.close();
				
				logFile.createNewFile();
				outFile.createNewFile();
				
				// execute the .bat to run the simulation in a new process 
				simuProcess = Runtime.getRuntime().exec("cmd /c \"" + tempExecutionFile.toString() + "\"", null, new File(executationPath.toString()));
				
				// process listeners
				outputHandler outputHandler = new outputHandler(simuProcess.getInputStream(), "OUTPUT",	true);					
				outputHandler.start();						
				
				outputHandler errorHandler = new outputHandler(simuProcess.getErrorStream(), "ERROR", true);
				errorHandler.start();
				
				// Wait for simulation to complete running
				int exitStatus = simuProcess.waitFor();		
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		


	}
	
	
	protected File getLogFile() {
		return logFile;
	}

	private void getProjectPathes(){
		//URL x=getClass().getProtectionDomain().getCodeSource().getLocation();
		//File myfile=new File(x.getPath(),"findme.log");
		//System.out.println("myfile path is "+myfile.getAbsolutePath());
		//System.out.println("Old Building path is "+x.getPath());
		ClassLoader cl=Thread.currentThread().getContextClassLoader();
		java.net.URL path= cl.getResource("simulator.class");
		
		
		URI uri = null;
		try {
			uri = path.toURI();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(uri);
		
		
		
		if(file.exists()){
			System.out.println("file exists in "+file.getAbsolutePath());
		}
		
		
		buildingPath=file.getPath().replaceFirst("simulator.class", "");
		//buildingPath=x.getPath().replaceFirst("/", "");
		//simulatorPath=buildingPath+"simuOrig.exe";
		simulatorPath=buildingPath+"Evacart workspace\\simuOrig.exe";
		executationPath=buildingPath+"Evacart workspace\\executions";
		valPath=buildingPath+"Evacart workspace\\Vals";
		modelPath=buildingPath+"Evacart workspace\\Evacuation Models";
		
		System.out.println("Building path is "+buildingPath);
		System.out.println("Execution path is "+executationPath);
		System.out.println("valPath path is "+valPath);
		System.out.println("modelPath path is "+modelPath);
		
		
	}
	
	private void createMaCopy(String maName,String valName) throws IOException{
		System.out.println("In createMaCopy, valName=:"+valName);
		
		File valFile=new File(executationPath,valName);
		//valFile.delete();
		valFile.createNewFile();
		valReader sizeReader = new valReader (valFile,true);
		int length=sizeReader.getLength();
		int width=sizeReader.getWidth();
		maCopy=new File(executationPath,maName+".ma");
		//maCopy.createNewFile();
		File maFile=new File(modelPath+"\\"+maName,maName+".ma");
		//Files.copy( maFile.toPath(), maCopy.toPath() );
		BufferedReader br;
		String line;
		FileWriter maFileWriter =	new FileWriter(maCopy);

		try {
			br = new BufferedReader(new FileReader(maFile));
			line = br.readLine();
			

			while (line != null) {
				if(line.startsWith("initialCellsValue :")){
					//line=line.substring(0, 19);
					line="initialCellsValue : ";
					line+=valName;
					maFileWriter.write(line);
					maFileWriter.write(System.getProperty("line.separator"));
					line=br.readLine();
					//System.out.println("change val connection to:"+line);
				}else if(line.startsWith("dim"))
				{
					line=line.replaceFirst("17", Integer.toString(length));
					line=line.replaceFirst("17", Integer.toString(width));
					maFileWriter.write(line);
					line=br.readLine();
					maFileWriter.write(System.getProperty("line.separator"));
					}
				else if(line.startsWith("zone"))
				{
					line=line.replaceFirst("16", Integer.toString(length-1));
					line=line.replaceFirst("16", Integer.toString(width-1));
					maFileWriter.write(line);
					line=br.readLine();
					maFileWriter.write(System.getProperty("line.separator"));
					
				}else{
					maFileWriter.write(line);
					line=br.readLine();
					//maFileWriter.write("\n");
					maFileWriter.write(System.getProperty("line.separator"));
				}
			}
			
			maFileWriter.close();
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private String collectParameters(String strategy) {
		/*String Parameters= "-m\"" + "MaFileName" + "\"";
		Parameters=Parameters+ " "+ "-o\"" + "temp.out" + "\"";	
		Parameters=Parameters+ " "+ "-l\"" + "temp.log" + "\"";		
		Parameters=Parameters+ " "+ "-t\"" + "time 00:00:00:000" + "\"";*/
		
		String Parameters= "-m\"" + strategy+".ma" + "\"";
		Parameters=Parameters+ " "+ "-o\"" + outFile.getName() + "\"";	
		Parameters=Parameters+ " "+ "-l\"" + logFile.getName() + "\"";
		if(simulationTime!="infinity"){
			Parameters=Parameters+ " "+ "-t\"" + simulationTime + "\"";
		}
		else{
			Parameters=Parameters+ " "+ "-t\"" +  "\"";
		}
		
		return Parameters;	
	}
	


	
}