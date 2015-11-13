package gui.javax.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class CommentBufferedWriter extends BufferedWriter {
	private int cantTabs = 0;
	
	/**
	 * Constructor for CommentBufferedWriter.
	 * @param arg0
	 */
	public CommentBufferedWriter(Writer arg0) {
		super(arg0);
	}

	/**
	 * Constructor for CommentBufferedWriter.
	 * @param arg0
	 * @param arg1
	 */
	public CommentBufferedWriter(Writer arg0, int arg1) {
		super(arg0, arg1);
	}
	
	public void writeln(String data)throws IOException{
		write(data);
		newLine();
	}

	public void writeln(String comment, String data)throws IOException{
		if(comment.indexOf(":") != -1){
			throw new IOException("Not ':' allowed in comment!");
		}
		for(int i = 0 ; i < getCantTabs(); i++){
			comment = "\t" + comment;
		}
		write("# "+comment+":");
		writeln(data == null?"":data);
	}
	public void writeln(String comment, int data)throws IOException{	
		writeln(comment, ""+data);
	}
	/**
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		this.flush();
		this.close();
		super.finalize();
	}

	/**
	 * @return
	 */
	public int getCantTabs() {
		return cantTabs;
	}

	/**
	 * @param i
	 */
	public void setCantTabs(int i) {
		cantTabs = i;
	}
	
	public void increaseTab(){
		setCantTabs(getCantTabs()+1);	
	}
	
	public void decreaseTab(){
		setCantTabs(getCantTabs()-1);
	}

}
