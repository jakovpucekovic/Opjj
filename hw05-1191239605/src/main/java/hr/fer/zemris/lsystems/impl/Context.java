package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class Context {
	
	private ObjectStack<TurtleState> stack;

	// vraća stanje s vrha stoga bez uklanjanja
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	// na vrh gura predano stanje
	public void pushState(TurtleState state) {
		stack.push(state);
	} 
	
	// briše jedno stanje s vrha
	public void popState() {
		stack.pop();
	}
}
