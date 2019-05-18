package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *	Class which generates new prime numbers and stores them, 
 *	also it notifies all the registered listeners when that happens.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class PrimListModel implements ListModel<Integer>{

	/**List of stored numbers.*/
	private List<Integer> numbers;
	
	/**List of listeners.*/
	private List<ListDataListener> listeners;
	
	/**
	 * 	Constructs a new {@link PrimListModel} and adds 1
	 * 	in the stored numbers.
	 */
	public PrimListModel() {
		numbers = new ArrayList<>();
		numbers.add(1);
		listeners = new ArrayList<>();
	}
	
	/**
	 *	Calculates the next prime number, adds it into the stored
	 *	numbers and notifies all listeners of the change. 
	 */
	public void next() {
		int newPrime = numbers.get(numbers.size()-1) + 1;
		while(!isPrime(newPrime)) {
			++newPrime;
		}
		numbers.add(newPrime);
		
		for(var l : listeners) {
			l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, numbers.size() - 1, numbers.size()));
		}
	}
	
	/**
	 * 	Checks whether a given number is prime by checking
	 * 	if it is divisible by any number in the range 
	 * 	from 2 to sqrt(n)+1.
	 * 	@param n Number to check if it's prime.
	 * 	@return <code>true</code> if the number is prime, <code>false</code> if not.
	 */
	private boolean isPrime(int n) {
		if(n == 1) {
			return true;
		}
		for(int i = 2, s = (int) (Math.round(Math.sqrt(n)) + 1); i < s; ++i) {
			if(n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int getSize() {
		return numbers.size();
	}

	/**
	 *	{@inheritDoc}
	 *	@throws IndexOutOfBoundsException If index is out of bounds.
	 */
	@Override
	public Integer getElementAt(int index) {
		return numbers.get(index);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
}
