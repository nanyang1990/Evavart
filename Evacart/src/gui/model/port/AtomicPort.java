package gui.model.port;



import gui.javax.io.CommentBufferedReader;
import gui.model.model.AbstractModel;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public class AtomicPort extends AbstractPort {

	/**
	 * Constructor for AtomicPort.
	 */
	public AtomicPort() {
		super();
	}

	/**
	 * Constructor for AtomicPort.
	 * @param anID
	 * @param theFunction
	 * @param aType
	 * @param theState
	 */
	public AtomicPort(String anID, String inOut, String aType, Object id) {
		super(anID, inOut, aType,id);
	}

	/**
	 * @see gui.model.Port#loadOtherPortDataFrom(CommentBufferedReader)
	 */
	protected void loadOtherPortDataFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception {
	}

	/* (non-Javadoc)
	 * @see gui.model.PortContainer#getEndLinkPorts()
	 */
}
