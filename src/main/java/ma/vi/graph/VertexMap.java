package ma.vi.graph;

import ma.vi.base.tuple.T2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A VertexMap maps vertices to a set of adjacent vertices and is used
 * to facilitate building of graphs.
 *
 * @param <V> The vertex type of the graph to build.
 * @param <W> The type of weight on the edges.
 * @author vikash.madhow@gmail.com
 */
public class VertexMap<V, W> extends HashMap<V, Set<T2<V, W>>> {
  public VertexMap<V, W> add(V vertex1, V vertex2) {
    return add(vertex1, null, vertex2);
  }

  public VertexMap<V, W> add(V from, W weight, V... tos) {
    for (V to: tos) {
      Set<T2<V, W>> targets = get(from);
      if (targets == null) {
        targets = new HashSet<>();
        put(from, targets);
      }
      targets.add(T2.of(to, weight));
    }
    return this;
  }
}
