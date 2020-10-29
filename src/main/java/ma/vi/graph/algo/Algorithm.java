package ma.vi.graph.algo;

import ma.vi.graph.Graph;

/**
 * An algorithm can be applied to a graph to produce a result.
 *
 * @param <V> The type of vertex of graphs that this algorithm can be applied to.
 * @param <W> The type of weight of graphs that this algorithm can be applied to.
 * @param <R> The result of applying this algorithm.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public interface Algorithm<V, W, R> {
  R execute(Graph<V, W> graph);
}
