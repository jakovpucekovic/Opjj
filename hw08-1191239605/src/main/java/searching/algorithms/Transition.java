package searching.algorithms;

/**
 *	Transition TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Transition<S> {

	private S state;
	private double cost;
	
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * 	Returns the state of the {@link Transition}.
	 * 	@return the state of the {@link Transition}.
	 */
	public S getState() {
		return state;
	}

	/**
	 * 	Returns the cost of the {@link Transition}.
	 * 	@return the cost of the {@link Transition}.
	 */
	public double getCost() {
		return cost;
	}
	
	
	
	
}
