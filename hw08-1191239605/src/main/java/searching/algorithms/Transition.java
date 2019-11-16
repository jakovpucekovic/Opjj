package searching.algorithms;

/**
 *	Class which represents a transition in the searching
 *	algorithm.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Transition<S> {

	/**State of the {@link Transition}.*/
	private S state;
	
	/**Cost of the {@link Transition}.*/
	private double cost;
	
	/**
	 * 	Constructs a new {@link Transition} with the given state
	 * 	and cost.
	 * 	@param state The state of the {@link Transition}.
	 * 	@param cost The cost of the {@link Transition}.
	 */
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
