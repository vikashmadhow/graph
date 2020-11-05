package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.Path;

import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * This function is called during exploration to expand a path. It returns the set of edges
 * by which the path should be expanded.
 *
 * @param <V> The type of vertex of graphs that this algorithm can be applied to.
 * @param <W> The type of weight of graphs that this algorithm can be applied to.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
@FunctionalInterface
public interface ExpandOp<V, W> {
  Set<Edge<V, W>> op(Graph<V, W> graph, Path<V, W> path);

  /**
   * The default path expansion function returns all outgoing edges of the last
   * vertex in the path.
   *
   * @param graph The graph being explored.
   * @param path  The path to expand.
   * @return All outgoing edges of the path to expand.
   */
  static <V, W> Set<Edge<V, W>> outgoingEdges(Graph<V, W> graph, Path<V, W> path) {
    V pathEnd = path.lastVertex().orElse(null);
    return pathEnd == null ? emptySet() : graph.outgoing(pathEnd);
  }
}
