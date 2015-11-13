package gui.animate.cellanimate;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.table.AbstractTableModel;


/**
 * Implements the Table Model for the PaletteTable
 */
public class PaletteTableModel extends AbstractTableModel {
  Object[][] data;
  String[] names;

  public PaletteTableModel() {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("[Your Icon]")));
    // Tabla
    names = new String[3];
    names[0] = "From";
    names[1] = "To";
    names[2] = "Color";
    data = new Object[1][3];
    data[0][0] = new Double(0.0);
    data[0][1] = new Double(1.0);
    data[0][2] = new Color(0.0f,0.0f,0.0f);
    };

    public int getColumnCount() { return names.length; }
    public int getRowCount() { return data.length;}
    public Object getValueAt(int row, int col) {return data[row][col];}
    public String getColumnName(int column) {return names[column];}
    public Class getColumnClass(int c) {return getValueAt(0, c).getClass();}
    public boolean isCellEditable(int row, int col) {
      if (col == 2) return false;
      else return true;
      }
    public void setValueAt(Object aValue, int row, int column) {
        data[row][column] = aValue;
    }
  public void addLine()
    {
    Object[][] temp = new Object[data.length+1][names.length];
    for (int i=0; i<data.length; i++)
      {
      temp[i] = data[i];
      }
    temp[data.length][0] = new Double(0.0);
    temp[data.length][1] = new Double(0.0);
    temp[data.length][2] = Color.white;
    data = temp;
    fireTableRowsInserted(data.length-1, data.length-1);
    }

  public void removeLine(int index)
    {
    Object[][] temp = new Object[data.length-1][names.length];
    for (int i=0; i<data.length; i++)
      {
      if (i<index)
        {
        temp[i] = data[i];
        }
      if (i>index)
        {
        temp[i-1] = data[i];
        }
      }
    data = temp;
    fireTableRowsDeleted(index, index);
    }

  public void setColor(int index, Color c)
    {
    data[index][2] = c;
    fireTableRowsUpdated(index, index);
    }

  public void setTexture(int index, String t)
    {
    data[index][2] = t;
    fireTableRowsUpdated(index, index);
    }

  public void saveData(String fileName) {
    String dataToSave = new String();
    dataToSave = "";
    for (int i=0; i<data.length; i++)
    {
      // [
      dataToSave = dataToSave.concat("[");
      // from
      dataToSave = dataToSave.concat(data[i][0].toString());
      // ;
      dataToSave = dataToSave.concat(";");
      // to
      dataToSave = dataToSave.concat(data[i][1].toString());
      // ]
      dataToSave = dataToSave.concat("]");
      // blank
      dataToSave = dataToSave.concat(" ");
      // get the color from data. If is not a color but a string, do the else clause
      Color colorValue = ( data[i][2] instanceof Color) ? ((Color)data[i][2]) :null;
      if (colorValue != null)
      {
        //red
        dataToSave = dataToSave.concat(new Integer(colorValue.getRed()).toString());
        //blank
        dataToSave = dataToSave.concat(" ");
        //green
        dataToSave = dataToSave.concat(new Integer(colorValue.getGreen()).toString());
        //blank
        dataToSave = dataToSave.concat(" ");
        //blue
        dataToSave = dataToSave.concat(new Integer(colorValue.getBlue()).toString());
      }
      else
      {
        String textureValue = ( data[i][2] instanceof String) ? ((String)data[i][2]) :null;
        dataToSave = dataToSave.concat(textureValue);
      }
      //endline
      dataToSave = dataToSave.concat("\r\n");
    }
    try
    {
      // Open a file of the current name.
      File file = new File (fileName);
      // Create an output writer that will write to that file.
      // FileWriter handles international characters encoding conversions.
      FileWriter out = new FileWriter(file);
      out.write(dataToSave);
      out.close();

    }
    catch (IOException e) {
    }

  }

  public boolean loadData(String fileName) {

  boolean ok_flag = true;
  int numLine = 0;
  try
  {
    //Open the file
    File file = new File (fileName);
    int size = (int)file.length();
    int charactersRead = 0;

    //FileReader to read the file
    FileReader in = new FileReader(file);
    char[] dataToLoad = new char[size];

    while(in.ready()) {
      charactersRead += in.read(dataToLoad, charactersRead, size - charactersRead);
    }
    in.close();

    int numLines = 0;
    int i=0;
    while(i < size)
      {
      if (dataToLoad[i] == '[')
        numLines++;
      i++;
      }

	if (numLines > 0) // standard file format
	{

	    Object[][] data2 = new Object[numLines][3];
	
	    try
	    {
	      i=0;
	      int ini = 0;
	      while (i<size)
	      {
	      // [
	        i++;
	
	        //first double
	        ini = i;
	        while (dataToLoad[i] != ';' && i < size)
	          i++;
	        String startInterval = new String(dataToLoad,ini,i-ini);
	        double fromRead = Double.parseDouble(startInterval.trim());
	        data2[numLine][0] = new Double(fromRead);
	        i++;  // ;
	
	        ini = i;
	        while (dataToLoad[i] != ']' && i < size)
	          i++;
	        String endInterval = new String(dataToLoad,ini,i-ini);
	        double toRead = Double.parseDouble(endInterval.trim());
	        data2[numLine][1] = new Double(toRead);
	        i++;  // ]
	        i++;  //blank
	
	        ini = i;
	        // try to read it as a color (r g b) If it can't, read it as a string
	        try {
	          while (dataToLoad[i] != '\u0020' && i < size)
	            i++;
	          String firstNumber = new String(dataToLoad,ini,i-ini);
	          int redRead = Integer.parseInt(firstNumber.trim());
	          i++;  //blank
	
	          ini = i;
	          while (dataToLoad[i] != '\u0020' && i < size)
	            i++;
	          String secondNumber = new String(dataToLoad,ini,i-ini);
	          int greenRead = Integer.parseInt(secondNumber.trim());
	          i++;  //blank
	
	          ini = i;
	          while (dataToLoad[i] != '\n' && i < size)
	            i++;
	          String thirdNumber = new String(dataToLoad,ini,i-ini);
	          int blueRead = Integer.parseInt(thirdNumber.trim());
	
	          data2[numLine][2] = new Color(redRead,greenRead,blueRead);
	        }
	        catch (NumberFormatException nfe)
	        {
	          i = ini;
	          while (dataToLoad[i] != '\n' && i < size)
	            i++;
	          String fileRead = new String(dataToLoad,ini,i-ini-1);
	          data2[numLine][2] = fileRead;
	        }
	        i++;  //endline
	        numLine++;
	      }
	      data = data2;
	      fireTableRowsInserted(0, numLine);
	    }
	    catch (Exception ex)
	    {
	      ok_flag = false;
	    }
	  }
    else // applet file format
    {
	    i = 0;
	    while(i < size)
    	{
      		if (dataToLoad[i] == '\n')
        		numLines++;
      		i++;
      	}
		numLines = (numLines - 1) / 2;

	    Object[][] data2 = new Object[numLines][3];

		String s = new String(dataToLoad);
		StringTokenizer tokenizer = null;
		tokenizer = new StringTokenizer(s, ",\n\r\f");
		String token = tokenizer.nextToken();
	
		if (token.equals("VALIDSAVEDFILE"))
		{
			int r,g,b;
			numLine = 0;
			while (numLine < numLines) // read all the colors;
			{
				r = Integer.parseInt(tokenizer.nextToken());
				g = Integer.parseInt(tokenizer.nextToken());
				b = Integer.parseInt(tokenizer.nextToken());
				data2[numLine][2] = new Color(r,g,b);
				numLine++;
			}
			numLine = 0;
			Double start,end;
			while (numLine < numLines) // read all the intervals;
			{
				start = new Double(Double.parseDouble(tokenizer.nextToken()));
				end = new Double(Double.parseDouble(tokenizer.nextToken()));
				data2[numLine][0] = start;
				data2[numLine][1] = end;
				numLine++;
			}
			data = data2;
			fireTableRowsInserted(0, numLine);
		}
	}
  }
  catch (IOException e)
  {
    ok_flag = false;
  }
    return ok_flag;
  }

  protected void setData(Object[][] inputData)
  {
    data = inputData;
  }

  protected  Object[][] getData()
  {
    return (data);
  }
}
