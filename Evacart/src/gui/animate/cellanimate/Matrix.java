package gui.animate.cellanimate;


public interface Matrix {

	public double getValue(int x, int y);
	
	//public void setData(double[][] data);
	
	public int getSizeX();
	
	public int getSizeY();
	
	public boolean contains(int x, int y);

	/**
	 * 
	 */
	public String getName(int x, int y);
	public void setName(String name);
}
