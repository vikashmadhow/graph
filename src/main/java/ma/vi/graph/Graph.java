package ma.vi.graph;

import java.util.Set;

/**
 * Defines the common state and behaviour of a graph.
 *
 * @param <V> The vertex type of the graph
 * @param <E> The edge type of the graph. This must be a subclass of
 *            {@link ma.vi.graph.Edge} specialised on current the node
 *            type of the graph.
 * @param <W> The weight type on the edges of the graph.
 * @author vikash.madhow@gmail.com
 */
public interface Graph<V, W, E extends Edge<V, W>> {
  /**
   * The set of vertices of the graph.
   */
  Set<V> vertices();

  /**
   * The set of edges in the graph.
   */
  Set<E> edges();

  /**
   * Returns the set of incoming edges for the given vertex.
   */
  Set<E> incoming(V vertex);

  /**
   * Returns the set of outgoing edges for the given vertex.
   */
  Set<E> outgoing(V vertex);

  /**
   * Returns the set of incoming and outgoing edges for the vertex.
   */
  Set<E> edges(V vertex);

  /**
   * Returns the degree of a vertex (number of edges having the vertex
   * as an endpoint).
   */
  int degree(V vertex);

  default int inDegree(V vertex) {
    return incoming(vertex).size();
  }

  default int outDegree(V vertex) {
    return outgoing(vertex).size();
  }

  /**
   * Creates a new edge connecting the two specified nodes appropriate for
   * the current type of graph.
   */
  E newEdge(V endPoint1, V endPoint2, W weight);

  /**
   * Produces a GraphViz representation of this graph in the DOT language.
   */
  String toGraphViz();
}
