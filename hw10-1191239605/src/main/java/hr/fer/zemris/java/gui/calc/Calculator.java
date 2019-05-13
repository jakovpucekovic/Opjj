package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 *	Calculator TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Calculator extends JFrame{

	private static final long serialVersionUID = 1L;

	private CalcModelImpl calc;
	
	
	/**
	 * 	Constructs a new Calculator.
	 * 	TODO javadoc
	 */
	public Calculator() {
		super();
		calc = new CalcModelImpl();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		setLocation(20, 50);
//		setSize(300, 200);
		
		initGUI();	
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout());
//		cp.setBackground(Color.WHITE); //treba biti light gray
		
		//label
		JLabel show = new JLabel();
		show.setBackground(Color.YELLOW);
		show.setFont(show.getFont().deriveFont(30.f));
		show.setHorizontalAlignment(SwingConstants.RIGHT);
		show.setOpaque(true);
		show.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		calc.addCalcValueListener(new CalcValueListener() {
			
			@Override
			public void valueChanged(CalcModel model) {
				show.setText(calc.toString());
			}
		});
		cp.add(show, new RCPosition(1, 1));
		
		//numbers
		cp.add(numberButton("0"), new RCPosition(5, 3));
		cp.add(numberButton("1"), new RCPosition(4, 3));
		cp.add(numberButton("2"), new RCPosition(4, 4));
		cp.add(numberButton("3"), new RCPosition(4, 5));
		cp.add(numberButton("4"), new RCPosition(3, 3));
		cp.add(numberButton("5"), new RCPosition(3, 4));
		cp.add(numberButton("6"), new RCPosition(3, 5));
		cp.add(numberButton("7"), new RCPosition(2, 3));
		cp.add(numberButton("8"), new RCPosition(2, 4));
		cp.add(numberButton("9"), new RCPosition(2, 5));
		
		//binary operators
		cp.add(operationButton("+"), new RCPosition(5, 6));
		cp.add(operationButton("-"), new RCPosition(4, 6));
		cp.add(operationButton("*"), new RCPosition(3, 6));
		cp.add(operationButton("/"), new RCPosition(2, 6));

		//=
		JButton equal = button("=");
		equal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(calc.isActiveOperandSet()) {
					calc.setValue(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
					calc.clearActiveOperand();
					calc.setPendingBinaryOperation(null);
				}
			}
		});
		cp.add(equal, new RCPosition(1, 6));
		
		//reset
		JButton reset = button("reset");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calc.clearAll();
			}
		});
		cp.add(reset, new RCPosition(2, 7));

		//switchSign
		JButton switchSign= button("+/-");
		switchSign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calc.swapSign();
			}
		});
		cp.add(switchSign, new RCPosition(5, 4));
		
		//decimal point
		JButton decPoint = button("."); 
		decPoint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calc.insertDecimalPoint();
			}
		});
		cp.add(decPoint, new RCPosition(5, 5));

		//inverse
		JButton inverse = button("1/x");
		inverse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(calc.isActiveOperandSet()) {
					calc.setActiveOperand(1.f / calc.getActiveOperand());
				}else {
					calc.setValue(1.f / calc.getValue());
				}
			}
		});
		cp.add(inverse, new RCPosition(2, 1));

		
		cp.add(button("sin"), new RCPosition(2, 2));
		cp.add(button("cos"), new RCPosition(3, 2));
		cp.add(button("tan"), new RCPosition(4, 2));
		cp.add(button("ctg"), new RCPosition(5, 2));
		cp.add(button("log"), new RCPosition(3, 1));
		cp.add(button("ln"), new RCPosition(4, 1));
		
		cp.add(button("x^n"), new RCPosition(5, 1));//on je poseban jer je binary, a ima inverznu operaciju


		JButton clear = button("clr");
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calc.clear();
			}
		});
		cp.add(clear, new RCPosition(1, 7));
		
		
		cp.add(button("push"), new RCPosition(3, 7));
		cp.add(button("pop"), new RCPosition(4, 7));
		
		//Checkbox
		JCheckBox checkbox = new JCheckBox("Inv");
		checkbox.setBackground(Color.LIGHT_GRAY);
		checkbox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
		cp.add(checkbox, new RCPosition(5, 7));
	}
	
	private JButton numberButton(String text) {
		JButton button = new JButton(text);
		button.setBackground(Color.LIGHT_GRAY);
		button.setOpaque(true);
		button.setFont(button.getFont().deriveFont(30.f));
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!calc.isEditable()){
					calc.clear();
				}
				calc.insertDigit(Integer.parseInt(text));
			}
		});
		return button;
	}
	
	private JButton operationButton(String text) {
		JButton button = new JButton(text);
		button.setBackground(Color.LIGHT_GRAY);
		button.setOpaque(true);
		button.setFont(button.getFont().deriveFont(30.f));
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
//				if(calc.getPendingBinaryOperation() != null){
//					calc.setValue(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
//					calc.clearActiveOperand();
//				}
					
				calc.setPendingBinaryOperation(new DoubleBinaryOperator() {
					
					@Override
					public double applyAsDouble(double left, double right) {
						switch(text) {
							case "+": return left + right;
							case "-": return left - right;
							case "*": return left * right;
							case "/": return left / right;
							case "x^n": return Math.pow(left, right); //TODO vidi kaj tu
							default : throw new IllegalArgumentException("Unknown operation");
						}
					}
				});
				
				calc.setActiveOperand(calc.getValue());
//				calc.clear();//TODO kako izbjeci brisanje koje imam?
			}
		});
		return button;
	}
	
	
	private JButton button(String text) {
		JButton button = new JButton(text);
		button.setBackground(Color.LIGHT_GRAY);
		button.setOpaque(true);
		button.setFont(button.getFont().deriveFont(30.f));
		return button;
	}
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
