package gui.animate.cellanimate;

import java.util.StringTokenizer;

/*
 * Created on 03/03/2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code Template
 */

/**
 * @author Pablo
 */
public class VTime implements Comparable {

	private int hours = 0;

	private int minutes = 0;

	private int seconds = 0;

	private int miliSeconds = 0;

	/**
	 * Crea un nuevo VTime a partir de un String en formato HH:MM:SS:mmm
	 * @param vTime
         * @throws NumberFormatException
	 */
	public VTime(String vTime) throws NumberFormatException
    {
		this.setTime(vTime);
	}

	public VTime(int hours, int minutes, int seconds, int miliSeconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		this.miliSeconds = miliSeconds;

                this.normalize();
	}

	public void setTime(VTime time)
	{
		this.hours = time.hours;
		this.minutes = time.minutes;
		this.seconds = time.seconds;
		this.miliSeconds = time.miliSeconds;
		this.normalize();
	}
	public void setTime(String vTime) throws NumberFormatException
	{
		StringTokenizer tokenizer = new StringTokenizer(vTime, ":");

		String s = tokenizer.nextToken();
		this.hours = Integer.parseInt(s);
		s = tokenizer.nextToken();
		this.minutes = Integer.parseInt(s);
		s = tokenizer.nextToken();
		this.seconds = Integer.parseInt(s);
		s = tokenizer.nextToken();
		this.miliSeconds = Integer.parseInt(s);

        this.normalize();
	}

	/**
	 * Normalizes the time.
	 */

        public void normalize ()
        {
          while (miliSeconds >= 1000)
          {
            miliSeconds -= 1000;
            seconds ++;
          }
          while (seconds >= 60)
          {
            seconds -= 60;
            minutes ++;
          }
          while (minutes >= 60)
          {
            minutes -= 60;
            hours ++;
          }
        }


	/**
	 * @return int
	 */

        public int getHours() {
		return hours;
	}

	/**
	 * @return int
	 */
	public int getMiliSeconds() {
		return miliSeconds;
	}

	/**
	 * @return int
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @return int
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * Sets the hours.
	 * @param hours The hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
                this.normalize();
	}

	/**
	 * Sets the miliSeconds.
	 * @param miliSeconds The miliSeconds to set
	 */
	public void setMiliSeconds(int miliSeconds) {
		this.miliSeconds = miliSeconds;
                this.normalize();
	}

	/**
	 * Sets the minutes.
	 * @param minutes The minutes to set
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
                this.normalize();
	}

	/**
	 * Sets the seconds.
	 * @param seconds The seconds to set
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds;
                this.normalize();
	}

	public long getTimeInMsec()
	{
		return miliSeconds + 1000L * (seconds + minutes*60 + hours*60*60);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(VTime v) {
		return
			this.hours == v.hours &&
			this.minutes == v.minutes &&
			this.seconds == v.seconds &&
			this.miliSeconds == v.miliSeconds;
	}

	public int compareTo(Object o) {
		VTime v = (VTime) o;

		long myMiliseconds = this.getTimeInMsec();

		long otherMiliseconds = v.getTimeInMsec();

		if (myMiliseconds > otherMiliseconds)
			return 1;
		else {
			if (myMiliseconds == otherMiliseconds)
				return 0;
			else
				return -1;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
        {
          String h = "0" + this.hours;
          String m = "0" + this.minutes;
          String s = "0" + this.seconds;
          String ms = "00" + this.miliSeconds;
          return (h.substring(h.length()-2) +":" + m.substring(m.length()-2) +":" + s.substring(s.length()-2) +":" + ms.substring(ms.length()-3));
	}
	
}
