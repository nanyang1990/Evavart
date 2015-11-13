/*
 * Created on 06-oct-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 *
 *
 */
public class ClassicLogElement extends LogElement{

	
	
	/**
	 * @param tokens
	 */
	public ClassicLogElement(String line) {
		super(line);
	}

	/**
	 * @param line
	 * @return
	 */
	protected void getTokens(String line) {
		try{
		StringTokenizer toker = new StringTokenizer(line,"/ ");
		String type = toker.nextToken().trim() + " " + toker.nextToken();
		TypeToken typeToken = new TypeToken(type);
		if(typeToken.getType() == TypeToken.MENSAJE_X){
			setType(TypeToken.MENSAJE_X);
			setTime(toker.nextToken());
			setModel(toker.nextToken());
			setPort(toker.nextToken());
			setValue(toker.nextToken());
			//skip para
			toker.nextToken();
			setTo(toker.nextToken());
		}
		else if(typeToken.getType() == TypeToken.MENSAJE_Y){
			setType(TypeToken.MENSAJE_Y);
			setTime(toker.nextToken());
			setModel(toker.nextToken());
			setPort(toker.nextToken());
			setValue(toker.nextToken());
			//skip para
			toker.nextToken();
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
		else if(typeToken.getType() == TypeToken.MENSAJE_AST){
			setType(TypeToken.MENSAJE_AST);
			setTime(toker.nextToken());
			//setMessage(toker.nextToken());
		}
		}
		catch(NoSuchElementException exxx){
			exxx.printStackTrace();
		}
	}

}
