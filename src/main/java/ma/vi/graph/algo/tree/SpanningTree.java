package ma.vi.graph.algo.tree;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.*;
import ma.vi.graph.algo.explore.Explore;
import ma.vi.graph.path.Path;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * A generic algorithm for constructing a spanning tree from a graph which is
 * further specialised to construct minimum spanning tree's using Prim's and
 * Kruskal's algorithms.
 * </p>
 *
 * @param <V> The vertex type of the graph to search.
 * @param <W> The weight type on the edges of the graph to search.
 * @param <E> The edge type of the graph to search.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class SpanningTree<V, W, E extends Edge<V, W>> implements Algorithm<V, W, E, Graph<V, W, E>> {
  /**
   * Sets the path queue to use which controls the exploration behaviour.
   * For instance, {@link FifoPathQueue} will explore breadth-first,
   * {@link LifoPathQueue} will result in depth-first while {@link PriorityPathQueue}
   * will explore path in order of costs (which can then be used to implement
   * Prim's algorithm).
   */
  public SpanningTree<V, W, E> pathQueue(PathQueue<V, W, E> pathQueue) {
    this.pathQueue = pathQueue;
    return this;
  }

  /**
   * A function taking as parameters the graph and current exploring path and
   * returning the successor edges to explore. The default function returns
   * all outgoing edges of the current path.
   */
  public SpanningTree<V, W, E> expandOp(ExpandOp<V, W, E> expandOp) {
    this.expandOp = expandOp;
    return this;
  }

  /**
   * A function to compute the cost of a new path, invoked with the graph, path
   * to be extended, the edge with which the path is being extended and, optionally,
   * a goal vertex.
   */
  public SpanningTree<V, W, E> pathCostOp(PathCostOp<V, W, E> pathCostOp) {
    this.pathCostOp = pathCostOp;
    return this;
  }

  @Override
  public Graph<V, W, E> execute(Graph<V, W, E> graph) {
    V startVertex = graph.vertices().iterator().next();
    Set<E> edges = new HashSet<>();
    new Explore<V, W, E, Path<V, W, E>>(startVertex)
                .pathQueue(pathQueue)
                .exploreOp(new TreeExploreOp<>(edges))
                .expandOp(expandOp)
                .pathCostOp(pathCostOp)
                .execute(graph);
    return graph.newGraph(edges);
  }

  protected PathQueue<V, W, E> pathQueue;
  protected ExpandOp<V, W, E> expandOp = ExpandOp::outgoingEdges;
  protected PathCostOp<V, W, E> pathCostOp = PathCostOp::byWeight;
}