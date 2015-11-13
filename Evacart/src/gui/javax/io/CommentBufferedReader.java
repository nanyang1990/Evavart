package gui.javax.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class CommentBufferedReader extends BufferedReader {

	/**
	 * Constructor for CommentBufferedReader.
	 * @param arg0
	 */
	public CommentBufferedReader(Reader arg0) {
		super(arg0);
	}

	/**
	 * Constructor for CommentBufferedReader.
	 * @param arg0
	 * @param arg1
	 */
	public CommentBufferedReader(Reader arg0, int arg1) {
		super(arg0, arg1);
	}

	/**
	 * @see java.io.BufferedReader#readLine()
	 */
	public String readLine() throws IOException {
		String commentedLine = super.readLine();
		//System.err.println(commentedLine);
		if(commentedLine != null && commentedLine.startsWith("# ")){
			commentedLine = commentedLine.substring(commentedLine.indexOf(":")+1);
		}
		return commentedLine;
		
	}

}
