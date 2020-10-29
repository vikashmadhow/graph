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
public class Edge<V, W> {
  public Edge(V endPoint1, W weight, V endPoint2) {
    this.endPoint1 = endPoint1;
    this.endPoint2 = endPoint2;
    this.weight = weight;
  }

  public static <V, W> Edge<V, W> of(V endPoint1, W weight, V endPoint2) {
    return new Edge<>(endPoint1, weight, endPoint2);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge<?, ?> edge = (Edge<?, ?>)o;
    return endPoint1.equals(edge.endPoint1)
        && endPoint2.equals(edge.endPoint2)
        && Objects.equals(weight, edge.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(endPoint1, endPoint2, weight);
  }

  @Override
  public String toString() {
    return endPoint1 + "-<" + weight + ">-" + endPoint2;
  }

  /**
   * The first endpoint (vertex) of the edge.
   */
  public final V endPoint1;

  /**
   * The second endpoint (vertex) of the edge.
   */
  public final V endPoint2;

  /**
   * The weight of the edge.
   */
  public final W weight;
}