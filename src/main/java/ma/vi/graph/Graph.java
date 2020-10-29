package ma.vi.graph;

import ma.vi.graph.algo.Algorithm;

import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * Represents a graph consisting of a set of vertices of type V, connected
 * by edges of type {@link Edge} which can have a weight of type W. Graphs
 * can be directed undirected.
 * </p>
 *
 * @param <V> The vertex type of the graph
 * @param <W> The weight type on the edges of the graph.
 * @author vikash.madhow@gmail.com
 */
public interface Graph<V, W> {
  /**
   * The set of vertices of the graph.
   */
  Set<V> vertices();

  /**
   * Returns the set of all incoming edges of the graph.
   */
  Set<Edge<V, W>> incoming();

  /**
   * Returns the set of all outgoing edges of the graph.
   */
  Set<Edge<V, W>> outgoing();

  /**
   * The set of edges in the graph.
   */
  Set<Edge<V, W>> edges();

  /**
   * Returns the set of incoming edges into a given vertex. For undirected
   * graphs this is the same as the {@link #outgoing(Object)} edges from the
   * vertex.
   */
  Set<Edge<V, W>> incoming(V vertex);

  /**
   * Returns the set of outgoing edges from a given vertex. For undirected
   * graphs this is the same as the {@link #incoming(Object)} edges into the
   * vertex.
   */
  Set<Edge<V, W>> outgoing(V vertex);

  /**
   * Returns the set of incoming and outgoing edges for the vertex. For directed
   * graphs edges(Object) = {@link #incoming(Object)} + {@link #outgoing(Object)},
   * while for undirected graphs, edges(Object) == {@link #incoming(Object)} == {@link #outgoing(Object)}
   */
  Set<Edge<V, W>> edges(V vertex);

  /**
   * Returns whether the graph is directed or not.
   */
  boolean directed();

  /**
   * Returns the edge between v1 and v2 in the graph or
   * empty if not found.
   */
  Optional<Edge<V, W>> edge(V v1, V v2);

  /**
   * Returns the degree of a vertex (number of edges having the vertex
   * as an endpoint).
   */
  default int degree(V vertex) {
    return inDegree(vertex) + outDegree(vertex);
  }

  /**
   * Returns the in-degree of a vertex (number of edges pointing to this vertex).
   * For undirected graphs this is the same as the degree.
   */
  default int inDegree(V vertex) {
    return incoming(vertex).size();
  }

  /**
   * Returns the out-degree of a vertex (number of edges out of this vertex).
   * For undirected graphs this is the same as the degree.
   */
  default int outDegree(V vertex) {
    return outgoing(vertex).size();
  }

  /**
   * Creates a new graph of the current type.
   */
  Graph<V, W> newGraph(Set<Edge<V, W>> edges);

  /**
   * Applies an algorithm to the graph and returns the value produced.
   */
  default <R> R apply(Algorithm<V, W, R> algo) {
    return algo.execute(this);
  }

  /**
   * Produces a GraphViz representation of this graph in the DOT language.
   */
  String toGraphViz();
}
