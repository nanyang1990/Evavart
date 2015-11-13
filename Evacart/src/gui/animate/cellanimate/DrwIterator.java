package gui.animate.cellanimate;

import java.util.Iterator;

/*
 * Created on 11/03/2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code Template
 */

/**
 * @author Pablo
 */
public class DrwIterator implements Iterator {

	private int currentIndex = 0;

	private DrwParser parser = null;

	private int matrixCount = 0;

	public DrwIterator(DrwParser parser) {
		this.parser = parser;
		this.matrixCount = parser.getMatrixCount();
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return this.currentIndex < this.matrixCount;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
		return this.parser.getMatrix(currentIndex++);
	}

	public VTime getCurrentTime() {
		return this.parser.getTime(this.currentIndex);
	}

	public boolean hasPrevious() {
		return this.currentIndex > 0;
	}

	public Object previous() {
		return this.parser.getMatrix(currentIndex--);
	}
}
