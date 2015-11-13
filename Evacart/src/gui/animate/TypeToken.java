/*
 * Created on 06-oct-2003
 * @author  jicidre@dc.uba.ar
 */
package gui.animate;

/**
 * @author Administrador3 jicidre@dc.uba.ar
 * Uno de los tipos de token de un archivo de log
 *
 */
public class TypeToken {
	public static int MENSAJE_AST = 0;
	public static int MENSAJE_D = 1;
	public static int MENSAJE_Y = 2;
	public static int MENSAJE_X = 3;

	private int type = -1;
	/**
	 * @param type
	 */
	public TypeToken(String type) {
		if("Mensaje Y".equals(type) || "Y".equals(type)){
			this.type = MENSAJE_Y;
		}
		else if("Mensaje D".equals(type) || "D".equals(type)){
			this.type = MENSAJE_D;
		} 
		else if("Mensaje X".equals(type) || "X".equals(type)){
			this.type = MENSAJE_X;		
		} 
		else if("Mensaje *".equals(type) || "*".equals(type)){
			this.type = MENSAJE_AST;		
		} 
		else{
			//throw new RuntimeException("Type not contempled in parser : " + type);
		}
	}

	/**
	 * 
	 */
	public int getType() {
		return type;
		
	}
	
	}
