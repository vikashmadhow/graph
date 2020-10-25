package ma.vi.graph;

import ma.vi.graph.path.DirectedPath;
import ma.vi.graph.path.Path;

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
  }

  @Override
  public boolean directed() {
    return true;
  }

  @Override
  public int degree(V vertex) {
    return incoming(vertex).size() + outgoing(vertex).size();
  }

  public DirectedEdge<V, W> newEdge(V endPoint1, W weight, V endPoint2) {
    return DirectedEdge.from(endPoint1, weight, endPoint2);
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
