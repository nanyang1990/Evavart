package gui.animate.cellanimate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class BorderDrwParser extends DrwParser {
	
	private boolean show2DOnly = true;
	private int sizeOf3rdDim = 1;
	

	protected void readMatrixSize() throws InvalidDrwException {
		try {
			//reset the file
			this.randomFile.seek(0);
			//skip the firs line
			this.randomFile.readLine();
			
			//get the numbers
			String numbers = this.randomFile.readLine();
			int numberOfCols = 0;
			
			//get the first number
			int ceroPosition = numbers.indexOf("0");
			int onePosition = numbers.indexOf("1");
			setNumberWidth(onePosition-ceroPosition );
			if( getNumberWidth() == 1){
				StringTokenizer counter = new StringTokenizer(numbers.trim());
				numberOfCols = 0;
				while(counter.hasMoreTokens())
				{
					numberOfCols += counter.nextToken().toString().trim().length();
				}
			}
			else{
				StringTokenizer counter = new StringTokenizer(numbers.trim());
				numberOfCols = counter.countTokens();
				
			}
			
			long filePosition = getFilePosition(0);
			int numberOfRows = 0;
			
			this.randomFile.seek(filePosition);

			String s = this.randomFile.readLine();

			
			// Salteo la linea punteada
			s = this.randomFile.readLine();
			sizeOf3rdDim = 0;
			int pos = 0;
			while( (pos = s.indexOf("+--", pos)) != -1)
			{
				sizeOf3rdDim++;
				pos += 3;
			}

			s = this.randomFile.readLine();
			while (s.indexOf("+--") == -1) {
				numberOfRows++;

				s = this.randomFile.readLine();
			}

			setMatrixRows(numberOfRows);
			setMatrixCols(numberOfCols);

		} catch (Exception e) {
			throw new InvalidDrwException("Invalid Border DRW " + e.toString());
		}
	}

	public int getMatrixCols()
	{
		if(!show2DOnly)
			return super.getMatrixCols();
		else
			return  super.getMatrixCols() / this.sizeOf3rdDim;
	}

	public void makeIndex() {
			ArrayList times = new ArrayList();
			ArrayList positions = new ArrayList();
	
			VTime vTime = null;
			Long position = null;
	
			openFile();
	
			try {
				String s = this.randomFile.readLine();
				while (s != null)
				{
					if (s.startsWith("Line"))
					{
						vTime = new VTime(s.substring(s.length() -12, s.length()));
						times.add(vTime);
						position = new Long(this.randomFile.getFilePointer());
						positions.add(position);
					}
					s = this.randomFile.readLine();
				}
	
			setTimes((VTime[]) times.toArray(new VTime[times.size()]));
			setFilePositions(convertArrayListToLongArray(positions));
	
			readMatrixSize();
			
			}
			catch (Exception e)
			{
				throw new InvalidDrwException("Invalid Border DRW " +e.toString() );
			}
	}

	private long[] convertArrayListToLongArray(ArrayList anArrayList) {
		long[] array = new long[anArrayList.size()];

		for (int i = 0; i < array.length; i++) {
			array[i] = ((Long) anArrayList.get(i)).longValue();
		}

		return array;
	}

	/* (non-Javadoc)
	 * @see .DrwParser#getMatrix(int)
	 */
	public Matrix getMatrix(int index) {
		long filePosition = getFilePosition(index);
		int matrixRows = getMatrixRows();
		int matrixCols = getMatrixCols();
		double[][] matrixData = new double[matrixRows][matrixCols];
		try {

			this.randomFile.seek(filePosition);

			// Salteo la linea con las marcas y el separador con guiones
			this.randomFile.readLine();
			this.randomFile.readLine();

			String s = null;
			StringTokenizer tokenizer = null;
			StringBuffer buf = new StringBuffer();
			int pos1, pos2;

			for (int i = 0; i < matrixRows; i++) {
				s = this.randomFile.readLine();
				pos2 = -1;
				buf.setLength(0);
				while((pos1 = s.indexOf('|', pos2+1))>=0)
				{
					pos2 = s.indexOf('|', pos1+1);
					buf.append(s.substring(pos1+1, pos2));
				}
				//tokenizer = new StringTokenizer(s, "| \t");
				//tokenizer.nextToken();
				for (int j = 0; j < matrixCols; j++) {
					//String token = tokenizer.nextToken();
						int from = j*getNumberWidth();
						int to = (j+1)*getNumberWidth();
						String number = buf.substring(from,to);
						float numberFl;
						try{
							numberFl = Float.parseFloat(number);
						}catch(Exception ex){
							//numberFl = Float.NaN;
							numberFl = 0;
						}
						matrixData[i][j] = numberFl;
				}
			}

			return new SimpleMatrix(matrixData,(new File(getFileName())).getName());

		} catch (IOException e) {
			throw new InvalidDrwException("Invalid matrix on DRW file " + e.toString());
		}
	}

	public VTime getTime(int index) {
		return this.times[index];
	}

	public int getIndexByTime(VTime time) {
	      int temp = Arrays.binarySearch(this.times, time);
	      if (temp <0)
	      {
	        temp = Math.abs(temp)-2;
	      }
	      return temp;
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

	public boolean supportTime() { return true; }

	public void setShow2DOnly(boolean show2DOnly)
	{
		this.show2DOnly = show2DOnly;
	}
	

}
