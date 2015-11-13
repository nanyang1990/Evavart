/*
 * Created on 03/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.javax.file;

import gui.javax.util.FileDataPersister;

import java.io.File;

/**
 * @author jcidre
 *
 */
public class JFileChooser extends javax.swing.JFileChooser{
	private boolean fixedDir = false;
	
	public JFileChooser(){
		super(FileDataPersister.getInstance().get("gui.configuration","defaultDir",System.getProperty("user.home")));
	}

	/* (non-Javadoc)
	 * @see javax.swing.JFileChooser#approveSelection()
	 * Remember last acceded directory
	 */
	public void approveSelection() {
		File lastDir = getCurrentDirectory();
		
		if(getSelectedFile().isDirectory()){
			lastDir = getSelectedFile();
		}
		setLastAccesedDir(lastDir);
		super.approveSelection();
		
	}
	public void setLastAccesedDir(File lastDir) {
		FileDataPersister.getInstance().put("gui.configuration","defaultDir",lastDir.getPath());
	}

	/**
	 * @return
	 */
	public boolean isFixedDir() {
		return fixedDir;
	}

	/**
	 * @param b
	 */
	public void setFixedDir(boolean b) {
		fixedDir = b;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JFileChooser#setCurrentDirectory(java.io.File)
	 */
	public void setCurrentDirectory(File dir) {
		if(isFixedDir()){
			if(getCurrentDirectory().equals(dir)){
				super.setCurrentDirectory(dir);		
			}
			else{
			}
		}
		else{
			super.setCurrentDirectory(dir);
		}
		
	}

}
