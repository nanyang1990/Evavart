package gui.model.link;

import gui.InformDialog;
import gui.graphEditor.Layoutable;
import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;
import gui.model.Expression;
import gui.model.model.AbstractModel;
import gui.model.port.AbstractPort;
import gui.model.unit.AbstractModelUnit;

import java.io.IOException;

/**
 * @author Administrador
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractCoupledLink extends AbstractLink {
	private Object uniqueId;
	private Expression startExpression;
	private Expression endExpression;
	private boolean showPorts;
	
	AbstractCoupledLink(){
	}
	AbstractCoupledLink(Layoutable start, Layoutable end, Object id){
		super(start, end, id);
	}

	public String getDescription(){
	    return getShortDescription();
	}

	public String getShortDescription(){
		StringBuffer linkString = new StringBuffer();

		if (this.getStartExpression() != null) {
			linkString.append("(" + this.getStartExpression().getShortDescription() + ")");
		}
		else {
			linkString.append("()");
		}
		linkString.append(" -> ");
		if (this.getEndExpression() != null) {
			linkString.append("(" + this.getEndExpression().getShortDescription() + ")");
		}
		else {
			linkString.append("()");
		}

		return linkString.toString();
	}


	public String getLinkLabel() {
		StringBuffer retr = new StringBuffer();
		
		String startHalf;
		
		if(getStartExpression().equals(getStartLinkPlugable())){
			startHalf = getStartExpression().getShortDescription();
		}
		else{
			startHalf = getStartExpression().getShortDescription() + "@" + getStartLinkPlugable().getName(); 		
		}

		String endHalf;
		
		if(getEndExpression().equals(getEndLinkPlugable())){
			endHalf = getEndExpression().getShortDescription();
		}
		else{
			endHalf = getEndExpression().getShortDescription() + "@" + getEndLinkPlugable().getName(); 		
		}
				
		retr.append(startHalf);
		retr.append(" ");
		retr.append(endHalf);
		return retr.toString();
	}



	public void setEndExpression(Expression endExpression) {
		this.endExpression = endExpression;
	}



	public void setStartExpression(Expression startExpression) {
		this.startExpression = startExpression;
	}



	/**
	 * Returns the uniqueId.
	 * @return Object
	 */
	public Object getUniqueId() {
		return uniqueId;
	}



	/**
	 * Sets the uniqueId.
	 * @param uniqueId The uniqueId to set
	 */
	public void setUniqueId(Object uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Expression getEndExpression() {
		return endExpression;
	}



	public Expression getStartExpression() {
		return startExpression;
	}

	public void unlink(Expression ex){
		if(ex.equals(this.getStartExpression())){
			this.setStartExpression(null);
			
		}
		if(ex.equals(this.getEndExpression())){
			this.setEndExpression(null);
		}
	}
	

	/**
	 * check if start and end could be the edges of a Link
	 * @param start
	 * @param end
	 */
	public static boolean checkLinkEdges(Layoutable start, Layoutable end, AbstractModel aGraph){
		// Port Self In not used with Port Self out
		// Port Self In not used with Inner Port In
		// Inner Port out not used with Inner Port In
		// Inner Port out not used with Port Self Out 
		
		boolean retr = true;
		
		if(start instanceof AbstractPort){
			//Port Self In ....
			if(((AbstractPort)start).getInOrOut().equals("In")){
				//ok, continue
				//..not used...
				/*
				if((aGraph.getIncidentLinks((Port)start).size()) > 0){
					//used!!!
					retr = false;
					(new InformDialog("The source port of a new connection must not be already used.")).setVisible(true);
					
				}
				*/
				//...Port Self out
				//... Inner Port In
				if(end instanceof AbstractPort){
					//...Port Self out
					if(((AbstractPort)end).getInOrOut().equals("Out")){
						//ok. Thats it
					}
					else{
						///ARRRGGG
						retr = false;
						(new InformDialog("The destination port of a new connection must be an output port.",null)).setVisible(true);						
					}
				}
				else{
					//its a model. Depends on the linking.
				}
			}	
			else{
				//Port Self Out. Cancel
				retr = false ;
				(new InformDialog("The source port of a new connection must be an input port.",null)).setVisible(true);				
			}
		}
		else{
			//Model. Continue. Depends on the linking
			
			//however, if the end is a port, must be an output port
			if(end instanceof AbstractPort){
				//...Port Self Out
				if(((AbstractPort)end).getInOrOut().equals("Out")){
					//ok
				}
				else{
					//arrgghh
					retr = false;
					(new InformDialog("The destination port of a new connection must be an output port.",null)).setVisible(true);				
				}
					
			}
			else{
				// up to here, everithing is ok
			}
			
		}
		
		
		return retr;		
	}
	public void saveTo(CommentBufferedWriter writer) throws IOException {
		writer.increaseTab();
		writer.increaseTab();
		super.saveTo(writer);
		if (getStartExpression() != null) {
			getStartExpression().saveObjectOrReferenceTo(writer);
		} 
		else {
			//print a blank.
			//when loading a file, a blank means no connection to port
			writer.writeln("StartExpr null", "");
		}
		if (getEndExpression() != null) {
			getEndExpression().saveObjectOrReferenceTo(writer);
		}
		else {
			//print a blank.
			//when loading a file, a blank means no connection to port
			writer.writeln("EndExpression is null", "");
		}
		writer.decreaseTab();
		writer.decreaseTab();
	}
	public void loadOtherLinkDataFrom(CommentBufferedReader reader, AbstractModel graphContainerOfLink) throws Exception {
			
			//Expressions and values
			//These have to been looked for inside the Graphs that contains the Expressions.
			
			//if the Link is plugged to a ModelUnit
			if(getStartLinkPlugable() instanceof AbstractModelUnit){
				this.setStartExpression(Expression.loadOrFindFrom(reader,((AbstractModelUnit)getStartLinkPlugable()).getModel()));
			}
			//if the Link is plugged to a Port 
			else if(getStartLinkPlugable() instanceof Expression){
				this.setStartExpression(Expression.loadOrFindFrom(reader,graphContainerOfLink));
			}
			else{
				throw new RuntimeException("Not supported type");
			}
	
			//if the Link is plugged to a ModelUnit
			if(getEndLinkPlugable() instanceof AbstractModelUnit){
				this.setEndExpression(Expression.loadOrFindFrom(reader,((AbstractModelUnit)getEndLinkPlugable()).getModel()));
			}
			//if the Link is plugged to a Port 
			else if(getEndLinkPlugable() instanceof Expression){
				this.setEndExpression(Expression.loadOrFindFrom(reader,graphContainerOfLink));
			}
			else{
				throw new RuntimeException("Not supported type. CoupledLink can not be attached to an instance of: " + getEndLinkPlugable() + ". ");
			}
			
						
	} 


	

    /**
     * @return Returns the showPorts.
     */
    public boolean isShowPorts() {
        return showPorts;
    }
    /**
     * @param showPorts The showPorts to set.
     */
    public void setShowPorts(boolean showPorts) {
        this.showPorts = showPorts;
    }
}
