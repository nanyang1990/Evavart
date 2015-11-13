package gui.javax.file;
import java.io.File;
import java.util.Vector;

public class ExtensionFilter extends javax.swing.filechooser.FileFilter {
	private Vector extensions;

	public ExtensionFilter(){
		this.extensions = new Vector();		
	}	
	public ExtensionFilter(String extension){
		this.extensions = new Vector();
		extensions.add(extension.toLowerCase());
	}
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}

		String fileName = file.getName();

		int peroidIndex = fileName.lastIndexOf('.');

		if (peroidIndex > 0 && peroidIndex < fileName.length() - 1) {

			String extension = fileName.substring(peroidIndex + 1).toLowerCase();

			if (getExtensions().contains(extension))
				return true;
			else
				return false;

		}

		return false;
	}

	public String getDescription() {
		StringBuffer descr = new StringBuffer();
		for(int i = 0 ; i < getExtensions().size(); i++){
			descr.append(getExtensions().get(i) + " and ");
		}
		descr.setLength(descr.length()-4);
		descr.append("files.");
		return descr.toString();
	}

	/**
	 * Returns the extensions.
	 * @return Vector
	 */
	public Vector getExtensions() {
		return extensions;
	}

	/**
	 * Sets the extensions.
	 * @param extensions The extensions to set
	 */
	public void setExtensions(Vector extensions) {
		this.extensions = extensions;
	}
	/**
	 * Add an extension.
	 * @param extension The extension to add
	 */
	public void addExtension(String extension) {
		getExtensions().add(extension);
	}

}