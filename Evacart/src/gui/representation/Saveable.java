package gui.representation;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.io.IOException;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public interface Saveable {
	public void saveTo(CommentBufferedWriter writer) throws IOException;

	/**
	 * restor the data of this drawable from a Reader
	 * */
	public void loadFrom(CommentBufferedReader reader) throws IOException;
	
}
