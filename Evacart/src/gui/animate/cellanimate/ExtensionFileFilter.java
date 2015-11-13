/*
 * Created on 23/03/2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package gui.animate.cellanimate;

import java.io.File;
import java.util.StringTokenizer;

import javax.swing.filechooser.FileFilter;

/**
 * Filters the files based on the extension
 * @author Pablo
 */
public class ExtensionFileFilter extends FileFilter {
	private String[] extensions = null;
	
	private String description = null;
	
	/**
	 * @param extension File extension to filter by
	 * @param description File description to show to the user
	 */
	public ExtensionFileFilter(String extension, String description) {
		StringTokenizer tokens = new StringTokenizer(extension, ";");
		extensions = new String[tokens.countTokens()];
		for(int i=0; i<extensions.length; i++)
		{
			extensions[i] = tokens.nextToken();
		}
		this.description = description;
	}
	
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		
		String ext = getExtension(f);
		for(int i=0; i<extensions.length; i++)
		{
			if (extensions[i].equals(ext)) return true;
		} 
		return false;
	}
	
	private String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 &&  i < s.length() - 1) {
			ext = s.substring(i+1).toLowerCase();
		}
		return ext;
	}
	
	public String getDescription() {
		return this.description;
	}
}
