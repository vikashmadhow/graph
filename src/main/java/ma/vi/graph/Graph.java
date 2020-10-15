package ma.vi.graph;

import ma.vi.base.tuple.T2;
import ma.vi.graph.path.Path;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * Represents a graph consisting of a set of vertices of type V, connected
 * by edges of type E which can have a weight of type W. Graphs can be directed
 * in which case their edges are of type {@link DirectedEdge} or, undirected
 * with edge type {@link UndirectedEdge}.
 * </p>
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
   * Returns the edge between v1 and v2 in the graph or
   * empty if not found.
   */
  default Optional<E> edge(V v1, V v2) {
    for (E edge: edges(v1)) {
      if (edge.endPoint2().equals(v2)) {
        return Optional.of(edge);
      }
    }
    return Optional.empty();
  }

  /**
   * The set of edges in the graph.
   */
  Set<E> edges();

  /**
   * Returns whether the graph is directed or not.
   */
  boolean directed();

  /**
   * Returns the set of incoming edges into a given vertex. For undirected
   * graphs this is the same as the {@link #outgoing(Object)} edges from the
   * vertex.
   */
  Set<E> incoming(V vertex);

  /**
   * Returns the set of outgoing edges from a given vertex. For undirected
   * graphs this is the same as the {@link #incoming(Object)} edges into the
   * vertex.
   */
  Set<E> outgoing(V vertex);

  /**
   * Returns the set of incoming and outgoing edges for the vertex. For directed
   * graphs edges(Object) = {@link #incoming(Object)} + {@link #outgoing(Object)},
   * while for undirected graphs, edges(Object) == {@link #incoming(Object)} == {@link #outgoing(Object)}
   */
  Set<E> edges(V vertex);

  /**
   * Returns the degree of a vertex (number of edges having the vertex
   * as an endpoint).
   */
  int degree(V vertex);

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
   * Construct a path with the same edge type as this graph.
   *
   * @param cost  The cost of the path
   * @param edges The edges of the path in the right order as the first and last
   *              edges will be used to set the start and end  the path.
   */
  Path<V, W, E> path(Integer cost, LinkedHashSet<E> edges);

  /**
   * Construct a path with a single vertex and no edge.
   */
  Path<V, W, E> path(Integer cost, V vertex);

  /**
   * Construct a path with a single vertex and no edge. The
   * cost of the path is set to 0.
   */
  default Path<V, W, E> path(V vertex) {
    return path(0, vertex);
  }

  /**
   * Construct a path of the vertices in the given order. The
   * weight on the edges are set to null.
   */
  default Path<V, W, E> path(Integer cost, V... vertices) {
    Path<V, W, E> path = path(vertices[0]);
    for (int i = 1; i < vertices.length; i++) {
      path = path.extend(vertices[i], null, cost);
    }
    return path;
  }

  /**
   * Construct a path of the vertices, starting from the first vertex and
   * extended by adding an edge from the vertices array in the given order.
   */
  default Path<V, W, E> path(Integer cost, V first, T2<V, W>... vertices) {
    Path<V, W, E> path = path(first);
    for (T2<V, W> v: vertices) {
      path = path.extend(v.a(), v.b(), cost);
    }
    return path;
  }

  /**
   * Creates a new edge connecting the two specified nodes appropriate for
   * the current type of graph.
   */
  E newEdge(V endPoint1, W weight, V endPoint2);

  /**
   * Produces a GraphViz representation of this graph in the DOT language.
   */
  String toGraphViz();
}
