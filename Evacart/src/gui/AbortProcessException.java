package gui;

/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class AbortProcessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for AbortProcessException.
	 */
	public AbortProcessException() {
		super();
	}

	/**
	 * Constructor for AbortProcessException.
	 * @param arg0
	 */
	public AbortProcessException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor for AbortProcessException.
	 * @param arg0
	 */
	public AbortProcessException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Constructor for AbortProcessException.
	 * @param arg0
	 * @param arg1
	 */
	public AbortProcessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
