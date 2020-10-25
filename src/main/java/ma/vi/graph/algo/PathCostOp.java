package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.path.Path;

/**
 * This function computes the cost of the path extended the given edge.
 */
@FunctionalInterface
public interface PathCostOp<V, W> {
  Long op(Graph<V, W> graph,
          Path<V, W> path,
          Edge<V, W> edge);

  static <V, W> Long byLength(Graph<V, W> graph,
                              Path<V, W> path,
                              Edge<V, W> edge) {
    return path.edges().size() + 1L;
  }

  static <V, W> Long byWeight(Graph<V, W> graph,
                              Path<V, W> path,
                              Edge<V, W> edge) {
    return path.weight() + (edge.weight instanceof Number ? ((Number)edge.weight).longValue() : 0L);
  }
}
