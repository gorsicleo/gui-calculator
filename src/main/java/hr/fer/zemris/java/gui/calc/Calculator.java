package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.calc.impl.BinaryOperators;
import hr.fer.zemris.java.gui.calc.impl.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.impl.UnaryOperators;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

@SuppressWarnings("serial")
public class Calculator extends JFrame{
	private static final Color FACEBOOK_BLUE_COLOR = new Color(59, 89, 182);
	private static final Color GRAY_COLOR = Color.GRAY;
	private static final Color DARK_GRAY_COLOR = Color.DARK_GRAY;

	private final ActionListener numberPressedListener = (e) -> numberPressed(((JButton) e.getSource()).getText());

	private CalcModelImpl engine;
	private JLabel display;
	private boolean isInvertedState;
	private Set<JButton> inverseButtons = new HashSet<JButton>();
	private Stack<Double> memory = new Stack<Double>();
	
	
	
	public Calculator() {
		initGUI();
	}


	

	private void initGUI() {
		JPanel panel = new JPanel(new CalcLayout(5));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		engine = new CalcModelImpl();
		panel.setBackground(Color.WHITE);
		addDisplay(panel);
		
		setTitle("Facebook Calculator");
		
		addNumberButtons(panel);
		
		addOperatorButtons(panel);
		
		addFunctionButtons(panel);
		
		getContentPane().add(panel);
		
		pack();
		
	}


	private void addFunctionButtons(JPanel panel) {
		JButton clear = createCalculatorButton("clr", DARK_GRAY_COLOR);
		panel.add(clear, new RCPosition(1, 7));

		JButton res = createCalculatorButton("reset", DARK_GRAY_COLOR);
		panel.add(res, new RCPosition(2, 7));

		JButton push = createCalculatorButton("push", DARK_GRAY_COLOR);
		panel.add(push, new RCPosition(3, 7));

		JButton pop = createCalculatorButton("pop", DARK_GRAY_COLOR);
		//pop.addActionListener();
		panel.add(pop, new RCPosition(4, 7));

		JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener( (e) -> {
			isInvertedState = !isInvertedState;
			inverseButtons.forEach( (button) -> invertButton(button));
		});
		panel.add(inv, new RCPosition(5, 7));
		
	}


	

	private void invertButton(JButton button) {
		String[][] namePairs = {{"sin", "cos", "tan", "ctg", "x^n", "ln", "log" },
				                {"arcsin", "arccos", "arctan", "arcctg", "x^(1/n)", "e^x", "10^x" }};

		String buttonText = button.getText();

		for (int i = 0; i< 7; i++) {
			if (namePairs[0][i].equals(buttonText)) {
				button.setText(namePairs[1][i]);
			} 
			
			if (namePairs[1][i].equals(buttonText)) {
				button.setText(namePairs[0][i]);
			}
		}
	}




	private void addOperatorButtons(JPanel panel) {
		JButton decimal = createCalculatorButton(".",GRAY_COLOR);
		panel.add(decimal, new RCPosition(5, 5));

		JButton swapSign = createCalculatorButton("+/-", GRAY_COLOR);
		panel.add(swapSign, new RCPosition(5, 4));

		JButton inverse = createCalculatorButton("1/x",GRAY_COLOR);
		panel.add(inverse, new RCPosition(2, 1));
		
		JButton log = createCalculatorButton("log",GRAY_COLOR);
		panel.add(log, new RCPosition(3, 1));
		inverseButtons.add(log);

		JButton ln = createCalculatorButton("ln",GRAY_COLOR);
		panel.add(ln, new RCPosition(4, 1));
		inverseButtons.add(ln);
		
		JButton sin = createCalculatorButton("sin",GRAY_COLOR);
		panel.add(sin, new RCPosition(2, 2));
		inverseButtons.add(sin);

		JButton cos = createCalculatorButton("cos",GRAY_COLOR);
		panel.add(cos, new RCPosition(3, 2));
		inverseButtons.add(cos);

		JButton tan = createCalculatorButton("tan",GRAY_COLOR);
		panel.add(tan, new RCPosition(4, 2));
		inverseButtons.add(tan);

		JButton ctg = createCalculatorButton("ctg",GRAY_COLOR);
		panel.add(ctg, new RCPosition(5, 2));
		inverseButtons.add(ctg);
		
		JButton multiply = createCalculatorButton("*",GRAY_COLOR);
		panel.add(multiply, new RCPosition(3, 6));

		JButton divide = createCalculatorButton("/",GRAY_COLOR);
		panel.add(divide, new RCPosition(2, 6));

		JButton add = createCalculatorButton("+",GRAY_COLOR);
		panel.add(add, new RCPosition(5, 6));

		JButton sub = createCalculatorButton("-",GRAY_COLOR);
		panel.add(sub, new RCPosition(4, 6));

		JButton equal = createCalculatorButton("=",GRAY_COLOR);
		panel.add(equal, new RCPosition(1, 6));

		JButton power = createCalculatorButton("x^n",GRAY_COLOR);
		panel.add(power, new RCPosition(5, 1));
		inverseButtons.add(power);
		
	}



	

	private void addDisplay(JPanel panel) {
		display = new JLabel(engine.getDisplay());
		display.setPreferredSize(getPreferredSize());
		display.setHorizontalAlignment(JTextField.RIGHT);
		
		display.setBackground(Color.white);
		display.setFont(new Font("Tahoma", Font.BOLD, 18));
		display.setOpaque(true);
		display.setBorder(BorderFactory.createLineBorder(FACEBOOK_BLUE_COLOR, 1));
		panel.add(display,new RCPosition(1, 1));
	}
	
	private JButton createCalculatorButton(String text, Color backgroundColor) {
		JButton bt = new JButton(text);
		bt.setBackground(backgroundColor);
        bt.setForeground(Color.WHITE);
        bt.setFocusPainted(false);
        bt.setFont(new Font("Tahoma", Font.BOLD, 12));
        addListenerForCreatedButton(text, bt);
		return bt;
	}

	private void addListenerForCreatedButton(String text, JButton bt) {
		
		if (Character.isDigit(text.charAt(0))) {
        	bt.addActionListener(numberPressedListener);
        }
		
		switch (text) {
		case "x^n":
			bt.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.POW, BinaryOperators.ROOT));
			break;
			
		case "sin":
			bt.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.SIN, UnaryOperators.ASIN));
			break;
			
		case "cos":
			bt.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.COS, UnaryOperators.ACOS));
			break;
			
		case "tan":
			bt.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.TAN, UnaryOperators.ATAN));
			break;
			
		case "ctg":
			bt.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.COT, UnaryOperators.ACOT));
			break;
			
		case "ln":
			bt.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.LN, UnaryOperators.E_POW));
			break;
			
		case "log":
			bt.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.DEC_LOG, UnaryOperators.DEC_POW));
			break;
			
		case "1/x":
			bt.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.RECIPROCAL,UnaryOperators.RECIPROCAL));
			break;
			
		case "+/-":
			bt.addActionListener((e) -> invertPressed());
			break;
			
		case ".":
			bt.addActionListener((e) -> decimalPressed());
			break;
		
		case "=":
			bt.addActionListener((e) -> equalsPressed());
			break;
			
		case "-":
			bt.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.SUB,BinaryOperators.SUB));
			break;
			
		case "+":
			bt.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.SUM,BinaryOperators.SUM));
			break;
			
		case "*":
			bt.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.MUL,BinaryOperators.MUL));
			break;
			
		case "/":
			bt.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.DIV,BinaryOperators.DIV));
			break;
			
		case "clr":
			bt.addActionListener((e) -> clearPressed());
			break;
			
		case "reset":
			bt.addActionListener((e) -> resetPressed());
			break;
			
		case "push":
			bt.addActionListener((e) -> pushPressed());
			break;
			
		case "pop":
			bt.addActionListener((e) -> popPressed());
			break;
			
		default:
			break;
		}
	}







	private void addNumberButtons(JPanel panel) {
		JButton bt0 = createCalculatorButton("0",FACEBOOK_BLUE_COLOR);
		panel.add(bt0, new RCPosition(5, 3));
		
		JButton bt1 = createCalculatorButton("1",FACEBOOK_BLUE_COLOR);
		panel.add(bt1, new RCPosition(4, 3));
		
		JButton bt2 = createCalculatorButton("2",FACEBOOK_BLUE_COLOR);
		panel.add(bt2, new RCPosition(4, 4));
		
		JButton bt3 = createCalculatorButton("3",FACEBOOK_BLUE_COLOR);
		panel.add(bt3, new RCPosition(4, 5));
		
		JButton bt4 = createCalculatorButton("4",FACEBOOK_BLUE_COLOR);
		panel.add(bt4, new RCPosition(3, 3));
		
		JButton bt5 = createCalculatorButton("5",FACEBOOK_BLUE_COLOR);
		panel.add(bt5, new RCPosition(3, 4));
		
		JButton bt6 = createCalculatorButton("6",FACEBOOK_BLUE_COLOR);
		panel.add(bt6, new RCPosition(3, 5));
		
		JButton bt7 = createCalculatorButton("7",FACEBOOK_BLUE_COLOR);
		panel.add(bt7, new RCPosition(2, 3));
	
		JButton bt8 = createCalculatorButton("8",FACEBOOK_BLUE_COLOR);
		panel.add(bt8, new RCPosition(2, 4));

		JButton bt9 = createCalculatorButton("9",FACEBOOK_BLUE_COLOR);
		panel.add(bt9, new RCPosition(2, 5));

	}

	
	
	private void numberPressed(String text) {
		engine.insertDigit(Integer.parseInt(text));
		updateDisplay();
	}

	private void updateDisplay() {
		display.setText(engine.getDisplay());
	}
	
	private void invertPressed() {
		engine.swapSign();
		updateDisplay();
	}
	
	private void decimalPressed() {
		engine.insertDecimalPoint();
		updateDisplay();
	}
	
	private void clearPressed() {
		engine.clear();
		updateDisplay();
	}
	
	private void resetPressed() {
		memory.clear();
		clearPressed();
	}
	
	private void pushPressed() {
		memory.push(engine.getValue());
	}
	
	private void popPressed() {
		if (memory.isEmpty()) {
			display.setText("MEMORY EMPTY");
			return;
		}
		double savedNumber = memory.pop() ;
		engine.setValue(savedNumber);
		updateDisplay();
		engine.clear();
	}
	
	private void binaryOperatorPressed(DoubleBinaryOperator operator, DoubleBinaryOperator invertedOperator) {
		engine.setActiveOperand(engine.getValue());
		
		if (!isInvertedState) engine.setPendingBinaryOperation(operator);
		else engine.setPendingBinaryOperation(invertedOperator);
		
		engine.clear();
		updateDisplay();
	}
	
	private void unaryOperatorPressed(DoubleUnaryOperator operator,DoubleUnaryOperator invertedOperator ) {
		double result = (isInvertedState)? invertedOperator.applyAsDouble(engine.getValue()):operator.applyAsDouble(engine.getValue());
		engine.setValue(result);
		updateDisplay();
		engine.clear();
	}
	
	
	private void equalsPressed() {
		double operand1 = engine.getActiveOperand();
		double operand2 = engine.getValue();
		double result = engine.getPendingBinaryOperation().applyAsDouble(operand1, operand2);
		engine.setValue(result);
		updateDisplay();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
	
	
}
