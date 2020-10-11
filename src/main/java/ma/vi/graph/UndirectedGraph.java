package ma.vi.graph;

import java.util.*;

/** An undirected graph.
 *
 * @param <N> The node type of the graph.
 * @param <W> The weight type of the graph.
 *
 * @author vikash.madhow@gmail.com
 */
public class UndirectedGraph<N, W> extends AbstractGraph<N, W, UndirectedEdge<N, W>>
{
    public UndirectedGraph(Set<UndirectedEdge<N, W>> edges)
    {
        super(edges);
    }

    public UndirectedGraph(NodeMap<N, W> nodeMap)
    {
        super(nodeMap);
    }

    /** Build the nodesToEdges map to facilitate graph navigation. */
    private Map<N, Set<UndirectedEdge<N, W>>> nodesToEdges()
    {
        if (nodesToEdges == null)
        {
            nodesToEdges = new HashMap<N, Set<UndirectedEdge<N, W>>>();
            for (UndirectedEdge<N, W> e : edges)
            {
                Set<UndirectedEdge<N, W>> targets = nodesToEdges.get(e.endPoint1);
                if (targets == null)
                {
                    targets = new HashSet<UndirectedEdge<N, W>>();
                    nodesToEdges.put(e.endPoint1, targets);
                }
                targets.add(e);

                targets = nodesToEdges.get(e.endPoint2);
                if (targets == null)
                {
                    targets = new HashSet<UndirectedEdge<N, W>>();
                    nodesToEdges.put(e.endPoint2, targets);
                }
                targets.add(newEdge(e.endPoint2, e.endPoint1, e.weight));
            }
        }
        return nodesToEdges;
    }

    /** Given a node, returns the set of incoming edges. */
    public Set<UndirectedEdge<N, W>> incoming(N node)
    {
        Set<UndirectedEdge<N, W>> incoming = nodesToEdges().get(node);
        return incoming == null ? Collections.<UndirectedEdge<N, W>>emptySet() : incoming;
    }

    /** Given a node, returns the set of outgoing edges. */
    public Set<UndirectedEdge<N, W>> outgoing(N node)
    {
        return incoming(node);
    }

    /** Given a node, returns the set of incoming and outgoing edges. */
    public Set<UndirectedEdge<N, W>> edges(N node)
    {
        return incoming(node);
    }

    /** Creates a new edge connecting the two specified nodes appropriate for
    * the current type of graph. */
    public UndirectedEdge<N, W> newEdge(N endPoint1, N endPoint2, W weight)
    {
        return UndirectedEdge.with(endPoint1, endPoint2, weight);
    }

    /** A mapping of nodes to edges, to facilitate computing incoming edges. */
    private Map<N, Set<UndirectedEdge<N, W>>> nodesToEdges;
}
