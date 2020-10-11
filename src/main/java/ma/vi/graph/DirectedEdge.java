package ma.vi.graph;

import ma.vi.base.tuple.T3;

/** A directed edge is an ordered pair of nodes.
 *
 * @param <N> The node type.
 * @param <W> The weight type.
 *
 * @author vikash.madhow@gmail.com
 */
public class DirectedEdge<N, W> extends T3<N, N, W> implements Edge<N, W>
{
    public DirectedEdge(N endPoint1, N endPoint2, W weight)
    {
        super(endPoint1, endPoint2, weight);
    }

    public static <N, W> DirectedEdge<N, W> with(N endPoint1, N endPoint2, W weight)
    {
        return new DirectedEdge<>(endPoint1, endPoint2, weight);
    }

    @Override public N endPoint1() { return a(); }
    @Override public N endPoint2() { return b(); }
    @Override public W weight()    { return c(); }

    @Override public String toString()
    {
        return "(" + endPoint1() + "-<" + weight() + ">-" + endPoint2() + ')';
    }
}
