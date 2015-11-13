
package gui.animate.cellanimate;

import java.text.DecimalFormat;

class CellState implements Matrix {
	private String name;
	private CellPosition dim = null;
	private boolean only2D = true; 
	private double[] matrix = null;

	public CellState(CellPosition dimension, double initialvalue)
	{
		int size = dimension.size();
		if(size==0)
			throw new IndexOutOfBoundsException("0-dimension Matrix");
		else if (size==1)
		{
			dim = new CellPosition(2);
			dim.setValue(0, 1);
			dim.setValue(1, dimension.getValue(0));
		}
		else
		{
			dim = new CellPosition(dimension);
		}
		
		size = 1;
		for(int i=0; i<dim.size(); i++)
		{
			size *= dim.getValue(i);
		}
		matrix = new double[size];
		for(int i=0; i<size; i++) {	matrix[i] = initialvalue; }
	}

	public CellState(CellState state)
	{
		dim = new CellPosition(state.dim);

		matrix = new double[state.matrix.length];
		System.arraycopy(state.matrix, 0, matrix, 0, matrix.length);
	}
	
	public void fill(double value)
	{
		for(int i=0; i<matrix.length; i++)
		{
			matrix[i] = value;
		}
	}

	public double getValue(CellPosition pos)
	{
		if(!dim.contains(pos))
			throw new IndexOutOfBoundsException(""+pos+" out of range "+dim);

		int index = CellPosition.posToIndex(dim, pos);
		return matrix[index];
	}
	
	public double getValue(int row, int col)
	{
		CellPosition pos = new CellPosition(dim.size());
		CellPosition new_dim = new CellPosition(dim);
		new_dim.setValue(0, 1);
		new_dim.reverse();
		
		CellPosition.indexToPos(new_dim, col, pos);
		pos.reverse();
		pos.setValue(0, row);
		return getValue(pos);
		
	}

	public boolean contains(int x, int y)
	{
		return (x>=0 && y>=0 && x<getSizeX() && y< getSizeY());	
	}
	
	public int getSizeX() {	return dim.getValue(0);	}
	
	public int getSizeY()
	{
		int sum = dim.getValue(1);
		if (!this.only2D)
		{
			for(int i=2; i<dim.size(); i++)
				sum *= dim.getValue(i);
		}
		return sum;
	}
	
	public void setValue(CellPosition pos, double value)
	{
		if(!dim.contains(pos))
			throw new IndexOutOfBoundsException(""+pos+" out of range "+dim);

		int index = CellPosition.posToIndex(dim, pos);
		matrix[index] = value;
	}
	
	public void set2DOnly(boolean only2D) { this.only2D = only2D; }
	
	public void setValue(CellState state)
	{
		dim = new CellPosition(state.dim);
		if(matrix!=null && matrix.length!=state.matrix.length)
		{
			matrix = new double[state.matrix.length];
		}
		System.arraycopy(state.matrix, 0, matrix, 0, matrix.length);
	}
	
	public CellPosition dimension()
	{
		return new CellPosition(dim);
	}
	
	private String format(String str, int width)
	{
		if(str!=null && str.length()>=width) return str;
		boolean rightAlign = true;
		if(width<0)
		{
			rightAlign = false;
			width = -width;
		}
		StringBuffer buf = new StringBuffer(str);
		for(int i=buf.length(); i<width; i++)
		{
			if(rightAlign)
				buf.insert(0, ' ');
			else
				buf.append(' ');
		}
		return buf.toString();
	}
	public String print(int width, int precise)
	{
		int i, j, k;
		int size = dim.size();
		StringBuffer buf = new StringBuffer();
		CellPosition pos = new CellPosition(dim.size());

		if(size>3)
		{
			for(i=0; i<matrix.length; i++)
			{
				CellPosition.indexToPos(dim, i, pos);
				buf.append(' ').append(pos.toString()).append(" = ").append(matrix[i]).append('\n');
			}
			return buf.toString();
		}

		if(precise>=width-2) precise = width-2;
		
		StringBuffer number_pattern = new StringBuffer("0");
		if(precise>0)
		{
			number_pattern.append('.');
			for(i=0; i<precise; i++) number_pattern.append('0');
		}

		DecimalFormat formater = new DecimalFormat(number_pattern.toString());
		
		StringBuffer column_no = new StringBuffer("     ");
		StringBuffer seperator = new StringBuffer("    +");
		for(i=0; i<dim.getValue(1); i++)
		{
			column_no.append(format(""+i, width));
			for(j=0; j<width; j++) seperator.append('-');
		}
		column_no.append(' ');
		seperator.append('+');
		
		int loop = (size==3 ? dim.getValue(2): 1);
		
		for(k=0; k<loop; k++) buf.append(column_no.toString());
		buf.append('\n');
		
		for(k=0; k<loop; k++) buf.append(seperator.toString());
		buf.append('\n');

			
		for(i=0; i<dim.getValue(0); i++)
		{
			for(k=0; k<loop; k++)
			{
				if(size==3)	pos.setValue(2, k);
			
				buf.append(format(""+i, 4));
				buf.append('|');
				pos.setValue(0, i);
				
				for(j=0; j<dim.getValue(1); j++)
				{
					pos.setValue(1, j);
					double value = this.getValue(pos);
					if(Double.isNaN(value))
						buf.append(format("?", width));
					else
						buf.append(format(formater.format(value), width));
				}
				buf.append('|');
			}
			buf.append('\n');
		}
			
		for(k=0; k<loop; k++) buf.append(seperator.toString());
		buf.append('\n');
		
		return buf.toString();
	}


	public static void main(String[] args) 
	{
		CellPosition dim = new CellPosition("(5, 5, 4)");
		CellState state = new CellState(dim, Double.NaN);
		System.out.println(state.print(10,3));
	}
	
	
	/**
	 * @return
	 */
	public String getName(int x, int y) {
		if(x == 0 && y == 0){
			return name;
		}
		else{
			return null;
		}
		
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

}
