package searching.slagalica;

import java.util.Arrays;

/**
 *	Class which models a configuration of the {@link Slagalica}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class KonfiguracijaSlagalice {

	/**Stored configuration.*/
	private int[] conf;
	
	/**
	 * 	Constructs a new {@link KonfiguracijaSlagalice} with from	
	 * 	the given configuration.
	 * 	@param conf The configuration of {@link KonfiguracijaSlagalice}.
	 * 	@throws IllegalArgumentException If the given array hasn't 9 elements
	 * 									 or doesn't contain numbers from 0 to 8.
	 */
	public KonfiguracijaSlagalice(int[] conf) {
		if(conf.length != 9) {
			throw new IllegalArgumentException("Should have 9 numbers.");
		}

		for (int i = 0; i < 9; ++i) {
			boolean found = false;
			for (int j = 0; j < conf.length; ++j) {
				if(conf[j] == i) {
					found = true;
					break;
				}
			}
			if(!found) {
				throw new IllegalArgumentException("Should have numbers from 0 to 8");
			}
		}
		this.conf = conf;
	}
	
	/**
	 *  Returns a copy of the current configuration.
	 *  @return A copy of the current configuration.
	 */
	public int[] getPolje() {
		return Arrays.copyOf(conf, conf.length);
	}
	
	/**
	 * 	Returns the index of the empty space.
	 * 	@param The index of the empty space.
	 */
	public int indexOfSpaces() {
		for(int i = 0, s = conf.length; i < s ; ++i) {
			if(conf[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 9; ++i) {
			if(conf[i] == 0) {
				sb.append('*');
			} else {
				sb.append(conf[i]);
			}
			
			if(i % 3 == 2 && i != 8) {
				sb.append('\n');
			} else {
				sb.append(' ');
			}
		}
		return sb.toString();
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(conf);
		return result;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(conf, other.conf);
	}
	
}
