/*
 * Created on 23/09/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui.animate.atomic;

import gui.animate.ColorFactory;
import gui.animate.Serie;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * @author Administrador3
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SeriesPropertiesManager {
    private Hashtable<?, ?> actualChunk;

    private float commonZoomX = -1;
   
    //private Hashtable seriesParametrosPersister = new Hashtable();

    private AtomicAnimate grafico;

    public SeriesPropertiesManager(AtomicAnimate grafico) {
        this.grafico = grafico;
    }

    /**
     * @param string
     * @throws Exception
     */
    protected void zoomYIn(String model, String port) throws Exception {
        Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
        Serie serie = (Serie) ports.get(port);
        serie.zoomYIn();
        //seriesParametrosPersister.put(serie.getPort() + serie.getModel() + "ZOOMY", new Float(serie.getZoomY()));
        getGrafico().doLayoutAll();
    }

    /**
     * @param string
     * @throws Exception
     */
    protected void zoomYOut(String model, String port) throws Exception {
        Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
        Serie serie = (Serie) ports.get(port);
        serie.zoomYOut();

        //seriesParametrosPersister.put(serie.getPort() + serie.getModel() + "ZOOMY", new Float(serie.getZoomY()));
        getGrafico().doLayoutAll();
    }

    /**
     * @param string
     * @throws Exception
     */
    protected void zoomXOut() throws Exception {
        Iterator<?> models = getActualChunk().keySet().iterator();
        while (models.hasNext()) {
            String model = (String) models.next();
            Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
            Iterator<?> series = ports.values().iterator();
            while (series.hasNext()) {
                Serie serie = (Serie) series.next();
                serie.zoomXOut();
                //seriesParametrosPersister.put(serie.getPort() + serie.getModel() + "ZOOMX", new Float(serie.getZoomX()));
            }
        }
        getGrafico().doLayoutAll();
    }

    /**
     * @param string
     * @throws Exception
     */
    protected void zoomXIn() throws Exception {
        Iterator<?> models = getActualChunk().keySet().iterator();
        while (models.hasNext()) {
            String model = (String) models.next();
            Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
            Iterator<?> series = ports.values().iterator();
            while (series.hasNext()) {
                Serie serie = (Serie) series.next();
                serie.zoomXIn();
               // seriesParametrosPersister.put(serie.getPort() + serie.getModel() + "ZOOMX", new Float(serie.getZoomX()));
            }
        }
        getGrafico().doLayoutAll();
    }

    /**
     * @param string
     */
    protected void toogleVisibility(String model, String port) {
        Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
        Serie serie = (Serie) ports.get(port);

        serie.setVisible(!serie.getVisible());
        //seriesParametrosPersister.put(serie.getPort() + serie.getModel() + "VISIBLE", new Boolean(serie.getVisible()));

        getGrafico().getAtomicGraphCanvas().remake();
        getGrafico().getAtomicYAxisCanvas().remake();
        getGrafico().doLayoutAll();
    }

    /**
     * @param string
     */
    protected void valuesOnOff() {
        Iterator<?> models = getActualChunk().keySet().iterator();
        while (models.hasNext()) {
            String model = (String) models.next();
            Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
            Iterator<?> series = ports.values().iterator();
            while (series.hasNext()) {
                Serie serie = (Serie) series.next();
                serie.setValuesOnOff(!serie.getValuesOnOff());
            }
        }
        getGrafico().doLayoutAll();
    }

    /**
     * @param theSerie
     * @param f
     */
    protected void scaleFromChanged(String model, String port, float f) {

        Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
        Serie serie = (Serie) ports.get(port);

        serie.setMinRenderingValue(f);
        //seriesParametrosPersister.put(serie.getPort() + serie.getModel() + "MIN", new Float(f));

        getGrafico().doLayoutAll();
    }

    /**
     * @param theSerie
     * @param f
     */
    protected void scaleToChanged(String model, String port, float f) {
        Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
        Serie serie = (Serie) ports.get(port);

        serie.setMaxRenderingValue(f);
        //seriesParametrosPersister.put(serie.getPort() + serie.getModel() + "MAX", new Float(f));

        getGrafico().doLayoutAll();
    }

    /**
     * @return Returns the grafico.
     */
    protected AtomicAnimate getGrafico() {
        return grafico;
    }

    /**
     * @param grafico
     *            The grafico to set.
     */
    protected void setGrafico(AtomicAnimate grafico) {
        this.grafico = grafico;
    }

    /**
     * @return Returns the actualChunk.
     */
    protected Hashtable<?, ?> getActualChunk() {
        return actualChunk;
    }

    /**
     * @param actualChunk
     *            The actualChunk to set.
     * @throws Exception
     */
    protected void setActualChunk(Hashtable<?, ?> actualChunk, Date lastStart, Date lastEnd) throws Exception {
        this.actualChunk = actualChunk;
        initChunk(lastStart, lastEnd);
    }

    private void initChunk(Date lastStart, Date lastEnd) throws Exception {
        Iterator<?> models = getActualChunk().keySet().iterator();
        int color = 1;
        
        while (models.hasNext()) {
            String model = (String) models.next();
            Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
            Iterator<?> series = ports.values().iterator();
            while (series.hasNext()) {
                Serie eachNewSerie = (Serie) series.next();
                if (!eachNewSerie.isInitialized()) {
                    //init vertical scope
                    float minVal = eachNewSerie.guessMinValue();
                    //seriesParametrosPersister.put(eachNewSerie.getPort() + eachNewSerie.getModel() + "MIN", new Float(minVal));
                    eachNewSerie.setMinRenderingValue(minVal);

                    float maxVal = eachNewSerie.guessMaxValue();
                    //seriesParametrosPersister.put(eachNewSerie.getPort() + eachNewSerie.getModel() + "MAX", new Float(maxVal));
                    eachNewSerie.setMaxRenderingValue(maxVal);

                    //init the visibility

                    boolean vis = true;
                    //seriesParametrosPersister.put(eachNewSerie.getPort() + eachNewSerie.getModel() + "VISIBLE", new Boolean(vis));
                    eachNewSerie.setVisible(vis);

                    eachNewSerie.setColor(ColorFactory.getColorFor(color++));
        			
                    
                }
                
            }
        }
        
        //ensure all Series has the same min and max date
        setCommonMinDate(lastStart);
        setCommonMaxDate(lastEnd);
        
        Iterator<?> models2 = getActualChunk().keySet().iterator();
        while (models2.hasNext()) {
            String model = (String) models2.next();
            Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
            Iterator<?> series = ports.values().iterator();
            while (series.hasNext()) {
                Serie eachNewSerie = (Serie) series.next();
                if (!eachNewSerie.isInitialized()) {

                    //init the zoom y
                    float zy = eachNewSerie.guessZoomY(getGrafico().getGraphHeight()/ports.size());
                   // seriesParametrosPersister.put(eachNewSerie.getPort() + eachNewSerie.getModel() + "ZOOMY", new Float(zy));
                    eachNewSerie.setZoomY(zy);

                    //init the zoom x
                    initZoomX(eachNewSerie);

                    eachNewSerie.setInitialized(true);
                }
                
            }
        }
        
    }

    public void reinitZoomX(){
        Iterator<?> models2 = getActualChunk().keySet().iterator();
        while (models2.hasNext()) {
            String model = (String) models2.next();
            Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
            Iterator<?> series = ports.values().iterator();
            while (series.hasNext()) {
                Serie eachNewSerie = (Serie) series.next();
                initZoomX(eachNewSerie);
            }
            
        }
    }

    
    
    
    private void initZoomX(Serie eachNewSerie){
        float zx = eachNewSerie.guessZoomX(getGrafico().getGraphWidth());
        //System.out.println(zx);
       // seriesParametrosPersister.put(eachNewSerie.getPort() + eachNewSerie.getModel() + "ZOOMX", new Float(zx));
        eachNewSerie.setZoomX(zx);
    }
    
    private void setCommonMinDate(Date min){
        Iterator<?> models2 = getActualChunk().keySet().iterator();
        while (models2.hasNext()) {
            String model = (String) models2.next();
            Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model); 
            Iterator<?> series = ports.values().iterator();
            while (series.hasNext()) {
                Serie eachNewSerie = (Serie) series.next();
                eachNewSerie.setMinRenderingDate(min);
                
            }
        }
    }

    private void setCommonMaxDate(Date max){
        Iterator<?> models2 = getActualChunk().keySet().iterator();
        while (models2.hasNext()) {
            String model = (String) models2.next();
            Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
            Iterator<?> series = ports.values().iterator();
            while (series.hasNext()) {
                Serie eachNewSerie = (Serie) series.next();
                eachNewSerie.setMaxRenderingDate(max);
                
            }
        }
        
    }
    
    /**
     * @param commonZoomX
     *            The commonZoomX to set.
     */
    private void setCommonZoomX(float commonZoomX) {
        this.commonZoomX = commonZoomX;
    }

    /**
     * @param model
     * @param port
     */
    public void rescale(String model, String port) {
        Hashtable<?, ?> ports = (Hashtable<?, ?>) getActualChunk().get(model);
        Serie serie = (Serie) ports.get(port);

        serie.setMaxRenderingValue(serie.guessMaxValue());
        serie.setMinRenderingValue(serie.guessMinValue());
       // seriesParametrosPersister.put(serie.getPort() + serie.getModel() + "MAX", new Float(f));
        // seriesParametrosPersister.put(serie.getPort() + serie.getModel() + "MIN", new Float(f));
        getGrafico().doLayoutAll();                
    }
}