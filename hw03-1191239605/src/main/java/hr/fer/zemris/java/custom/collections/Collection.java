package hr.fer.zemris.java.custom.collections;

/**
 *	Interface that represents some general collection of objects.
 *
 * 	@author Jakov Pucekovic
 * 	@version 2.0
 */
public interface Collection {
	
	/**
	 * 	Checks if this {@link Collection} contains objects.
	 * 	@return <code>true</code> if the collection is empty, <code>false</code> if not.
	 */
	default boolean isEmpty() {
		return !(size() > 0);
	}
	
	/**
	 * 	Returns the number of objects currently stored in this {@link Collection}.
	 */
	abstract int size();
	
	/**
	 *	Adds the given value into this {@link Collection}. 
	 *	@param value Value to add into the Collection.
	 */
	abstract void add(Object value);
	
	/**
	 * 	Checks if this {@link Collection} contains a given value.
	 * 	@param	value Object for which we want to know if it's contained
	 * 				  in this {@link Collection}.
	 * 	@return	<code>true</code> if this {@link Collection} contains the value,
	 * 			<code>false</code> otherwise.
	 */
	abstract boolean contains(Object value);
	
	/**
	 * 	Checks if this {@link Collection} contains the value as 
	 * 	determined by equals method and removes one occurrence
	 *  of it(not specified which one).
	 *  @param value The value to remove from this {@link Collection}.
	 * 	@return <code>true</code> if the value is found and removed, <code>false</code> 
	 * 			if the value is not found. 
	 */
	abstract boolean remove(Object value);
	
	/**
	 * 	Allocates a new array with size equal to size of this {@link Collection}
	 * 	and fills it with collection content.
	 * 	@return The newly allocated array. Never returns <code>null</code>.
	 */
	abstract Object[] toArray();
	
	/**
	 *	Calls processor.process() for each element of this {@link Collection}.
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * 	Method adds all elements from the given {@link Collection}
	 * 	into this {@link Collection}. The other {@link Collection} 
	 *  remains unchanged.
	 *  @param other The {@link Collection} whose elements need to 
	 *  			 be added.
	 */
	default void addAll(Collection other) {
		class MyProcessor implements Processor{
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		MyProcessor proc = new MyProcessor();
		other.forEach(proc);
	}
	
	/**
	 * 	Removes all elements from this {@link Collection}. 
	 */
	abstract void clear();

	/**
	 * 	Creates an {@link ElementsGetter} for this {@link Collection}.
	 */
	abstract ElementsGetter createElementsGetter();
	
	/**
	 * 	Adds all elements that satisfy the test to the {@link Collection}.
	 * 	@param col The {@link Collection} from which elements are added.
	 * 	@param tester Tester which performs the test on the elements.
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			Object obj = getter.getNextElement();
			if(tester.test(obj)) {
				add(obj);
			}
		}
	
	}

}
