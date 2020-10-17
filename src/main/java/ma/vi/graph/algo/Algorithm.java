package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public interface Algorithm<V, W, E extends Edge<V, W>, R>  {
  R execute(Graph<V, W, E> graph);
}
