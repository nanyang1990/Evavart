package gui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author jicidre@dc.uba.ar
 * Insert Type description here.
 */
public class Constants {
	private static Constants instance;
	private String RESOURCE = "gui.Constants";
	private ResourceBundle resource;

	public int getInt(String key, String dflt) {
		return Integer.parseInt(this.getString(key, dflt));
	}

	public String getString(String key, String dflt) {
		String data = null;
		try{
			data = resource.getString(key);
		}
		catch(MissingResourceException ex){
			System.out.println(ex.toString());
		}
		
		if (data == null || "".equals(data)) {
			data = dflt;
		}
		return data;
	}
	/**
	 * Constructor for Constants.
	 */
	public Constants() {
		super();
		try {
			resource = ResourceBundle.getBundle(RESOURCE);
		}
		catch (MissingResourceException e) {
			e.printStackTrace(System.err);
		}

	}

	public static Constants getInstance() {
		if (instance == null) {
			instance = new Constants();
		}
		return instance;
	}

}
