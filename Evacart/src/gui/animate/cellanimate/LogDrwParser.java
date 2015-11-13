package gui.animate.cellanimate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;

public class LogDrwParser extends DrwParser {

	String modelName = null;
	IniFile ini = null;
	
	CellPosition dim = null;
	int rows, cols;
	
	VTime currentTime = null;
	CellState currentState = null;
		
	Vector timeList = new Vector();
	Vector stateList = new Vector();
	
	boolean show2DOnly = true; 
	
	public LogDrwParser(IniFile ini, String modelName, String logFilename)
	{
		this.modelName = modelName;
		this.ini = ini;
		this.fileName = logFilename;
		
	}

	public int getMatrixCount() { return this.times.length; }

	public void setNumberWidth(int w)
	{
		if(w>numberWidth) numberWidth = w;
	}
		
	public void get(int index, VTime time, CellState state)
	{
		time.setTime((VTime) timeList.elementAt(index));
		state.setValue((CellState)stateList.elementAt(index));
	}
		
	
	void setValue(VTime time, CellPosition pos, double value)
	{
		int res = currentTime.compareTo(time);
		if(res>0)
		{
			System.err.println("non-increment timestamp found: "+time.toString());
			return;
		}
		else if(res<0)
		{
			currentState = new CellState(currentState);
			stateList.add(currentState);
			
			currentTime = new VTime(time.toString());
			timeList.add(currentTime);
		}
		currentState.setValue(pos, value);
		if(!Double.isNaN(value))
			this.setNumberWidth(Double.toString(value).length());
	}
		

	public void clear()
	{
		dim = null;
		rows = 0; cols = 0; numberWidth = 1;
		timeList.removeAllElements();
		stateList.removeAllElements();
		currentTime = null;
		currentState = null;
		this.setTimes(null);
		this.show2DOnly = true; 
	}
	
	public boolean loadInitialValue()
	{			
		Vector v = null;
		CellPosition nt = null;
		int pos;
		double value;
		String data = null;


		clear();

		String iniPath = ini.GetFileName(); 
		pos = iniPath.lastIndexOf(File.separatorChar);
		if(pos>=0)
			iniPath = iniPath.substring(0, pos+1);
		else
			iniPath = "";
		
		if((v=ini.get(modelName, "width"))!=null)
		{
			cols = Integer.parseInt( (String)v.elementAt(0));
			v = ini.get(modelName, "height");
			rows = Integer.parseInt((String)v.elementAt(0));
			dim = new CellPosition(2);
			dim.setValue(0, rows);
			dim.setValue(1, cols);
		}
		else
		{
			if((v=ini.get(modelName, "dim"))!=null)
			{
				dim = new CellPosition((String)v.elementAt(0));
				rows = dim.getValue(0);
				cols = dim.getValue(1);
			}
		}
		if(dim==null)
		{
			System.err.println("cannot find dimension of cell model "+modelName);
			return false;
		}
		
		//System.out.println(modelName+" dimension = "+dim.toString());
		nt = new CellPosition(dim.size());
					
					
		value = Double.NaN;
		if((v=ini.get(modelName, "initialvalue"))!=null)
		{
			value = Double.parseDouble((String)v.elementAt(0));
			this.setNumberWidth(Double.toString(value).length());
		}
		currentState = new CellState(dim, value);

					
		if((v=ini.get(modelName, "initialrow"))!=null)
		{
			for(int j=0; j<v.size(); j++)
			{
				data = (String)v.elementAt(j);
				pos = data.indexOf(' ');
				int row = Integer.parseInt(data.substring(0, pos));
				if(row>=rows)
				{
					System.err.println(	"The number of row for initialRow is out of range. It's " + row + " and must be in [ 0, " + (rows-1) + "]" );
					return false;
				}
				nt.setValue(0, row);
				StringTokenizer tokens = new StringTokenizer(data.substring(pos+1));
				if(tokens.countTokens()<cols)
				{
					System.err.println("Insuficient data for initialRow. Last row with " + 
						tokens.countTokens() + " elements. Note: May be a middle row definition with less elements.");
					return false;
				}
				for(int col = 0; col<cols; col++)
				{
					try {
						value = Double.parseDouble(tokens.nextToken());
					}catch(Exception ex){
						value = Double.NaN;
					}
					nt.setValue(1, col);
					currentState.setValue(nt, value);
					this.setNumberWidth(Double.toString(value).length());
				}
			}
		}
					

		if((v=ini.get(modelName, "initialRowValue"))!=null)
		{
			for(int j=0; j<v.size(); j++)
			{
				data = (String)v.elementAt(j);
				pos = data.indexOf(' ');
				int row = Integer.parseInt(data.substring(0, pos));
				if(row>=rows)
				{
					System.err.println(	"The number of row for initialRow is out of range. It's " + row + " and must be in [ 0, " + (rows-1) + "]" );
					return false;
				}
				data = data.substring(pos+1).trim();
				if(data.length()==0)
				{
					System.err.println("Invalid initial row value for initialRowValue (must be a pair rowNumber rowValues)!");
					return false;
				}

				nt.setValue(0, row);
				if(data.length() != cols)
				{
					System.err.println("The size of the rows for the initial values of the CoupledCell must be equal to the width value !" );
					return false;
				}
				for(int col=0; col<cols; col++)
				{
					char ch = data.charAt(col);
					value = (ch>='0' && ch<='9')? ch-'0' : Double.NaN; 
					nt.setValue(1, col);
					currentState.setValue(nt, value);
				}
			}
		}
					
					
		if((v=ini.get(modelName, "initialCellsValue"))!=null)
		{
			String filename = (String)v.elementAt(0);
			if(filename.length()>0)
			{
				try {
					BufferedReader reader = new BufferedReader(new FileReader(iniPath+filename));
							
					while((data=reader.readLine())!=null)
					{
						if(data.trim().length()>0)
						{
							if((pos = data.indexOf('=')) > 0)
							{
								nt.setValue(data.substring(0, pos));
								value = Double.NaN;
								try{
									value = Double.parseDouble(data.substring(pos+1).trim());
									this.setNumberWidth(Double.toString(value).length());
								}catch(NumberFormatException e){}
										
								currentState.setValue(nt, value);
							}
						}
					}
					reader.close();
							
				}catch(FileNotFoundException e){
					System.err.println("Can't open the file '" + filename + "' defined by the initialCellsValue clause");
					return false;							
				}catch(IOException e) {
					System.err.println(e.getMessage());
					return false;
				}
			}
		}

		if((v=ini.get(modelName, "initialMapValue"))!=null)
		{
			String filename = (String)v.elementAt(0);
			if(filename.length()>0)
			{
				try {
					BufferedReader reader = new BufferedReader(new FileReader(iniPath+filename));
					CellPosition.indexToPos(dim, 0, nt);
					boolean overflow = false;
					while((data=reader.readLine())!=null && !overflow)
					{
						data = data.trim();
						if(data.length()>0)
						{
							try{
								value = Double.parseDouble(data);
							}catch(NumberFormatException e){
								value = Double.NaN;
							}
							currentState.setValue(nt, value);
							overflow = nt.next(dim);
						}
					}
					reader.close();
							
				}catch(FileNotFoundException e){
					System.err.println("Can't open the file '" + filename + "' defined by the initialCellsValue clause");
					return false;							
				}catch(IOException e) {
					System.err.println(e.getMessage());
					return false;
				}
			}
		}
		
		currentTime = new VTime(0,0,0,0);
		
		timeList.addElement(currentTime);
		stateList.addElement(currentState);

		return true;
			
	}
	public String toString()
	{
		return modelName+'@'+super.toString();
	}
		
	public void print(int width, int precise)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("Model : ").append(modelName);
		int size = timeList.size();
		for(int i=0; i<size; i++)
		{
			buf.append("Time: ").append(timeList.elementAt(i).toString()).append('\n');
			buf.append( ((CellState)stateList.elementAt(i)).print(width, precise));
			buf.append('\n');
		}
		System.out.println(buf.toString());
		//return buf.toString();
	}
	
	public void makeIndex()
	{
		this.setTimes((VTime[]) timeList.toArray(new VTime[timeList.size()]));
		
		this.readMatrixSize();
	}
	
	protected void readMatrixSize() {
		setMatrixRows(rows);
		
		if(show2DOnly)
		{
			setMatrixCols(cols);
		}
		else
		{
			int column_num = 1;
			for(int i=1; i<dim.size(); i++)
			{
				column_num *= dim.getValue(i);
			}
			setMatrixCols(column_num);
		}
	}

	public Matrix getMatrix(int index) {
		return (CellState) stateList.elementAt(index);
	}

	public VTime getTime(int index)
	{
		return this.times[index];
	}

	public int getIndexByTime(VTime time)
	{
		int temp = Arrays.binarySearch(this.times, time);
		if (temp <0)
		{
		  temp = Math.abs(temp)-2;
		}
		return temp;
	}
	public boolean supportTime(){ return true; }

	public void setShow2DOnly(boolean show2DOnly)
	{
		if(this.show2DOnly != show2DOnly)
		{
			this.show2DOnly = show2DOnly;
			for(int i=0; i<stateList.size(); i++)
			{
				((CellState)stateList.elementAt(i)).set2DOnly(show2DOnly);
			}
		}
	}

}
