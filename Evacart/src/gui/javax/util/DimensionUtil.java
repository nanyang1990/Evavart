/*
 * Created on 14/07/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.javax.util;

import java.awt.Dimension;

/**
 * @author jcidre
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DimensionUtil {

	public static Dimension getMaxEdges(Dimension one, Dimension other){
		return new Dimension(Math.max(one.width, other.width), Math.max(one.height,other.height));
	}
	
	public static boolean includes(Dimension bigger, Dimension smaller){
		if(bigger.height < smaller.height){
			return false;
		}
		if(bigger.width < smaller.width){
			return false;
		}
		
		return true;
		
	}
}
