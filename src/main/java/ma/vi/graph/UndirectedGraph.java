package ma.vi.graph;

import ma.vi.graph.path.Path;
import ma.vi.graph.path.UndirectedPath;

import java.util.HashSet;
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
    for (Edge<V, W> e: edges) {
//      if (e.endPoint1.equals(e.endPoint2)) {
//        /*
//         * a - a : out = {a: a-a}  in = {a: a-a}
//         */
//        out.computeIfAbsent(e.endPoint1, k -> new HashSet<>()).add(e);
//        in.computeIfAbsent(e.endPoint2, k -> new HashSet<>()).add(e);
//      } else {
        /*
         * a - b : out = {a: a-b, b: a-b} in = {a: a-b, b:a-b}
         */
        out.computeIfAbsent(e.endPoint1, k -> new HashSet<>()).add(e);
        out.computeIfAbsent(e.endPoint2, k -> new HashSet<>()).add(e);
        in.computeIfAbsent(e.endPoint1, k -> new HashSet<>()).add(e);
        in.computeIfAbsent(e.endPoint2, k -> new HashSet<>()).add(e);
//      }
    }
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
