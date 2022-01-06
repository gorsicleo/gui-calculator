package hr.fer.zemris.java.gui.calc.impl;

import java.util.function.DoubleBinaryOperator;

/**Class contains binary operations for simple calculator.
 * @author gorsicleo
 *
 */
public class BinaryOperators {

	public static final DoubleBinaryOperator SUM = (a,b) -> a+b;
	
	public static final DoubleBinaryOperator SUB = (a,b) -> a-b;
	
	public static final DoubleBinaryOperator MUL = (a,b) -> a*b;
	
	public static final DoubleBinaryOperator DIV = (a,b) -> a/b;
	
	public static final DoubleBinaryOperator POW = (b,e) -> Math.pow(b, e);
	
	public static final DoubleBinaryOperator ROOT = (b,e) -> Math.pow(b, 1.0/e);
	
	
}
