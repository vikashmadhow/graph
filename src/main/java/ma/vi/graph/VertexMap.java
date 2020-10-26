package ma.vi.graph;

import ma.vi.base.lang.Builder;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A VertexMap maps vertices to a set of adjacent vertices and is used
 * to facilitate building of graphs.
 *
 * @param <V> The vertex type of the graph to build.
 * @param <W> The type of weight on the edges.
 * @author vikash.madhow@gmail.com
 */
public class VertexMap<V, W> implements Builder<Set<Edge<V, W>>> {
  public VertexMap<V, W> add(V vertex1, V vertex2) {
    return add(vertex1, null, vertex2);
  }

  public VertexMap<V, W> add(V from, W weight, V... tos) {
    for (V to: tos) {
      edges.computeIfAbsent(from, k -> new LinkedHashSet<>())
           .add(Edge.of(from, weight, to));
    }
    return this;
  }

  @Override
  public Set<Edge<V, W>> build() {
    Set<Edge<V, W>> es = new LinkedHashSet<>();
    edges.values().forEach(es::addAll);
    return es;
  }

  private final Map<V, Set<Edge<V, W>>> edges = new LinkedHashMap<>();
}
