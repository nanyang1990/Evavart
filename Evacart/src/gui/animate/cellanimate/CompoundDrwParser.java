/*
 * Created on 2003-11-23
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui.animate.cellanimate;

import java.util.Arrays;
import java.util.Vector;
/**
 * @author Jidong Cao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CompoundDrwParser extends DrwParser {

	private DrwParser[] drawers = null;
	private int matrixCount = 0;
	
	public CompoundDrwParser(DrwParser[] drawers)
	{
		this.drawers = new DrwParser[drawers.length];
		System.arraycopy(drawers, 0, this.drawers, 0, drawers.length);
	}

	public void makeIndex()
	{
		readMatrixSize();
		
		if(drawers.length==1) return;

		numberWidth = drawers[0].getNumberWidth();
		for(int i=1; i<drawers.length; i++)
		{
			if( numberWidth < drawers[i].getNumberWidth() )
			{
				numberWidth = drawers[i].getNumberWidth();
			}
		}
		
		if(!this.supportTime())
		{
			matrixCount = 0;
			for(int i=0; i<drawers.length; i++)
			{
				if( matrixCount < drawers[i].getMatrixCount() )
				{
					matrixCount = drawers[i].getMatrixCount();
				}
			}
		}
		else
		{
			Vector timeList = new Vector();
			for(int i=0; i<drawers.length; i++)
			{
				for(int j=0; j<drawers[i].getMatrixCount(); j++)
				{
					timeList.addElement(drawers[i].getTime(j));
				}
			}
		
			VTime temp[] = new VTime[timeList.size()];
			timeList.copyInto(temp);
			Arrays.sort(temp);
		
			matrixCount = 1;
			for(int i=1; i<temp.length; i++)
			{
				if(!temp[i].equals(temp[i-1])) matrixCount++;
			}
		
			this.times = new VTime[matrixCount];
			this.times[0] = temp[0];
			int count = 1;
			for(int i=1; i<temp.length; i++)
			{
				if(!temp[i].equals(temp[i-1]))
				{
					this.times[count++] = temp[i];
				}
			}
		}
	}

	public int getMatrixCount()
	{
		if(drawers.length==1)
		{
			return drawers[0].getMatrixCount();
		}
		
		return matrixCount;
	}

	protected void readMatrixSize()
	{
		int rows = 0;
		int cols = 0;
		
		for(int i=0; i<drawers.length; i++)
		{
			int row = drawers[i].getMatrixRows();
			int col = drawers[i].getMatrixCols();
			
			rows += row;
			cols = (col>cols) ? col : cols;
		}
		
		this.setMatrixRows(rows);
		this.setMatrixCols(cols);
	}

	public Matrix getMatrix(int index)
	{
		if(drawers.length==1)
		{
			return drawers[0].getMatrix(index);
		}
		
		Matrix items[] = new Matrix[drawers.length];

		VTime time = getTime(index);
		int idx;
		for(int k=0; k<drawers.length; k++)
		{
			if(!this.supportTime())
				idx = Math.min(index, drawers[k].getMatrixCount()-1);
			else
				idx = drawers[k].getIndexByTime(time);

			items[k] = drawers[k].getMatrix(idx);
		}
		
		return new CompoundMatrix(items);
		
		
		/*
		int matrixRows = getMatrixRows();
		int matrixCols = getMatrixCols();
		double[][] matrixData = new double[matrixRows][matrixCols];
		for(int i=0; i<matrixRows; i++)
		{
			Arrays.fill(matrixData[i], Double.NaN);
		}
		
		VTime time = getTime(index);
		int idx, currentRow = 0;
		for(int k=0; k<drawers.length; k++)
		{
			if(!this.supportTime())
				idx = Math.min(index, drawers[k].getMatrixCount()-1);
			else
				idx = drawers[k].getIndexByTime(time);

			Matrix matrix = drawers[k].getMatrix(idx);
			for(int i=0; i<matrix.getSizeX(); i++)
			{
				for(int j=0; j<matrix.getSizeY(); j++)
				{
					matrixData[currentRow][j] = matrix.getValue(i, j);
				}
				currentRow++;
			}
		}
		
		return new SimpleMatrix(matrixData);
		*/
	}

	public VTime getTime(int index)
	{
		if(drawers.length==1)
		{
			return drawers[0].getTime(index) ;
		}

		if(!this.supportTime())
			return drawers[0].getTime(index);
		else
			return this.times[index];
	}

	public int getIndexByTime(VTime time) 
	{
		if(drawers.length==1)
		{
			return drawers[0].getIndexByTime(time) ;
		}
		
		if(!this.supportTime())
			return 0;
			
		int temp = Arrays.binarySearch(this.times, time);
		if (temp <0)
			temp = Math.abs(temp)-2;

		return temp;
	}
	
	public String getFilename()
	{
		if(drawers!=null && drawers.length>0)
			return drawers[0].getFileName();
		
		return null;
	}
	
	public String toString()
	{
		if(drawers!=null && drawers.length>0)
			return drawers[0].toString();
			
		return "simu";
	}

	public void clear()
	{
		this.setMatrixRows(0);
		this.setMatrixCols(0);
		this.setNumberWidth(1);
		this.matrixCount = 0;
		this.setTimes(null);
		this.setFilePositions(null);
		for(int i=0; i<drawers.length; i++)
		{
			drawers[i].clear();
		}
	}

	public boolean supportTime()
	{
		if(drawers.length==1)
		{
			return drawers[0].supportTime() ;
		}
		else
		{
			for(int i=0; i<drawers.length; i++)
			{
				if(!drawers[i].supportTime()) return false;
			}
		
			return true;
		}
	}
	public void setShow2DOnly(boolean show2DOnly)
	{
		for(int i=0; i<drawers.length; i++)
		{
			drawers[i].setShow2DOnly(show2DOnly);
		}
	}
	
}
