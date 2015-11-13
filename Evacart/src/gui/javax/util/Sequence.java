package gui.javax.util;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public class Sequence {
	private int sequence = 0;
	public synchronized int next(){
		return sequence++;
	}
	public synchronized void setUsed(int used){
		if(sequence <= used){
			sequence = used+1;
		}
	}
}
