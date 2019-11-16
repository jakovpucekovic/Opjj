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
 *	Class which contains 3 searching algorithms which can be used to 
 *	solve problems with {@link Coloring} to color a picture.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class SubspaceExploreUtil {

	/**
	 * 	Implementation of the breadth-first-search algorithm.
	 * 	@param s0 {@link Supplier} of the initial state.
	 * 	@param process {@link Consumer} which processes the given state.
	 * 	@param succ {@link Function} which returns the list of next possible states.
	 * 	@param acceptable {@link Predicate} which tells if the goal is met.
	 */
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
	
	/**
	 * 	Implementation of the depth-first-search algorithm.
	 * 	@param s0 {@link Supplier} of the initial state.
	 * 	@param process {@link Consumer} which processes the given state.
	 * 	@param succ {@link Function} which returns the list of next possible states.
	 * 	@param acceptable {@link Predicate} which tells if the goal is met.
	 */
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
	
	/**
	 * 	Implementation of an improved breadth-first-search algorithm which
	 * 	doesn't go through the same values twice.
	 * 	@param s0 {@link Supplier} of the initial state.
	 * 	@param process {@link Consumer} which processes the given state.
	 * 	@param succ {@link Function} which returns the list of next possible states.
	 * 	@param acceptable {@link Predicate} which tells if the goal is met.
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S,List<S>> succ, Predicate<S> acceptable) {
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
