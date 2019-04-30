package searching.slagalica;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 *	Slagalica TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Slagalica {
	
	private KonfiguracijaSlagalice configuration;
	
	public Slagalica(KonfiguracijaSlagalice configuration) {
		this.configuration = configuration;
	}

	public Supplier<KonfiguracijaSlagalice> initialState = new Supplier<>() {

		@Override
		public KonfiguracijaSlagalice get() {
			return configuration;
		}
		
	};
	
	public Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>> succ = new Function<>() {

		@Override
		public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
			List<Transition<KonfiguracijaSlagalice>> list = new ArrayList<>();
			
			int up = t.indexOfSpaces() - 3;
			int down = t.indexOfSpaces() + 3;
			int left = t.indexOfSpaces() - 1;
			int right = t.indexOfSpaces() + 1;
			
			if(up < 9 && up > 0) {
				int[] newConf = t.getConfiguration();
				switchValueAtIndexes(up, t.indexOfSpaces(), newConf);
				list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConf), 1));
			}
			if(down > 0 && down < 9) {
				int[] newConf = t.getConfiguration();
				switchValueAtIndexes(down, t.indexOfSpaces(), newConf);
				list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConf), 1));
			}
			if(left % 3 != 2 && left > 0 && left < 9) {
				int[] newConf = t.getConfiguration();
				switchValueAtIndexes(left, t.indexOfSpaces(), newConf);
				list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConf), 1));
			}
			if(right % 3 != 0 && right > 0 && right < 9) {
				int[] newConf = t.getConfiguration();
				switchValueAtIndexes(right, t.indexOfSpaces(), newConf);
				list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConf), 1));
			}
			
			return list;
		}
	};
	
	public Predicate<KonfiguracijaSlagalice> pred = new Predicate<>() {

		@Override
		public boolean test(KonfiguracijaSlagalice t) {
			if(t.indexOfSpaces() != 8) {
				return false;
			}		
			
			//TODO ne radi za tablicu vecu od 3x3
			for(int i = 1; i < 8; ++i) {
				if(t.indexOf(i) != i - 1) {
					return false;
				}
			}
			
			return true;
		}
	};
	
	private void switchValueAtIndexes(int a, int b, int[] conf) {
		int temp = conf[a];
		conf[a] = conf[b];
		conf[b]= temp;
	}
}
