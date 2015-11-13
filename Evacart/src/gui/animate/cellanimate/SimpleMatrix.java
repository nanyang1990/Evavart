package gui.animate.cellanimate;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SimpleMatrix implements Matrix {

	private String name;

	private int matrixSize[] = { 0, 0 };

	private double data[][] = null;

	SimpleMatrix(double[][] data, String name) {
		this.data = data;
		this.matrixSize[0] = data.length;
		if (data.length > 0) {
			this.matrixSize[1] = data[0].length;
		}
		else {
			this.matrixSize[1] = 0;
		}
		this.setName(name);
	}

	public boolean contains(int x, int y) {
		return (x >= 0 && x < matrixSize[0] && y >= 0 && y < matrixSize[1]);
	}

	public double getValue(int x, int y) {
		if (x > matrixSize[0] || y > matrixSize[1])
			throw new ArrayIndexOutOfBoundsException("Matrix smaller than indexes");

		return this.data[x][y];
	}

	public void setData(double[][] data) {
		this.data = data;
		this.matrixSize[0] = data.length;
		this.matrixSize[1] = data[0].length;
	}

	public int getSizeX() {
		return this.matrixSize[0];
	}

	public int getSizeY() {
		return this.matrixSize[1];
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		StringWriter sw = new StringWriter();

		PrintWriter pw = new PrintWriter(sw);

		pw.println("Rows: " + getSizeX());
		pw.println("Cols: " + getSizeY());

		for (int i = 0, sizeX = getSizeX(); i < sizeX; i++) {
			pw.print(i + "\t");
			for (int j = 0, sizeY = getSizeY(); j < sizeY; j++) {
				pw.print(getValue(i, j));
				pw.print(" ");
			}
			pw.println();
		}

		return sw.toString();
	}

	/**
	 * @return
	 */
	public String getName(int x, int y) {
		if (x == 0 && y == 0) {
			return name;
		}
		else {
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
