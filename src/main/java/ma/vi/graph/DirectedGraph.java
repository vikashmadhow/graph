package ma.vi.graph;

import java.util.*;

/** A Directed graph.
 *
 * @param <N> The node type.
 * @param <W> The weight type.
 *
 * @author vikash.madhow@gmail.com
 */
public class DirectedGraph<N, W> extends AbstractGraph<N, W, DirectedEdge<N, W>>
{
    public DirectedGraph(Set<DirectedEdge<N, W>> edges)
    {
       super(edges);
    }

    public DirectedGraph(NodeMap<N, W> nodeMap)
    {
        super(nodeMap);
    }

    private Map<N, Set<DirectedEdge<N, W>>> nodesToEdges()
    {
        if (nodesToEdges == null)
        {
            nodesToEdges = new HashMap<N, Set<DirectedEdge<N, W>>>();
            for (DirectedEdge<N, W> e : edges)
            {
                Set<DirectedEdge<N, W>> targets = nodesToEdges.get(e.endPoint1());
                if (targets == null)
                {
                    targets = new HashSet<DirectedEdge<N, W>>();
                    nodesToEdges.put(e.endPoint1(), targets);
                }
                targets.add(e);
            }
        }
        return nodesToEdges;
    }

    private Map<N, Set<DirectedEdge<N, W>>> nodesToIncomingEdges()
    {
        if (nodesToIncomingEdges == null)
        {
            nodesToIncomingEdges = new HashMap<N, Set<DirectedEdge<N, W>>>();
            for (DirectedEdge<N, W> e : edges)
            {
                Set<DirectedEdge<N, W>> targets = nodesToIncomingEdges.get(e.endPoint2());
                if (targets == null)
                {
                    targets = new HashSet<DirectedEdge<N, W>>();
                    nodesToIncomingEdges.put(e.endPoint2(), targets);
                }
                targets.add(e);
            }
        }
        return nodesToIncomingEdges;
    }

    /** Given a node, returns the set of incoming edges. */
    public Set<DirectedEdge<N, W>> incoming(N node)
    {
        Set<DirectedEdge<N, W>> set = nodesToIncomingEdges().get(node);
        return set == null ? Collections.<DirectedEdge<N, W>>emptySet() : set;
    }

    /** Given a node, returns the set of outgoing edges. */
    public Set<DirectedEdge<N, W>> outgoing(N node)
    {
        Set<DirectedEdge<N, W>> set = nodesToEdges().get(node);
        return set == null ? Collections.<DirectedEdge<N, W>>emptySet() : set;
    }

    /** Given a node, returns the set of incoming and outgoing edges. */
    public Set<DirectedEdge<N, W>> edges(N node)
    {
        Set<DirectedEdge<N, W>> set = new HashSet<DirectedEdge<N, W>>();
        set.addAll(incoming(node));
        set.addAll(outgoing(node));
        return set;
    }

    public DirectedEdge<N, W> newEdge(N endPoint1, N endPoint2, W weight)
    {
        return DirectedEdge.with(endPoint1, endPoint2, weight);
    }

    /** Nodes to outgoing edges. */
    private Map<N, Set<DirectedEdge<N, W>>> nodesToEdges;

    /** Nodes to incoming edges. */
    private Map<N, Set<DirectedEdge<N, W>>> nodesToIncomingEdges;
}
