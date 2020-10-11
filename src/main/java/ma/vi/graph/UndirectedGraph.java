package ma.vi.graph;

import java.util.*;

/**
 * An undirected graph.
 *
 * @param <V> The vertex type of the graph.
 * @param <W> The weight type of the graph.
 * @author vikash.madhow@gmail.com
 */
public class UndirectedGraph<V, W> extends AbstractGraph<V, W, UndirectedEdge<V, W>> {
  public UndirectedGraph(Set<UndirectedEdge<V, W>> edges) {
    super(edges);
  }

  public UndirectedGraph(VertexMap<V, W> vertexMap) {
    super(vertexMap);
  }

  /**
   * Build the nodesToEdges map to facilitate graph navigation.
   */
  private Map<V, Set<UndirectedEdge<V, W>>> verticesToEdges() {
    if (verticesToEdges == null) {
      verticesToEdges = new HashMap<>();
      for (UndirectedEdge<V, W> e: edges) {
        Set<UndirectedEdge<V, W>> targets = verticesToEdges.computeIfAbsent(e.endPoint1, k -> new HashSet<>());
        targets.add(e);
        targets = verticesToEdges.computeIfAbsent(e.endPoint2, k -> new HashSet<>());
        targets.add(newEdge(e.endPoint2, e.endPoint1, e.weight));
      }
    }
    return verticesToEdges;
  }

  /**
   * Given a vertex, returns the set of incoming edges.
   */
  public Set<UndirectedEdge<V, W>> incoming(V vertex) {
    Set<UndirectedEdge<V, W>> incoming = verticesToEdges().get(vertex);
    return incoming == null ? Collections.emptySet() : incoming;
  }

  /**
   * Given a vertex, returns the set of outgoing edges.
   */
  public Set<UndirectedEdge<V, W>> outgoing(V vertex) {
    return incoming(vertex);
  }

  @Override
  public int degree(V vertex) {
    return incoming(vertex).size();
  }

  /**
   * Given a node, returns the set of incoming and outgoing edges.
   */
  public Set<UndirectedEdge<V, W>> edges(V vertex) {
    return incoming(vertex);
  }

  /**
   * Creates a new edge connecting the two specified nodes appropriate for
   * the current type of graph.
   */
  public UndirectedEdge<V, W> newEdge(V endPoint1, V endPoint2, W weight) {
    return UndirectedEdge.with(endPoint1, endPoint2, weight);
  }

  /**
   * A mapping of nodes to edges, to facilitate computing incoming edges.
   */
  private Map<V, Set<UndirectedEdge<V, W>>> verticesToEdges;
}
