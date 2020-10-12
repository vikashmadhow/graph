package ma.vi.graph;

import java.util.*;

/**
 * A Directed graph.
 *
 * @param <V> The vertex type.
 * @param <W> The weight type.
 * @author vikash.madhow@gmail.com
 */
public class DirectedGraph<V, W> extends AbstractGraph<V, W, DirectedEdge<V, W>> {
  public DirectedGraph(Set<DirectedEdge<V, W>> edges) {
    super(edges);
  }

  public DirectedGraph(VertexMap<V, W> vertexMap) {
    super(vertexMap);
  }

  @Override
  public boolean directed() {
    return true;
  }

  /**
   * Given a node, returns the set of incoming edges.
   */
  public Set<DirectedEdge<V, W>> incoming(V node) {
    Set<DirectedEdge<V, W>> set = nodesToIncomingEdges().get(node);
    return set == null ? Collections.emptySet() : set;
  }

  /**
   * Given a node, returns the set of outgoing edges.
   */
  public Set<DirectedEdge<V, W>> outgoing(V node) {
    Set<DirectedEdge<V, W>> set = verticesToEdges().get(node);
    return set == null ? Collections.emptySet() : set;
  }

  /**
   * Given a node, returns the set of incoming and outgoing edges.
   */
  public Set<DirectedEdge<V, W>> edges(V node) {
    Set<DirectedEdge<V, W>> set = new HashSet<>();
    set.addAll(incoming(node));
    set.addAll(outgoing(node));
    return set;
  }

  @Override
  public int degree(V vertex) {
    return incoming(vertex).size() + outgoing(vertex).size();
  }

  public DirectedEdge<V, W> newEdge(V endPoint1, V endPoint2, W weight) {
    return DirectedEdge.from(endPoint1, endPoint2, weight);
  }

  private Map<V, Set<DirectedEdge<V, W>>> verticesToEdges() {
    if (verticesToEdges == null) {
      verticesToEdges = new HashMap<>();
      for (DirectedEdge<V, W> e: edges) {
        Set<DirectedEdge<V, W>> targets = verticesToEdges.computeIfAbsent(e.endPoint1(), k -> new HashSet<>());
        targets.add(e);
      }
    }
    return verticesToEdges;
  }

  private Map<V, Set<DirectedEdge<V, W>>> nodesToIncomingEdges() {
    if (nodesToIncomingEdges == null) {
      nodesToIncomingEdges = new HashMap<>();
      for (DirectedEdge<V, W> e: edges) {
        Set<DirectedEdge<V, W>> targets = nodesToIncomingEdges.computeIfAbsent(e.endPoint2(), k -> new HashSet<>());
        targets.add(e);
      }
    }
    return nodesToIncomingEdges;
  }

  /**
   * Nodes to outgoing edges.
   */
  private Map<V, Set<DirectedEdge<V, W>>> verticesToEdges;

  /**
   * Nodes to incoming edges.
   */
  private Map<V, Set<DirectedEdge<V, W>>> nodesToIncomingEdges;
}
