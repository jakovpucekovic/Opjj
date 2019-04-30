package searching.algorithms;

import java.util.LinkedList;
import java.util.List;
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
	
}
