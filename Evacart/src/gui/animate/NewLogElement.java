/*
 * Created on 27-mar-2004
 * @author  jicidre@dc.uba.ar
 */
package gui.animate;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * Log element of the new log type
 */
public class NewLogElement extends LogElement {

	/**
	 * 
	 */
	public NewLogElement() {
		super();
	}

	/**
	 * @param line
	 */
	public NewLogElement(String line) {
		super(line);
	}

	/* (non-Javadoc)
	 * @see gui.animate.LogElement#getTokens(java.lang.String)
	 */
	protected void getTokens(String line) {
		try {
			StringTokenizer toker = new StringTokenizer(line, "/ ");
			//skip 0
			toker.nextToken();
			//skip L
			toker.nextToken();
			
			String type = toker.nextToken().trim();
			TypeToken typeToken = new TypeToken(type);
			if (typeToken.getType() == TypeToken.MENSAJE_X) {
				setType(TypeToken.MENSAJE_X);
				setTime(toker.nextToken());
				//skip from
				toker.nextToken();
				setPort(toker.nextToken());
				setValue(toker.nextToken());
				//skip para
				//toker.nextToken();
				setModel(toker.nextToken());
			}
			else if (typeToken.getType() == TypeToken.MENSAJE_Y) {
				setType(TypeToken.MENSAJE_Y);
				setTime(toker.nextToken());
				setModel(toker.nextToken());
				setPort(toker.nextToken());
				setValue(toker.nextToken());
				//skip para
				//String para = toker.nextToken();
				//to
				setTo(toker.nextToken());
			}
			/*
			else if(typeToken.getType() == TypeToken.MENSAJE_D){
				setType(TypeToken.MENSAJE_D);
				setTime(toker.nextToken());
				setFrom(toker.nextToken());
				setMessage(toker.nextToken());
			}
			*/
			else if (typeToken.getType() == TypeToken.MENSAJE_AST) {
				setType(TypeToken.MENSAJE_AST);
				setTime(toker.nextToken());
				//setMessage(toker.nextToken());
			}
		}
		catch (NoSuchElementException exxx) {
			exxx.printStackTrace();
		}

	}

}
