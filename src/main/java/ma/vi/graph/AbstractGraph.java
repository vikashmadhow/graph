package ma.vi.graph;

import ma.vi.base.tuple.T2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A partial implementation of the generic methods of the {@link Graph} interface.
 *
 * @param <N> The node type of the graph
 * @param <E> The edge type of the graph. This must be a subclass of
 *            {@link ma.vi.graph.Edge} specialised on current the node
 *            type of the graph.
 * @param <W> The weight type on the edges of the graph.
 * @author vikash.madhow@gmail.com
 */
public abstract class AbstractGraph<N, W, E extends Edge<N, W>> implements Graph<N, W, E> {
  /**
   * Creates a graph from the set of edges.
   */
  protected AbstractGraph(Set<E> edges) {
    this.edges = edges;
  }

  /**
   * Creates a graph from a node map.
   */
  protected AbstractGraph(NodeMap<N, W> nodeMap) {
    edges = new HashSet<E>();
    for (Map.Entry<N, Set<T2<N, W>>> entry: nodeMap.entrySet()) {
      for (T2<N, W> to: entry.getValue()) {
        edges.add(newEdge(entry.getKey(), to.a(), to.b()));
      }
    }
  }

  /**
   * Returns the set of all nodes in the graph.
   */
  @Override
  public Set<N> nodes() {
    if (nodes == null) {
      nodes = new HashSet<N>();
      for (E e: edges) {
        nodes.add(e.endPoint1());
        nodes.add(e.endPoint2());
      }
    }
    return nodes;
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
      Graph<N, W, E> that = (Graph<N, W, E>)o;
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
    StringBuffer spec = new StringBuffer("digraph G {\n");

//    // nodes
//    for (N node: nodes()) {
//      spec.append('\t').append('"').append(nodeToString(node)).append("\";\n");
//    }

    // edges
    for (E edge: edges) {
      spec.append('\t')
          .append('"').append(nodeToString(edge.endPoint1())).append('"')
          .append(edge instanceof DirectedEdge ? " -> " : " -- ")
          .append('"').append(nodeToString(edge.endPoint2())).append('"')
          .append(" [label=\"").append(weightToString(edge.weight())).append("\"];\n");
    }
    spec.append("}");
    return spec.toString();
  }

  protected String nodeToString(N node) {
    return String.valueOf(node);
  }

  protected String weightToString(W weight) {
    return String.valueOf(weight);
  }

  /**
   * Edges of the graph.
   */
  protected final Set<E> edges;

  /**
   * Nodes in the graph.
   */
  protected Set<N> nodes;
}
