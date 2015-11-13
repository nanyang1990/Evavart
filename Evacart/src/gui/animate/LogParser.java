/*
 * Created on 06-oct-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate;

import gui.InformDialog;
/*
import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
*/
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

/**
 * @author Administrador3 jicidre@dc.uba.ar Parser of a log file as an
 *         enumeration of LogElements
 *  
 */
public abstract class LogParser implements Enumeration<Object> {

    private Component parent;

    private BufferedReader reader;

    private LogElement next;

    private File logFile;

    private long currPos;
    
	public final static SimpleDateFormat LOG_TIME_PARSER = new SimpleDateFormat("HH:mm:ss:SSS");

    public LogParser(Component parent) {
        super();
        this.parent = parent;
    }

  

    public void setFile(File file) throws FileNotFoundException {
        if (reader != null) {
            try {
                reader.close();
                currPos = 0;
                next = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logFile = file;
        reader = new BufferedReader(new FileReader(file));
        currPos = 0;
        next = null;
    }

    /**
     * @return
     */
    public Component getParent() {
        return parent;
    }

    /**
     * @param component
     */
    public void setParent(Component component) {
        parent = component;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Enumeration#hasMoreElements()
     */
    public boolean hasMoreElements() {
        if (next == null) {
            try {
                next = readNextElement();
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return next != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Enumeration#nextElement()
     */
    public Object nextElement() {
        Object retr = null;
        if (next != null) {
            retr = next;
        } else {
            try {
                retr = readNextElement();
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        next = null;
        return retr;
    }

    /**
     * @return
     * @throws ParseException
     */
    protected LogElement readNextElement() throws ParseException {
        try {
            LogElement retr = null;
            while (retr == null) {
                String line = reader.readLine();
                //System.err.println(line);
                if (line != null) {
                    currPos += line.toCharArray().length;
                    retr = parse(line);
                    if (retr != null) {
                        if (!isNextOK(retr)) {
                            retr = null;
                        } else {
                            return retr;
                        }
                    }
                } else {
                    return null;
                }
            }
            return null;
        } catch (IOException e) {
            new InformDialog("Error reading File : " + e.toString(),e).setVisible(true);
            return null;
        }

    }

    /**
     * @param next
     * @return
     */
    public abstract boolean isNextOK(LogElement next);

    /**
     * @param line
     */
    private LogElement parse(String line) {
        LogElement aLogElement = LogElement.getLogElementFor(line);
        return aLogElement;

    }


    /**
     * @return Returns the logFile.
     */
    public File getLogFile() {
        return logFile;
    }
    /**
     * @param logFile The logFile to set.
     */
    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }
    /**
     * @return Returns the reader.
     */
    public BufferedReader getReader() {
        return reader;
    }
    /**
     * @param reader The reader to set.
     */
    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }
    /**
     * @return Returns the currPos.
     */
    protected long getCurrPos() {
        return currPos;
    }
    /**
     * @param currPos The currPos to set.
     */
    protected void setCurrPos(long currPos) {
        this.currPos = currPos;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    protected void finalize() throws Throwable {
        close();

        super.finalize();
    }
    
    /**
     * 
     */
    public void close() {
        try{
            reader.close();
        }
        catch (Exception e) {
        }
        reader = null;
        
    }
    
    /*
     * unused
     *   private void openFile() {
        JFileChooser fChooser = new JFileChooser();
        ExtensionFilter filter = new ExtensionFilter();
        filter.addExtension("log");

        fChooser.setFileFilter(filter);
        int returnVal = fChooser.showDialog(getParent(), "Set");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //get the selected file
            File file = fChooser.getSelectedFile();
            try {
                setFile(file);
            } catch (FileNotFoundException e) {
                new InformDialog("Error opening File : " + e.toString(),e).setVisible(true);
            }
        }

    }
    */

}