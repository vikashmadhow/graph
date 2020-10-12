package ma.vi.graph;

import ma.vi.base.tuple.T3;

/**
 * A directed edge is an ordered pair of vertices.
 *
 * @param <V> The vertex type.
 * @param <W> The weight type.
 * @author vikash.madhow@gmail.com
 */
public class DirectedEdge<V, W> extends T3<V, V, W> implements Edge<V, W> {
  public DirectedEdge(V endPoint1, V endPoint2, W weight) {
    super(endPoint1, endPoint2, weight);
  }

  public static <V, W> DirectedEdge<V, W> from(V endPoint1, V endPoint2, W weight) {
    return new DirectedEdge<>(endPoint1, endPoint2, weight);
  }

  @Override
  public V endPoint1() {
    return a();
  }

  @Override
  public V endPoint2() {
    return b();
  }

  @Override
  public W weight() {
    return c();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof DirectedEdge) {
      DirectedEdge<V, W> that = (DirectedEdge<V, W>)other;
      return (endPoint1().equals(that.endPoint1()) && endPoint2().equals(that.endPoint2()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return 13 * (53 + endPoint1().hashCode()) + endPoint2().hashCode();
  }

  @Override
  public String toString() {
    return "(" + endPoint1() + "-<" + weight() + ">-" + endPoint2() + ')';
  }
}
