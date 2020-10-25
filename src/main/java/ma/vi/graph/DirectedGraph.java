package ma.vi.graph;

import ma.vi.graph.path.DirectedPath;
import ma.vi.graph.path.Path;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A Directed graph.
 *
 * @param <V> The vertex type.
 * @param <W> The weight type.
 * @author vikash.madhow@gmail.com
 */
public class DirectedGraph<V, W> extends AbstractGraph<V, W> {
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

  public DirectedGraph<V, W> newGraph(Set<Edge<V, W>> edges) {
    return new DirectedGraph<>(edges);
  }

  @Override
  public Path<V, W> path(Long cost, V vertex) {
    return new DirectedPath<>(cost, vertex);
  }

  @Override
  public Path<V, W> path(Long cost, LinkedHashSet<Edge<V, W>> edges) {
    return new DirectedPath<>(cost, edges);
  }
}
