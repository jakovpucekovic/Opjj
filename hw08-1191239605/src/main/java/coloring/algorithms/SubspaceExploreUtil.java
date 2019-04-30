package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *	SubspaceExploreUtil TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class SubspaceExploreUtil {

	//TODO jel ovo treba biti parametrizirano ili ne?
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process,	Function<S,List<S>> succ, Predicate<S> acceptable) {
		List<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		
		while(!toExplore.isEmpty()) {
			S si = toExplore.get(0);
			toExplore.remove(0);
			
			if(!acceptable.test(si)) {
				continue;
			}
			
			process.accept(si);
			toExplore.addAll(succ.apply(si));
		}
	}
	
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process,	Function<S,List<S>> succ, Predicate<S> acceptable) {
		List<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		
		while(!toExplore.isEmpty()) {
			S si = toExplore.get(0);
			toExplore.remove(0);
			
			if(!acceptable.test(si)) {
				continue;
			}
			
			process.accept(si);
			toExplore.addAll(0, succ.apply(si));
		}
	}
	
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process,	Function<S,List<S>> succ, Predicate<S> acceptable) {
		List<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		
		Set<S> visited = new HashSet<>();
		visited.add(s0.get());
		
		while(!toExplore.isEmpty()) {
			S si = toExplore.get(0);
			toExplore.remove(0);
			
			if(!acceptable.test(si)) {
				continue;
			}
			
			process.accept(si);
			List<S> children = succ.apply(si);
			children.removeAll(visited);
			toExplore.addAll(children);
			visited.addAll(children);
		}
	}
	
}
