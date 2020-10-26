package ma.vi.graph.algo.tree;

import ma.vi.graph.Graph;
import ma.vi.graph.algo.Algorithm;
import ma.vi.graph.algo.PriorityPathQueue;

/**
 * <p>
 * Construct a minimum spanning tree (MST) using Prim's (Jarník's) algorithm.
 * </p>
 *
 * @param <V> The vertex type of the graph to search.
 * @param <W> The weight type on the edges of the graph to search.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class PrimMst<V, W> implements Algorithm<V, W, Graph<V, W>> {
  @Override
  public Graph<V, W> execute(Graph<V, W> graph) {
    return new SpanningTree<V, W>()
                .pathQueue(new PriorityPathQueue<>())
                .pathCostOp((g, p, e) -> e.weight instanceof Number ? ((Number)e.weight).longValue() : 0)
                .execute(graph);
  }
}