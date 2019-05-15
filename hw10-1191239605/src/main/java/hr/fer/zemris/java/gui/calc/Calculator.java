package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

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

	/**Stores the {@link CalcModelImpl} which does the calculations.*/
	private CalcModelImpl calc;
	
	
	/**
	 * 	Constructs a new {@link Calculator}.
	 */
	public Calculator() {
		super();
		calc = new CalcModelImpl();
				
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		setLocation(20, 50);
		
		initGUI();	
	}
	
	/**
	 *	Initializes the GUI, adds all the buttons and labels. 
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
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
		cp.add(new NumberButton("0"), new RCPosition(5, 3));
		cp.add(new NumberButton("1"), new RCPosition(4, 3));
		cp.add(new NumberButton("2"), new RCPosition(4, 4));
		cp.add(new NumberButton("3"), new RCPosition(4, 5));
		cp.add(new NumberButton("4"), new RCPosition(3, 3));
		cp.add(new NumberButton("5"), new RCPosition(3, 4));
		cp.add(new NumberButton("6"), new RCPosition(3, 5));
		cp.add(new NumberButton("7"), new RCPosition(2, 3));
		cp.add(new NumberButton("8"), new RCPosition(2, 4));
		cp.add(new NumberButton("9"), new RCPosition(2, 5));
		
		//binary operators
		cp.add(new OperationButton("+", (x,y)->x+y), new RCPosition(5, 6));
		cp.add(new OperationButton("-", (x,y)->x-y), new RCPosition(4, 6));
		cp.add(new OperationButton("*", (x,y)->x*y), new RCPosition(3, 6));
		cp.add(new OperationButton("/", (x,y)->x/y), new RCPosition(2, 6));

		//unary operators
		cp.add(new UnaryButton("sin", "arcsin", Math::sin, Math::asin), new RCPosition(2, 2));
		cp.add(new UnaryButton("cos", "arccos", Math::cos, Math::acos), new RCPosition(3, 2));
		cp.add(new UnaryButton("tan", "arctan", Math::tan, Math::atan), new RCPosition(4, 2));
		cp.add(new UnaryButton("ctg", "arcctg", x->1/Math.tan(x), x->Math.PI/2 - Math.tan(x)), new RCPosition(5, 2)); 
		cp.add(new UnaryButton("log", "10^x", Math::log10, x->Math.pow(10,x)), new RCPosition(3, 1));
		cp.add(new UnaryButton("ln", "e^x", Math::log, Math::exp), new RCPosition(4, 1));		
		
		//=		
		cp.add(new MyButton("=", e->{
				if(calc.isActiveOperandSet()) {
					calc.setValue(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
					calc.clearActiveOperand();
					calc.setPendingBinaryOperation(null);
				}else {
					calc.setValue(calc.getValue());
				}
			}), new RCPosition(1, 6));
		
		//reset
		cp.add(new MyButton("reset", e-> calc.clearAll()), new RCPosition(2, 7));
		
		//clr
		cp.add(new MyButton("clr", e -> {
				calc.clear();
			}), new RCPosition(1, 7));

		//switchSign
		cp.add(new MyButton("+/-", e-> calc.swapSign()), new RCPosition(5, 4));
		
		//decimal point
		cp.add(new MyButton(".", e-> calc.insertDecimalPoint()), new RCPosition(5, 5));

		//inverse
		cp.add(new UnaryButton("1/x", "1/x", x->1.f/x, x->1.f/x), new RCPosition(2, 1));
		
		
		//TODO ova 3
		cp.add(new MyButton("x^n"), new RCPosition(5, 1));//on je poseban jer je binary, a ima inverznu operaciju
		cp.add(new MyButton("push"), new RCPosition(3, 7));
		cp.add(new MyButton("pop"), new RCPosition(4, 7));
		
		//Checkbox
		JCheckBox checkbox = new JCheckBox("Inv");
		checkbox.setBackground(Color.LIGHT_GRAY);
		checkbox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(var comp : cp.getComponents()) {
					if(comp instanceof UnaryButton) {
						((UnaryButton)comp).inv();
					}
				}
			}
		});
		
		cp.add(checkbox, new RCPosition(5, 7));
	}
	
	/**
	 *	Class which represents a {@link JButton} with
	 *	a set background color and opacity set to true and the given text.
	 *	All other buttons are derived from this class for customizations sake.
	 */
	private class MyButton extends JButton{
		
		private static final long serialVersionUID = 1L;

		/**
		 * 	Constructs a new {@link OperationButton} with the given parameters.
		 * 	@param text Text to display on the button.
		 */
		public MyButton(String text) {
			setBackground(Color.LIGHT_GRAY);
			setOpaque(true);
			setText(text);
		}	
		
		/**
		 * 	Constructs a new {@link MyButton} with the given parameters.
		 * 	@param text Text to display on the button.
		 * 	@param actionListener Action to do when the button is pressed..
		 */
		public MyButton(String text, ActionListener actionListener) {
			this(text);
			addActionListener(actionListener);
		}
	}
	
	/**
	 *	Class which models a {@link MyButton} which inserts
	 *	the digit which is displayed as the text of the button
	 *	when the button is pressed.
	 */
	private class NumberButton extends MyButton{
		
		private static final long serialVersionUID = 1L;

		/**
		 * 	Constructs a new {@link NumberButton} with the given text.
		 * 	@param text Number to display on the button and add when the
		 * 				button is pressed.
		 */
		public NumberButton(String text) {
			super(text);
			setFont(getFont().deriveFont(30.f));
			addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!calc.isEditable()){
						calc.clear();
					}
					calc.insertDigit(Integer.parseInt(text));
				}
			});
		}
	}
	
	/**
	 *	Class which models a {@link MyButton} which has a 
	 *	binary operation which is performed when the button is
	 *	pressed.
	 */
	private class OperationButton extends MyButton{
		
		private static final long serialVersionUID = 1L;

		/**
		 * 	Constructs a new {@link OperationButton} with the given parameters.
		 * 	@param text Text to display on the button.
		 * 	@param operation Operation to perform when button is clicked.
		 */
		public OperationButton(String text, DoubleBinaryOperator operation) {
			super(text);
			setFont(getFont().deriveFont(20.f));
			addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(calc.getPendingBinaryOperation() != null){
						calc.setActiveOperand(calc.getPendingBinaryOperation()
												  .applyAsDouble(calc.getActiveOperand(), calc.getValue()));
						calc.setValue(calc.getActiveOperand());
					} else {	
						calc.setActiveOperand(calc.getValue());
					}
					calc.setPendingBinaryOperation(operation);				
					calc.clear();
				}
			});
		}
	}

	/**
	 *	Class which models a {@link MyButton} which has 2
	 *	unary operations which are performed depending on the 
	 *	state of the button when the button is pressed.
	 */
	private class UnaryButton extends MyButton{
		
		private static final long serialVersionUID = 1L;
		
		/**Flag which signals if the inverseText is set.*/
		private boolean invSet;
		/**Main text of the button.*/
		private String text;
		/**Inverse text of the button.*/
		private String inverseText;
		
		/**
		 * 	Constructs a new {@link UnaryButton} with the given parameters.
		 * 	@param text	Main text of the button.
		 * 	@param inverseText Inverse text of the button.
		 * 	@param normal Operation to perform if in normal mode.
		 * 	@param inverse Operation to perform if in inverse mode.
		 */
		public UnaryButton(String text, String inverseText, DoubleUnaryOperator normal, DoubleUnaryOperator inverse) {
			super(text);
			this.text = text;
			this.inverseText = inverseText;
			this.invSet = false;
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {		
					if(invSet) {
						calc.setValue(inverse.applyAsDouble(calc.getValue()));
					} else {
						calc.setValue(normal.applyAsDouble(calc.getValue()));						
					}
				}
			});
		}
		
		/**
		 *	{@inheritDoc}
		 */
		@Override
		public Dimension getMinimumSize() {
			setText(text);
			Dimension original = super.getMinimumSize();
			setText(inverseText);
			Dimension inverse = super.getMinimumSize();
			if(!invSet) {
				setText(text);
			}
			return original.width > inverse.width ? original : inverse;
		}
		
		/**
		 *	{@inheritDoc}
		 */
		@Override
		public Dimension getPreferredSize() {
			setText(text);
			Dimension original = super.getPreferredSize();
			setText(inverseText);
			Dimension inverse = super.getPreferredSize();
			if(!invSet) {
				setText(text);
			}
			return original.width > inverse.width ? original : inverse;
		}
		
		/**
		 *	{@inheritDoc}
		 */
		@Override
		public Dimension getMaximumSize() {
			setText(text);
			Dimension original = super.getMaximumSize();
			setText(inverseText);
			Dimension inverse = super.getMaximumSize();
			if(!invSet) {
				setText(text);
			}
			return original.width > inverse.width ? original : inverse;
		}
		
		/**
		 * 	Changes the text on the {@link UnaryButton} between
		 * 	the text and inverseText.
		 */
		public void inv() {
			invSet = !invSet;
			if(invSet) {
				setText(inverseText); 
			} else {
				setText(text);
			}
		}
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
