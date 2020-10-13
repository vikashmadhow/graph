package ma.vi.graph.explore;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.Path;

import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * This function is called during exploration to expand a path. It returns the set of edges
 * by which the path should be expanded.
 */
@FunctionalInterface
public interface ExpandOp<V, W, E extends Edge<V, W>> {
  Set<E> op(Graph<V, W, E> graph, Path<V, W, E> path);

  /**
   * The default path expansion function returns all outgoing edges of the last
   * vertex in the path.
   *
   * @param graph The graph being explored.
   * @param path  The path to expand.
   * @return All outgoing edges of the path to expand.
   */
  static <V, W, E extends Edge<V, W>> Set<E> outgoingEdges(Graph<V, W, E> graph,
                                                           Path<V, W, E> path) {
    V pathEnd = path.end().orElse(null);
    return pathEnd == null ? emptySet() : graph.outgoing(pathEnd);
  }

}
