package ma.vi.graph;

import java.util.Objects;

/**
 * An undirected edge in a graph is an unordered pair of vertices.
 *
 * @param <V> The vertex type.
 * @param <W> The weight type.
 * @author vikash.madhow@gmail.com
 */
public class UndirectedEdge<V, W> implements Edge<V, W> {
  public UndirectedEdge(V endPoint1, W weight, V endPoint2) {
    this.endPoint1 = endPoint1;
    this.endPoint2 = endPoint2;
    this.weight = weight;
  }

  public static <V, W> UndirectedEdge<V, W> with(V endPoint1, W weight, V endPoint2) {
    return new UndirectedEdge<>(endPoint1, weight, endPoint2);
  }

  @Override
  public V endPoint1() {
    return endPoint1;
  }

  @Override
  public V endPoint2() {
    return endPoint2;
  }

  @Override
  public W weight() {
    return weight;
  }

  public static <V, W> UndirectedEdge<V, W> make(V from, W weight, V to) {
    return new UndirectedEdge<>(from, weight, to);
  }

  /**
   * An undirected edge is equal to another if they have the same source
   * and targets in any order.
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof UndirectedEdge) {
      UndirectedEdge<V, W> that = (UndirectedEdge<V, W>)other;
      return (endPoint1.equals(that.endPoint1) && endPoint2.equals(that.endPoint2) && Objects.equals(weight(), that.weight()))
          || (endPoint1.equals(that.endPoint2) && endPoint2.equals(that.endPoint1) && Objects.equals(weight(), that.weight()));
    }
    return false;
  }

  /**
   * Symmetric hash code for unordered pair paralleling equals. I.e.,
   * two undirected edges will have the same hashcode irrespective of the
   * order of the source and target nodes.
   */
  @Override
  public int hashCode() {
    return 13 * (53 + (weight == null ? 0 : weight.hashCode()))
         + endPoint1.hashCode()
         + endPoint2.hashCode();
  }

  @Override
  public String toString() {
    return "(" + endPoint1 + "-<" + weight + ">-" + endPoint2 + ')';
  }

  public final V endPoint1;
  public final V endPoint2;
  public final W weight;
}
