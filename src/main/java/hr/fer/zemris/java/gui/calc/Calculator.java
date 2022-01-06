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

/**Driver class for calculator. It uses {@link CalcModelImpl} as calculator logic.
 * @author gorsicleo
 *
 */
@SuppressWarnings("serial")
public class Calculator extends JFrame{
	
	private static final Color FACEBOOK_BLUE_COLOR = new Color(59, 89, 182);
	
	private static final Color GRAY_COLOR = Color.GRAY;
	
	private static final Color DARK_GRAY_COLOR = Color.DARK_GRAY;

	/**Function that extracts button text*/
	private final ActionListener numberPressedListener = (e) -> numberPressed(((JButton) e.getSource()).getText());

	/**Calculator logic implementation*/
	private CalcModelImpl engine;
	
	/**Calculator display*/
	private JLabel display;
	
	private boolean isInvertedState;
	
	/**Reference to all buttons that change text when inverted*/
	private Set<JButton> inverseButtons = new HashSet<JButton>();
	
	/**Stack memory*/
	private Stack<Double> memory = new Stack<Double>();
	
	
	
	/**Constructor.*/
	public Calculator() {
		initGUI();
	}


	
	/**Method creates new panel and {@link CalcModelImpl}.
	 * Then calls methods to put all buttons and other calculator components*/
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


	/**Method creates function keys and puts them to calculator*/
	private void addFunctionButtons(JPanel panel) {
		JButton clear = createCalculatorButton("clr", DARK_GRAY_COLOR);
		panel.add(clear, new RCPosition(1, 7));

		JButton res = createCalculatorButton("reset", DARK_GRAY_COLOR);
		panel.add(res, new RCPosition(2, 7));

		JButton push = createCalculatorButton("push", DARK_GRAY_COLOR);
		panel.add(push, new RCPosition(3, 7));

		JButton pop = createCalculatorButton("pop", DARK_GRAY_COLOR);
		panel.add(pop, new RCPosition(4, 7));

		JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener( (e) -> {
			isInvertedState = !isInvertedState;
			inverseButtons.forEach( (button) -> invertButton(button));
		});
		panel.add(inv, new RCPosition(5, 7));
		
	}


	
	/**function that iterates through all buttons that change 
	 * their names in inverted state and sets their text
	*/
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




	/**Method creates operator keys and puts them to calculator*/
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



	
	/**Method creates display and puts it to calculator*/
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
	
	/**Method creates one button, sets button background to given color and sets listener*/
	private JButton createCalculatorButton(String text, Color backgroundColor) {
		JButton bt = new JButton(text);
		bt.setBackground(backgroundColor);
        bt.setForeground(Color.WHITE);
        bt.setFocusPainted(false);
        bt.setFont(new Font("Tahoma", Font.BOLD, 12));
        addListenerForCreatedButton(text, bt);
		return bt;
	}
	

	/**Function adds listener to given button according to button text.
	 * @param text button text
	 * @param bt button for adding listener
	 */
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
			bt.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.RECIPROCAL));
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
			bt.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.SUB));
			break;
			
		case "+":
			bt.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.SUM));
			break;
			
		case "*":
			bt.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.MUL));
			break;
			
		case "/":
			bt.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.DIV));
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






	/**Method creates number keys and puts them to calculator*/
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

	
	/**Function calls {@link CalcModelImpl} insertDigit function when number key is pressed*/
	private void numberPressed(String text) {
		engine.insertDigit(Integer.parseInt(text));
		updateDisplay();
	}

	/**Refreshes display with latest display state from {@link CalcModelImpl}*/
	private void updateDisplay() {
		display.setText(engine.getDisplay());
	}
	
	/**Function calls {@link CalcModelImpl} swapSign function when invert key is pressed*/
	private void invertPressed() {
		engine.swapSign();
		updateDisplay();
	}
	
	/**Function calls {@link CalcModelImpl} to insert decimal point*/
	private void decimalPressed() {
		engine.insertDecimalPoint();
		updateDisplay();
	}
	
	/**Function calls {@link CalcModelImpl} to clear its state after clear button has been pressed*/
	private void clearPressed() {
		engine.clear();
		updateDisplay();
	}
	
	/**Function calls {@link CalcModelImpl} to reset its state after reset button has been pressed*/
	private void resetPressed() {
		memory.clear();
		clearPressed();
	}
	
	/**Method pushes to calculator stack memory number that is currently displayed*/
	private void pushPressed() {
		memory.push(engine.getValue());
	}
	/**Method pops one number form memory and sets it to calculator current value*/
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
	
	/**Function calls {@link CalcModelImpl} to update pendingBinaryOperator. */
	private void binaryOperatorPressed(DoubleBinaryOperator operator, DoubleBinaryOperator invertedOperator) {
		engine.setActiveOperand(engine.getValue());
		
		if (!isInvertedState) engine.setPendingBinaryOperation(operator);
		else engine.setPendingBinaryOperation(invertedOperator);
		
		engine.clear();
		updateDisplay();
	}
	
	/**Overload for functions that are not impacted by inverse checkbox*/
	private void binaryOperatorPressed(DoubleBinaryOperator operator) {
		binaryOperatorPressed(operator, operator);
	}
	
	/**Overload for functions that are not impacted by inverse checkbox*/
	private void unaryOperatorPressed(DoubleUnaryOperator operator) {
		unaryOperatorPressed(operator, operator);
	}
	
	/**Calculates and displays result of current value in calculator after operator is applied*/
	private void unaryOperatorPressed(DoubleUnaryOperator operator,DoubleUnaryOperator invertedOperator ) {
		double result = (isInvertedState)? invertedOperator.applyAsDouble(engine.getValue()):operator.applyAsDouble(engine.getValue());
		engine.setValue(result);
		updateDisplay();
		engine.clear();
	}
	
	/**Executes binary operation*/
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
