package hr.fer.zemris.java.custom.collections;
/**
 *  Interface which returns the user elements
 * 	one by one.
 * 
 *	@author Jakov Pucekovic
 *	@version 1.0 
 */
public interface ElementsGetter {

	/**
	 * 	Answers whether there is another element.
	 * 	@return <code>true</code> if yes, <code>false</code> if no.
	 */
	boolean hasNextElement();
	
	/**
	 * 	Returns the next element.
	 * 	@return Next element.
	 */
	Object getNextElement();
	
	/**
	 * 	Processes all remaining elements.
	 * 	@param proc {@link Processor} which does the processing.
	 */
	default void processRemaining(Processor proc) {
		while(hasNextElement()) {
			proc.process(getNextElement());
		}
	}
}
