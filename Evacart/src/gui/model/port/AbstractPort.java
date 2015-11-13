package gui.model.port;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.javax.util.Identifiable;
import gui.model.Descriptable;
import gui.model.Expression;
import gui.model.model.AbstractModel;

import java.io.IOException;
import java.util.Vector;

public abstract class AbstractPort extends Expression implements Identifiable, PortContainer, Descriptable {
    private Object uniqueId;

    private String portId;

    private String inOrOut;

    private String portType;

    private boolean selected = false;

    /**
     * load extra Port data
     */
    protected abstract void loadOtherPortDataFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception;

    public AbstractPort() {

    }

    public String getShortDescription() {
        return getPortId();
    }

    public String getPortId() {
        return portId;
    }

    public String getInOrOut() {
        return inOrOut;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortId(String anID) {
        portId = anID;
    }

    public void setInOrOut(String inOut) {
        //System.err.println(theFunction);
        inOrOut = inOut;
    }

    public void setPortType(String aType) {
        portType = aType;
    }

    public String toString() {
        return getShortDescription();
    }

    /**
     * save the port data
     */
    public void saveTo(CommentBufferedWriter writer) throws IOException {
        writer.writeln("Port Class", this.getClass().getName());
        writer.writeln("Port UniqueId", this.getUniqueId().toString());
        writer.writeln("Port ID", portId);
        writer.writeln("Port In/Out", getInOrOut());
        writer.writeln("Port Type", getPortType());
    }

    public static AbstractPort loadFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception {
        String portClass = reader.readLine();
        AbstractPort port = (AbstractPort) (java.lang.Class.forName(portClass)).newInstance();
        String id = reader.readLine();
        port.setUniqueId(id);
        graph.getSequence().setUsed(Integer.parseInt((String) id));
        String portID = reader.readLine();
        String inOut = reader.readLine();
        String type = reader.readLine();

        port.setPortId(portID);
        port.setInOrOut(inOut);
        port.setPortType(type);
        port.loadOtherPortDataFrom(reader, graph);
        return port;
    }

    public AbstractPort(String anID, String inOut, String aType, Object id) {
        portId = anID;
        setInOrOut(inOut);
        setPortType(aType);
        setUniqueId(id);

    }

    /**
     * @see gui.model.Expression#createInstance(CommentBufferedReader)
     */
    protected Expression createInstance(CommentBufferedReader reader, AbstractModel graph) throws IOException {
        return graph.findPortByUniqueId(reader.readLine());
    }

    /**
     * @see gui.model.Expression#saveObjectOrReferenceTo(CommentBufferedWriter)
     */
    public void saveObjectOrReferenceTo(CommentBufferedWriter writer) throws IOException {
        //reference
        writer.writeln("Port Class", this.getClass().getName());
        writer.writeln("PortRef", (String) this.getUniqueId());

    }

    /**
     * Returns the selected.
     * 
     * @return boolean
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected.
     * 
     * @param selected
     *            The selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Insert the method's description here. Creation date: (14/08/2002
     * 11:44:32)
     * 
     * @return java.lang.String
     */
    public String getName() {
        return getPortId();
    }

    public void toggleSelected() {
        setSelected(!isSelected());
    }

    /**
     * @see gui.graphEditor.LinkPlugable#getInPorts()
     */
    public Vector getInPorts() {
        Vector retr = new Vector();
        if (this.getInOrOut().equals("In")) {
            retr.add(this);
        }
        return retr;
    }

    /**
     * @see gui.graphEditor.LinkPlugable#getOutPorts()
     */
    public Vector getOutPorts() {
        Vector retr = new Vector();
        if (this.getInOrOut().equals("Out")) {
            retr.add(this);
        }
        return retr;
    }

    /**
     * returns an Id that identifies this Port inequivocally in such a way that
     * it can by persisted.
     */
    public Object getUniqueId() {
        return uniqueId;
    }

    /**
     * Sets the uniqueId.
     * 
     * @param uniqueId
     *            The uniqueId to set
     */
    public void setUniqueId(Object uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @see gui.model.PortContainer#addPort(Port)
     */
    public void addPort(AbstractPort aPort) {
        //make no sense in a port
        throw new RuntimeException("Incorrect use of Port");
    }

    /**
     * @see gui.model.PortContainer#getPorts()
     */
    public Vector getPorts() {
        Vector retr = new Vector();
        retr.add(this);
        return retr;
    }

    /**
     * @see gui.model.PortContainer#setPorts(Vector)
     */
    public void setPorts(Vector ports) {
        //make no sense in a port
        throw new RuntimeException("Incorrect use of Port");
    }

    public String getDescription() {
        StringBuffer retr = new StringBuffer();
        retr.append("Port ID: " + getPortId() + "\n");
        retr.append("Port In/Out: " + getInOrOut() + "\n");
        retr.append("Port Type: " + getPortType() + "\n");
        return retr.toString();

    }

}