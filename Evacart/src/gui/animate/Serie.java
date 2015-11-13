/*
 * Created on 02-sep-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate;



import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrador3 jicidre@dc.uba.ar
  *
 */
public class Serie implements Cloneable{
	private String model;
	private boolean valuesOnOff = true;
	private boolean visible = true;
	private Color color;
	String port;
	List<Pair> values;
	private float maxRenderingValue;
	private float minRenderingValue;

	private Date maxRenderingDate;
	private Date minRenderingDate;
	
	private float zoomX = -1;
	private float zoomY = -1;
	
	private boolean initialized = false;

	public Serie(){
		port = "UNDEFINED";
		values = new ArrayList<Pair>();
	}
	/**
	 * @return
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @return
	 */
	public List<Pair> getValues() {
		return values;
	}

	/**
	 * @param string
	 */
	public void setPort(String string) {
		port = string;
	}

	/**
	 * @param list
	 */
	public void setValues(List<Pair> list) {
		values = list;
	}

	public void addValue(Pair aPair){
		getValues().add(aPair);
	}
	/**
	 * 
	 */
	public boolean getVisible() {
		return visible;
		
	}
	/**
	 * @param b
	 */
	public void setVisible(boolean b) {
		this.visible = b;
	}
	/**
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return
	 */
	public float getZoomX() throws Exception{
	    /*
		if(zoomX == -1){
			float distance = getMaxX().getTime() - getMinX().getTime();
			zoomX = 500000/distance;
			//System.err.println("XXXX " + zoomX);
		}
		*/
		if(zoomX < 0 || zoomX == Float.POSITIVE_INFINITY || zoomX == Float.NEGATIVE_INFINITY || zoomX == Float.NaN){
			throw new Exception("Not a valid zoom");
		}
		
		return zoomX;
	}

	/**
	 * @return
	 */
	public float getZoomY() throws Exception{
	    /*
		if(zoomY == -1){
			float distance = getMaxY() - getMinY();
			zoomY = 100/distance;
			//System.err.println("YYYY " + zoomY);
		}
		*/
		if(zoomY < 0 || zoomY == Float.POSITIVE_INFINITY || zoomY == Float.NEGATIVE_INFINITY || zoomY == Float.NaN){
		    throw new Exception("Not a valid zoom");
		}
		return zoomY;
	}

	/**
	 * @param f
	 */
	public void setZoomX(float f) {
		zoomX = f;
	}

	/**
	 * @param f
	 */
	public void setZoomY(float f) {
		zoomY = f;
	}
	public void zoomYIn() throws Exception{
		setZoomY(getZoomY() * 2f);
	}

	public void zoomYOut()throws Exception{
		setZoomY(getZoomY() / 2f);
	}
	/**
	 * 
	 */
	public void zoomXIn() throws Exception{
		setZoomX(getZoomX() * 2f);		
	}
	/**
	 * 
	 */
	public void zoomXOut() throws Exception{
		setZoomX(getZoomX() / 2f);
	}
	/**
	 * 
	 */
	public boolean getValuesOnOff() {
		return valuesOnOff;
		
	}

	/**
	 * @param b
	 */
	public void setValuesOnOff(boolean b) {
		valuesOnOff = b;
	}

	/**
		 * Returns the values for the X Axis
		 * @param serie
		 * @return
		 */
	public List<Date> getXValues() {
		List<Date> retr = new ArrayList<Date>();
		if(getVisible()){
			Iterator<Pair> values = getValues().iterator();
			while(values.hasNext()){
				Pair each = values.next();
				retr.add(each.getTime());
			}
		}
		return retr;
	}

	/**
		 * Returns the values for the X Axis
		 * @param serie
		 * @return
		 */
	public List<Float> getYValues() {
		List<Float> retr = new ArrayList<Float>();
		if(getVisible()){
			Iterator<Pair> values = getValues().iterator();
			while(values.hasNext()){
				Pair each = values.next();
				retr.add(new Float(each.getValue()));
			}
		}
		return retr;
	}

	/**
		 * @param collection
		 * @return
		 */
	public float getMaxY() {
		float maxY = 0;
		Iterator<Float> yvalues = getYValues().iterator();
		while(yvalues.hasNext()){
			Float each = yvalues.next();
			float thisY = each.floatValue();
			if(thisY > maxY){
				maxY = thisY;
			}
		}
		return maxY;
	}

	/**
		 * @param collection
		 * @return
		 */
	public float getMinY() {
		float minY = 0;
		Iterator<Float> yvalues = getYValues().iterator();
		while(yvalues.hasNext()){
			Float each = yvalues.next();
			float thisY = each.floatValue();
			if(thisY < minY){
				minY = thisY;
			}
		}
		return minY;
	}


	/**
		 * Devuelve el valor mas grande del eje de las Xs
		 * @param collection
		 * @return
		 */
	public Date getMaxX() {
		Date maxX = null;
		Iterator<Date> xvalues = getXValues().iterator();
		while(xvalues.hasNext()){
			Date each = xvalues.next();
			if(maxX == null || each.after(maxX)){
				maxX = each;
			}
		}
		return maxX;
	}

	/**
		 * Devuelve el valor mas grande del eje de las Xs
		 * @param collection
		 * @return
		 */
	public Date getMinX() {
		Date minX = null;
		Iterator<Date> xvalues = getXValues().iterator();
		while(xvalues.hasNext()){
			Date each = xvalues.next();
			if(minX == null || each.before(minX)){
				minX = each;
			}
		}
		return minX;
	}

	public float guessMinValue(){
		return getMinY() * 0.9f;
	}

	public float guessMaxValue(){
		return getMaxY() * 1.1f;
	}
	/**
	 * 
	 */
	public float getMaxRenderingValue() {
		return maxRenderingValue;
		
	}


	/**
	 * @return
	 */
	public float getMinRenderingValue() {
		return minRenderingValue;
	}

	/**
	 * @param f
	 */
	public void setMaxRenderingValue(float f) {
		maxRenderingValue = f;
	}

	/**
	 * @param f
	 */
	public void setMinRenderingValue(float f) {
		minRenderingValue = f;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
	    float zoomx = -1;
	    float zoomy = -1;
	    try {
            zoomx = this.getZoomX();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	    try {
            zoomy = this.getZoomY();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
	    
	    
		return this.getPort() + "("+ zoomx+","+zoomy+") "+ this.getMinRenderingValue() +" "+ this.getMaxRenderingValue();
	}
	/**
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
		
	}

	/**
	 * @return
	 */
	public String getModel() {
		return model;
	}

    /**
     * @return Returns the initialized.
     */
    public boolean isInitialized() {
        return initialized;
    }
    /**
     * @param initialized The initialized to set.
     */
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
    /**
     * 
     */
    public void clean() {
        this.getValues().clear();
    }
    /**
     * @return
     */
    public float guessZoomY(float height) {
        float dif = getMaxRenderingValue() - getMinRenderingValue();
        return height / dif * 0.8f;        
    }
    
    public float guessZoomX(float width){
        float dif = getMaxRenderingDate().getTime() - getMinRenderingDate().getTime();
        return width / dif * 0.65f;        
    }
    /**
     * @return Returns the maxRenderingDate.
     */
    public Date getMaxRenderingDate() {
        return maxRenderingDate;
    }
    /**
     * @param maxRenderingDate The maxRenderingDate to set.
     */
    public void setMaxRenderingDate(Date maxRenderingDate) {
        this.maxRenderingDate = maxRenderingDate;
    }
    /**
     * @return Returns the minRenderingDate.
     */
    public Date getMinRenderingDate() {
        return minRenderingDate;
    }
    /**
     * @param minRenderingDate The minRenderingDate to set.
     */
    public void setMinRenderingDate(Date minRenderingDate) {
        this.minRenderingDate = minRenderingDate;
    }
}
