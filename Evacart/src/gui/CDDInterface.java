/*
 * Created on 11-nov-2003
 * @author  jicidre@dc.uba.ar
 */
package gui;

import java.io.File;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * Interfaz con el CDD
 *
 */
public interface CDDInterface {

	/**
	 * @param b
	 */
	void setVisible(boolean b);

    /**
     * @param path
     */
    void setModelDir(File path);

    /**
     * @param maFile
     */
    void setMA(File maFile);
 }
