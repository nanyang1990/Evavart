package gui.animate.cellanimate;

class CellPosition 
{
	private int[] coordinates = null;
	private static int[] temp_data = new int[10];

	private void makeSureCapacity(int len)
	{
		if(len>temp_data.length)
		{
			int new_len = temp_data.length+10;
			while(new_len < len) new_len += 10;
			int[] new_temp_data = new int[new_len];
			System.arraycopy(temp_data, 0, new_temp_data, 0, temp_data.length);
			temp_data = new_temp_data;
		}
	}
	public static int posToIndex(CellPosition dim, CellPosition pos)
	{
		if(dim.size()!=pos.size())
			throw new RuntimeException("Dimension of two CellPosition are not same!");

		if(!dim.contains(pos))
			throw new RuntimeException("Target CellPosition is out of range!");

		int[] weight = new int[dim.size()];
		weight[weight.length-1] = 1;
		for(int i=weight.length-2; i>=0; i--)
		{
			weight[i] = weight[i+1] * dim.getValue(i+1);
		}

		int index = 0;
		for(int i=0; i<weight.length; i++)
		{
			index += weight[i] * pos.getValue(i);
		}
		return index;
	}
	public static boolean indexToPos(CellPosition dim, int index, CellPosition pos)
	{
		if(dim==null || pos==null || index<0 || dim.size()!=pos.size())
			return false;

		int[] weight = new int[dim.size()];
		weight[weight.length-1] = 1;
		for(int i=weight.length-2; i>=0; i--)
		{
			weight[i] = weight[i+1] * dim.getValue(i+1);
		}
		for(int i=0; i<weight.length; i++)
		{
			pos.setValue(i, (int)(index/weight[i]));
			index %= weight[i];
		}
		if(pos.getValue(0)>=dim.getValue(0))
			return false;
			
		return true;
	}

	public CellPosition()
	{
		this(2);
	}
	public CellPosition(int[] data)
	{
		coordinates = new int[data.length];
		System.arraycopy(data, 0, coordinates, 0, data.length);
	}

	public CellPosition(int dim)
	{
		coordinates = new int[dim];
	}

	public CellPosition(CellPosition pos)
	{
		coordinates = new int[pos.coordinates.length];
		System.arraycopy(pos.coordinates, 0, coordinates, 0, coordinates.length);
	}
	
	public CellPosition(CellPosition dim, int initialvalue)
	{
		coordinates = new int[dim.coordinates.length];
		CellPosition.indexToPos(dim, initialvalue, this);
	}

	public CellPosition(String str)
	{
		try 
		{
			this.setValue(str);
			
		}
		catch(Exception e)
		{
			coordinates = new int[1];
		}
	}

	public int size() { return coordinates.length; }

	public int getValue(int index) { return coordinates[index]; }

	public void setValue(int index, int n) { coordinates[index] = n; }
	
	public boolean next(CellPosition dim)
	{
		if(dim.contains(this))
		{
			int index = CellPosition.posToIndex(dim, this);
			CellPosition.indexToPos(dim, index+1, this);
			if(dim.contains(this))
				return false;
		}
		return true;
	}
	
	public void reverse()
	{
		int temp;
		int size = coordinates.length;
		for(int i=0; i< size/2; i++)
		{
			temp = coordinates[i];
			coordinates[i] = coordinates[size-1-i];
			coordinates[size-1-i] = temp;
		}
	}

	public void setValue(String str) throws NumberFormatException
	{
		boolean valid = false;
		int begin = str.indexOf('(');
		if(begin>=0)
		{
			int end = str.indexOf(')', begin+1);
			if(end>0)
			{
				boolean in_digit = false;
				int count = 0;
				int sum = 0;
				valid = true;

				for(int i=begin+1; i<end; i++)
				{
					char ch = str.charAt(i);
					if(ch==' ' || ch=='\t' || ch==',')
					{
						if(in_digit)
						{
							makeSureCapacity(count+1);
							temp_data[count++] = sum;
							in_digit = false;
							sum = 0;
						}
						continue;
					}
					else if(ch>='0' && ch<='9')
					{
						in_digit = true;
						sum = sum * 10 + (ch -'0');
					}
					else
					{
						valid = false;
						break;
					}
				}
				if(valid)
				{
					if(in_digit)
					{
						makeSureCapacity(count+1);
						temp_data[count++] = sum;
						in_digit = false;
					}
					if(coordinates==null || count!=coordinates.length)
					{
						coordinates = new int[count];
					}
					System.arraycopy(temp_data, 0, coordinates, 0, count);
				}
			}
		}
		if(!valid) throw new NumberFormatException();
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append('(');
		for(int i=0; i<size(); i++)
		{
			if(i>0)	buf.append(',');
			buf.append(coordinates[i]);
		}
		buf.append(')');
		return buf.toString();
	}

	public boolean equals(Object obj)
	{
		if(obj!=null && obj instanceof CellPosition)
		{
			CellPosition pos = (CellPosition)obj;
			if(size()==pos.size())
			{
				boolean same = true;
				for(int i=0; i<size(); i++)
				{
					if(coordinates[i]!=pos.coordinates[i])
					{
						same = false;
						break;
					}
				}
				return same;
			}

		}
		return false;
	}

	public boolean contains(CellPosition pos)
	{
		if(pos!=null && pos.size()==this.size())
		{
			for(int i=0; i<size(); i++)
			{
				if(pos.coordinates[i]>=0 && pos.coordinates[i]<coordinates[i])
				{
					continue;
				}
				return false;
			}
			return true;
		}
		return false;
	}


	public static void main(String[] args) 
	{

		CellPosition cs = new CellPosition(3);

		System.out.println("size = "+cs.size()+ " " + cs);
		
		cs.setValue("tager( 0, 11, 22, 3333 )(0,0)");
		System.out.println("size = "+cs.size()+ " " + cs);
		
		CellPosition cs2 = new CellPosition(cs);

		System.out.println("size = "+cs2.size()+ " " + cs2 + cs.equals(cs2));
	}
}
