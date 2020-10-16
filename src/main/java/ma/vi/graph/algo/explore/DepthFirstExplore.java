package ma.vi.graph.algo.explore;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.*;

/**
 * An algorithm to explore a graph in depth-first order.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class DepthFirstExplore<V, W, E extends Edge<V, W>, A, R> implements Algorithm<V, W, E, R> {
  public DepthFirstExplore(V startVertex) {
    this.startVertex = startVertex;
  }

  @Override
  public R execute(Graph<V, W, E> graph) {
    return new Explore<V, W, E, A, R>(startVertex)
                .pathQueue(new LifoPathQueue<>())
                .exploreOp(exploreOp)
                .expandOp(expandOp)
                .pathCostOp(pathCostOp)
                .accumulator(accumulator)
                .execute(graph);
  }


  /**
   * The function taking a graph and a path invoked for every path explored for the
   * graph. The accumulator parameter is passed as the last parameter to this function
   * and can be used to accumulate certain information during the exploration. It could
   * be used, for instance, to construct a spanning subgraph of this graph.
   */
  public DepthFirstExplore<V, W, E, A, R> exploreOp(ExploreOp<V, W, E, A, R> exploreOp) {
    this.exploreOp = exploreOp;
    return this;
  }

  /**
   * A function taking as parameters the graph and current exploring path and
   * returning the successor edges to explore. The default function returns
   * all outgoing edges of the current path.
   */
  public DepthFirstExplore<V, W, E, A, R> expandOp(ExpandOp<V, W, E> expandOp) {
    this.expandOp = expandOp;
    return this;
  }

  /**
   * A function to compute the cost of a new path, invoked with the graph, path
   * to be extended, the edge with which the path is being extended and, optionally,
   * a goal vertex.
   */
  public DepthFirstExplore<V, W, E, A, R> pathCostOp(PathCostOp<V, W, E> pathCostOp) {
    this.pathCostOp = pathCostOp;
    return this;
  }

  /**
   * A value supplied to the exploreOp to accumulate data during the exploration.
   * Could be used by custom graph algorithms.
   */
  public DepthFirstExplore<V, W, E, A, R> accumulator(A accumulator) {
    this.accumulator = accumulator;
    return this;
  }

  protected final V startVertex;
  protected ExpandOp<V, W, E> expandOp = ExpandOp::outgoingEdges;
  protected ExploreOp<V, W, E, A, R> exploreOp;
  protected A accumulator;
  protected PathCostOp<V, W, E> pathCostOp = PathCostOp::byWeight;
}
