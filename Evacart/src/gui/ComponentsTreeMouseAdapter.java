package gui;

import gui.graphEditor.AbstractModelEditor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public class ComponentsTreeMouseAdapter extends MouseAdapter {
	
	public ComponentsTreeMouseAdapter(AbstractModelEditor g){
		super();
		this.setModelEditor(g);
	}
	
	private AbstractModelEditor modelEditor = null;
	/**
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1) {
			//left button clicke
			if (event.getClickCount() == 2) {
				//double click!!!
				getModelEditor().componentsTreeDoubleClicked(event.getComponent(),event.getPoint());
			}
		}
		else if (event.getButton() == MouseEvent.BUTTON3) {
			//right button clicked
			getModelEditor().componentsTreeRightClicked(event.getComponent(),event.getPoint());
		}
	}


	/**
	 * Returns the graphEditor.
	 * @return ModelEditor
	 */
	public AbstractModelEditor getModelEditor() {
		return modelEditor;
	}

	/**
	 * Sets the graphEditor.
	 * @param graphEditor The graphEditor to set
	 */
	public void setModelEditor(AbstractModelEditor graphEditor) {
		this.modelEditor = graphEditor;
	}

}
