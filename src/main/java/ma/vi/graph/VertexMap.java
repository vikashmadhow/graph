package ma.vi.graph;

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
public class VertexMap<V, W> extends LinkedHashMap<V, Set<Edge<V, W>>> {
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
      Set<Edge<V, W>> targets = get(from);
      if (targets == null) {
        targets = new LinkedHashSet<>();
        put(from, targets);
      }
      targets.add(Edge.of(from, weight, to));
    }
    return this;
  }

  public V firstVertex() {
    return firstVertex;
  }

  public V lastVertex() {
    return lastVertex;
  }

  public Set<Edge<V, W>> build() {
    Set<Edge<V, W>> edges = new LinkedHashSet<>();
    values().forEach(edges::addAll);
    return edges;
  }

  private V firstVertex;
  private V lastVertex;
}
