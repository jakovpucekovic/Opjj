package hr.fer.zemris.java.hw13.classes;

/**
 *	Class which calculates and stores the sin and cos of a number.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class TrigonometricContainer {

	/**Stored value*/
	private int value;

	/**Sin of the value*/
	private double sin;
	
	/**Cos of the value*/
	private double cos;
	
	/**
	 * 	Constructs a new {@link TrigonometricContainer}.
	 * 	@param value Value to store.
	 */
	public TrigonometricContainer(int value) {
		super();
		this.value = value;
		this.sin = Math.sin(Math.toRadians(value));
		this.cos = Math.cos(Math.toRadians(value));
	}
	
	/**
	 * 	Returns the value of the TrigonometricContainer.
	 * 	@return the value of the TrigonometricContainer.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * 	Returns the sin of the TrigonometricContainer.
	 * 	@return the sin of the TrigonometricContainer.
	 */
	public double getSin() {
		return sin;
	}
		
	/**
	 * 	Returns the cos of the TrigonometricContainer.
	 * 	@return the cos of the TrigonometricContainer.
	 */
	public double getCos() {
		return cos;
	}
	
}
