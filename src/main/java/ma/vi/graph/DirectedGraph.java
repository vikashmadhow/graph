package ma.vi.graph;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A Directed graph.
 *
 * @param <V> The vertex type.
 * @param <W> The weight type.
 * @author vikash.madhow@gmail.com
 */
public class DirectedGraph<V, W> extends AbstractGraph<V, W> {
  /**
   * Creates an empty graph.
   */
  public DirectedGraph() {
    super();
  }

  /**
   * Creates a graph with a single vertex.
   */
  public DirectedGraph(V vertex) {
    super(vertex);
  }

  /**
   * Creates a graph from a set of edges with its vertices obtained
   * from the endpoints of the edges.
   */
  public DirectedGraph(Set<Edge<V, W>> edges) {
    super(edges);
    for (Edge<V, W> e: edges) {
      out.computeIfAbsent(e.endPoint1, k -> new HashSet<>()).add(e);
      in.computeIfAbsent(e.endPoint2, k -> new HashSet<>()).add(e);
    }
  }

  @Override
  public boolean directed() {
    return true;
  }

  @Override
  public Optional<Edge<V, W>> edge(V v1, V v2) {
    for (Edge<V, W> edge: outgoing(v1)) {
      if (edge.endPoint2.equals(v2)) {
        return Optional.of(edge);
      }
    }
    return Optional.empty();
  }

  public DirectedGraph<V, W> newGraph(Set<Edge<V, W>> edges) {
    return new DirectedGraph<>(edges);
  }
}
