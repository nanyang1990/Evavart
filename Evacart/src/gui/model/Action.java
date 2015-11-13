package gui.model;

import gui.javax.util.Identifiable;

public class Action implements Identifiable{

	private Object uniqueId;
	private String actionString = new String();
	//private AbstractLink parentLink;

	public Action(String anActionString, Object id) {
		setUniqueId(id);
		actionString = anActionString;
		//parentLink = aParentLink;
	}
/*
	public AbstractLink getParentLink() {
		return parentLink;
	}
*/
	public String get() {
		return actionString;
	}
	public String toString() {
		return actionString;
	}
	
	/**
	 * @return
	 */
	public String getActionString() {
		return actionString;
	}

	/**
	 * @return
	 */
	public Object getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param string
	 */
	public void setActionString(String string) {
		actionString = string;
	}

	/**
	 * @param link
	 */
	/*
	public void setParentLink(AbstractLink link) {
		parentLink = link;
	}
*/
	/**
	 * @param object
	 */
	public void setUniqueId(Object object) {
		uniqueId = object;
	}

}