package gui.animate.cellanimate;

public class CompoundMatrix implements Matrix {
	private Matrix items[] = null;
	private int matrixSize[] = { 0, 0 };
	
	private int index0[] = null;
	private int index1[] = null;

	public CompoundMatrix(Matrix matrixes[])
	{
		items = new Matrix[matrixes.length];
		System.arraycopy(matrixes, 0, items, 0, matrixes.length);
		
		for(int i=0; i<items.length; i++)
		{
			matrixSize[0] += items[i].getSizeX();
			matrixSize[1] = Math.max(matrixSize[1], items[i].getSizeY());
		}
		
		index0 = new int[matrixSize[0]];
		index1 = new int[matrixSize[0]];
		
		int count = 0;
		for(int i=0; i<items.length; i++)
		{
			for(int j=0; j<items[i].getSizeX(); j++)
			{
				index0[count] = i;
				index1[count] = j;
				count++;
			}
		}
	}
	public double getValue(int x, int y)
	{
		if(!this.contains(x, y))
			return Double.NaN;
		else
			return items[index0[x]].getValue(index1[x], y);
	}

	public int getSizeX() {	return matrixSize[0]; }

	public int getSizeY() { return matrixSize[1]; }

	public boolean contains(int x, int y)
	{
		if(x>=0 && x< matrixSize[0])
		{
			return items[index0[x]].contains(index1[x], y);
		}
		return false;
	}

	/**
	 * @return
	 */
	public String getName(int x, int y) {
		Matrix theMatrix = items[index0[x]];
		if(index1[x] == 0 && y == 0){
			return theMatrix.getName(0,0);
		}
		else{
			return null;
		} 
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		
	}

}
