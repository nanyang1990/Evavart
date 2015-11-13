package gui.cdd;

import gui.InformDialog;
import gui.model.Action;
import gui.model.Expression;
import gui.model.ExpressionAndValue;
import gui.model.Function;
import gui.model.Variable;
import gui.model.link.AbstractAtomicLink;
import gui.model.link.AbstractCoupledLink;
import gui.model.link.AtomicExternalLink;
import gui.model.link.AtomicInternalLink;
import gui.model.model.AbstractModel;
import gui.model.model.EditableAtomicModel;
import gui.model.model.EditableCoupledModel;
import gui.model.model.ImportedAtomicModel;
import gui.model.model.ImportedCoupledModel;
import gui.model.model.ImportedFromCDDAtomicModel;
import gui.model.model.ImportedFromRegisterAtomicModel;
import gui.model.port.AbstractPort;
import gui.model.port.AtomicPort;
import gui.model.port.CoupledPort;
import gui.model.unit.AbstractModelUnit;
import gui.model.unit.AtomicModelUnit;
import gui.model.unit.AtomicUnit;
import gui.model.unit.EditableAtomicModelUnit;
import gui.model.unit.EditableCoupledModelUnit;
import gui.model.unit.ImportedCoupledModelUnit;
import gui.model.unit.ImportedFromCDDAtomicModelUnit;
import gui.model.unit.ImportedFromRegisterAtomicModelUnit;
import gui.model.unit.Initializable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author jcidre
 *
 */
public class Translator {
	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * Exprot a Model to the CPP format
	 *
	 * @param aModel 
	 * @param aFile 
	 *
	 * @throws IOException 
	 * @throws RuntimeException 
	 */
	public static void exportToCDPP(AbstractModel aModel, File aFile) throws IOException {
		if (aModel instanceof EditableCoupledModel) {
			EditableCoupledModel ecModel = (EditableCoupledModel)aModel;
			exportToCDPP(ecModel, aFile);
		}
		else if (aModel instanceof EditableAtomicModel) {
			EditableAtomicModel eaModel = (EditableAtomicModel)aModel;
			exportToCDPP(eaModel, aFile);
		}
		else {
			throw new RuntimeException("Model Type not contempled.");
		}
	}

	/**
	*    the file should be an .ma with a Coupled Model or an cdd with an Atomic Model
	*    or a cpp with the definition of default registered models.
	* @param aReader
	* @param aFileName
	* @throws IOException
	*/
	public static Collection importFrom(File modelFile) throws IOException {
		String aFileName = modelFile.getName();
		if (aFileName.toLowerCase().endsWith(".ma")) {
			return importFromMA(modelFile);
		}
		else if (aFileName.toLowerCase().endsWith(".cdd")) {
			return importFromCDD(modelFile);
		}
		else if (aFileName.toLowerCase().endsWith(".cpp")) {
			return importFromRegister(modelFile);
		}
		else {
			throw new RuntimeException("Unexpected Model type: " + aFileName);
		}
	}

	/**
	 * returns this link as de .MA grammar requires
	 * rule 23   IntDef -> GGADT_INT GGADT_COLON GGADT_STATEID GGADT_STATEID PortValueOutList Actions
	 * rule 26   ExtDef -> GGADT_EXT GGADT_COLON GGADT_STATEID GGADT_STATEID Expresion GGADT_INPUT GGADT_CONSTANT Actions
	 */
	private static String commonToCDPPString(AbstractAtomicLink link) {
		StringBuffer retr = new StringBuffer();

		//GGAD_INT or GGAD_EXT		
		retr.append(link.getLinkInternalExternal());

		//GGAD_COLON
		retr.append(": ");

		//GGAD_STATEID
		retr.append(link.getStartLinkPlugable().getName() + " ");

		//GGAD_STATEID
		retr.append(link.getEndLinkPlugable().getName() + " ");

		return retr.toString();
	}

	private static void exportToCDPP(EditableCoupledModel aModel, File aFile) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(aModel.getActualPath().getAbsolutePath() + File.separator + aFile.getName()));

		//model class
		writer.println("[" + aModel.getModelName() + "]");

		innerExportToCPP(aModel, writer);
		writer.flush();
		writer.close();
	}

	private static void exportToCDPP(EditableAtomicModel model, File file) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(file));

		//Title
		writer.println("[" + model.getModelName() + "]");

		//Ports
		if (model.getInPorts().size() > 0) {
			writer.print("in: ");

			Enumeration tmpPorts = model.getInPorts().elements();
			while (tmpPorts.hasMoreElements()) {
				AbstractPort tmpPort = (AbstractPort)tmpPorts.nextElement();
				writer.print(tmpPort.getPortId() + " ");
			}
			writer.println();
		}

		if (model.getOutPorts().size() > 0) {
			writer.print("out: ");

			Enumeration tmpPorts = model.getOutPorts().elements();
			while (tmpPorts.hasMoreElements()) {
				AbstractPort tmpPort = (AbstractPort)tmpPorts.nextElement();
				writer.print(tmpPort.getPortId() + " ");
			}
			writer.println();
		}

		//Variables
		Enumeration tmpVars = model.getVariables().elements();
		StringBuffer string4 = new StringBuffer();
		while (tmpVars.hasMoreElements()) {
			Variable tmpVar = (Variable)tmpVars.nextElement();
			String name = tmpVar.getName();
			string4.append(name + " ");
		}
		if (model.getVariables().size() > 0) {
			writer.println("var: " + string4);
		}

		//States
		Enumeration tmpStates = model.getModels().elements();
		StringBuffer string3 = new StringBuffer();
		while (tmpStates.hasMoreElements()) {
			AtomicUnit tmpState = (AtomicUnit)tmpStates.nextElement();
			string3.append(tmpState.getName() + " ");
		}
		writer.println("state: " + string3);

		//Initial state
		if (model.getInitialUnit().getName().compareTo("") != 0) {
			writer.println("initial : " + model.getInitialUnit().getName());
		}

		//Links
		Enumeration tmpLinks = model.getLinks().elements();
		while (tmpLinks.hasMoreElements()) {
			AbstractAtomicLink tmpLink = (AbstractAtomicLink)tmpLinks.nextElement();

			writer.println(toCDPPString(tmpLink));
		}

		Enumeration tempStates = model.getModels().elements();

		//States TL
		while (tempStates.hasMoreElements()) {
			AtomicUnit tempState = (AtomicUnit)tempStates.nextElement();
			writer.println(tempState.getName() + ":" + tempState.getTimeAdvance());
		}

		Enumeration tmpVars2 = model.getVariables().elements();
		while (tmpVars2.hasMoreElements()) {
			Variable tmpVar = (Variable)tmpVars2.nextElement();
			if ((tmpVar.getValue() != null) && !("".equals(tmpVar.getValue()))) {
				writer.println(tmpVar.getName() + ":" + tmpVar.getValue());
			}
		}
		writer.flush();
		writer.close();
	}

	private static Collection importFromCDD(File modelFile) throws IOException {
		ImportedAtomicModel retr = new ImportedFromCDDAtomicModel();
		BufferedReader reader = new BufferedReader(new FileReader(modelFile));

		/*
		//Title
		writer.println("[" + model.getModelName() + "]");
		*/
		String modelName = reader.readLine();
		modelName = modelName.substring(1, modelName.length() - 1);
		retr.setModelName(modelName);

		//Ports

		/*
		if (model.getInPorts().size() > 0) {
		    writer.print("in: ");
		    Enumeration tmpPorts = model.getInPorts().elements();
		    while (tmpPorts.hasMoreElements()) {
		        Port tmpPort = (Port)tmpPorts.nextElement();
		        writer.print(tmpPort.getPortId() + " ");
		    }
		    writer.println();
		}
		
		if (model.getOutPorts().size() > 0) {
		    writer.print("out: ");
		    Enumeration tmpPorts = model.getOutPorts().elements();
		    while (tmpPorts.hasMoreElements()) {
		        Port tmpPort = (Port)tmpPorts.nextElement();
		        writer.print(tmpPort.getPortId() + " ");
		    }
		    writer.println();
		}
		*/
		String inPorts = reader.readLine();
		inPorts = inPorts.substring(4);

		StringTokenizer inToker = new StringTokenizer(inPorts);
		while (inToker.hasMoreTokens()) {
			String aPort = inToker.nextToken();
			retr.addPort(new AtomicPort(aPort, "In", "Integer", "" + retr.getSequence().next()));
		}

		String outPorts = reader.readLine();
		outPorts = outPorts.substring(4);

		StringTokenizer outToker = new StringTokenizer(outPorts);
		while (outToker.hasMoreTokens()) {
			String aPort = outToker.nextToken();
			retr.addPort(new AtomicPort(aPort, "Out", "Integer", "" + retr.getSequence().next()));
		}

		/*
		    //Variables
		    Enumeration tmpVars = model.getVariables().elements();
		    StringBuffer string4 = new StringBuffer();
		    while (tmpVars.hasMoreElements()) {
		        Variable tmpVar = (Variable)tmpVars.nextElement();
		        string4.append(tmpVar.getName() + " ");
		    }
		    if (model.getVariables().size() > 0)
		        writer.println("var: " + string4);
		
		    //States
		    Enumeration tmpStates = model.getModels().elements();
		    StringBuffer string3 = new StringBuffer();
		    while (tmpStates.hasMoreElements()) {
		        AtomicUnit tmpState = (AtomicUnit)tmpStates.nextElement();
		        string3.append(tmpState.getName() + " ");
		    }
		    writer.println("state: " + string3);
		
		    //Initial state
		    if (model.getInitialUnit().getName().compareTo("") != 0)
		        writer.println("initial : " + model.getInitialUnit().getName());
		
		    //Links
		    Enumeration tmpLinks = model.getLinks().elements();
		    while (tmpLinks.hasMoreElements()) {
		        AtomicLink tmpLink = (AtomicLink)tmpLinks.nextElement();
		        writer.println(tmpLink.toCDPPString());
		    }
		    Enumeration tempStates = model.getModels().elements();
		
		    //States TL
		    while (tempStates.hasMoreElements()) {
		        AtomicUnit tempState = (AtomicUnit)tempStates.nextElement();
		        writer.println(tempState.getName() + ":" + tempState.getTimeAdvance());
		    }
		
		    Enumeration tmpVars2 = model.getVariables().elements();
		    while (tmpVars2.hasMoreElements()) {
		        Variable tmpVar = (Variable)tmpVars2.nextElement();
		        if (tmpVar.getValue().compareTo("") != 0)
		            writer.println(tmpVar.getName() + ":" + tmpVar.getValue());
		    }
		
		    */
		retr.setExportClasspath(modelFile);

		Collection respuesta = new ArrayList();
		respuesta.add(retr);
		return respuesta;
	}

	private static Collection importFromMA(File file) throws IOException {
		ImportedCoupledModel retr = new ImportedCoupledModel();
		BufferedReader reader = new BufferedReader(new FileReader(file));

		//model class
		//writer.println("[" + aModel.getModelName() + "]");
		String modelName = reader.readLine();
		modelName = modelName.substring(1, modelName.length() - 1);
		retr.setModelName(modelName);

		//components
		/*
		writer.print("components : ");
		Enumeration allUnits = model.getModels().elements();
		
		while (allUnits.hasMoreElements()) {
		    AbstractModelUnit aUnit = (AbstractModelUnit)allUnits.nextElement();
		    if(aUnit instanceof CoupledModelUnit){
		        writer.print(aUnit.getName() + " ");
		    }
		    else{
		        writer.print(aUnit.getName() + "@ggad ");
		    }
		}
		writer.println();
		*/
		
		String components = reader.readLine();
		Vector componentNames = new Vector();
		components = components.substring(components.indexOf(":") +1);
		StringTokenizer toker = new StringTokenizer(components," ",false);
		while(toker.hasMoreElements()){
			String each = toker.nextToken();
			if(each.indexOf("@") != -1){
				each = each.substring(0,each.indexOf("@"));
			}
			componentNames.add(each);
		}
		
		retr.setComponentNames(componentNames);
		
		

		//Ports

		/*
		if(model.getOutPorts().size() > 0){
		    writer.print("out : ");
		    Enumeration tmpPorts = model.getOutPorts().elements();
		    while (tmpPorts.hasMoreElements()) {
		        Port tmpPort = (Port) tmpPorts.nextElement();
		        writer.print(tmpPort.getPortId() + " ");
		    }
		    writer.println();
		}
		
		
		if(model.getInPorts().size() > 0){
		    writer.print("in : ");
		    Enumeration tmpPorts = model.getInPorts().elements();
		    while (tmpPorts.hasMoreElements()) {
		        Port tmpPort = (Port) tmpPorts.nextElement();
		        writer.print(tmpPort.getPortId() + " ");
		
		            }
		    writer.println();
		}
		
		*/
		String ports = reader.readLine();
		if (ports.startsWith("out :")) {
			ports = ports.substring(5);

			StringTokenizer outToker = new StringTokenizer(ports);
			while (outToker.hasMoreTokens()) {
				String aPort = outToker.nextToken();
				retr.addPort(new CoupledPort(aPort, "Out", "Integer", "" + retr.getSequence().next()));
			}

			ports = reader.readLine();
		}

		if (ports.startsWith("in :")) {
			ports = ports.substring(4);

			StringTokenizer inToker = new StringTokenizer(ports);
			while (inToker.hasMoreTokens()) {
				String aPort = inToker.nextToken();
				retr.addPort(new CoupledPort(aPort, "In", "Integer", "" + retr.getSequence().next()));
			}
		}

		/*
		        Enumeration tmpLinks = model.getallLinks().elements();
		        while (tmpLinks.hasMoreElements()) {
		            CoupledLink tmpLink = (CoupledLink)tmpLinks.nextElement();
		            writer.println("Link : " + tmpLink.getLinkLabel());
		        }
		        writer.println();
		
		        Enumeration allModels = model.getModels().elements();
		
		        while (allModels.hasMoreElements()) {
		            AbstractModelUnit aUnit = (AbstractModelUnit)allModels.nextElement();
		            writer.println("["+aUnit.getName() + "]");
		            if (aUnit instanceof EditableAtomicModelUnit) {
		                EditableAtomicModelUnit each = (EditableAtomicModelUnit)aUnit;
		                writer.println("source : " + aUnit.getModel().getExportClasspath());
		            }
		            else if (aUnit instanceof CoupledModelUnit) {
		                CoupledModelUnit each = (CoupledModelUnit)aUnit;
		                innerExportToCPP((EditableCoupledModel)each.getModel(),writer);
		            }
		
		            writer.println();
		        }
		
		        */
		retr.setExportClasspath(file);

		Collection col = new ArrayList();
		col.add(retr);
		return col;
	}

	private static Collection importFromRegister(File file) throws IOException {
		Collection retr = new HashSet();
		BufferedReader aReader = new BufferedReader(new FileReader(file));

		String aString = aReader.readLine();
		StringBuffer aSBuffer = new StringBuffer("p");
		aSBuffer.setCharAt(0, '\"');
		while (aString != null) {
			if (aString.indexOf("registerAtomic") >= 0) {
				int i = aString.indexOf(new String(aSBuffer));
				int j = aString.lastIndexOf(new String(aSBuffer));
				if ((i != -1) && (j != -1)) {
					String anewName = aString.substring(i + 1, j);
					if (anewName != null) {
						ImportedAtomicModel each = new ImportedFromRegisterAtomicModel();
						each.setModelName(anewName);
						each.setModelFileName(new File(file.getName()));
						retr.add(each);
					}
				}
			}
			aString = aReader.readLine();
		}
		
		(new InformDialog("Since importing from register.cpp cannot obtain ports information.\nRemember to add the necesary ports to the model before adding units.",null)).setVisible(true);
		return retr;
	}

/**
 * Imported from an .ma. Just copy the definition
 * @param model
 * @param writer
 * @throws IOException
 */
	private static void innerExportToCPP(ImportedCoupledModel model, PrintWriter writer) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(model.getExportClasspath()));
		String data = reader.readLine();
		while(data != null){
			writer.println(data);
			data = reader.readLine();
		}
	}

	private static void innerExportToCPP(EditableCoupledModel model, PrintWriter writer) throws IOException {
		//components
		writer.print("components : ");

		Enumeration allUnits = model.getModels().elements();

		while (allUnits.hasMoreElements()) {
			AbstractModelUnit aUnit = (AbstractModelUnit)allUnits.nextElement();
			if (aUnit instanceof EditableAtomicModelUnit) {
				writer.print(aUnit.getName() + "@ggad ");
			}
			else if (aUnit instanceof ImportedFromRegisterAtomicModelUnit){
				writer.print(aUnit.getName() + "@" + aUnit.getModel().getModelName() + " ");			
			}
			else{
				writer.print(aUnit.getName() + " "); 
			}
		}
		writer.println();

		//Ports
		if (model.getOutPorts().size() > 0) {
			writer.print("out : ");

			Enumeration tmpPorts = model.getOutPorts().elements();
			while (tmpPorts.hasMoreElements()) {
				AbstractPort tmpPort = (AbstractPort)tmpPorts.nextElement();
				writer.print(tmpPort.getPortId() + " ");
			}
			writer.println();
		}

		if (model.getInPorts().size() > 0) {
			writer.print("in : ");

			Enumeration tmpPorts = model.getInPorts().elements();
			while (tmpPorts.hasMoreElements()) {
				AbstractPort tmpPort = (AbstractPort)tmpPorts.nextElement();
				writer.print(tmpPort.getPortId() + " ");
			}
			writer.println();
		}

		Enumeration tmpLinks = model.getLinks().elements();
		while (tmpLinks.hasMoreElements()) {
			AbstractCoupledLink tmpLink = (AbstractCoupledLink)tmpLinks.nextElement();
			writer.println("Link : " + tmpLink.getLinkLabel());
		}
		writer.println();

		Enumeration allModels = model.getModels().elements();

		while (allModels.hasMoreElements()) {
			AbstractModelUnit aUnit = (AbstractModelUnit)allModels.nextElement();
			if (aUnit instanceof EditableAtomicModelUnit || aUnit instanceof ImportedFromCDDAtomicModelUnit) {
				AtomicModelUnit each = (AtomicModelUnit)aUnit;
				writer.println("[" + each.getName() + "]");
				writer.println("source : " + aUnit.getModel().getExportClasspath().getName());
			}
			else if (aUnit instanceof EditableCoupledModelUnit) {
				EditableCoupledModelUnit each = (EditableCoupledModelUnit)aUnit;
				writer.println("[" + each.getName() + "]");				
				innerExportToCPP((EditableCoupledModel)each.getModel(), writer);
			}
			else if (aUnit instanceof ImportedCoupledModelUnit) {
				ImportedCoupledModelUnit each = (ImportedCoupledModelUnit)aUnit;
				innerExportToCPP((ImportedCoupledModel)each.getModel(), writer);
			}
			
			//initial parameters
			if(aUnit.getModel() instanceof Initializable){
				Initializable each = (Initializable)aUnit.getModel();
				Vector params = each.getInitialParameters();
				if(params.size() > 0){
				
					writer.println("[" + aUnit.getName() + "]");		
					
					for (Iterator parmeters = params.iterator(); parmeters.hasNext();) {
						String eachParam = (String)parmeters.next();
						writer.println(eachParam);
					}
				}
			}
			writer.println();
		}
	}

	private static String toCDPPString(AbstractAtomicLink link) {
		if (link instanceof AtomicExternalLink) {
			AtomicExternalLink new_name = (AtomicExternalLink)link;
			return toCDPPString(new_name);
		}
		else if (link instanceof AtomicInternalLink) {
			AtomicInternalLink new_name = (AtomicInternalLink)link;
			return toCDPPString(new_name);
		}
		else {
			throw new RuntimeException("Link Type not contempled");
		}
	}

	private static String toCDPPString(AtomicInternalLink link) {
		String retr = commonToCDPPString(link);

		Iterator exprs = link.getExpressionsAndValues().iterator();
		while (exprs.hasNext()) {
			ExpressionAndValue each = (ExpressionAndValue)exprs.next();
			retr += (toCDPPString(each.getExpression()) + "!" + each.getValue() + " ");
		}

		retr += toCDPPString(link.getActions());
		return retr;
	}

	private static String toCDPPString(Expression expression) {
		if (expression instanceof AbstractPort) {
			AbstractPort port = (AbstractPort)expression;
			return toCDPPString(port);
		}
		else if (expression instanceof Function) {
			Function func = (Function)expression;
			return toCDPPString(func);
		}
		else {
			throw new RuntimeException("Expression not contempled Error");
		}
	}

	private static String toCDPPString(Function fun) {
		return fun.getFunctionString();
	}

	private static String toCDPPString(AbstractPort port) {
		return port.getPortId();
	}

	private static String toCDPPString(AtomicExternalLink link) {
		String retr = commonToCDPPString(link);

		//Expresion GGADT_INPUT GGADT_CONSTANT			
		if (link.getExpressionsAndValues().size() > 0) {
			ExpressionAndValue exprVal = (ExpressionAndValue)link.getExpressionsAndValues().iterator().next();
			retr += (toCDPPString(exprVal.getExpression()) + "?" + exprVal.getValue() + " ");
		}
		retr += toCDPPString(link.getActions());
		return retr;
	}

	private static String toCDPPString(Collection actions) {
		String retr = new String();

		//Actions
		if (actions.size() > 0) {
			//has actions!!!
			//rule 45   Actions -> GGADT_BEGIN ActionList GGADT_END			
			//rule 47   ActionList -> Action GGADT_SEMICOLON
			//rule 48   ActionList -> ActionList Action GGADT_SEMICOLON
			retr += "{";

			Iterator actionsIter = actions.iterator();
			while (actionsIter.hasNext()) {
				Action each = (Action)actionsIter.next();
				retr += (toCDPPString(each) + ";");
			}
			retr += "}";
		}

		return retr;
	}

	private static String toCDPPString(Action action) {
		return action.getActionString();
	}
}
