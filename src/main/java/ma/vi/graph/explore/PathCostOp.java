package ma.vi.graph.explore;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.path.Path;

/**
 * This function computes the cost of the path extended the given edge.
 */
@FunctionalInterface
public interface PathCostOp<V, W, E extends Edge<V, W>> {
  Integer op(Graph<V, W, E> graph,
             Path<V, W, E> path,
             E edge);

  static <V, W, E extends Edge<V, W>> Integer byLength(Graph<V, W, E> graph,
                                                       Path<V, W, E> path,
                                                       E edge) {
    return path.edges().size() + 1;
  }

  static <V, W, E extends Edge<V, W>> Integer byWeight(Graph<V, W, E> graph,
                                                       Path<V, W, E> path,
                                                       E edge) {
    int cost = path.cost() == null ? 0 : path.cost();
    if (edge.weight() instanceof Number) {
      cost += ((Number)edge.weight()).intValue();
    }
    return cost;
  }
}
