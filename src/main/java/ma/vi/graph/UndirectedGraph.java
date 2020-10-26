package ma.vi.graph;

import ma.vi.graph.path.Path;
import ma.vi.graph.path.UndirectedPath;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
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
    for (Edge<V, W> e: edges) {
      out.computeIfAbsent(e.endPoint1, k -> new HashSet<>()).add(e);
      out.computeIfAbsent(e.endPoint2, k -> new HashSet<>()).add(e);
      in.computeIfAbsent(e.endPoint1, k -> new HashSet<>()).add(e);
      in.computeIfAbsent(e.endPoint2, k -> new HashSet<>()).add(e);
    }
  }

  @Override
  public boolean directed() {
    return false;
  }

  @Override
  public Optional<Edge<V, W>> edge(V v1, V v2) {
    for (Edge<V, W> edge: incoming(v1)) {
      if ((edge.endPoint1.equals(v1) && edge.endPoint2.equals(v2))
       || (edge.endPoint1.equals(v2) && edge.endPoint2.equals(v1))) {
        return Optional.of(edge);
      }
    }
    for (Edge<V, W> edge: outgoing(v1)) {
      if ((edge.endPoint1.equals(v1) && edge.endPoint2.equals(v2))
       || (edge.endPoint1.equals(v2) && edge.endPoint2.equals(v1))) {
        return Optional.of(edge);
      }
    }
    return Optional.empty();
  }

  @Override
  public int degree(V vertex) {
    return inDegree(vertex);
  }

  @Override
  public int inDegree(V vertex) {
    int degree = incoming(vertex).size();
    if (edge(vertex, vertex).isPresent()) {
      // self-loop contribute 2 to degree in undirected graphs
      degree += 1;
    }
    return degree;
  }

  @Override
  public int outDegree(V vertex) {
    return inDegree(vertex);
  }

  /**
   * Given a node, returns the set of incoming and outgoing edges.
   */
  public Set<Edge<V, W>> edges(V vertex) {
    return incoming(vertex);
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
