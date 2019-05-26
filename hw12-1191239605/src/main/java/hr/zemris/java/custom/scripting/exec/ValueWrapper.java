package hr.zemris.java.custom.scripting.exec;

/**
 *	Class {@link ValueWrapper} which stores a value and 
 *	can interact with {@link ObjectMultistack}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class ValueWrapper {

	/**
	 * Two numbers are considered equal if their difference
	 * is less than EPSILON.
	 */
	private static final double EPSILON = 1e-8;
	
	/**Stored value*/
	private Object value;
	
	/**
	 * 	Constructs a new {@link ValueWrapper} with the given value.
	 * 	@param value Value to store.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	/**
	 * 	Returns the value of the {@link ValueWrapper}.
	 * 	@return The value of the {@link ValueWrapper}.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * 	Sets the value of the {@link ValueWrapper}.
	 * 	@param value The value to set.
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * 	Adds the given value to the stored value if the values
	 * 	are of the valid types ({@link Integer}, {@link Double}
	 * 	or {@link String} which represents {@link Integer} or 
	 * 	{@link Double}).
	 * 	@param incValue Value to add to the stored value.
	 * 	@throws RuntimeException If the values are not of valid typing. 
	 */
	public void add(Object incValue) {
		value = calculate('+', value, incValue);
	}
	
	/**
	 * 	Subtracts the given value from the stored value if the values
	 * 	are of the valid types ({@link Integer}, {@link Double}
	 * 	or {@link String} which represents {@link Integer} or 
	 * 	{@link Double}).
	 * 	@param decValue Value to subtract from the stored value.
	 * 	@throws RuntimeException If the values are not of valid typing. 
	 */
	public void subtract(Object decValue) {
		value = calculate('-', value, decValue);
	}
	
	/**
	 * 	Multiplies the stored value by the given value if the values
	 * 	are of the valid types ({@link Integer}, {@link Double}
	 * 	or {@link String} which represents {@link Integer} or 
	 * 	{@link Double}).
	 * 	@param mulValue Value with which to multiply.
	 * 	@throws RuntimeException If the values are not of valid typing. 
	 */
	public void multiply(Object mulValue) {
		value = calculate('*', value, mulValue);
	}
	
	/**
	 * 	Divides the stored value by the given value if the values
	 * 	are of the valid types ({@link Integer}, {@link Double}
	 * 	or {@link String} which represents {@link Integer} or 
	 * 	{@link Double}).
	 * 	@param divValue Value with which to divide.
	 * 	@throws RuntimeException If the values are not of valid typing. 
	 */	public void divide(Object divValue) {
		value = calculate('/', value, divValue);
	}

	/**
	 * 	Compares the stored value with the given value if the values
	 * 	are of the valid types ({@link Integer}, {@link Double}
	 * 	or {@link String} which represents {@link Integer} or 
	 * 	{@link Double}).
	 * 	@param withValue Value to compare with.
	 * 	@return 0 if values are equal, 1 if stored value is 
	 * 			bigger or -1 if stored value is smaller.
	 * 	@throws RuntimeException If the values are not of valid typing.
	 */ 
	public int numCompare(Object withValue) {
		Obj a = new Obj(value);
		Obj b = new Obj(withValue);
		double res = a.value - b.value;
		if(Math.abs(res) < EPSILON) {
			return 0;
		}
		if(res > 0) {
			return 1;
		}
		return -1;
	}
	
	/**
	 * 	Method which performs the given operation of given {@link Object}s.
	 * 	@param operation Operation to perform, valid operations are +,-,*,/
	 * 	@param a First {@link Object}.
	 * 	@param b Second {@link Object}.
	 * 	@return {@link Object} created by performing the operation.
	 * 	@throws IllegalArgumentException If unknown operation given.
	 * 	@throws RuntimeException If the values are not of valid typing.
	 */
	private Object calculate(char operation, Object a, Object b) {
		Obj first = new Obj(a);
		Obj second = new Obj(b);
		
		double result;
		switch(operation) {
			case '+': result = first.value + second.value; break;
			case '-': result = first.value - second.value; break;
			case '*': result = first.value * second.value; break;
			case '/': result = first.value / second.value; break;
			default: throw new IllegalArgumentException();
		}
		int iresult = (int) result;
		return first.isDouble || second.isDouble ? (Object)result : (Object)iresult;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return value.toString();
	}

	/**
	 * 	Helper class which represents a number which can be 
	 * 	an {@link Integer} or a {@link Double}.
	 **/
	private static class Obj{
		
		/**Signals whether the stored value should be viewed as a double.*/
		private boolean isDouble;
		
		/**Stored value*/
		private double value = 0;
		
		/**
		 *	Creates a new {@link Obj} from the given {@link Object}.
		 *	@param o {@link Object} from which the {@link Obj} should be created.
		 *	@throws RuntimeException If the given {@link Object} cannot be interpreted
		 *							 as a valid {@link Integer} or {@link Double}. 
		 */
		Obj(Object o){
			if(o == null) {
				isDouble = false;
			} else if(o instanceof String) {
				if(((String) o).indexOf('.') != -1 ||
				   ((String) o).indexOf('E') != -1 ||
				   ((String) o).indexOf('e') != -1) {
					try {
						value = Double.parseDouble((String) o);
						isDouble = true;
					} catch(NumberFormatException ex) {
						throw new RuntimeException();
					}
				} else {
					try {
						value = Integer.parseInt((String) o);
						isDouble = false;
					} catch(NumberFormatException ex) {
						throw new RuntimeException();
					}
				}
			} else if(o instanceof Integer) {
				value =(int) o;
				isDouble = false;
			} else if(o instanceof Double) {
				value = (double) o;
				isDouble = true;
			} else {
				throw new RuntimeException();				
			}
		}
	}
	
}
