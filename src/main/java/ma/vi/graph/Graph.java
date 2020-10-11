package ma.vi.graph;

import java.util.Set;

/**
 * Defines the common state and behaviour of a graph.
 *
 * @param <N> The node type of the graph
 * @param <E> The edge type of the graph. This must be a subclass of
 *            {@link ma.vi.graph.Edge} specialised on current the node
 *            type of the graph.
 * @param <W> The weight type on the edges of the graph.
 * @author vikash.madhow@gmail.com
 */
public interface Graph<N, W, E extends Edge<N, W>> {
  /**
   * The set of nodes in the graph.
   */
  Set<N> nodes();

  /**
   * The set of edges in the graph.
   */
  Set<E> edges();

  /**
   * Given a node, returns the set of incoming edges.
   */
  Set<E> incoming(N node);

  /**
   * Given a node, returns the set of outgoing edges.
   */
  Set<E> outgoing(N node);

  /**
   * Given a node, returns the set of incoming and outgoing edges.
   */
  Set<E> edges(N node);

  /**
   * Creates a new edge connecting the two specified nodes appropriate for
   * the current type of graph.
   */
  E newEdge(N endPoint1, N endPoint2, W weight);

  /**
   * Produces a GraphViz representation of this graph in the DOT language.
   */
  String toGraphViz();
}
