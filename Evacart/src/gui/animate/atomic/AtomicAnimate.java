/*
 * Created on 02-sep-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate.atomic;

import gui.AtomicAnimateIf;
import gui.InformDialog;
import gui.animate.LogElement;
import gui.animate.Pair;
import gui.animate.Serie;
import gui.model.model.AtomicModel;
import gui.model.model.EditableCoupledModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 * @author Administrador3 jicidre@dc.uba.ar
  *
 */ 
public class AtomicAnimate extends JFrame implements AtomicAnimateIf{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat timePattern = new SimpleDateFormat("HH:mm:ss:SSS");
	private LogParser logElements;
	private AtomicYAxisCanvas atomicYAxisCanvas;
	private File log;
	private EditableCoupledModel model;
	private AtomicGraphCanvas atomicCanvas;
	private Hashtable<String, Hashtable<String, Serie>> actualChunk;
	public  int initialChunkIndex = 50;
	private SeriesPropertiesManager seriesPropertiesManager;
	private SeriesSelectorPanel seriePanel;
	private TopPanel topPanel;
    private Date startingIn;
    private Date endingIn;

	public AtomicAnimate(){
	}
	
	public void init(File log, File model, int initialChunk) throws Exception {
		seriesPropertiesManager = new SeriesPropertiesManager(this);
	    this.setInitialChunkIndex(initialChunk);
		setActualChunk(new Hashtable<String, Hashtable<String, Serie>>());
		this.log = log;
		openFile(log);
		this.jbInit();
		this.initModels(model);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					exit();
				} catch (Exception ex) {
					(new InformDialog(ex.toString(),ex)).setVisible(true);
				}

			}
		});


	}
	
	public void startAnimation() throws Exception{
	    //startingIn = LogParser.LOG_TIME_PARSER.parse("00:00:00:000");
	    startingIn = logElements.getTimeOfIndex(1);
	    endingIn = logElements.getTimeOfIndex(getInitialChunkIndex());
		showChunkOfData();
		topPanel.minReached();
		doLayoutAll();
	}

	
	/**
	 * reads the model file to obtain all models
	 * @throws Exception
	 */
	private void initModels(File model) throws Exception{
	    
		this.model = new EditableCoupledModel();
		this.model.loadFrom(model.getParentFile(), new File(model.getName()));
		this.getTopPanel().setModelos(this.model.getAll(AtomicModel.class));
	}
	

	/**
	 * @throws Exception
	 */
	protected boolean showChunkOfData() throws Exception {
		//setea el iterator en posicion
		logElements.jumpTo(startingIn);

		//borro los datos de las series
		cleanActualChunk();

		//recargo los modelos desde el iterator
		Date lastTime = null;
//		Date firstTime = null;
		
		boolean stop = false;
		while (logElements.hasMoreElements() && !stop) {
			LogElement each = (LogElement)logElements.nextElement();
			if(lastTime == null){
			    lastTime = each.getDate();
	//		    firstTime = each.getDate();
			    //chunk = 0
			}
			else if(lastTime.equals(each.getDate())){
			    //same chunk
			}
			else{
			    //new chunk
				lastTime = each.getDate();
			}
			if(lastTime.getTime() <= endingIn.getTime()){
				appendLogToModel(each.getModel(), each);
				//System.err.println(each);
			}
			else{
			    //thats it
			    stop = true;
			}
		}
		
		
		setActualChunk(getActualChunk());
		return !stop;
	}
	
	
	
	
    private void cleanActualChunk(){
        Iterator<String> models = getActualChunk().keySet().iterator();
        while (models.hasNext()) {
            String model = models.next();
            Hashtable<?, ?> ports = getActualChunk().get(model);
            Iterator<?> series = ports.values().iterator();
            while (series.hasNext()) {
                Serie eachNewSerie = (Serie) series.next();
                eachNewSerie.clean();
            }
        }
	}

	/**
	 * @param log
	 * @return
	 */
	private void openFile(File log) throws FileNotFoundException {
		LogParser parser = new LogParser(this);
		parser.setFile(log);
		logElements = parser;
	}

	/**
	 * @param model
	 * @param each
	 * creates and structure of model X port X Serie
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	private void appendLogToModel(String model, LogElement each) throws NumberFormatException, ParseException {
		Hashtable<String, Serie> series = getActualChunk().get(model);
		if (series == null) {
			series = new Hashtable<String, Serie>();
			getActualChunk().put(model, series);
		}

		//ya tengo las series
		String port = each.getPort();
		Serie serie = series.get(port);
		if (serie == null) {
			serie = new Serie();
			series.put(port, serie);
			serie.setModel(model);
			serie.setPort(port);
		}
		Pair eachPair = new Pair(each.getTime(), each.getValue());
		serie.addValue(eachPair);
		
		

	}

	

	private void jbInit() {
		setTitle("Atomic Animate");
		setBackground(Color.white);

		this.getContentPane().setLayout(new BorderLayout());

		this.getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		this.getContentPane().add(getLeftPanel(), BorderLayout.WEST);
		this.getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
	}
	private Component getCenterPanel() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setOpaque(false);
		scroll.getViewport().add(getAtomicGraphCanvas());
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(null);
		//getAtomicGraphCanvas().add(scroll,BorderLayout.CENTER);

		final JScrollPane scroll2 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll2.setOpaque(false);
		scroll2.getViewport().add(getAtomicYAxisCanvas());
		scroll2.getViewport().setOpaque(false);
		scroll2.setBorder(null);
		scroll2.setPreferredSize(new Dimension(30, 0));

		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				JScrollBar graphScroll = (JScrollBar)e.getSource();
				scroll2.getVerticalScrollBar().getModel().setValue(graphScroll.getValue());

			}
		});

		centerPanel.add(scroll, BorderLayout.CENTER);
		centerPanel.add(scroll2, BorderLayout.WEST);

		return centerPanel;
	}

	private Component getLeftPanel() {
	    JPanel leftPanel = new JPanel();
	    leftPanel.setLayout(new BorderLayout());
	    
		JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setOpaque(false);
		scroll.getViewport().add(getSerieSelectorPanel());
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(null);
		
		leftPanel.add(scroll, BorderLayout.CENTER);
		return leftPanel;
	}

	/**
     * @return
     */
    private SeriesSelectorPanel getSerieSelectorPanel() {
        if(seriePanel == null){
            seriePanel = new SeriesSelectorPanel();
            seriePanel.setAtomicAnimate(this);
        }
        return seriePanel;
    }

    private TopPanel getTopPanel() {
		if (topPanel == null) {
			topPanel = new TopPanel(this);
		}
		return topPanel;
	}
	/**
	 * @param string
	 */
	protected void modelSelection(String modelSelected) throws NumberFormatException, ParseException {
		Hashtable<?, ?> puertosDeSelectedModel = getActualChunk().get(modelSelected);
		this.setSeries(puertosDeSelectedModel.values(), modelSelected);
		doLayoutAll();

	}

	public void doLayoutAll() {
		this.doLayout();
		this.repaint();

	}

	protected AtomicGraphCanvas getAtomicGraphCanvas() {
		if (atomicCanvas == null) {
			atomicCanvas = new AtomicGraphCanvas(this);
			//atomicCanvas.setSeries(getSeries());
		}
		return atomicCanvas;
	}
	protected AtomicYAxisCanvas getAtomicYAxisCanvas() {
		if (atomicYAxisCanvas == null) {
			atomicYAxisCanvas = new AtomicYAxisCanvas(this);
			//atomicYAxisCanvas.setBackground(Color.YELLOW);
			//atomicYAxisCanvas.setLayout(new BorderLayout());

		}
		return atomicYAxisCanvas;
	}


	/**
	 * @param collection
	 */
	public void setSeries(Collection<?> newSeries, String actualModel) {
		if (newSeries != null) {
		    getAtomicGraphCanvas().setSeries(newSeries);
			getAtomicYAxisCanvas().setSeries(newSeries);
			getSerieSelectorPanel().setSeries(newSeries, actualModel);
		}
	}

	private void setActualChunk(Hashtable<String, Hashtable<String, Serie>> newModelos) throws Exception {
		this.actualChunk = newModelos;
		getSeriesPropertiesManager().setActualChunk(newModelos, startingIn, endingIn);
		getTopPanel().setModelos(getActualChunk().keySet());



	}

	private Hashtable<String, Hashtable<String, Serie>> getActualChunk() {
		return actualChunk;
	}

	/**
	 * @param string
	 */
	protected void setTimePattern(String string) {
		this.timePattern = new SimpleDateFormat(string);
		this.doLayoutAll();
	}
	
	protected DateFormat getTimePattern(){
		return timePattern;
	}

	/**
	 * @return Returns the initialChunc.
	 */
	public int getInitialChunkIndex() {
		return initialChunkIndex;
	}
	/**
	 * @param initialChunc The initialChunc to set.
	 */
	public void setInitialChunkIndex(int initialChunk) {
		this.initialChunkIndex = initialChunk;
	}
    /**
     * @return Returns the seriesPropertiesManager.
     */
    protected SeriesPropertiesManager getSeriesPropertiesManager() {
        return seriesPropertiesManager;
    }
    /**
     * @param seriesPropertiesManager The seriesPropertiesManager to set.
     */
    protected void setSeriesPropertiesManager(SeriesPropertiesManager seriesPropertiesManager) {
        this.seriesPropertiesManager = seriesPropertiesManager;
    }
    
    public float getGraphHeight(){
        float retr = getAtomicGraphCanvas().getHeight();
        return retr;
    }
    
    public float getGraphWidth(){
        float retr = getAtomicGraphCanvas().getWidth();
        return retr;
        
    }

    /**
     * @throws Exception
     * 
     */
    public void previousChunkOfData() throws Exception {
        long newStartTime = startingIn.getTime() - (endingIn.getTime() - startingIn.getTime());
        if(newStartTime <= LogParser.LOG_TIME_PARSER.parse("00:00:00:000").getTime()){
            newStartTime = LogParser.LOG_TIME_PARSER.parse("00:00:00:000").getTime();
            topPanel.minReached();
        }
        else{
            topPanel.inMiddleOfArchive();
        }
        Date newStart = new Date(newStartTime);
        Date newEnd = new Date(newStart.getTime() + (endingIn.getTime() - startingIn.getTime()));;
        
        startingIn = newStart;
        endingIn = newEnd;
        showChunkOfData();
		doLayoutAll();
    }

    /**
     * @throws Exception
     * 
     */
    public void nextChunkOfData() throws Exception {
        Date newStart = endingIn;
        Date newEnd = new Date(endingIn.getTime() + (endingIn.getTime() - startingIn.getTime()));
        startingIn = newStart;
        endingIn = newEnd;
        boolean maxReached = showChunkOfData();
        if(maxReached){
            topPanel.maxReached();
        }
        else{
            topPanel.inMiddleOfArchive();
        }
		doLayoutAll();
    }

    /**
     * @throws Exception
     * 
     */
    public void drillUp() throws Exception {
        long chunkMilis = endingIn.getTime() - startingIn.getTime();
        long halfchunk = chunkMilis /2;
        
        endingIn = new Date(endingIn.getTime() + halfchunk);
        boolean maxReached = showChunkOfData();
        this.getSeriesPropertiesManager().reinitZoomX();
        
        if(maxReached){
            topPanel.maxReached();
        }
        else{
            topPanel.inMiddleOfArchive();
        }
		doLayoutAll();
        
    }

    /**
     * @throws Exception
     * 
     */
    public void drillDwon() throws Exception {
        long chunkMilis = endingIn.getTime() - startingIn.getTime();
        long halfchunk = chunkMilis /2;
        
        endingIn = new Date(endingIn.getTime() - halfchunk);        
        boolean maxReached = showChunkOfData();
        this.getSeriesPropertiesManager().reinitZoomX();
        
        if(maxReached){
            topPanel.maxReached();
        }
        else{
            topPanel.inMiddleOfArchive();
        }
		doLayoutAll();
        
    }
    
    /* (non-Javadoc)
     * @see java.awt.Frame#finalize()
     */
    protected void finalize() throws Throwable {
        logElements = null;
    	atomicYAxisCanvas = null;
    	log = null;
    	model = null;
    	atomicCanvas = null;
    	actualChunk = null;
   
        super.finalize();
    }
    
    /**
     * finalize the critical objects
     *
     */
    private void exit(){
        logElements.close();
    }
}
