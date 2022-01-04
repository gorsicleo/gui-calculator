package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
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
	
	private void binaryOperatorPressed(DoubleBinaryOperator operator) {
		engine.setActiveOperand(engine.getValue());
		engine.setPendingBinaryOperation(operator);
		engine.clear();
		updateDisplay();
	}
	
	private void unaryOperatorPressed(DoubleUnaryOperator operator) {
		operator.applyAsDouble(engine.getValue());
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
	


	private static final Color FACEBOOK_BLUE_COLOR = new Color(59, 89, 182);
	private static final Color GRAY_COLOR = Color.GRAY;
	private static final Color DARK_GRAY_COLOR = Color.DARK_GRAY;
	
	private final ActionListener numberPressedListener = (e) -> numberPressed(((JButton) e.getSource()).getText());

	private CalcModelImpl engine;
	
	private JLabel display;
	
	private boolean isInvertedState;
	
	
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
		JButton clear = createMaterialButton("clr", DARK_GRAY_COLOR);
		clear.addActionListener((e) -> clearPressed());
		panel.add(clear, new RCPosition(1, 7));

		JButton res = createMaterialButton("res", DARK_GRAY_COLOR);
		//res.addActionListener(new ClrResAction());
		panel.add(res, new RCPosition(2, 7));

		JButton push = createMaterialButton("push", DARK_GRAY_COLOR);
		//push.addActionListener(new PushPopAction());
		panel.add(push, new RCPosition(3, 7));

		JButton pop = createMaterialButton("pop", DARK_GRAY_COLOR);
		//pop.addActionListener(new PushPopAction());
		panel.add(pop, new RCPosition(4, 7));

		JCheckBox inv = new JCheckBox("Inv");
		//inv.addActionListener(new InvertAction());
		panel.add(inv, new RCPosition(5, 7));
		
	}


	private void addOperatorButtons(JPanel panel) {
		JButton decimal = createMaterialButton(".",GRAY_COLOR);
		decimal.addActionListener((e) -> decimalPressed());
		panel.add(decimal, new RCPosition(5, 5));

		JButton swapSign = createMaterialButton("+/-", GRAY_COLOR);
		swapSign.addActionListener((e) -> invertPressed());
		panel.add(swapSign, new RCPosition(5, 4));

		JButton inverse = createMaterialButton("1/x",GRAY_COLOR);
		inverse.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.RECIPROCAL));
		panel.add(inverse, new RCPosition(2, 1));

		JButton log = createMaterialButton("log",GRAY_COLOR);
		log.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.DEC_LOG));
		panel.add(log, new RCPosition(3, 1));

		JButton ln = createMaterialButton("ln",GRAY_COLOR);
		ln.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.LN));
		panel.add(ln, new RCPosition(4, 1));

		JButton sin = createMaterialButton("sin",GRAY_COLOR);
		sin.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.SIN));
		panel.add(sin, new RCPosition(2, 2));

		JButton cos = createMaterialButton("cos",GRAY_COLOR);
		cos.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.COS));
		panel.add(cos, new RCPosition(3, 2));

		JButton tan = createMaterialButton("tan",GRAY_COLOR);
		tan.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.TAN));
		panel.add(tan, new RCPosition(4, 2));

		JButton ctg = createMaterialButton("ctg",GRAY_COLOR);
		ctg.addActionListener((e) -> unaryOperatorPressed(UnaryOperators.COT));
		panel.add(ctg, new RCPosition(5, 2));
		
		JButton multiply = createMaterialButton("*",GRAY_COLOR);
		multiply.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.MUL));
		panel.add(multiply, new RCPosition(3, 6));

		JButton divide = createMaterialButton("/",GRAY_COLOR);
		divide.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.DIV));
		panel.add(divide, new RCPosition(2, 6));

		JButton add = createMaterialButton("+",GRAY_COLOR);
		add.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.SUM));
		panel.add(add, new RCPosition(5, 6));

		JButton sub = createMaterialButton("-",GRAY_COLOR);
		sub.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.SUB));
		panel.add(sub, new RCPosition(4, 6));

		JButton equal = createMaterialButton("=",GRAY_COLOR);
		equal.addActionListener((e) -> equalsPressed());
		panel.add(equal, new RCPosition(1, 6));

		JButton power = createMaterialButton("x^n",GRAY_COLOR);
		power.addActionListener((e) -> binaryOperatorPressed(BinaryOperators.POW));
		panel.add(power, new RCPosition(5, 1));
		
	}



	

	private void addDisplay(JPanel panel) {
		display = new JLabel(engine.getDisplay());
		
		display.setHorizontalAlignment(JTextField.RIGHT);
		
		display.setBackground(Color.white);
		display.setFont(new Font("Tahoma", Font.BOLD, 18));
		display.setOpaque(true);
		display.setBorder(BorderFactory.createLineBorder(FACEBOOK_BLUE_COLOR, 1));
		panel.add(display,new RCPosition(1, 1));
	}
	
	private JButton createMaterialButton(String text, Color backgroundColor) {
		JButton bt = new JButton(text);
		bt.setBackground(backgroundColor);
        bt.setForeground(Color.WHITE);
        bt.setFocusPainted(false);
        bt.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		return bt;
	}


	private void addNumberButtons(JPanel panel) {
		JButton bt7 = createMaterialButton("7",FACEBOOK_BLUE_COLOR);
		
		bt7.addActionListener(numberPressedListener);
		panel.add(bt7, new RCPosition(2, 3));

		JButton bt4 = createMaterialButton("4",FACEBOOK_BLUE_COLOR);
		bt4.addActionListener(numberPressedListener);
		panel.add(bt4, new RCPosition(3, 3));

		JButton bt1 = createMaterialButton("1",FACEBOOK_BLUE_COLOR);
		bt1.addActionListener(numberPressedListener);
		panel.add(bt1, new RCPosition(4, 3));

		JButton bt0 = createMaterialButton("0",FACEBOOK_BLUE_COLOR);
		bt0.addActionListener(numberPressedListener);
		panel.add(bt0, new RCPosition(5, 3));

		JButton bt8 = createMaterialButton("8",FACEBOOK_BLUE_COLOR);
		bt8.addActionListener(numberPressedListener);
		panel.add(bt8, new RCPosition(2, 4));

		JButton bt5 = createMaterialButton("5",FACEBOOK_BLUE_COLOR);
		bt5.addActionListener(numberPressedListener);
		panel.add(bt5, new RCPosition(3, 4));

		JButton bt2 = createMaterialButton("2",FACEBOOK_BLUE_COLOR);
		bt2.addActionListener(numberPressedListener);
		panel.add(bt2, new RCPosition(4, 4));

		JButton bt9 = createMaterialButton("9",FACEBOOK_BLUE_COLOR);
		bt9.addActionListener(numberPressedListener);
		panel.add(bt9, new RCPosition(2, 5));

		JButton bt6 = createMaterialButton("6",FACEBOOK_BLUE_COLOR);
		bt6.addActionListener(numberPressedListener);
		panel.add(bt6, new RCPosition(3, 5));

		JButton bt3 = createMaterialButton("3",FACEBOOK_BLUE_COLOR);
		bt3.addActionListener(numberPressedListener);
		panel.add(bt3, new RCPosition(4, 5));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
	
	
}
