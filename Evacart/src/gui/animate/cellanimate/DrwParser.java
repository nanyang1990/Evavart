package gui.animate.cellanimate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * Created on 03/03/2003
 */

/**
 * @author Pablo
 */
public abstract class DrwParser {

	/**
	 * Stores the file name being parsed
	 */
	protected String fileName = null;

	/**
	 * Store the positions in the file associated with each matrix
	 */
	private long[] filePositions = null;

	/**
	 * Stores the times associated with each matrix
	 */
	protected VTime[] times = null;

	/**
	 * Stores the number of rows in the matrix
	 */
	private int matrixRows = 0;

	/**
	 * Stores the number of columns in the matrix
	 */
	private int matrixCols = 0;
	
	/**
	 * 
	 */
	protected int numberWidth;
	
	/**
	 * Factory method. Creates a new parser. Checks if the file is a BorderDRW or a SimpleDRW, and creates the appropiate parser
	 * @param fileName File name to parse
	 * @return DrwParser Parser
	 * @throws FileNotFoundException
	 */
	public static DrwParser getParser(String fileName) throws FileNotFoundException {
		RandomAccessFile randomFile;
		try {
			randomFile = new RandomAccessFile(fileName, "r");
		
			DrwParser parser = null;
			
			String s = randomFile.readLine();
			if (s.length() == 0) {
				parser = new SimpleDrwParser();
			} else if(s.startsWith("Time")){
				parser = new SimpleDrwParser();
			}else{
				parser = new BorderDrwParser();
			}
	
			parser.fileName = fileName;
			
			return parser;

		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw new InvalidDrwException("Cannot read from DRW file");
		}
	}

	public abstract void makeIndex();
	
	public abstract void clear();
	
	public abstract boolean supportTime();
	
	public abstract void setShow2DOnly(boolean show2DOnly);

	protected abstract void readMatrixSize();

	public abstract Matrix getMatrix(int index);

	/**
	 * Returns the VTime associated to a given matrix in a DRW file
	 * @param index The index of the matrix in the DRW file
	 * @return VTime associated to a given matrix in a DRW file
	 */
	public abstract VTime getTime(int index);

	/**
	 * Returns a matrix based on its VTime
	 * @param time The time to search for
	 * @return int Index of the matrix on the DRW file
	 */
	public abstract int getIndexByTime(VTime time); 

	public int getMatrixCount() {
		return this.filePositions.length;
	}

	public DrwIterator getIterator() {
		return new DrwIterator(this);
	}

	/**
	 * Returns the file name opened by this parser
	 * @return String File name opened by this parser
	 */
	protected String getFileName() {
		return fileName;
	}

	/**
	 * Sets the VTime associated with each matrix on the file
	 * @param times Array with the VTime associated with each matrix on the DRW file
	 */
	protected void setTimes(VTime[] times) {
		this.times = times;
	}

	/**
	 * Sets the file positions of each matrix in the DRW file
	 * @param filePositions Array with the positions of each matrix on the file
	 */
	protected void setFilePositions(long[] filePositions) {
		this.filePositions = filePositions;
	}

	/**
	 * Returns the position in the file of a given matrix, in order to do a file seek
	 * @param index The index of the matrix in the DRW file
	 * @return long the position in the file of a given matrix, in order to do a file seek
	 */
	public long getFilePosition(int index) {
			return this.filePositions[index];
	}

	/**
	 * @return int
	 */
	public int getMatrixCols() {
		return matrixCols;
	}

	/**
	 * @return int
	 */
	public int getMatrixRows() {
		return matrixRows;
	}

	/**
	 * Sets the matrixCols.
	 * @param matrixCols The matrixCols to set
	 */
	public void setMatrixCols(int matrixCols) {
		this.matrixCols = matrixCols;
	}

	/**
	 * Sets the matrixRows.
	 * @param matrixRows The matrixRows to set
	 */
	public void setMatrixRows(int matrixRows) {
		this.matrixRows = matrixRows;
	}

	protected RandomAccessFile randomFile = null;

	protected void openFile() {
		close();
		
		File aFile = new File(getFileName());
	
		try {
			this.randomFile = new RandomAccessFile(aFile, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.randomFile = null;
		}
	}

	public void close() {
		if (this.randomFile != null) {
			try {
				this.randomFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			DrwParser parser = DrwParser.getParser("ejemplos/simple.drw");
			parser.makeIndex();
			System.out.println("Cantidad de matrices: " +parser.getMatrixCount());
			System.out.println(parser.getMatrix(0).toString());
			DrwIterator it = parser.getIterator();
			
			while (it.hasNext()) {
				System.out.println(it.getCurrentTime());
				it.next();
			}
			
			System.out.println(parser.getIndexByTime(new VTime("0:2:58:0")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @return
	 */
	public int getNumberWidth() {
		return numberWidth;
	}

	/**
	 * @param i
	 */
	public void setNumberWidth(int i) {
		numberWidth = i;
	}

	public String toString()
	{
		String logname = fileName;
		int pos = fileName.lastIndexOf(File.separatorChar);
		if(pos>=0) logname = fileName.substring(pos+1);
		//pos = logname.lastIndexOf('.');
		//if(pos>=0) logname = logname.substring(0, pos);
	
		return logname;
	}
	
	public boolean equals(Object obj)
	{
		if(!this.getClass().equals(obj.getClass()))
			return false;
		return this.toString().equals(obj.toString());
	}
}
