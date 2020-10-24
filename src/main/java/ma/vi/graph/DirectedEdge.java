package ma.vi.graph;

import ma.vi.base.tuple.T3;

import java.util.Objects;

/**
 * A directed edge is an ordered pair of vertices.
 *
 * @param <V> The vertex type.
 * @param <W> The weight type.
 * @author vikash.madhow@gmail.com
 */
public class DirectedEdge<V, W> extends T3<V, W, V> implements Edge<V, W> {
  public DirectedEdge(V endPoint1, W weight, V endPoint2) {
    super(endPoint1, weight, endPoint2);
  }

  public static <V, W> DirectedEdge<V, W> from(V endPoint1, W weight, V endPoint2) {
    return new DirectedEdge<>(endPoint1, weight, endPoint2);
  }

  @Override
  public V endPoint1() {
    return a();
  }

  @Override
  public W weight() {
    return b();
  }

  @Override
  public V endPoint2() {
    return c();
  }

  public static <V, W> DirectedEdge<V, W> make(V from, W weight, V to) {
    return new DirectedEdge<>(from, weight, to);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof DirectedEdge) {
      DirectedEdge<V, W> that = (DirectedEdge<V, W>)other;
      return endPoint1().equals(that.endPoint1())
          && endPoint2().equals(that.endPoint2())
          && Objects.equals(weight(), that.weight());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = hash * 13 + (weight() == null ? 0 : weight().hashCode());
    hash = hash * 13 + endPoint1().hashCode();
    hash = hash * 13 + endPoint2().hashCode();
    return hash;
  }

  @Override
  public String toString() {
    return "(" + endPoint1() + "-<" + weight() + ">-" + endPoint2() + ')';
  }
}
