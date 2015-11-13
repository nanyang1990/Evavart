package gui.animate.cellanimate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;

class DrawLog
{
	IniFile ini = null;
	String[] allModelNames = null;
	
	private static class CellInfo {
		CellPosition pos = new CellPosition();
		double value = 0;
		String modelName = null;
	}

	public static int parseLine(String data, VTime current, String[] modelNames, CellInfo result)
	{
		if(data!=null && data.startsWith("Mensaje Y"))
		{
			StringTokenizer iter = new StringTokenizer(data);
			if(iter.countTokens()==12)
			{
				String[] tokens = new String[12];
				for(int i=0; iter.hasMoreTokens(); i++)
				{
					tokens[i] = iter.nextToken();
				}
				if("Mensaje".equals(tokens[0]) && "Y".equals(tokens[1]) && "out".equals(tokens[7]))
				{
					result.modelName = tokens[11].substring(0, tokens[11].indexOf('('));
					if(result.modelName.equals(tokens[5].substring(0, tokens[5].indexOf('('))))
					{
						for(int i=0; i<modelNames.length; i++)
						{
							if(result.modelName.equals(modelNames[i]))
							{
								result.pos.setValue(tokens[5]);
								current.setTime(tokens[3]);
								try {
									result.value = Double.parseDouble(tokens[9]);
								}catch(NumberFormatException ex){
									result.value = Double.NaN;
								}
								return i;
							}
						}
					}
				}
			}
		}
		return -1;
	}
	
	public boolean loadIniFile(String ini_filename)
	{
		int pos;
		
		// clear up
		ini = null;
		allModelNames = null;

		try {
			ini = new IniFile(ini_filename);

			allModelNames = ini.findAllCellComponents();
			if(allModelNames==null || allModelNames.length==0)
			{
				//System.out.println("No Cell-DEVS models defined in file "+ ini_filename);
				return false;
			}

		}catch(Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	public static boolean loadLogFile(LogDrwParser drawer)
	{
		LogDrwParser drawers[] = new LogDrwParser[1];
		drawers[0] = drawer;
		return loadLogFile(drawers);
	}
	
	public static boolean loadLogFile(LogDrwParser[] drawers)
	{
		int pos;
		String data = null;
		
		String modelNames[] = new String[drawers.length];
		String logFilename = drawers[0].getFileName();
		
		for(int i=0; i<drawers.length; i++)
		{
			modelNames[i] = drawers[i].modelName;
			if(!drawers[i].loadInitialValue())
			{
				System.err.println("Failed to load initial value of model "+drawers[i].toString());
				return false;
			}
		}
		
		VTime currentTime = new VTime(0,0,0,0);
		CellInfo info = new CellInfo();
		BufferedReader logfile = null;
		try 
		{
			logfile = new BufferedReader(new FileReader(logFilename));
			while((data = logfile.readLine())!=null)
			{
				if((pos=parseLine(data, currentTime, modelNames, info))>=0)
				{
					drawers[pos].setValue(currentTime, info.pos, info.value);
				}
			}
			logfile.close();

		}catch(FileNotFoundException e) {
			System.out.println("Can't open the log file " + logFilename);
			return false;
		}catch(Exception e){
			System.err.println("Read log file error: "+e.getMessage());
			return false;
		}
		return true;
	}
/*
	public void print()
	{
		for(int i=0; i<models.length; i++)
		{
			CellState state = new CellState(models[i].dim, 0);
			VTime time = new VTime(0,0,0,0);
			System.out.println("Model: " + models[i].modelName);
			for(int j=0; j<models[i].getMatrixCount(); j++)
			{
				models[i].get(j, time, state);
				System.out.println("Time: " + time.toString());
				System.out.println(state.print(10, 3));
				System.out.println(); 
			}
		}
	}
	
	public static void main(String argv[])
	{
		//String mafile = "corazona.ma";
		String mafile = "E:\\ftproot\\user2\\94587\\project\\cdmodler\\CellAnimate\\Traffic.ma";
		String log_filename = "traffic.log";

		DrawLog drw = new DrawLog();
		
		if(drw.loadIniFile(mafile))
		{
			if(drw.loadLogFile(log_filename))
			{
				drw.print();
			}
			
		}
	}
*/	

}
