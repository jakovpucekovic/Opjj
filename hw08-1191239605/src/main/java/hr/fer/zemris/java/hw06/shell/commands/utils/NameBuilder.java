package hr.fer.zemris.java.hw06.shell.commands.utils;

/**
 *	Interface which represents an object which does
 *	various manipulations on the given {@link StringBuilder}
 *	while building a name from the given {@link FilterResult}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface NameBuilder {
			
	/**
	 * 	Executes the {@link NameBuilder}.
	 * 	@param result {@link FilterResult} from which the name is generated.
	 * 	@param sb {@link StringBuilder} in which the name is generated.
	 */
	void execute(FilterResult result, StringBuilder sb);
	
	/**
	 * 	Offers a binary composite for the {@link NameBuilder} interface
	 * 	allowing the chaining of {@link NameBuilder}.
	 * 	@param other The other {@link NameBuilder}.
	 * 	@return A new {@link NameBuilder} which first executes the execute
	 * 			method of this {@link NameBuilder} and then the execute
	 * 			method of the other {@link NameBuilder}.
	 */
	default NameBuilder then(NameBuilder other) { return (fr, sb)->{
			execute(fr, sb);
			other.execute(fr, sb);
		};
	}
	
}
