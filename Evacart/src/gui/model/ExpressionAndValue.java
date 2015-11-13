package gui.model;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public class ExpressionAndValue {
	private Expression expression;
	private String value;

	public ExpressionAndValue(Expression expression, String value){
		this.setExpression(expression);
		this.setValue(value);
	}

	/**
	 * Returns the expression.
	 * @return Expression
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * Returns the value.
	 * @return int
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the expression.
	 * @param expression The expression to set
	 */
	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	/**
	 * Sets the value.
	 * @param value The value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return this.getExpression().toString() + "->" + this.getValue();	
	}

}
