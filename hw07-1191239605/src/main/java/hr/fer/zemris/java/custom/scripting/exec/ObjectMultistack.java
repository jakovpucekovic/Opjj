package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *	Class {@link ObjectMultistack} which represents a collection 
 *	similar to a map of stacks. Each key is a {@link String} which 
 *	is paired with a stacklike structure of {@link Object}s.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class ObjectMultistack {
	
	/**Map used for storing keys and stacks of {@link MultistackEntry}*/
	Map<String, MultistackEntry> map;
	
	/**
	 * 	Constructs a new ObjectMultistack.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}
	
	/**
	 * 	Pushes the {@link ValueWrapper} on the stack paired with the given key.
	 * 	@param keyName Key to the stack where the {@link ValueWrapper} should be pushed.
	 * 	@param valueWrapper {@link ValueWrapper} which should be pushed.
	 * 	@throws NullPointerException If one on the arguments are <code>null</code>.
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName);
		Objects.requireNonNull(valueWrapper);
		if(isEmpty(keyName)) {
			map.put(keyName, new MultistackEntry(valueWrapper, null));
		} else {
			map.put(keyName, new MultistackEntry(valueWrapper, map.get(keyName)));
		}
	}  
	
	/**
	 *	Removes the top of the stacks paired with the given key
	 *	and returns the removed value.
	 *	@param keyName Key paired with the stack from which the element
	 *				   is removed.
	 *	@return The removed element which was at the top of the stack before
	 *			removal.
	 *	@throws EmptyStackException If the stack paired with the given key is empty. 
	 */
	public ValueWrapper pop(String keyName) {
		if(isEmpty(keyName)) {
			throw new EmptyStackException();
		}
		MultistackEntry toReturn = map.get(keyName);
		if(toReturn.next == null) {
			map.remove(keyName);
		}else {
			map.put(keyName, toReturn.getNext());
		}
		return toReturn.getValue();
	}                      
	
	/**
	 *	Returns the top of the stacks paired with the given key.
	 *	@param keyName Key paired with the stack from which the element
	 *				   is returned.
	 *	@return The element at the top of the stack.
	 *	@throws EmptyStackException If the stack paired with the given key is empty. 
	 */
	public ValueWrapper peek(String keyName) {
		if(isEmpty(keyName)) {
			throw new EmptyStackException();
		}
		return map.get(keyName).getValue();
	}                     
	
	/**
	 * 	Checks whether this {@link ObjectMultistack} contains
	 * 	any entries for the given key.
	 * 	@param keyName The key to check.
	 * 	@return <code>true</code> if there are no entries for the given key, <code>false</code> otherwise.
	 */
	public boolean isEmpty(String keyName) {
		return !map.containsKey(keyName);
	}                       

	/**
	 * 	Class which models an entry in the stacks of {@link ObjectMultistack}.
	 */
	private static class MultistackEntry{
		
		/**Stored value*/
		private ValueWrapper value;
		
		/**Reference to next {@link MultistackEntry}.*/
		private MultistackEntry next;
	
		/**
		 * 	Constructs a new MultistackEntry with the given parameters..
		 * 	@param value {@link ValueWrapper} to be saved on the {@link ObjectMultistack}.
		 * 	@param next Reference to the next {@link MultistackEntry}.
		 */
		private MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
		
		/**
		 * 	Returns the value of the {@link MultistackEntry}.
		 * 	@return the value of the {@link MultistackEntry}.
		 */
		public ValueWrapper getValue() {
			return value;
		}
		
		/**
		 * 	Returns the next {@link MultistackEntry}.
		 * 	@return the next {@link MultistackEntry}.
		 */
		public MultistackEntry getNext() {
			return next;
		}
		
	}

}
