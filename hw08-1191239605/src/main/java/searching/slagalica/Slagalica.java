package searching.slagalica;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 *	Class which models a 3x3 puzzle described in the
 *	8th homework.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Slagalica {
	
	/**Current configuration.*/
	private KonfiguracijaSlagalice configuration;
	
	/**Contains the configuration of a solved {@link Slagalica}.*/
	private static final KonfiguracijaSlagalice SOLVED = new KonfiguracijaSlagalice(new int[] {1,2,3,4,5,6,7,8,0}); 
	
	/**
	 * 	Constructs a new {@link Slagalica} with the given initial configuration.
	 * 	@param configuration The initial configuration.
	 */
	public Slagalica(KonfiguracijaSlagalice configuration) {
		this.configuration = configuration;
	}

	/**
	 * 	{@link Supplier} which return the initial state of
	 * 	the {@link Slagalica}.
	 */
	public Supplier<KonfiguracijaSlagalice> initialState = () -> {return configuration;};
	
	/**
	 *	{@link Function} which returns a {@link List} of
	 *	{@link Transition}s of next possible {@link KonfiguracijaSlagalice}. 
	 */
	public Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>> succ = new Function<>() {

		@Override
		public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
			List<Transition<KonfiguracijaSlagalice>> list = new ArrayList<>();
			int ind = t.indexOfSpaces();
			
			/*Tile above*/
			if(ind > 2) {
				int[] newConf = t.getPolje();
				switchValueAtIndexes(ind - 3, ind, newConf);
				list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConf), 1));
			}
			/*Tile below*/
			if(ind < 6) {
				int[] newConf = t.getPolje();
				switchValueAtIndexes(ind + 3, ind, newConf);
				list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConf), 1));
			}
			/*Tile left*/
			if(ind % 3 > 0) {
				int[] newConf = t.getPolje();
				switchValueAtIndexes(ind - 1, ind, newConf);
				list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConf), 1));
			}
			/*Tile right*/
			if(ind % 3 < 2) {
				int[] newConf = t.getPolje();
				switchValueAtIndexes(ind + 1, ind, newConf);
				list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConf), 1));
			}
			return list;
		}
	};
	
	/**
	 *	A {@link Predicate} which tests whether
	 *	the given {@link KonfiguracijaSlagalice} 
	 *	equals to the solved {@link KonfiguracijaSlagalice}.
	 */
	public Predicate<KonfiguracijaSlagalice> goal = (KonfiguracijaSlagalice puzzleConfig) -> puzzleConfig.equals(SOLVED);	
	
	/**
	 *	Switches the values at the given indexes in the given
	 *	integer array.
	 *	@param a First index.
	 *	@param b Second index.
	 *	@param conf Array in which the values should be switched.
	 */
	private void switchValueAtIndexes(int a, int b, int[] conf) {
		int temp = conf[a];
		conf[a] = conf[b];
		conf[b]= temp;
	}
}
