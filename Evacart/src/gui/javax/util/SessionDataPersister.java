/*
 * Created on 24/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.javax.util;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.io.IOException;

/**
 * @author jcidre
 * this is a DataPersistor that do not remember the data between sessions 
 */
public class SessionDataPersister extends DataPersister {
	private static DataPersister instance;

	/**
	 * 
	 */
	public SessionDataPersister() {
		super();
	}

	public static DataPersister getInstance() {
		if (instance == null) {
			instance = new SessionDataPersister();
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see gui.javax.util.DataPersister#loadFrom(gui.javax.io.CommentBufferedReader)
	 */
	public void loadFrom(CommentBufferedReader reader) throws IOException {

	}

	/* (non-Javadoc)
	 * @see gui.javax.util.DataPersister#saveTo(gui.javax.io.CommentBufferedWriter)
	 */
	public void saveTo(CommentBufferedWriter writer) throws IOException {

	}

}
