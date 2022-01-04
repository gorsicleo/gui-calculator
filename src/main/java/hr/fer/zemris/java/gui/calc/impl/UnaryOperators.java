package hr.fer.zemris.java.gui.calc.impl;

import java.util.function.DoubleUnaryOperator;

public class UnaryOperators {

	public static final DoubleUnaryOperator DEC_LOG = Math::log10;
	
	public static final DoubleUnaryOperator DEC_POW = (exp) -> Math.pow(10, exp);
	
	public static final DoubleUnaryOperator LN = Math::log;
	
	public static final DoubleUnaryOperator E_POW = (exp) -> Math.pow(Math.E, exp);
	
	public static final DoubleUnaryOperator RECIPROCAL = (a) -> 1.0/a;
	
	public static final DoubleUnaryOperator COS = Math::cos;
	
	public static final DoubleUnaryOperator ACOS = Math::acos;
	
	public static final DoubleUnaryOperator SIN = Math::sin;
	
	public static final DoubleUnaryOperator ASIN = Math::asin;
	
	public static final DoubleUnaryOperator TAN = Math::tan;
	
	public static final DoubleUnaryOperator ATAN = Math::atan;
	
	public static final DoubleUnaryOperator COT = (a) -> 1.0/Math.tan(a);
	
	public static final DoubleUnaryOperator ACOT = (a) -> Math.atan(1.0/1.0/Math.tan(a));
	
}
