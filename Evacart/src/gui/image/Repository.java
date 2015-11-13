/*
 * Created on 14-oct-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.image;

import gui.OkCancelJDialog;
import gui.QueryDialog;
import gui.javax.util.FileDataPersister;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * Repository of Images
 *
 */
public class Repository {

	private static Repository instance;
	private File repository;
	
	protected Repository(){
		
	 	repository = new File(FileDataPersister.getInstance().get("gui.configuration","image.repository","images"));
	}
	public static Repository getInstance(){
		if(instance == null){
			instance = new Repository();
		}
		return instance;
	}
	
	
	
	public void addImage(String aNewImage) throws IOException{
		addImage(new File(aNewImage));
	}	
	
	public void addImage(File aNewImage) throws IOException{
		if(aNewImage != null && aNewImage.exists()){
			File repFile = new File(repository.getPath()+File.separator+aNewImage.getName());
			if(repFile.exists() && !repFile.equals(aNewImage)){
				QueryDialog query = new QueryDialog("File already exists in repository, overwrite?");
				query.setVisible(true);
				if(query.getReturnState() == OkCancelJDialog.OK_RETURN_STATE){
					repFile.delete();
					copy(aNewImage,repFile);	
				}
			}
			else if(repFile.exists()){
			}
			else{
					copy(aNewImage,repFile);
			}
		}			
		
	}
	

	public File getImage(File imageName){		
		File retr = new File(repository.getPath()+File.separator + imageName.getName());
		if(retr.exists()){
			return retr;
		}
		else{
			return null;
		}

	}
	
	
    private void copy(File from, File to) throws IOException {

		if(!to.exists()){
			to.createNewFile();
		}
        FileReader in = new FileReader(from);
        FileWriter out = new FileWriter(to);
        int c;

        while ((c = in.read()) != -1)
           out.write(c);

        in.close();
        out.close();
    }
	/**
	 * @param imageRep
	 */
	public void setRepository(File imageRep) {
		this.repository = imageRep;
		
	}

	/**
	 * @param imageRep
	 */
	public File getRepository() {
		return this.repository;
	}
	
}
