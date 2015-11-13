/*
 * Created on 03-ene-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui.animate;

import java.awt.Color;

/**
 * @author jicidre
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ColorFactory {
	public static Color getColorFor(int index){
		Color retr;
		switch(index%8){
			case 1:
				retr = Color.RED;
				break;
			case 2:
				retr = Color.BLUE;
				break;
			case 3:
				retr = Color.GREEN;
				retr = retr.darker();
				retr = retr.darker();
				//retr = retr.darker();
				break;
			case 4:
				retr = Color.PINK;
				retr = retr.darker();
				retr = retr.darker();
				//retr = retr.darker();
				break;
			case 5:
				retr = Color.MAGENTA;
				break;
			case 6:
				retr = Color.CYAN;
				break;
			case 7:
				retr = Color.PINK;
				break;
			case 8:
				retr = Color.ORANGE;
				retr = retr.darker();
				retr = retr.darker();
				break;
			default:
				retr = Color.getHSBColor(1/index, 0.5f, 0.5f);
				break;
		}
		return retr;
	} 
}
