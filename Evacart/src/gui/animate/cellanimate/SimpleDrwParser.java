package gui.animate.cellanimate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * Created on 04/03/2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code Template
 */

/**
 * @author Pablo
 */
public class SimpleDrwParser extends DrwParser {

	private VTime nullTime = new VTime(0, 0, 0, 0);

	protected void readMatrixSize() throws InvalidDrwException {
		long filePosition = getFilePosition(0);
		int numberOfRows = 0;
		int numberOfCols = 0;
		try {

			this.randomFile.seek(filePosition);

			String s = this.randomFile.readLine();

			StringTokenizer colCounter = new StringTokenizer(s, " \t");
			numberOfCols = colCounter.countTokens();

			// Salteo la linea punteada

			while (s.length() != 0 && !s.startsWith("Time")) {
				numberOfRows++;

				s = this.randomFile.readLine();
			}

			setMatrixRows(numberOfRows);
			setMatrixCols(numberOfCols);

		}
		catch (IOException e) {
			throw new InvalidDrwException("Invalid Simple DRW");
		}
	}

	public void makeIndex() {
		ArrayList times = new ArrayList();
		ArrayList positions = new ArrayList();

		VTime vTime = null;
		Long position = null;

		openFile();

		try {
			String s = this.randomFile.readLine();
			while (s != null) {
				if (s.length() == 0 || s.startsWith("Time")) {

					position = new Long(this.randomFile.getFilePointer());
					positions.add(position);

				}

				s = this.randomFile.readLine();
			}
		}
		catch (IOException e) {
			throw new InvalidDrwException("Invalid Simple DRW");
		}

		setFilePositions(convertArrayListToLongArray(positions));

		readMatrixSize();
	}

	private long[] convertArrayListToLongArray(ArrayList anArrayList) {
		long[] array = new long[anArrayList.size()];

		for (int i = 0; i < array.length; i++) {
			array[i] = ((Long)anArrayList.get(i)).longValue();
		}

		return array;
	}

	/* (non-Javadoc)
	 * @see .DrwParser#getMatrix(int)
	 */
	public Matrix getMatrix(int index) {
		long filePosition = getFilePosition(index);
		double[][] matrixData = new double[getMatrixRows()][getMatrixCols()];
		int matrixRows = getMatrixRows();
		int matrixCols = getMatrixCols();
		try {

			this.randomFile.seek(filePosition);

			String s = null;
			StringTokenizer tokenizer = null;

			for (int i = 0; i < matrixRows; i++) {
				s = this.randomFile.readLine();

				tokenizer = new StringTokenizer(s, " \t");

				for (int j = 0; j < matrixCols; j++) {
					String token = tokenizer.nextToken();
					try {
						matrixData[i][j] = Float.parseFloat(token);
					}catch(Exception ex){
						matrixData[i][j] = Double.NaN;
					}
				}
			}

			return new SimpleMatrix(matrixData, (new File(getFileName())).getName());

		}
		catch (IOException e) {
			throw new InvalidDrwException("Invalid matrix on DRW file");
		}
	}

	public VTime getTime(int index) {
		return this.nullTime;
	}

	public int getIndexByTime(VTime time) {
		return 0;
	}

	public void clear()
	{
		this.setMatrixRows(0);
		this.setMatrixCols(0);
		this.setNumberWidth(1);
		this.setTimes(null);
		this.setFilePositions(null);
		this.close();
	}

	public boolean supportTime(){ return false; }
	
	public void setShow2DOnly(boolean show2DOnly) { }

}
