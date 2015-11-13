package gui.representation;

import gui.javax.io.CommentBufferedReader;
import gui.model.model.AbstractModel;

import java.io.File;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public class ImageFactory {
	public static Representable loadFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception{
		String theClass = reader.readLine();
		Representable representable = (Representable)(java.lang.Class.forName(theClass)).newInstance();
		((Saveable)representable).loadFrom(reader);
		return representable;
		
	}

	/**
	 * @param imageFileName
	 * @return
	 */
	public static Representable newImage(File imageFileName) {
		return new Image(imageFileName);
	}
}
