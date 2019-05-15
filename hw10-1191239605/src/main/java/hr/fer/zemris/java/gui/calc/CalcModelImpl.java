package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 *	Class which implements a {@link CalcModel} capable of 
 *	storing values, operands and operations. It also notifies
 *	all registered listeners when the value is changed.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CalcModelImpl implements CalcModel{

	/**Signal whether the {@link CalcModelImpl} is editable.*/
	private boolean isEditable;
	/**Signals whether the stored value is negative.*/
	private boolean isNegative; 
	/**String representation of the stored value.*/
	private String input;
	/**Stored value.*/
	private double value;
	/**Signals whether the active operand is set.*/
	private boolean isActiveOperandSet;
	/**Stores the active operand.*/
	private double activeOperand;
	/**Stores the pending operation.*/
	private DoubleBinaryOperator pendingOperation;
	/**Stores all listeners which need to be notified in case of value change.*/
	private List<CalcValueListener> listeners;
	
	/**
	 * 	Constructs a new {@link CalcModelImpl} and sets
	 * 	initial values.
	 */
	public CalcModelImpl() {
		this.isEditable = true;
		this.isNegative = false;
		this.input = "";
		this.value = 0;
		this.activeOperand = 0;
		this.isActiveOperandSet = false;
		
		this.listeners = new ArrayList<>();
	}
	
	/**
	 * 	Notifies all registered listeners.
	 */
	private void notifyAllListeners() {
		for(var l : listeners) {
			l.valueChanged(this);
		}
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		if(input.isBlank()) {
			return isNegative ? "-0" : "0";
		}
		return isNegative ? "-" + input : input;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public double getValue() {
		return isNegative ? -value : value;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void setValue(double value) {
		this.value = value < 0 ? - value : value;
		input = Double.toString(this.value);
		isNegative = value < 0;
		isEditable = false;
		notifyAllListeners();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public boolean isEditable() {
		return isEditable;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void clear() {
		value = 0;
		isNegative = false;
		input = "";
		isEditable = true;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void clearAll() {
		value = 0;
		isNegative = false;
		input = "";
		isEditable = true;
		isActiveOperandSet = false;
		activeOperand = 0;
		pendingOperation = null;
		notifyAllListeners();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Not editable.");
		}
		isNegative = !isNegative;
		notifyAllListeners();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Not editable.");
		}
		if(input.isBlank()) {
			throw new CalculatorInputException("No digits before decimal point.");
		}
		if(input.indexOf('.') != -1) {
			throw new CalculatorInputException("Already contains decimal point.");
		}
		input += ".";
		notifyAllListeners();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Given argument is not a digit.");
		}
		if(!isEditable) {
			throw new CalculatorInputException("Not editable.");
		}
		if(value == 0 && input.equals("0") && digit == 0) {
			return;
		}
		if(value * 10 + digit > Double.MAX_VALUE) { //TODO vidi jel ovo okej
//		if(value * 10 + digit < value) {
			throw new CalculatorInputException("Overflow happened");
		}
		if(input.equals("0") && digit != 0) {
			input = "";
		}
		input += digit;
		value = Double.parseDouble(input);
		notifyAllListeners();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet) {
			throw new IllegalStateException("Active operator not set.");
		}
		return activeOperand;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isActiveOperandSet = true;
//		notifyAllListeners();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
		isActiveOperandSet = false;
//		notifyAllListeners();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

}
