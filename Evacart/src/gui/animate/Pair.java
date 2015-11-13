/*
 * Created on 02-sep-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrador3 jicidre@dc.uba.ar
  *
 */
public class Pair {
	private float value;
	private Date time;
	public String toString(){
		return this.getValue() + " " + this.getTime();
	}
	public Pair(Date time, float value){
		this.value = value;
		this.time = time;
	}
	
	public Pair(String time, String value) throws NumberFormatException, ParseException{
		
		this(new SimpleDateFormat("HH:mm:ss:SSS").parse(time),Float.parseFloat(value));
	}
	
	/**
	 * @return
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @return
	 */
	public float getValue() {
		return value;
	}

	/**
	 * @param date
	 */
	public void setTime(Date date) {
		time = date;
	}

	/**
	 * @param f
	 */
	public void setValue(float f) {
		value = f;
	}

}
