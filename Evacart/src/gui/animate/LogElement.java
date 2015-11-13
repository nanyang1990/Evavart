/*
 * Created on 27-mar-2004
 * @author  jicidre@dc.uba.ar
 */
package gui.animate;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 *
 */
public abstract class LogElement {

	/**
	 * 
	 */
	public LogElement() {
		super();
	}

	/**
	 * @param tokens
	 */
	public LogElement(String line) {
		parse(line);
	}

	public void parse(String line) {
		this.lineOrig = line;
		this.getTokens(line);			
	}


	/**
	 * @param line
	 * @return
	 */
	protected abstract void getTokens(String line);

	protected int type = -1;

	protected String time;

	protected String port;

	protected String value;

	protected String model;

	protected String lineOrig;

	protected String to;

	/**
		 * @param string
		 */
	protected void setPort(String string) {
		this.port = string;
		
	}

	/**
		 * @param string
		 */
	protected void setTime(String string) {
		this.time = string;
		
	}

	/**
		 * @param i
		 */
	protected void setType(int i) {
		this.type = i;
		
	}

	/**
		 * @return
		 */
	public String getPort() {
		return port;
	}

	/**
		 * @return
		 */
	public String getTime() {
		return time;
	}
	
	public Date getDate() throws ParseException{
	   return LogParser.LOG_TIME_PARSER.parse(getTime()); 
	}

	/**
		 * @return
		 */
	public int getType() {
		return type;
	}

	public String toString() {
		StringBuffer retr = new StringBuffer();
		retr.append("Type " + getType());
		retr.append(", Time " + getTime());
		retr.append(", Model " + getModel());
		retr.append(", Port " + getPort());
		retr.append(", Value " + getValue());
		retr.append(", To " + getTo());
		retr.append("\n");
		retr.append(lineOrig);
		return retr.toString();
	}

	/**
		 * @return
		 */
	public String getValue() {
		return value;
	}

	/**
		 * @param string
		 */
	public void setValue(String string) {
		value = string;
	}

	/**
		 * @return
		 */
	public String getModel() {
		return model;
	}

	/**
		 * @param string
		 */
	public void setModel(String string) {
		int parizq = string.indexOf("(");
		int parder = string.indexOf(")");
		if(parizq != -1 && parder != -1){
			string = string.substring(0,parizq);
		}
		model = string;
	}

	/**
		 * @return
		 */
	public String getTo() {
		return to;
	}

	/**
		 * @param string
		 */
	public void setTo(String string) {
		int parizq = string.indexOf("(");
		int parder = string.indexOf(")");
		if(parizq != -1 && parder != -1){
			string = string.substring(0,parizq);
		}
		to = string;
	}

	/**
	 * @param line
	 * @return
	 * Creates a LogElement for this type of line
	 */
	public static LogElement getLogElementFor(String line) {
		if(line.toUpperCase().startsWith("MENSAJE")){
			return new ClassicLogElement(line);
		}
		else if(line.startsWith("0 /")){
			return new NewLogElement(line);
		}
		else{
			return null;
		}
	}



}
