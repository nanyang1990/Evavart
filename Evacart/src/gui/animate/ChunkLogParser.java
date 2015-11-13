/*
 * Created on 22/09/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui.animate;

import java.awt.Component;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Administrador3
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class ChunkLogParser extends LogParser {
    
    private SortedMap<Long, Long> timeMillisPositionArchive = new TreeMap<Long, Long>();
    
	/**
	 * @param parent
	 */
	public ChunkLogParser(Component parent) {
		super(parent);
	}
	
	/* (non-Javadoc)
     * @see gui.animate.LogParser#readNextElement()
     */
    protected LogElement readNextElement() throws ParseException {
        long positionBefore = getCurrPos();
        
        LogElement retr = super.readNextElement();
        if(retr != null){
	        Date time = retr.getDate();
	        if(!timeMillisPositionArchive.containsKey(new Long(time.getTime()))){
	            timeMillisPositionArchive.put(new Long(time.getTime()),new Long(positionBefore));
	        }
        }
        return retr;
    }
	
	
    /**
     * Sets the reader at the begining of the first occurrence of
     * an entry with timestamp equal or bigger than startingIn
     * @param startingIn
     * @throws ParseException
     */
    public void jumpTo(Date startingIn) throws IOException, ParseException {
        SortedMap<Long, Long> submapTime = timeMillisPositionArchive.headMap(plusOneMillis(startingIn));
        Long archivePosition;
        try {
            Long nearestIndex = submapTime.lastKey();
            archivePosition = submapTime.get(nearestIndex);
        } catch (Exception ex) {
            archivePosition = new Long(0);
        }

        //reseteo el reader;
        setFile(getLogFile());
        //skip up tho the nearest lower entry
        getReader().skip(archivePosition.longValue());
        setCurrPos(getCurrPos()+archivePosition.longValue());
        
        //start skipping until reach de desired starting time
        while(hasMoreElements()){
            LogElement each = (LogElement)nextElement();
            if(each.getDate().getTime() >= startingIn.getTime()){
                break;
            }
        }

        //again
        
        submapTime = timeMillisPositionArchive.headMap(plusOneMillis(startingIn));
        try {
            Long nearestIndex = submapTime.lastKey();
            archivePosition = submapTime.get(nearestIndex);
        } catch (Exception ex) {
            archivePosition = new Long(0);
        }

        //reseteo el reader;
        setFile(getLogFile());
        getReader().skip(archivePosition.longValue());
        setCurrPos(getCurrPos()+archivePosition.longValue());

    }
    
    /*
     * the SortedSet.headMap() is strict. so
     */
    private Long plusOneMillis(Date orig){
         return new Long(orig.getTime()+1);
    }
    public Date getTimeOfIndex(int index){
        Long retr = null;
        while(timeMillisPositionArchive.size() < index && hasMoreElements()){
            nextElement();
        }        
        
        Iterator<Long> times = timeMillisPositionArchive.keySet().iterator();
        for(int i = 0; i < index && times.hasNext(); i++){
            retr = times.next();
        }
        return new Date(retr.longValue());
     }
    
}
