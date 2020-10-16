package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.explore.Explore;
import ma.vi.graph.path.Path;

import java.util.Optional;

/**
 * This function is invoked on every path visited by the {@link Explore} algorithms.
 * <p>
 * The return value of this function is interpreted by the explore function as follows:
 * <ul>
 * <li>a null value signifies that the path should not be expanded further (and exploration
 *     should continue by backtracking to the next unexplored vertex and continue from there);</li>
 * <li>an empty set means that the exploration should continue by expanding the path;</li>
 * <li>finally a non-empty set means that the exploration is completed and the set
 *     should be returned as its result.</li>
 * </ul>
 */
@FunctionalInterface
public interface ExploreOp<V, W, E extends Edge<V, W>, A, R> {
  Optional<R> op(Graph<V, W, E> graph,
                 Path<V, W, E> path,
                 A accumulator);
}
