package ma.vi.graph;

import ma.vi.base.tuple.T2;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A VertexMap maps vertices to a set of adjacent vertices and is used
 * to facilitate building of graphs.
 *
 * @param <V> The vertex type of the graph to build.
 * @param <W> The type of weight on the edges.
 * @author vikash.madhow@gmail.com
 */
public class VertexMap<V, W> extends LinkedHashMap<V, Set<T2<V, W>>> {
  public VertexMap<V, W> add(V vertex1, V vertex2) {
    return add(vertex1, null, vertex2);
  }

  public VertexMap<V, W> add(V from, W weight, V... tos) {
    if (firstVertex == null) {
      firstVertex = from;
    }
    if (tos.length > 0) {
      lastVertex = tos[tos.length - 1];
    } else if (lastVertex == null) {
      lastVertex = from;
    }
    for (V to: tos) {
      Set<T2<V, W>> targets = get(from);
      if (targets == null) {
        targets = new LinkedHashSet<>();
        put(from, targets);
      }
      targets.add(T2.of(to, weight));
    }
    return this;
  }

  public V firstVertex() {
    return firstVertex;
  }

  public V lastVertex() {
    return lastVertex;
  }

  private V firstVertex;
  private V lastVertex;
}
