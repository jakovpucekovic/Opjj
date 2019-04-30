package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *	SearchUtil TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class SearchUtil {

	/**
	 * 	
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S,List<Transition<S>>> succ, Predicate<S> goal){
		List<Node<S>> toExplore = new LinkedList<>();
		toExplore.add(new Node<S>(null, s0.get(), 0));
		while(!toExplore.isEmpty()) {
			Node<S> next = toExplore.get(0);
			toExplore.remove(0);
			if(goal.test(next.getState())) {
				return next;
			}
			List<Transition<S>> l = succ.apply(next.getState());
			List<Node<S>> nl = l.stream().map(x -> new Node<>(next, x.getState(), next.getCost() + x.getCost())).collect(Collectors.toList());
			toExplore.addAll(nl);
		}
		return null;
	}
	
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S,List<Transition<S>>> succ, Predicate<S> goal){
		List<Node<S>> toExplore = new LinkedList<>();
		toExplore.add(new Node<S>(null, s0.get(), 0));
		
		Set<S> visited = new HashSet<>();
		visited.add(s0.get());
		
		while(!toExplore.isEmpty()) {
			Node<S> next = toExplore.get(0);
			toExplore.remove(0);
			
			if(goal.test(next.getState())) {
				return next;
			}
			
			List<Transition<S>> children = succ.apply(next.getState());
			children = children.stream().filter(x -> !visited.contains(x.getState())).collect(Collectors.toList());
			List<Node<S>> nodeChildren = children.stream().map(x -> new Node<>(next, x.getState(), next.getCost() + x.getCost())).collect(Collectors.toList());
			visited.addAll(children.stream().map(x-> x.getState()).collect(Collectors.toList()));
			toExplore.addAll(nodeChildren);
		}
		return null;
	}
	
}
