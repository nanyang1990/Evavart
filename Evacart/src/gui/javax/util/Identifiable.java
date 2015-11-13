package gui.javax.util;

/**
 * @author jicidre@dc.uba.ar
 * returns an Id that identifies this Unit inequivocally
 * in such a way that it can by persisted, for example.
 */

public interface Identifiable {
	public Object getUniqueId();
	public void setUniqueId(Object id);

}
