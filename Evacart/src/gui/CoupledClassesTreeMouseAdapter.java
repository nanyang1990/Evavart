package gui;

import gui.graphEditor.AddPortToModelEventListener;
import gui.graphEditor.DeletePortToModelEventListener;
import gui.image.Repository;
import gui.javax.file.ExtensionFilter;
import gui.javax.file.JFileChooser;
import gui.javax.util.FileDataPersister;
import gui.model.model.AbstractModel;
import gui.model.model.ImportedFromRegisterAtomicModel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public class CoupledClassesTreeMouseAdapter extends MouseAdapter {
	private JTree tree = null;
	//private String defaultDir = "";
	private Component container = null;

	public CoupledClassesTreeMouseAdapter(JTree t, String d, Component c) {
		super();
		this.setTree(t);

		this.setContainer(c);

	}

	private void selectImage(AbstractModel model) throws IOException {

		JFileChooser fc = new JFileChooser();
		ExtensionFilter filter = new ExtensionFilter();

		File savedDir = fc.getCurrentDirectory();
		fc.setCurrentDirectory(Repository.getInstance().getRepository());


		filter.addExtension("gif");
		filter.addExtension("jpg");
		filter.addExtension("jpeg");
		fc.setFileFilter(filter);
		int returnVal = fc.showDialog(getContainer(), "Set");

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			//get the selected file
			File imageFileName = fc.getSelectedFile();

			Repository.getInstance().addImage(imageFileName);
			//persist it
			FileDataPersister.getInstance().put(
				"unit.class.image.filename",
				model.getModelName(),
				imageFileName.getName());

		}
		fc.setLastAccesedDir(savedDir);
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(MouseEvent event) {
		super.mouseClicked(event);

		DefaultMutableTreeNode node =
			(DefaultMutableTreeNode) getTree().getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof AbstractModel) {
			AbstractModel model = (AbstractModel) node.getUserObject();

			if (event.getButton() == MouseEvent.BUTTON1) {
				//left button clicke
				if (event.getClickCount() == 2) {
					//double click!!!
					try {
						this.selectImage(model);
					}
					catch (IOException e) {
						new InformDialog("Error al cargar la imagen : ", e).setVisible(true);
					}
				}
			}
			if (event.getButton() == MouseEvent.BUTTON3) {
				//left button clicke
				if (event.getClickCount() == 1) {
					//double click!!!
					//this.selectImage(theClass);
					this.getContextMenu(model,event);
				}
			}
		}
	}

	/**
	 * Returns the tree.
	 * @return JTree
	 */
	public JTree getTree() {
		return tree;
	}

	/**
	 * Sets the tree.
	 * @param tree The tree to set
	 */
	public void setTree(JTree tree) {
		this.tree = tree;
	}



	/**
	 * Returns the container.
	 * @return Component
	 */
	public Component getContainer() {
		return container;
	}

	/**
	 * Sets the container.
	 * @param container The container to set
	 */
	public void setContainer(Component container) {
		this.container = container;
	}


	private void getContextMenu(final AbstractModel model, MouseEvent anEvent){
		JPopupMenu classPopup = new JPopupMenu();
		
		JMenuItem selectImageItem = new JMenuItem("selectImage");
		selectImageItem.addActionListener(new ActionListener() {
			/**
			 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
			 */
			public void actionPerformed(ActionEvent arg0) {
				try {
					selectImage(model);
				}
				catch (IOException e) {
					e.printStackTrace();
					new InformDialog("Error al cargar la imagen : " , e).setVisible(true);					
				}
			}
		});
		classPopup.add(selectImageItem);


		JMenuItem clearImageItem = new JMenuItem("clear Image");
		clearImageItem.addActionListener(new ActionListener() {
			/**
			 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
			 */
			public void actionPerformed(ActionEvent arg0) {
				clearImage(model);
			}
		});
		classPopup.add(clearImageItem);

		if(model instanceof ImportedFromRegisterAtomicModel){
				JMenuItem addPortItem = new JMenuItem("Add port");
				JMenuItem deletePortItem = new JMenuItem("Delete port");
				classPopup.add(addPortItem);
				classPopup.add(deletePortItem);

				addPortItem.addActionListener(new AddPortToModelEventListener(getTree(),((ImportedFromRegisterAtomicModel)model)));
				deletePortItem.addActionListener(new DeletePortToModelEventListener(getTree(),((ImportedFromRegisterAtomicModel)model)));
		
		}

		classPopup.setInvoker(getTree());
		classPopup.show(getTree(), anEvent.getX(), anEvent.getY());
		
		
	}
	
	private void clearImage(AbstractModel model){
		FileDataPersister.getInstance().remove("unit.class.image.filename",model.getModelName());
	}
	
}
