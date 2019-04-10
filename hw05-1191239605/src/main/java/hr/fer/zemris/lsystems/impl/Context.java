package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * 	Class which TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Context {
	
	/**Stack which stores the {@link TurtleState}s.*/
	private ObjectStack<TurtleState> stack = new ObjectStack<>();

	/**
	 * 	Returns the current state of the turtle.
	 * 	@return The current state of the turtle.
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}

	/**
	 * 	Sets the new state of the turtle.
	 * 	@param state The new state of the turtle.
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	} 
	
	/**
	 *	Deletes the current state of the turtle. 
	 */
	public void popState() {
		stack.pop();
	}
}
