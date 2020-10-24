package ma.vi.graph;

import java.util.Objects;

/**
 * <p>
 * An edge links two vertices, known informally as the source and target. Edges comes in
 * two varieties: Directed and Undirected. Directed edges discriminate between the source
 * and target vertices with the edge akin to a one-way road where traffic can only move
 * from the source to the target; undirected edges have no such restrictions and movement
 * can be construed as  allowed in both directions.
 * </p>
 *
 * <p>
 * Another major difference between directed and undirected edges is that an undirected edge
 * with source A and target B is equal to an undirected edge with source B and target A, i.e.,
 * an undirected edge is an unordered pair; whereas a directed edge behaves as an ordered pair.
 * </p>
 *
 * @param <V> The type of vertex that this edge connects.
 * @param <W> The weight type of the edge.
 * @author vikash.madhow@gmail.com
 */
public interface Edge<V, W> {
  /**
   * The first endpoint (vertex) of the edge.
   */
  V endPoint1();

  /**
   * The second endpoint (vertex) of the edge.
   */
  V endPoint2();

  /**
   * The weight of the edge.
   */
  W weight();

//  /**
//   * Equality of edges through the {@link #equals(Object)} method only compares
//   * the endpoints, ignoring the weight. Thus two edges between the same endpoints
//   * but with different weights would still be considered equal. This is
//   * True if this edge is equal to the other edge and they have the same weight.
//   */
//  default boolean sameEndPoint(Edge<V, W> other) {
//    return equals(other)
//        && Objects.equals(weight(), other.weight());
//  }
}