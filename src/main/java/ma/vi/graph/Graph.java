package ma.vi.graph;

import ma.vi.base.tuple.T2;
import ma.vi.graph.algo.Algorithm;
import ma.vi.graph.path.Path;

import java.util.Arrays;
import java.util.LinkedHashSet;
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
 *
 * @author vikash.madhow@gmail.com
 */
public interface Graph<V, W> {
  /**
   * The set of vertices of the graph.
   */
  Set<V> vertices();

  Set<Edge<V, W>> incoming();

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
  default Optional<Edge<V, W>> edge(V v1, V v2) {
    for (Edge<V, W> edge: edges(v1)) {
      if (edge.endPoint2.equals(v2)) {
        return Optional.of(edge);
      }
    }
    return Optional.empty();
  }

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
   * Construct a path with the same edge type as this graph.
   *
   * @param cost  The cost of the path
   * @param edges The edges of the path in the right order as the first and last
   *              edges will be used to set the start and end  the path.
   */
  Path<V, W> path(Long cost, LinkedHashSet<Edge<V, W>> edges);

  default Path<V, W> path(Long cost, Edge<V, W>... edges) {
    return path(cost, new LinkedHashSet<>(Arrays.asList(edges)));
  }

  default Path<V, W> path(Long cost, Optional<Edge<V, W>>... edges) {
    LinkedHashSet<Edge<V, W>> edgesSet = new LinkedHashSet<>();
    for (Optional<Edge<V, W>> e: edges) {
      e.ifPresent(edgesSet::add);
    }
    return path(cost, edgesSet);
  }

  /**
   * Construct a path with a single vertex and no edge.
   */
  Path<V, W> path(Long cost, V vertex);

  /**
   * Construct a path with a single vertex and no edge. The
   * cost of the path is set to 0.
   */
  default Path<V, W> path(V vertex) {
    return path(0L, vertex);
  }

  /**
   * Construct a path of the vertices in the given order. The
   * weight on the edges are set to null.
   */
  default Path<V, W> path(Long cost, V... vertices) {
    Path<V, W> path = path(vertices[0]);
    for (int i = 1; i < vertices.length; i++) {
      path = path.extend(vertices[i], null, cost);
    }
    return path;
  }

  /**
   * Construct a path of the vertices, starting from the first vertex and
   * extended by adding an edge from the vertices array in the given order.
   */
  default Path<V, W> path(Long cost, V first, T2<V, W>... vertices) {
    Path<V, W> path = path(first);
    for (T2<V, W> v: vertices) {
      path = path.extend(v.a(), v.b(), cost);
    }
    return path;
  }

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
