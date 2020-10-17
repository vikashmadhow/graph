package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.path.Path;

/**
 * This function computes the cost of the path extended the given edge.
 */
@FunctionalInterface
public interface PathCostOp<V, W, E extends Edge<V, W>> {
  Long op(Graph<V, W, E> graph,
          Path<V, W, E> path,
          E edge);

  static <V, W, E extends Edge<V, W>> Long byLength(Graph<V, W, E> graph,
                                                    Path<V, W, E> path,
                                                    E edge) {
    return path.edges().size() + 1L;
  }

  static <V, W, E extends Edge<V, W>> Long byWeight(Graph<V, W, E> graph,
                                                    Path<V, W, E> path,
                                                    E edge) {
    return path.weight() + (edge.weight() instanceof Number ? ((Number)edge.weight()).longValue() : 0L);
  }
}
