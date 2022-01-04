package hr.fer.zemris.java.gui.calc.impl;

import java.util.function.DoubleBinaryOperator;

public class BinaryOperators {

	public static final DoubleBinaryOperator SUM = (a,b) -> a+b;
	
	public static final DoubleBinaryOperator SUB = (a,b) -> a-b;
	
	public static final DoubleBinaryOperator MUL = (a,b) -> a*b;
	
	public static final DoubleBinaryOperator DIV = (a,b) -> a/b;
	
	public static final DoubleBinaryOperator POW = (b,e) -> Math.pow(b, e);
	
	public static final DoubleBinaryOperator RECIPROCAL_POW = (b,e) -> Math.pow(b, 1.0/e);
	
	
}
