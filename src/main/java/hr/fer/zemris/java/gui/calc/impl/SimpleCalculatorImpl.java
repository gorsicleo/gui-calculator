package hr.fer.zemris.java.gui.calc.impl;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class SimpleCalculatorImpl implements CalcModel{

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setValue(double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActiveOperandSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearActiveOperand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		// TODO Auto-generated method stub
		
	}

}
