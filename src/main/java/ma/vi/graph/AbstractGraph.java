package ma.vi.graph;

import ma.vi.base.tuple.T2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public abstract class AbstractGraph<V, W, E extends Edge<V, W>> implements Graph<V, W, E> {
  /**
   * Creates a graph from the set of edges.
   */
  protected AbstractGraph(Set<E> edges) {
    this.edges = edges;
  }

  /**
   * Creates a graph from a node map.
   */
  protected AbstractGraph(VertexMap<V, W> nodeMap) {
    edges = new HashSet<>();
    vertices = new HashSet<>();
    for (Map.Entry<V, Set<T2<V, W>>> entry: nodeMap.entrySet()) {
      for (T2<V, W> to: entry.getValue()) {
        edges.add(newEdge(entry.getKey(), to.a(), to.b()));
        vertices.add(entry.getKey());
        vertices.add(to.a());
      }
    }
  }

  /**
   * Returns the set of all nodes in the graph.
   */
  @Override
  public Set<V> vertices() {
    if (vertices == null) {
      vertices = new HashSet<>();
      for (E e: edges) {
        vertices.add(e.endPoint1());
        vertices.add(e.endPoint2());
      }
    }
    return vertices;
  }

  /**
   * Returns the set of all edges in the graph.
   */
  @Override
  public Set<E> edges() {
    return edges;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof Graph) {
      Graph<V, W, E> that = (Graph<V, W, E>)o;
      return edges().equals(that.edges());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return edges.hashCode();
  }

  @Override
  public String toString() {
    return edges.toString();
  }

  /**
   * Produces a GraphViz representation of this graph in the DOT language.
   */
  public String toGraphViz() {
    StringBuilder spec = new StringBuilder("digraph G {\n");

//    // nodes
//    for (N node: nodes()) {
//      spec.append('\t').append('"').append(nodeToString(node)).append("\";\n");
//    }

    // edges
    for (E edge: edges) {
      spec.append('\t')
          .append('"').append(vertexToString(edge.endPoint1())).append('"')
          .append(edge instanceof DirectedEdge ? " -> " : " -- ")
          .append('"').append(vertexToString(edge.endPoint2())).append('"')
          .append(" [label=\"").append(weightToString(edge.weight())).append("\"];\n");
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
   * Edges of the graph.
   */
  protected final Set<E> edges;

  /**
   * Vertices in the graph.
   */
  protected Set<V> vertices;
}
