package gui.model.unit;

import gui.Constants;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.model.AbstractModel;
import gui.representation.Circle;
import gui.representation.Representable;

import java.awt.Point;
import java.io.IOException;
import java.util.regex.Pattern;

public class AtomicUnit extends AbstractUnit {

    private String timeAdvance;

    public AtomicUnit() {
        this(null, null, null);
    }

    public AtomicUnit(String aName, Point aPoint, Object id) {
        super();
        this.setName(aName);
        this.setLocation(aPoint);
        this.setUniqueId(id);
    }

    public String getTimeAdvance() {
        if (timeAdvance == null) {
            try {
                setTimeAdvance("00:00:00:00");
            }
            catch (Exception ex) {
            }
        }
        return timeAdvance;
    }

    public void setTimeAdvance(String time) throws Exception {
        String regex = "\\d{0,4}:\\d{0,4}:\\d{0,4}:\\d{0,4}";
        if (Pattern.matches(regex, time)) {
            timeAdvance = time;
        } else {
            throw new Exception(time + " does not match the pattern " + regex);
        }
    }

    public String toString() {
        return getShortDescription();
    }

    public void saveTo(CommentBufferedWriter writer) throws IOException {
        super.saveTo(writer);
        writer.writeln("Atomic TL", getTimeAdvance());
    }

    protected void loadOtherUnitDataFrom(CommentBufferedReader reader, AbstractModel graph) throws Exception {
        this.setTimeAdvance(reader.readLine());
    }

    public AtomicUnit getStatebyID(String anID) {
        if (getName().equals(anID))
            return this;
        else
            return null;
    }

    public AtomicUnit getStatebyLocation(int x, int y) {
        if ((x == getLocation().x) && (y == getLocation().y))
            return this;
        else
            return null;
    }

    /**
     * @see gui.model.AbstractUnit#setOtherImageData(SelectableDrawable)
     */
    public void prepareImage(Representable image) {
        super.prepareImage(image);
        image.setText(this.getName() + " " + getTimeAdvance());
        image.setWidth(Constants.getInstance().getInt("draw.atomicUnit.width", "30"));
        image.setHeight(Constants.getInstance().getInt("draw.atomicUnit.height", "30"));

    }

    protected Representable getDefaultImage() {
        return new Circle();
    }

    public String getDescription() {
        StringBuffer desc = new StringBuffer();
        desc.append("Unit Name: " + getName() + "\n");
        desc.append("TTL: " +  getTimeAdvance() + "\n");
        return desc.toString();
    }

    public String getShortDescription() {
        return getName();
    }

    /**
     * @see gui.graphEditor.Layoutable#getImage()
     */
    public Representable getImage() {
        Representable image = new Circle();
        this.prepareImage(image);
        return image;
    }

}