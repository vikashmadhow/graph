package ma.vi.graph;

import ma.vi.graph.path.Path;
import ma.vi.graph.path.UndirectedPath;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * An undirected graph.
 *
 * @param <V> The vertex type of the graph.
 * @param <W> The weight type of the graph.
 * @author vikash.madhow@gmail.com
 */
public class UndirectedGraph<V, W> extends AbstractGraph<V, W> {
  public UndirectedGraph(Set<Edge<V, W>> edges) {
    super(edges);
  }

  @Override
  public boolean directed() {
    return false;
  }

  /**
   * Given a node, returns the set of incoming and outgoing edges.
   */
  public Set<Edge<V, W>> edges(V vertex) {
    return incoming(vertex);
  }

  @Override
  public int degree(V vertex) {
    return incoming(vertex).size();
  }

  public UndirectedGraph<V, W> newGraph(Set<Edge<V, W>> edges) {
    return new UndirectedGraph<>(edges);
  }

  @Override
  public Path<V, W> path(Long cost, V vertex) {
    return new UndirectedPath<>(cost, vertex);
  }

  @Override
  public Path<V, W> path(Long cost, LinkedHashSet<Edge<V, W>> edges) {
    return new UndirectedPath<>(cost, edges);
  }
}
