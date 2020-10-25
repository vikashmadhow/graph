package ma.vi.graph;

import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

/**
 * A partial implementation of the generic methods of the {@link Graph} interface.
 *
 * @param <V> The vertex type of the graph
 * @param <E> The edge type of the graph. This must be a subclass of
 *            {@link ma.vi.graph.Edge} specialised on current the node
 *            type of the graph.
 * @param <W> The weight type on the edges of the graph.
 * @author vikash.madhow@gmail.com
 */
public abstract class AbstractGraph<V, W> implements Graph<V, W> {
  /**
   * Creates a graph from the set of edges.
   */
  protected AbstractGraph(Set<Edge<V, W>> edges) {
    this.edges = edges;
  }

  @Override public Set<Edge<V, W>> incoming() {
    Set<Edge<V, W>> edges = new LinkedHashSet<>();
    in.values().forEach(edges::addAll);
    return edges;
  }

  @Override public Set<Edge<V, W>> outgoing() {
    Set<Edge<V, W>> edges = new LinkedHashSet<>();
    in.values().forEach(edges::addAll);
    return edges;
  }

  /**
   * Returns the set of all edges in the graph.
   */
  @Override
  public Set<Edge<V, W>> edges() {
    if (edges == null) {
      edges = new HashSet<>();
      out.values().forEach(edges::addAll);
      in.values().forEach(edges::addAll);
    }
    return edges;
  }

  /**
   * Returns the set of incoming edges to the vertex.
   */
  public Set<Edge<V, W>> incoming(V vertex) {
    return in.getOrDefault(vertex, emptySet());
  }

  /**
   * Returns the set of outgoing edges from the vertex.
   */
  public Set<Edge<V, W>> outgoing(V vertex) {
    return out.getOrDefault(vertex, emptySet());
  }

  @Override
  public Set<Edge<V, W>> edges(V vertex) {
    Set<Edge<V, W>> edges = new LinkedHashSet<>(in.getOrDefault(vertex, emptySet()));
    edges.addAll(out.getOrDefault(vertex, emptySet()));
    return edges;
  }

  /**
   * Returns the set of all nodes in the graph.
   */
  @Override
  public Set<V> vertices() {
    if (vertices == null) {
      vertices = new HashSet<>(in.keySet());
      vertices.addAll(out.keySet());
    }
    return unmodifiableSet(vertices);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof Graph) {
      Graph<V, W> that = (Graph<V, W>)o;
      return in.equals(that.incoming()) &&
             out.equals(that.outgoing());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = hash * 13 + in.hashCode();
    hash = hash * 13 + out.hashCode();
    return hash;
  }

  @Override
  public String toString() {
    return edges().toString();
  }

  /**
   * Produces a GraphViz representation of this graph in the DOT language.
   */
  public String toGraphViz() {
    StringBuilder spec = new StringBuilder(directed() ? "digraph" : "graph").append(" G {\n");
    for (Edge<V, W> edge: edges()) {
      spec.append('\t')
          .append('"').append(vertexToString(edge.endPoint1)).append('"')
          .append(directed() ? " -> " : " -- ")
          .append('"').append(vertexToString(edge.endPoint2)).append('"')
          .append(" [label=\"").append(weightToString(edge.weight)).append("\"];\n");
    }
    spec.append("}");
    return spec.toString();
  }

  protected String vertexToString(V vertex) {
    return String.valueOf(vertex);
  }

  protected String weightToString(W weight) {
    return String.valueOf(weight);
  }

  /**
   * Vertices to outgoing edges.
   */
  protected final Map<V, Set<Edge<V, W>>> out = new HashMap<>();

  /**
   * Vertices to incoming edges.
   */
  protected final Map<V, Set<Edge<V, W>>> in = new HashMap<>();

  /**
   * Edges of the graph.
   */
  protected volatile Set<Edge<V, W>> edges;

  /**
   * Vertices in the graph.
   */
  protected volatile Set<V> vertices;
}
