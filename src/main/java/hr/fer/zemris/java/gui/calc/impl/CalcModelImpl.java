package hr.fer.zemris.java.gui.calc.impl;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel {

	private static final String OVERFLOW_ERROR = "Overflow";

	private static final String ACTIVE_OPERAND_NOT_SET_ERROR = "Cannot get active operand when it is not set.";

	private static final String POINT_ALREADY_INSERTED_ERROR = "Decimal point is already inserted.";

	private static final String NOT_PARSABLE_ERROR = "Given input combined with previous inputs cannot be parsed to numeric value";

	private static final String NOT_EDIATBLE_ERROR = "Calculator is not in editable state!";

	// private JLabel display;

	private boolean isEditable;

	private boolean isNegative;

	private String input;

	private double numericInput;

	private String display;

	private Double activeOperand;

	private boolean isActiveOperandSet;

	private DoubleBinaryOperator pendingOperation;

	public CalcModelImpl() {
		isEditable = true;
		isNegative = false;
		input = "";
		numericInput = 0.0;
		display = null;
		activeOperand = 0.0;
		pendingOperation = null;
		isActiveOperandSet = false;
	}

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
		return numericInput;
	}

	@Override
	public void setValue(double value) {
		// method toString already supports infinity and NaN
		display = Double.toString(Math.abs(value));
		numericInput = value;
		isNegative = value < 0 ? true : false;
		isEditable = false;
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		isActiveOperandSet = true;
		input = "";
		numericInput = 0.0;
		display = null;

	}

	@Override
	public void clearAll() {
		isEditable = true;
		isNegative = false;
		input = "";
		numericInput = 0.0;
		display = null;
		activeOperand = 0.0;
		pendingOperation = null;
		isActiveOperandSet = false;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		numericInput = -numericInput;
		display = input = Double.toString(numericInput);

		display = (display.endsWith(".0")) ? display.substring(0, display.length() - 2) : display;
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException(NOT_EDIATBLE_ERROR);
		}

		if (input.contains(".")) {
			throw new CalculatorInputException(POINT_ALREADY_INSERTED_ERROR);
		}

		input = input + ".";
		try {
			numericInput = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			throw new CalculatorInputException(NOT_PARSABLE_ERROR);
		}

	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {

		if (!isEditable) {
			throw new CalculatorInputException(NOT_EDIATBLE_ERROR);
		}
		parseNewDigit(digit);
		input = (input + digit);
		trimLeadingZeroes();
		display = input;

	}

	private void parseNewDigit(int digit) {
		try {
			numericInput = Double.parseDouble(input + digit);
		} catch (NumberFormatException e) {
			throw new CalculatorInputException(NOT_PARSABLE_ERROR);
		}

		if (numericInput >= Double.MAX_VALUE) {
			throw new CalculatorInputException(OVERFLOW_ERROR);
		}
	}

	private void trimLeadingZeroes() {
		input = input.startsWith("0") ? input.substring(1) : input;
		input = input.startsWith(".") ? "0" + input : input;
		input = (input.isEmpty() ? "0" : "") + input;
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException(ACTIVE_OPERAND_NOT_SET_ERROR);
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isActiveOperandSet = true;

	}

	@Override
	public void clearActiveOperand() {
		isActiveOperandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}

	@Override
	public String toString() {
		if (display != null) {
			return (isNegative == true) ? "-" + display : display;
		} else {
			return (isNegative == true) ? "-0" : "0";
		}
	}

}