package hr.fer.zemris.java.hw13.classes;

/**
 *	TrigonometricContainer TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class TrigonometricContainer {

	private int value;
	private double sin;
	private double cos;
	
	/**
	 * 	Constructs a new {@link TrigonometricContainer}.
	 * 	@param value
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
