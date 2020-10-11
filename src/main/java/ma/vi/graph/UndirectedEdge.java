package ma.vi.graph;

/**
 * An edge in a graph is simply an unordered pair of nodes.
 *
 * @param <N> The node type.
 * @param <W> The weight type.
 * @author vikash.madhow@gmail.com
 */
public class UndirectedEdge<N, W> implements Edge<N, W> {
  public UndirectedEdge(N endPoint1, N endPoint2, W weight) {
    this.endPoint1 = endPoint1;
    this.endPoint2 = endPoint2;
    this.weight = weight;
  }

  public static <N, W> UndirectedEdge<N, W> with(N endPoint1, N endPoint2, W weight) {
    return new UndirectedEdge<N, W>(endPoint1, endPoint2, weight);
  }

  @Override
  public N endPoint1() {
    return endPoint1;
  }

  @Override
  public N endPoint2() {
    return endPoint2;
  }

  @Override
  public W weight() {
    return weight;
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
      UndirectedEdge<N, W> that = (UndirectedEdge<N, W>)other;
      return (endPoint1.equals(that.endPoint1) && endPoint2.equals(that.endPoint2)) ||
          (endPoint1.equals(that.endPoint2) && endPoint2.equals(that.endPoint1));
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
    return 53 + endPoint1.hashCode() + endPoint2.hashCode();
  }

  @Override
  public String toString() {
    return "(" + endPoint1 + "-<" + weight + ">-" + endPoint2 + ')';
  }

  public final N endPoint1;
  public final N endPoint2;
  public final W weight;
}
